package de.beachboys.aoc2023;

import de.beachboys.Day;
import de.beachboys.Direction;
import de.beachboys.Util;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jooq.lambda.tuple.Tuple2;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day23 extends Day {

    public static final String START_VERTEX = "S";
    public static final String END_VERTEX = "E";
    private Graph<String, DefaultWeightedEdge> graphPart2;
    private Map<Tuple2<Integer, Integer>, String> map;
    private Tuple2<Integer, Integer> start;
    private Tuple2<Integer, Integer> end;

    public Object part1(List<String> input) {
        parseInput(input);

        return getMaxDistanceToEnd(start, 0, new HashSet<>());
    }

    private int getMaxDistanceToEnd(Tuple2<Integer, Integer> pos, int distanceFromStart, Set<Tuple2<Integer, Integer>> history) {
        if (end.equals(pos)) {
            return distanceFromStart;
        }
        if (history.contains(pos)) {
            return 0;
        }
        int result = 0;
        history.add(pos);
        String mapChar = map.get(pos);
        if (".".equals(mapChar)) {
            for (Direction dir : Direction.values()) {
                Tuple2<Integer, Integer> newPos = dir.move(pos, 1);
                if (!history.contains(newPos) && !"#".equals(map.getOrDefault(newPos, "#"))) {
                    result = Math.max(getMaxDistanceToEnd(newPos, distanceFromStart + 1, history), result);
                }
            }
        } else {
            Tuple2<Integer, Integer> newPos = Direction.fromString(mapChar).move(pos, 1);
            if (!history.contains(newPos)) {
                result = getMaxDistanceToEnd(newPos, distanceFromStart + 1, history);
            }
        }
        history.remove(pos);
        return result;
    }

    public Object part2(List<String> input) {
        parseInput(input);
        modifyMapAndBuildGraphForPart2();

        return getMaxDistanceToEnd(START_VERTEX,0, new HashSet<>());
    }

    private int getMaxDistanceToEnd(String vertex, int distanceFromStart, Set<String> history) {
        int result = 0;
        if (history.contains(vertex)) {
            return 0;
        }
        if (END_VERTEX.equals(vertex)) {
            return distanceFromStart;
        }
        history.add(vertex);
        for (DefaultWeightedEdge edge : graphPart2.outgoingEdgesOf(vertex)) {
            int edgeDistance = (int) graphPart2.getEdgeWeight(edge);
            String newVertex = Util.getOtherVertex(graphPart2, edge, vertex);
            result = Math.max(getMaxDistanceToEnd(newVertex, distanceFromStart + edgeDistance, history), result);
        }
        history.remove(vertex);
        return result;
    }

    private void parseInput(List<String> input) {
        map = Util.buildImageMap(input);
        start = map.keySet().stream().filter(p -> p.v2 == 0 && ".".equals(map.get(p))).findFirst().orElseThrow();
        end = map.keySet().stream().filter(p -> p.v2 == input.size() - 1 && ".".equals(map.get(p))).findFirst().orElseThrow();
    }


    private void modifyMapAndBuildGraphForPart2() {
        for (Tuple2<Integer, Integer> pos : map.keySet()) {
            String s = map.get(pos);
            if ("^".equals(s) || "v".equals(s) || "<".equals(s) || ">".equals(s)) {
                map.put(pos, ".");
            }
        }
        map.put(start, START_VERTEX);
        map.put(end, END_VERTEX);

        graphPart2 = Util.buildGraphFromMap(map, start);
    }

}
