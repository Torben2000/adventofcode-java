package de.beachboys.ec2025;

import de.beachboys.Quest;
import de.beachboys.Util;

import java.util.*;

public class Quest19 extends Quest {

    @Override
    public Object part1(List<String> input) {
        Map<Integer, Set<Integer>> openHeightsPerWall = parseInput(input);
        Map<Integer, Integer> minFlapsPerHeight = new HashMap<>();
        minFlapsPerHeight.put(0, 0);
        int currentX = 0;
        for (int wallX : openHeightsPerWall.keySet().stream().sorted().toList()) {
            Map<Integer, Integer> newMinFlapsPerHeight = new HashMap<>();
            for (int height : minFlapsPerHeight.keySet()) {
                for (int targetHeight : openHeightsPerWall.get(wallX)) {
                    int diffX = wallX - currentX;
                    int diffY = targetHeight - height;
                    if (isReachableWithFlaps(diffX, diffY)) {
                        int numFlaps = Math.max(diffY, 0) + (diffX - Math.abs(diffY)) / 2; // manage height difference + keep height
                        if (isNumFlapsPossible(numFlaps, diffX)) {
                            newMinFlapsPerHeight.put(targetHeight, Math.min(newMinFlapsPerHeight.getOrDefault(targetHeight, Integer.MAX_VALUE), minFlapsPerHeight.get(height) + numFlaps));
                        }
                    }
                }
            }
            minFlapsPerHeight = newMinFlapsPerHeight;
            currentX = wallX;
        }
        return minFlapsPerHeight.values().stream().min(Integer::compareTo).orElseThrow();
    }

    private static boolean isNumFlapsPossible(int numFlaps, int diffX) {
        return numFlaps >= 0 && numFlaps <= diffX;
    }

    private static boolean isReachableWithFlaps(int diffX, int diffY) {
        return (diffX - diffY) % 2 == 0;
    }

    private static Map<Integer, Set<Integer>> parseInput(List<String> input) {
        Map<Integer, Set<Integer>> openHeightsPerWall = new HashMap<>();
        for (String line : input) {
            List<Integer> lineAsInts = Util.parseIntCsv(line);
            Set<Integer> openHeights = openHeightsPerWall.getOrDefault(lineAsInts.get(0), new HashSet<>());
            for (int i = 0; i < lineAsInts.get(2); i++) {
                openHeights.add(lineAsInts.get(1) + i);
            }
            openHeightsPerWall.put(lineAsInts.get(0), openHeights);
        }
        return openHeightsPerWall;
    }

    @Override
    public Object part2(List<String> input) {
        return part1(input);
    }

    @Override
    public Object part3(List<String> input) {
        return part1(input);
    }
}
