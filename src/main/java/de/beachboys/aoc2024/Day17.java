package de.beachboys.aoc2024;

import de.beachboys.Day;
import de.beachboys.Util;

import java.util.*;
import java.util.stream.Collectors;

public class Day17 extends Day {

    private long registerA;
    private long registerB;
    private long registerC;
    private List<Integer> program;

    public Object part1(List<String> input) {
        parseInput(input);
        List<Integer> output = runProgram(registerA, registerB, registerC);
        io.logInfo(registerA);
        io.logInfo(registerB);
        io.logInfo(registerC);
        return output.stream().map(String::valueOf).collect(Collectors.joining(","));
    }

    public Object part2(List<String> input) {
        parseInput(input);
        int factor = checkSupportForPart2AndReturnFactor();
        Set<Long> numbers = Set.of(0L);
        for (int i = 1; i <= program.size(); i++) {
            Set<Long> newNumbers = new HashSet<>();
            for (long number : numbers) {
                long base = number * factor;
                for (int j = 0; j < factor; j++) {
                    long a = j + base;
                    List<Integer> output = runProgram(a, 0, 0);
                    if (output.getFirst().equals(program.get(program.size() - i))) {
                        newNumbers.add(a);
                    }
                }
            }
            numbers = newNumbers;
        }
        return numbers.stream().sorted().findFirst().orElseThrow();
    }

    private List<Integer> runProgram(long a, long b, long c) {
        registerA = a;
        registerB = b;
        registerC = c;
        List<Integer> output = new ArrayList<>();
        for (int i = 0; i < program.size(); i+=2) {
            int opcode = program.get(i);
            int operand = program.get(i+1);
            switch (opcode) {
                case 0:
                    registerA = (long) (registerA / Math.pow(2, getComboOperand(operand)));
                    break;
                case 1:
                    registerB = registerB ^ operand;
                    break;
                case 2:
                    registerB = getComboOperand(operand) % 8;
                    break;
                case 3:
                    if (registerA != 0) {
                        i = operand - 2;
                    }
                    break;
                case 4:
                    registerB = registerB ^ registerC;
                    break;
                case 5:
                    output.add((int) (getComboOperand(operand) % 8));
                    break;
                case 6:
                    registerB = (long) (registerA / Math.pow(2, getComboOperand(operand)));
                    break;
                case 7:
                    registerC = (long) (registerA / Math.pow(2, getComboOperand(operand)));
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        }
        return output;
    }

    private long getComboOperand(int operand) {
        return switch (operand) {
            case 0, 1, 2, 3 -> operand;
            case 4 -> registerA;
            case 5 -> registerB;
            case 6 -> registerC;
            default -> throw new IllegalArgumentException();
        };
    }

    private void parseInput(List<String> input) {
        registerA = Long.parseLong(input.get(0).substring("Register A: ".length()));
        registerB = Long.parseLong(input.get(1).substring("Register B: ".length()));
        registerC = Long.parseLong(input.get(2).substring("Register C: ".length()));

        program = Util.parseIntCsv(input.get(4).substring("Program: ".length()));
    }

    private int checkSupportForPart2AndReturnFactor() {
        if (program.getLast() != 0 || program.get(program.size() - 2) != 3) {
            throw new IllegalArgumentException("Only supported for jump at the end");
        }
        int factor = 0;
        for (int i = 0; i < program.size(); i+=2) {
            if (program.get(i) == 0) {
                if (factor != 0) {
                    throw new IllegalArgumentException("Only one division of A supported");
                }
                factor = (int) Math.pow(2, program.get(i + 1));
            }
        }
        return factor;
    }

}
