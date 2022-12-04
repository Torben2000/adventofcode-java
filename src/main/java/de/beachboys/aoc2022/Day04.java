package de.beachboys.aoc2022;

import de.beachboys.Day;

import java.util.List;

public class Day04 extends Day {

    public Object part1(List<String> input) {
        long result = 0;
        for (String line : input) {

            String[] ranges = line.split(",");
            String[] range1 = ranges[0].split("-");
            String[] range2 = ranges[1].split("-");
            int min1 = Integer.parseInt(range1[0]);
            int max1 = Integer.parseInt(range1[1]);
            int min2 = Integer.parseInt(range2[0]);
            int max2 = Integer.parseInt(range2[1]);

            if (min1 <= min2 && max1 >= max2 || min1 >= min2 && max1 <= max2) {
                result++;
            }
        }
        return result;
    }

    public Object part2(List<String> input) {
        long result = 0;
        for (String line : input) {
            String[] ranges = line.split(",");
            String[] range1 = ranges[0].split("-");
            String[] range2 = ranges[1].split("-");
            int min1 = Integer.parseInt(range1[0]);
            int max1 = Integer.parseInt(range1[1]);
            int min2 = Integer.parseInt(range2[0]);
            int max2 = Integer.parseInt(range2[1]);

            if (min1 <= max2 && max1 >= min2) {
                result++;
            }
        }
        return result;
    }

}
