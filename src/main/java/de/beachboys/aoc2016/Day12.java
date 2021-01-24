package de.beachboys.aoc2016;

import de.beachboys.Day;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day12 extends Day {

    private final Map<String, Integer> registers = new HashMap<>();

    public Object part1(List<String> input) {
        return runLogic(input, 0);
    }

    public Object part2(List<String> input) {
        return runLogic(input, 1);
    }

    private Integer runLogic(List<String> input, int initialValueC) {
        AssembunnyInterpreter interpreter = new AssembunnyInterpreter(input);
        interpreter.setValueToRegister(AssembunnyInterpreter.Register.C, initialValueC);
        interpreter.runProgram();
        return interpreter.getValueFromRegister(AssembunnyInterpreter.Register.A);
        /*
        registers.put("a", 0);
        registers.put("b", 0);
        registers.put("c", initialValueC);
        registers.put("d", 0);
        int instructionPointer = 0;
        while (instructionPointer < input.size()) {
            instructionPointer += executeInstruction(input.get(instructionPointer));
        }
        return registers.get("a");

         */
    }

    private int executeInstruction(String instruction) {
        int instructionPointerManipulation = 1;
        String[] instructionParts = instruction.split(" ");
        switch (instructionParts[0]) {
            case "cpy":
                registers.put(instructionParts[2], getValue(instructionParts[1]));
                break;
            case "inc":
                registers.put(instructionParts[1], registers.get(instructionParts[1]) + 1);
                break;
            case "dec":
                registers.put(instructionParts[1], registers.get(instructionParts[1]) - 1);
                break;
            case "jnz":
                if (getValue(instructionParts[1]) != 0) {
                    instructionPointerManipulation = Integer.parseInt(instructionParts[2]);
                }
        }
        return instructionPointerManipulation;
    }

    private int getValue(String valueReference) {
        int value;
        try {
            value = Integer.parseInt(valueReference);
        } catch (NumberFormatException e) {
            value = registers.get(valueReference);
        }
        return value;
    }

}
