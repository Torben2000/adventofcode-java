package de.beachboys.aoc2023;

import de.beachboys.Day;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day08 extends Day {

    public Object part1(List<String> input) {
        char[] instructions = input.getFirst().toCharArray();
        Map<String, Tuple2<String, String>> networkMap = getNetworkMap(input);
        return runLogic(List.of("AAA"), networkMap, instructions);
    }

    public Object part2(List<String> input) {
        char[] instructions = input.getFirst().toCharArray();
        Map<String, Tuple2<String, String>> networkMap = getNetworkMap(input);
        List<String> currentNodes = new ArrayList<>();
        for (String node : networkMap.keySet()) {
            if (node.endsWith("A")) {
                currentNodes.add(node);
            }
        }
        return runLogic(currentNodes, networkMap, instructions);
    }

    private static long runLogic(List<String> currentNodes, Map<String, Tuple2<String, String>> networkMap, char[] instructions) {
        List<Long> steps = new ArrayList<>();
        for (String currentNode : currentNodes) {
            int instructionIndex = 0;
            long stepCounter = 0;
            while (!currentNode.endsWith("Z")) {
                stepCounter++;
                Tuple2<String, String> targetNodes = networkMap.get(currentNode);
                if (instructions[instructionIndex] == 'L') {
                    currentNode = targetNodes.v1;
                } else {
                    currentNode = targetNodes.v2;
                }
                instructionIndex = (instructionIndex + 1) % instructions.length;
            }
            steps.add(stepCounter);
        }
        return steps.stream().mapToLong(Long::longValue).reduce(Util::leastCommonMultiple).orElseThrow();
    }

    private static Map<String, Tuple2<String, String>> getNetworkMap(List<String> input) {
        Map<String, Tuple2<String, String>> map = new HashMap<>();
        for (int i = 2; i < input.size(); i++) {
            String line = input.get(i);
            String[] startAndTargetStrings = line.split( " = ");
            String[] targetStrings = startAndTargetStrings[1].substring(1, "(BBB, CCC".length()).split(", ");
            map.put(startAndTargetStrings[0], Tuple.tuple(targetStrings[0], targetStrings[1]));
        }
        return map;
    }

}
