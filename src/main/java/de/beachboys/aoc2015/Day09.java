package de.beachboys.aoc2015;

import de.beachboys.Day;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.tour.HeldKarpTSP;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.List;

public class Day09 extends Day {

    public Object part1(List<String> input) {
        Graph<String, DefaultWeightedEdge> graph = buildGraph(input, 1);
        addHelperVertexToTransformTaskToTSP(graph);
        return getShortestPathLength(graph);
    }

    public Object part2(List<String> input) {
        Graph<String, DefaultWeightedEdge> graph = buildGraph(input, -1);
        addHelperVertexToTransformTaskToTSP(graph);
        return Math.abs(getShortestPathLength(graph));
    }

    private int getShortestPathLength(Graph<String, DefaultWeightedEdge> graph) {
        HeldKarpTSP<String, DefaultWeightedEdge> alg = new HeldKarpTSP<>();
        GraphPath<String, DefaultWeightedEdge> tour = alg.getTour(graph);
        return (int) tour.getWeight();
    }

    private void addHelperVertexToTransformTaskToTSP(Graph<String, DefaultWeightedEdge> graph) {
        String helperVertex = "helperVertex";
        graph.addVertex(helperVertex);
        for (String vertex : graph.vertexSet()) {
            if (!helperVertex.equals(vertex)) {
                graph.addEdge(vertex, helperVertex);
                graph.setEdgeWeight(vertex, helperVertex, 0.0);
            }
        }
    }

    private Graph<String, DefaultWeightedEdge> buildGraph(List<String> input, int distanceFactor) {
        Graph<String, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        for (String line : input) {
            String[] locationsAndDistance = line.split(" = ");
            int distance = Integer.parseInt(locationsAndDistance[1]);
            String[] locations = locationsAndDistance[0].split(" to ");
            if (!graph.containsVertex(locations[0])) {
                graph.addVertex(locations[0]);
            }
            if (!graph.containsVertex(locations[1])) {
                graph.addVertex(locations[1]);
            }
            graph.addEdge(locations[0], locations[1]);
            graph.setEdgeWeight(locations[0], locations[1], distance * distanceFactor);
        }
        return graph;
    }

}
