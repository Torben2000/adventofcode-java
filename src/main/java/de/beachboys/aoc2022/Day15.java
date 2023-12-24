package de.beachboys.aoc2022;

import de.beachboys.Day;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day15 extends Day {

    private static final Pattern LINE_PATTERN = Pattern.compile("Sensor at x=([-0-9]+), y=([-0-9]+): closest beacon is at x=([-0-9]+), y=([-0-9]+)");
    public Object part1(List<String> input) {
        long result = 0;
        int rowToCheck = Util.getIntValueFromUser("Row to check", 2000000, io);
        Map<Integer, List<Tuple2<Integer, Integer>>> blockedSpaces = getBlockedSpaceByRow(input, rowToCheck, rowToCheck);

        for (Tuple2<Integer, Integer> blockedSpace : blockedSpaces.get(rowToCheck)) {
            result += blockedSpace.v2 - blockedSpace.v1;
        }

        return result;
    }

    public Object part2(List<String> input) {
        int max = Util.getIntValueFromUser("Max coordinate", 4000000, io);
        Map<Integer, List<Tuple2<Integer, Integer>>> blockedSpaces = getBlockedSpaceByRow(input, 0, max);

        for (int y = 0; y <= max; y++) {
            List<Tuple2<Integer, Integer>> blockedSpacesByRow = blockedSpaces.get(y);
            blockedSpacesByRow = blockedSpacesByRow.stream().sorted(Comparator.comparingInt(Tuple2::v1)).toList();
            int x = 0;
            for (Tuple2<Integer, Integer> pair : blockedSpacesByRow) {
                if (pair.v1 > x) {
                    return 4000000L * (long) x + y;
                }
                x = Math.max(x, pair.v2 + 1);
            }
        }

        return "No space found";
    }

    private Map<Integer, List<Tuple2<Integer, Integer>>> getBlockedSpaceByRow(List<String> input, int min, int max) {
        Map<Integer, List<Tuple2<Integer, Integer>>> blockedSpaceByRow = new HashMap<>();
        for (String line : input) {
            Matcher m = LINE_PATTERN.matcher(line);
            if (m.matches()) {
                Tuple2<Integer, Integer> sensor = Tuple.tuple(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)));
                Tuple2<Integer, Integer> beacon = Tuple.tuple(Integer.parseInt(m.group(3)), Integer.parseInt(m.group(4)));
                int distance = Util.getManhattanDistance(sensor, beacon);

                for (int yDistance = -distance; yDistance <= distance; yDistance++) {
                    int y = sensor.v2 + yDistance;
                    int xDistance = distance - Math.abs(yDistance);
                    int x1 = sensor.v1 - xDistance;
                    int x2 = sensor.v1 + xDistance;
                    if (y < min) {
                        yDistance += min - y - 1;
                    } else if (y > max) {
                        break;
                    } else {
                        addBlockedSpace(blockedSpaceByRow, x1, x2, y);
                    }
                }
            }

        }
        return blockedSpaceByRow;
    }

    private static void addBlockedSpace(Map<Integer, List<Tuple2<Integer, Integer>>> blockedSpaceByRow, int start, int end, int row) {
        List<Tuple2<Integer, Integer>> blockedSpacesOfRow = blockedSpaceByRow.getOrDefault(row, new ArrayList<>());
        int mergedStart = start;
        int mergedEnd = end;
        Set<Tuple2<Integer, Integer>> blockedSpacesToRemove = new HashSet<>();
        for (Tuple2<Integer, Integer> blockedSpace : blockedSpacesOfRow) {
            if (mergedStart <= blockedSpace.v2 && blockedSpace.v1 <= mergedEnd) {
                blockedSpacesToRemove.add(blockedSpace);
                mergedStart = Math.min(mergedStart, blockedSpace.v1);
                mergedEnd = Math.max(mergedEnd, blockedSpace.v2);
            }
        }
        blockedSpacesOfRow.removeAll(blockedSpacesToRemove);
        blockedSpacesOfRow.add(Tuple.tuple(mergedStart, mergedEnd));
        blockedSpaceByRow.put(row, blockedSpacesOfRow);
    }

}
