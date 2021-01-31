package de.beachboys;

import org.javatuples.Pair;

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
            case "north":
                return Direction.NORTH;
            case "e":
            case "east":
                return Direction.EAST;
            case "s":
            case "south":
                return Direction.SOUTH;
            case "w":
            case "west":
                return Direction.WEST;
            default:
                throw new IllegalArgumentException();
        }
    }

    public Pair<Integer, Integer> move(Pair<Integer, Integer> currentPosition, int distance) {
        return Pair.with(currentPosition.getValue0() + distance * stepX, currentPosition.getValue1() + distance * stepY);
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

}
