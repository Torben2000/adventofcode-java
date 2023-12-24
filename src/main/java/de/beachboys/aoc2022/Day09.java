package de.beachboys.aoc2022;

import de.beachboys.Day;
import de.beachboys.Direction;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

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
        List<Tuple2<Integer, Integer>> knots = new ArrayList<>();
        for (int i = 0; i < numberOfKnots; i++) {
            knots.add(Tuple.tuple(0, 0));
        }
        Set<Tuple2<Integer, Integer>> visitedPositionsOfTail = new HashSet<>();
        visitedPositionsOfTail.add(knots.getLast());

        for (String line : input) {
            Direction direction = Direction.fromString(line.substring(0, 1));
            int steps = Integer.parseInt(line.substring(2));
            for (int i = 0; i < steps; i++) {
                knots.set(0, direction.move(knots.getFirst(), 1));

                for (int j = 1; j < knots.size(); j++) {
                    knots.set(j, getNewFollowingKnot(knots.get(j), knots.get(j - 1)));
                }

                visitedPositionsOfTail.add(knots.getLast());
            }
        }
        return visitedPositionsOfTail.size();
    }

    private static Tuple2<Integer, Integer> getNewFollowingKnot(Tuple2<Integer, Integer> oldFollowingKnot, Tuple2<Integer, Integer> newHead) {
        int xDiff = newHead.v1 - oldFollowingKnot.v1;
        int yDiff = newHead.v2 - oldFollowingKnot.v2;

        int newX = getNewValue(xDiff, yDiff, oldFollowingKnot.v1, newHead.v1);
        int newY = getNewValue(yDiff, xDiff, oldFollowingKnot.v2, newHead.v2);

        return Tuple.tuple(newX, newY);
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
