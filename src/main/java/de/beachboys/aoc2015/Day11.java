package de.beachboys.aoc2015;

import de.beachboys.Day;

import java.util.List;

public class Day11 extends Day {

    public Object part1(List<String> input) {
        return getNextValidPassword(input.get(0));
    }

    public Object part2(List<String> input) {
        return getNextValidPassword(getNextValidPassword(input.get(0)));
    }

    private String getNextValidPassword(String password) {
        do {
            password = getNextPassword(password);
        } while (!isValidPassword(password));

        return password;
    }

    private String getNextPassword(String password) {
        StringBuilder nextPassword = new StringBuilder();
        boolean increaseFinished = false;
        for (int i = password.length() - 1; i >= 0; i--) {
            char currentChar = password.charAt(i);
            if (!increaseFinished) {
                if ('z' == currentChar) {
                    currentChar = 'a';
                } else {
                    currentChar++;
                    increaseFinished = true;
                }
                if ('i' == currentChar || 'l' == currentChar || 'o' == currentChar) {
                    currentChar++;
                }
            }
            nextPassword.insert(0, currentChar);
        }
        return nextPassword.toString();
    }

    private boolean isValidPassword(String password) {
        char prevCharForDouble = 0;
        char prevCharForIncrease = 0;
        char prevPrevCharForIncrease = 0;
        int doubleCounter = 0;
        boolean increasingChars = false;
        boolean invalidCharDetected = false;
        for (int i = 0; i < password.length(); i++) {
            char currentChar = password.charAt(i);
            if ('i' == currentChar || 'l' == currentChar || 'o' == currentChar) {
                invalidCharDetected = true;
            }
            if (prevCharForDouble == currentChar) {
                doubleCounter++;
                prevCharForDouble = 0;
            } else {
                prevCharForDouble = currentChar;
            }
            if (prevCharForIncrease + 1 == currentChar && prevPrevCharForIncrease + 1 == prevCharForIncrease) {
                increasingChars = true;
            } else {
                prevPrevCharForIncrease = prevCharForIncrease;
                prevCharForIncrease = currentChar;
            }
        }
        return !invalidCharDetected && increasingChars && doubleCounter >= 2;
    }

}
