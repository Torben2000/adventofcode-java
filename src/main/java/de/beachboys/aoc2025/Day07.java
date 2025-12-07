package de.beachboys.aoc2025;

import de.beachboys.Day;
import de.beachboys.Direction;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day07 extends Day {

    public Object part1(List<String> input) {
        return getNumSplitPositionsAndNumPaths(input).v1;
    }

    public Object part2(List<String> input) {
        return getNumSplitPositionsAndNumPaths(input).v2;
    }

    private static Tuple2<Long, Long> getNumSplitPositionsAndNumPaths(List<String> input) {
        long numPaths = 0;
        long numSplitPositions = 0;

        Map<Tuple2<Integer, Integer>, String> map = Util.buildImageMap(input);
        Tuple2<Integer, Integer> start = map.entrySet().stream().filter(e -> "S".equals(e.getValue())).map(Map.Entry::getKey).findFirst().orElseThrow();

        Map<Tuple2<Integer, Integer>, Long> currentPositionsWithNumPaths = new HashMap<>();
        currentPositionsWithNumPaths.put(start, 1L);

        while (!currentPositionsWithNumPaths.isEmpty()) {
            Map<Tuple2<Integer, Integer>, Long> newPositionsWithNumPaths = new HashMap<>();
            for (Tuple2<Integer, Integer> pos : currentPositionsWithNumPaths.keySet()) {
                long numPathsForPos = currentPositionsWithNumPaths.get(pos);
                if (map.containsKey(pos)) {
                    Tuple2<Integer, Integer> down = Direction.SOUTH.move(pos, 1);
                    if ("^".equals(map.get(pos))) {
                        numSplitPositions++;
                        Tuple2<Integer, Integer> leftAndDown = Direction.WEST.move(down, 1);
                        newPositionsWithNumPaths.put(leftAndDown, newPositionsWithNumPaths.getOrDefault(leftAndDown, 0L) + numPathsForPos);
                        Tuple2<Integer, Integer> rightAndDown = Direction.EAST.move(down, 1);
                        newPositionsWithNumPaths.put(rightAndDown, newPositionsWithNumPaths.getOrDefault(rightAndDown, 0L) + numPathsForPos);
                    } else {
                        newPositionsWithNumPaths.put(down, newPositionsWithNumPaths.getOrDefault(down, 0L) + numPathsForPos);
                    }
                } else {
                    numPaths += numPathsForPos;
                }
            }
            currentPositionsWithNumPaths = newPositionsWithNumPaths;
        }
        return Tuple.tuple(numSplitPositions, numPaths);
    }

}
