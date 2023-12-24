package de.beachboys.aoc2023;

import de.beachboys.Day;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

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
        List<Tuple2<Long, Long>> races = parseInput(input, isJustOneRace);

        long result = 1;
        for (Tuple2<Long, Long> race : races) {
            long leftBound = getInnerBound(race, race.v1 - 1, 1);
            long rightBound = getInnerBound(race, leftBound, race.v1 - 1);
            result *= (rightBound - leftBound + 1);
        }
        return result;
    }

    private static long getInnerBound(Tuple2<Long, Long> race, long extremeInnerBound, long extremeOuterBound) {
        long outerBound = extremeOuterBound;
        long innerBound = extremeInnerBound;
        while (Math.abs(outerBound - innerBound) != 1) {
            long toCheck = (outerBound + innerBound) / 2;
            if (race.v2 < toCheck * (race.v1 - toCheck)) {
                innerBound = toCheck;
            } else {
                outerBound = toCheck;
            }
        }
        return innerBound;
    }

    private static List<Tuple2<Long, Long>> parseInput(List<String> input, boolean isJustOneRace) {
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
        List<Tuple2<Long, Long>> races = new ArrayList<>();
        for (int i = 0; i < times.size(); i++) {
            races.add(Tuple.tuple(times.get(i), distances.get(i)));
        }
        return races;
    }

}
