package de.beachboys.aoc2018;

import de.beachboys.Day;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day05 extends Day {

    public Object part1(List<String> input) {
        return runAllReactions(input.get(0)).length();
    }

    public Object part2(List<String> input) {
        String polymer = runAllReactions(input.get(0));

        Set<String> units = getLowerCaseUnitsFromPolymer(polymer);

        int minLength = Integer.MAX_VALUE;
        for (String unit : units) {
            String polymerWithoutUnit = polymer.replace(unit, "").replace(unit.toUpperCase(), "");
            minLength = Math.min(minLength, runAllReactions(polymerWithoutUnit).length());
        }
        return minLength;
    }

    private Set<String> getLowerCaseUnitsFromPolymer(String polymer) {
        Set<String> units = new HashSet<>();
        for (char character : polymer.toCharArray()) {
            units.add((character + "").toLowerCase());
        }
        return units;
    }

    private String runAllReactions(String polymer) {
        String currentPolymer = polymer;
        String nextPolymer = runReactionCycle(currentPolymer);
        while (!currentPolymer.equals(nextPolymer)) {
            currentPolymer = nextPolymer;
            nextPolymer = runReactionCycle(currentPolymer);
        }
        return currentPolymer;
    }

    private String runReactionCycle(String polymer) {
        StringBuilder sb = new StringBuilder();
        String previousChar = "";
        for (char character : polymer.toCharArray()) {
            String currentChar = "" + character;
            if (previousChar.equals(currentChar) || !previousChar.equalsIgnoreCase(currentChar)) {
                sb.append(previousChar);
                previousChar = currentChar;
            } else {
                previousChar = "";
            }
        }
        sb.append(previousChar);
        return sb.toString();
    }

}
