package de.beachboys.ec2025;

import de.beachboys.Quest;

import java.util.ArrayList;
import java.util.List;

public class Quest11 extends Quest {

    @Override
    public Object part1(List<String> input) {
        List<Long> ducksPerColumn = parseInput(input);
        boolean inFirstPhase = true;
        for (int i = 0; i < 10; i++) {
            if (inFirstPhase) {
                inFirstPhase = firstPhaseRound(ducksPerColumn);
            }
            if (!inFirstPhase) {
                secondPhaseRound(ducksPerColumn);
            }
        }

        long result = 0;
        for (int i = 0; i < ducksPerColumn.size(); i++) {
            result += (i + 1) * ducksPerColumn.get(i);
        }
        return result;
    }

    @Override
    public Object part2(List<String> input) {
        List<Long> ducksPerColumn = parseInput(input);
        int i = 0;
        while (firstPhaseRound(ducksPerColumn)) {
            i++;
        }
        return i + secondPhaseFast(ducksPerColumn);
    }

    @Override
    public Object part3(List<String> input) {
        return part2(input);
    }

    private static long secondPhaseFast(List<Long> ducksPerColumn) {
        long totalDucks = 0;
        for (long column : ducksPerColumn) {
            totalDucks += column;
        }
        long finalDucksPerColumn = totalDucks / ducksPerColumn.size();

        long requiredChanges = 0;
        for (long column : ducksPerColumn) {
            requiredChanges += Math.abs(finalDucksPerColumn - column);
        }

        // per round exactly 2 columns are changed by 1
        return requiredChanges / 2;
    }

    private static boolean firstPhaseRound(List<Long> ducksPerColumn) {
        boolean change = false;
        for (int i = 0; i < ducksPerColumn.size() - 1; i++) {
            if (ducksPerColumn.get(i) > ducksPerColumn.get(i + 1)) {
                ducksPerColumn.set(i, ducksPerColumn.get(i) - 1);
                ducksPerColumn.set(i + 1, ducksPerColumn.get(i + 1) + 1);
                change = true;
            }
        }
        return change;
    }

    private static void secondPhaseRound(List<Long> ducksPerColumn) {
        for (int i = 0; i < ducksPerColumn.size() - 1; i++) {
            if (ducksPerColumn.get(i) < ducksPerColumn.get(i + 1)) {
                ducksPerColumn.set(i, ducksPerColumn.get(i) + 1);
                ducksPerColumn.set(i+1, ducksPerColumn.get(i+1) - 1);
            }
        }
    }

    private static List<Long> parseInput(List<String> input) {
        List<Long> ducksPerColumn = new ArrayList<>();
        for (String line : input) {
            long column = Long.parseLong(line);
            ducksPerColumn.add(column);
        }
        return ducksPerColumn;
    }
}
