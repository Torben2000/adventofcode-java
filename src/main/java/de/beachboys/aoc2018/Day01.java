package de.beachboys.aoc2018;

import de.beachboys.Day;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day01 extends Day {

    public Object part1(List<String> input) {
        int frequency = 0;
        for (String line : input) {
            frequency += Integer.parseInt(line);
        }
        return frequency;
    }

    public Object part2(List<String> input) {
        Set<Integer> seenFrequencies = new HashSet<>();
        int frequency = 0;
        seenFrequencies.add(frequency);
        while (true) {
            for (String line : input) {
                frequency += Integer.parseInt(line);
                if (seenFrequencies.contains(frequency)) {
                    return frequency;
                }
                seenFrequencies.add(frequency);
            }
        }
    }

}
