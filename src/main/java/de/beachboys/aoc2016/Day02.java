package de.beachboys.aoc2016;

import de.beachboys.Day;

import java.util.List;
import java.util.function.BiFunction;

public class Day02 extends Day {

    public Object part1(List<String> input) {
        BiFunction<String, String, String> moveToKey = this::moveToKeyPart1;
        return runLogic(input, moveToKey);
    }

    public Object part2(List<String> input) {
        BiFunction<String, String, String> moveToKey = this::moveToKeyPart2;
        return runLogic(input, moveToKey);
    }

    private Object runLogic(List<String> input, BiFunction<String, String, String> moveToKey) {
        StringBuilder code = new StringBuilder();
        String currentKey = "5";
        for (String line : input) {
            for (int i = 0; i < line.length(); i++) {
                currentKey = moveToKey.apply(currentKey, line.substring(i, i + 1));
            }
            code.append(currentKey);
        }

        return code.toString();
    }

    private String moveToKeyPart1(String currentKey, String command) {
        return switch (command + currentKey) {
            case "U1", "U4", "L1", "L2" -> "1";
            case "U2", "U5", "L3", "R1" -> "2";
            case "U3", "U6", "R2", "R3" -> "3";
            case "U7", "L4", "L5", "D1" -> "4";
            case "U8", "L6", "R4", "D2" -> "5";
            case "U9", "R5", "R6", "D3" -> "6";
            case "L7", "L8", "D4", "D7" -> "7";
            case "R7", "L9", "D5", "D8" -> "8";
            case "D9", "R8", "R9", "D6" -> "9";
            default -> throw new IllegalArgumentException();
        };
    }

    private String moveToKeyPart2(String currentKey, String command) {
        return switch (command + currentKey) {
            case "U1", "U3", "L1", "R1" -> "1";
            case "U2", "U6", "L2", "L3" -> "2";
            case "U7", "L4", "R2", "D1" -> "3";
            case "U4", "U8", "R3", "R4" -> "4";
            case "U5", "L5", "L6", "D5" -> "5";
            case "UA", "L7", "R5", "D2" -> "6";
            case "UB", "L8", "R6", "D3" -> "7";
            case "UC", "L9", "R7", "D4" -> "8";
            case "U9", "R8", "R9", "D9" -> "9";
            case "LA", "LB", "D6", "DA" -> "A";
            case "UD", "LC", "RA", "D7" -> "B";
            case "RB", "RC", "D8", "DC" -> "C";
            case "LD", "RD", "DB", "DD" -> "D";
            default -> throw new IllegalArgumentException();
        };
    }

}
