package de.beachboys.aoc2020;

import de.beachboys.Day;

import java.util.List;

public class Day01 extends Day {

    public Object part1(List<String> input) {
        List<Integer> intList = input.stream().map(Integer::valueOf).toList();
        for (int i = 0; i < intList.size(); i++) {
            for (int j = i + 1; j < intList.size(); j++) {
                if (intList.get(i) + intList.get(j) == 2020) {
                    return intList.get(i) * intList.get(j);
                }
            }
        }
        return 0;
    }

    public Object part2(List<String> input) {
        List<Integer> intList = input.stream().map(Integer::valueOf).toList();
        for (int i = 0; i < intList.size(); i++) {
            for (int j = i + 1; j < intList.size(); j++) {
                for (int k = j + 1; k < intList.size(); k++) {
                    if (intList.get(i) + intList.get(j) + intList.get(k) == 2020) {
                        return intList.get(i) * intList.get(j) * intList.get(k);
                    }
                }
            }
        }
        return 0;
    }

}
