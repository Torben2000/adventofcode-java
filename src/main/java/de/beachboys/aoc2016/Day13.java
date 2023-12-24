package de.beachboys.aoc2016;

import de.beachboys.Day;
import de.beachboys.Direction;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.*;

public class Day13 extends Day {

    private Tuple2<Integer, Integer> target = Tuple.tuple(-1, -1);
    private int maxSteps = Integer.MAX_VALUE;
    private int favoriteNumber;
    private final Set<Tuple2<Integer, Integer>> visitedPositions = new HashSet<>();
    private final Deque<Tuple2<Integer, Tuple2<Integer, Integer>>> positionsToCheck = new LinkedList<>();

    public Object part1(List<String> input) {
        int targetX = Util.getIntValueFromUser("Target coordinate X", 31, io);
        int targetY = Util.getIntValueFromUser("Target coordinate Y", 39, io);
        target = Tuple.tuple(targetX, targetY);
        return runLogicAndReturnNumberOfStepsToReachTarget(input);
    }

    public Object part2(List<String> input) {
        maxSteps = 50;
        runLogicAndReturnNumberOfStepsToReachTarget(input);
        return visitedPositions.size();
    }

    private int runLogicAndReturnNumberOfStepsToReachTarget(List<String> input) {
        favoriteNumber = Integer.parseInt(input.getFirst());
        Tuple2<Integer, Integer> start = Tuple.tuple(1, 1);
        queueNextPosition(start, 0);
        while (!positionsToCheck.isEmpty()) {
            Tuple2<Integer, Tuple2<Integer, Integer>> positionToCheck = positionsToCheck.poll();
            if (positionToCheck.v1 < maxSteps) {
                for (Direction dir : Direction.values()) {
                    Tuple2<Integer, Integer> nextPosition = dir.move(positionToCheck.v2, 1);
                    if (target.equals(nextPosition)) {
                        return positionToCheck.v1 + 1;
                    }
                    if (isValidPosition(nextPosition) && !visitedPositions.contains(nextPosition) && isOpenSpace(nextPosition)) {
                        queueNextPosition(nextPosition, positionToCheck.v1 + 1);
                    }
                }
            }
        }
        return -1;
    }

    private void queueNextPosition(Tuple2<Integer, Integer> nextPosition, int numberOfStepsToReachNextPosition) {
        visitedPositions.add(nextPosition);
        positionsToCheck.add(Tuple.tuple(numberOfStepsToReachNextPosition, nextPosition));
    }

    private boolean isValidPosition(Tuple2<Integer, Integer> position) {
        return position.v1 >= 0 && position.v2 >= 0;
    }

    private boolean isOpenSpace(Tuple2<Integer, Integer> position) {
        int x = position.v1;
        int y = position.v2;
        int sum = x*x + 3*x + 2*x*y + y + y*y + favoriteNumber;
        String binaryString = Integer.toBinaryString(sum);
        int numberOfOnes = binaryString.replace("0", "").length();
        return numberOfOnes % 2 == 0;
    }

}
