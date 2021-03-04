package de.beachboys.aoc2018;

import de.beachboys.Day;
import de.beachboys.Util;
import org.javatuples.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Day18 extends Day {

    public static final String TREE = "|";
    public static final String LUMBERYARD = "#";
    public static final String OPEN = ".";

    private Map<Pair<Integer, Integer>, String> map;

    public Object part1(List<String> input) {
        return runLogic(input, 10);
    }

    public Object part2(List<String> input) {
        return runLogic(input, 1000000000);
    }

    private long runLogic(List<String> input, int minutes) {
        map = Util.buildImageMap(input);
        Map<String, Integer> seenStates = new HashMap<>();
        seenStates.put(String.join("\n", input), 0);

        for (int i = 1; i <= minutes; i++) {
            Map<Pair<Integer, Integer>, String> newMap = new HashMap<>();
            for (Map.Entry<Pair<Integer, Integer>, String> mapEntry : map.entrySet()) {
                Pair<Integer, Integer> position = mapEntry.getKey();
                String mapValue = mapEntry.getValue();
                if (isOpen(mapValue) && countNeighbors(position, this::isWooded) >= 3) {
                    newMap.put(position, TREE);
                } else if (isWooded(mapValue) && countNeighbors(position, this::isLumberyard) >= 3) {
                    newMap.put(position, LUMBERYARD);
                } else if (isLumberyard(mapValue) && (countNeighbors(position, this::isLumberyard) == 0 || countNeighbors(position, this::isWooded) == 0)) {
                    newMap.put(position, OPEN);
                } else {
                    newMap.put(position, mapValue);
                }
            }
            map = newMap;
            String mapString = Util.paintMap(map);
            if (seenStates.containsKey(mapString)) {
                int minuteOfSeenState = seenStates.get(mapString);
                int loopLength = i - minuteOfSeenState;
                int neededRound = minutes % loopLength;
                while (neededRound < minuteOfSeenState) {
                    neededRound += loopLength;
                }
                int finalNeededRound = neededRound;
                map = Util.buildImageMap(seenStates.entrySet().stream().filter(entry -> finalNeededRound == entry.getValue()).map(Map.Entry::getKey).collect(Collectors.joining()));
                break;
            } else {
                seenStates.put(mapString, i);
            }
        }
        return map.values().stream().filter(this::isWooded).count() * map.values().stream().filter(this::isLumberyard).count();
    }

    private int countNeighbors(Pair<Integer, Integer> position, Predicate<String> neighborFilter) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i != 0 || j != 0) {
                    if (neighborFilter.test(map.get(Pair.with(position.getValue0() + i, position.getValue1() + j)))) {
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
