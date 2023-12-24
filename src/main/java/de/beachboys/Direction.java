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
        switch (directionString.toLowerCase()) {
            case "n":
            case "u":
            case "north":
            case "up":
            case "^":
                return Direction.NORTH;
            case "e":
            case "r":
            case "east":
            case "right":
            case ">":
                return Direction.EAST;
            case "s":
            case "d":
            case "south":
            case "down":
            case "v":
                return Direction.SOUTH;
            case "w":
            case "l":
            case "west":
            case "left":
            case "<":
                return Direction.WEST;
            default:
                throw new IllegalArgumentException();
        }
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
        switch (this) {
            case NORTH:
                return WEST;
            case WEST:
                return SOUTH;
            case SOUTH:
                return EAST;
            case EAST:
                return NORTH;
        }
        throw new IllegalStateException();
    }

    public Direction turnRight() {
        switch (this) {
            case NORTH:
                return EAST;
            case WEST:
                return NORTH;
            case SOUTH:
                return WEST;
            case EAST:
                return SOUTH;
        }
        throw new IllegalStateException();
    }

    public Direction getOpposite() {
        switch (this) {
            case NORTH:
                return SOUTH;
            case WEST:
                return EAST;
            case SOUTH:
                return NORTH;
            case EAST:
                return WEST;
        }
        throw new IllegalStateException();
    }

    public static Set<Tuple2<Integer, Integer>> getDirectNeighbors(Tuple2<Integer, Integer> position) {
        Set<Tuple2<Integer, Integer>> neighbors = new HashSet<>();
        for (Direction dir : values()) {
            neighbors.add(dir.move(position, 1));
        }
        return neighbors;
    }

}
