package de.beachboys.aoc2021;

import de.beachboys.Day;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Day25 extends Day {

    public static final String EAST = ">";
    public static final String SOUTH = "v";
    public static final String EMPTY = ".";

    public Object part1(List<String> input) {
        int width = input.getFirst().length();
        int height = input.size();

        Map<Tuple2<Integer, Integer>, String> map = Util.buildImageMap(input);

        Map<Tuple2<Integer, Integer>, String> newMap;
        long counter = 0;
        boolean moved = true;
        while (moved) {
            moved = false;

            newMap = new HashMap<>(map);
            Set<Tuple2<Integer, Integer>> eastPositions = getCucumberPositions(map, EAST);
            for (Tuple2<Integer, Integer> position : eastPositions) {
                Tuple2<Integer, Integer> newPosition = Tuple.tuple((position.v1 + 1) % width, position.v2);
                moved |= moveIfPossible(map, newMap, EAST, position, newPosition);
            }
            map = newMap;

            newMap = new HashMap<>(map);
            Set<Tuple2<Integer, Integer>> southPositions = getCucumberPositions(map, SOUTH);
            for (Tuple2<Integer, Integer> position : southPositions) {
                Tuple2<Integer, Integer> newPosition = Tuple.tuple(position.v1, (position.v2 + 1) % height);
                moved |= moveIfPossible(map, newMap, SOUTH, position, newPosition);
            }
            map = newMap;

            counter++;
        }
        return counter;
    }

    private Set<Tuple2<Integer, Integer>> getCucumberPositions(Map<Tuple2<Integer, Integer>, String> map, String cucumberRepresentation) {
        return map.entrySet().stream().filter(e -> cucumberRepresentation.equals(e.getValue())).map(Map.Entry::getKey).collect(Collectors.toSet());
    }

    private boolean moveIfPossible(Map<Tuple2<Integer, Integer>, String> oldMap, Map<Tuple2<Integer, Integer>, String> newMap, String cucumberRepresentation, Tuple2<Integer, Integer> oldPosition, Tuple2<Integer, Integer> newPosition) {
        if (EMPTY.equals(oldMap.get(newPosition))) {
            newMap.put(oldPosition, EMPTY);
            newMap.put(newPosition, cucumberRepresentation);
            return true;
        }
        return false;
    }

    public Object part2(List<String> input) {
        return "There is no puzzle! :-)";
    }

}
