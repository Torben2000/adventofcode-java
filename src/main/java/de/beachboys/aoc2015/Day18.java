package de.beachboys.aoc2015;

import de.beachboys.Day;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day18 extends Day {

    private int width;
    private int height;

    private Set<Tuple2<Integer, Integer>> conwaySet;

    public Object part1(List<String> input) {
        return runLogic(input, () -> {});
    }

    public Object part2(List<String> input) {
        return runLogic(input, this::turnOnCorners);
    }

    private int runLogic(List<String> input, Runnable conwaySetManipulator) {
        int numberOfSteps = Util.getIntValueFromUser("Number of steps", 100, io);
        width = input.get(0).length();
        height = input.size();
        conwaySet = Util.buildConwaySet(input, "#");
        conwaySetManipulator.run();

        for (int i = 0; i < numberOfSteps; i++) {
            Set<Tuple2<Integer, Integer>> newSet = new HashSet<>();
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int numNeighbors = getActiveNeighborCount(x, y);
                    if (numNeighbors == 3 || numNeighbors == 2 && conwaySet.contains(Tuple.tuple(x, y))) {
                        newSet.add(Tuple.tuple(x, y));
                    }
                }
            }
            conwaySet = newSet;
            conwaySetManipulator.run();
        }

        return conwaySet.size();
    }

    private int getActiveNeighborCount(int x, int y) {
        int count = 0;
        for (int neighborX = x - 1; neighborX <= x + 1; neighborX++) {
            for (int neighborY = y - 1; neighborY <= y + 1; neighborY++) {
                if (neighborX != x || neighborY != y) {
                    if (conwaySet.contains(Tuple.tuple(neighborX, neighborY))) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    private void turnOnCorners() {
        conwaySet.add(Tuple.tuple(0, 0));
        conwaySet.add(Tuple.tuple(0, height - 1));
        conwaySet.add(Tuple.tuple(width - 1, 0));
        conwaySet.add(Tuple.tuple(width - 1, height - 1));
    }

}
