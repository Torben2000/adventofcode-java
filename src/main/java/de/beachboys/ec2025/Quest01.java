package de.beachboys.ec2025;

import de.beachboys.Quest;
import de.beachboys.Util;

import java.util.List;

public class Quest01 extends Quest {

    @Override
    public Object part1(List<String> input) {
        List<String> names = Util.parseCsv(input.getFirst());
        List<String> instructions = Util.parseCsv(input.get(2));
        int index = 0;
        for (String instruction : instructions) {
            if (instruction.charAt(0) == 'L') {
                index = Math.max(0, index - Integer.parseInt(instruction.substring(1)));
            } else {
                index = Math.min(names.size() - 1, index + Integer.parseInt(instruction.substring(1)));
            }
        }
        return names.get(index);
    }

    @Override
    public Object part2(List<String> input) {
        List<String> names = Util.parseCsv(input.getFirst());
        List<String> instructions = Util.parseCsv(input.get(2));
        int index = 0;
        for (String instruction : instructions) {
            if (instruction.charAt(0) == 'L') {
                index = Util.modPositive(index - Integer.parseInt(instruction.substring(1)), names.size());
            } else {
                index = Util.modPositive(index + Integer.parseInt(instruction.substring(1)), names.size());
            }
        }
        return names.get(index);
    }

    @Override
    public Object part3(List<String> input) {
        List<String> names = Util.parseCsv(input.getFirst());
        List<String> instructions = Util.parseCsv(input.get(2));
        int index;
        for (String instruction : instructions) {
            if (instruction.charAt(0) == 'L') {
                index = Util.modPositive(-Integer.parseInt(instruction.substring(1)), names.size());
            } else {
                index = Util.modPositive(Integer.parseInt(instruction.substring(1)), names.size());
            }

            String temp = names.get(index);
            names.set(index, names.getFirst());
            names.set(0, temp);
        }
        return names.getFirst();
    }
}
