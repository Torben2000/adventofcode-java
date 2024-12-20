package de.beachboys.aoc2024;

import de.beachboys.Day;
import de.beachboys.Direction;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple2;

import java.util.*;

public class Day20 extends Day {

    public Object part1(List<String> input) {
        return runLogic(input, 2);
    }

    public Object part2(List<String> input) {
        return runLogic(input, 20);
    }

    private Map<Tuple2<Integer, Integer>, String> map;
    private Tuple2<Integer, Integer> start;
    private Tuple2<Integer, Integer> end;
    private List<Tuple2<Integer, Integer>> track;
    private long runLogic(List<String> input, int defaultValueCheatLength) {
        int minimumToSave = Util.getIntValueFromUser("Minimum to save", 100, io);
        int cheatLength = Util.getIntValueFromUser("Maximum cheat length", defaultValueCheatLength, io);

        parse(input);

        Map<Tuple2<Integer, Integer>, Integer> distanceFromStart = buildDistancesMap(start);
        Map<Tuple2<Integer, Integer>, Integer> distanceFromEnd = buildDistancesMap(end);

        long result = 0;
        int initialScore = distanceFromStart.get(end);
        for (Tuple2<Integer, Integer> cheatStart : track) {
            for (Tuple2<Integer, Integer> cheatEnd : track) {
                int manhattanDistance = Util.getManhattanDistance(cheatStart, cheatEnd);
                if (manhattanDistance <= cheatLength && initialScore - minimumToSave >= distanceFromStart.get(cheatStart) + manhattanDistance + distanceFromEnd.get(cheatEnd)) {
                    result++;
                }
            }
        }
        return result;
    }

    private void parse(List<String> input) {
        map = Util.buildImageMap(input);
        start = map.entrySet().stream().filter(e -> "S".equals(e.getValue())).map(Map.Entry::getKey).findFirst().orElseThrow();
        end = map.entrySet().stream().filter(e -> "E".equals(e.getValue())).map(Map.Entry::getKey).findFirst().orElseThrow();
        track = map.entrySet().stream().filter(e -> !"#".equals(e.getValue())).map(Map.Entry::getKey).toList();
    }


    private Map<Tuple2<Integer, Integer>, Integer> buildDistancesMap(Tuple2<Integer, Integer> start) {
        Map<Tuple2<Integer, Integer>, Integer> distancesMap = new HashMap<>();
        Set<Tuple2<Integer, Integer>> trackElementToAddToMap = new HashSet<>(track);

        Set<Tuple2<Integer, Integer>> history = new HashSet<>();
        history.add(start);
        List<Tuple2<Integer, Integer>> currentTrackElements = List.of(start);
        for (int distance = 0; !trackElementToAddToMap.isEmpty(); distance++) {
            List<Tuple2<Integer, Integer>> nextTrackElements = new LinkedList<>();
            for (Tuple2<Integer, Integer> trackElement : currentTrackElements) {
                if (trackElementToAddToMap.contains(trackElement)) {
                    distancesMap.put(trackElement, distance);
                    trackElementToAddToMap.remove(trackElement);
                }
                for (Direction value : Direction.values()) {
                    Tuple2<Integer, Integer> newPos = value.move(trackElement, 1);
                    if (!history.contains(newPos) && map.containsKey(newPos)) {
                        if (!"#".equals(map.get(newPos))) {
                            nextTrackElements.add(newPos);
                            history.add(newPos);
                        }
                    }
                }
                currentTrackElements = nextTrackElements;
            }
        }
        return distancesMap;
    }

}
