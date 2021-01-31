package de.beachboys;

import org.javatuples.Triplet;

public enum DirectionHex {

    NORTH(0, 1, -1), SOUTH(0, -1, 1), NORTHWEST(-1, 1, 0), NORTHEAST(1, 0, -1), SOUTHWEST(-1, 0, 1), SOUTHEAST(1, -1, 0);

    public final int stepX;
    public final int stepY;
    public final int stepZ;

    DirectionHex(int stepX, int stepY, int stepZ) {
        this.stepX = stepX;
        this.stepY = stepY;
        this.stepZ = stepZ;
    }

    public static DirectionHex fromString(String directionString) {
        switch (directionString.toLowerCase()) {
            case "n":
            case "north":
                return DirectionHex.NORTH;
            case "ne":
            case "northeast":
                return DirectionHex.NORTHEAST;
            case "se":
            case "southeast":
                return DirectionHex.SOUTHEAST;
            case "s":
            case "south":
                return DirectionHex.SOUTH;
            case "sw":
            case "southwest":
                return DirectionHex.SOUTHWEST;
            case "nw":
            case "northwest":
                return DirectionHex.NORTHWEST;
            default:
                throw new IllegalArgumentException();
        }
    }



    public Triplet<Integer, Integer, Integer> move(Triplet<Integer, Integer, Integer> currentPosition, int distance) {
        return Triplet.with(currentPosition.getValue0() + distance * stepX, currentPosition.getValue1() + distance * stepY, currentPosition.getValue2() + distance * stepZ);
    }

    public DirectionHex turn(boolean left) {
        if (left) {
            return turnLeft();
        }
        return turnRight();
    }

    public DirectionHex turnLeft() {
        switch (this) {
            case NORTH:
                return NORTHWEST;
            case NORTHWEST:
                return SOUTHWEST;
            case SOUTHWEST:
                return SOUTH;
            case SOUTH:
                return SOUTHEAST;
            case SOUTHEAST:
                return NORTHEAST;
            case NORTHEAST:
                return NORTH;
        }
        throw new IllegalStateException();
    }

    public DirectionHex turnRight() {
        switch (this) {
            case NORTH:
                return NORTHEAST;
            case NORTHWEST:
                return NORTH;
            case SOUTHWEST:
                return NORTHWEST;
            case SOUTH:
                return SOUTHWEST;
            case SOUTHEAST:
                return SOUTH;
            case NORTHEAST:
                return SOUTHEAST;
        }
        throw new IllegalStateException();
    }

    public DirectionHex getOpposite() {
        switch (this) {
            case NORTH:
                return SOUTH;
            case NORTHWEST:
                return SOUTHEAST;
            case SOUTHWEST:
                return NORTHEAST;
            case SOUTH:
                return NORTH;
            case SOUTHEAST:
                return NORTHWEST;
            case NORTHEAST:
                return SOUTHWEST;
        }
        throw new IllegalStateException();
    }

}
