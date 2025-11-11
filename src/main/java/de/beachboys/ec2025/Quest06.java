package de.beachboys.ec2025;

import de.beachboys.Quest;
import de.beachboys.Util;

import java.util.ArrayList;
import java.util.List;

public class Quest06 extends Quest {

    @Override
    public Object part1(List<String> input) {
        String line = input.getFirst();
        long result = 0;
        int numA = 0;
        for (int i = 0; i < line.length(); i++) {
            switch (line.charAt(i)) {
                case 'A':
                    numA++;
                    break;
                case 'a':
                    result += numA;
                    break;
            }
        }
        return result;
    }

    @Override
    public Object part2(List<String> input) {
        String line = input.getFirst();
        long result = 0;
        int numA = 0;
        int numB = 0;
        int numC = 0;
        for (int i = 0; i < line.length(); i++) {
            switch (line.charAt(i)) {
                case 'A':
                    numA++;
                    break;
                case 'B':
                    numB++;
                    break;
                case 'C':
                    numC++;
                    break;
                case 'a':
                    result += numA;
                    break;
                case 'b':
                    result += numB;
                    break;
                case 'c':
                    result += numC;
                    break;
            }
        }
        return result;
    }

    @Override
    public Object part3(List<String> input) {
        String line = input.getFirst();
        int repetitions = Util.getIntValueFromUser("Pattern repetitions", 1000, io);
        int distanceLimit = Util.getIntValueFromUser("Distance limit", 1000, io);

        StringBuilder lineToUse = new StringBuilder();
        int neededRepetitionsOfLineToUse = 1;
        for (int i = 1; i <= repetitions; i++) {
            lineToUse.append(line);
            if (lineToUse.length() >= distanceLimit && repetitions % i == 0) {
                neededRepetitionsOfLineToUse = repetitions / i;
                break;
            }
        }

        List<Integer> knightsA = new ArrayList<>();
        List<Integer> knightsB = new ArrayList<>();
        List<Integer> knightsC = new ArrayList<>();
        for (int i = 0; i < lineToUse.length(); i++) {
            switch (lineToUse.charAt(i)) {
                case 'A':
                    knightsA.add(i);
                    break;
                case 'B':
                    knightsB.add(i);
                    break;
                case 'C':
                    knightsC.add(i);
                    break;
            }
        }


        long result = 0;
        for (int i = 0; i < lineToUse.length(); i++) {
            char c = lineToUse.charAt(i);
            switch (c) {
                case 'a':
                    result += getPossibleMentorCount(neededRepetitionsOfLineToUse, knightsA, i, lineToUse.length(), distanceLimit);
                    break;
                case 'b':
                    result += getPossibleMentorCount(neededRepetitionsOfLineToUse, knightsB, i, lineToUse.length(), distanceLimit);
                    break;
                case 'c':
                    result += getPossibleMentorCount(neededRepetitionsOfLineToUse, knightsC, i, lineToUse.length(), distanceLimit);
                    break;
            }
        }
        return result;
    }

    private static long getPossibleMentorCount(int neededRepetitionsOfLineToUse, List<Integer> knightIndexes, int noviceIndex, int lineLength, int distanceLimit) {
        long result = 0;
        result += neededRepetitionsOfLineToUse * knightIndexes.stream().filter(k -> isInRange(k, noviceIndex, distanceLimit)).count();
        result += (neededRepetitionsOfLineToUse - 1) * knightIndexes.stream().filter(k -> isInRangeInNextRepetition(k, noviceIndex, lineLength, distanceLimit)).count();
        result += (neededRepetitionsOfLineToUse - 1) * knightIndexes.stream().filter(k -> isInRangeInPreviousRepetition(k, noviceIndex, lineLength, distanceLimit)).count();
        return result;
    }

    private static boolean isInRange(int knightIndex, int noviceIndex, int distanceLimit) {
        return knightIndex >= (noviceIndex - distanceLimit) && knightIndex <= (noviceIndex + distanceLimit);
    }

    private static boolean isInRangeInNextRepetition(int knightIndex, int noviceIndex, int lineLength, int distanceLimit) {
        return noviceIndex + distanceLimit >= lineLength && knightIndex <= (noviceIndex + distanceLimit) % lineLength;
    }

    private static boolean isInRangeInPreviousRepetition(int knightIndex, int noviceIndex, int lineLength, int distanceLimit) {
        return noviceIndex - distanceLimit < 0 && knightIndex >= Util.modPositive(noviceIndex - distanceLimit, lineLength);
    }
}
