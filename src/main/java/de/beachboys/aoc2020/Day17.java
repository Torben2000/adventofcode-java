package de.beachboys.aoc2020;

import de.beachboys.Day;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;
import org.jooq.lambda.tuple.Tuple3;
import org.jooq.lambda.tuple.Tuple4;

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

    private <T extends Tuple> long runLogic(List<String> input, Function<Tuple2<Integer, Integer>, T> convertPositionFromTwoDimensions, Function<Map<T, String>, Map<T, String>> buildMapWithSurroundingCubes, BiFunction<Map<T, String>, T, Integer> getSurroundingActiveCubes) {
        Map<Tuple2<Integer, Integer>, String> twoDimensionalMap = Util.buildImageMap(input);
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

    private Tuple3<Integer, Integer, Integer> convertToThreeDimensions(Tuple2<Integer, Integer> position) {
        return position.concat(0);
    }

    private Tuple4<Integer, Integer, Integer, Integer> convertToFourDimensions(Tuple2<Integer, Integer> position) {
        return position.concat(0).concat(0);
    }

    private Map<Tuple3<Integer, Integer, Integer>, String> addSurroundingCubesThreeDimensions(Map<Tuple3<Integer, Integer, Integer>, String> map) {
        Map<Tuple3<Integer, Integer, Integer>, String> newMap = new HashMap<>(map);
        for (Tuple3<Integer, Integer, Integer> position : map.keySet()) {
            for (int xOffset = -1; xOffset <= 1; xOffset++) {
                for (int yOffset = -1; yOffset <= 1; yOffset++) {
                    for (int zOffset = -1; zOffset <= 1; zOffset++) {
                        Tuple3<Integer, Integer, Integer> offset = Tuple.tuple(xOffset, yOffset, zOffset);
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

    private Map<Tuple4<Integer, Integer, Integer, Integer>, String> addSurroundingCubesFourDimensions(Map<Tuple4<Integer, Integer, Integer, Integer>, String> map) {
        Map<Tuple4<Integer, Integer, Integer, Integer>, String> newMap = new HashMap<>(map);
        for (Tuple4<Integer, Integer, Integer, Integer> position : map.keySet()) {
            for (int xOffset = -1; xOffset <= 1; xOffset++) {
                for (int yOffset = -1; yOffset <= 1; yOffset++) {
                    for (int zOffset = -1; zOffset <= 1; zOffset++) {
                        for (int wOffset = -1; wOffset <= 1; wOffset++) {
                            Tuple4<Integer, Integer, Integer, Integer> offset = Tuple.tuple(xOffset, yOffset, zOffset, wOffset);
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

    private int getSurroundingActiveCubesThreeDimensions(Map<Tuple3<Integer, Integer, Integer>, String> map, Tuple3<Integer, Integer, Integer> position) {
        int activeCounter = 0;
        for (int xOffset = -1; xOffset <= 1; xOffset++) {
            for (int yOffset = -1; yOffset <= 1; yOffset++) {
                for (int zOffset = -1; zOffset <= 1; zOffset++) {
                    Tuple3<Integer, Integer, Integer> offset = Tuple.tuple(xOffset, yOffset, zOffset);
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

    private int getSurroundingActiveCubesFourDimensions(Map<Tuple4<Integer, Integer, Integer, Integer>, String> map, Tuple4<Integer, Integer, Integer, Integer> position) {
        int occupiedCounter = 0;
        for (int xOffset = -1; xOffset <= 1; xOffset++) {
            for (int yOffset = -1; yOffset <= 1; yOffset++) {
                for (int zOffset = -1; zOffset <= 1; zOffset++) {
                    for (int wOffset = -1; wOffset <= 1; wOffset++) {
                        Tuple4<Integer, Integer, Integer, Integer> offset = Tuple.tuple(xOffset, yOffset, zOffset, wOffset);
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

    private Tuple4<Integer, Integer, Integer, Integer> getAdjacentPositionFourDimensions(Tuple4<Integer, Integer, Integer, Integer> position, Tuple4<Integer, Integer, Integer, Integer> offset) {
        return Tuple.tuple(position.v1 + offset.v1, position.v2 + offset.v2, position.v3 + offset.v3, position.v4 + offset.v4);
    }

    private Tuple3<Integer, Integer, Integer> getAdjacentPositionThreeDimensions(Tuple3<Integer, Integer, Integer> position, Tuple3<Integer, Integer, Integer> offset) {
        return Tuple.tuple(position.v1 + offset.v1, position.v2 + offset.v2, position.v3 + offset.v3);
    }

    private boolean isActiveCube(String value) {
        return ACTIVE_CUBE.equals(value);
    }

    private boolean isInactiveCube(String value) {
        return INACTIVE_CUBE.equals(value);
    }


}
