package de.beachboys.aoc2020;

import de.beachboys.Day;

import java.util.*;

public class Day06 extends Day {

    private enum Mode {
        AND, OR
    }

    public Object part1(List<String> input) {
        return getSumOfAnsweredGroupQuestions(input, Mode.OR);
    }

    public Object part2(List<String> input) {
        return getSumOfAnsweredGroupQuestions(input, Mode.AND);
    }

    private int getSumOfAnsweredGroupQuestions(List<String> input, Mode mode) {
        List<Set<Integer>> groups = buildGroupList(input, mode);
        return groups.stream().mapToInt(Set::size).sum();
    }

    private List<Set<Integer>> buildGroupList(List<String> input, Mode mode) {
        List<Set<Integer>> groups = new ArrayList<>();
        Set<Integer> currentGroup = new HashSet<>();
        boolean isFirstLineOfGroup = true;
        for (String line : input) {
            if (line.isBlank()) {
                groups.add(currentGroup);
                currentGroup = new HashSet<>();
                isFirstLineOfGroup = true;
            } else {
                Set<Integer> currentPerson = new HashSet<>();
                line.chars().forEach(currentPerson::add);
                if (isFirstLineOfGroup || mode == Mode.OR) {
                    isFirstLineOfGroup = false;
                    currentGroup.addAll(currentPerson);
                } else {
                    currentGroup.retainAll(currentPerson);
                }
            }
        }
        groups.add(currentGroup);
        return groups;
    }

}
