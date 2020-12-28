package de.beachboys.aoc2015;

import de.beachboys.Day;

import java.util.List;

public class Day02 extends Day {

    public Object part1(List<String> input) {
        long sum = 0L;
        for (String dimensionString : input) {
            String[] dimensionsAsStrings = dimensionString.split("x");
            long l = Long.parseLong(dimensionsAsStrings[0]);
            long w = Long.parseLong(dimensionsAsStrings[1]);
            long h = Long.parseLong(dimensionsAsStrings[2]);
            sum += 2 * l * w + 2 * w * h + 2 * l * h + Math.min(Math.min(l * w, w * h), l * h);
        }
        return sum;
    }

    public Object part2(List<String> input) {
        long sum = 0L;
        for (String dimensionString : input) {
            String[] dimensionsAsStrings = dimensionString.split("x");
            long l = Long.parseLong(dimensionsAsStrings[0]);
            long w = Long.parseLong(dimensionsAsStrings[1]);
            long h = Long.parseLong(dimensionsAsStrings[2]);
            sum += l * w * h + 2 * Math.min(Math.min(l + w, w + h), l + h);
        }
        return sum;
    }

}
