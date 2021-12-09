package de.beachboys.aoc2021;

import de.beachboys.Day;
import de.beachboys.Direction;
import de.beachboys.Util;
import org.javatuples.Pair;

import java.util.*;

public class Day09 extends Day {

    private final Map<Pair<Integer, Integer>, Integer> map = new HashMap<>();

    public Object part1(List<String> input) {
        Set<Pair<Integer, Integer>> lowPoints = buildMapAndReturnLowPoints(input);
        return lowPoints.stream().mapToInt(p -> map.getOrDefault(p, Integer.MAX_VALUE) + 1).sum();
    }

    public Object part2(List<String> input) {
        Set<Pair<Integer, Integer>> lowPoints = buildMapAndReturnLowPoints(input);

        List<Integer> basins = new ArrayList<>();
        for (Pair<Integer, Integer> lowPoint : lowPoints) {
            Set<Pair<Integer, Integer>> basin = getBasinFromLowPoint(lowPoint);
            basins.add(basin.size());
        }

        basins.sort(Comparator.reverseOrder());
        return basins.get(0) * basins.get(1) * basins.get(2);
    }

    private Set<Pair<Integer, Integer>> buildMapAndReturnLowPoints(List<String> input) {
        map.clear();
        Map<Pair<Integer, Integer>, String> tempMap = Util.buildImageMap(input);
        tempMap.forEach((k, v) -> map.put(k, Integer.valueOf(v)));

        int maxX = input.get(0).length();
        int maxY = input.size();
        Set<Pair<Integer, Integer>> lowPoints = new HashSet<>();

        for (int i = 0; i < maxX; i++) {
            for (int j = 0; j < maxY; j++) {
                Pair<Integer, Integer> position = Pair.with(i, j);
                int value = map.get(position);
                boolean isLowPoint = true;
                for (Pair<Integer, Integer> neighbor : Direction.getDirectNeighbors(position)) {
                    int neighborValue = map.getOrDefault(neighbor, Integer.MAX_VALUE);
                    if (neighborValue <= value) {
                        isLowPoint = false;
                    }
                }
                if (isLowPoint) {
                    lowPoints.add(position);
                }
            }
        }
        return lowPoints;
    }

    private Set<Pair<Integer, Integer>> getBasinFromLowPoint(Pair<Integer, Integer> lowPoint) {
        Set<Pair<Integer, Integer>> basin = new HashSet<>();
        basin.add(lowPoint);
        Set<Pair<Integer, Integer>> positionsToCheck = new HashSet<>(Direction.getDirectNeighbors(lowPoint));
        Set<Pair<Integer, Integer>> checkedPositions = new HashSet<>(basin);

        while (!positionsToCheck.isEmpty()) {
            Set<Pair<Integer, Integer>> newPositionsToCheck = new HashSet<>();
            for (Pair<Integer, Integer> position : positionsToCheck) {
                int value = map.getOrDefault(position, 9);
                if (value != 9 && !basin.contains(position)) {
                    basin.add(position);
                    newPositionsToCheck.addAll(Direction.getDirectNeighbors(position));
                }
            }

            checkedPositions.addAll(positionsToCheck);
            newPositionsToCheck.removeAll(checkedPositions);
            positionsToCheck.clear();
            positionsToCheck.addAll(newPositionsToCheck);
            newPositionsToCheck.clear();
        }

        return basin;
    }

}
