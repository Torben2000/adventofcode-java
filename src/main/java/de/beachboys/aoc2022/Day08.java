package de.beachboys.aoc2022;

import de.beachboys.Day;
import de.beachboys.Direction;
import de.beachboys.Util;
import org.javatuples.Pair;

import java.util.List;
import java.util.Map;

public class Day08 extends Day {

    public Object part1(List<String> input) {
        return getVisibleTreesAndMaxScenicScore(input).getValue0();
    }

    public Object part2(List<String> input) {
        return getVisibleTreesAndMaxScenicScore(input).getValue1();
    }

    private Pair<Integer, Integer> getVisibleTreesAndMaxScenicScore(List<String> input) {
        Map<Pair<Integer, Integer>, String> map = Util.buildImageMap(input);
        int maxX = input.get(0).length() - 1;
        int maxY = input.size() - 1;
        int maxScenicScore = 0;
        int visibleTrees = 2 * maxX + 2 * maxY;
        for (int x = 1; x < maxX; x++) {
            for (int y = 1; y < maxY; y++) {
                Pair<Integer, Integer> treePos = Pair.with(x, y);
                int height = getTreeHeight(map, treePos);
                int scenicScore = 1;
                boolean visible = false;

                for (Direction direction : Direction.values()) {
                    boolean visibleFromDirection = true;
                    Pair<Integer, Integer> curPos = direction.move(treePos, 1);
                    while (Util.isInRectangle(curPos, Pair.with(0, 0), Pair.with(maxX, maxY))) {
                        if (height <= getTreeHeight(map, curPos)) {
                            scenicScore *= Util.getManhattanDistance(treePos, curPos);
                            visibleFromDirection = false;
                            break;
                        }
                        curPos = direction.move(curPos, 1);
                    }
                    if (visibleFromDirection) {
                        scenicScore *= Util.getManhattanDistance(treePos, curPos) - 1;
                        visible = true;
                    }
                }

                if (visible) {
                    visibleTrees++;
                    maxScenicScore = Math.max(maxScenicScore, scenicScore);
                }
            }

        }
        return Pair.with(visibleTrees, maxScenicScore);
    }

    private static int getTreeHeight(Map<Pair<Integer, Integer>, String> map, Pair<Integer, Integer> treePos) {
        return Integer.parseInt(map.get(treePos));
    }

}
