package de.beachboys.aoc2018;

import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Device {

    public final List<Long> registers = new ArrayList<>(List.of(0L, 0L, 0L, 0L, 0L, 0L));
    private final Map<String, Consumer<Triplet<Integer, Integer, Integer>>> operations = new HashMap<>();
    private final List<Pair<String, Triplet<Integer, Integer, Integer>>> program = new ArrayList<>();
    private final int ip;

    public Device(List<String> programCode) {
        buildOperationMap();
        ip = Integer.parseInt(programCode.get(0).substring(4));
        program.clear();
        Pattern operationPattern = Pattern.compile("([a-z]+) ([0-9]+) ([0-9]+) ([0-9]+)");
        for (String line : programCode.subList(1, programCode.size())) {
            Matcher m = operationPattern.matcher(line);
            if (m.matches()) {
                program.add(Pair.with(m.group(1), Triplet.with(Integer.parseInt(m.group(2)), Integer.parseInt(m.group(3)), Integer.parseInt(m.group(4)))));
            }
        }
    }

    public Optional<Object> runProgram(Function<Pair<String, Triplet<Integer, Integer, Integer>>, Optional<Object>> specialLogic) {
        while (registers.get(ip) < program.size()) {
            int lineToExecute = registers.get(ip).intValue();
            Pair<String, Triplet<Integer, Integer, Integer>> programLine = program.get(lineToExecute);
            Optional<Object> returnValue = specialLogic.apply(programLine);
            if (returnValue.isPresent()) {
                return returnValue;
            }
            operations.get(programLine.getValue0()).accept(programLine.getValue1());
            registers.set(ip, registers.get(ip) + 1);
        }
        return Optional.empty();
    }

    private void buildOperationMap() {
        operations.put("addr", t -> registers.set(t.getValue2(), registers.get(t.getValue0()) + registers.get(t.getValue1())));
        operations.put("addi", t -> registers.set(t.getValue2(), registers.get(t.getValue0()) + t.getValue1()));
        operations.put("mulr", t -> registers.set(t.getValue2(), registers.get(t.getValue0()) * registers.get(t.getValue1())));
        operations.put("muli", t -> registers.set(t.getValue2(), registers.get(t.getValue0()) * t.getValue1()));
        operations.put("banr", t -> registers.set(t.getValue2(), registers.get(t.getValue0()) & registers.get(t.getValue1())));
        operations.put("bani", t -> registers.set(t.getValue2(), registers.get(t.getValue0()) & t.getValue1()));
        operations.put("borr", t -> registers.set(t.getValue2(), registers.get(t.getValue0()) | registers.get(t.getValue1())));
        operations.put("bori", t -> registers.set(t.getValue2(), registers.get(t.getValue0()) | t.getValue1()));
        operations.put("setr", t -> registers.set(t.getValue2(), registers.get(t.getValue0())));
        operations.put("seti", t -> registers.set(t.getValue2(), t.getValue0().longValue()));
        operations.put("gtir", t -> registers.set(t.getValue2(), t.getValue0() > registers.get(t.getValue1()) ? 1L : 0L));
        operations.put("gtri", t -> registers.set(t.getValue2(), registers.get(t.getValue0()) > t.getValue1() ? 1L : 0L));
        operations.put("gtrr", t -> registers.set(t.getValue2(), registers.get(t.getValue0()) > registers.get(t.getValue1()) ? 1L : 0L));
        operations.put("eqir", t -> registers.set(t.getValue2(), Long.valueOf(t.getValue0()).equals(registers.get(t.getValue1())) ? 1L : 0L));
        operations.put("eqri", t -> registers.set(t.getValue2(), registers.get(t.getValue0()).equals(Long.valueOf(t.getValue1())) ? 1L : 0L));
        operations.put("eqrr", t -> registers.set(t.getValue2(), registers.get(t.getValue0()).equals(registers.get(t.getValue1())) ? 1L : 0L));
    }

}
