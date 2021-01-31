package de.beachboys.aoc2017;

import de.beachboys.Day;
import de.beachboys.DirectionHexFlatTop;
import de.beachboys.Util;
import org.javatuples.Triplet;

import java.util.List;

public class Day11 extends Day {

    private int maxDistance;

    public Object part1(List<String> input) {
        Triplet<Integer, Integer, Integer> finalPosition = runLogic(input);
        return getDistanceToStart(finalPosition);
    }

    public Object part2(List<String> input) {
        runLogic(input);
        return maxDistance;
    }

    private Triplet<Integer, Integer, Integer> runLogic(List<String> input) {
        List<String> directions = Util.parseCsv(input.get(0));
        Triplet<Integer, Integer, Integer> currentPosition = Triplet.with(0, 0, 0);
        maxDistance = 0;
        for (String directionString : directions) {
            currentPosition = DirectionHexFlatTop.fromString(directionString).move(currentPosition, 1);
            maxDistance = Math.max(maxDistance, getDistanceToStart(currentPosition));
        }
        return currentPosition;
    }

    private int getDistanceToStart(Triplet<Integer, Integer, Integer> position) {
        return (Math.abs(position.getValue0()) + Math.abs(position.getValue1()) + Math.abs(position.getValue2())) / 2;
    }

}
