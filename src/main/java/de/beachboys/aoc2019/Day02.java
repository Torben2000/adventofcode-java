package de.beachboys.aoc2019;

import de.beachboys.Day;
import de.beachboys.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day02 extends Day {

    private final IntcodeComputer computer = new IntcodeComputer();

    public Object part1(List<String> input) {
        List<Long> list = Util.parseLongCsv(input.get(0));

        computer.runLogic(list, io);

        io.logDebug("Complete list " + list.toString());
        return list.stream().map(x -> x + "").collect(Collectors.joining(","));
    }
    public Object part2(List<String> input) {
        List<Long> list = Util.parseLongCsv(input.get(0));
        List<Long> listNew;
        for (long i = 0; i<100; i++) {
            for (long j = 0; j< 100; j++) {
                listNew = new ArrayList<>(list);
                listNew.set(1, i);
                listNew.set(2, j);
                try {
                    computer.runLogic(listNew, io);
                    if (listNew.get(0) == 19690720) {
                        return (listNew.get(1)*100+listNew.get(2));
                    }
                } catch (Exception e) {
                    // no handling
                }

            }
        }

        return "error";
    }

}
