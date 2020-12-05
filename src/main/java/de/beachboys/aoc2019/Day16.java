package de.beachboys.aoc2019;

import de.beachboys.Day;

import java.util.List;
import java.util.function.Consumer;

public class Day16 extends Day {

    public Object part1(List<String> input) {
        return calculateResult(input.get(0), 0, this::recalculateDigitsPart1);
    }

    public Object part2(List<String> input) {
        String numbers = input.get(0).repeat(10000);
        int offset = Integer.parseInt(numbers.substring(0, 7));
        return calculateResult(numbers, offset, this::recalculateDigitsPart2);
    }

    private String calculateResult(String numbers, int offset, Consumer<int[]> recalculationLogic) {
        int numberOfPhases = Integer.parseInt(io.getInput("Number of phases"));
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

    private void recalculateDigitsPart1(int[] digits) {
        for (int i = 0; i < digits.length; i++) {
            int currentValue = 0;
            for (int j = i; j < digits.length; j++) {
                currentValue += digits[j] * getFactor(i, j);
            }
            digits[i] = Math.abs(currentValue % 10);
        }
    }

    private int getFactor(int i, int j) {
        switch (((((j + 1) / (i + 1)) + 1) % 4)) {
            case 0:
                return -1;
            case 1:
            case 3:
                return 0;
            case 2:
                return 1;
        }
        throw new IllegalArgumentException();
    }

    private void recalculateDigitsPart2(int[] digits) {
        // Magic that I only get partially...but it works
        int currentValue = 0;
        for (int i = digits.length - 1; i >= 0; i--) {
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
