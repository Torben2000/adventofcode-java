package de.beachboys.aoc2023;

import de.beachboys.Day;
import de.beachboys.Direction;
import de.beachboys.Util;
import org.javatuples.Pair;

import java.util.*;

public class Day10 extends Day {

    private Map<Pair<Integer, Integer>, String> map;
    private final List<Pair<Integer, Integer>> corners = new ArrayList<>();
    private Direction startLeavingDir;


    public Object part1(List<String> input) {
        parseInput(input);
        return Util.getPolygonLineLength(corners) / 2;
    }

    public Object part2(List<String> input) {
        parseInput(input);
        return Util.getPolygonSize(corners, false);
    }

    private void parseInput(List<String> input) {
        map = Util.buildImageMap(input);
        Pair<Integer, Integer> start = map.entrySet().stream().filter(e -> "S".equals(e.getValue())).map(Map.Entry::getKey).findFirst().orElseThrow();
        corners.clear();
        corners.add(start);
        Pair<Integer, Integer> pos = start;
        for (Direction dir : Direction.values()) {
            Pair<Integer, Integer> newPos = dir.move(start, 1);
            String charAtNewPos = map.getOrDefault(newPos, ".");
            if (canEnter(charAtNewPos, dir)) {
                pos = newPos;
                startLeavingDir = dir;
                break;
            }
        }

        Direction dir = startLeavingDir;
        while (!"S".equals(map.get(pos))) {
            Direction leavingDirection = getLeavingDirection(pos, dir);
            if (dir != leavingDirection) {
                corners.add(pos);
            }
            dir = leavingDirection;
            pos = dir.move(pos, 1);
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

}
