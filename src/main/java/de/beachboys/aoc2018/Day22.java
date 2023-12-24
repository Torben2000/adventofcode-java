package de.beachboys.aoc2018;

import de.beachboys.Day;
import de.beachboys.Util;
import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day22 extends Day {

    private final Map<Tuple2<Integer, Integer>, Long> erosionLevels = new HashMap<>();
    private final Map<Tuple2<Integer, Integer>, Long> terrains = new HashMap<>();

    public Object part1(List<String> input) {
        Tuple2<Integer, Integer> target = fillErosionLevelsAndTerrains(input, 0);

        long sum = 0L;
        for (int i = 0; i <= target.v1; i++) {
            for (int j = 0; j <= target.v2; j++) {
                sum += terrains.get(Tuple.tuple(i, j));
            }
        }
        return sum;
    }

    public Object part2(List<String> input) {
        int additionalColsAndRows = 100;
        Tuple2<Integer, Integer> target = fillErosionLevelsAndTerrains(input, additionalColsAndRows);

        Graph<String, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        for (int i = 0; i < target.v1 + additionalColsAndRows; i++) {
            for (int j = 0; j < target.v2 + additionalColsAndRows; j++) {
                Tuple2<Integer, Integer> pos = Tuple.tuple(i, j);
                Tuple2<Integer, Integer> right = Tuple.tuple(i + 1, j);
                Tuple2<Integer, Integer> down = Tuple.tuple(i, j + 1);
                long terrain = terrains.get(pos);
                long terrainRight = terrains.get(right);
                long terrainDown = terrains.get(down);
                linkToNeighborFieldsIfTerrainMatchesForTool(graph, pos, terrain, right, terrainRight, down,  terrainDown, 0L, 1L, "C");
                linkToNeighborFieldsIfTerrainMatchesForTool(graph, pos, terrain, right, terrainRight, down,  terrainDown, 1L, 2L, "N");
                linkToNeighborFieldsIfTerrainMatchesForTool(graph, pos, terrain, right, terrainRight, down,  terrainDown, 0L, 2L, "T");
                if (terrain == 0L) {
                    Util.addEdge(graph, pos + "C", pos + "T", 7);
                }
                if (terrain == 1L) {
                    Util.addEdge(graph, pos + "C", pos + "N", 7);
                }
                if (terrain == 2L) {
                    Util.addEdge(graph, pos + "N", pos + "T", 7);
                }
            }
        }

        DijkstraShortestPath<String, DefaultWeightedEdge> alg = new DijkstraShortestPath<>(graph);
        return (long) alg.getPath(Tuple.tuple(0, 0) + "T", target + "T").getWeight();
    }

    private Tuple2<Integer, Integer> fillErosionLevelsAndTerrains(List<String> input, int additionalColsAndRows) {
        int depth = Integer.parseInt(input.get(0).substring("depth: ".length()));
        String targetString = input.get(1).substring("target: ".length());
        String[] splitTarget = targetString.split(",");
        Tuple2<Integer, Integer> target = Tuple.tuple(Integer.parseInt(splitTarget[0]), Integer.parseInt(splitTarget[1]));
        Tuple2<Integer, Integer> bottomRight = Tuple.tuple(target.v1 + additionalColsAndRows, target.v2 + additionalColsAndRows);
        erosionLevels.clear();
        terrains.clear();
        for (int i = 0; i <= bottomRight.v1; i++) {
            for (int j = 0; j <= bottomRight.v2; j++) {
                long geologicIndex;
                if (i == 0 && j == 0 || i == target.v1 && j == target.v2) {
                    geologicIndex = 0;
                } else if (i == 0) {
                    geologicIndex = j * 48271L;
                } else if (j == 0) {
                    geologicIndex = i * 16807L;
                } else {
                    geologicIndex = erosionLevels.get(Tuple.tuple(i, j - 1)) * erosionLevels.get(Tuple.tuple(i - 1, j));
                }
                long erosionLevel = (geologicIndex + depth) % 20183;
                erosionLevels.put(Tuple.tuple(i, j), erosionLevel);
                terrains.put(Tuple.tuple(i, j), erosionLevel % 3);
            }
        }
        return target;
    }

    private void linkToNeighborFieldsIfTerrainMatchesForTool(Graph<String, DefaultWeightedEdge> graph, Tuple2<Integer, Integer> pos, long terrain, Tuple2<Integer, Integer> right, long terrainRight, Tuple2<Integer, Integer> down, long terrainDown, long terrain1, long terrain2, String tool) {
        if (terrain == terrain1 || terrain == terrain2) {
            graph.addVertex(pos + tool);
            if (terrainRight == terrain1 || terrainRight == terrain2) {
                graph.addVertex(right + tool);
                Util.addEdge(graph, pos + tool, right + tool, 1);
            }
            if (terrainDown == terrain1 || terrainDown == terrain2) {
                graph.addVertex(down + tool);
                Util.addEdge(graph, pos + tool, down + tool, 1);
            }
        }
    }

}
