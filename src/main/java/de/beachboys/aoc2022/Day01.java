package de.beachboys.aoc2022;

import de.beachboys.Day;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Day01 extends Day {

    public Object part1(List<String> input) {
        List<Integer> sortedCaloriesOfAllElves = getSortedCaloriesOfAllElves(input);
        return sortedCaloriesOfAllElves.get(0);
    }

    public Object part2(List<String> input) {
        List<Integer> sortedCaloriesOfAllElves = getSortedCaloriesOfAllElves(input);
        return sortedCaloriesOfAllElves.get(0) + sortedCaloriesOfAllElves.get(1) + sortedCaloriesOfAllElves.get(2);
    }

    private static List<Integer> getSortedCaloriesOfAllElves(List<String> input) {
        List<Integer> caloriesOfAllElves = new ArrayList<>();
        int caloriesOfElf = 0;
        for (String line : input) {
            if (line.isEmpty()) {
                caloriesOfAllElves.add(caloriesOfElf);
                caloriesOfElf = 0;
            } else {
                int foodCalories = Integer.parseInt(line);
                caloriesOfElf += foodCalories;
            }
        }
        caloriesOfAllElves.add(caloriesOfElf);

        return caloriesOfAllElves.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
    }

}
