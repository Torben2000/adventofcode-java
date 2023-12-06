package de.beachboys.aoc2023;

import de.beachboys.Day;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;

public class Day06 extends Day {

    public Object part1(List<String> input) {
        return runLogic(input, false);
    }

    public Object part2(List<String> input) {
        return runLogic(input, true);
    }

    private static long runLogic(List<String> input, boolean isJustOneRace) {
        List<Pair<Long, Long>> races = parseInput(input, isJustOneRace);

        long result = 1;
        for (Pair<Long, Long> race : races) {
            int count = 0;
            for (long j = 1; j < race.getValue0(); j++) {
                if (race.getValue1() < j * (race.getValue0() - j)) {
                    count++;
                }
            }
            result *=count;
        }
        return result;
    }

    private static List<Pair<Long, Long>> parseInput(List<String> input, boolean isJustOneRace) {
        String multiSpaceReplacement;
        if (isJustOneRace) {
            multiSpaceReplacement = "";
        } else {
            multiSpaceReplacement = " ";
        }
        List<Long> times = new ArrayList<>();
        List<Long> distances = new ArrayList<>();
        for (String line : input) {
            String[] numbersAsStrings = line.split(": ")[1].trim().replaceAll(" +", multiSpaceReplacement).split(" ");
            for (String numberAsString : numbersAsStrings) {
                if (line.startsWith("Time")) {
                    times.add(Long.parseLong(numberAsString));
                } else {
                    distances.add(Long.parseLong(numberAsString));
                }
            }
        }
        List<Pair<Long, Long>> races = new ArrayList<>();
        for (int i = 0; i < times.size(); i++) {
            races.add(Pair.with(times.get(i), distances.get(i)));
        }
        return races;
    }

}
