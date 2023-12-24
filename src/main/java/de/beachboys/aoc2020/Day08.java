package de.beachboys.aoc2020;

import de.beachboys.Day;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day08 extends Day {

    public Object part1(List<String> input) {
        return runProgram(input).v2;
    }

    public Object part2(List<String> input) {
        List<String> opStringList = new ArrayList<>(input);
        for (int i = 0; i < opStringList.size(); i++) {
            String original = opStringList.get(i);
            Tuple2<String, Integer> op = getOp(original);
            if ("jmp".equals(op.v1) || "nop".equals(op.v1)) {
                if ("jmp".equals(op.v1)) {
                    opStringList.set(i, original.replace("jmp", "nop"));
                } else {
                    opStringList.set(i, original.replace("nop", "jmp"));
                }
                Tuple2<Boolean, Integer> programResult = runProgram(opStringList);
                if (programResult.v1) {
                    return programResult.v2;
                }
                opStringList.set(i, original);
            }
        }
        return -1;
    }

    private Tuple2<Boolean, Integer> runProgram(List<String> input) {
        int acc = 0;
        Set<Integer> doneOpIndexes = new HashSet<>();
        int i = 0;
        while (true) {
            if (i >= input.size()) {
                return Tuple.tuple(true, acc);
            }
            if (doneOpIndexes.contains(i)) {
                return Tuple.tuple(false, acc);
            }
            doneOpIndexes.add(i);

            Tuple2<String, Integer> op = getOp(input.get(i));
            switch (op.v1) {
                case "acc":
                    acc += op.v2;
                    i += 1;
                    break;
                case "jmp":
                    i += op.v2;
                    break;
                case "nop":
                    i += 1;
                    break;
            }
        }
    }

    private Tuple2<String, Integer> getOp(String opAsString) {
        String[] splitOp = opAsString.split(" ");
        return Tuple.tuple(splitOp[0], Integer.valueOf(splitOp[1]));
    }

}
