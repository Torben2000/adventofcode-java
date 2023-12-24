package de.beachboys.aoc2019;

import de.beachboys.Day;

import java.util.List;
import java.util.function.Consumer;

public class Day16 extends Day {

    public Object part1(List<String> input) {
        return calculateResult(input.getFirst(), 0);
    }

    public Object part2(List<String> input) {
        String numbers = input.getFirst().repeat(10000);
        int offset = Integer.parseInt(numbers.substring(0, 7));
        return calculateResult(numbers, offset);
    }

    private String calculateResult(String numbers, int offset) {
        int numberOfPhases = Integer.parseInt(io.getInput("Number of phases"));
        Consumer<int[]> recalculationLogic;
        if (offset >= numbers.length() / 2) {
            recalculationLogic = this::recalculateDigitsFastButOnlyCorrectForSecondHalf;
        } else {
            recalculationLogic = this::recalculateDigitsSlowButCorrect;
        }
        for (int i = 0; i < numberOfPhases; i++) {
            numbers = runPhase(numbers, recalculationLogic);
        }
        return numbers.substring(offset, offset + 8);
    }

    private String runPhase(String numbers, Consumer<int[]> recalculationLogic) {
        int[] digits = getDigits((numbers));
        recalculationLogic.accept(digits);
        return getNumberString(digits);
    }

    private void recalculateDigitsSlowButCorrect(int[] digits) {
        for (int i = 0; i < digits.length; i++) {
            int currentValue = 0;
            for (int j = i; j < digits.length; j++) {
                currentValue += digits[j] * getFactor(i, j);
            }
            digits[i] = Math.abs(currentValue % 10);
        }
    }

    private int getFactor(int i, int j) {
        return switch (((((j + 1) / (i + 1)) + 1) % 4)) {
            case 0 -> -1;
            case 1, 3 -> 0;
            case 2 -> 1;
            default -> throw new IllegalArgumentException();
        };
    }

    private void recalculateDigitsFastButOnlyCorrectForSecondHalf(int[] digits) {
        int currentValue = 0;
        for (int i = digits.length - 1; i >= digits.length / 2; i--) {
            currentValue += digits[i];
            currentValue %= 10;
            digits[i] = currentValue;
        }
    }

    private String getNumberString(int[] digits) {
        StringBuilder newNumber = new StringBuilder();
        for (int digit : digits) {
            newNumber.append(digit);
        }
        return newNumber.toString();
    }

    private int[] getDigits(String numbers) {
        int[] digits = new int[numbers.length()];
        for (int i = 0; i < digits.length; i++) {
            digits[i] = numbers.charAt(i) - 48;
        }
        return digits;
    }

}
