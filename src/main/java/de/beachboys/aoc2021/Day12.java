package de.beachboys.aoc2021;

import de.beachboys.Day;

import java.util.*;

public class Day12 extends Day {

    private final Map<String, List<String>> graph = new HashMap<>();

    public Object part1(List<String> input) {
        parseGraph(input);
        return findWaysToEnd(List.of(), "start", false, Set.of()).size();
    }

    public Object part2(List<String> input) {
        parseGraph(input);
        return findWaysToEnd(List.of(), "start", true, Set.of()).size();
    }

    private void parseGraph(List<String> input) {
        graph.clear();
        for (String line : input) {
            String[] nodes = line.split("-");
            graph.putIfAbsent(nodes[0], new ArrayList<>());
            graph.putIfAbsent(nodes[1], new ArrayList<>());
            graph.get(nodes[0]).add(nodes[1]);
            graph.get(nodes[1]).add(nodes[0]);
        }
    }

    private Set<List<String>> findWaysToEnd(List<String> pathToCave, String cave, boolean allowSecondVisitOfOneSmallCave, Set<String> visitedSmallCaves) {
        Set<List<String>> waysToEnd = new HashSet<>();
        if ("end".equals(cave)) {
            waysToEnd.addAll(List.of(pathToCave));
        } else if (pathToCave.size() == 0 || !"start".equals(cave)) {

            boolean allowSecondVisitOfOneSmallCaveNew = allowSecondVisitOfOneSmallCave;
            Set<String> visitedSmallCavesInclCurrent = new HashSet<>(visitedSmallCaves);
            if (allowSecondVisitOfOneSmallCave && visitedSmallCaves.contains(cave)) {
                allowSecondVisitOfOneSmallCaveNew = false;
            } else if (isSmallCave(cave)) {
                visitedSmallCavesInclCurrent.add(cave);
            }

            if (!isSmallCave(cave) || allowSecondVisitOfOneSmallCave || !visitedSmallCaves.contains(cave)) {
                List<String> pathWithCave = new ArrayList<>(pathToCave);
                pathWithCave.add(cave);
                for (String nextCave : graph.get(cave)) {
                    waysToEnd.addAll(findWaysToEnd(pathWithCave, nextCave, allowSecondVisitOfOneSmallCaveNew, visitedSmallCavesInclCurrent));
                }
            }

        }
        return waysToEnd;
    }

    private boolean isSmallCave(String node) {
        return node.toLowerCase().equals(node);
    }

}
