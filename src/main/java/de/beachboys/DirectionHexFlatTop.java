package de.beachboys;

import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple3;

import java.util.HashSet;
import java.util.Set;

public enum DirectionHexFlatTop {

    NORTH(0, 1, -1), SOUTH(0, -1, 1), NORTHWEST(-1, 1, 0), NORTHEAST(1, 0, -1), SOUTHWEST(-1, 0, 1), SOUTHEAST(1, -1, 0);

    public final int stepX;
    public final int stepY;
    public final int stepZ;

    DirectionHexFlatTop(int stepX, int stepY, int stepZ) {
        this.stepX = stepX;
        this.stepY = stepY;
        this.stepZ = stepZ;
    }

    public static DirectionHexFlatTop fromString(String directionString) {
        switch (directionString.toLowerCase()) {
            case "n":
            case "north":
                return DirectionHexFlatTop.NORTH;
            case "ne":
            case "northeast":
                return DirectionHexFlatTop.NORTHEAST;
            case "se":
            case "southeast":
                return DirectionHexFlatTop.SOUTHEAST;
            case "s":
            case "south":
                return DirectionHexFlatTop.SOUTH;
            case "sw":
            case "southwest":
                return DirectionHexFlatTop.SOUTHWEST;
            case "nw":
            case "northwest":
                return DirectionHexFlatTop.NORTHWEST;
            default:
                throw new IllegalArgumentException();
        }
    }

    public Tuple3<Integer, Integer, Integer> move(Tuple3<Integer, Integer, Integer> currentPosition, int distance) {
        return Tuple.tuple(currentPosition.v1 + distance * stepX, currentPosition.v2 + distance * stepY, currentPosition.v3 + distance * stepZ);
    }

    public DirectionHexFlatTop turn(boolean left) {
        if (left) {
            return turnLeft();
        }
        return turnRight();
    }

    public DirectionHexFlatTop turnLeft() {
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

    public DirectionHexFlatTop turnRight() {
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

    public DirectionHexFlatTop getOpposite() {
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

    public static Set<Tuple3<Integer, Integer, Integer>> getDirectNeighbors(Tuple3<Integer, Integer, Integer> position) {
        Set<Tuple3<Integer, Integer, Integer>> neighbors = new HashSet<>();
        for (DirectionHexFlatTop dir : values()) {
            neighbors.add(dir.move(position, 1));
        }
        return neighbors;
    }

}
