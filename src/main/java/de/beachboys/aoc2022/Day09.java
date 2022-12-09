package de.beachboys.aoc2022;

import de.beachboys.Day;
import de.beachboys.Direction;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day09 extends Day {

    public Object part1(List<String> input) {
        return runLogic(input, 2);
    }

    public Object part2(List<String> input) {
        return runLogic(input, 10);
    }

    private static int runLogic(List<String> input, int numberOfKnots) {
        List<Pair<Integer, Integer>> knots = new ArrayList<>();
        for (int i = 0; i < numberOfKnots; i++) {
            knots.add(Pair.with(0, 0));
        }
        Set<Pair<Integer, Integer>> visitedPositionsOfTail = new HashSet<>();
        visitedPositionsOfTail.add(knots.get(knots.size() - 1));

        for (String line : input) {
            Direction direction = Direction.fromString(line.substring(0, 1));
            int steps = Integer.parseInt(line.substring(2));
            for (int i = 0; i < steps; i++) {
                knots.set(0, direction.move(knots.get(0), 1));

                for (int j = 1; j < knots.size(); j++) {
                    knots.set(j, getNewFollowingKnot(knots.get(j), knots.get(j - 1)));
                }

                visitedPositionsOfTail.add(knots.get(knots.size() - 1));
            }
        }
        return visitedPositionsOfTail.size();
    }

    private static Pair<Integer, Integer> getNewFollowingKnot(Pair<Integer, Integer> oldFollowingKnot, Pair<Integer, Integer> newHead) {
        int xDiff = newHead.getValue0() - oldFollowingKnot.getValue0();
        int yDiff = newHead.getValue1() - oldFollowingKnot.getValue1();

        int newX = getNewValue(xDiff, yDiff, oldFollowingKnot.getValue0(), newHead.getValue0());
        int newY = getNewValue(yDiff, xDiff, oldFollowingKnot.getValue1(), newHead.getValue1());

        return Pair.with(newX, newY);
    }

    private static int getNewValue(int diffInThisDimension, int diffInOtherDimension, int oldValue, int headValue) {
        int newValue;
        if (diffInThisDimension > 1) {
            newValue = oldValue + 1;
        } else if (diffInThisDimension < -1) {
            newValue = oldValue - 1;
        } else if (diffInThisDimension != 0 && (diffInOtherDimension < -1 || diffInOtherDimension > 1)) {
            newValue = headValue;
        } else {
            newValue = oldValue;
        }
        return newValue;
    }

}
