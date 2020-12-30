package de.beachboys.aoc2015;

import de.beachboys.Day;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Day23 extends Day {

    private final Map<String, Integer> registers = new HashMap<>();

    private final List<Function<Integer, Integer>> operations = new ArrayList<>();

    public Object part1(List<String> input) {
        parseOperations(input);

        registers.put("a", 0);
        registers.put("b", 0);

        runProgram();

        return registers.get("b");
    }

    public Object part2(List<String> input) {
        parseOperations(input);

        registers.put("a", 1);
        registers.put("b", 0);

        runProgram();

        return registers.get("b");
    }

    private void runProgram() {
        int opIndex = 0;
        while (opIndex < operations.size()) {
            opIndex = operations.get(opIndex).apply(opIndex);
        }
    }

    private void parseOperations(List<String> input) {
        for (String line : input) {
            String[] opAndParams = line.split(" ");
            switch (opAndParams[0]) {
                case "hlf":
                    operations.add(index -> {
                        registers.put(opAndParams[1], registers.get(opAndParams[1]) / 2);
                        return index + 1;
                    });
                    break;
                case "tpl":
                    operations.add(index -> {
                        registers.put(opAndParams[1], registers.get(opAndParams[1]) * 3);
                        return index + 1;
                    });
                    break;
                case "inc":
                    operations.add(index -> {
                        registers.put(opAndParams[1], registers.get(opAndParams[1]) + 1);
                        return index + 1;
                    });
                    break;
                case "jmp":
                    operations.add(index -> index + Integer.parseInt(opAndParams[1]));
                    break;
                case "jie":
                    operations.add(index -> {
                        String register = opAndParams[1].substring(0, 1);
                        if (registers.get(register) % 2 == 0) {
                            return index + Integer.parseInt(opAndParams[2]);
                        }
                        return index + 1;
                    });
                    break;
                case "jio":
                    operations.add(index -> {
                        String register = opAndParams[1].substring(0, 1);
                        if (registers.get(register) == 1) {
                            return index + Integer.parseInt(opAndParams[2]);
                        }
                        return index + 1;
                    });
                    break;
            }
        }
    }
}
