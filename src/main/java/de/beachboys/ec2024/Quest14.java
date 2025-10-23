package de.beachboys.ec2024;

import de.beachboys.Quest;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple3;

import java.util.*;

public class Quest14 extends Quest {

    private int maxHeight = 0;
    private final Set<Tuple3<Integer, Integer, Integer>> treeSegments = new HashSet<>();
    private final Set<Tuple3<Integer, Integer, Integer>> leaves = new HashSet<>();
    @Override
    public Object part1(List<String> input) {
        parseTree(input);
        return maxHeight;
    }

    @Override
    public Object part2(List<String> input) {
        parseTree(input);
        return treeSegments.size();
    }

    @Override
    public Object part3(List<String> input) {
        parseTree(input);
        int minMurkiness = Integer.MAX_VALUE;
        for (int i = 1; i <= maxHeight; i++) {
            Tuple3<Integer, Integer, Integer> trunkSegment = Tuple.tuple(0, i, 0);
            if (treeSegments.contains(trunkSegment)) {
                int murkiness = 0;
                for (Tuple3<Integer, Integer, Integer> leaf : leaves) {
                    murkiness += getMurkiness(trunkSegment, leaf);
                }
                minMurkiness = Math.min(minMurkiness, murkiness);
            }
        }
        return minMurkiness;
    }

    private void parseTree(List<String> input) {
        maxHeight = 0;
        treeSegments.clear();
        leaves.clear();
        for (String branch : input) {
            Tuple3<Integer, Integer, Integer> currentSegment = Tuple.tuple(0,0,0);
            List<String> steps = Util.parseToList(branch, ",");
            for (String step : steps) {
                int i = Integer.parseInt(step.substring(1));
                if (step.startsWith("U")) {
                    for (int j = 0; j < i; j++) {
                        currentSegment = Tuple.tuple(currentSegment.v1, currentSegment.v2+1, currentSegment.v3);
                        treeSegments.add(currentSegment);
                    }
                    maxHeight = Math.max(maxHeight, currentSegment.v2);
                } else if (step.startsWith("D")) {
                    for (int j = 0; j < i; j++) {
                        currentSegment = Tuple.tuple(currentSegment.v1, currentSegment.v2-1, currentSegment.v3);
                        treeSegments.add(currentSegment);
                    }
                } else if (step.startsWith("L")) {
                    for (int j = 0; j < i; j++) {
                        currentSegment = Tuple.tuple(currentSegment.v1-1, currentSegment.v2, currentSegment.v3);
                        treeSegments.add(currentSegment);
                    }
                } else if (step.startsWith("R")) {
                    for (int j = 0; j < i; j++) {
                        currentSegment = Tuple.tuple(currentSegment.v1+1, currentSegment.v2, currentSegment.v3);
                        treeSegments.add(currentSegment);
                    }
                } else if (step.startsWith("F")) {
                    for (int j = 0; j < i; j++) {
                        currentSegment = Tuple.tuple(currentSegment.v1, currentSegment.v2, currentSegment.v3+1);
                        treeSegments.add(currentSegment);
                    }
                } else if (step.startsWith("B")) {
                    for (int j = 0; j < i; j++) {
                        currentSegment = Tuple.tuple(currentSegment.v1, currentSegment.v2, currentSegment.v3-1);
                        treeSegments.add(currentSegment);
                    }
                }
            }
            leaves.add(currentSegment);
        }
    }

    private int getMurkiness(Tuple3<Integer, Integer, Integer> trunkSegment, Tuple3<Integer, Integer, Integer> leaf) {
        Set<Tuple3<Integer, Integer, Integer>> notSeen = new HashSet<>(treeSegments);
        Map<Integer, Set<Tuple3<Integer, Integer, Integer>>> queue = new HashMap<>();
        queue.put(0, Set.of(trunkSegment));

        for (int distance = 0; distance < Integer.MAX_VALUE; distance++) {
            Set<Tuple3<Integer, Integer, Integer>> positions = queue.get(distance);
            if (positions != null) {
                for (Tuple3<Integer, Integer, Integer> pos : positions) {
                    if (leaf.equals(pos)) {
                        return distance;
                    }
                    notSeen.remove(pos);
                    List<Tuple3<Integer, Integer, Integer>> next = List.of(
                            Tuple.tuple(pos.v1+1, pos.v2, pos.v3),
                            Tuple.tuple(pos.v1-1, pos.v2, pos.v3),
                            Tuple.tuple(pos.v1, pos.v2+1, pos.v3),
                            Tuple.tuple(pos.v1, pos.v2-1, pos.v3),
                            Tuple.tuple(pos.v1, pos.v2, pos.v3+1),
                            Tuple.tuple(pos.v1, pos.v2, pos.v3-1));
                    for (Tuple3<Integer, Integer, Integer> newPos : next) {
                       if (notSeen.contains(newPos)) {
                            int newDistance = distance + 1;
                            Set<Tuple3<Integer, Integer, Integer>> newPositions = queue.getOrDefault(newDistance, new HashSet<>());
                            newPositions.add(newPos);
                            queue.put(newDistance, newPositions);
                        }
                    }
                }
            }

        }
        throw new IllegalArgumentException();
    }
}
