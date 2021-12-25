package de.beachboys.aoc2021;

import de.beachboys.Day;
import de.beachboys.Util;
import org.javatuples.Pair;

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
        int width = input.get(0).length();
        int height = input.size();

        Map<Pair<Integer, Integer>, String> map = Util.buildImageMap(input);

        Map<Pair<Integer, Integer>, String> newMap;
        long counter = 0;
        boolean moved = true;
        while (moved) {
            moved = false;

            newMap = new HashMap<>(map);
            Set<Pair<Integer, Integer>> eastPositions = getCucumberPositions(map, EAST);
            for (Pair<Integer, Integer> position : eastPositions) {
                Pair<Integer, Integer> newPosition = position.setAt0((position.getValue0() + 1) % width);
                moved |= moveIfPossible(map, newMap, EAST, position, newPosition);
            }
            map = newMap;

            newMap = new HashMap<>(map);
            Set<Pair<Integer, Integer>> southPositions = getCucumberPositions(map, SOUTH);
            for (Pair<Integer, Integer> position : southPositions) {
                Pair<Integer, Integer> newPosition = position.setAt1((position.getValue1() + 1) % height);
                moved |= moveIfPossible(map, newMap, SOUTH, position, newPosition);
            }
            map = newMap;

            counter++;
        }
        return counter;
    }

    private Set<Pair<Integer, Integer>> getCucumberPositions(Map<Pair<Integer, Integer>, String> map, String cucumberRepresentation) {
        return map.entrySet().stream().filter(e -> cucumberRepresentation.equals(e.getValue())).map(Map.Entry::getKey).collect(Collectors.toSet());
    }

    private boolean moveIfPossible(Map<Pair<Integer, Integer>, String> oldMap, Map<Pair<Integer, Integer>, String> newMap, String cucumberRepresentation, Pair<Integer, Integer> oldPosition, Pair<Integer, Integer> newPosition) {
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
