package de.beachboys.aoc2022;

import de.beachboys.Day;
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
                int height = getTreeHeight(map, x, y);
                int scenicScore = 1;
                boolean visibleLeft = true;
                for (int i = x - 1; i >= 0; i--) {
                    if (height <= getTreeHeight(map, i, y)) {
                        scenicScore *= x - i;
                        visibleLeft = false;
                        break;
                    }
                }
                if (visibleLeft) {
                    scenicScore *= x;
                }
                boolean visibleRight = true;
                for (int i = x + 1; i <= maxX; i++) {
                    if (height <= getTreeHeight(map, i, y)) {
                        scenicScore *= i - x;
                        visibleRight = false;
                        break;
                    }
                }
                if (visibleRight) {
                    scenicScore *= maxX-x;
                }
                boolean visibleTop = true;
                for (int i = y - 1; i >= 0; i--) {
                    if (height <= getTreeHeight(map, x, i)) {
                        scenicScore *= y - i;
                        visibleTop = false;
                        break;
                    }
                }
                if (visibleTop) {
                    scenicScore *= y;
                }
                boolean visibleDown = true;
                for (int i = y + 1; i <= maxY; i++) {
                    if (height <= getTreeHeight(map, x, i)) {
                        scenicScore *= i - y;
                        visibleDown = false;
                        break;
                    }
                }
                if (visibleDown) {
                    scenicScore *= maxY - y;
                }
                if (visibleLeft || visibleRight || visibleTop || visibleDown) {
                    visibleTrees++;
                    maxScenicScore = Math.max(maxScenicScore, scenicScore);
                }
            }

        }
        return Pair.with(visibleTrees, maxScenicScore);
    }

    private int getTreeHeight(Map<Pair<Integer, Integer>, String> map, int i, int j) throws NumberFormatException {
        return Integer.parseInt(map.get(Pair.with(i, j)));
    }

}
