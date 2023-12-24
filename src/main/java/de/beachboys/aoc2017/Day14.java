package de.beachboys.aoc2017;

import de.beachboys.Day;
import de.beachboys.Direction;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple2;

import java.util.*;
import java.util.stream.Collectors;

public class Day14 extends Day {

    public Object part1(List<String> input) {
        return buildUsedSquareSet(input).size();
    }

    public Object part2(List<String> input) {
        Set<Tuple2<Integer, Integer>> usedSquares = buildUsedSquareSet(input);
        int regionCounter = 0;
        while (!usedSquares.isEmpty()) {
            regionCounter++;
            Tuple2<Integer, Integer> startingPosition = usedSquares.stream().findAny().orElseThrow();
            Deque<Tuple2<Integer, Integer>> queue = new LinkedList<>();
            usedSquares.remove(startingPosition);
            queue.add(startingPosition);
            while (!queue.isEmpty()) {
                Tuple2<Integer, Integer> currentPosition = queue.poll();
                for (Direction dir : Direction.values()) {
                    Tuple2<Integer, Integer> neighborPosition = dir.move(currentPosition, 1);
                    if (usedSquares.remove(neighborPosition)) {
                        queue.add(neighborPosition);
                    }
                }
            }
        }
        return regionCounter;
    }

    private Set<Tuple2<Integer, Integer>> buildUsedSquareSet(List<String> input) {
        KnotHash knotHash = new KnotHash();
        String prefix = input.get(0);
        StringBuilder diskImage = new StringBuilder();
        for (int i = 0; i < 128; i++) {
            knotHash.reset();
            String currentHash = knotHash.knotHashToBinary(prefix + "-" + i);
            diskImage.append(currentHash).append("\n");
        }
        return Util.buildImageMap(diskImage.toString()).entrySet().stream().filter(entry -> "1".equals(entry.getValue())).map(Map.Entry::getKey).collect(Collectors.toSet());
    }

}
