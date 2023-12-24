package de.beachboys.aoc2023;

import de.beachboys.Day;
import de.beachboys.Direction;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;
import org.jooq.lambda.tuple.Tuple3;

import java.util.*;

public class Day17 extends Day {

    private final Map<Tuple2<Integer, Integer>, Integer> map = new HashMap<>();
    private Tuple2<Integer, Integer> start;
    private Tuple2<Integer, Integer> end;
    private final Map<Integer, Set<Tuple3<Tuple2<Integer, Integer>, Direction, Integer>>> queue = new HashMap<>();
    private final Set<Tuple3<Tuple2<Integer, Integer>, Direction, Integer>> seenQueueElements = new HashSet<>();

    public Object part1(List<String> input) {
        return runLogic(input, 0, 3);
    }

    public Object part2(List<String> input) {
        return runLogic(input, 4, 10);
    }

    public int runLogic(List<String> input, int minDistanceInOneDirection, int maxDistanceInOneDirection) {
        parseInputAndClearState(input);
        Tuple3<Tuple2<Integer, Integer>, Direction, Integer> startSouth = Tuple.tuple(start, Direction.SOUTH, 0);
        Tuple3<Tuple2<Integer, Integer>, Direction, Integer> startEast = Tuple.tuple(start, Direction.EAST, 0);
        queue.put(0, Set.of(startSouth, startEast));
        for (int currentHeatLoss = 0; currentHeatLoss < Integer.MAX_VALUE; currentHeatLoss++) {
            for (Tuple3<Tuple2<Integer, Integer>, Direction, Integer> queueElement : queue.getOrDefault(currentHeatLoss, Set.of())) {
                if (end.equals(queueElement.v1) && queueElement.v3 >= minDistanceInOneDirection) {
                    return currentHeatLoss;
                }
                if (queueElement.v3 < maxDistanceInOneDirection) {
                    addNewPositionToQueue(currentHeatLoss, queueElement.v1, queueElement.v2, queueElement.v3);
                }
                if (queueElement.v3 >= minDistanceInOneDirection) {
                    addNewPositionToQueue(currentHeatLoss, queueElement.v1, queueElement.v2.turnRight(), 0);
                    addNewPositionToQueue(currentHeatLoss, queueElement.v1, queueElement.v2.turnLeft(), 0);
                }
            }
        }
        return Integer.MAX_VALUE;
    }

    private void addNewPositionToQueue(int heatLossOfOldPosition, Tuple2<Integer, Integer> oldPosition, Direction direction, int oldDistanceInDirection) {
        Tuple2<Integer, Integer> newPosition = direction.move(oldPosition, 1);
        int newHeatLoss = heatLossOfOldPosition + map.getOrDefault(newPosition, 0);
        Tuple3<Tuple2<Integer, Integer>, Direction, Integer> queueElement = Tuple.tuple(newPosition, direction, oldDistanceInDirection + 1);
        if (!seenQueueElements.contains(queueElement) && Util.isInRectangle(newPosition, start, end)) {
            Set<Tuple3<Tuple2<Integer, Integer>, Direction, Integer>> set = queue.getOrDefault(newHeatLoss, new HashSet<>());
            set.add(queueElement);
            queue.put(newHeatLoss, set);
            seenQueueElements.add(queueElement);
        }

    }

    private void parseInputAndClearState(List<String> input) {
        Map<Tuple2<Integer, Integer>, String> mapWithStrings = Util.buildImageMap(input);
        map.clear();
        for (Map.Entry<Tuple2<Integer, Integer>, String> entry : mapWithStrings.entrySet()) {
            map.put(entry.getKey(), Integer.parseInt(entry.getValue()));
        }
        start = Tuple.tuple(0, 0);
        end = Tuple.tuple(input.getFirst().length() - 1, input.size() - 1);
        queue.clear();
        seenQueueElements.clear();
    }

}
