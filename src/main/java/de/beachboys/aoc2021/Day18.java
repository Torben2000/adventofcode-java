package de.beachboys.aoc2021;

import de.beachboys.Day;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day18 extends Day {

    private static final Pattern PAIR_PATTERN = Pattern.compile("\\[([0-9]*),([0-9]*)]");

    public Object part1(List<String> input) {
        return getMagnitude(sumUp(input));
    }

    public Object part2(List<String> input) {
        int maxMagnitude = 0;
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.size(); j++) {
                if (i != j) {
                    maxMagnitude = Math.max(maxMagnitude, getMagnitude(addSnailfishNumbers(input.get(i), input.get(j))));
                }
            }
        }
        return maxMagnitude;
    }

    protected String sumUp(List<String> snailfishNumbers) {
        return snailfishNumbers.stream().reduce(null, this::addSnailfishNumbers);
    }

    protected String addSnailfishNumbers(String leftSnailfishNumber, String rightSnailfishNumber) {
        if (leftSnailfishNumber == null) {
            return rightSnailfishNumber;
        } else if (rightSnailfishNumber == null) {
            return leftSnailfishNumber;
        }
        return reduceSnailfishNumber("[" + leftSnailfishNumber + "," + rightSnailfishNumber + "]");
    }

    private String reduceSnailfishNumber(String snailfishNumber) {
        String newSnailfishNumber = snailfishNumber;
        String oldSnailfishNumber = "";
        while (!oldSnailfishNumber.equals(newSnailfishNumber)) {
            oldSnailfishNumber = newSnailfishNumber;
            newSnailfishNumber = explode(newSnailfishNumber);
            if (oldSnailfishNumber.equals(newSnailfishNumber)) {
                newSnailfishNumber = split(newSnailfishNumber);
            }
        }
        return newSnailfishNumber;
    }

    protected String split(String snailfishNumber) {
        int startOfNumberToSplit = -1;
        int numberToSplit = 0;
        for (int i = 0; i < snailfishNumber.length(); i++) {
            char c = snailfishNumber.charAt(i);
            if (c >= '0' && c <= '9') {
                if (startOfNumberToSplit == -1) {
                    startOfNumberToSplit = i;
                }
                numberToSplit *= 10;
                numberToSplit += c - '0';
            } else if (startOfNumberToSplit == i - 1) {
                startOfNumberToSplit = -1;
                numberToSplit = 0;
            } else if (startOfNumberToSplit != -1) {
                int newLeftNumber = numberToSplit / 2;
                int newRightNumber = numberToSplit - newLeftNumber;
                String newPair = "[" + newLeftNumber + "," + newRightNumber + "]";
                return snailfishNumber.substring(0, startOfNumberToSplit) + newPair + snailfishNumber.substring(i);
            }
        }
        return snailfishNumber;
    }

    protected String explode(String snailfishNumber) {
        int currentDepth = 0;
        for (int i = 0; i < snailfishNumber.length(); i++) {
            char c = snailfishNumber.charAt(i);
            if (c == ']') {
                currentDepth--;
            } else if (c == '[') {
                currentDepth++;
                if (currentDepth == 5) {
                    Matcher m = PAIR_PATTERN.matcher(snailfishNumber.substring(i));
                    if (m.find()) {
                        int leftNumberInPair = Integer.parseInt(m.group(1));
                        int rightNumberInPair = Integer.parseInt(m.group(2));
                        String leftString = addNumberToFirstFoundNumberInPartialSnailfishNumber(snailfishNumber.substring(0, i), leftNumberInPair, true);
                        String rightString = addNumberToFirstFoundNumberInPartialSnailfishNumber(snailfishNumber.substring(i + m.group(0).length()), rightNumberInPair, false);
                        return leftString + "0" + rightString;
                    }
                }
            }
        }
        return snailfishNumber;
    }

    private String addNumberToFirstFoundNumberInPartialSnailfishNumber(String partialSnailfishNumber, int numberToAdd, boolean startAtEnd) {
        int numberEnd = -1;
        int numberStart = -1;
        if (startAtEnd) {
            for (int i = partialSnailfishNumber.length() - 1; i >= 0; i--) {
                char c = partialSnailfishNumber.charAt(i);
                if (c >= '0' && c <= '9') {
                    if (numberEnd == -1) {
                        numberEnd = i;
                    }
                } else if (numberEnd > -1) {
                    numberStart = i + 1;
                    break;
                }
            }
        } else {
            for (int i = 0; i < partialSnailfishNumber.length(); i++) {
                char c = partialSnailfishNumber.charAt(i);
                if (c >= '0' && c <= '9') {
                    if (numberStart == -1) {
                        numberStart = i;
                    }
                } else if (numberStart > -1) {
                    numberEnd = i - 1;
                    break;
                }
            }
        }

        String newPartialSnailfishNumber;
        if (numberStart == -1) {
            newPartialSnailfishNumber = partialSnailfishNumber;
        } else {
            int number = 0;
            for (int i = numberStart; i <= numberEnd; i++) {
                number *= 10;
                number += partialSnailfishNumber.charAt(i) - '0';
            }
            newPartialSnailfishNumber = partialSnailfishNumber.substring(0, numberStart) + (numberToAdd + number) + partialSnailfishNumber.substring(numberEnd + 1);
        }
        return newPartialSnailfishNumber;
    }

    protected int getMagnitude(String snailfishNumber) {
        String magnitudeString = snailfishNumber;
        while (magnitudeString.contains("[")) {
            Matcher m = PAIR_PATTERN.matcher(magnitudeString);
            if (m.find()) {
                int localMagnitude = 3 * Integer.parseInt(m.group(1)) + 2 * Integer.parseInt(m.group(2));
                magnitudeString = magnitudeString.replaceFirst(PAIR_PATTERN.toString(), localMagnitude + "");
            }
        }
        return Integer.parseInt(magnitudeString);
    }

}
