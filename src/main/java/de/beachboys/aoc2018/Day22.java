package de.beachboys.aoc2018;

import de.beachboys.Day;
import de.beachboys.Util;
import org.javatuples.Pair;
import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day22 extends Day {

    private final Map<Pair<Integer, Integer>, Long> erosionLevels = new HashMap<>();
    private final Map<Pair<Integer, Integer>, Long> terrains = new HashMap<>();

    public Object part1(List<String> input) {
        Pair<Integer, Integer> target = fillErosionLevelsAndTerrains(input, 0);

        long sum = 0L;
        for (int i = 0; i <= target.getValue0(); i++) {
            for (int j = 0; j <= target.getValue1(); j++) {
                sum += terrains.get(Pair.with(i, j));
            }
        }
        return sum;
    }

    public Object part2(List<String> input) {
        int additionalColsAndRows = 100;
        Pair<Integer, Integer> target = fillErosionLevelsAndTerrains(input, additionalColsAndRows);

        Graph<String, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        for (int i = 0; i < target.getValue0() + additionalColsAndRows; i++) {
            for (int j = 0; j < target.getValue1() + additionalColsAndRows; j++) {
                Pair<Integer, Integer> pos = Pair.with(i, j);
                Pair<Integer, Integer> right = Pair.with(i + 1, j);
                Pair<Integer, Integer> down = Pair.with(i, j + 1);
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
        return (long) alg.getPath(Pair.with(0, 0) + "T", target + "T").getWeight();
    }

    private Pair<Integer, Integer> fillErosionLevelsAndTerrains(List<String> input, int additionalColsAndRows) {
        int depth = Integer.parseInt(input.get(0).substring("depth: ".length()));
        String targetString = input.get(1).substring("target: ".length());
        String[] splitTarget = targetString.split(",");
        Pair<Integer, Integer> target = Pair.with(Integer.parseInt(splitTarget[0]), Integer.parseInt(splitTarget[1]));
        Pair<Integer, Integer> bottomRight = Pair.with(target.getValue0() + additionalColsAndRows, target.getValue1() + additionalColsAndRows);
        erosionLevels.clear();
        terrains.clear();
        for (int i = 0; i <= bottomRight.getValue0(); i++) {
            for (int j = 0; j <= bottomRight.getValue1(); j++) {
                long geologicIndex;
                if (i == 0 && j == 0 || i == target.getValue0() && j == target.getValue1()) {
                    geologicIndex = 0;
                } else if (i == 0) {
                    geologicIndex = j * 48271L;
                } else if (j == 0) {
                    geologicIndex = i * 16807L;
                } else {
                    geologicIndex = erosionLevels.get(Pair.with(i, j - 1)) * erosionLevels.get(Pair.with(i - 1, j));
                }
                long erosionLevel = (geologicIndex + depth) % 20183;
                erosionLevels.put(Pair.with(i, j), erosionLevel);
                terrains.put(Pair.with(i, j), erosionLevel % 3);
            }
        }
        return target;
    }

    private void linkToNeighborFieldsIfTerrainMatchesForTool(Graph<String, DefaultWeightedEdge> graph, Pair<Integer, Integer> pos, long terrain, Pair<Integer, Integer> right, long terrainRight, Pair<Integer, Integer> down, long terrainDown, long terrain1, long terrain2, String tool) {
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
