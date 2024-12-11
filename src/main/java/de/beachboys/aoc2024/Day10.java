package de.beachboys.aoc2024;

import de.beachboys.Day;
import de.beachboys.Direction;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple2;

import java.util.*;
import java.util.stream.Collectors;

public class Day10 extends Day {

    private Map<Tuple2<Integer, Integer>, String> map;
    private Set<Tuple2<Integer, Integer>> trailheads;

    public Object part1(List<String> input) {
        parseInput(input);

        long result = 0;
        for (Tuple2<Integer, Integer> trailhead : trailheads) {
            result += getHikingTrails(List.of(trailhead), 0).stream().map(List::getLast).collect(Collectors.toSet()).size();
        }
        return result;
    }

    public Object part2(List<String> input) {
        parseInput(input);

        long result = 0;
        for (Tuple2<Integer, Integer> trailhead : trailheads) {
            result += getHikingTrails(List.of(trailhead), 0).size();
        }
        return result;
    }

    private void parseInput(List<String> input) {
        map = Util.buildImageMap(input);
        trailheads = map.entrySet().stream().filter(e -> "0".equals(e.getValue())).map(Map.Entry::getKey).collect(Collectors.toSet());
    }

    private Set<List<Tuple2<Integer, Integer>>> getHikingTrails(List<Tuple2<Integer, Integer>> currentHikingTrail, int currentHeight) {
        Set<List<Tuple2<Integer, Integer>>> hikingTrails = new HashSet<>();
        if (currentHeight == 9) {
            hikingTrails.add(currentHikingTrail);
        } else {
            int newHeight = currentHeight + 1;
            Tuple2<Integer, Integer> currentPos = currentHikingTrail.getLast();
            for (Direction value : Direction.values()) {
                Tuple2<Integer, Integer> newPos = value.move(currentPos, 1);
                String heightAtNewPos = map.get(newPos);
                if (String.valueOf(newHeight).equals(heightAtNewPos)) {
                    List<Tuple2<Integer, Integer>> newHikingTrail = new ArrayList<>(currentHikingTrail);
                    newHikingTrail.add(newPos);
                    hikingTrails.addAll(getHikingTrails(newHikingTrail, newHeight));
                }
            }
        }
        return hikingTrails;
    }

}
