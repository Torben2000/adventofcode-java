package de.beachboys.aoc2015;

import de.beachboys.Day;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class Day20 extends Day {

    public Object part1(List<String> input) {
        return runLogic(input, 10, this::getVisitingElvesPart1);
    }

    public Object part2(List<String> input) {
        return runLogic(input, 11, this::getVisitingElvesPart2);
    }

    private int runLogic(List<String> input, int presentsPerHouse, Function<Integer, Set<Integer>> getVisitingElves) {
        int minSumOfCurrentHouse = Integer.parseInt(input.getFirst());
        int currentHouse = 0;
        int sumOfCurrentHouse = 0;
        while (sumOfCurrentHouse < minSumOfCurrentHouse) {
            currentHouse++;
            sumOfCurrentHouse = getVisitingElves.apply(currentHouse).stream().mapToInt(Integer::intValue).sum() * presentsPerHouse;
        }
        return currentHouse;
    }

    private Set<Integer> getVisitingElvesPart1(Integer house) {
        Set<Integer> elves = new HashSet<>();
        for (int i = 1; i <= Math.sqrt(house); i++) {
            if (house % i == 0) {
                elves.add(i);
                elves.add(house / i);
            }
        }
        return elves;
    }

    private Set<Integer> getVisitingElvesPart2(Integer n) {
        Set<Integer> elves = new HashSet<>();
        for (int i = 1; i <= Math.min(n, 50); i++) {
            if (n % i == 0) {
                elves.add(n / i);
            }
        }
        return elves;
    }

}
