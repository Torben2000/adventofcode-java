package de.beachboys.aoc2023;

import de.beachboys.Day;
import de.beachboys.Direction;
import de.beachboys.Util;
import org.jooq.lambda.function.Function3;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day18 extends Day {

    public Object part1(List<String> input) {
        return runLogic(input, (d, s, c) -> Tuple.tuple(d, s));
    }


    public Object part2(List<String> input) {
        Function3<Direction, Integer, String, Tuple2<Direction, Integer>> modifier = Day18::getRealDirectionAndStepsPart2;
        return runLogic(input, modifier);
    }

    private static long runLogic(List<String> input, Function3<Direction, Integer, String, Tuple2<Direction, Integer>> directionAndStepsModifier) {
        Pattern inputPattern = Pattern.compile("([RLUD]) ([0-9]+) \\(#([0-f]{6})\\)");

        Tuple2<Integer, Integer> pos = Tuple.tuple(0,0);
        List<Tuple2<Integer, Integer>> polygonPoints = new ArrayList<>();
        polygonPoints.add(pos);
        for (String line : input) {
            Matcher m = inputPattern.matcher(line);
            if (m.matches()) {
                Direction dir = Direction.fromString(m.group(1));
                int steps = Integer.parseInt(m.group(2));
                String color = m.group(3);

                Tuple2<Direction, Integer> realDirAndSteps = directionAndStepsModifier.apply(dir, steps, color);
                pos = realDirAndSteps.v1.move(pos, realDirAndSteps.v2);
                polygonPoints.add(pos);
            }
        }

        return Util.getPolygonSize(polygonPoints, true);
    }


    private static Tuple2<Direction, Integer> getRealDirectionAndStepsPart2(Direction dir, int steps, String color) {
        int realSteps = Integer.parseInt(color.substring(0, 5), 16);
        Direction realDir;
        switch (color.substring(5)) {
            case "0" -> realDir = Direction.EAST;
            case "1" -> realDir = Direction.SOUTH;
            case "2" -> realDir = Direction.WEST;
            case "3" -> realDir = Direction.NORTH;
            default -> throw new IllegalArgumentException();
        }
        return Tuple.tuple(realDir, realSteps);
    }
}
