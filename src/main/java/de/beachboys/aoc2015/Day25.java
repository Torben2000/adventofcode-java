package de.beachboys.aoc2015;

import de.beachboys.Day;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day25 extends Day {

    public Object part1(List<String> input) {
        long col = 1;
        long row = 1;
        Matcher m = Pattern.compile("To continue, please consult the code grid in the manual. +Enter the code at row ([0-9]+), column ([0-9]+).").matcher(input.get(0));
        if (m.matches()) {
            row = Integer.parseInt(m.group(1));
            col = Integer.parseInt(m.group(2));
        }

        long requiredRow = row + col - 1;
        long loop = requiredRow * (requiredRow - 1) / 2 + col - 1;

        long currentValue = 20151125;

        for (long i = 0; i < loop; i++) {
            currentValue = (currentValue * 252533) % 33554393;
        }
        return currentValue;
    }

    public Object part2(List<String> input) {
        return "There is no puzzle! :-)";
    }

}
