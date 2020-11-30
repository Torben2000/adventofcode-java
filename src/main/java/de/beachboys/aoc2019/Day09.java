package de.beachboys.aoc2019;

import de.beachboys.Day;
import de.beachboys.IOHelper;
import de.beachboys.Util;

import java.util.ArrayList;
import java.util.List;

public class Day09 extends Day {

    private IntcodeComputer computer = new IntcodeComputer();

    public Object part1(List<String> input) {
        List<Long> list = Util.parseLongCsv(input.get(0));
        computer.runLogic(new ArrayList<>(list), io);
        return computer.getLastOutput();

    }

    public Object part2(List<String> input) {
        return 2;
    }

}
