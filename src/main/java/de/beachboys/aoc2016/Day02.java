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
        switch (command + currentKey) {
            case "U1":
            case "U4":
            case "L1":
            case "L2":
                return "1";
            case "U2":
            case "U5":
            case "L3":
            case "R1":
                return "2";
            case "U3":
            case "U6":
            case "R2":
            case "R3":
                return "3";
            case "U7":
            case "L4":
            case "L5":
            case "D1":
                return "4";
            case "U8":
            case "L6":
            case "R4":
            case "D2":
                return "5";
            case "U9":
            case "R5":
            case "R6":
            case "D3":
                return "6";
            case "L7":
            case "L8":
            case "D4":
            case "D7":
                return "7";
            case "R7":
            case "L9":
            case "D5":
            case "D8":
                return "8";
            case "D9":
            case "R8":
            case "R9":
            case "D6":
                return "9";
        }
        throw new IllegalArgumentException();
    }

    private String moveToKeyPart2(String currentKey, String command) {
        switch (command + currentKey) {
            case "U1":
            case "U3":
            case "L1":
            case "R1":
                return "1";
            case "U2":
            case "U6":
            case "L2":
            case "L3":
                return "2";
            case "U7":
            case "L4":
            case "R2":
            case "D1":
                return "3";
            case "U4":
            case "U8":
            case "R3":
            case "R4":
                return "4";
            case "U5":
            case "L5":
            case "L6":
            case "D5":
                return "5";
            case "UA":
            case "L7":
            case "R5":
            case "D2":
                return "6";
            case "UB":
            case "L8":
            case "R6":
            case "D3":
                return "7";
            case "UC":
            case "L9":
            case "R7":
            case "D4":
                return "8";
            case "U9":
            case "R8":
            case "R9":
            case "D9":
                return "9";
            case "LA":
            case "LB":
            case "D6":
            case "DA":
                return "A";
            case "UD":
            case "LC":
            case "RA":
            case "D7":
                return "B";
            case "RB":
            case "RC":
            case "D8":
            case "DC":
                return "C";
            case "LD":
            case "RD":
            case "DB":
            case "DD":
                return "D";
        }
        throw new IllegalArgumentException();
    }

}
