package de.beachboys.aoc2019;

import de.beachboys.Day;
import de.beachboys.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day02 implements Day {

    public Object part1(List<String> input) {
        List<Integer> list = Util.parseIntCsv(input.get(0));

        runLogic(list);

        System.out.println("Complete list " + list.toString());
        return list.stream().map(x -> x + "").collect(Collectors.joining(","));
    }

    private void runLogic(List<Integer> list) {
        int currentIndex = 0;

        int opcode = 0;
        while(opcode != 99) {
            opcode = list.get(currentIndex);
            switch (opcode) {
                case 99:
                    //
                    break;
                case 1:
                    int result = list.get(list.get(currentIndex + 1)) + list.get(list.get(currentIndex + 2));
                    int setIndex = list.get(currentIndex + 3);
                    if (setIndex < list.size()) {
                        list.set(setIndex, result);
                    }
                    break;
                case 2:
                    int result2 = list.get(list.get(currentIndex + 1)) * list.get(list.get(currentIndex + 2));
                    int setIndex2 = list.get(currentIndex + 3);
                    if (setIndex2 < list.size()) {
                        list.set(setIndex2, result2);
                    }
                    break;
                default:
            }
            currentIndex += 4;
        }
    }

    public Object part2(List<String> input) {
        List<Integer> list = Util.parseIntCsv(input.get(0));
        List<Integer> listNew;
        for (int i = 0; i<100; i++) {
            for (int j = 0; j< 100; j++) {
                listNew = new ArrayList<>(list);
                listNew.set(1, i);
                listNew.set(2, j);
                try {
                    runLogic(listNew);
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