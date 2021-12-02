package de.beachboys.aoc2021;

import de.beachboys.Day;
import de.beachboys.Direction;
import org.javatuples.Pair;

import java.util.List;
import java.util.stream.Collectors;

public class Day02 extends Day {

    public Object part1(List<String> input) {
        long horizontal = 0;
        long depth = 0;
        for (String line : input) {
            int value = Integer.parseInt(line.split(" ")[1]);
            switch (line.charAt(0)) {
                case 'f':
                    horizontal += value;
                    break;
                case 'u':
                    depth -= value;
                    break;
                case 'd':
                    depth += value;
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        }

        return horizontal * depth;
    }

    public Object part2(List<String> input) {
        long aim = 0;
        long horizontal = 0;
        long depth = 0;
        for (String line : input) {
            int value = Integer.parseInt(line.split(" ")[1]);
            switch (line.charAt(0)) {
                case 'f':
                    horizontal += value;
                    depth += aim * value;
                    break;
                case 'u':
                    aim -= value;
                    break;
                case 'd':
                    aim += value;
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        }

        return horizontal * depth;
    }

}
