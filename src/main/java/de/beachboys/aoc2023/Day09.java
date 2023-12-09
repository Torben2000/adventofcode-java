package de.beachboys.aoc2023;

import de.beachboys.Day;
import de.beachboys.Util;

import java.util.ArrayList;
import java.util.List;

public class Day09 extends Day {

    public Object part1(List<String> input) {
        long result = 0;
        for (String line : input) {
            List<List<Long>> allLists = getValueAndDiffLists(line);
            result += allLists.stream().mapToLong(List::getLast).sum();
        }
        return result;
    }

    public Object part2(List<String> input) {
        long result = 0;
        for (String line : input) {
            List<List<Long>> allLists = getValueAndDiffLists(line);
            long previousValue = 0;
            for (int j = allLists.size() - 1; j >= 0; j--) {
                previousValue = allLists.get(j).getFirst() - previousValue;
            }
            result += previousValue;
        }
        return result;
    }

    private static List<List<Long>> getValueAndDiffLists(String line) {
        List<Long> currentList = Util.parseToLongList(line, " ");
        List<List<Long>> allLists = new ArrayList<>();
        boolean containsOnlyZeros = false;
        while (!containsOnlyZeros) {
            allLists.add(currentList);
            List<Long> newList = new ArrayList<>();
            containsOnlyZeros = true;
            for (int i = 0; i < currentList.size() - 1; i++) {
                long diff = currentList.get(i + 1) - currentList.get(i);
                newList.add(diff);
                if (diff != 0) {
                    containsOnlyZeros = false;
                }
            }
            currentList = newList;
        }
        return allLists;
    }

}
