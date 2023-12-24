package de.beachboys.aoc2018;

import de.beachboys.Day;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple3;

import java.util.*;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day16 extends Day {

    private enum ParseMode {
        BEFORE, OPERATION, AFTER
    }

    private final Pattern beforePattern = Pattern.compile("Before: \\[([0-9]+), ([0-9]+), ([0-9]+), ([0-9]+)]");
    private final Pattern operationPattern = Pattern.compile("([0-9]+) ([0-9]+) ([0-9]+) ([0-9]+)");
    private final Pattern afterPattern = Pattern.compile("After: {2}\\[([0-9]+), ([0-9]+), ([0-9]+), ([0-9]+)]");

    private List<Integer> registers = new ArrayList<>(List.of(0, 0, 0, 0));
    private final Map<String, Consumer<Tuple3<Integer, Integer, Integer>>> operations = new HashMap<>();
    List<TestData> testDataList = new ArrayList<>();
    private final List<List<Integer>> program = new ArrayList<>();
    private final Map<Integer, String> opCodeMappings = new HashMap<>();


    public Object part1(List<String> input) {
        parseInput(input);
        buildOperationMap();
        int counter = 0;
        for (TestData testData : testDataList) {
            int matchCounter = 0;
            for (Consumer<Tuple3<Integer, Integer, Integer>> operation : operations.values()) {
                registers = new ArrayList<>(testData.before);
                operation.accept(Tuple.tuple(testData.operation.get(1), testData.operation.get(2), testData.operation.get(3)));
                if (registers.equals(testData.after)) {
                    matchCounter++;
                }
            }
            if (matchCounter >= 3) {
                counter++;
            }
        }
        return counter;
    }

    public Object part2(List<String> input) {
        parseInput(input);
        buildOperationMap();
        buildOpCodeMappingFromTestData();
        runProgram();
        return registers.get(0);
    }

    private void runProgram() {
        registers = new ArrayList<>(List.of(0, 0, 0, 0));
        for (List<Integer> programLine: program) {
            operations.get(opCodeMappings.get(programLine.get(0))).accept(Tuple.tuple(programLine.get(1), programLine.get(2), programLine.get(3)));
        }
    }

    private void buildOpCodeMappingFromTestData() {
        opCodeMappings.clear();
        Map<Integer, Set<String>> possibleOpCodeMappings = new HashMap<>();
        for (TestData testData : testDataList) {
            Set<String> possibleOperations = new HashSet<>();
            for (Map.Entry<String, Consumer<Tuple3<Integer, Integer, Integer>>> operation : operations.entrySet()) {
                registers = new ArrayList<>(testData.before);
                operation.getValue().accept(Tuple.tuple(testData.operation.get(1), testData.operation.get(2), testData.operation.get(3)));
                if (registers.equals(testData.after)) {
                    possibleOperations.add(operation.getKey());
                }
            }
            Integer opCode = testData.operation.get(0);
            possibleOpCodeMappings.putIfAbsent(opCode, possibleOperations);
            possibleOpCodeMappings.get(opCode).retainAll(possibleOperations);
        }
        while (!possibleOpCodeMappings.isEmpty()) {
            possibleOpCodeMappings.entrySet().stream().filter(entry -> entry.getValue().size() == 1).forEach(entry -> opCodeMappings.put(entry.getKey(), entry.getValue().stream().findAny().orElseThrow()));
            opCodeMappings.keySet().forEach(possibleOpCodeMappings::remove);
            possibleOpCodeMappings.values().forEach(set -> set.removeAll(opCodeMappings.values()));
        }
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
        operations.put("seti", t -> registers.set(t.v3, t.v1));
        operations.put("gtir", t -> registers.set(t.v3, t.v1 > registers.get(t.v2) ? 1 : 0));
        operations.put("gtri", t -> registers.set(t.v3, registers.get(t.v1) > t.v2 ? 1 : 0));
        operations.put("gtrr", t -> registers.set(t.v3, registers.get(t.v1) > registers.get(t.v2) ? 1 : 0));
        operations.put("eqir", t -> registers.set(t.v3, t.v1.equals(registers.get(t.v2)) ? 1 : 0));
        operations.put("eqri", t -> registers.set(t.v3, registers.get(t.v1).equals(t.v2) ? 1 : 0));
        operations.put("eqrr", t -> registers.set(t.v3, registers.get(t.v1).equals(registers.get(t.v2)) ? 1 : 0));
    }

    private void parseInput(List<String> input) {
        testDataList.clear();
        program.clear();

        TestData currentTestData = new TestData();
        ParseMode parseMode = ParseMode.BEFORE;
        boolean testDataMode = true;

        for (String line : input) {
            if (line.isEmpty()) {
                continue;
            }
            Matcher m = getMatcher(parseMode, line);
            if (m.matches()) {
                List<Integer> list = getIntegerList(m);
                if (testDataMode) {
                    switch (parseMode) {
                        case BEFORE:
                            currentTestData.before = list;
                            parseMode = ParseMode.OPERATION;
                            break;
                        case OPERATION:
                            currentTestData.operation = list;
                            parseMode = ParseMode.AFTER;
                            break;
                        case AFTER:
                            currentTestData.after = list;
                            parseMode = ParseMode.BEFORE;
                            testDataList.add(currentTestData);
                            currentTestData = new TestData();
                            break;
                        default:
                            throw new IllegalArgumentException();
                    }
                } else {
                    program.add(list);
                }
            } else {
                m = operationPattern.matcher(line);
                if (m.matches()) {
                    testDataMode = false;
                    parseMode = ParseMode.OPERATION;
                    program.add(getIntegerList(m));
                }
            }
        }
    }

    private Matcher getMatcher(ParseMode parseMode, String line) {
        Matcher m;
        switch (parseMode) {
            case BEFORE:
                m = beforePattern.matcher(line);
                break;
            case OPERATION:
                m = operationPattern.matcher(line);
                break;
            case AFTER:
                m = afterPattern.matcher(line);
                break;
            default:
                throw new IllegalArgumentException();
        }
        return m;
    }

    private List<Integer> getIntegerList(Matcher m) {
        List<Integer> list = new ArrayList<>(4);
        for (int i = 1; i <= 4; i++) {
            list.add(Integer.valueOf(m.group(i)));
        }
        return list;
    }

    private static class TestData {
        List<Integer> before;
        List<Integer> operation;
        List<Integer> after;
    }
}
