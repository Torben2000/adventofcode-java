package de.beachboys.aoc2023;

import de.beachboys.Day;

import java.util.*;

public class Day04 extends Day {

    public Object part1(List<String> input) {
        long result = 0;
        for (String line : input) {
            int matchingNumberCount = getMatchingNumberCount(line);
            if (matchingNumberCount > 0) {
                result += (long) Math.pow(2, matchingNumberCount - 1);
            }
        }
        return result;
    }

    public Object part2(List<String> input) {
        long[] numberOfScratchCards = new long[input.size()];
        Arrays.fill(numberOfScratchCards, 1);

        for (int i = 0; i < input.size(); i++) {
            String line = input.get(i);
            int matchingNumberCount = getMatchingNumberCount(line);
            for (int j = 0; j < matchingNumberCount; j++) {
                numberOfScratchCards[i + 1 + j] += numberOfScratchCards[i];
            }
        }

        long result = 0;
        for (long numberOfScratchCard : numberOfScratchCards) {
            result += numberOfScratchCard;
        }
        return result;
    }

    private static int getMatchingNumberCount(String line) {
        String[] winningNumbersAndMyNumbersAsStrings = line.split(": ")[1].split(" \\| ");
        String[] winningNumbersAsStrings = winningNumbersAndMyNumbersAsStrings[0].trim().replaceAll(" +", " ").split(" ");
        String[] myNumbersAsStrings = winningNumbersAndMyNumbersAsStrings[1].trim().replaceAll(" +", " ").split(" ");
        Set<Integer> winningNumbers = new HashSet<>();
        List<Integer> myNumbers = new ArrayList<>();
        for (String winningNumberAsString : winningNumbersAsStrings) {
            winningNumbers.add(Integer.parseInt(winningNumberAsString));
        }
        for (String myNumberAsString : myNumbersAsStrings) {
            myNumbers.add(Integer.parseInt(myNumberAsString));
        }

        int count = 0;
        for (Integer myNumber : myNumbers) {
            if (winningNumbers.contains(myNumber)) {
                count++;
            }
        }
        return count;
    }

}
