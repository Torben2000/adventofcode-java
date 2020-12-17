package de.beachboys.aoc2020;

import de.beachboys.Day;
import de.beachboys.Util;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Triplet;
import org.javatuples.Tuple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day17 extends Day {

    public static final String ACTIVE_CUBE = "#";
    public static final String INACTIVE_CUBE = ".";

    public Object part1(List<String> input) {
        return runLogic(input, this::convertToThreeDimensions, this::addSurroundingCubesThreeDimensions, this::getSurroundingActiveCubesThreeDimensions);
    }

    public Object part2(List<String> input) {
        return runLogic(input, this::convertToFourDimensions, this::addSurroundingCubesFourDimensions, this::getSurroundingActiveCubesFourDimensions);
    }

    private <T extends Tuple> long runLogic(List<String> input, Function<Pair<Integer, Integer>, T> convertPositionFromTwoDimensions, Function<Map<T, String>, Map<T, String>> buildMapWithSurroundingCubes, BiFunction<Map<T, String>, T, Integer> getSurroundingActiveCubes) {
        Map<Pair<Integer, Integer>, String> twoDimensionalMap = Util.buildImageMap(input);
        Map<T, String> map = twoDimensionalMap.entrySet().stream().collect(Collectors.toMap(e -> convertPositionFromTwoDimensions.apply(e.getKey()), Map.Entry::getValue));
        for (int i = 0; i < 6; i++) {
            map = buildMapWithSurroundingCubes.apply(map);
            map = runCycle(map, getSurroundingActiveCubes);
        }
        return map.values().stream().filter(this::isActiveCube).count();
    }

    private <T extends Tuple> Map<T, String> runCycle(Map<T, String> map, BiFunction<Map<T, String>, T, Integer> getSurroundingActiveCubes) {
        Map<T, String> newMap = new HashMap<>(map);
        for (T position : map.keySet()) {
            String value = map.get(position);
            int activeCount = getSurroundingActiveCubes.apply(map, position);
            if (isInactiveCube(value) && activeCount == 3) {
                newMap.put(position, ACTIVE_CUBE);
            } else if (isActiveCube(value) && !(activeCount == 2 || activeCount == 3)) {
                newMap.put(position, INACTIVE_CUBE);
            }
        }
        return newMap;
    }

    private Triplet<Integer, Integer, Integer> convertToThreeDimensions(Pair<Integer, Integer> position) {
        return Triplet.with(position.getValue0(), position.getValue1(), 0);
    }

    private Quartet<Integer, Integer, Integer, Integer> convertToFourDimensions(Pair<Integer, Integer> position) {
        return Quartet.with(position.getValue0(), position.getValue1(), 0, 0);
    }

    private Map<Triplet<Integer, Integer, Integer>, String> addSurroundingCubesThreeDimensions(Map<Triplet<Integer, Integer, Integer>, String> map) {
        Map<Triplet<Integer, Integer, Integer>, String> newMap = new HashMap<>(map);
        for (Triplet<Integer, Integer, Integer> position : map.keySet()) {
            for (int xOffset = -1; xOffset <= 1; xOffset++) {
                for (int yOffset = -1; yOffset <= 1; yOffset++) {
                    for (int zOffset = -1; zOffset <= 1; zOffset++) {
                        Triplet<Integer, Integer, Integer> offset = Triplet.with(xOffset, yOffset, zOffset);
                        addAdjacentPositionToNewMapIfMissingFromMap(map, newMap, position, offset, this::getAdjacentPositionThreeDimensions);
                    }
                }
            }
        }
        return newMap;
    }

    private <T extends Tuple> void addAdjacentPositionToNewMapIfMissingFromMap(Map<T, String> map, Map<T, String> newMap, T position, T offset, BiFunction<T, T, T> getAdjacentPosition) {
        if (notAllValuesZero(offset)) {
            T adjacentPosition = getAdjacentPosition.apply(position, offset);
            if (!map.containsKey(adjacentPosition)) {
                newMap.putIfAbsent(adjacentPosition, INACTIVE_CUBE);
            }
        }
    }

    private Map<Quartet<Integer, Integer, Integer, Integer>, String> addSurroundingCubesFourDimensions(Map<Quartet<Integer, Integer, Integer, Integer>, String> map) {
        Map<Quartet<Integer, Integer, Integer, Integer>, String> newMap = new HashMap<>(map);
        for (Quartet<Integer, Integer, Integer, Integer> position : map.keySet()) {
            for (int xOffset = -1; xOffset <= 1; xOffset++) {
                for (int yOffset = -1; yOffset <= 1; yOffset++) {
                    for (int zOffset = -1; zOffset <= 1; zOffset++) {
                        for (int wOffset = -1; wOffset <= 1; wOffset++) {
                            Quartet<Integer, Integer, Integer, Integer> offset = Quartet.with(xOffset, yOffset, zOffset, wOffset);
                            addAdjacentPositionToNewMapIfMissingFromMap(map, newMap, position, offset, this::getAdjacentPositionFourDimensions);
                        }
                    }
                }
            }
        }
        return newMap;
    }

    private boolean notAllValuesZero(Tuple tuple) {
        return !tuple.toList().stream().map(o -> (int) o == 0).reduce((a, b) -> a && b).orElseThrow();
    }

    private int getSurroundingActiveCubesThreeDimensions(Map<Triplet<Integer, Integer, Integer>, String> map, Triplet<Integer, Integer, Integer> position) {
        int activeCounter = 0;
        for (int xOffset = -1; xOffset <= 1; xOffset++) {
            for (int yOffset = -1; yOffset <= 1; yOffset++) {
                for (int zOffset = -1; zOffset <= 1; zOffset++) {
                    Triplet<Integer, Integer, Integer> offset = Triplet.with(xOffset, yOffset, zOffset);
                    if (notAllValuesZero(offset)) {
                        if (hasAdjacentActiveCube(map, position, offset, this::getAdjacentPositionThreeDimensions)) {
                            activeCounter++;
                        }
                    }
                }
            }
        }
        return activeCounter;
    }

    private int getSurroundingActiveCubesFourDimensions(Map<Quartet<Integer, Integer, Integer, Integer>, String> map, Quartet<Integer, Integer, Integer, Integer> position) {
        int occupiedCounter = 0;
        for (int xOffset = -1; xOffset <= 1; xOffset++) {
            for (int yOffset = -1; yOffset <= 1; yOffset++) {
                for (int zOffset = -1; zOffset <= 1; zOffset++) {
                    for (int wOffset = -1; wOffset <= 1; wOffset++) {
                        Quartet<Integer, Integer, Integer, Integer> offset = Quartet.with(xOffset, yOffset, zOffset, wOffset);
                        if (notAllValuesZero(offset)) {
                            if (hasAdjacentActiveCube(map, position, offset, this::getAdjacentPositionFourDimensions)) {
                                occupiedCounter++;
                            }
                        }
                    }
                }
            }
        }
        return occupiedCounter;
    }

    private <T extends Tuple> boolean hasAdjacentActiveCube(Map<T, String> map, T position, T offset, BiFunction<T, T, T> getAdjacentPosition) {
        T adjacentPosition = getAdjacentPosition.apply(position, offset);
        String value = map.get(adjacentPosition);
        return isActiveCube(value);
    }

    private Quartet<Integer, Integer, Integer, Integer> getAdjacentPositionFourDimensions(Quartet<Integer, Integer, Integer, Integer> position, Quartet<Integer, Integer, Integer, Integer> offset) {
        return Quartet.with(position.getValue0() + offset.getValue0(), position.getValue1() + offset.getValue1(), position.getValue2() + offset.getValue2(), position.getValue3() + offset.getValue3());
    }

    private Triplet<Integer, Integer, Integer> getAdjacentPositionThreeDimensions(Triplet<Integer, Integer, Integer> position, Triplet<Integer, Integer, Integer> offset) {
        return Triplet.with(position.getValue0() + offset.getValue0(), position.getValue1() + offset.getValue1(), position.getValue2() + offset.getValue2());
    }

    private boolean isActiveCube(String value) {
        return ACTIVE_CUBE.equals(value);
    }

    private boolean isInactiveCube(String value) {
        return INACTIVE_CUBE.equals(value);
    }


}
