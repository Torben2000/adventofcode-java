package de.beachboys.aoc2020;

import de.beachboys.Day;
import de.beachboys.DirectionHexPointyTop;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day24 extends Day {

    public Object part1(List<String> input) {
        Set<Tuple3<Integer, Integer, Integer>> blackTiles = getInitialBlackTiles(input);
        return blackTiles.size();
    }

    public Object part2(List<String> input) {
        Set<Tuple3<Integer, Integer, Integer>> blackTiles = getInitialBlackTiles(input);
        for (int i = 0; i < 100; i++) {
            blackTiles = runCycle(blackTiles);
        }

        return blackTiles.size();
    }

    private Set<Tuple3<Integer, Integer, Integer>> runCycle(Set<Tuple3<Integer, Integer, Integer>> blackTiles) {
        Set<Tuple3<Integer, Integer, Integer>> newBlackTiles = new HashSet<>();
        int xMin = blackTiles.stream().mapToInt(Tuple3::v1).min().orElseThrow() - 1;
        int xMax = blackTiles.stream().mapToInt(Tuple3::v1).max().orElseThrow() + 1;
        int yMin = blackTiles.stream().mapToInt(Tuple3::v2).min().orElseThrow() - 1;
        int yMax = blackTiles.stream().mapToInt(Tuple3::v2).max().orElseThrow() + 1;
        int zMin = blackTiles.stream().mapToInt(Tuple3::v3).min().orElseThrow() - 1;
        int zMax = blackTiles.stream().mapToInt(Tuple3::v3).max().orElseThrow() + 1;
        for (int x = xMin; x <= xMax; x++) {
            for (int y = yMin; y <= yMax; y++) {
                for (int z = zMin; z <= zMax; z++) {
                    Tuple3<Integer, Integer, Integer> position = Tuple.tuple(x, y, z);
                    int blackTileCount = getSurroundingBlackTileCount(blackTiles, position);
                    if (blackTileCount == 2 || (blackTileCount == 1 && blackTiles.contains(position))) {
                        newBlackTiles.add(position);
                    }
                }
            }
        }
        return newBlackTiles;
    }

    private Set<Tuple3<Integer, Integer, Integer>> getInitialBlackTiles(List<String> input) {
        List<Tuple3<Integer, Integer, Integer>> tiles = getTilesFromInput(input);
        Set<Tuple3<Integer, Integer, Integer>> blackTiles = new HashSet<>();
        for (Tuple3<Integer, Integer, Integer> tile : tiles) {
            if (!blackTiles.remove(tile)) {
                blackTiles.add(tile);
            }
        }
        return blackTiles;
    }

    private List<Tuple3<Integer, Integer, Integer>> getTilesFromInput(List<String> input) {
        List<Tuple3<Integer, Integer, Integer>> tiles = new ArrayList<>();
        for (String line : input) {
            StringBuilder currentDir = new StringBuilder();
            Tuple3<Integer, Integer, Integer> currentPosition = Tuple.tuple(0, 0, 0);
            for (int i = 0; i < line.length(); i++) {
                currentDir.append(line.charAt(i));
                try {
                    DirectionHexPointyTop direction = DirectionHexPointyTop.fromString(currentDir.toString());
                    currentPosition = direction.move(currentPosition, 1);
                    currentDir = new StringBuilder();
                } catch (IllegalArgumentException e) {
                    // not a valid direction yet
                }
            }
            tiles.add(currentPosition);
        }
        return tiles;
    }

    private int getSurroundingBlackTileCount(Set<Tuple3<Integer, Integer, Integer>> blackTiles, Tuple3<Integer, Integer, Integer> position) {
        int blackTileCount = 0;
        for (DirectionHexPointyTop direction : DirectionHexPointyTop.values()) {
            if (blackTiles.contains(direction.move(position, 1))) {
                blackTileCount++;
            }
        }
        return blackTileCount;
    }

}
