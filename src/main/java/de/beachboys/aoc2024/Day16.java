package de.beachboys.aoc2024;

import de.beachboys.Day;
import de.beachboys.Direction;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;
import org.jooq.lambda.tuple.Tuple3;

import java.util.*;

public class Day16 extends Day {

    private final Map<Integer, List<Tuple3<Tuple2<Integer, Integer>, Direction, Set<Tuple2<Integer, Integer>>>>> queue = new HashMap<>();
    private final Set<Tuple2<Tuple2<Integer, Integer>, Direction>> history = new HashSet<>();

    public Object part1(List<String> input) {
        return runLogic(input, false);
    }

    public Object part2(List<String> input) {
        return runLogic(input, true);
    }

    private int runLogic(List<String> input, boolean countSeats) {
        Map<Tuple2<Integer, Integer>, String> map = Util.buildImageMap(input);

        Tuple2<Integer, Integer> start = map.entrySet().stream().filter(e -> "S".equals(e.getValue())).map(Map.Entry::getKey).findFirst().orElseThrow();
        Tuple2<Integer, Integer> end = map.entrySet().stream().filter(e -> "E".equals(e.getValue())).map(Map.Entry::getKey).findFirst().orElseThrow();

        Set<Tuple2<Integer, Integer>> seats = new HashSet<>();

        queue.clear();
        queue.put(0, List.of(Tuple.tuple(start, Direction.EAST, countSeats ? Set.of(start) : null)));
        history.clear();

        for (int score = 0; score < Integer.MAX_VALUE; score++) {
            List<Tuple3<Tuple2<Integer, Integer>, Direction, Set<Tuple2<Integer, Integer>>>> queueEntriesForScore = queue.get(score);
            if (queueEntriesForScore != null) {
                boolean endReached = false;
                for (Tuple3<Tuple2<Integer, Integer>, Direction, Set<Tuple2<Integer, Integer>>> entry : queueEntriesForScore) {
                    if (end.equals(entry.v1)) {
                        if (countSeats) {
                            endReached = true;
                            seats.addAll(entry.v3);
                        } else {
                            return score;
                        }
                    } else {
                        history.add(Tuple.tuple(entry.v1, entry.v2));
                        Tuple2<Integer, Integer> newPos = entry.v2.move(entry.v1, 1);
                        if (!"#".equals(map.get(newPos))) {
                            addToQueue(score + 1, newPos, entry.v2, entry.v3);
                        }
                        addToQueue(score + 1000, entry.v1, entry.v2.turnLeft(), entry.v3);
                        addToQueue(score + 1000, entry.v1, entry.v2.turnRight(), entry.v3);
                    }
                }
                if (endReached) {
                    return seats.size();
                }
            }
        }
        throw new IllegalArgumentException();
    }

    private void addToQueue(int score, Tuple2<Integer, Integer> newPos, Direction newDir, Set<Tuple2<Integer, Integer>> path) {
        if (!history.contains(Tuple.tuple(newPos, newDir))) {
            if (!queue.containsKey(score)) {
                queue.put(score, new LinkedList<>());
            }
            Set<Tuple2<Integer, Integer>> newPath = null;
            if (path != null) {
                if (path.contains(newPos)) {
                    newPath = path;
                } else {
                    newPath = new HashSet<>(path);
                    newPath.add(newPos);
                }
            }
            queue.get(score).add(Tuple.tuple(newPos, newDir, newPath));
        }
    }

}
