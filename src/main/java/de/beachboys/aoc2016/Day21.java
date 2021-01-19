package de.beachboys.aoc2016;

import de.beachboys.Day;
import de.beachboys.Util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day21 extends Day {

    @FunctionalInterface
    private interface PasswordModifier {
        void modify(StringBuilder password, String group1, String group2);
    }

    private final Pattern swapPositionPattern = Pattern.compile("swap position ([0-9]+) with position ([0-9]+)");
    private final Pattern swapLetterPattern = Pattern.compile("swap letter ([a-z]) with letter ([a-z])");
    private final Pattern rotateBasedOnLetterPositionPattern = Pattern.compile("rotate based on position of letter ([a-z])(.*)");
    private final Pattern rotateLeftRightPattern = Pattern.compile("rotate (left|right) ([0-9]+) step[s]*");
    private final Pattern reversePositionsPattern = Pattern.compile("reverse positions ([0-9]+) through ([0-9]+)");
    private final Pattern movePattern = Pattern.compile("move position ([0-9]+) to position ([0-9]+)");

    public Object part1(List<String> input) {
        StringBuilder password = new StringBuilder(Util.getStringValueFromUser("Password", "abcdefgh", io));
        for (String operation : input) {
            if (operation.startsWith("swap position")) {
                runOperation(password, operation, swapPositionPattern, this::swapPositions);
            } else if (operation.startsWith("swap letter")) {
                runOperation(password, operation, swapLetterPattern, this::swapLetters);
            } else if (operation.startsWith("rotate based")) {
                runOperation(password, operation, rotateBasedOnLetterPositionPattern, this::rotateBasedOnLetterPosition);
            } else if (operation.startsWith("rotate")) {
                runOperation(password, operation, rotateLeftRightPattern, this::rotateLeftRight);
            } else if (operation.startsWith("reverse")) {
                runOperation(password, operation, reversePositionsPattern, this::reversePositions);
            } else if (operation.startsWith("move")) {
                runOperation(password, operation, movePattern, this::move);
            }
        }
        return password;
    }

    public Object part2(List<String> input) {
        StringBuilder password = new StringBuilder(Util.getStringValueFromUser("Password", "fbgdceah", io));
        for (int i = input.size() - 1; i >= 0; i--) {
            String operation = input.get(i);
            if (operation.startsWith("swap position")) {
                runOperation(password, operation, swapPositionPattern, this::swapPositions);
            } else if (operation.startsWith("swap letter")) {
                runOperation(password, operation, swapLetterPattern, this::swapLetters);
            } else if (operation.startsWith("rotate based")) {
                runOperation(password, operation, rotateBasedOnLetterPositionPattern, this::reverseRotateBasedOnLetterPosition);
            } else if (operation.startsWith("rotate")) {
                runOperation(password, operation, rotateLeftRightPattern, this::reverseRotateLeftRight);
            } else if (operation.startsWith("reverse")) {
                runOperation(password, operation, reversePositionsPattern, this::reversePositions);
            } else if (operation.startsWith("move")) {
                runOperation(password, operation, movePattern, this::reverseMove);
            }
        }
        return password;
    }

    private void runOperation(StringBuilder password, String operation, Pattern operationPattern, PasswordModifier modifier) {
        Matcher matcher = operationPattern.matcher(operation);
        if (matcher.matches()) {
            modifier.modify(password, matcher.group(1), matcher.group(2));
        }
    }

    private void move(StringBuilder password, String sourceAsString, String targetAsString) {
        int pos1 = Integer.parseInt(sourceAsString);
        int pos2 = Integer.parseInt(targetAsString);
        String pos1String = password.substring(pos1, pos1 + 1);
        password.delete(pos1, pos1 + 1);
        password.insert(pos2, pos1String);
    }

    private void reverseMove(StringBuilder password, String sourceAsString, String targetAsString) {
        move(password, targetAsString, sourceAsString);
    }

    private void reversePositions(StringBuilder password, String startPositionAsString, String endPositionAsString) {
        int pos1 = Integer.parseInt(startPositionAsString);
        int pos2 = Integer.parseInt(endPositionAsString);
        password.replace(pos1, pos2 + 1, new StringBuilder(password.substring(pos1, pos2 + 1)).reverse().toString());
    }

    private void rotateLeftRight(StringBuilder password, String leftOrRight, String rotationAsString) {
        rotateLeftRightInternal(password, "left", leftOrRight, rotationAsString);
    }

    private void reverseRotateLeftRight(StringBuilder password, String leftOrRight, String rotationAsString) {
        rotateLeftRightInternal(password, "right", leftOrRight, rotationAsString);
    }

    private void rotateLeftRightInternal(StringBuilder password, String directionToInvertRotation, String leftOrRight, String rotationAsString) {
        int rotation = Integer.parseInt(rotationAsString);
        if (directionToInvertRotation.equals(leftOrRight)) {
            rotation = password.length() - rotation;
        }
        rotate(password, rotation);
    }

    private void rotateBasedOnLetterPosition(StringBuilder password, String letter, String unusedString) {
        int posOfLetter = password.indexOf(letter);
        int rotation = posOfLetter + 1;
        if (posOfLetter >= 4) {
            rotation++;
        }
        rotate(password, rotation);
    }

    private void reverseRotateBasedOnLetterPosition(StringBuilder password, String letter, String unusedString) {
        int rotationCounter = 0;
        int posOfLetter;
        int requiredRotationCounter;
        do {
            rotationCounter++;
            rotate(password, password.length() - 1);
            posOfLetter = password.indexOf(letter);
            requiredRotationCounter = posOfLetter + 1;
            if (posOfLetter >= 4) {
                requiredRotationCounter++;
            }
        } while (rotationCounter != requiredRotationCounter);
    }

    private void swapLetters(StringBuilder password, String letter1String, String letter2String) {
        int pos1 = password.indexOf(letter1String);
        int pos2 = password.indexOf(letter2String);
        swapStrings(password, pos1, pos2, letter1String, letter2String);
    }

    private void swapPositions(StringBuilder password, String position1AsString, String position2AsString) {
        int pos1 = Integer.parseInt(position1AsString);
        int pos2 = Integer.parseInt(position2AsString);
        String pos1String = password.substring(pos1, pos1 + 1);
        String pos2String = password.substring(pos2, pos2 + 1);
        swapStrings(password, pos1, pos2, pos1String, pos2String);
    }

    private void rotate(StringBuilder password, int rotation) {
        int rotationToUse = password.length() - rotation % password.length();
        String stringToRotate = password.substring(rotationToUse);
        password.delete(rotationToUse, password.length());
        password.insert(0, stringToRotate);
    }

    private void swapStrings(StringBuilder password, int pos1, int pos2, String pos1String, String pos2String) {
        password.replace(pos1, pos1 + 1, pos2String);
        password.replace(pos2, pos2 + 1, pos1String);
    }

}
