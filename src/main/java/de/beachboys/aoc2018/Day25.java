package de.beachboys.aoc2018;

import de.beachboys.Day;
import org.javatuples.Quartet;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day25 extends Day {

    public Object part1(List<String> input) {
        List<Quartet<Integer, Integer, Integer, Integer>> points = parseInput(input);
        Map<Quartet<Integer, Integer, Integer, Integer>, List<Quartet<Integer, Integer, Integer, Integer>>> directlyConnectedPoints = getDirectlyConnectedPoints(points);

        int constellations = 0;
        while (!directlyConnectedPoints.isEmpty()) {
            constellations++;
            findConnectedPointsAndRemoveFromMap(directlyConnectedPoints.keySet().stream().findFirst().orElseThrow(), directlyConnectedPoints);
        }

        return constellations;
    }

    private List<Quartet<Integer, Integer, Integer, Integer>> parseInput(List<String> input) {
        Pattern pattern = Pattern.compile("([-0-9]+),([-0-9]+),([-0-9]+),([-0-9]+)");
        List<Quartet<Integer, Integer, Integer, Integer>> points = new ArrayList<>();
        for (String line : input) {
            Matcher m = pattern.matcher(line.trim());
            if (m.matches()) {
                points.add(Quartet.with(Integer.valueOf(m.group(1)), Integer.valueOf(m.group(2)), Integer.valueOf(m.group(3)), Integer.valueOf(m.group(4))));
            } else {
                throw new IllegalArgumentException();
            }
        }
        return points;
    }

    private Map<Quartet<Integer, Integer, Integer, Integer>, List<Quartet<Integer, Integer, Integer, Integer>>> getDirectlyConnectedPoints(List<Quartet<Integer, Integer, Integer, Integer>> points) {
        Map<Quartet<Integer, Integer, Integer, Integer>, List<Quartet<Integer, Integer, Integer, Integer>>> connectedPoints = new HashMap<>();
        for (Quartet<Integer, Integer, Integer, Integer> point1 : points) {
            List<Quartet<Integer, Integer, Integer, Integer>> list = new ArrayList<>();
            connectedPoints.put(point1, list);
            for (Quartet<Integer, Integer, Integer, Integer> point2 : points) {
                if (point1 != point2) {
                    if (getManhattanDistance(point1, point2) <= 3) {
                        list.add(point2);
                    }
                }
            }
        }
        return connectedPoints;
    }

    private int getManhattanDistance(Quartet<Integer, Integer, Integer, Integer> point1, Quartet<Integer, Integer, Integer, Integer> point2) {
        return Math.abs(point1.getValue0() - point2.getValue0()) + Math.abs(point1.getValue1() - point2.getValue1()) + Math.abs(point1.getValue2() - point2.getValue2()) + Math.abs(point1.getValue3() - point2.getValue3());
    }

    private void findConnectedPointsAndRemoveFromMap(Quartet<Integer, Integer, Integer, Integer> point, Map<Quartet<Integer, Integer, Integer, Integer>, List<Quartet<Integer, Integer, Integer, Integer>>> directlyConnectedPoints) {
        List<Quartet<Integer, Integer, Integer, Integer>> directlyToCurrentPointConnectedPoints = directlyConnectedPoints.remove(point);
        if (directlyToCurrentPointConnectedPoints != null) {
            for (Quartet<Integer, Integer, Integer, Integer> otherPoint : directlyToCurrentPointConnectedPoints) {
                findConnectedPointsAndRemoveFromMap(otherPoint, directlyConnectedPoints);
            }
        }
    }

    public Object part2(List<String> input) {
        return "There is no puzzle! :-)";
    }

}
