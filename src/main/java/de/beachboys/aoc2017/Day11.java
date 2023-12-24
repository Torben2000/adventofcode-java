package de.beachboys.aoc2017;

import de.beachboys.Day;
import de.beachboys.DirectionHexFlatTop;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple3;

import java.util.List;

public class Day11 extends Day {

    private int maxDistance;

    public Object part1(List<String> input) {
        Tuple3<Integer, Integer, Integer> finalPosition = runLogic(input);
        return getDistanceToStart(finalPosition);
    }

    public Object part2(List<String> input) {
        runLogic(input);
        return maxDistance;
    }

    private Tuple3<Integer, Integer, Integer> runLogic(List<String> input) {
        List<String> directions = Util.parseCsv(input.getFirst());
        Tuple3<Integer, Integer, Integer> currentPosition = Tuple.tuple(0, 0, 0);
        maxDistance = 0;
        for (String directionString : directions) {
            currentPosition = DirectionHexFlatTop.fromString(directionString).move(currentPosition, 1);
            maxDistance = Math.max(maxDistance, getDistanceToStart(currentPosition));
        }
        return currentPosition;
    }

    private int getDistanceToStart(Tuple3<Integer, Integer, Integer> position) {
        return (Math.abs(position.v1) + Math.abs(position.v2) + Math.abs(position.v3)) / 2;
    }

}
