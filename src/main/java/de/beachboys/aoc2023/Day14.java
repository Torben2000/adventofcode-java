package de.beachboys.aoc2023;

import de.beachboys.Day;
import de.beachboys.Util;
import org.javatuples.Pair;

import java.util.*;

public class Day14 extends Day {

    private Set<Pair<Integer, Integer>> cubeShapedRocks;
    private int height;
    private int width;

    public Object part1(List<String> input) {
        Set<Pair<Integer, Integer>> roundedRocks = parseInputAndGetRoundedRocks(input);
        roundedRocks = slideNorth(roundedRocks);
        return getScore(roundedRocks);
    }

    public Object part2(List<String> input) {

        Set<Pair<Integer, Integer>> roundedRocks = parseInputAndGetRoundedRocks(input);


        Map<Set<Pair<Integer, Integer>>, Integer> history = new HashMap<>();

        int totalRounds = 1000000000;
        for (int currentRound = 0; currentRound < totalRounds; currentRound++) {
            roundedRocks = slideNorth(roundedRocks);
            roundedRocks = slideWest(roundedRocks);
            roundedRocks = slideSouth(roundedRocks);
            roundedRocks = slideEast(roundedRocks);

            if (history.containsKey(roundedRocks)) {
                int firstOccurrence = history.get(roundedRocks);
                int cycleLength = currentRound - firstOccurrence;
                int cycles = (totalRounds - firstOccurrence) / cycleLength;
                int indexToUse = totalRounds - (cycles * cycleLength) - 1;

                roundedRocks = history.entrySet().stream().filter(e -> e.getValue().equals(indexToUse)).map(Map.Entry::getKey).findFirst().orElseThrow();
                return getScore(roundedRocks);
            }
            history.put(roundedRocks, currentRound);
        }
        throw new IllegalArgumentException();
     }

    private Set<Pair<Integer, Integer>> slideEast(Set<Pair<Integer, Integer>> roundedRocks) {
        Set<Pair<Integer, Integer>> newRoundedRocks;
        newRoundedRocks = new HashSet<>();
        for (int x = width - 1; x >= 0; x--) {
            for (int y = height - 1; y >= 0; y--) {
                if (roundedRocks.contains(Pair.with(x, y))) {
                    boolean found = false;
                    for (int i = x + 1; i < height; i++) {
                        if (cubeShapedRocks.contains(Pair.with(i, y)) || newRoundedRocks.contains(Pair.with(i, y))) {
                            newRoundedRocks.add(Pair.with(i - 1, y));
                            found = true;
                            break;
                        }

                    }
                    if (!found) {
                        newRoundedRocks.add(Pair.with(width - 1, y));
                    }
                }
            }
        }
        roundedRocks = newRoundedRocks;
        return roundedRocks;
    }

    private Set<Pair<Integer, Integer>> slideSouth(Set<Pair<Integer, Integer>> roundedRocks) {
        Set<Pair<Integer, Integer>> newRoundedRocks;
        newRoundedRocks = new HashSet<>();
        for (int y = height - 1; y >= 0; y--) {
            for (int x = width - 1; x >= 0; x--) {
                if (roundedRocks.contains(Pair.with(x, y))) {
                    boolean found = false;
                    for (int i = y + 1; i < height; i++) {
                        if (cubeShapedRocks.contains(Pair.with(x, i)) || newRoundedRocks.contains(Pair.with(x, i))) {
                            newRoundedRocks.add(Pair.with(x, i - 1));
                            found = true;
                            break;
                        }

                    }
                    if (!found) {
                        newRoundedRocks.add(Pair.with(x, height - 1));
                    }
                }
            }
        }
        roundedRocks = newRoundedRocks;
        return roundedRocks;
    }

    private Set<Pair<Integer, Integer>> slideWest(Set<Pair<Integer, Integer>> roundedRocks) {
        Set<Pair<Integer, Integer>> newRoundedRocks;
        newRoundedRocks = new HashSet<>();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (roundedRocks.contains(Pair.with(x, y))) {
                    boolean found = false;
                    for (int i = x - 1; i >= 0; i--) {
                        if (cubeShapedRocks.contains(Pair.with(i, y)) || newRoundedRocks.contains(Pair.with(i, y))) {
                            newRoundedRocks.add(Pair.with(i + 1, y));
                            found = true;
                            break;
                        }

                    }
                    if (!found) {
                        newRoundedRocks.add(Pair.with(0, y));
                    }
                }
            }
        }
        roundedRocks = newRoundedRocks;
        return roundedRocks;
    }

    private Set<Pair<Integer, Integer>> slideNorth(Set<Pair<Integer, Integer>> roundedRocks) {
        Set<Pair<Integer, Integer>> newRoundedRocks;
        newRoundedRocks = new HashSet<>();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (roundedRocks.contains(Pair.with(x, y))) {
                    boolean found = false;
                    for (int i = y - 1; i >= 0; i--) {
                        if (cubeShapedRocks.contains(Pair.with(x, i)) || newRoundedRocks.contains(Pair.with(x, i))) {
                            newRoundedRocks.add(Pair.with(x, i + 1));
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        newRoundedRocks.add(Pair.with(x, 0));
                    }
                }
            }
        }
        roundedRocks = newRoundedRocks;
        return roundedRocks;
    }

    private long getScore(Set<Pair<Integer, Integer>> roundedRocks) {
        long result = 0;
        for (Pair<Integer, Integer> rock : roundedRocks) {
            result += height - rock.getValue1();
        }
        return result;
    }

    private Set<Pair<Integer, Integer>> parseInputAndGetRoundedRocks(List<String> input) {
        Set<Pair<Integer, Integer>> roundedRocks = Util.buildConwaySet(input, "O");
        cubeShapedRocks = Util.buildConwaySet(input, "#");
        height = input.size();
        width = input.getFirst().length();
        return roundedRocks;
    }

}
