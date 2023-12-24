package de.beachboys.aoc2020;

import de.beachboys.Day;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.List;
import java.util.stream.Collectors;

public class Day09 extends Day {

    public Long part1(List<String> input) {
        List<Long> longList = input.stream().map(Long::valueOf).collect(Collectors.toList());
        return getFirstWrongSum(longList);
    }

    public Object part2(List<String> input) {
        List<Long> longList = input.stream().map(Long::valueOf).collect(Collectors.toList());
        long wrongValue = getFirstWrongSum(longList);
        Tuple2<Integer, Long> foundMatch = Tuple.tuple(0, -1L);
        for (int i = 0; i < longList.size(); i++) {
            for (int j = i + 1; j < longList.size(); j++) {
                long sum = 0L;
                long min = 0L;
                long max = Long.MAX_VALUE;
                for (int k = i; k <= j; k++) {
                    Long currValue = longList.get(k);
                    if (min < currValue) {
                        min = currValue;
                    }
                    if (max > currValue) {
                        max = currValue;
                    }
                    sum += currValue;
                }
                if (sum == wrongValue) {
                    int indexDiff = j - i;
                    if (indexDiff > foundMatch.v1) {
                        foundMatch = Tuple.tuple(indexDiff, min + max);
                    }
                }
            }

        }
        return foundMatch.v2;
    }

    private long getFirstWrongSum(List<Long> longList) {
        int preamble = Util.getIntValueFromUser("Preamble", 25, io);
        for (int i = preamble; i < longList.size(); i++) {
            boolean matchFound = false;
            for (int j = i - preamble; j < i; j++) {
                for (int k = j + 1; k < i; k++) {
                    if (longList.get(j) + longList.get(k) == longList.get(i)) {
                        matchFound = true;
                        break;
                    }
                }
            }
            if (!matchFound) {
                return longList.get(i);
            }
        }
        return -1L;
    }

}
