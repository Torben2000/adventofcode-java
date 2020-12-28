package de.beachboys.aoc2015;

import de.beachboys.Day;

import java.util.List;

public class Day01 extends Day {

    public Object part1(List<String> input) {
        String instructions = input.get(0);
        int floor = 0;
        for (int i = 0; i < instructions.length(); i++) {
            char character = instructions.charAt(i);
            if (character == '(') {
                floor++;
            } else if (character == ')') {
                floor--;
            }
        }
        return floor;
    }

    public Object part2(List<String> input) {
        String instructions = input.get(0);
        int floor = 0;
        for (int i = 0; i < instructions.length(); i++) {
            char character = instructions.charAt(i);
            if (character == '(') {
                floor++;
            } else if (character == ')') {
                floor--;
            }
            if (floor < 0) {
                return i + 1;
            }
        }
        return -1;
    }

}
