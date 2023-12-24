package de.beachboys.aoc2016;

import de.beachboys.Day;
import de.beachboys.Util;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.alg.tour.HamiltonianCycleAlgorithmBase;
import org.jgrapht.alg.tour.HeldKarpTSP;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jooq.lambda.tuple.Tuple2;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Day24 extends Day {

    private final static String START_VERTEX = "0";
    private final static int PENALTY_WEIGHT_FOR_HELPER_VERTEX_IF_NOT_START_VERTEX = 10000;
    private Graph<String, DefaultWeightedEdge> graph;

    public Object part1(List<String> input) {
        return runLogic(input, this::addHelperVertexToTransformPart1TaskToTSP) - PENALTY_WEIGHT_FOR_HELPER_VERTEX_IF_NOT_START_VERTEX;
    }

    public Object part2(List<String> input) {
        return runLogic(input, ()->{});
    }

    private int runLogic(List<String> input, Runnable graphManipulator) {
        Map<Tuple2<Integer, Integer>, String> map = Util.buildImageMap(input);
        graph = Util.buildGraphFromMap(map, map.entrySet().stream().filter(e -> START_VERTEX.equals(e.getValue())).map(Map.Entry::getKey).findFirst().orElseThrow());
        addDirectEdgesBetweenNodes();
        removeCrossingVertices();

        graphManipulator.run();

        HamiltonianCycleAlgorithmBase<String, DefaultWeightedEdge> alg = new HeldKarpTSP<>();
        GraphPath<String, DefaultWeightedEdge> tour = alg.getTour(graph);

        return (int) tour.getWeight();
    }

    private void addDirectEdgesBetweenNodes() {
        ShortestPathAlgorithm<String, DefaultWeightedEdge> dijkstraAlg = new DijkstraShortestPath<>(graph);
        List<String> allNodes = graph.vertexSet().stream().filter(Predicate.not(this::isCrossing)).sorted().toList();
        for (int i = 0; i < allNodes.size(); i++) {
            for (int j = i+1; j < allNodes.size(); j++) {
                String source = allNodes.get(i);
                String sink = allNodes.get(j);
                double newWeight = dijkstraAlg.getPathWeight(source, sink);
                graph.addEdge(source, sink);
                graph.setEdgeWeight(source, sink, newWeight);
            }
        }
    }

    private void removeCrossingVertices() {
        Set<String> crossings = graph.vertexSet().stream().filter(this::isCrossing).collect(Collectors.toSet());
        for (String crossing : crossings) {
            graph.removeVertex(crossing);
        }
    }

    private void addHelperVertexToTransformPart1TaskToTSP() {
        String helperVertex = "helperVertex";
        graph.addVertex(helperVertex);
        for (String vertex : graph.vertexSet()) {
            if (!helperVertex.equals(vertex)) {
                graph.addEdge(vertex, helperVertex);
                graph.setEdgeWeight(vertex, helperVertex, START_VERTEX.equals(vertex) ? 0.0 : PENALTY_WEIGHT_FOR_HELPER_VERTEX_IF_NOT_START_VERTEX);
            }
        }
    }

    private boolean isCrossing(String imageValue) {
        return imageValue.startsWith("crossing");
    }

}
