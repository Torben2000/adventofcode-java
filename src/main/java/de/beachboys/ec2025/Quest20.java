package de.beachboys.ec2025;

import de.beachboys.Quest;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.*;

public class Quest20 extends Quest {

    @Override
    public Object part1(List<String> input) {
        Map<Tuple2<Integer, Integer>, String> map = Util.buildImageMap(input);

        long result = 0;
        for (int x = 0; x < input.getFirst().length(); x++) {
            for (int y = 0; y < input.size(); y++) {
                Tuple2<Integer, Integer> pos = Tuple.tuple(x, y);
                if (isTrampoline(map, pos)) {
                    if (isTrampoline(map, Tuple.tuple(x + 1, y))) {
                        result++;
                    }
                    if (isConnectedToFieldBelow(x, y)) {
                        if (isTrampoline(map, Tuple.tuple(x, y + 1))) {
                            result++;
                        }
                    }
                }
            }
        }
        return result;
    }

    @Override
    public Object part2(List<String> input) {
        Map<Tuple2<Integer, Integer>, String> map = Util.buildImageMap(input);
        Map<Tuple2<Integer, Integer>, Set<Tuple2<Integer, Integer>>> trampolineConnections = buildTrampolineConnectionMapPart2(input, map);

        Tuple2<Integer, Integer> start = map.entrySet().stream().filter(e -> "S".equals(e.getValue())).map(Map.Entry::getKey).findFirst().orElseThrow();
        Tuple2<Integer, Integer> end = map.entrySet().stream().filter(e -> "E".equals(e.getValue())).map(Map.Entry::getKey).findFirst().orElseThrow();

        Set<Tuple2<Integer, Integer>> seen = new HashSet<>();
        Map<Integer, Set<Tuple2<Integer, Integer>>> queue = new HashMap<>();
        queue.put(0, Set.of(start));

        for (int numJumps = 0; numJumps < Integer.MAX_VALUE; numJumps++) {
            Set<Tuple2<Integer, Integer>> positions = queue.get(numJumps);
            if (positions != null) {
                for (Tuple2<Integer, Integer> pos : positions) {
                    if (end.equals(pos)) {
                        return numJumps;
                    }
                    seen.add(pos);
                    for (Tuple2<Integer, Integer> newPos : trampolineConnections.get(pos)) {
                        if (!seen.contains(newPos)) {
                            Set<Tuple2<Integer, Integer>> newPositions = queue.getOrDefault(numJumps + 1, new HashSet<>());
                            newPositions.add(newPos);
                            queue.put(numJumps + 1, newPositions);
                        }
                    }
                }
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Object part3(List<String> input) {
        List<Map<Tuple2<Integer, Integer>, String>> maps = new ArrayList<>();
        maps.add(Util.buildImageMap(input));
        maps.add(turnMap120Degrees(maps.getLast(), input.getFirst().length(), input.size()));
        maps.add(turnMap120Degrees(maps.getLast(), input.getFirst().length(), input.size()));

        Map<Tuple2<Integer, Tuple2<Integer, Integer>>, Set<Tuple2<Integer, Tuple2<Integer, Integer>>>> trampolineConnections = buildTrampolineConnectionMapPart3(input, maps);

        Tuple2<Integer, Integer> start = maps.getFirst().entrySet().stream().filter(e -> "S".equals(e.getValue())).map(Map.Entry::getKey).findFirst().orElseThrow();
        Set<Tuple2<Integer, Tuple2<Integer, Integer>>> end = new HashSet<>();
        for (int i = 0; i < maps.size(); i++) {
            end.add(Tuple.tuple(i, maps.get(i).entrySet().stream().filter(e -> "E".equals(e.getValue())).map(Map.Entry::getKey).findFirst().orElseThrow()));
        }

        Set<Tuple2<Integer, Tuple2<Integer, Integer>>> seen = new HashSet<>();
        Map<Integer, Set<Tuple2<Integer, Tuple2<Integer, Integer>>>> queue = new HashMap<>();
        queue.put(0, Set.of(Tuple.tuple(0, start)));

        for (int numJumps = 0; numJumps < Integer.MAX_VALUE; numJumps++) {
            Set<Tuple2<Integer, Tuple2<Integer, Integer>>> positions = queue.get(numJumps);
            if (positions != null) {
                for (Tuple2<Integer, Tuple2<Integer, Integer>> pos : positions) {
                    if (end.contains(pos)) {
                        return numJumps;
                    }
                    seen.add(pos);
                    if (trampolineConnections.containsKey(pos)) {
                        for (Tuple2<Integer, Tuple2<Integer, Integer>> newPos : trampolineConnections.get(pos)) {
                            if (!seen.contains(newPos)) {
                                Set<Tuple2<Integer, Tuple2<Integer, Integer>>> newPositions = queue.getOrDefault(numJumps + 1, new HashSet<>());
                                newPositions.add(newPos);
                                queue.put(numJumps + 1, newPositions);
                            }
                        }
                    }
                }
            }
        }
        throw new IllegalArgumentException();
    }

    private static Map<Tuple2<Integer, Integer>, Set<Tuple2<Integer, Integer>>> buildTrampolineConnectionMapPart2(List<String> input, Map<Tuple2<Integer, Integer>, String> map) {
        Map<Tuple2<Integer, Integer>, Set<Tuple2<Integer, Integer>>> trampolineConnections = new HashMap<>();
        for (int x = 0; x < input.getFirst().length(); x++) {
            for (int y = 0; y < input.size(); y++) {
                Tuple2<Integer, Integer> pos = Tuple.tuple(x, y);
                if (isTrampoline(map, pos)) {
                    Tuple2<Integer, Integer> right = Tuple.tuple(x + 1, y);
                    if (isTrampoline(map, right)) {
                        addTrampolineConnection(trampolineConnections, pos, right);
                        addTrampolineConnection(trampolineConnections, right, pos);
                    }
                    if (isConnectedToFieldBelow(x, y)) {
                        Tuple2<Integer, Integer> down = Tuple.tuple(x, y + 1);
                        if (isTrampoline(map, down)) {
                            addTrampolineConnection(trampolineConnections, pos, down);
                            addTrampolineConnection(trampolineConnections, down, pos);
                        }
                    }
                }
            }
        }
        return trampolineConnections;
    }

    private static Map<Tuple2<Integer, Tuple2<Integer, Integer>>, Set<Tuple2<Integer, Tuple2<Integer, Integer>>>> buildTrampolineConnectionMapPart3(List<String> input, List<Map<Tuple2<Integer, Integer>, String>> maps) {
        Map<Tuple2<Integer, Tuple2<Integer, Integer>>, Set<Tuple2<Integer, Tuple2<Integer, Integer>>>> trampolineConnections = new HashMap<>();
        for (int i = 0; i < maps.size(); i++) {
            int nextMapIndex = (i+1)% maps.size();
            int previousMapIndex = (i+2)% maps.size();
            Map<Tuple2<Integer, Integer>, String> map = maps.get(i);
            Map<Tuple2<Integer, Integer>, String> nextMap = maps.get(nextMapIndex);
            Map<Tuple2<Integer, Integer>, String> previousMap = maps.get(previousMapIndex);

            for (int x = 0; x < input.getFirst().length(); x++) {
                for (int y = 0; y < input.size(); y++) {
                    Tuple2<Integer, Integer> pos = Tuple.tuple(x, y);
                    if (isTrampoline(map, pos)) {
                        if (isTrampoline(nextMap, pos)) {
                            addTrampolineConnection(trampolineConnections, Tuple.tuple(i, pos), Tuple.tuple(nextMapIndex, pos));
                        }
                        Tuple2<Integer, Integer> right = Tuple.tuple(x + 1, y);
                        if (isTrampoline(nextMap, right)) {
                            addTrampolineConnection(trampolineConnections, Tuple.tuple(i, pos), Tuple.tuple(nextMapIndex, right));
                        }
                        if (isTrampoline(previousMap, right)) {
                            addTrampolineConnection(trampolineConnections, Tuple.tuple(previousMapIndex, right), Tuple.tuple(i, pos));
                        }
                        if (isConnectedToFieldBelow(x, y)) {
                            Tuple2<Integer, Integer> down = Tuple.tuple(x, y + 1);
                            if (isTrampoline(nextMap, down)) {
                                addTrampolineConnection(trampolineConnections, Tuple.tuple(i, pos), Tuple.tuple(nextMapIndex, down));
                            }
                            if (isTrampoline(previousMap, down)) {
                                addTrampolineConnection(trampolineConnections, Tuple.tuple(previousMapIndex, down), Tuple.tuple(i, pos));
                            }
                        }
                    }
                }
            }
        }
        return trampolineConnections;
    }

    private Map<Tuple2<Integer, Integer>, String> turnMap120Degrees(Map<Tuple2<Integer, Integer>, String> map, int width, int height) {
        Map<Tuple2<Integer, Integer>, String> newMap = new HashMap<>();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                newMap.put(Tuple.tuple(x, y), ".");
            }
        }

        int newX = width - 1;
        int newY = 0;
        int nextX = width - 3;
        boolean moveLeft = true;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (map.containsKey(Tuple.tuple(x, y))) {
                    if (newX >= 0) {
                        newMap.put(Tuple.tuple(newX, newY), map.get(Tuple.tuple(x, y)));
                    }
                    if (moveLeft) {
                        newX--;
                    } else {
                        newY++;
                        if (newY >= height) {
                            newY = 0;
                            newX = nextX;
                            nextX -= 2;
                        }
                    }
                    moveLeft = !moveLeft;
                }
            }
        }
        return newMap;
    }

    private static <T> void addTrampolineConnection(Map<T, Set<T>> trampolineConnectionMap, T source, T target) {
        Set<T> targetSet = trampolineConnectionMap.getOrDefault(source, new HashSet<>());
        targetSet.add(target);
        trampolineConnectionMap.put(source, targetSet);
    }

    private static boolean isTrampoline(Map<Tuple2<Integer, Integer>, String> map, Tuple2<Integer, Integer> pos) {
        return "T".equals(map.get(pos)) || "S".equals(map.get(pos)) || "E".equals(map.get(pos));
    }

    private static boolean isConnectedToFieldBelow(int x, int y) {
        return (x + y) % 2 == 1;
    }
}
