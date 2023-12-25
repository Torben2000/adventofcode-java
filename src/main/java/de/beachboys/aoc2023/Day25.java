package de.beachboys.aoc2023;

import de.beachboys.Day;
import org.jgrapht.Graph;
import org.jgrapht.alg.StoerWagnerMinimumCut;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day25 extends Day {

    public Object part1(List<String> input) {
        Graph<String, DefaultWeightedEdge> graph = parseInput(input);

        StoerWagnerMinimumCut<String, DefaultWeightedEdge> minimumCutAlg = new StoerWagnerMinimumCut<>(graph);
        if (3.0 != minimumCutAlg.minCutWeight()) {
            throw new IllegalArgumentException();
        }
        Set<String> vertexSet1 = minimumCutAlg.minCut();
        Set<String> vertexSet2 = new HashSet<>(graph.vertexSet());
        vertexSet2.removeAll(vertexSet1);

        return vertexSet1.size() * vertexSet2.size();
    }

    private static Graph<String, DefaultWeightedEdge> parseInput(List<String> input) {
        Graph<String, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        for (String line : input) {
            String[] componentAndOtherComponent = line.split(": ");
            String[] otherComponents = componentAndOtherComponent[1].split(" ");
            String component = componentAndOtherComponent[0];

            graph.addVertex(component);
            for (String otherComponent : otherComponents) {
                graph.addVertex(otherComponent);
                graph.addEdge(component, otherComponent);
            }
        }
        return graph;
    }

    public Object part2(List<String> input) {
        return "There is no puzzle! :-)";
    }

}
