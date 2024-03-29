package de.beachboys.aoc2021;

import de.beachboys.Day;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day20 extends Day {


    private String imageEnhancementAlgorithm;
    private int initialMaxX;
    private int initialMaxY;

    public Object part1(List<String> input) {
        return runLogic(input, 1);
    }

    public Object part2(List<String> input) {
        return runLogic(input, 25);
    }

    private int runLogic(List<String> input, int doubleExecutions) {
        imageEnhancementAlgorithm = input.get(0);
        initialMaxX = input.get(3).length();
        initialMaxY = input.size() - 2;

        Set<Tuple2<Integer, Integer>> conwaySet = Util.buildConwaySet(input.subList(2, input.size()), "#");
        for (int i = 1; i <= doubleExecutions; i++) {
            final int secondExecutionRound = i * 2;
            conwaySet = runConwayRound(conwaySet, secondExecutionRound - 1);
            conwaySet = runConwayRound(conwaySet, secondExecutionRound);
            conwaySet = conwaySet.stream().filter(p -> p.v1 >= -secondExecutionRound
                            && p.v2 >= -secondExecutionRound
                            && p.v1 <= initialMaxX + secondExecutionRound
                            && p.v2 <= initialMaxY + secondExecutionRound)
                    .collect(Collectors.toSet());
        }
        return conwaySet.size();
    }

    private Set<Tuple2<Integer, Integer>> runConwayRound(Set<Tuple2<Integer, Integer>> conwaySet, int round) {
        Set<Tuple2<Integer, Integer>> newSet = new HashSet<>();
        int buffer = 2;
        for (int x = - buffer - round; x <= initialMaxX + buffer + round; x++) {
            for (int y = - buffer - round; y <= initialMaxY + buffer + round; y++) {
                Tuple2<Integer, Integer> pos = Tuple.tuple(x, y);
                int number = 0;
                for (int yOffset = -1; yOffset <= 1; yOffset++) {
                    for (int xOffset = -1; xOffset <= 1; xOffset++) {
                        number *= 2;
                        number += conwaySet.contains((Tuple.tuple(x + xOffset, y + yOffset))) ? 1 : 0;
                    }
                }
                if (imageEnhancementAlgorithm.charAt(number) == '#') {
                    newSet.add(pos);
                }
            }
        }
        return newSet;
    }

}
