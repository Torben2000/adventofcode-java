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

    public Tuple2<Long, Long> moveLong(Tuple2<Long, Long> currentPosition, long distance) {
        return Tuple.tuple(currentPosition.v1 + distance * stepX, currentPosition.v2 + distance * stepY);
    }

    public Direction turn(boolean left) {
        return turn(left, 1);
    }

    public Direction turn(boolean left, int count) {
        if (left) {
            return turnLeft(count);
        }
        return turnRight(count);
    }

    public Direction turnLeft(int count) {
        Direction newDir = this;
        for (int i = 0; i < count % values().length; i++) {
            newDir = newDir.turnLeft();
        }
        return newDir;
    }

    public Direction turnLeft() {
        return switch (this) {
            case NORTH -> WEST;
            case WEST -> SOUTH;
            case SOUTH -> EAST;
            case EAST -> NORTH;
        };
    }

    public Direction turnRight(int count) {
        Direction newDir = this;
        for (int i = 0; i < count % values().length; i++) {
            newDir = newDir.turnRight();
        }
        return newDir;
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
        return getDirectNeighbors(position, false);
    }

    public static Set<Tuple2<Integer, Integer>> getDirectNeighbors(Tuple2<Integer, Integer> position, boolean includingDiagonally) {
        Set<Tuple2<Integer, Integer>> neighbors = new HashSet<>();
        for (Direction dir : values()) {
            neighbors.add(dir.move(position, 1));
            if (includingDiagonally) {
                neighbors.add(dir.moveDiagonallyRight(position, 1));
            }
        }
        return neighbors;
    }

    public Tuple2<Integer, Integer> moveDiagonallyLeft(Tuple2<Integer, Integer> currentPosition, int distance) {
        return turnLeft().move(move(currentPosition, distance), distance);
    }

    public Tuple2<Integer, Integer> moveDiagonallyRight(Tuple2<Integer, Integer> currentPosition, int distance) {
        return turnRight().move(move(currentPosition, distance), distance);
    }

    public Tuple2<Long, Long> moveDiagonallyLeftLong(Tuple2<Long, Long> currentPosition, int distance) {
        return turnLeft().moveLong(moveLong(currentPosition, distance), distance);
    }

    public Tuple2<Long, Long> moveDiagonallyRightLong(Tuple2<Long, Long> currentPosition, int distance) {
        return turnRight().moveLong(moveLong(currentPosition, distance), distance);
    }

}
