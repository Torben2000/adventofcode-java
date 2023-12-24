package de.beachboys.aoc2022;

import de.beachboys.Day;
import de.beachboys.OCR;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day10 extends Day {

    private int currentCycle = 1;
    private int currentX = 1;
    private int part1Result = 0;
    private final Set<Tuple2<Integer, Integer>> pixels = new HashSet<>();

    public Object part1(List<String> input) {
        runLogic(input, this::checkPart1);

        return part1Result;
    }

    public Object part2(List<String> input) {
        runLogic(input, this::checkPart2);

        return OCR.runOCRAndReturnOriginalOnError(Util.paintSet(pixels));
    }

    private void runLogic(List<String> input, Runnable check) {
        resetFields();
        for (String line : input) {
            currentCycle++;
            check.run();
            String[] split = line.split(" ");
            if ("addx".equals(split[0])) {
                int value = Integer.parseInt(split[1]);
                currentX += value;
                currentCycle++;
                check.run();
            }
        }
    }

    private void resetFields() {
        currentX = 1;
        currentCycle = 1;
        part1Result = 0;
        pixels.clear();
        pixels.add(Tuple.tuple(0, 0));
    }

    private void checkPart1() {
        if (currentCycle % 40 == 20) {
            part1Result += currentCycle * currentX;
        }
    }

    private void checkPart2() {
        int x = (currentCycle - 1) % 40;
        if (x - 1 <= currentX && x + 1 >= currentX) {
            pixels.add(Tuple.tuple(x, (currentCycle - 1) / 40));
        }
    }

}
