package de.beachboys.aoc2020;

import de.beachboys.Day;
import org.javatuples.Pair;

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
        Pair<Integer, Long> foundMatch = Pair.with(0, -1L);
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
                    if (indexDiff > foundMatch.getValue0()) {
                        foundMatch = Pair.with(indexDiff, min + max);
                    }
                }
            }

        }
        return foundMatch.getValue1();
    }

    private long getFirstWrongSum(List<Long> longList) {
        int preamble = 25;
        String preambleAsInput = io.getInput("Preamble (default 25):");
        if (!preambleAsInput.isEmpty()) {
            preamble = Integer.parseInt(preambleAsInput);
        }
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
