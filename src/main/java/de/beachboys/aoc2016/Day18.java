package de.beachboys.aoc2016;

import de.beachboys.Day;
import de.beachboys.Util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day18 extends Day {

    public Object part1(List<String> input) {
        return runLogic(input, 40);
    }

    public Object part2(List<String> input) {
        return runLogic(input, 400000);
    }

    private long runLogic(List<String> input, int defaultNumberOfLines) {
        int numberOfLines = Util.getIntValueFromUser("Number of lines", defaultNumberOfLines, io);
        String firstLine = input.getFirst();
        int width = firstLine.length();
        Set<Integer> safeTilesNextLine = new HashSet<>();
        for (int i = 0; i < width; i++) {
            if (".".equals(firstLine.substring(i, i + 1))) {
                safeTilesNextLine.add(i);
            }
        }
        long numberOfSafeTiles = safeTilesNextLine.size();

        for (int i = 0; i < numberOfLines - 1; i++) {
            Set<Integer> safeTilesCurrentLine = safeTilesNextLine;
            safeTilesNextLine = new HashSet<>();
            safeTilesCurrentLine.add(-1);
            safeTilesCurrentLine.add(width);
            for (int j = 0; j < width; j++) {
                boolean left = safeTilesCurrentLine.contains(j - 1);
                boolean right = safeTilesCurrentLine.contains(j + 1);
                if (left == right) {
                    safeTilesNextLine.add(j);
                }
            }
            numberOfSafeTiles += safeTilesNextLine.size();
        }

        return numberOfSafeTiles;
    }

}
