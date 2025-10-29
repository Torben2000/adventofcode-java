package de.beachboys.ec2024;

import de.beachboys.Direction;
import de.beachboys.Quest;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.*;
import java.util.stream.Collectors;

public class Quest18 extends Quest {

    private Map<Tuple2<Integer, Integer>, String> map;
    private Set<Tuple2<Integer, Integer>> palmTrees;

    @Override
    public Object part1(List<String> input) {
        fillMapAndPalmTrees(input);
        Set<Tuple2<Integer, Integer>> startingPositions = Set.of(Tuple.tuple(0, 1));
        return getDurationsToReachGoals(startingPositions, palmTrees).values().stream().mapToInt(Integer::intValue).max().orElseThrow();

    }

    @Override
    public Object part2(List<String> input) {
        fillMapAndPalmTrees(input);
        Set<Tuple2<Integer, Integer>> startingPositions = Set.of(Tuple.tuple(0, 1), Tuple.tuple(input.getFirst().length() - 1, input.size() - 2));
        return getDurationsToReachGoals(startingPositions, palmTrees).values().stream().mapToInt(Integer::intValue).max().orElseThrow();
    }

    @Override
    public Object part3(List<String> input) {
        fillMapAndPalmTrees(input);
        Set<Tuple2<Integer, Integer>> wellLocations = map.entrySet().stream().filter(e -> ".".equals(e.getValue())).map(Map.Entry::getKey).collect(Collectors.toSet());

        Map<Tuple2<Integer, Integer>, Integer> sumsPerWellLocation = new HashMap<>();
        for (Tuple2<Integer, Integer> palmTree : palmTrees) {
            Map<Tuple2<Integer, Integer>, Integer> durationsToReachWellLocations = getDurationsToReachGoals(Set.of(palmTree), wellLocations);
            for (Tuple2<Integer, Integer> wellLocation : durationsToReachWellLocations.keySet()) {
                sumsPerWellLocation.put(wellLocation, sumsPerWellLocation.getOrDefault(wellLocation, 0) + durationsToReachWellLocations.get(wellLocation));
            }
        }

        return sumsPerWellLocation.values().stream().mapToInt(Integer::intValue).min().orElseThrow();
    }

    private void fillMapAndPalmTrees(List<String> input) {
        map = Util.buildImageMap(input);
        palmTrees = map.entrySet().stream().filter(e -> "P".equals(e.getValue())).map(Map.Entry::getKey).collect(Collectors.toSet());
    }

    private Map<Tuple2<Integer, Integer>, Integer> getDurationsToReachGoals(Set<Tuple2<Integer, Integer>> startingPositions, Set<Tuple2<Integer, Integer>> goals) {
        Set<Tuple2<Integer, Integer>> seenGoals = new HashSet<>();
        Set<Tuple2<Integer, Integer>> seen = new HashSet<>();
        Map<Tuple2<Integer, Integer>, Integer> returnMap = new HashMap<>();
        Set<Tuple2<Integer, Integer>> currentPositions = startingPositions;
        for (int minute = 0; minute < Integer.MAX_VALUE; minute++) {
            Set<Tuple2<Integer, Integer>> newCurrentPositions = new HashSet<>();
            for (Tuple2<Integer, Integer> pos : currentPositions) {
                if (goals.contains(pos) && !seenGoals.contains(pos)) {
                    seenGoals.add(pos);
                    returnMap.put(pos, minute);
                    if (seenGoals.size() == goals.size()) {
                        return returnMap;
                    }
                }
                seen.add(pos);
                for (Direction dir : Direction.values()) {
                    Tuple2<Integer, Integer> newPos = dir.move(pos, 1);
                    if (map.containsKey(newPos) && !seen.contains(newPos) && !"#".equals(map.get(newPos))) {
                        newCurrentPositions.add(newPos);
                    }
                }
            }
            currentPositions = newCurrentPositions;
        }
        throw new IllegalArgumentException();
    }
}
