package de.beachboys.aoc2023;

import de.beachboys.Day;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Day05 extends Day {

    private final List<List<Triplet<Long, Long, Long>>> mappings = new ArrayList<>();
    
    public Object part1(List<String> input) {
        List<Long> currentNumbers = parseInput(input);
        for (int i = 0; i < 7; i++) {
            List<Long> newNumbers = new ArrayList<>();
            for (Long currentNumber : currentNumbers) {
                boolean found = false;
                for (Triplet<Long, Long, Long> mapping : mappings.get(i)) {
                    if (currentNumber >= mapping.getValue1() && currentNumber < mapping.getValue1() + mapping.getValue2()) {
                        newNumbers.add(currentNumber + mapping.getValue0()- mapping.getValue1());
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    newNumbers.add(currentNumber);
                }
            }
            currentNumbers = newNumbers;
        }

        return currentNumbers.stream().sorted().findFirst().orElseThrow();
    }

    public Object part2(List<String> input) {
        List<Long> tempSeedNumbers = parseInput(input);
        List<Pair<Long, Long>> currentRanges = new ArrayList<>();
        for (int j = 0; j < tempSeedNumbers.size() / 2; j++) {
            currentRanges.add(Pair.with(tempSeedNumbers.get(2 * j), tempSeedNumbers.get(2 * j + 1)));
        }

        for (int i = 0; i < 7; i++) {
            List<Pair<Long, Long>> newRanges = new ArrayList<>();
            for (Pair<Long, Long> currentRange : currentRanges) {
                List<Pair<Long, Long>> remainingSubrangesToMap = new LinkedList<>();
                remainingSubrangesToMap.add(currentRange);
                for (Triplet<Long, Long, Long> mapping : mappings.get(i)) {
                    List<Pair<Long, Long>> newRemainingSubrangesToMap = new LinkedList<>();
                    for (Pair<Long, Long> subrange : remainingSubrangesToMap) {
                        if (subrange.getValue0() + subrange.getValue1() > mapping.getValue1() && subrange.getValue0() < mapping.getValue1() + mapping.getValue2()) {
                            if (subrange.getValue0() < mapping.getValue1()) {
                                newRemainingSubrangesToMap.add(Pair.with(subrange.getValue0(), mapping.getValue1() - subrange.getValue0()));
                            }
                            if (subrange.getValue0() + subrange.getValue1() > mapping.getValue1() + mapping.getValue2()) {
                                newRemainingSubrangesToMap.add(Pair.with(mapping.getValue1() + mapping.getValue2(), subrange.getValue0() + subrange.getValue1() - (mapping.getValue1() + mapping.getValue2())));
                            }

                            long left = Math.max(mapping.getValue1(), subrange.getValue0());
                            long rightPlusOne = Math.min(mapping.getValue1() + mapping.getValue2(), subrange.getValue0() + subrange.getValue1());
                            long rangeLength = rightPlusOne - left;
                            newRanges.add(Pair.with(left + mapping.getValue0() - mapping.getValue1(), rangeLength));
                        } else {
                            newRemainingSubrangesToMap.add(subrange);
                        }
                    }
                    remainingSubrangesToMap = newRemainingSubrangesToMap;
                }
                newRanges.addAll(remainingSubrangesToMap);
            }
            currentRanges = newRanges;
        }

        return currentRanges.stream().sorted().findFirst().orElseThrow().getValue0();
    }

    private List<Long> parseInput(List<String> input) {
        mappings.clear();
        List<Long> seeds = new ArrayList<>();
        int curList = 0;
        for (String line : input) {
            if (!line.isEmpty()) {
                if (line.startsWith("seeds")) {
                    String[] seedStrings = line.substring("seeds: ".length()).split(" ");
                    for (String seedString : seedStrings) {
                        seeds.add(Long.parseLong(seedString));
                    }
                } else if ("seed-to-soil map:".equals(line)) {
                    curList = 0;
                    mappings.add(curList, new ArrayList<>());
                } else if ("soil-to-fertilizer map:".equals(line)) {
                    curList = 1;
                    mappings.add(curList, new ArrayList<>());
                } else if ("fertilizer-to-water map:".equals(line)) {
                    curList = 2;
                    mappings.add(curList, new ArrayList<>());
                } else if ("water-to-light map:".equals(line)) {
                    curList = 3;
                    mappings.add(curList, new ArrayList<>());
                } else if ("light-to-temperature map:".equals(line)) {
                    curList = 4;
                    mappings.add(curList, new ArrayList<>());
                } else if ("temperature-to-humidity map:".equals(line)) {
                    curList = 5;
                    mappings.add(curList, new ArrayList<>());
                } else if ("humidity-to-location map:".equals(line)) {
                    curList = 6;
                    mappings.add(curList, new ArrayList<>());
                } else {
                    String[] tripletAsString = line.split(" ");
                    mappings.get(curList).add(Triplet.with(
                            Long.parseLong(tripletAsString[0]),
                            Long.parseLong(tripletAsString[1]),
                            Long.parseLong(tripletAsString[2])));
                }
            }
        }
        return seeds;
    }

}
