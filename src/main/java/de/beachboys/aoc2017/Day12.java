package de.beachboys.aoc2017;

import de.beachboys.Day;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day12 extends Day {

    public Object part1(List<String> input) {
        ConnectivityInspector<Integer, DefaultEdge> inspector = buildGraphAndReturnInspector(input);
        return inspector.connectedSetOf(0).size();
    }

    public Object part2(List<String> input) {
        ConnectivityInspector<Integer, DefaultEdge> inspector = buildGraphAndReturnInspector(input);
        return inspector.connectedSets().size();
    }

    private ConnectivityInspector<Integer, DefaultEdge> buildGraphAndReturnInspector(List<String> input) {
        Pattern linePattern = Pattern.compile("([0-9]+) <-> ([0-9, ]+)");
        Pattern numberPattern = Pattern.compile("[0-9]+");
        Graph<Integer, DefaultEdge> graph = new DefaultUndirectedGraph<>(DefaultEdge.class);
        for (String line : input) {
            Matcher lineMatcher = linePattern.matcher(line);
            if (lineMatcher.matches()) {
                Integer leftSideNode = Integer.parseInt(lineMatcher.group(1));
                List<Integer> rightSideNodes = new ArrayList<>();
                String rightSide = lineMatcher.group(2);
                Matcher rightSideNodeMatcher = numberPattern.matcher(rightSide);
                while (rightSideNodeMatcher.find()) {
                    rightSideNodes.add(Integer.valueOf(rightSideNodeMatcher.group()));
                }
                rightSideNodes.forEach(node -> Graphs.addEdgeWithVertices(graph, leftSideNode, node));
            }
        }
        return new ConnectivityInspector<>(graph);
    }

}
