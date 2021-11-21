package de.beachboys.aoc2018;

import de.beachboys.Day;
import de.beachboys.Direction;
import de.beachboys.Util;
import org.javatuples.Pair;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day17 extends Day {

    public static final String FLOWING_WATER = "|";
    public static final String STILL_WATER = "~";
    public static final String CLAY = "#";

    private final Map<Pair<Integer, Integer>, String> map = new HashMap<>();
    private int minHeight;
    private int maxHeight;

    public Object part1(List<String> input) {
        runLogic(input);
        return map.entrySet().stream()
                .filter(entry -> entry.getKey().getValue1() >= minHeight
                        && (STILL_WATER.equals(entry.getValue()) || FLOWING_WATER.equals(entry.getValue())))
                .count();
    }

    public Object part2(List<String> input) {
        runLogic(input);
        return map.entrySet().stream()
                .filter(entry -> entry.getKey().getValue1() >= minHeight
                        && STILL_WATER.equals(entry.getValue()))
                .count();
    }

    private void runLogic(List<String> input) {
        parseInput(input);
        Pair<Integer, Integer> spring = Pair.with(500, 0);
        map.put(spring, "+");
        flowWater(spring);
        io.logDebug(Util.paintMap(map));
    }

    private void parseInput(List<String> input) {
        Pattern p = Pattern.compile("([xy])=([0-9]+), ([xy])=([0-9]+)..([0-9]+)");
        for (String line : input) {
            Matcher m = p.matcher(line);
            if (m.matches()) {
                String xy = m.group(1);
                int firstValue = Integer.parseInt(m.group(2));
                String xy2 = m.group(3);
                int rangeStart = Integer.parseInt(m.group(4));
                int rangeEnd = Integer.parseInt(m.group(5));
                if (xy.equals(xy2)) {
                    throw new IllegalArgumentException();
                }
                for (int i = rangeStart; i <= rangeEnd; i++) {
                    if ("x".equals(xy)) {
                        map.put(Pair.with(firstValue, i), CLAY);
                    } else {
                        map.put(Pair.with(i, firstValue), CLAY);
                    }
                }
            } else {
                throw new IllegalArgumentException();
            }
        }
        minHeight = map.keySet().stream().map(Pair::getValue1).mapToInt(Integer::intValue).min().orElseThrow();
        maxHeight = map.keySet().stream().map(Pair::getValue1).mapToInt(Integer::intValue).max().orElseThrow();
    }

    private void flowWater(Pair<Integer, Integer> currentPos) {
        if (currentPos.getValue1() > maxHeight) {
            return;
        }
        if (!map.containsKey(currentPos)) {
            map.put(currentPos, FLOWING_WATER);
        }
        Pair<Integer, Integer> below = Direction.SOUTH.move(currentPos, 1);
        if (!map.containsKey(below)) {
            flowWater(below);
        }
        if (isNotFlowingWater(below)) {
            flowSideways(currentPos);
        }
   }

    private boolean isNotFlowingWater(Pair<Integer, Integer> below) {
        return below.getValue1() <= maxHeight && !FLOWING_WATER.equals(map.get(below));
    }

    private void flowSideways(Pair<Integer, Integer> currentPos) {
        Pair<Integer, Integer> leftBorder = flowSidewaysAndReturnBorder(currentPos, Direction.WEST);
        Pair<Integer, Integer> rightBorder = flowSidewaysAndReturnBorder(currentPos, Direction.EAST);
        if (CLAY.equals(map.get(leftBorder)) && CLAY.equals(map.get(rightBorder))) {
            fillWithStillWater(leftBorder, rightBorder);
        }
    }

    private void fillWithStillWater(Pair<Integer, Integer> leftBorder, Pair<Integer, Integer> rightBorder) {
        Pair<Integer, Integer> curPos = Direction.EAST.move(leftBorder, 1);
        while (!curPos.equals(rightBorder)) {
            map.put(curPos, STILL_WATER);
            curPos = Direction.EAST.move(curPos, 1);
        }
    }

    private Pair<Integer, Integer> flowSidewaysAndReturnBorder(Pair<Integer, Integer> curPos, Direction direction) {
        Pair<Integer, Integer> nextPos = direction.move(curPos, 1);
        boolean continueSideways = true;
        while (continueSideways && !map.containsKey(nextPos)) {
            map.put(nextPos, FLOWING_WATER);
            Pair<Integer, Integer> below = Direction.SOUTH.move(nextPos, 1);
            if (!map.containsKey(below)) {
                flowWater(below);
                continueSideways = STILL_WATER.equals(map.get(below));
            }
            if (continueSideways) {
                nextPos = direction.move(nextPos, 1);
            }
        }
        return nextPos;
    }

}
