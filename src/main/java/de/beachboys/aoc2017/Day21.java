package de.beachboys.aoc2017;

import de.beachboys.Day;
import de.beachboys.Util;

import java.util.*;
import java.util.regex.Pattern;

public class Day21 extends Day {

    private final Map<String, String> rules = new HashMap<>();

    public Object part1(List<String> input) {
        return runLogic(input, 5);
    }

    public Object part2(List<String> input) {
        return runLogic(input, 18);
    }

    private int runLogic(List<String> input, int defaultValue) {
        int rounds = Util.getIntValueFromUser("Number of rounds", defaultValue, io);
        parseInput(input);
        List<String> pixelImage = List.of(".#.", "..#", "###");
        for (int i = 0; i < rounds; i++) {
            pixelImage = applyRules(pixelImage);
        }
        return pixelImage.stream().map(string -> string.replaceAll(Pattern.quote("."), "")).mapToInt(String::length).sum();
    }

    private List<String> applyRules(List<String> pixelImage) {
        if (pixelImage.size() % 2 == 0) {
            return applyRules(pixelImage, 2);
        } else {
            return applyRules(pixelImage, 3);
        }
    }

    private List<String> applyRules(List<String> pixelImage, int squareSize) {
        List<String> newImage = new ArrayList<>();
        for (int i = 0; i < pixelImage.size() / squareSize; i++) {
            for (int j = 0; j < squareSize + 1; j++) {
                newImage.add("");
            }
            for (int j = 0; j < pixelImage.size() / squareSize; j++) {
                StringBuilder ruleKey = new StringBuilder();
                for (int k = 0; k < squareSize; k++) {
                    ruleKey.append(pixelImage.get(squareSize * i + k), squareSize * j, squareSize * (j + 1));
                    ruleKey.append("/");
                }
                String ruleValue = rules.get(ruleKey.deleteCharAt(ruleKey.length() - 1).toString());
                for (int k = 0; k < squareSize + 1; k++) {
                    int index = (squareSize + 1) * i + k;
                    int offsetStart = (squareSize + 2) * k;
                    int offsetEnd = offsetStart + squareSize + 1;
                    newImage.set(index, newImage.get(index) + ruleValue.substring(offsetStart, offsetEnd));
                }
            }
        }
        return newImage;
    }

    private void parseInput(List<String> input) {
        rules.clear();
        for (String line : input) {
            String[] split = line.split(" => ");
            String leftSide = split[0];
            String rightSide = split[1];
            rules.put(leftSide, rightSide);
            for (int i = 0; i < 3; i++) {
                leftSide = rotate(leftSide);
                rules.put(leftSide, rightSide);
            }
            leftSide = flip(leftSide);
            rules.put(leftSide, rightSide);
            for (int i = 0; i < 3; i++) {
                leftSide = rotate(leftSide);
                rules.put(leftSide, rightSide);
            }
        }
    }

    private String rotate(String patternString) {
        char[][] patternArray = getChars(patternString);
        char[][] newPatternArray = new char[patternArray.length][patternArray.length];
        for (int i = 0; i < patternArray.length; i++) {
            for (int j = 0; j < patternArray.length; j++) {
                newPatternArray[i][j] = patternArray[patternArray.length - 1 - j][i];
            }
        }
        return getString(newPatternArray);
    }

    private String flip(String patternString) {
        char[][] patternArray = getChars(patternString);
        for (int i = 0; i < patternArray.length; i++) {
            for (int j = 0; j < patternArray.length / 2; j++) {
                char tmp = patternArray[i][patternArray.length - 1 - j];
                patternArray[i][patternArray.length - 1 - j] = patternArray[i][j];
                patternArray[i][j] = tmp;
            }
        }
        return getString(patternArray);
    }

    private String getString(char[][] patternArray) {
        StringBuilder patternString = new StringBuilder();
        for (char[] chars : patternArray) {
            patternString.append(String.copyValueOf(chars));
            patternString.append("/");
        }
        return patternString.deleteCharAt(patternString.length() - 1).toString();
    }

    private char[][] getChars(String patternString) {
        String[] splitPattern = patternString.split("/");
        char[][] patternArray = new char[splitPattern.length][splitPattern.length];
        for (int i=0; i<patternArray.length; i++) {
            patternArray[i] = splitPattern[i].toCharArray();
        }
        return patternArray;
    }

}
