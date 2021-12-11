package de.beachboys.aoc2021;

import de.beachboys.Day;
import de.beachboys.Util;
import org.javatuples.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class Day11 extends Day {

    private final Map<Pair<Integer, Integer>, Integer> map = new HashMap<>();
    private int maxX ;
    private int maxY;

    public Object part1(List<String> input) {
        return runLogic(input, 100, false);
    }

    public Object part2(List<String> input) {
        return runLogic(input, Integer.MAX_VALUE, true);
    }

    private long runLogic(List<String> input, int numberOfSteps, boolean breakOnSimultaneousFlashAndReturnSteps) {
        map.clear();
        Map<Pair<Integer, Integer>, String> tempMap = Util.buildImageMap(input);
        tempMap.forEach((k, v) -> map.put(k, Integer.valueOf(v)));
        maxX = input.get(0).length();
        maxY = input.size();
        long sum = 0L;

        for (int step = 0; step < numberOfSteps; step++) {
            increaseEnergyLevel();
            Set<Pair<Integer, Integer>> flashes = new HashSet<>();
            Set<Pair<Integer, Integer>> newFlashes;
            do {
                newFlashes = map.entrySet().stream().filter(e -> e.getValue() > 9).map(Map.Entry::getKey).filter(pos -> !flashes.contains(pos)).collect(Collectors.toSet());
                for (Pair<Integer, Integer> flash : newFlashes) {
                    increaseEnergyLevelOfNeighbors(flash);
                }
                flashes.addAll(newFlashes);
            } while (!newFlashes.isEmpty());

            if (breakOnSimultaneousFlashAndReturnSteps && flashes.size() == map.size()) {
                return step + 1;
            }

            sum += flashes.size();

            for (Pair<Integer, Integer> flashedOctopus : flashes) {
                map.put(flashedOctopus, 0);
            }
        }

        return sum;
    }

    private void increaseEnergyLevelOfNeighbors(Pair<Integer, Integer> flash) {
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (x != 0 || y != 0) {
                    Pair<Integer, Integer> neighbor = Pair.with(flash.getValue0() + x, flash.getValue1() + y);
                    if (map.containsKey(neighbor)) {
                        map.put(neighbor, map.get(neighbor) + 1);
                    }
                }
            }
        }
    }

    private void increaseEnergyLevel() {
        for (int x = 0; x < maxX; x++) {
            for (int y = 0; y < maxY; y++) {
                Pair<Integer, Integer> pos = Pair.with(x, y);
                map.put(pos, map.get(pos) + 1);
            }
        }
    }

}
