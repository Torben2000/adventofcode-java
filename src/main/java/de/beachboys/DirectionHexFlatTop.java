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
        return switch (directionString.toLowerCase()) {
            case "n", "north" -> DirectionHexFlatTop.NORTH;
            case "ne", "northeast" -> DirectionHexFlatTop.NORTHEAST;
            case "se", "southeast" -> DirectionHexFlatTop.SOUTHEAST;
            case "s", "south" -> DirectionHexFlatTop.SOUTH;
            case "sw", "southwest" -> DirectionHexFlatTop.SOUTHWEST;
            case "nw", "northwest" -> DirectionHexFlatTop.NORTHWEST;
            default -> throw new IllegalArgumentException();
        };
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
        return switch (this) {
            case NORTH -> NORTHWEST;
            case NORTHWEST -> SOUTHWEST;
            case SOUTHWEST -> SOUTH;
            case SOUTH -> SOUTHEAST;
            case SOUTHEAST -> NORTHEAST;
            case NORTHEAST -> NORTH;
        };
    }

    public DirectionHexFlatTop turnRight() {
        return switch (this) {
            case NORTH -> NORTHEAST;
            case NORTHWEST -> NORTH;
            case SOUTHWEST -> NORTHWEST;
            case SOUTH -> SOUTHWEST;
            case SOUTHEAST -> SOUTH;
            case NORTHEAST -> SOUTHEAST;
        };
    }

    public DirectionHexFlatTop getOpposite() {
        return switch (this) {
            case NORTH -> SOUTH;
            case NORTHWEST -> SOUTHEAST;
            case SOUTHWEST -> NORTHEAST;
            case SOUTH -> NORTH;
            case SOUTHEAST -> NORTHWEST;
            case NORTHEAST -> SOUTHWEST;
        };
    }

    public static Set<Tuple3<Integer, Integer, Integer>> getDirectNeighbors(Tuple3<Integer, Integer, Integer> position) {
        Set<Tuple3<Integer, Integer, Integer>> neighbors = new HashSet<>();
        for (DirectionHexFlatTop dir : values()) {
            neighbors.add(dir.move(position, 1));
        }
        return neighbors;
    }

}
