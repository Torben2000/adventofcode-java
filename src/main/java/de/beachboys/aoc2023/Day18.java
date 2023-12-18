package de.beachboys.aoc2023;

import de.beachboys.Day;
import de.beachboys.Direction;
import de.beachboys.TriFunction;
import de.beachboys.Util;
import org.javatuples.Pair;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day18 extends Day {

    private Set<Pair<Integer, Integer>> outside;
    private Set<Pair<Integer, Integer>> history;
    private Pair<Integer, Integer> topLeft;
    private Pair<Integer, Integer> bottomRight;
    private Set<Pair<Integer, Integer>> set;

    public Object part1(List<String> input) {
        return runLogic(input, (d, s, c) -> Pair.with(d, s));
    }


    public Object part2(List<String> input) {
        TriFunction<Direction, Integer, String, Pair<Direction, Integer>> modifier = Day18::getRealDirectionAndStepsPart2;
        return runLogic(input, modifier);
    }

    private static long runLogic(List<String> input, TriFunction<Direction, Integer, String, Pair<Direction, Integer>> directionAndStepsModifier) {
        Pattern inputPattern = Pattern.compile("(.) (.*) \\(#(.{6})\\)");

        Pair<Integer, Integer> pos = Pair.with(0,0);
        List<Pair<Integer, Integer>> polygonPoints = new ArrayList<>();
        polygonPoints.add(pos);
        for (String line : input) {
            Matcher m = inputPattern.matcher(line);
            if (m.matches()) {
                Direction dir = Direction.fromString(m.group(1));
                int steps = Integer.parseInt(m.group(2));
                String color = m.group(3);

                Pair<Direction, Integer> realDirAndSteps = directionAndStepsModifier.apply(dir, steps, color);
                pos = realDirAndSteps.getValue0().move(pos, realDirAndSteps.getValue1());
                polygonPoints.add(pos);
            }
        }

        return Util.getPolygonSize(polygonPoints, true);
    }


    private static Pair<Direction, Integer> getRealDirectionAndStepsPart2(Direction dir, int steps, String color) {
        int realSteps = Integer.parseInt(color.substring(0, 5), 16);
        Direction realDir;
        switch (color.substring(5)) {
            case "0" -> realDir = Direction.EAST;
            case "1" -> realDir = Direction.SOUTH;
            case "2" -> realDir = Direction.WEST;
            case "3" -> realDir = Direction.NORTH;
            default -> throw new IllegalArgumentException();
        }
        return Pair.with(realDir, realSteps);
    }
}
