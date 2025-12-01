package de.beachboys.aoc2025;

import de.beachboys.Day;
import de.beachboys.Util;

import java.util.List;

public class Day01 extends Day {

    public Object part1(List<String> input) {
        long result = 0;
        int dial = 50;
        for (String line : input) {
            boolean rotateLeft = line.charAt(0) == 'L';
            int rotationValue = Integer.parseInt(line.substring(1));
            if (rotateLeft) {
                dial = Util.modPositive(dial - rotationValue, 100);
            } else {
                dial = Util.modPositive(dial + rotationValue, 100);
            }
            if (dial == 0) {
                result++;
            }
        }
        return result;
    }

    public Object part2(List<String> input) {
        long result = 0;
        int dial = 50;
        for (String line : input) {
            boolean rotateLeft = line.charAt(0) == 'L';
            int rotationValue = Integer.parseInt(line.substring(1));
            if (rotateLeft) {
                for (int i = 0; i < rotationValue; i++) {
                    dial = Util.modPositive(dial - 1, 100);
                    if (dial == 0) {
                        result++;
                    }
                }
            } else {
                for (int i = 0; i < rotationValue; i++) {
                    dial = Util.modPositive(dial + 1, 100);
                    if (dial == 0) {
                        result++;
                    }
                }
            }
        }
        return result;
    }

}
