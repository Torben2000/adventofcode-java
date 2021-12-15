package de.beachboys.aoc2021;

import de.beachboys.Day;
import de.beachboys.Direction;
import de.beachboys.Util;
import org.javatuples.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class Day15 extends Day {

    private final Map<Pair<Integer, Integer>, Integer> riskMap = new HashMap<>();
    private Pair<Integer, Integer> goal;
    private final Map<Pair<Integer, Integer>, Integer> minRiskMap = new HashMap<>();


    public Object part1(List<String> input) {
        return runLogic(input, 1);
    }

    public Object part2(List<String> input) {
        return runLogic(input, 5);
    }

    private Integer runLogic(List<String> input, int mapRepetitions) {
        parseInputToRiskMapAndGoal(input, mapRepetitions);

        initiallyFillMinRiskMap();

        Set<Pair<Integer, Integer>> positionsToCheckAgain = new HashSet<>(minRiskMap.keySet());

        while (!positionsToCheckAgain.isEmpty()) {
            Pair<Integer, Integer> pos = positionsToCheckAgain.stream().findFirst().orElseThrow();
            positionsToCheckAgain.remove(pos);
            boolean change = false;
            int minRiskNeighbors = minRiskMap.get(pos) - riskMap.get(pos);
            Set<Pair<Integer, Integer>> neighbors = Direction.getDirectNeighbors(pos).stream().filter(p ->  p.getValue0() >= 0 && p.getValue0() <= goal.getValue0() && p.getValue1() >= 0 && p.getValue1() <= goal.getValue1()).collect(Collectors.toSet());
            for (Pair<Integer, Integer> neighbor : neighbors) {
                Integer minRiskNeighbor = minRiskMap.get(neighbor);
                if (minRiskNeighbor < minRiskNeighbors) {
                    change = true;
                    minRiskNeighbors = minRiskNeighbor;
                }
            }
            if (change) {
                minRiskMap.put(pos, riskMap.get(pos) + minRiskNeighbors);
                positionsToCheckAgain.addAll(neighbors);
            }
        }
        return minRiskMap.get(goal);
    }

    private void initiallyFillMinRiskMap() {
        minRiskMap.clear();
        for (int i = 0; i <= goal.getValue0(); i++) {
            for (int j = 0; j <= goal.getValue1(); j++) {
                Pair<Integer, Integer> pos = Pair.with(i, j);
                if (i == 0 && j == 0) {
                    minRiskMap.put(pos, 0);
                } else {
                    minRiskMap.put(pos, riskMap.get(pos) + Math.min(minRiskMap.getOrDefault(Pair.with(i, j - 1), Integer.MAX_VALUE), minRiskMap.getOrDefault(Pair.with(i - 1, j), Integer.MAX_VALUE)));
                }
            }
        }
    }

    private void parseInputToRiskMapAndGoal(List<String> input, int mapRepetitions) {
        int inputWidth = input.get(0).length();
        int inputHeight = input.size();
        riskMap.clear();
        Map<Pair<Integer, Integer>, String> tempMap = Util.buildImageMap(input);
        tempMap.forEach((k, v) -> riskMap.put(k, Integer.valueOf(v)));
        for (int i = 0; i < inputWidth; i++) {
            for (int j = 0; j < inputHeight; j++) {
                for (int k = 0; k < mapRepetitions; k++) {
                    for (int l = 0; l < mapRepetitions; l++) {
                        int risk = riskMap.get(Pair.with(i, j)) + k + l;
                        while (risk > 9) {
                            risk = risk - 9;
                        }
                        riskMap.put(Pair.with(i + k * inputWidth, j + l * inputHeight), risk);
                    }
                }
            }
        }
        goal = Pair.with(inputWidth * mapRepetitions - 1, inputHeight * mapRepetitions - 1);
    }

}
