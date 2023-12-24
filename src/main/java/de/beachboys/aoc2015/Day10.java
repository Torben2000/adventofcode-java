package de.beachboys.aoc2015;

import de.beachboys.Day;
import de.beachboys.Util;

import java.util.List;

public class Day10 extends Day {

    public Object part1(List<String> input) {
        int defaultIterations = 40;
        return runLogic(input, defaultIterations);
    }

    public Object part2(List<String> input) {
        int defaultIterations = 50;
        return runLogic(input, defaultIterations);
    }

    private int runLogic(List<String> input, int defaultIterations) {
        String currentValue = input.getFirst();
        int numOfIterations = Util.getIntValueFromUser("Number of iterations", defaultIterations, io);

        for (int i = 0; i < numOfIterations; i++) {
            currentValue = lookAndSay(currentValue);
        }

        io.logInfo(currentValue);
        return currentValue.length();
    }

    private String lookAndSay(String numString) {
        StringBuilder sb = new StringBuilder();
        int currentCounter = 0;
        String currentNumber = "";
        for (int i = 0; i < numString.length(); i++) {
            String digit = numString.substring(i, i + 1);
            if (!digit.equals(currentNumber)) {
                if (currentCounter > 0) {
                    sb.append(currentCounter);
                    sb.append(currentNumber);
                }
                currentCounter = 1;
                currentNumber = digit;
            } else {
                currentCounter++;
            }
        }
        if (currentCounter > 0) {
            sb.append(currentCounter);
            sb.append(currentNumber);
        }
        return sb.toString();
    }

}
