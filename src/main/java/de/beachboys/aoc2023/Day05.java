package de.beachboys.aoc2023;

import de.beachboys.Day;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;
import org.jooq.lambda.tuple.Tuple3;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Day05 extends Day {

    private final List<List<Tuple3<Long, Long, Long>>> mappings = new ArrayList<>();
    
    public Object part1(List<String> input) {
        List<Long> currentNumbers = parseInput(input);
        for (int i = 0; i < 7; i++) {
            List<Long> newNumbers = new ArrayList<>();
            for (Long currentNumber : currentNumbers) {
                boolean found = false;
                for (Tuple3<Long, Long, Long> mapping : mappings.get(i)) {
                    if (currentNumber >= mapping.v2 && currentNumber < mapping.v2 + mapping.v3) {
                        newNumbers.add(currentNumber + mapping.v1- mapping.v2);
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
        List<Tuple2<Long, Long>> currentRanges = new ArrayList<>();
        for (int j = 0; j < tempSeedNumbers.size() / 2; j++) {
            currentRanges.add(Tuple.tuple(tempSeedNumbers.get(2 * j), tempSeedNumbers.get(2 * j + 1)));
        }

        for (int i = 0; i < 7; i++) {
            List<Tuple2<Long, Long>> newRanges = new ArrayList<>();
            for (Tuple2<Long, Long> currentRange : currentRanges) {
                List<Tuple2<Long, Long>> remainingSubrangesToMap = new LinkedList<>();
                remainingSubrangesToMap.add(currentRange);
                for (Tuple3<Long, Long, Long> mapping : mappings.get(i)) {
                    List<Tuple2<Long, Long>> newRemainingSubrangesToMap = new LinkedList<>();
                    for (Tuple2<Long, Long> subrange : remainingSubrangesToMap) {
                        if (subrange.v1 + subrange.v2 > mapping.v2 && subrange.v1 < mapping.v2 + mapping.v3) {
                            if (subrange.v1 < mapping.v2) {
                                newRemainingSubrangesToMap.add(Tuple.tuple(subrange.v1, mapping.v2 - subrange.v1));
                            }
                            if (subrange.v1 + subrange.v2 > mapping.v2 + mapping.v3) {
                                newRemainingSubrangesToMap.add(Tuple.tuple(mapping.v2 + mapping.v3, subrange.v1 + subrange.v2 - (mapping.v2 + mapping.v3)));
                            }

                            long left = Math.max(mapping.v2, subrange.v1);
                            long rightPlusOne = Math.min(mapping.v2 + mapping.v3, subrange.v1 + subrange.v2);
                            long rangeLength = rightPlusOne - left;
                            newRanges.add(Tuple.tuple(left + mapping.v1 - mapping.v2, rangeLength));
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

        return currentRanges.stream().sorted().findFirst().orElseThrow().v1;
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
                    mappings.get(curList).add(Tuple.tuple(
                            Long.parseLong(tripletAsString[0]),
                            Long.parseLong(tripletAsString[1]),
                            Long.parseLong(tripletAsString[2])));
                }
            }
        }
        return seeds;
    }

}
