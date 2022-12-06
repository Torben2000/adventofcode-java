package de.beachboys.aoc2022;

import de.beachboys.Day;

import java.util.*;
import java.util.stream.Collectors;

public class Day06 extends Day {

    public Object part1(List<String> input) {
        return runLogic(input, 4);
    }

    public Object part2(List<String> input) {
        return runLogic(input, 14);
    }

    private static Object runLogic(List<String> input, int markerLength) {
        String datastream = input.get(0);
        for (int i = 0; i < datastream.length() - markerLength; i++) {
            String possibleMarker = datastream.substring(i, i + markerLength);
            Set<Integer> set = possibleMarker.chars().boxed().collect(Collectors.toSet());
            if (set.size() == markerLength) {
                return i + markerLength;
            }
        }
        return "not found";
    }

}
