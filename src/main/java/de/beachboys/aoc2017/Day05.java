package de.beachboys.aoc2017;

import de.beachboys.Day;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day05 extends Day {

    public Object part1(List<String> input) {
        return runLogic(input, offset -> offset + 1);
    }

    public Object part2(List<String> input) {
        return runLogic(input, offset -> offset < 3 ? offset + 1 : offset - 1);
    }

    private int runLogic(List<String> input, Function<Integer, Integer> adjustOffset) {
        List<Integer> list = input.stream().map(Integer::valueOf).collect(Collectors.toList());
        int stepCount = 0;
        int pointer = 0;
        while (pointer < list.size()) {
            stepCount++;
            Integer offset = list.get(pointer);
            list.set(pointer, adjustOffset.apply(offset));
            pointer += offset;
        }
        return stepCount;
    }

}
