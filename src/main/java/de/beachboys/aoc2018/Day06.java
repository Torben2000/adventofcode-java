package de.beachboys.aoc2018;

import de.beachboys.Day;
import de.beachboys.Direction;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day06 extends Day {

    private static final int AREA_BELONGING_TO_NO_ONE = -1;

    private Tuple2<Integer, Integer> upperLeft;
    private Tuple2<Integer, Integer> lowerRight;

    public Object part1(List<String> input) {
        Map<Tuple2<Integer, Integer>, Integer> closestPositions = new HashMap<>();
        Map<Tuple2<Integer, Integer>, Integer> newClosestPositions = getInitialPositions(input);
        setAreaBorders(newClosestPositions.keySet());

        Set<Integer> infiniteAreas = new HashSet<>();
        infiniteAreas.add(AREA_BELONGING_TO_NO_ONE);

        while (!newClosestPositions.isEmpty()) {
            closestPositions.putAll(newClosestPositions);
            Map<Tuple2<Integer, Integer>, Integer> nextNewClosestPositions = new HashMap<>();
            for (Map.Entry<Tuple2<Integer, Integer>, Integer> entry : newClosestPositions.entrySet()) {
                if (AREA_BELONGING_TO_NO_ONE != entry.getValue()) {
                    for (Tuple2<Integer, Integer> adjacentPosition : getAdjacentPositions(entry.getKey())) {
                        if (!closestPositions.containsKey(adjacentPosition)) {
                            if (isOutsideOfAreas(adjacentPosition)) {
                                infiniteAreas.add(entry.getValue());
                            } else if (nextNewClosestPositions.containsKey(adjacentPosition) && !nextNewClosestPositions.get(adjacentPosition).equals(entry.getValue())) {
                                nextNewClosestPositions.put(adjacentPosition, AREA_BELONGING_TO_NO_ONE);
                            } else {
                                nextNewClosestPositions.put(adjacentPosition, entry.getValue());
                            }
                        }
                    }
                }
            }
            newClosestPositions = nextNewClosestPositions;
        }

        return countNonInfiniteAreas(closestPositions, infiniteAreas).values().stream().max(Integer::compareTo).orElseThrow();
    }

    public Object part2(List<String> input) {
        int maxDistance = Util.getIntValueFromUser("Max distance", 10000, io);
        Set<Tuple2<Integer, Integer>> positions = getInitialPositions(input).keySet();
        setAreaBorders(positions);

        int counter = 0;
        for (int i = upperLeft.v1; i <= lowerRight.v1; i++) {
            for (int j = upperLeft.v2; j <= lowerRight.v2; j++) {
                if (getSumOfDistances(positions, i, j) < maxDistance) {
                    counter++;
                    if (isOnBorder(i, j)) {
                        io.logInfo("Match found on border, make loops wider!");
                    }
                }
            }
        }
        return counter;
    }

    private Map<Integer, Integer> countNonInfiniteAreas(Map<Tuple2<Integer, Integer>, Integer> closestPositions, Set<Integer> infiniteAreas) {
        List<Integer> areaIds = closestPositions.values().stream().filter(Predicate.not(infiniteAreas::contains)).collect(Collectors.toList());
        Map<Integer, Integer> counter = new HashMap<>();
        for (Integer areaId : areaIds) {
            counter.merge(areaId, 1, Integer::sum);
        }
        return counter;
    }

    private void setAreaBorders(Set<Tuple2<Integer, Integer>> positions) {
        int maxX = positions.stream().map(Tuple2::v1).max(Integer::compareTo).orElseThrow();
        int maxY = positions.stream().map(Tuple2::v2).max(Integer::compareTo).orElseThrow();
        int minX = positions.stream().map(Tuple2::v1).min(Integer::compareTo).orElseThrow();
        int minY = positions.stream().map(Tuple2::v2).min(Integer::compareTo).orElseThrow();
        upperLeft = Tuple.tuple(minX, minY);
        lowerRight = Tuple.tuple(maxX, maxY);
    }

    private boolean isOutsideOfAreas(Tuple2<Integer, Integer> position) {
        return position.v1 < upperLeft.v1
                || position.v1 > lowerRight.v1
                || position.v2 < upperLeft.v2
                || position.v2 > lowerRight.v2;
    }

    private Map<Tuple2<Integer, Integer>, Integer> getInitialPositions(List<String> input) {
        Map<Tuple2<Integer, Integer>, Integer> newClosestPositions = new HashMap<>();
        Pattern p = Pattern.compile("([0-9]+), ([0-9]+)");
        for (int i = 0; i < input.size(); i++) {
            Matcher m = p.matcher(input.get(i));
            if (m.matches()) {
                newClosestPositions.put(Tuple.tuple(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2))), i);
            }
        }
        return newClosestPositions;
    }

    private List<Tuple2<Integer, Integer>> getAdjacentPositions(Tuple2<Integer, Integer> position) {
        List<Tuple2<Integer, Integer>> list = new ArrayList<>();
        for (Direction dir : Direction.values()) {
            list.add(dir.move(position, 1));
        }
        return list;
    }

    private boolean isOnBorder(int x, int y) {
        return x == upperLeft.v1 || x == lowerRight.v1 || y == upperLeft.v2 || y == lowerRight.v2;
    }

    private int getSumOfDistances(Set<Tuple2<Integer, Integer>> positions, int x, int y) {
        int distance = 0;
        for (Tuple2<Integer, Integer> position : positions) {
            distance += Math.abs(position.v1 - x) + Math.abs(position.v2 - y);
        }
        return distance;
    }

}
