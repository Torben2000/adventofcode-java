package de.beachboys.aoc2017;

import de.beachboys.Day;
import de.beachboys.Direction;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.IntSupplier;

public class Day03 extends Day {

    private final Map<Tuple2<Integer, Integer>, Integer> squares = new HashMap<>();
    private Direction currentDirection = Direction.EAST;
    private Tuple2<Integer, Integer> currentPosition = Tuple.tuple(0, 0);
    private int currentSquareId = 1;

    public Object part1(List<String> input) {
        runLogic(input, () -> squares.size() + 1, true);
        return Math.abs(currentPosition.v1) + Math.abs(currentPosition.v2);

    }

    public Object part2(List<String> input) {
        IntSupplier getCurrentSquareId = () -> {
            List<Tuple2<Integer, Integer>> neighbors = new ArrayList<>();
            for (int diffX = -1; diffX <= 1; diffX++) {
                for (int diffY = -1; diffY <= 1; diffY++) {
                    if (diffX != 0 || diffY != 0) {
                        neighbors.add(Tuple.tuple(currentPosition.v1 + diffX, currentPosition.v2 + diffY));
                    }
                }
            }
            return neighbors.stream().mapToInt(position -> squares.getOrDefault(position, 0)).sum();
        };
        runLogic(input, getCurrentSquareId, false);
        return currentSquareId;
    }

    private void runLogic(List<String> input, IntSupplier getCurrentSquareId, boolean stopLoopIfCurrentSquareIdEqualsTargetSquareId) {
        int targetSquareId = Integer.parseInt(input.get(0));
        squares.put(currentPosition, currentSquareId);
        while (currentSquareId < targetSquareId || !stopLoopIfCurrentSquareIdEqualsTargetSquareId && currentSquareId == targetSquareId) {
            Tuple2<Integer, Integer> turnedPosition = currentDirection.turnLeft().move(currentPosition, 1);
            if (squares.containsKey(turnedPosition)) {
                currentPosition = currentDirection.move(currentPosition, 1);
            } else {
                currentPosition = turnedPosition;
                currentDirection = currentDirection.turnLeft();
            }
            currentSquareId = getCurrentSquareId.getAsInt();
            squares.put(currentPosition, currentSquareId);
        }
    }

}
