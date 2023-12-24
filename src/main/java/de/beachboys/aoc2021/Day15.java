package de.beachboys.aoc2021;

import de.beachboys.Day;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day15 extends Day {

    private final Map<Tuple2<Integer, Integer>, Integer> riskMap = new HashMap<>();
    private Tuple2<Integer, Integer> goal;

    public Object part1(List<String> input) {
        return runLogic(input, 1);
    }

    public Object part2(List<String> input) {
        return runLogic(input, 5);
    }

    private Integer runLogic(List<String> input, int mapRepetitions) {
        parseInputToRiskMapAndGoal(input, mapRepetitions);

        Tuple2<Integer, Integer> start = Tuple.tuple(0, 0);
        return Util.getShortestPath(Set.of(start), Set.of(goal),
                (position, neighbor) -> Util.isInRectangle(neighbor, start, goal),
                (distance, position, neighbor) -> distance + riskMap.get(neighbor));
    }

    private void parseInputToRiskMapAndGoal(List<String> input, int mapRepetitions) {
        int inputWidth = input.get(0).length();
        int inputHeight = input.size();
        riskMap.clear();
        Map<Tuple2<Integer, Integer>, String> tempMap = Util.buildImageMap(input);
        tempMap.forEach((k, v) -> riskMap.put(k, Integer.valueOf(v)));
        for (int i = 0; i < inputWidth; i++) {
            for (int j = 0; j < inputHeight; j++) {
                for (int k = 0; k < mapRepetitions; k++) {
                    for (int l = 0; l < mapRepetitions; l++) {
                        int risk = riskMap.get(Tuple.tuple(i, j)) + k + l;
                        while (risk > 9) {
                            risk = risk - 9;
                        }
                        riskMap.put(Tuple.tuple(i + k * inputWidth, j + l * inputHeight), risk);
                    }
                }
            }
        }
        goal = Tuple.tuple(inputWidth * mapRepetitions - 1, inputHeight * mapRepetitions - 1);
    }

}
