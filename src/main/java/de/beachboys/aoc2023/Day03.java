package de.beachboys.aoc2023;

import de.beachboys.Day;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Day03 extends Day {

    public Object part1(List<String> input) {
        Set<Number> numbers = getNumbers(input);
        Set<Tuple2<Integer, Integer>> symbolPositions = getSymbolPositions(input);

        Set<Number> numbersNextToSymbols = new HashSet<>();
        for (Tuple2<Integer, Integer> symbolPosition : symbolPositions) {
            numbersNextToSymbols.addAll(getAdjacentNumbers(symbolPosition, numbers));
        }

        long result = 0;
        for (Number numberNextToSymbol : numbersNextToSymbols) {
            result += numberNextToSymbol.value;
        }
        return result;
    }

    public Object part2(List<String> input) {
        Set<Tuple2<Integer, Integer>> asteriskPositions = getAsteriskPositions(input);
        Set<Number> numbers = getNumbers(input);

        long result = 0;
        for (Tuple2<Integer, Integer> asteriskPosition : asteriskPositions) {
            Set<Number> adjacentNumbers = getAdjacentNumbers(asteriskPosition, numbers);
            if (adjacentNumbers.size() == 2) {
                result += adjacentNumbers.stream().mapToInt(n -> n.value).reduce((a, b) -> a * b).orElseThrow();
            }
        }
        return result;
    }

    private static Set<Tuple2<Integer, Integer>> getSymbolPositions(List<String> input) {
        Set<Tuple2<Integer, Integer>> symbolPositions = new HashSet<>();
        for (int y = 0; y < input.size(); y++) {
            String line = input.get(y);
            for (int x = 0; x < line.length(); x++) {
                char c = line.charAt(x);
                if (c != '.' && (c < '0' || c > '9')) {
                    symbolPositions.add(Tuple.tuple(x, y));
                }
            }
        }
        return symbolPositions;
    }

    private static Set<Number> getNumbers(List<String> input) {
        Set<Number> numbers = new HashSet<>();
        for (int y = 0; y < input.size(); y++) {
            String line = input.get(y);
            for (int x = 0; x < line.length(); x++) {
                try {
                    int num = Integer.parseInt(line.substring(x, x+1));
                    int minX = x;
                    while (true) {
                        x++;
                        try {
                            num = num * 10 + Integer.parseInt(line.substring(x, x+1));
                        } catch (Exception e) {
                            break;
                        }
                    }
                    numbers.add(new Number(minX, x-1, y, num));
                } catch (NumberFormatException e) {
                    // end of number found
                }
            }
        }
        return numbers;
    }

    private static Set<Number> getAdjacentNumbers(Tuple2<Integer, Integer> position, Set<Number> numbers) {
        Set<Number> adjacentNumbers = new HashSet<>();
        for (int x = position.v1 - 1; x <= position.v1 + 1 ; x++) {
            for (int y = position.v2 - 1; y <= position.v2 + 1; y++) {
                if (position.v1 != x || position.v2 != y) {
                    for (Number number : numbers) {
                        if (number.minX <= x && number.maxX >= x && number.y == y) {
                            adjacentNumbers.add(number);
                            break;
                        }
                    }
                }
            }
        }
        return adjacentNumbers;
    }

    private static Set<Tuple2<Integer, Integer>> getAsteriskPositions(List<String> input) {
        Set<Tuple2<Integer, Integer>> asterisks = new HashSet<>();
        for (int y = 0; y < input.size(); y++) {
            String line = input.get(y);
            for (int x = 0; x < line.length(); x++) {
                if (line.charAt(x) == '*') {
                    asterisks.add(Tuple.tuple(x, y));
                }
            }
        }
        return asterisks;
    }

    private static class Number {
        int minX;
        int maxX;
        int y;
        int value;

        public Number(int minX, int maxX, int y, int value) {
            this.minX = minX;
            this.maxX = maxX;
            this.y = y;
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Number number = (Number) o;
            return minX == number.minX && maxX == number.maxX && y == number.y && value == number.value;
        }

        @Override
        public int hashCode() {
            return Objects.hash(minX, maxX, y, value);
        }
    }

}
