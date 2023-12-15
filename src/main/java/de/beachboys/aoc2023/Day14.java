package de.beachboys.aoc2023;

import de.beachboys.Day;
import de.beachboys.Direction;
import de.beachboys.Util;
import org.javatuples.Pair;

import java.util.*;

public class Day14 extends Day {

    private Set<Pair<Integer, Integer>> cubeShapedRocks;
    private int height;
    private int width;

    public Object part1(List<String> input) {
        Set<Pair<Integer, Integer>> roundedRocks = parseInputAndGetRoundedRocks(input);
        return getScore(slide(roundedRocks, Direction.NORTH, Comparator.comparing(Pair::getValue1)));
    }

    public Object part2(List<String> input) {
        Set<Pair<Integer, Integer>> roundedRocks = parseInputAndGetRoundedRocks(input);
        return getScore(Util.manipulateStateMultipleTimesOptimized(1000000000, roundedRocks, this::rotate));
    }

    private Set<Pair<Integer, Integer>> rotate(Set<Pair<Integer, Integer>> roundedRocks) {
        Comparator<Pair<Integer, Integer>> orderByX = Comparator.comparing(Pair::getValue0);
        Comparator<Pair<Integer, Integer>> orderByY = Comparator.comparing(Pair::getValue1);
        roundedRocks = slide(roundedRocks, Direction.NORTH, orderByY);
        roundedRocks = slide(roundedRocks, Direction.WEST, orderByX);
        roundedRocks = slide(roundedRocks, Direction.SOUTH, orderByY.reversed());
        roundedRocks = slide(roundedRocks, Direction.EAST, orderByX.reversed());
        return roundedRocks;
    }

    private Set<Pair<Integer, Integer>> slide(Set<Pair<Integer, Integer>> roundedRocks, Direction direction, Comparator<Pair<Integer, Integer>> orderToMoveRocks) {
        Set<Pair<Integer, Integer>> newRoundedRocks;
        newRoundedRocks = new HashSet<>();
        List<Pair<Integer, Integer>> sortedRocks = roundedRocks.stream().sorted(orderToMoveRocks).toList();

        for (Pair<Integer, Integer> sortedRock : sortedRocks) {
            Pair<Integer, Integer> newRock = sortedRock;
            while (true) {
                Pair<Integer, Integer> newRockPos = direction.move(newRock, 1);
                if (cubeShapedRocks.contains(newRockPos) || newRoundedRocks.contains(newRockPos)
                        || !Util.isInRectangle(newRockPos, Pair.with(0, 0), Pair.with(width - 1, height - 1))) {
                    newRoundedRocks.add(newRock);
                    break;
                }
                newRock = newRockPos;
            }
        }
        return newRoundedRocks;
    }

    private long getScore(Set<Pair<Integer, Integer>> roundedRocks) {
        long result = 0;
        for (Pair<Integer, Integer> rock : roundedRocks) {
            result += height - rock.getValue1();
        }
        return result;
    }

    private Set<Pair<Integer, Integer>> parseInputAndGetRoundedRocks(List<String> input) {
        Set<Pair<Integer, Integer>> roundedRocks = Util.buildConwaySet(input, "O");
        cubeShapedRocks = Util.buildConwaySet(input, "#");
        height = input.size();
        width = input.getFirst().length();
        return roundedRocks;
    }

}
