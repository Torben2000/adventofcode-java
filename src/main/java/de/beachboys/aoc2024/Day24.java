package de.beachboys.aoc2024;

import de.beachboys.Day;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;
import org.jooq.lambda.tuple.Tuple3;
import org.jooq.lambda.tuple.Tuple4;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day24 extends Day {

    private final List<Tuple4<String, String, String, String>> gates = new ArrayList<>();
    private final Map<String, Boolean> wireValues = new HashMap<>();
    private final Set<String> switchedWires = new HashSet<>();

    public Object part1(List<String> input) {
        parseInput(input);
        runLogic();
        String z = wireValues.keySet().stream().filter(k -> k.startsWith("z")).sorted(Comparator.reverseOrder()).map(k -> wireValues.get(k) ? "1": "0").collect(Collectors.joining());
        return Long.parseLong(z, 2);

    }

    /**
     * Used schematics for analysis: https://de.wikipedia.org/wiki/Volladdierer#/media/Datei:Volladdierer_Aufbau_DIN40900.svg
     */
    public Object part2(List<String> input) {
        if ("Test".equals(input.getFirst())) {
            return "Not possible";
        }
        parseInput(input);

        switchedWires.clear();
        Set<Tuple3<String, String, String>> orGates = getSpecificGates("OR");
        Set<Tuple3<String, String, String>> andGates = getSpecificGates("AND");
        Set<Tuple3<String, String, String>> xorGates = getSpecificGates("XOR");

        List<String> xAndYGates = new ArrayList<>();
        List<String> xXorYGates = new ArrayList<>();
        List<Tuple2<String, String>> sGates = new ArrayList<>();
        for (int i = 0; i < wireValues.size() / 2; i++) {
            String xStr = String.format("x%02d", i);
            String zStr = String.format("z%02d", i);
            xAndYGates.add(andGates.stream().filter(g -> g.v1.equals(xStr) || g.v2.equals(xStr)).findFirst().orElseThrow().v3);
            xXorYGates.add(xorGates.stream().filter(g -> g.v1.equals(xStr) || g.v2.equals(xStr)).findFirst().orElseThrow().v3);
            sGates.add(xorGates.stream().filter(g -> g.v3.equals(zStr)).findFirst().orElse(Tuple.tuple(null, null, zStr)).limit2());
        }
        String zExtraStr = String.format("z%02d", sGates.size());
        if (gates.stream().filter(g -> zExtraStr.equals(g.v4)).count() == 1) {
            sGates.add(xorGates.stream().filter(g -> g.v3.equals(zExtraStr)).findFirst().orElse(Tuple.tuple(null, null, zExtraStr)).limit2());
        }

        checkSGatesHaveValidType(sGates, orGates);
        checkOnlyFirstSGateIsXXorY(xXorYGates);
        checkSGatesHaveRightXXorYGateInput(sGates, xXorYGates);
        checkSGatesHaveOrGateInput(sGates, orGates, xXorYGates);
        checkXAndYGatesOutputToOrGate(xAndYGates, orGates);
        checkOrGatesHaveTwoAndGatesInput(orGates, andGates);

        String result = switchedWires.stream().sorted().collect(Collectors.joining(","));
        if (switchedWires.size() != 8) {
            throw new IllegalArgumentException("Incorrect result: " + result);
        }
        return result;
    }

    private void checkSGatesHaveValidType(List<Tuple2<String, String>> sGates, Set<Tuple3<String, String, String>> orGates) {
        for (int i = 0; i < sGates.size(); i++) {
            if (i < sGates.size() - 1 && sGates.get(i).v1 == null) {
                switchedWires.add(String.format("z%02d", i));
            }
        }
        String lastZStr = String.format("z%02d", sGates.size() - 1);
        boolean lastZFound = false;
        for (Tuple3<String, String, String> orGate : orGates) {
            if (lastZStr.equals(orGate.v3)) {
                lastZFound = true;
                break;
            }
        }
        if (!lastZFound) {
            switchedWires.add(lastZStr);
        }
    }

    private void checkSGatesHaveOrGateInput(List<Tuple2<String, String>> sGates, Set<Tuple3<String, String, String>> orGates, List<String> xXorYGates) {
        // first two skipped because there is no previous "normal" c_out or gate
        for (int i = 2; i < sGates.size(); i++) {
            Tuple2<String, String> sGate = sGates.get(i);
            if (sGate.v1 != null) {
                boolean orGateFound = false;
                for (Tuple3<String, String, String> orGate : orGates) {
                    if (sGate.v1.equals(orGate.v3) || sGate.v2.equals(orGate.v3)) {
                        orGateFound = true;
                        break;
                    }
                }
                if (!orGateFound) {
                    if (sGate.v1.equals(xXorYGates.get(i))) {
                        switchedWires.add(sGate.v2);
                    } else if (sGate.v2.equals(xXorYGates.get(i))) {
                        switchedWires.add(sGate.v1);
                    }
                }
            }
        }
    }

    private void checkSGatesHaveRightXXorYGateInput(List<Tuple2<String, String>> sGates, List<String> xXorYGates) {
        for (int i = 1; i < sGates.size(); i++) {
            Tuple2<String, String> sGate = sGates.get(i);
            if (sGate.v1 != null) {
                if (!sGate.v1.equals(xXorYGates.get(i)) && !sGate.v2.equals(xXorYGates.get(i))) {
                    switchedWires.add(xXorYGates.get(i));
                }
            }
        }
    }

    private void checkOrGatesHaveTwoAndGatesInput(Set<Tuple3<String, String, String>> orGates, Set<Tuple3<String, String, String>> andGates) {
        for (Tuple3<String, String, String> orGate : orGates) {
            boolean foundConnection1 = false;
            boolean foundConnection2 = false;
            for (Tuple3<String, String, String> andGate : andGates) {
                if (orGate.v1.equals(andGate.v3)) {
                    foundConnection1 = true;
                }
                if (orGate.v2.equals(andGate.v3)) {
                    foundConnection2 = true;
                }
            }
            if (!foundConnection1) {
                switchedWires.add(orGate.v1);
            }
            if (!foundConnection2) {
                switchedWires.add(orGate.v2);
            }
        }
    }

    private void checkXAndYGatesOutputToOrGate(List<String> xAndYGates, Set<Tuple3<String, String, String>> orGates) {
        // first one skipped because there is no previous c_out
        for (int i = 1; i < xAndYGates.size(); i++) {
            String xAndYGate = xAndYGates.get(i);
            boolean found = false;
            for (Tuple3<String, String, String> orGate : orGates) {
                if (xAndYGate.equals(orGate.v1) || xAndYGate.equals(orGate.v2)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                switchedWires.add(xAndYGate);
            }
        }
    }

    private void checkOnlyFirstSGateIsXXorY(List<String> xXorYGates) {
        if (!"z00".equals(xXorYGates.getFirst())) {
            switchedWires.add("z00");
        }
        for (String xOrYGate : xXorYGates.subList(1, xXorYGates.size())) {
            if (xOrYGate.startsWith("z")) {
                switchedWires.add(xOrYGate);
            }
        }
    }

    private Set<Tuple3<String, String, String>> getSpecificGates(String gateType) {
        Set<Tuple3<String, String, String>> specificGate = new HashSet<>();
        for (Tuple4<String, String, String, String> gate : gates.stream().filter(t -> gateType.equals(t.v2)).toList()) {
            specificGate.add(Tuple.tuple(gate.v1, gate.v3, gate.v4));
        }
        return specificGate;
    }

    private void runLogic() {
        List<Tuple4<String, String, String, String>> gatesToProcess = gates;
        while (!gatesToProcess.isEmpty()) {
            List<Tuple4<String, String, String, String>> next = new ArrayList<>();
            for (Tuple4<String, String, String, String> gate : gatesToProcess) {
                if (wireValues.containsKey(gate.v1) && wireValues.containsKey(gate.v3)) {
                    boolean currentValue = switch (gate.v2) {
                        case "AND" -> wireValues.get(gate.v1) && wireValues.get(gate.v3);
                        case "OR" -> wireValues.get(gate.v1) || wireValues.get(gate.v3);
                        case "XOR" -> wireValues.get(gate.v1) != wireValues.get(gate.v3);
                        case null, default -> throw new IllegalArgumentException();
                    };
                    wireValues.put(gate.v4, currentValue);
                } else {
                    next.add(gate);
                }
            }
            gatesToProcess = next;
        }
    }

    private void parseInput(List<String> input) {
        gates.clear();
        wireValues.clear();
        boolean parseGates = false;
        for (String line : input) {
            if (line.isEmpty()) {
                parseGates = true;
            } else if (parseGates) {
                String[] split = line.split(Pattern.quote(" "));
                Tuple4<String, String, String, String> t = Tuple.tuple(split[0], split[1], split[2], split[4]);
                gates.add(t);
            } else {
                String[] split = line.split(Pattern.quote(": "));
                wireValues.put(split[0], "1".equals(split[1]));
            }
        }
    }

}
