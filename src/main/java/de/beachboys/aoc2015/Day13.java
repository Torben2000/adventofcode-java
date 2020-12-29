package de.beachboys.aoc2015;

import de.beachboys.Day;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.tour.HeldKarpTSP;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.List;

public class Day13 extends Day {

    public Object part1(List<String> input) {
        Graph<String, DefaultWeightedEdge> graph = buildGraph(input);
        return findOptimalSeatingOrderAndReturnHappiness(graph);
    }

    public Object part2(List<String> input) {
        Graph<String, DefaultWeightedEdge> graph = buildGraph(input);
        addMyselfToGraph(graph);
        return findOptimalSeatingOrderAndReturnHappiness(graph);
    }

    private int findOptimalSeatingOrderAndReturnHappiness(Graph<String, DefaultWeightedEdge> graph) {
        HeldKarpTSP<String, DefaultWeightedEdge> alg = new HeldKarpTSP<>();
        GraphPath<String, DefaultWeightedEdge> tour = alg.getTour(graph);
        return Math.abs((int) tour.getWeight());
    }

    private Graph<String, DefaultWeightedEdge> buildGraph(List<String> input) {
        Graph<String, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        for (String line : input) {
            String[] words = line.substring(0, line.length() - 1).split(" ");
            String person1 = words[0];
            String person2 = words[10];
            int value = Integer.parseInt(words[3]);
            if ("lose".equals(words[2])) {
                value *= -1;
            }

            if (!graph.containsVertex(person1)) {
                graph.addVertex(person1);
            }
            if (!graph.containsVertex(person2)) {
                graph.addVertex(person2);
            }
            if (!graph.containsEdge(person1, person2)){
                graph.addEdge(person1, person2);
                graph.setEdgeWeight(person1, person2, -value);
            } else {
                double currentEdgeWeight = graph.getEdgeWeight(graph.getEdge(person1, person2));
                graph.setEdgeWeight(person1, person2, currentEdgeWeight - value);
            }
        }
        return graph;
    }

    private void addMyselfToGraph(Graph<String, DefaultWeightedEdge> graph) {
        String iAsPerson = "I";
        graph.addVertex(iAsPerson);
        for (String vertex : graph.vertexSet()) {
            if (!iAsPerson.equals(vertex)) {
                graph.addEdge(vertex, iAsPerson);
                graph.setEdgeWeight(vertex, iAsPerson, 0.0);
            }
        }
    }


}
