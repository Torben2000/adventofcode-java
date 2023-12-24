package de.beachboys.aoc2018;

import de.beachboys.Day;

import java.util.*;

public class Day02 extends Day {

    public Object part1(List<String> input) {
        int twoCount = 0;
        int threeCount = 0;
        for (String line : input) {
            Map<Character, Integer> counter = new HashMap<>();
            for (char character : line.toCharArray()) {
                counter.merge(character, 1, Integer::sum);
            }
            if (counter.containsValue(2)) {
                twoCount++;
            }
            if (counter.containsValue(3)) {
                threeCount++;
            }
        }

        return twoCount * threeCount;
    }

    public Object part2(List<String> input) {
        Map<Integer, Set<String>> boxIdsWithMissingLetter = new HashMap<>();
        for (int i = 0; i < input.getFirst().length(); i++) {
            boxIdsWithMissingLetter.put(i, new HashSet<>());
        }
        for (String line : input) {
            for (int i = 0; i < line.length(); i++) {
                String boxIdWithMissingLetter = line.substring(0, i) + line.substring(i + 1);
                if (boxIdsWithMissingLetter.get(i).contains(boxIdWithMissingLetter)) {
                    return boxIdWithMissingLetter;
                }
                boxIdsWithMissingLetter.get(i).add(boxIdWithMissingLetter);
            }
        }
        return "not found";
    }

}
