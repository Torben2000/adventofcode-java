package de.beachboys.aoc2022;

import de.beachboys.Day;
import de.beachboys.Util;
import org.javatuples.Pair;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day15 extends Day {

    private static final Pattern LINE_PATTERN = Pattern.compile("Sensor at x=([-0-9]+), y=([-0-9]+): closest beacon is at x=([-0-9]+), y=([-0-9]+)");
    public Object part1(List<String> input) {
        long result = 0;
        int rowToCheck = Util.getIntValueFromUser("Row to check", 2000000, io);
        Map<Integer, List<Pair<Integer, Integer>>> blockedSpaces = getBlockedSpaceByRow(input, rowToCheck, rowToCheck);

        for (Pair<Integer, Integer> blockedSpace : blockedSpaces.get(rowToCheck)) {
            result += blockedSpace.getValue1() - blockedSpace.getValue0();
        }

        return result;
    }

    public Object part2(List<String> input) {
        int max = Util.getIntValueFromUser("Max coordinate", 4000000, io);
        Map<Integer, List<Pair<Integer, Integer>>> blockedSpaces = getBlockedSpaceByRow(input, 0, max);

        for (int y = 0; y <= max; y++) {
            List<Pair<Integer, Integer>> blockedSpacesByRow = blockedSpaces.get(y);
            blockedSpacesByRow = blockedSpacesByRow.stream().sorted(Comparator.comparingInt(Pair::getValue0)).collect(Collectors.toList());
            int x = 0;
            for (Pair<Integer, Integer> pair : blockedSpacesByRow) {
                if (pair.getValue0() > x) {
                    return 4000000L * (long) x + y;
                }
                x = Math.max(x, pair.getValue1() + 1);
            }
        }

        return "No space found";
    }

    private Map<Integer, List<Pair<Integer, Integer>>> getBlockedSpaceByRow(List<String> input, int min, int max) {
        Map<Integer, List<Pair<Integer, Integer>>> blockedSpaceByRow = new HashMap<>();
        for (String line : input) {
            Matcher m = LINE_PATTERN.matcher(line);
            if (m.matches()) {
                Pair<Integer, Integer> sensor = Pair.with(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)));
                Pair<Integer, Integer> beacon = Pair.with(Integer.parseInt(m.group(3)), Integer.parseInt(m.group(4)));
                int distance = Util.getManhattanDistance(sensor, beacon);

                for (int xDistance = 0; xDistance <= distance; xDistance++) {
                    int yDistance = distance - xDistance;
                    int x1 = sensor.getValue0() - xDistance;
                    int x2 = sensor.getValue0() + xDistance;
                    int y1 = sensor.getValue1() - yDistance;
                    int y2 = sensor.getValue1() + yDistance;
                    if (y1 >= min) {
                        addBlockedSpace(blockedSpaceByRow, x1, x2, y1);
                    }
                    if (y2 <= max) {
                        addBlockedSpace(blockedSpaceByRow, x1, x2, y2);
                    }
                }
            }

        }
        return blockedSpaceByRow;
    }

    private static void addBlockedSpace(Map<Integer, List<Pair<Integer, Integer>>> blockedSpaceByRow, int start, int end, int row) {
        List<Pair<Integer, Integer>> blockedSpacesOfRow = blockedSpaceByRow.getOrDefault(row, new ArrayList<>());
        int mergedStart = start;
        int mergedEnd = end;
        Set<Pair<Integer, Integer>> blockedSpacesToRemove = new HashSet<>();
        for (Pair<Integer, Integer> blockedSpace : blockedSpacesOfRow) {
            if (mergedStart <= blockedSpace.getValue1() && blockedSpace.getValue0() <= mergedEnd) {
                blockedSpacesToRemove.add(blockedSpace);
                mergedStart = Math.min(mergedStart, blockedSpace.getValue0());
                mergedEnd = Math.max(mergedEnd, blockedSpace.getValue1());
            }
        }
        blockedSpacesOfRow.removeAll(blockedSpacesToRemove);
        blockedSpacesOfRow.add(Pair.with(mergedStart, mergedEnd));
        blockedSpaceByRow.put(row, blockedSpacesOfRow);
    }

}
