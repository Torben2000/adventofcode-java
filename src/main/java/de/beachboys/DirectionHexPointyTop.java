package de.beachboys;

import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple3;

import java.util.HashSet;
import java.util.Set;

public enum DirectionHexPointyTop {

    WEST(-1, 1, 0), EAST(1, -1, 0), NORTHWEST(0, 1, -1), NORTHEAST(1, 0, -1), SOUTHWEST(-1, 0, 1), SOUTHEAST(0, -1, 1);

    public final int stepX;
    public final int stepY;
    public final int stepZ;

    DirectionHexPointyTop(int stepX, int stepY, int stepZ) {
        this.stepX = stepX;
        this.stepY = stepY;
        this.stepZ = stepZ;
    }

    public static DirectionHexPointyTop fromString(String directionString) {
        return switch (directionString.toLowerCase()) {
            case "e", "east" -> DirectionHexPointyTop.EAST;
            case "ne", "northeast" -> DirectionHexPointyTop.NORTHEAST;
            case "se", "southeast" -> DirectionHexPointyTop.SOUTHEAST;
            case "w", "west" -> DirectionHexPointyTop.WEST;
            case "sw", "southwest" -> DirectionHexPointyTop.SOUTHWEST;
            case "nw", "northwest" -> DirectionHexPointyTop.NORTHWEST;
            default -> throw new IllegalArgumentException();
        };
    }

    public Tuple3<Integer, Integer, Integer> move(Tuple3<Integer, Integer, Integer> currentPosition, int distance) {
        return Tuple.tuple(currentPosition.v1 + distance * stepX, currentPosition.v2 + distance * stepY, currentPosition.v3 + distance * stepZ);
    }

    public DirectionHexPointyTop turn(boolean left) {
        if (left) {
            return turnLeft();
        }
        return turnRight();
    }

    public DirectionHexPointyTop turnLeft() {
        return switch (this) {
            case NORTHWEST -> WEST;
            case WEST -> SOUTHWEST;
            case SOUTHWEST -> SOUTHEAST;
            case SOUTHEAST -> EAST;
            case EAST -> NORTHEAST;
            case NORTHEAST -> NORTHWEST;
        };
    }

    public DirectionHexPointyTop turnRight() {
        return switch (this) {
            case NORTHWEST -> NORTHEAST;
            case WEST -> NORTHWEST;
            case SOUTHWEST -> WEST;
            case SOUTHEAST -> SOUTHWEST;
            case EAST -> SOUTHEAST;
            case NORTHEAST -> EAST;
        };
    }

    public DirectionHexPointyTop getOpposite() {
        return switch (this) {
            case WEST -> EAST;
            case NORTHWEST -> SOUTHEAST;
            case SOUTHWEST -> NORTHEAST;
            case EAST -> WEST;
            case SOUTHEAST -> NORTHWEST;
            case NORTHEAST -> SOUTHWEST;
        };
    }

    public static Set<Tuple3<Integer, Integer, Integer>> getDirectNeighbors(Tuple3<Integer, Integer, Integer> position) {
        Set<Tuple3<Integer, Integer, Integer>> neighbors = new HashSet<>();
        for (DirectionHexPointyTop dir : values()) {
            neighbors.add(dir.move(position, 1));
        }
        return neighbors;
    }

}
