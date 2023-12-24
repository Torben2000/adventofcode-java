package de.beachboys.aoc2021;

import de.beachboys.Day;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day03 extends Day {

    public Object part1(List<String> input) {
        StringBuilder gammaRate = new StringBuilder();
        StringBuilder epsilonRate = new StringBuilder();
        for (int i = 0; i < input.getFirst().length(); i++) {
            if (moreOrEqualOnesAtPosition(input, i)) {
                gammaRate.append("1");
                epsilonRate.append("0");
            } else {
                gammaRate.append("0");
                epsilonRate.append("1");
            }
        }
        return Integer.parseInt(gammaRate.toString(), 2) * Integer.parseInt(epsilonRate.toString(), 2);
    }

    public Object part2(List<String> input) {
        String oxygenGeneratorRating = getRating(input, '1', '0');
        String co2ScrubberRating = getRating(input, '0', '1');
        return Integer.parseInt(oxygenGeneratorRating, 2) * Integer.parseInt(co2ScrubberRating, 2);
    }

    private String getRating(List<String> input, char charForMoreOnes, char charForLessOnes) {
        Set<String> lines = new HashSet<>(input);
        int position = 0;
        while (lines.size() > 1) {
            char charAtPosition = moreOrEqualOnesAtPosition(lines, position) ? charForMoreOnes : charForLessOnes;
            Set<String> linesToRemove = new HashSet<>();
            for (String line : lines) {
                if (line.charAt(position) != charAtPosition) {
                    linesToRemove.add(line);
                }
            }
            lines.removeAll(linesToRemove);
            position++;
        }
        return lines.stream().findFirst().orElseThrow();
    }

    private boolean moreOrEqualOnesAtPosition(Collection<String> set, int position) {
        int ones = 0;
        int zeros = 0;
        for (String line : set) {
            char c = line.charAt(position);
            if (c == '1') {
                ones++;
            } else {
                zeros++;
            }
        }
        return ones >= zeros;
    }

}
