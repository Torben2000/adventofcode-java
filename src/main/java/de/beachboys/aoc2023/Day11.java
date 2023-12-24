package de.beachboys.aoc2023;

import de.beachboys.Day;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple2;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Day11 extends Day {

    public Object part1(List<String> input) {
        return runLogic(input, 2);
    }

    public Object part2(List<String> input) {
        long emptyRowFactor = Util.getLongValueFromUser("Factor for empty rows", 1000000, io);
        return runLogic(input, emptyRowFactor);
    }

    private static long runLogic(List<String> input, long emptyRowFactor) {
        long result = 0;

        Set<Tuple2<Integer, Integer>> galaxyMap = Util.buildConwaySet(input, "#");
        int minX = galaxyMap.stream().map(Tuple2::v1).mapToInt(Integer::intValue).min().orElseThrow();
        int maxX = galaxyMap.stream().map(Tuple2::v1).mapToInt(Integer::intValue).max().orElseThrow();
        int minY = galaxyMap.stream().map(Tuple2::v2).mapToInt(Integer::intValue).min().orElseThrow();
        int maxY = galaxyMap.stream().map(Tuple2::v2).mapToInt(Integer::intValue).max().orElseThrow();
        List<Integer> emptyColumns = new ArrayList<>();
        for (int i = minX + 1; i < maxX ; i++) {
            int finalI = i;
            long count = galaxyMap.stream().filter(p -> p.v1 == finalI).count();
            if (count == 0) {
                emptyColumns.add(i);
            }
        }
        List<Integer> emptyRows = new ArrayList<>();
        for (int i = minY; i < maxY ; i++) {
            int finalI = i;
            long count = galaxyMap.stream().filter(p -> p.v2 == finalI).count();
            if (count == 0) {
                emptyRows.add(i);
            }
        }

        ArrayList<Tuple2<Integer, Integer>> galaxyMapAsList = new ArrayList<>(galaxyMap);

        for (int i = 0; i < galaxyMapAsList.size(); i++) {
            Tuple2<Integer, Integer> pos1 = galaxyMapAsList.get(i);
            for (int j = i + 1; j < galaxyMapAsList.size(); j++) {
                Tuple2<Integer, Integer> pos2 = galaxyMapAsList.get(j);
                int emptyColumnsBetweenPositions1 = (int) emptyColumns.stream().filter(x -> x < pos1.v1 && x > pos2.v1).count();
                int emptyColumnsBetweenPositions2 = (int) emptyColumns.stream().filter(x -> x < pos2.v1 && x > pos1.v1).count();
                int emptyRowsBetweenPositions1 = (int) emptyRows.stream().filter(k -> k < pos1.v2 && k > pos2.v2).count();
                int emptyRowsBetweenPositions2 = (int) emptyRows.stream().filter(k -> k < pos2.v2 && k > pos1.v2).count();
                result += Util.getManhattanDistance(pos1, pos2)
                        + (emptyColumnsBetweenPositions1 + emptyColumnsBetweenPositions2 + emptyRowsBetweenPositions1 + emptyRowsBetweenPositions2)
                        * (emptyRowFactor - 1);
            }
        }

        return result;
    }

}
