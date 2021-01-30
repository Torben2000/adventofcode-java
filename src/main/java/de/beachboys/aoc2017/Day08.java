package de.beachboys.aoc2017;

import de.beachboys.Day;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day08 extends Day {

    private final Map<String, Integer> registers = new HashMap<>();

    private int highestRegisterValueEver = 0;

    public Object part1(List<String> input) {
        runLogic(input);
        return registers.values().stream().mapToInt(Integer::intValue).max().orElseThrow();
    }

    public Object part2(List<String> input) {
        runLogic(input);
        return highestRegisterValueEver;
    }

    private void runLogic(List<String> input) {
        Pattern p = Pattern.compile("([a-z]+) (inc|dec) (-?[0-9]+) if ([a-z]+) ([!=<>]+) (-?[0-9]+)");
        for (String line : input) {
            Matcher m = p.matcher(line);
            if (m.matches()) {
                String register = m.group(1);
                int factor = "inc".equals(m.group(2)) ? 1 : -1;
                int modifier = Integer.parseInt(m.group(3));
                String comparisonRegister = m.group(4);
                String comparisonOperator = m.group(5);
                int comparisonValue = Integer.parseInt(m.group(6));
                if (isComparisonTrue(comparisonRegister, comparisonOperator, comparisonValue)) {
                    int newValue = registers.getOrDefault(register, 0) + factor * modifier;
                    registers.put(register, newValue);
                    highestRegisterValueEver = Math.max(highestRegisterValueEver, newValue);
                }
            }
        }
    }

    private boolean isComparisonTrue(String comparisonRegister, String comparisonOperator, int comparisonValue) {
        int registerValue = registers.getOrDefault(comparisonRegister, 0);
        switch (comparisonOperator) {
            case "==":
                return registerValue == comparisonValue;
            case "!=":
                return registerValue != comparisonValue;
            case ">":
                return registerValue > comparisonValue;
            case "<":
                return registerValue < comparisonValue;
            case ">=":
                return registerValue >= comparisonValue;
            case "<=":
                return registerValue <= comparisonValue;
        }
        throw new IllegalArgumentException();
    }

}
