package de.beachboys.aoc2017;

import de.beachboys.Day;
import de.beachboys.Direction;
import de.beachboys.Util;
import org.javatuples.Pair;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Day22 extends Day {

    private Direction currentDirection = Direction.NORTH;

    public Object part1(List<String> input) {
        return runLogic(input, 10000, this::updateDirectionAndReturnNewMapValuePart1);
    }

    public Object part2(List<String> input) {
        return runLogic(input, 10000000, this::updateDirectionAndReturnNewMapValuePart2);
    }

    private int runLogic(List<String> input, int bursts, Function<String, String> updateDirectionAndReturnNewMapValue) {
        Map<Pair<Integer, Integer>, String> map = Util.buildImageMap(input);
        Pair<Integer, Integer> currentPosition = Pair.with(input.get(0).length() / 2, input.size() / 2);
        int counter = 0;
        for (int i = 0; i < bursts; i++) {
            String currentMapValue = map.getOrDefault(currentPosition, ".");
            String newMapValue = updateDirectionAndReturnNewMapValue.apply(currentMapValue);
            map.put(currentPosition, newMapValue);
            if ("#".equals(newMapValue)) {
                counter++;
            }
            currentPosition = currentDirection.move(currentPosition, 1);
        }
        return counter;
    }

    private String updateDirectionAndReturnNewMapValuePart1(String currentMapValue) {
        switch (currentMapValue) {
            case "#":
                currentDirection = currentDirection.turnRight();
                return ".";
            case ".":
                currentDirection = currentDirection.turnLeft();
                return "#";
            default:
                throw new IllegalArgumentException();
        }
    }

    private String updateDirectionAndReturnNewMapValuePart2(String currentMapValue) {
        switch (currentMapValue) {
            case "#":
                currentDirection = currentDirection.turnRight();
                return "F";
            case ".":
                currentDirection = currentDirection.turnLeft();
                return "W";
            case "W":
                return "#";
            case "F":
                currentDirection = currentDirection.getOpposite();
                return ".";
            default:
                throw new IllegalArgumentException();
        }
    }

}
