package de.beachboys.aoc2016;

import de.beachboys.Day;
import de.beachboys.Direction;
import de.beachboys.Util;
import org.javatuples.Pair;

import java.util.*;

public class Day13 extends Day {

    private Pair<Integer, Integer> target = Pair.with(-1, -1);
    private int maxSteps = Integer.MAX_VALUE;
    private int favoriteNumber;
    private final Set<Pair<Integer, Integer>> visitedPositions = new HashSet<>();
    private final Deque<Pair<Integer, Pair<Integer, Integer>>> positionsToCheck = new LinkedList<>();

    public Object part1(List<String> input) {
        int targetX = Util.getIntValueFromUser("Target coordinate X", 31, io);
        int targetY = Util.getIntValueFromUser("Target coordinate Y", 39, io);
        target = Pair.with(targetX, targetY);
        return runLogicAndReturnNumberOfStepsToReachTarget(input);
    }

    public Object part2(List<String> input) {
        maxSteps = 50;
        runLogicAndReturnNumberOfStepsToReachTarget(input);
        return visitedPositions.size();
    }

    private int runLogicAndReturnNumberOfStepsToReachTarget(List<String> input) {
        favoriteNumber = Integer.parseInt(input.get(0));
        Pair<Integer, Integer> start = Pair.with(1, 1);
        queueNextPosition(start, 0);
        while (!positionsToCheck.isEmpty()) {
            Pair<Integer, Pair<Integer, Integer>> positionToCheck = positionsToCheck.poll();
            if (positionToCheck.getValue0() < maxSteps) {
                for (Direction dir : Direction.values()) {
                    Pair<Integer, Integer> nextPosition = dir.move(positionToCheck.getValue1(), 1);
                    if (target.equals(nextPosition)) {
                        return positionToCheck.getValue0() + 1;
                    }
                    if (isValidPosition(nextPosition) && !visitedPositions.contains(nextPosition) && isOpenSpace(nextPosition)) {
                        queueNextPosition(nextPosition, positionToCheck.getValue0() + 1);
                    }
                }
            }
        }
        return -1;
    }

    private void queueNextPosition(Pair<Integer, Integer> nextPosition, int numberOfStepsToReachNextPosition) {
        visitedPositions.add(nextPosition);
        positionsToCheck.add(Pair.with(numberOfStepsToReachNextPosition, nextPosition));
    }

    private boolean isValidPosition(Pair<Integer, Integer> position) {
        return position.getValue0() >= 0 && position.getValue1() >= 0;
    }

    private boolean isOpenSpace(Pair<Integer, Integer> position) {
        int x = position.getValue0();
        int y = position.getValue1();
        int sum = x*x + 3*x + 2*x*y + y + y*y + favoriteNumber;
        String binaryString = Integer.toBinaryString(sum);
        int numberOfOnes = binaryString.replace("0", "").length();
        return numberOfOnes % 2 == 0;
    }

}
