package de.beachboys.aoc2016;

import de.beachboys.Day;
import de.beachboys.Direction;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day01 extends Day {

    public Object part1(List<String> input) {
        Tuple2<Integer, Integer> currentPosition = Tuple.tuple(0, 0);
        Direction currentDir = Direction.NORTH;

        for (String command : Util.parseToList(input.get(0), ", ")) {
            String turnCommand = command.substring(0, 1);
            currentDir = currentDir.turn("L".equals(turnCommand));
            int distance = Integer.parseInt(command.substring(1));
            currentPosition = currentDir.move(currentPosition, distance);
        }

        return Math.abs(currentPosition.v1) + Math.abs(currentPosition.v2);
    }

    public Object part2(List<String> input) {
        Set<Tuple2<Integer, Integer>> visitedPositions = new HashSet<>();
        Tuple2<Integer, Integer> currentPosition = Tuple.tuple(0, 0);
        Direction currentDir = Direction.NORTH;

        for (String command : Util.parseToList(input.get(0), ", ")) {
            String turnCommand = command.substring(0, 1);
            currentDir = currentDir.turn("L".equals(turnCommand));
            int distance = Integer.parseInt(command.substring(1));
            for (int i = 0; i < distance; i++) {
                currentPosition = currentDir.move(currentPosition, 1);
                if (visitedPositions.contains(currentPosition)) {
                    return Math.abs(currentPosition.v1) + Math.abs(currentPosition.v2);
                }
                visitedPositions.add(currentPosition);
            }
        }
        throw new IllegalStateException();
    }

}
