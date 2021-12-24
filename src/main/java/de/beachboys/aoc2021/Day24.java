package de.beachboys.aoc2021;

import de.beachboys.Day;
import org.javatuples.Pair;

import java.util.*;
import java.util.function.IntUnaryOperator;

public class Day24 extends Day {

    private final long[] ram = new long[4];

    public Object part1(List<String> input) {
        return runLogic(input, 9, d -> d - 1);
    }

    public Object part2(List<String> input) {
        return runLogic(input, 1, d -> d + 1);
    }

    private String runLogic(List<String> input, int firstDigitValueToTry, IntUnaryOperator nextDigitToTry) {
        int[] modelNumber = getFirstPossibleModelNumber(input, firstDigitValueToTry, nextDigitToTry);
        if (checkModelNumberForValidityWithALU(input, modelNumber)) {
            return printModelNumber(modelNumber);
        }

        throw new IllegalArgumentException("something went wrong");
    }

    private int[] getFirstPossibleModelNumber(List<String> aluProgram, int firstDigitValueToTry, IntUnaryOperator nextDigitToTry) {
        Map<Integer, Pair<Integer, Integer>> calculatedDigits = getCalculatedDigits(aluProgram);

        int[] modelNumber = new int[14];

        int currentDigitIndex = 0;
        for (int i = 0; i < 7; i++) {
            int digit = firstDigitValueToTry;
            while (!calculatedDigits.containsKey(currentDigitIndex)) {
                currentDigitIndex++;
            }

            Pair<Integer, Integer> calculation = calculatedDigits.get(currentDigitIndex);

            boolean digitFound = false;
            while (digit > 0 && digit < 10) {
                int otherDigit = digit + calculation.getValue1();
                if (otherDigit > 0 && otherDigit < 10) {
                    modelNumber[currentDigitIndex] = digit;
                    modelNumber[calculation.getValue0()] = otherDigit;
                    digitFound = true;
                    break;
                }
                digit = nextDigitToTry.applyAsInt(digit);
            }
            if (!digitFound) {
                throw new IllegalArgumentException("no possible digit found for index " + currentDigitIndex);
            }
            currentDigitIndex++;
        }
        return modelNumber;
    }

    private Map<Integer, Pair<Integer, Integer>> getCalculatedDigits(List<String> aluProgram) {
        Map<Integer, Pair<Integer, Integer>> calculatedDigits = new HashMap<>();

        int curDigit = -1;
        Deque<Integer> inputDigits = new LinkedList<>();
        Map<Integer, Integer> diffValues = new HashMap<>();
        for (int i = 0; i < aluProgram.size(); i++) {
            String line = aluProgram.get(i);
            if (line.startsWith("div z ")) {
                curDigit++;
                if ("1".equals(line.substring("div z ".length()))) {
                    inputDigits.add(curDigit);
                } else {
                    Integer inputDigit = inputDigits.pollLast();
                    int diff = diffValues.get(inputDigit) + Integer.parseInt(aluProgram.get(i + 1).substring("add x ".length()));
                    calculatedDigits.put(inputDigit, Pair.with(curDigit, diff));
                }
            } else if ("add y w".equals(line)) {
                diffValues.put(curDigit, Integer.valueOf(aluProgram.get(i + 1).substring("add y ".length())));
            }
        }
        return calculatedDigits;
    }

    private boolean checkModelNumberForValidityWithALU(List<String> aluProgram, int[] modelNumber) {
        ram[0] = 0;
        ram[1] = 0;
        ram[2] = 0;
        ram[3] = 0;
        int digitIndex = 0;
        for (String line : aluProgram) {
            switch (line.substring(0, 3)) {
                case "inp":
                    ram[getIndex(line.charAt(4))] = modelNumber[digitIndex];
                    digitIndex++;
                    break;
                case "add":
                    ram[getIndex(line.charAt(4))] = ram[getIndex(line.charAt(4))] + getValue(line.substring(6));
                    break;
                case "mul":
                    ram[getIndex(line.charAt(4))] = ram[getIndex(line.charAt(4))] * getValue(line.substring(6));
                    break;
                case "div":
                    ram[getIndex(line.charAt(4))] = ram[getIndex(line.charAt(4))] / getValue(line.substring(6));
                    break;
                case "mod":
                    ram[getIndex(line.charAt(4))] = ram[getIndex(line.charAt(4))] % getValue(line.substring(6));
                    break;
                case "eql":
                    ram[getIndex(line.charAt(4))] = (ram[getIndex(line.charAt(4))] == getValue(line.substring(6))) ? 1 : 0;
                    break;
            }
        }
        return ram[3] == 0;
    }

    private String printModelNumber(int[] modelNumber) {
        StringBuilder modelNumberAsString = new StringBuilder();
        for (int i = 0; i < 14; i++) {
            modelNumberAsString.append(modelNumber[i]);
        }
        return modelNumberAsString.toString();
    }

    private long getValue(String substring) {
        try {
            return Long.parseLong(substring);
        } catch (NumberFormatException e) {
            return ram[getIndex(substring.charAt(0))];
        }
    }

    int getIndex(char c) {
        switch (c) {
            case 'w':
                return 0;
            case 'x':
                return 1;
            case 'y':
                return 2;
            case 'z':
                return 3;
            default:
                throw new IllegalArgumentException();
        }
    }

}
