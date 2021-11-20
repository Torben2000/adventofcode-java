package de.beachboys.aoc2018;

import de.beachboys.Day;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day19 extends Day {

    private final Pattern operationPattern = Pattern.compile("([a-z]+) ([0-9]+) ([0-9]+) ([0-9]+)");
    private List<Long> registers;
    private final Map<String, Consumer<Triplet<Integer, Integer, Integer>>> operations = new HashMap<>();
    private final List<Pair<String, Triplet<Integer, Integer, Integer>>> program = new ArrayList<>();

    public Object part1(List<String> input) {
        registers = new ArrayList<>(List.of(0L, 0L, 0L, 0L, 0L, 0L));
        return runLogic(input);
    }

    public Object part2(List<String> input) {
        registers = new ArrayList<>(List.of(1L, 0L, 0L, 0L, 0L, 0L));
        return runLogic(input);
    }

    private Long runLogic(List<String> input) {
        buildOperationMap();
        int ip = Integer.parseInt(input.get(0).substring(4));
        program.clear();
        for (String line : input.subList(1, input.size())) {
            Matcher m = operationPattern.matcher(line);
            if (m.matches()) {
                program.add(Pair.with(m.group(1), Triplet.with(Integer.parseInt(m.group(2)), Integer.parseInt(m.group(3)), Integer.parseInt(m.group(4)))));
            }
        }
         while (registers.get(ip) < program.size()) {
            int lineToExecute = registers.get(ip).intValue();
            Pair<String, Triplet<Integer, Integer, Integer>> programLine = program.get(lineToExecute);
            // special handling for real logic
            if (Pair.with("seti", Triplet.with(1, 5, 2)).equals(programLine)) {
               return getSumOfAllDivisors(registers.get(5));
            }
            operations.get(programLine.getValue0()).accept(programLine.getValue1());
            registers.set(ip, registers.get(ip) + 1);
        }
        return registers.get(0);
    }

    private long getSumOfAllDivisors(long longToCheck) {
        long sum = 0;
        for (long i = 1; i <= longToCheck; i++) {
            if (longToCheck % i == 0) {
                sum += i;
            }
        }
        return sum;
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
