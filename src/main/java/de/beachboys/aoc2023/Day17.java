package de.beachboys.aoc2023;

import de.beachboys.Day;
import de.beachboys.Direction;
import de.beachboys.Util;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.*;

public class Day17 extends Day {

    private final Map<Pair<Integer, Integer>, Integer> map = new HashMap<>();
    private Pair<Integer, Integer> start;
    private Pair<Integer, Integer> end;
    private final Map<Integer, Set<Triplet<Pair<Integer, Integer>, Direction, Integer>>> queue = new HashMap<>();
    private final Set<Triplet<Pair<Integer, Integer>, Direction, Integer>> seenQueueElements = new HashSet<>();

    public Object part1(List<String> input) {
        return runLogic(input, 0, 3);
    }

    public Object part2(List<String> input) {
        return runLogic(input, 4, 10);
    }

    public int runLogic(List<String> input, int minDistanceInOneDirection, int maxDistanceInOneDirection) {
        parseInputAndClearState(input);
        Triplet<Pair<Integer, Integer>, Direction, Integer> startSouth = Triplet.with(start, Direction.SOUTH, 0);
        Triplet<Pair<Integer, Integer>, Direction, Integer> startEast = Triplet.with(start, Direction.EAST, 0);
        queue.put(0, Set.of(startSouth, startEast));
        for (int currentHeatLoss = 0; currentHeatLoss < Integer.MAX_VALUE; currentHeatLoss++) {
            for (Triplet<Pair<Integer, Integer>, Direction, Integer> queueElement : queue.getOrDefault(currentHeatLoss, Set.of())) {
                if (end.equals(queueElement.getValue0()) && queueElement.getValue2() >= minDistanceInOneDirection) {
                    return currentHeatLoss;
                }
                if (queueElement.getValue2() < maxDistanceInOneDirection) {
                    addNewPositionToQueue(currentHeatLoss, queueElement.getValue0(), queueElement.getValue1(), queueElement.getValue2());
                }
                if (queueElement.getValue2() >= minDistanceInOneDirection) {
                    addNewPositionToQueue(currentHeatLoss, queueElement.getValue0(), queueElement.getValue1().turnRight(), 0);
                    addNewPositionToQueue(currentHeatLoss, queueElement.getValue0(), queueElement.getValue1().turnLeft(), 0);
                }
            }
        }
        return Integer.MAX_VALUE;
    }

    private void addNewPositionToQueue(int heatLossOfOldPosition, Pair<Integer, Integer> oldPosition, Direction direction, int oldDistanceInDirection) {
        Pair<Integer, Integer> newPosition = direction.move(oldPosition, 1);
        int newHeatLoss = heatLossOfOldPosition + map.getOrDefault(newPosition, 0);
        Triplet<Pair<Integer, Integer>, Direction, Integer> queueElement = Triplet.with(newPosition, direction, oldDistanceInDirection + 1);
        if (!seenQueueElements.contains(queueElement) && Util.isInRectangle(newPosition, start, end)) {
            Set<Triplet<Pair<Integer, Integer>, Direction, Integer>> set = queue.getOrDefault(newHeatLoss, new HashSet<>());
            set.add(queueElement);
            queue.put(newHeatLoss, set);
            seenQueueElements.add(queueElement);
        }

    }

    private void parseInputAndClearState(List<String> input) {
        Map<Pair<Integer, Integer>, String> mapWithStrings = Util.buildImageMap(input);
        map.clear();
        for (Map.Entry<Pair<Integer, Integer>, String> entry : mapWithStrings.entrySet()) {
            map.put(entry.getKey(), Integer.parseInt(entry.getValue()));
        }
        start = Pair.with(0, 0);
        end = Pair.with(input.getFirst().length() - 1, input.size() - 1);
        queue.clear();
        seenQueueElements.clear();
    }

}
