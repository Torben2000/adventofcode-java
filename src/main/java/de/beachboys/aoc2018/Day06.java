package de.beachboys.aoc2018;

import de.beachboys.Day;
import de.beachboys.Direction;
import de.beachboys.Util;
import org.javatuples.Pair;

import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day06 extends Day {

    private static final int AREA_BELONGING_TO_NO_ONE = -1;

    private Pair<Integer, Integer> upperLeft;
    private Pair<Integer, Integer> lowerRight;

    public Object part1(List<String> input) {
        Map<Pair<Integer, Integer>, Integer> closestPositions = new HashMap<>();
        Map<Pair<Integer, Integer>, Integer> newClosestPositions = getInitialPositions(input);
        setAreaBorders(newClosestPositions.keySet());

        Set<Integer> infiniteAreas = new HashSet<>();
        infiniteAreas.add(AREA_BELONGING_TO_NO_ONE);

        while (!newClosestPositions.isEmpty()) {
            closestPositions.putAll(newClosestPositions);
            Map<Pair<Integer, Integer>, Integer> nextNewClosestPositions = new HashMap<>();
            for (Map.Entry<Pair<Integer, Integer>, Integer> entry : newClosestPositions.entrySet()) {
                if (AREA_BELONGING_TO_NO_ONE != entry.getValue()) {
                    for (Pair<Integer, Integer> adjacentPosition : getAdjacentPositions(entry.getKey())) {
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
        Set<Pair<Integer, Integer>> positions = getInitialPositions(input).keySet();
        setAreaBorders(positions);

        int counter = 0;
        for (int i = upperLeft.getValue0(); i <= lowerRight.getValue0(); i++) {
            for (int j = upperLeft.getValue1(); j <= lowerRight.getValue1(); j++) {
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

    private Map<Integer, Integer> countNonInfiniteAreas(Map<Pair<Integer, Integer>, Integer> closestPositions, Set<Integer> infiniteAreas) {
        List<Integer> areaIds = closestPositions.values().stream().filter(Predicate.not(infiniteAreas::contains)).collect(Collectors.toList());
        Map<Integer, Integer> counter = new HashMap<>();
        for (Integer areaId : areaIds) {
            counter.merge(areaId, 1, Integer::sum);
        }
        return counter;
    }

    private void setAreaBorders(Set<Pair<Integer, Integer>> positions) {
        int maxX = positions.stream().map(Pair::getValue0).max(Integer::compareTo).orElseThrow();
        int maxY = positions.stream().map(Pair::getValue1).max(Integer::compareTo).orElseThrow();
        int minX = positions.stream().map(Pair::getValue0).min(Integer::compareTo).orElseThrow();
        int minY = positions.stream().map(Pair::getValue1).min(Integer::compareTo).orElseThrow();
        upperLeft = Pair.with(minX, minY);
        lowerRight = Pair.with(maxX, maxY);
    }

    private boolean isOutsideOfAreas(Pair<Integer, Integer> position) {
        return position.getValue0() < upperLeft.getValue0()
                || position.getValue0() > lowerRight.getValue0()
                || position.getValue1() < upperLeft.getValue1()
                || position.getValue1() > lowerRight.getValue1();
    }

    private Map<Pair<Integer, Integer>, Integer> getInitialPositions(List<String> input) {
        Map<Pair<Integer, Integer>, Integer> newClosestPositions = new HashMap<>();
        Pattern p = Pattern.compile("([0-9]+), ([0-9]+)");
        for (int i = 0; i < input.size(); i++) {
            Matcher m = p.matcher(input.get(i));
            if (m.matches()) {
                newClosestPositions.put(Pair.with(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2))), i);
            }
        }
        return newClosestPositions;
    }

    private List<Pair<Integer, Integer>> getAdjacentPositions(Pair<Integer, Integer> position) {
        List<Pair<Integer, Integer>> list = new ArrayList<>();
        for (Direction dir : Direction.values()) {
            list.add(dir.move(position, 1));
        }
        return list;
    }

    private boolean isOnBorder(int x, int y) {
        return x == upperLeft.getValue0() || x == lowerRight.getValue0() || y == upperLeft.getValue1() || y == lowerRight.getValue1();
    }

    private int getSumOfDistances(Set<Pair<Integer, Integer>> positions, int x, int y) {
        int distance = 0;
        for (Pair<Integer, Integer> position : positions) {
            distance += Math.abs(position.getValue0() - x) + Math.abs(position.getValue1() - y);
        }
        return distance;
    }

}
