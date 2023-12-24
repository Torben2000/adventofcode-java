package de.beachboys.aoc2023;

import de.beachboys.Day;
import de.beachboys.Direction;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day14 extends Day {

    private Set<Tuple2<Integer, Integer>> cubeShapedRocks;
    private int height;
    private int width;

    public Object part1(List<String> input) {
        Set<Tuple2<Integer, Integer>> roundedRocks = parseInputAndGetRoundedRocks(input);
        return getScore(slide(roundedRocks, Direction.NORTH, Comparator.comparing(Tuple2::v2)));
    }

    public Object part2(List<String> input) {
        Set<Tuple2<Integer, Integer>> roundedRocks = parseInputAndGetRoundedRocks(input);
        return getScore(Util.manipulateStateMultipleTimesOptimized(1000000000, roundedRocks, this::rotate));
    }

    private Set<Tuple2<Integer, Integer>> rotate(Set<Tuple2<Integer, Integer>> roundedRocks) {
        Comparator<Tuple2<Integer, Integer>> orderByX = Comparator.comparing(Tuple2::v1);
        Comparator<Tuple2<Integer, Integer>> orderByY = Comparator.comparing(Tuple2::v2);
        roundedRocks = slide(roundedRocks, Direction.NORTH, orderByY);
        roundedRocks = slide(roundedRocks, Direction.WEST, orderByX);
        roundedRocks = slide(roundedRocks, Direction.SOUTH, orderByY.reversed());
        roundedRocks = slide(roundedRocks, Direction.EAST, orderByX.reversed());
        return roundedRocks;
    }

    private Set<Tuple2<Integer, Integer>> slide(Set<Tuple2<Integer, Integer>> roundedRocks, Direction direction, Comparator<Tuple2<Integer, Integer>> orderToMoveRocks) {
        Set<Tuple2<Integer, Integer>> newRoundedRocks;
        newRoundedRocks = new HashSet<>();
        List<Tuple2<Integer, Integer>> sortedRocks = roundedRocks.stream().sorted(orderToMoveRocks).toList();

        for (Tuple2<Integer, Integer> sortedRock : sortedRocks) {
            Tuple2<Integer, Integer> newRock = sortedRock;
            while (true) {
                Tuple2<Integer, Integer> newRockPos = direction.move(newRock, 1);
                if (cubeShapedRocks.contains(newRockPos) || newRoundedRocks.contains(newRockPos)
                        || !Util.isInRectangle(newRockPos, Tuple.tuple(0, 0), Tuple.tuple(width - 1, height - 1))) {
                    newRoundedRocks.add(newRock);
                    break;
                }
                newRock = newRockPos;
            }
        }
        return newRoundedRocks;
    }

    private long getScore(Set<Tuple2<Integer, Integer>> roundedRocks) {
        long result = 0;
        for (Tuple2<Integer, Integer> rock : roundedRocks) {
            result += height - rock.v2;
        }
        return result;
    }

    private Set<Tuple2<Integer, Integer>> parseInputAndGetRoundedRocks(List<String> input) {
        Set<Tuple2<Integer, Integer>> roundedRocks = Util.buildConwaySet(input, "O");
        cubeShapedRocks = Util.buildConwaySet(input, "#");
        height = input.size();
        width = input.getFirst().length();
        return roundedRocks;
    }

}
