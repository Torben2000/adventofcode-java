package de.beachboys.aoc2024;

import de.beachboys.Day;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

public class Day08 extends Day {

    Map<Tuple2<Integer, Integer>, String> map;
    Set<String> frequencies;
    Tuple2<Integer, Integer> bottomRightCorner;

    public Object part1(List<String> input) {
        return runLogic(input, this::getAntinodePositionsForAntennasPart1);
    }

    public Object part2(List<String> input) {
        return runLogic(input, this::getAntinodePositionsForAntennasPart2);
    }

    private int runLogic(List<String> input, BiFunction<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>, Set<Tuple2<Integer, Integer>>> antinodePositionCreator) {
        parseInput(input);
        Set<Tuple2<Integer, Integer>> antinodePositions = new HashSet<>();
        for (String frequency : frequencies) {
            if (!".".equals(frequency)) {
                List<Tuple2<Integer, Integer>> antennaPositions = map.entrySet().stream().filter(e -> frequency.equals(e.getValue())).map(Map.Entry::getKey).toList();
                for (int i = 0; i < antennaPositions.size(); i++) {
                    for (int j = i + 1; j < antennaPositions.size(); j++) {
                        antinodePositions.addAll(antinodePositionCreator.apply(antennaPositions.get(i), antennaPositions.get(j)));
                    }
                }
            }
        }
        return antinodePositions.size();
    }

    private Set<Tuple2<Integer, Integer>> getAntinodePositionsForAntennasPart1(Tuple2<Integer, Integer> antenna1, Tuple2<Integer, Integer> antenna2) {
        Set<Tuple2<Integer, Integer>> antinodePositions = new HashSet<>();

        Tuple2<Integer, Integer> possibleAntinodePosition = Tuple.tuple(antenna1.v1 + (antenna1.v1 - antenna2.v1), antenna1.v2 + (antenna1.v2 - antenna2.v2));
        if (isOnMap(possibleAntinodePosition)) {
            antinodePositions.add(possibleAntinodePosition);
        }

        possibleAntinodePosition = Tuple.tuple(antenna2.v1 + (antenna2.v1 - antenna1.v1), antenna2.v2 + (antenna2.v2 - antenna1.v2));
        if (isOnMap(possibleAntinodePosition)) {
            antinodePositions.add(possibleAntinodePosition);
        }

        return antinodePositions;
    }

    private Set<Tuple2<Integer, Integer>> getAntinodePositionsForAntennasPart2(Tuple2<Integer, Integer> antenna1, Tuple2<Integer, Integer> antenna2) {
        int normalizer = (int) Util.greatestCommonDivisor(Math.abs(antenna1.v1 - antenna2.v1), Math.abs(antenna1.v2 - antenna2.v2));
        int stepX = (antenna1.v1 - antenna2.v1) / normalizer;
        int stepY = (antenna1.v2 - antenna2.v2) / normalizer;

        Set<Tuple2<Integer, Integer>> antinodePositions = new HashSet<>();

        Tuple2<Integer, Integer> possibleAntinodePosition = antenna1;
        while (isOnMap(possibleAntinodePosition)) {
            antinodePositions.add(possibleAntinodePosition);
            possibleAntinodePosition = Tuple.tuple(possibleAntinodePosition.v1 + stepX, possibleAntinodePosition.v2 + stepY);
        }

        possibleAntinodePosition = antenna1;
        while (isOnMap(possibleAntinodePosition)) {
            antinodePositions.add(possibleAntinodePosition);
            possibleAntinodePosition = Tuple.tuple(possibleAntinodePosition.v1 - stepX, possibleAntinodePosition.v2 - stepY);
        }

        return antinodePositions;
    }

    private boolean isOnMap(Tuple2<Integer, Integer> position) {
        return Util.isInRectangle(position, Tuple.tuple(0, 0), bottomRightCorner);
    }

    private void parseInput(List<String> input) {
        map = Util.buildImageMap(input);
        frequencies = new HashSet<>(map.values());
        bottomRightCorner = Tuple.tuple(input.getFirst().length() - 1, input.size() - 1);
    }

}
