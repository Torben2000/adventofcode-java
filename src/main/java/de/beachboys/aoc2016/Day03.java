package de.beachboys.aoc2016;

import de.beachboys.Day;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day03 extends Day {

    public Object part1(List<String> input) {
        int numTriangles = 0;
        Pattern pattern = Pattern.compile(" *([0-9]+) +([0-9]+) +([0-9]+)");
        for (String line : input) {
            Matcher m = pattern.matcher(line);
            if (m.matches()) {
                int side1 = Integer.parseInt(m.group(1));
                int side2 = Integer.parseInt(m.group(2));
                int side3 = Integer.parseInt(m.group(3));
                if (side1 < side2 + side3 && side2 < side1 + side3 && side3 < side1 + side2) {
                    numTriangles++;
                }
            }
        }
        return numTriangles;
    }

    public Object part2(List<String> input) {
        int numTriangles = 0;
        Pattern pattern = Pattern.compile(" *([0-9]+) +([0-9]+) +([0-9]+)");
        for (int i = 0; i < input.size(); i = i + 3) {
            Matcher m1 = pattern.matcher(input.get(i));
            Matcher m2 = pattern.matcher(input.get(i + 1));
            Matcher m3 = pattern.matcher(input.get(i + 2));
            if (m1.matches() && m2.matches() && m3.matches()) {
                for (int j = 1; j <= 3; j++) {
                    int side1 = Integer.parseInt(m1.group(j));
                    int side2 = Integer.parseInt(m2.group(j));
                    int side3 = Integer.parseInt(m3.group(j));
                    if (side1 < side2 + side3 && side2 < side1 + side3 && side3 < side1 + side2) {
                        numTriangles++;
                    }
                }
            }
        }
        return numTriangles;
    }

}
