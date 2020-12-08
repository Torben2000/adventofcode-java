package de.beachboys.aoc2020;

import de.beachboys.Day;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day08 extends Day {

    public Object part1(List<String> input) {
        return runProgram(input).getValue1();
    }

    public Object part2(List<String> input) {
        List<String> opStringList = new ArrayList<>(input);
        for (int i = 0; i < opStringList.size(); i++) {
            String original = opStringList.get(i);
            Pair<String, Integer> op = getOp(original);
            if ("jmp".equals(op.getValue0()) || "nop".equals(op.getValue0())) {
                if ("jmp".equals(op.getValue0())) {
                    opStringList.set(i, original.replace("jmp", "nop"));
                } else {
                    opStringList.set(i, original.replace("nop", "jmp"));
                }
                Pair<Boolean, Integer> programResult = runProgram(opStringList);
                if (programResult.getValue0()) {
                    return programResult.getValue1();
                }
                opStringList.set(i, original);
            }
        }
        return -1;
    }

    private Pair<Boolean, Integer> runProgram(List<String> input) {
        int acc = 0;
        Set<Integer> doneOpIndexes = new HashSet<>();
        int i = 0;
        while (true) {
            if (i >= input.size()) {
                return Pair.with(true, acc);
            }
            if (doneOpIndexes.contains(i)) {
                return Pair.with(false, acc);
            }
            doneOpIndexes.add(i);

            Pair<String, Integer> op = getOp(input.get(i));
            switch (op.getValue0()) {
                case "acc":
                    acc += op.getValue1();
                    i += 1;
                    break;
                case "jmp":
                    i += op.getValue1();
                    break;
                case "nop":
                    i += 1;
                    break;
            }
        }
    }

    private Pair<String, Integer> getOp(String opAsString) {
        String[] splitOp = opAsString.split(" ");
        return Pair.with(splitOp[0], Integer.valueOf(splitOp[1]));
    }

}
