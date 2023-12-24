package de.beachboys.aoc2018;

import de.beachboys.Day;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day25 extends Day {

    public Object part1(List<String> input) {
        List<Tuple4<Integer, Integer, Integer, Integer>> points = parseInput(input);
        Map<Tuple4<Integer, Integer, Integer, Integer>, List<Tuple4<Integer, Integer, Integer, Integer>>> directlyConnectedPoints = getDirectlyConnectedPoints(points);

        int constellations = 0;
        while (!directlyConnectedPoints.isEmpty()) {
            constellations++;
            findConnectedPointsAndRemoveFromMap(directlyConnectedPoints.keySet().stream().findFirst().orElseThrow(), directlyConnectedPoints);
        }

        return constellations;
    }

    private List<Tuple4<Integer, Integer, Integer, Integer>> parseInput(List<String> input) {
        Pattern pattern = Pattern.compile("([-0-9]+),([-0-9]+),([-0-9]+),([-0-9]+)");
        List<Tuple4<Integer, Integer, Integer, Integer>> points = new ArrayList<>();
        for (String line : input) {
            Matcher m = pattern.matcher(line.trim());
            if (m.matches()) {
                points.add(Tuple.tuple(Integer.valueOf(m.group(1)), Integer.valueOf(m.group(2)), Integer.valueOf(m.group(3)), Integer.valueOf(m.group(4))));
            } else {
                throw new IllegalArgumentException();
            }
        }
        return points;
    }

    private Map<Tuple4<Integer, Integer, Integer, Integer>, List<Tuple4<Integer, Integer, Integer, Integer>>> getDirectlyConnectedPoints(List<Tuple4<Integer, Integer, Integer, Integer>> points) {
        Map<Tuple4<Integer, Integer, Integer, Integer>, List<Tuple4<Integer, Integer, Integer, Integer>>> connectedPoints = new HashMap<>();
        for (Tuple4<Integer, Integer, Integer, Integer> point1 : points) {
            List<Tuple4<Integer, Integer, Integer, Integer>> list = new ArrayList<>();
            connectedPoints.put(point1, list);
            for (Tuple4<Integer, Integer, Integer, Integer> point2 : points) {
                if (point1 != point2) {
                    if (getManhattanDistance(point1, point2) <= 3) {
                        list.add(point2);
                    }
                }
            }
        }
        return connectedPoints;
    }

    private int getManhattanDistance(Tuple4<Integer, Integer, Integer, Integer> point1, Tuple4<Integer, Integer, Integer, Integer> point2) {
        return Math.abs(point1.v1 - point2.v1) + Math.abs(point1.v2 - point2.v2) + Math.abs(point1.v3 - point2.v3) + Math.abs(point1.v4 - point2.v4);
    }

    private void findConnectedPointsAndRemoveFromMap(Tuple4<Integer, Integer, Integer, Integer> point, Map<Tuple4<Integer, Integer, Integer, Integer>, List<Tuple4<Integer, Integer, Integer, Integer>>> directlyConnectedPoints) {
        List<Tuple4<Integer, Integer, Integer, Integer>> directlyToCurrentPointConnectedPoints = directlyConnectedPoints.remove(point);
        if (directlyToCurrentPointConnectedPoints != null) {
            for (Tuple4<Integer, Integer, Integer, Integer> otherPoint : directlyToCurrentPointConnectedPoints) {
                findConnectedPointsAndRemoveFromMap(otherPoint, directlyConnectedPoints);
            }
        }
    }

    public Object part2(List<String> input) {
        return "There is no puzzle! :-)";
    }

}
