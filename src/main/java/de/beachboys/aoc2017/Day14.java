package de.beachboys.aoc2017;

import de.beachboys.Day;
import de.beachboys.Direction;
import de.beachboys.Util;
import org.javatuples.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class Day14 extends Day {

    public Object part1(List<String> input) {
        return buildUsedSquareSet(input).size();
    }

    public Object part2(List<String> input) {
        Set<Pair<Integer, Integer>> usedSquares = buildUsedSquareSet(input);
        int regionCounter = 0;
        while (!usedSquares.isEmpty()) {
            regionCounter++;
            Pair<Integer, Integer> startingPosition = usedSquares.stream().findAny().orElseThrow();
            Deque<Pair<Integer, Integer>> queue = new LinkedList<>();
            usedSquares.remove(startingPosition);
            queue.add(startingPosition);
            while (!queue.isEmpty()) {
                Pair<Integer, Integer> currentPosition = queue.poll();
                for (Direction dir : Direction.values()) {
                    Pair<Integer, Integer> neighborPosition = dir.move(currentPosition, 1);
                    if (usedSquares.remove(neighborPosition)) {
                        queue.add(neighborPosition);
                    }
                }
            }
        }
        return regionCounter;
    }

    private Set<Pair<Integer, Integer>> buildUsedSquareSet(List<String> input) {
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
