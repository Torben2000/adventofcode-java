package de.beachboys.aoc2021;

import de.beachboys.Day;

import java.util.List;

public class Day24 extends Day {

    private final long[] ram = new long[4];

    public Object part1(List<String> input) {
        return runLogic(input, calculateModelNumberPart1());
    }

    public Object part2(List<String> input) {
        return runLogic(input, calculateModelNumberPart2());
    }

    private String runLogic(List<String> input, int[] modelNumber) {
        if (checkModelNumberForValidityWithALU(input, modelNumber)) {
            return printModelNumber(modelNumber);
        }

        throw new IllegalArgumentException("something went wrong");
    }

    private int[] calculateModelNumberPart1() {
        int[] modelNumber = new int[14];
        modelNumber[0] = 9;
        modelNumber[1] = 9;
        modelNumber[2] = 3;
        modelNumber[4] = 4;
        modelNumber[5] = 8;
        modelNumber[9] = 9;
        modelNumber[10] = 1;

        modelNumber[3] = modelNumber[2] + 6;
        modelNumber[6] = modelNumber[5] + 1;
        modelNumber[7] = modelNumber[4] + 5;
        modelNumber[8] = modelNumber[1] - 1;
        modelNumber[11] = modelNumber[10] + 8;
        modelNumber[12] = modelNumber[9] - 2;
        modelNumber[13] = modelNumber[0] - 8;
        return modelNumber;
    }


    private int[] calculateModelNumberPart2() {
        int[] modelNumber = new int[14];
        modelNumber[0] = 9;
        modelNumber[1] = 2;
        modelNumber[2] = 1;
        modelNumber[4] = 1;
        modelNumber[5] = 1;
        modelNumber[9] = 3;
        modelNumber[10] = 1;

        modelNumber[3] = modelNumber[2] + 6;
        modelNumber[6] = modelNumber[5] + 1;
        modelNumber[7] = modelNumber[4] + 5;
        modelNumber[8] = modelNumber[1] - 1;
        modelNumber[11] = modelNumber[10] + 8;
        modelNumber[12] = modelNumber[9] - 2;
        modelNumber[13] = modelNumber[0] - 8;
        return modelNumber;
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
