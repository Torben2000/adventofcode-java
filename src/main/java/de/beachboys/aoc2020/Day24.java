package de.beachboys.aoc2020;

import de.beachboys.Day;
import org.javatuples.Pair;

import java.util.*;

public class Day24 extends Day {

    private enum Direction {
        E(1, 1), SE(0, 1), SW(-1, 0), W(-1, -1), NW(0, -1), NE(1, 0);

        public final int stepX;
        public final int stepY;

        Direction(int stepX, int stepY) {
            this.stepX = stepX;
            this.stepY = stepY;
        }
    }

    public Object part1(List<String> input) {
        Set<Pair<Integer, Integer>> blackTiles = getInitialBlackTiles(input);
        return blackTiles.size();
    }

    public Object part2(List<String> input) {
        Set<Pair<Integer, Integer>> blackTiles = getInitialBlackTiles(input);
        for (int i = 0; i < 100; i++) {
            blackTiles = runCycle(blackTiles);
        }

        return blackTiles.size();
    }

    private Set<Pair<Integer, Integer>> runCycle(Set<Pair<Integer, Integer>> blackTiles) {
        Set<Pair<Integer, Integer>> newBlackTiles = new HashSet<>();
        int xMin = blackTiles.stream().mapToInt(Pair::getValue0).min().orElseThrow() - 1;
        int xMax = blackTiles.stream().mapToInt(Pair::getValue0).max().orElseThrow() + 1;
        int yMin = blackTiles.stream().mapToInt(Pair::getValue1).min().orElseThrow() - 1;
        int yMax = blackTiles.stream().mapToInt(Pair::getValue1).max().orElseThrow() + 1;
        for (int x = xMin; x <= xMax; x++) {
            for (int y = yMin; y <= yMax; y++) {
                Pair<Integer, Integer> position = Pair.with(x, y);
                int blackTileCount = getSurroundingBlackTileCount(blackTiles, position);
                if (blackTileCount == 2 || (blackTileCount == 1 && blackTiles.contains(position))) {
                    newBlackTiles.add(position);
                }
            }
        }
        blackTiles = newBlackTiles;
        return blackTiles;
    }

    private Set<Pair<Integer, Integer>> getInitialBlackTiles(List<String> input) {
        List<Pair<Integer, Integer>> tiles = getTilesFromInput(input);
        Set<Pair<Integer, Integer>> blackTiles = new HashSet<>();
        for (Pair<Integer, Integer> tile : tiles) {
            if (!blackTiles.remove(tile)) {
                blackTiles.add(tile);
            }
        }
        return blackTiles;
    }

    private List<Pair<Integer, Integer>> getTilesFromInput(List<String> input) {
        List<Pair<Integer, Integer>> tiles = new ArrayList<>();
        for (String line : input) {
            StringBuilder currentDir = new StringBuilder();
            List<Direction> tileDirections = new ArrayList<>();
            for (int i = 0; i < line.length(); i++) {
                currentDir.append(line.charAt(i));
                try {
                    Direction direction = Direction.valueOf(currentDir.toString().toUpperCase());
                    currentDir = new StringBuilder();
                    tileDirections.add(direction);
                } catch (IllegalArgumentException e) {
                    // not a valid direction yet
                }
            }
            tiles.add(getPosition(tileDirections));
        }
        return tiles;
    }

    private Pair<Integer, Integer> getPosition(List<Direction> tileDirections) {
        int x = 0;
        int y = 0;
        for (Direction d : tileDirections) {
            x += d.stepX;
            y += d.stepY;
        }
        return Pair.with(x, y);
    }


    private int getSurroundingBlackTileCount(Set<Pair<Integer, Integer>> blackTiles, Pair<Integer, Integer> position) {
        int blackTileCount = 0;
        for (int xOffset = -1; xOffset <= 1; xOffset++) {
            for (int yOffset = -1; yOffset <= 1; yOffset++) {
                if (isAllowedAdjacent(xOffset, yOffset)) {
                    Pair<Integer, Integer> adjacentPosition = Pair.with(position.getValue0() + xOffset, position.getValue1() + yOffset);
                    if (blackTiles.contains(adjacentPosition)) {
                        blackTileCount++;
                    }
                }
            }
        }
        return blackTileCount;
    }

    private boolean isAllowedAdjacent(int xOffset, int yOffset) {
        return !(xOffset == 0 && yOffset == 0)
                && !(xOffset == -1 && yOffset == 1)
                && !(xOffset == 1 && yOffset == -1);
    }

}
