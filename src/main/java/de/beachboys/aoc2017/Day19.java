package de.beachboys.aoc2017;

import de.beachboys.Day;
import de.beachboys.Direction;
import de.beachboys.Util;
import org.javatuples.Pair;

import java.util.List;
import java.util.Map;

public class Day19 extends Day {

    private StringBuilder letters;

    private int numberOfSteps;

    public Object part1(List<String> input) {
        runLogic(input);
        return letters.toString();
    }

    public Object part2(List<String> input) {
        runLogic(input);
        return numberOfSteps;
    }

    private void runLogic(List<String> input) {
        letters = new StringBuilder();
        numberOfSteps = 0;
        Map<Pair<Integer, Integer>, String> map = Util.buildImageMap(input);

        Pair<Integer, Integer> currentPosition = map.entrySet().stream().filter(entry -> isOnRoute(entry.getValue()) && entry.getKey().getValue1() == 0).findFirst().orElseThrow().getKey();
        Direction currentDirection = Direction.SOUTH;
        String currentValue = map.get(currentPosition);

        while (isOnRoute(currentValue)) {
            numberOfSteps++;
            currentPosition = currentDirection.move(currentPosition, 1);
            currentValue = map.get(currentPosition);
            if (isLetter(currentValue)) {
                letters.append(currentValue);
            } else if (isCorner(currentValue)) {
                currentDirection = getDirectionAfterTurn(map, currentPosition, currentDirection);
            }
        }
    }

    private Direction getDirectionAfterTurn(Map<Pair<Integer, Integer>, String> map, Pair<Integer, Integer> currentPosition, Direction currentDirection) {
        Direction nextDirection = currentDirection.turnLeft();
        if (!isOnRoute(map.get(nextDirection.move(currentPosition, 1)))) {
            nextDirection = currentDirection.turnRight();
        }
        return nextDirection;
    }

    private boolean isCorner(String value) {
        return "+".equals(value);
    }

    private boolean isLetter(String value) {
        return value.matches("[A-Z]");
    }

    private boolean isOnRoute(String value) {
        return !" ".equals(value);
    }

}
