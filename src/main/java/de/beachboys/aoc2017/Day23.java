package de.beachboys.aoc2017;

import de.beachboys.Day;
import de.beachboys.Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day23 extends Day {

    public Object part1(List<String> input) {
        Assembly asm = new Assembly();
        asm.runProgram(input);
        return asm.mulCounter;
    }

    public Object part2(List<String> input) {
        int min = Util.getIntValueFromUser("Min number", 108400, io);
        int max = Util.getIntValueFromUser("Max number", 125400, io);
        int counter = 0;
        for (int i = min; i <= max; i = i + 17) {
            if (!isPrime(i)) {
                counter++;
            }
        }
        return counter;
    }

    private boolean isPrime(int number) {
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }


    private static class Assembly {

        private int instructionPointer = 0;
        private final Map<String, Long> registers = new HashMap<>();
        private int mulCounter = 0;

        public void runProgram(List<String> program) {
            while (instructionPointer < program.size()) {
                instructionPointer += (int) executeInstruction(program.get(instructionPointer));
            }
        }

        private long executeInstruction(String instruction) {
            long instructionPointerManipulation = 1;
            String[] instructionParts = instruction.split(" ");
            switch (instructionParts[0]) {
                case "set":
                    setValue(instructionParts[1], getValue(instructionParts[2]));
                    break;
                case "sub":
                    setValue(instructionParts[1], getValue(instructionParts[1]) - getValue(instructionParts[2]));
                    break;
                case "mul":
                    setValue(instructionParts[1], getValue(instructionParts[1]) * getValue(instructionParts[2]));
                    mulCounter++;
                    break;
                case "jnz":
                    if (getValue(instructionParts[1]) != 0) {
                        instructionPointerManipulation = getValue(instructionParts[2]);
                    }
                    break;
                default:
                    throw new IllegalArgumentException();
            }
            return instructionPointerManipulation;
        }

        public void setValue(String register, long value) {
            registers.put(register, value);
        }

        public long getValue(String valueReference) {
            long value;
            try {
                value = Long.parseLong(valueReference);
            } catch (NumberFormatException e) {
                value = registers.getOrDefault(valueReference, 0L);
            }
            return value;
        }

    }

}
