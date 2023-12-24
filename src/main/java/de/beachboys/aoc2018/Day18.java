package de.beachboys.aoc2018;

import de.beachboys.Day;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class Day18 extends Day {

    public static final String TREE = "|";
    public static final String LUMBERYARD = "#";
    public static final String OPEN = ".";

    public Object part1(List<String> input) {
        return runLogic(input, 10);
    }

    public Object part2(List<String> input) {
        return runLogic(input, 1000000000);
    }

    private long runLogic(List<String> input, int minutes) {
        Map<Tuple2<Integer, Integer>, String> map = Util.buildImageMap(input);
        map = Util.manipulateStateMultipleTimesOptimized(minutes, map, this::transformAcres);
        return map.values().stream().filter(this::isWooded).count() * map.values().stream().filter(this::isLumberyard).count();
    }

    private Map<Tuple2<Integer, Integer>, String> transformAcres(Map<Tuple2<Integer, Integer>, String> map) {
        Map<Tuple2<Integer, Integer>, String> newMap = new HashMap<>();
        for (Map.Entry<Tuple2<Integer, Integer>, String> mapEntry : map.entrySet()) {
            Tuple2<Integer, Integer> position = mapEntry.getKey();
            String mapValue = mapEntry.getValue();
            if (isOpen(mapValue) && countNeighbors(map, position, this::isWooded) >= 3) {
                newMap.put(position, TREE);
            } else if (isWooded(mapValue) && countNeighbors(map, position, this::isLumberyard) >= 3) {
                newMap.put(position, LUMBERYARD);
            } else if (isLumberyard(mapValue) && (countNeighbors(map, position, this::isLumberyard) == 0 || countNeighbors(map, position, this::isWooded) == 0)) {
                newMap.put(position, OPEN);
            } else {
                newMap.put(position, mapValue);
            }
        }
        return newMap;
    }

    private int countNeighbors(Map<Tuple2<Integer, Integer>, String> map, Tuple2<Integer, Integer> position, Predicate<String> neighborFilter) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i != 0 || j != 0) {
                    if (neighborFilter.test(map.get(Tuple.tuple(position.v1 + i, position.v2 + j)))) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    private boolean isOpen(String mapValue) {
        return OPEN.equals(mapValue);
    }

    private boolean isWooded(String mapValue) {
        return TREE.equals(mapValue);
    }

    private boolean isLumberyard(String mapValue) {
        return LUMBERYARD.equals(mapValue);
    }

}
