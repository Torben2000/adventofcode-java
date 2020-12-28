package de.beachboys.aoc2015;

import de.beachboys.Day;

import java.util.List;

public class Day05 extends Day {

    public Object part1(List<String> input) {
        int niceCounter = 0;
        for (String string : input) {
            if (string.matches(".*[aeiou].*[aeiou].*[aeiou].*")
                    && string.matches(".*(.)\\1.*")
                    && !string.matches(".*ab.*")
                    && !string.matches(".*cd.*")
                    && !string.matches(".*pq.*")
                    && !string.matches(".*xy.*")) {
                niceCounter++;
            }
        }
        return niceCounter;
    }

    public Object part2(List<String> input) {
        int niceCounter = 0;
        for (String string : input) {
            if (string.matches(".*(.{2})[^\1]*\\1.*")
                    && string.matches(".*(.).\\1.*")) {
                niceCounter++;
            }
        }
        return niceCounter;
    }

}
