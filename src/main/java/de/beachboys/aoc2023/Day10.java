package de.beachboys.aoc2023;

import de.beachboys.Day;
import de.beachboys.Direction;
import de.beachboys.Util;
import org.javatuples.Pair;

import java.util.*;

public class Day10 extends Day {

    private Map<Pair<Integer, Integer>, String> map;
    private Pair<Integer, Integer> start;
    private final Set<Pair<Integer, Integer>> pipes = new HashSet<>();
    private Direction startLeavingDir;


    public Object part1(List<String> input) {
        parseInput(input);
        return pipes.size() / 2;
    }

    public Object part2(List<String> input) {
        parseInput(input);

        Set<Pair<Integer, Integer>> leftFromPipes = new HashSet<>();
        Set<Pair<Integer, Integer>> rightFromPipes = new HashSet<>();

        Pair<Integer, Integer> pos = start;
        Direction enteringDirection = startLeavingDir.getOpposite();
        for (int i = 0; i < pipes.size(); i++) {
            Direction leavingDirection = getLeavingDirection(pos, enteringDirection);
            for (Direction leftDir : getDirectionsNextToPipe(enteringDirection, leavingDirection, true)) {
                fillSetNextToPipes(leftFromPipes, leftDir, pos);
            }
            for (Direction rightDir : getDirectionsNextToPipe(enteringDirection, leavingDirection, false)) {
                fillSetNextToPipes(rightFromPipes, rightDir, pos);
            }

            enteringDirection = leavingDirection;
            pos = enteringDirection.move(pos, 1);
        }

        // other checks might be needed for other input where (0, 0) is in pipes
        if (leftFromPipes.contains(Pair.with(0, 0))) {
            return rightFromPipes.size();
        }
        return leftFromPipes.size();
    }

    private void parseInput(List<String> input) {
        map = Util.buildImageMap(input);
        start = map.entrySet().stream().filter(e -> "S".equals(e.getValue())).map(Map.Entry::getKey).findFirst().orElseThrow();
        pipes.clear();
        Pair<Integer, Integer> pos = start;
        for (Direction dir : Direction.values()) {
            Pair<Integer, Integer> newPos = dir.move(start, 1);
            String charAtNewPos = map.getOrDefault(newPos, ".");
            if (canEnter(charAtNewPos, dir)) {
                pos = newPos;
                pipes.add(pos);
                startLeavingDir = dir;
                break;
            }
        }

        Direction dir = startLeavingDir;
        while (!"S".equals(map.get(pos))) {
            dir = getLeavingDirection(pos, dir);
            pos = dir.move(pos, 1);
            pipes.add(pos);
        }

        map.put(pos, getPipeChar(startLeavingDir, dir));
    }

    private boolean canEnter(String pipeCharacter, Direction enteringDirection) {
        return "|".equals(pipeCharacter) && (Direction.NORTH.equals(enteringDirection) || Direction.SOUTH.equals(enteringDirection))
                || "-".equals(pipeCharacter) && (Direction.EAST.equals(enteringDirection) || Direction.WEST.equals(enteringDirection))
                || "L".equals(pipeCharacter) && (Direction.SOUTH.equals(enteringDirection) || Direction.WEST.equals(enteringDirection))
                || "J".equals(pipeCharacter) && (Direction.SOUTH.equals(enteringDirection) || Direction.EAST.equals(enteringDirection))
                || "7".equals(pipeCharacter) && (Direction.NORTH.equals(enteringDirection) || Direction.EAST.equals(enteringDirection))
                || "F".equals(pipeCharacter) && (Direction.NORTH.equals(enteringDirection) || Direction.WEST.equals(enteringDirection));
    }

    private Direction getLeavingDirection(Pair<Integer, Integer> pos, Direction enteringDirection) {
        switch (map.get(pos)) {
            case "|":
            case "-":
                return enteringDirection;
            case "L":
                if (Direction.SOUTH.equals(enteringDirection)) {
                    return Direction.EAST;
                } else {
                    return Direction.NORTH;
                }
            case "J":
                if (Direction.SOUTH.equals(enteringDirection)) {
                    return Direction.WEST;
                } else {
                    return Direction.NORTH;
                }
            case "7":
                if (Direction.NORTH.equals(enteringDirection)) {
                    return Direction.WEST;
                } else {
                    return Direction.SOUTH;
                }
            case "F":
                if (Direction.NORTH.equals(enteringDirection)) {
                    return Direction.EAST;
                } else {
                    return Direction.SOUTH;
                }
            default:
                throw new IllegalArgumentException();
        }
    }

    private String getPipeChar(Direction leavingDirection, Direction enteringDirection) {
        if (Direction.NORTH == leavingDirection && Direction.NORTH == enteringDirection || Direction.SOUTH == leavingDirection && Direction.SOUTH == enteringDirection) {
            return "|";
        }
        if (Direction.WEST == leavingDirection && Direction.WEST == enteringDirection || Direction.EAST == leavingDirection && Direction.EAST == enteringDirection) {
            return "-";
        }
        if (Direction.NORTH == leavingDirection && Direction.EAST == enteringDirection || Direction.WEST == leavingDirection && Direction.SOUTH == enteringDirection) {
            return "J";
        }
        if (Direction.SOUTH == leavingDirection && Direction.EAST == enteringDirection || Direction.WEST == leavingDirection && Direction.NORTH == enteringDirection) {
            return "7";
        }
        if (Direction.NORTH == leavingDirection && Direction.WEST == enteringDirection || Direction.EAST == leavingDirection && Direction.SOUTH == enteringDirection) {
            return "L";
        }
        if (Direction.SOUTH == leavingDirection && Direction.WEST == enteringDirection || Direction.EAST == leavingDirection && Direction.NORTH == enteringDirection) {
            return "F";
        }
        throw new IllegalArgumentException();
    }

    private Set<Direction> getDirectionsNextToPipe(Direction enteringDirection, Direction leavingDirection, boolean leftFromPipe) {
        Set<Direction> directions = new HashSet<>();
        Direction first = leftFromPipe ? leavingDirection.turnLeft() : leavingDirection.turnRight();
        if (!enteringDirection.getOpposite().equals(first)) {
            directions.add(first);
            Direction second = leavingDirection.getOpposite();
            if (!enteringDirection.getOpposite().equals(second)) {
                directions.add(second);
            }
        }
        return directions;
    }

    private void fillSetNextToPipes(Set<Pair<Integer, Integer>> set, Direction leavingDirection, Pair<Integer, Integer> positionToLeave) {
        Pair<Integer, Integer> pos = leavingDirection.move(positionToLeave, 1);
        if (!set.contains(pos) && !pipes.contains(pos) && map.containsKey(pos)) {
            set.add(pos);
            for (Direction direction : Direction.values()) {
                fillSetNextToPipes(set, direction, pos);
            }
        }
    }

}
