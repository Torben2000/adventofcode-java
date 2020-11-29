package de.beachboys.aoc2019;

import de.beachboys.Day;
import de.beachboys.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Day05 implements Day {

    private IntcodeComputer computer = new IntcodeComputer();

    public Object part1(List<String> input) {
        List<Integer> list = Util.parseIntCsv(input.get(0));

        computer.runLogic(list);

        System.out.println("Complete list " + list.toString());
        return list.stream().map(x -> x + "").collect(Collectors.joining(","));
    }


    public Object part2(List<String> input) {
        return part1(input);
    }

}
