package de.beachboys.aoc2019;

import de.beachboys.Day;
import de.beachboys.Util;

import java.util.*;

public class Day04 extends Day {

    @Override
    public Object part1(List<String> input) {
        List<Integer> splitInput = Util.parseToIntList(input.get(0), "-");
        int counter = 0;
        for (int i = splitInput.get(0); i <= splitInput.get(1); i++) {
            int[] digits = getDigits(i);
            if (hasAdjacentNumbers(digits) && doesNotDecrease(digits)) {
                counter++;
            }
        }

        return counter;
    }

    private int[] getDigits(int i) {
        int[] digits = new int[6];
        int curValue = i;
        digits[5] = curValue % 10;
        curValue = curValue / 10;
        digits[4] = curValue % 10;
        curValue = curValue / 10;
        digits[3] = curValue % 10;
        curValue = curValue / 10;
        digits[2] = curValue % 10;
        curValue = curValue / 10;
        digits[1] = curValue % 10;
        curValue = curValue / 10;
        digits[0] = curValue;
        return digits;
    }

    private boolean doesNotDecrease(int[] digits) {
        return (digits[0] <= digits[1] && digits[1] <= digits[2] && digits[2] <= digits[3] && digits[3] <= digits[4] && digits[4] <= digits[5]);
    }

    private boolean hasAdjacentNumbers(int[] digits) {
        return (digits[0] == digits[1] || digits[1] == digits[2] || digits[2] == digits[3] || digits[3] == digits[4] || digits[4] == digits[5]);
    }

    private boolean hasAdjacentNumbers2(int[] digits) {
        return (digits[0] == digits[1] && digits[1] != digits[2]|| digits[1] == digits[2] && digits[0] != digits[1]  && digits[2] != digits[3] || digits[2] == digits[3]  && digits[1] != digits[2]  && digits[3] != digits[4] || digits[3] == digits[4]  && digits[2] != digits[3]  && digits[4] != digits[5] || digits[4] == digits[5] && digits[3] != digits[4]);
    }

    @Override
    public Object part2(List<String> input) {
        List<Integer> splitInput = Util.parseToIntList(input.get(0), "-");
        int counter = 0;
        for (int i = splitInput.get(0); i <= splitInput.get(1); i++) {
            int[] digits = getDigits(i);
            if (hasAdjacentNumbers2(digits) && doesNotDecrease(digits)) {
                counter++;
            }
        }

        return counter;
    }
}
