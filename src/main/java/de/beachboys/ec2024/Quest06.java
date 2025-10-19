package de.beachboys.ec2024;

import de.beachboys.Quest;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple3;

import java.util.*;

public class Quest06 extends Quest {

    @Override
    public Object part1(List<String> input) {
        return runLogic(input, false);
    }

    @Override
    public Object part2(List<String> input) {
        return runLogic(input, true);
    }

    @Override
    public Object part3(List<String> input) {
        return runLogic(input, true);
    }

    private static String runLogic(List<String> input, boolean onlyFirstLetter) {
        Map<String, List<String>> tree = parseTree(input);

        Map<Integer, Set<String>> pathsByLength = new HashMap<>();
        Deque<Tuple3<String, String, Integer>> queue = new LinkedList<>();
        Set<String> history = new HashSet<>();
        queue.add(buildQueueEntry("RR", "", 0, onlyFirstLetter));
        while (!queue.isEmpty()) {
            Tuple3<String, String, Integer> queueEntry = queue.poll();
            if (!history.contains(queueEntry.v1)) {
                history.add(queueEntry.v1);
                if (tree.containsKey(queueEntry.v1)) {
                    for (String currentBranch : tree.get(queueEntry.v1)) {
                        if ("@".equals(currentBranch)) {
                            Set<String> pathsWithSameLength = pathsByLength.getOrDefault(queueEntry.v3, new HashSet<>());
                            pathsWithSameLength.add(queueEntry.v2 + currentBranch);
                            pathsByLength.put(queueEntry.v3, pathsWithSameLength);
                        } else {
                            queue.add(buildQueueEntry(currentBranch, queueEntry.v2, queueEntry.v3, onlyFirstLetter));
                        }
                    }
                }
            }
        }
        return pathsByLength.values().stream().filter(e -> e.size() == 1).findFirst().orElseThrow().stream().findFirst().orElseThrow();
    }

    private static Map<String, List<String>> parseTree(List<String> input) {
        Map<String, List<String>> tree = new HashMap<>();
        for (String line : input) {
            String[] branchAndSubBranches = line.split(":");
            tree.put(branchAndSubBranches[0], List.of(branchAndSubBranches[1].split(",")));
        }
        return tree;
    }

    private static Tuple3<String, String, Integer> buildQueueEntry(String newBranch, String currentPath, int currentPathLength, boolean onlyFirstLetter) {
        String pathAddition = onlyFirstLetter ? newBranch.substring(0,1) : newBranch;
        return Tuple.tuple(newBranch, currentPath + pathAddition, currentPathLength + 1);
    }
}
