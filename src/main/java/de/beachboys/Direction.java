package de.beachboys;

import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.HashSet;
import java.util.Set;

public enum Direction {

    NORTH(0, -1), SOUTH(0, 1), WEST(-1, 0), EAST(1, 0);

    public final int stepX;
    public final int stepY;

    Direction(int stepX, int stepY) {
        this.stepX = stepX;
        this.stepY = stepY;
    }

    public static Direction fromString(String directionString) {
        return switch (directionString.toLowerCase()) {
            case "n", "u", "north", "up", "^" -> Direction.NORTH;
            case "e", "r", "east", "right", ">" -> Direction.EAST;
            case "s", "d", "south", "down", "v" -> Direction.SOUTH;
            case "w", "l", "west", "left", "<" -> Direction.WEST;
            default -> throw new IllegalArgumentException();
        };
    }

    public Tuple2<Integer, Integer> move(Tuple2<Integer, Integer> currentPosition, int distance) {
        return Tuple.tuple(currentPosition.v1 + distance * stepX, currentPosition.v2 + distance * stepY);
    }

    public Direction turn(boolean left) {
        if (left) {
            return turnLeft();
        }
        return turnRight();
    }

    public Direction turnLeft() {
        return switch (this) {
            case NORTH -> WEST;
            case WEST -> SOUTH;
            case SOUTH -> EAST;
            case EAST -> NORTH;
        };
    }

    public Direction turnRight() {
        return switch (this) {
            case NORTH -> EAST;
            case WEST -> NORTH;
            case SOUTH -> WEST;
            case EAST -> SOUTH;
        };
    }

    public Direction getOpposite() {
        return switch (this) {
            case NORTH -> SOUTH;
            case WEST -> EAST;
            case SOUTH -> NORTH;
            case EAST -> WEST;
        };
    }

    public static Set<Tuple2<Integer, Integer>> getDirectNeighbors(Tuple2<Integer, Integer> position) {
        Set<Tuple2<Integer, Integer>> neighbors = new HashSet<>();
        for (Direction dir : values()) {
            neighbors.add(dir.move(position, 1));
        }
        return neighbors;
    }

}
