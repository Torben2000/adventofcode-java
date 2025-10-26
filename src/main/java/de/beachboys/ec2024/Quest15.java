package de.beachboys.ec2024;

import de.beachboys.Direction;
import de.beachboys.Quest;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.*;

public class Quest15 extends Quest {

    @Override
    public Object part1(List<String> input) {
        Map<Tuple2<Integer, Integer>, String> map = Util.buildImageMap(input);
        Tuple2<Integer, Integer> start = map.entrySet().stream().filter(e -> 0 == e.getKey().v2 && ".".equals(e.getValue())).map(Map.Entry::getKey).findFirst().orElseThrow();
        return find(map, start, buildHerbSetFromMap(map));
    }


    @Override
    public Object part2(List<String> input) {
        return part1(input);
    }

    @Override
    public Object part3(List<String> input) {
        Map<Tuple2<Integer, Integer>, String> map = Util.buildImageMap(input);
        Tuple2<Integer, Integer> start = map.entrySet().stream().filter(e -> 0 == e.getKey().v2 && ".".equals(e.getValue())).map(Map.Entry::getKey).findFirst().orElseThrow();

        List<Tuple2<Integer, Integer>> connectors = map.entrySet().stream().filter(e -> "K".equals(e.getValue())).map(Map.Entry::getKey).toList().stream().toList();
        if (connectors.size() != 2) {
            throw new IllegalArgumentException();
        }
        Tuple2<Integer, Integer> connectorLeft = connectors.stream().min(Comparator.comparing(p -> p.v1)).orElseThrow();
        Tuple2<Integer, Integer> connectorRight = connectors.stream().max(Comparator.comparing(p -> p.v1)).orElseThrow();

        Map<Tuple2<Integer, Integer>, String> mapLeft = new HashMap<>();
        map.entrySet().stream().filter(e -> e.getKey().v1 <= connectorLeft.v1).forEach(e -> mapLeft.put(e.getKey(), e.getValue()));
        Map<Tuple2<Integer, Integer>, String> mapMiddle = new HashMap<>();
        map.entrySet().stream().filter(e -> e.getKey().v1 >= connectorLeft.v1 && e.getKey().v1 <= connectorRight.v1).forEach(e -> mapMiddle.put(e.getKey(), e.getValue()));
        Map<Tuple2<Integer, Integer>, String> mapRight = new HashMap<>();
        map.entrySet().stream().filter(e -> e.getKey().v1 >= connectorRight.v1).forEach(e -> mapRight.put(e.getKey(), e.getValue()));
        if (mapMiddle.containsValue("Z")) {
            throw new IllegalArgumentException();
        }
        mapMiddle.put(connectorRight, "Z");

        return find(mapMiddle, start, buildHerbSetFromMap(mapMiddle)) + find(mapLeft, connectorLeft, buildHerbSetFromMap(mapLeft)) + find(mapRight, connectorRight, buildHerbSetFromMap(mapRight));
    }

    private static int find(Map<Tuple2<Integer, Integer>, String> map, Tuple2<Integer, Integer> startAndEnd, Set<String> allHerbs) {
        int numHerbs = allHerbs.size();
        Set<Tuple2<Tuple2<Integer, Integer>, Set<String>>> seen = new HashSet<>();
        Map<Integer, Set<Tuple2<Tuple2<Integer, Integer>, Set<String>>>> queue = new HashMap<>();
        queue.put(0, Set.of(Tuple.tuple(startAndEnd, Set.of())));

        for (int steps = 0; steps < Integer.MAX_VALUE; steps++) {
            Set<Tuple2<Tuple2<Integer, Integer>, Set<String>>> queueEntries = queue.get(steps);
            if (queueEntries != null) {
                for (Tuple2<Tuple2<Integer, Integer>, Set<String>> queueEntry : queueEntries) {
                    Set<String> herbs = queueEntry.v2;
                    if (startAndEnd.equals(queueEntry.v1) && herbs.size() == numHerbs) {
                        return steps;
                    }
                    seen.add(queueEntry);

                    String mapValue = map.get(queueEntry.v1);
                    if (allHerbs.contains(mapValue) && !herbs.contains(mapValue)) {
                        herbs = new HashSet<>(herbs);
                        herbs.add(mapValue);
                    }
                    for (Direction dir : Direction.values()) {
                        Tuple2<Integer, Integer> newPos = dir.move(queueEntry.v1, 1);
                        Tuple2<Tuple2<Integer, Integer>, Set<String>> newQueueEntry = Tuple.tuple(newPos, herbs);
                        if (!seen.contains(newQueueEntry) && map.containsKey(newPos) && !"#".equals(map.get(newPos)) && !"~".equals(map.get(newPos))) {
                            Set<Tuple2<Tuple2<Integer, Integer>, Set<String>>> newPositions = queue.getOrDefault(steps + 1, new HashSet<>());
                            newPositions.add(newQueueEntry);
                            queue.put(steps + 1, newPositions);
                        }
                    }
                }
            }

        }
        throw new IllegalArgumentException();
    }

    private static Set<String> buildHerbSetFromMap(Map<Tuple2<Integer, Integer>, String> map) {
        HashSet<String> allHerbsRight = new HashSet<>(map.values());
        allHerbsRight.remove(".");
        allHerbsRight.remove("#");
        allHerbsRight.remove("~");
        return allHerbsRight;
    }
}
