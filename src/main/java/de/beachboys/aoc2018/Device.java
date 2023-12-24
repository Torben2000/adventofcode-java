package de.beachboys.aoc2018;

import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;
import org.jooq.lambda.tuple.Tuple3;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Device {

    public final List<Long> registers = new ArrayList<>(List.of(0L, 0L, 0L, 0L, 0L, 0L));
    private final Map<String, Consumer<Tuple3<Integer, Integer, Integer>>> operations = new HashMap<>();
    private final List<Tuple2<String, Tuple3<Integer, Integer, Integer>>> program = new ArrayList<>();
    private final int ip;

    public Device(List<String> programCode) {
        buildOperationMap();
        ip = Integer.parseInt(programCode.getFirst().substring(4));
        program.clear();
        Pattern operationPattern = Pattern.compile("([a-z]+) ([0-9]+) ([0-9]+) ([0-9]+)");
        for (String line : programCode.subList(1, programCode.size())) {
            Matcher m = operationPattern.matcher(line);
            if (m.matches()) {
                program.add(Tuple.tuple(m.group(1), Tuple.tuple(Integer.parseInt(m.group(2)), Integer.parseInt(m.group(3)), Integer.parseInt(m.group(4)))));
            }
        }
    }

    public Optional<Object> runProgram(Function<Tuple2<String, Tuple3<Integer, Integer, Integer>>, Optional<Object>> specialLogic) {
        while (registers.get(ip) < program.size()) {
            int lineToExecute = registers.get(ip).intValue();
            Tuple2<String, Tuple3<Integer, Integer, Integer>> programLine = program.get(lineToExecute);
            Optional<Object> returnValue = specialLogic.apply(programLine);
            if (returnValue.isPresent()) {
                return returnValue;
            }
            operations.get(programLine.v1).accept(programLine.v2);
            registers.set(ip, registers.get(ip) + 1);
        }
        return Optional.empty();
    }

    private void buildOperationMap() {
        operations.put("addr", t -> registers.set(t.v3, registers.get(t.v1) + registers.get(t.v2)));
        operations.put("addi", t -> registers.set(t.v3, registers.get(t.v1) + t.v2));
        operations.put("mulr", t -> registers.set(t.v3, registers.get(t.v1) * registers.get(t.v2)));
        operations.put("muli", t -> registers.set(t.v3, registers.get(t.v1) * t.v2));
        operations.put("banr", t -> registers.set(t.v3, registers.get(t.v1) & registers.get(t.v2)));
        operations.put("bani", t -> registers.set(t.v3, registers.get(t.v1) & t.v2));
        operations.put("borr", t -> registers.set(t.v3, registers.get(t.v1) | registers.get(t.v2)));
        operations.put("bori", t -> registers.set(t.v3, registers.get(t.v1) | t.v2));
        operations.put("setr", t -> registers.set(t.v3, registers.get(t.v1)));
        operations.put("seti", t -> registers.set(t.v3, t.v1.longValue()));
        operations.put("gtir", t -> registers.set(t.v3, t.v1 > registers.get(t.v2) ? 1L : 0L));
        operations.put("gtri", t -> registers.set(t.v3, registers.get(t.v1) > t.v2 ? 1L : 0L));
        operations.put("gtrr", t -> registers.set(t.v3, registers.get(t.v1) > registers.get(t.v2) ? 1L : 0L));
        operations.put("eqir", t -> registers.set(t.v3, Long.valueOf(t.v1).equals(registers.get(t.v2)) ? 1L : 0L));
        operations.put("eqri", t -> registers.set(t.v3, registers.get(t.v1).equals(Long.valueOf(t.v2)) ? 1L : 0L));
        operations.put("eqrr", t -> registers.set(t.v3, registers.get(t.v1).equals(registers.get(t.v2)) ? 1L : 0L));
    }

}
