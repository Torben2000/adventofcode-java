package de.beachboys.aoc2016;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AssembunnyInterpreter {

    private final List<String> program;

    private int instructionPointer = 0;

    public enum Register {
        A, B, C, D
    }

    private final Map<Register, Integer> registers = new HashMap<>();

    public AssembunnyInterpreter(List<String> program) {
        for (Register register : Register.values()) {
            setValueToRegister(register, 0);
        }
        this.program = new ArrayList<>(program);
    }

    public void runProgram() {
        while (instructionPointer < program.size()) {
            instructionPointer += executeInstruction(program.get(instructionPointer));
        }
    }

    public void setValueToRegister(Register register, int value) {
        registers.put(register, value);
    }

    public int getValueFromRegister(Register register) {
        return registers.get(register);
    }

    private int executeInstruction(String instruction) {
        int instructionPointerManipulation = 1;
        String[] instructionParts = instruction.split(" ");
        try {
            switch (instructionParts[0]) {
                case "cpy":
                    registers.put(this.getRegister(instructionParts[2]), getValue(instructionParts[1]));
                    break;
                case "inc":
                    int valueToAdd = checkForMultiplicationAndGetResult();
                    if (valueToAdd > 1) {
                        instructionPointerManipulation = 5;
                    }
                    Register registerForInc = this.getRegister(instructionParts[1]);
                    registers.put(registerForInc, registers.get(registerForInc) + valueToAdd);
                    break;
                case "dec":
                    Register registerForDec = this.getRegister(instructionParts[1]);
                    registers.put(registerForDec, registers.get(registerForDec) - 1);
                    break;
                case "jnz":
                    if (getValue(instructionParts[1]) != 0) {
                        instructionPointerManipulation = getValue(instructionParts[2]);
                    }
                    break;
                case "tgl":
                    int lineToChange = instructionPointer + getValue(instructionParts[1]);
                    if (lineToChange >= 0 && lineToChange < program.size()) {
                        String instructionToChange = program.get(lineToChange);
                        String[] instructionToChangeParts = instructionToChange.split(" ");
                        if (instructionToChangeParts.length > 3) {
                            throw new IllegalArgumentException();
                        }
                        String newInstruction;
                        if ("inc".equals(instructionToChangeParts[0])) {
                            newInstruction = "dec";
                        } else if ("jnz".equals(instructionToChangeParts[0])) {
                            newInstruction = "cpy";
                        } else if (instructionToChangeParts.length == 2) {
                            newInstruction = "inc";
                        } else {
                            newInstruction = "jnz";
                        }
                        program.set(lineToChange, newInstruction + instructionToChange.substring(3));
                    }
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            //skip
        }
        return instructionPointerManipulation;
    }

    private int checkForMultiplicationAndGetResult() {
        int factor1 = getMultiplicationFactor(0, -2);
        if (factor1 > 1) {
            int factor2 = getMultiplicationFactor(2, -5);
            if (factor2 > 1) {
                return factor1 * factor2;
            }
        }
        return 1;
    }

    private int getMultiplicationFactor(int offset, int jumpSize) {
        int factor = 1;
        if (program.size() > instructionPointer + 2 + offset) {
            String possibleJumpInstruction = program.get(instructionPointer + 2 + offset);
            Matcher matcher = Pattern.compile("jnz ([a-z]) " + jumpSize).matcher(possibleJumpInstruction);
            if (matcher.matches()) {
                String countRegister = matcher.group(1);
                if (Pattern.compile("(inc|dec) " + countRegister).matcher(program.get(instructionPointer + 1 + offset)).matches()) {
                    factor = Math.abs(getValue(countRegister));
                }
            }
        }
        return factor;
    }

    private Register getRegister(String instructionPart) {
        return Register.valueOf(instructionPart.toUpperCase());
    }

    private int getValue(String valueReference) {
        int value;
        try {
            value = Integer.parseInt(valueReference);
        } catch (NumberFormatException e) {
            value = registers.get(getRegister(valueReference));
        }
        return value;
    }

}
