package de.beachboys.aoc2017;

import de.beachboys.Day;

import java.util.List;

public class Day01 extends Day {

    public Object part1(List<String> input) {
        return solveCaptcha(input.getFirst(), 1);
    }

    private int solveCaptcha(String captchaInput, int offset) {
        int result = 0;
        char[] charArray = captchaInput.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char character = charArray[i];
            if (charArray[i] == charArray[(i + offset) % charArray.length] ) {
                result += character - '0';
            }
        }
        return result;
    }

    public Object part2(List<String> input) {
        String captchaInput = input.getFirst();
        return solveCaptcha(captchaInput, captchaInput.length() / 2);
    }

}
