package de.beachboys.aoc2023;

import de.beachboys.Day;
import de.beachboys.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day15 extends Day {

    public Object part1(List<String> input) {
        long result = 0;
        List<String> steps = Util.parseCsv(input.getFirst());
        for (String step : steps) {
           result += hash(step);
        }
        return result;
    }

    public Object part2(List<String> input) {
        int result = 0;
        Map<String, Integer> focalLengths = new HashMap<>();
        Map<Integer, List<String>> boxes = new HashMap<>();
        for (int j = 0; j < 256; j++) {
            boxes.put(j, new ArrayList<>());
        }
        List<String> steps = Util.parseCsv(input.getFirst());

        for (String step : steps) {
            if (step.endsWith("-")) {
                String label = step.substring(0, step.length() - 1);
                int boxIndex = hash(label);
                boxes.get(boxIndex).remove(label);
            } else {
                String label = step.substring(0, step.length() - 2);
                int boxIndex = hash(label);
                List<String> lensSlots = boxes.get(boxIndex);
                if (!lensSlots.contains(label)) {
                    lensSlots.add(label);
                }
                int focalLength = Integer.parseInt(step.substring(step.length() - 1));
                focalLengths.put(label, focalLength);
            }
        }

        for (Map.Entry<Integer, List<String>> box : boxes.entrySet()) {
            int value = 0;
            for (int boxIndex = 0; boxIndex < box.getValue().size(); boxIndex++) {
                value += (boxIndex + 1) * focalLengths.get(box.getValue().get(boxIndex));
            }
            result += (box.getKey() + 1) * value;
        }

        return result;
    }

    private static int hash(String s) {
        int currentValue = 0;
        for (char c : s.toCharArray()) {
            currentValue += c;
            currentValue *= 17;
            currentValue %= 256;
        }
        return currentValue;
    }

}
