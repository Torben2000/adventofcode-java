package de.beachboys.aoc2020;

import de.beachboys.Day;

import java.util.List;

public class Day03 extends Day {

    public Object part1(List<String> input) {
        boolean[][] treeMap = buildTreeMap(input);
        return countTrees(treeMap, 3, 1);
    }

    private int countTrees(boolean[][] treeMap, int xStep, int yStep) {
        int counter = 0;
        int y = 0;
        int x = 0;
        while (y < treeMap[0].length) {
            int realX = x % treeMap.length;
            if (treeMap[realX][y]) {
                counter++;
            }
            y += yStep;
            x += xStep;
        }
        return counter;
    }

    public Object part2(List<String> input) {
        boolean[][] treeMap = buildTreeMap(input);
        return ((long) countTrees(treeMap, 1, 1) * countTrees(treeMap, 3, 1)) * countTrees(treeMap, 5, 1) * countTrees(treeMap, 7, 1) * countTrees(treeMap, 1, 2);
    }

    private boolean[][] buildTreeMap(List<String> input) {
        boolean[][] isTree = new boolean[input.getFirst().length()][input.size()];
        for (int y = 0; y < input.size(); y++) {
            String currentLine = input.get(y);
            for (int x = 0; x < currentLine.length(); x++) {
                isTree[x][y] = currentLine.charAt(x) == '#';
            }
        }
        return isTree;
    }
}
