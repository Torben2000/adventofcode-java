package de.beachboys.aoc2018;

import de.beachboys.Day;
import de.beachboys.Direction;
import de.beachboys.Util;
import org.javatuples.Pair;
import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day20 extends Day {

    private int replacementCounter = 0;
    private static final Pattern INNER_PARENTHESES_PATTERN = Pattern.compile(".*(\\([^()]*\\)).*");
    private final Map<Integer, List<String>> replacementMap = new HashMap<>();
    private final Queue<GraphBuilderQueueElement> queue = new LinkedList<>();
    private Graph<String, DefaultWeightedEdge> graph;

    public Object part1(List<String> input) {
        ShortestPathAlgorithm.SingleSourcePaths<String, DefaultWeightedEdge> paths = runLogicAndReturnShortestPaths(input);

        int longestShortestPath = 0;
        for (String vertex : graph.vertexSet()) {
            longestShortestPath = Math.max(longestShortestPath, paths.getPath(vertex).getLength());
        }

        return longestShortestPath;
    }

    public Object part2(List<String> input) {
        ShortestPathAlgorithm.SingleSourcePaths<String, DefaultWeightedEdge> paths = runLogicAndReturnShortestPaths(input);

        int longPathCounter = 0;
        for (String vertex : graph.vertexSet()) {
            if (paths.getPath(vertex).getLength() >= 1000) {
                longPathCounter++;
            }
        }
        return longPathCounter;
    }

    private ShortestPathAlgorithm.SingleSourcePaths<String, DefaultWeightedEdge> runLogicAndReturnShortestPaths(List<String> input) {
        String stringWithoutParentheses = buildNavRegexWithoutParentheses(input);
        Pair<Integer, Integer> startingPosition = Pair.with(0, 0);
        buildGraph(startingPosition, stringWithoutParentheses);
        return getShortestPaths(startingPosition);
    }

    private String buildNavRegexWithoutParentheses(List<String> input) {
        replacementCounter = 0;
        replacementMap.clear();
        return replaceParentheses(input.get(0).substring(1, input.get(0).length() - 1));
    }

    private String replaceParentheses(String input) {
        Matcher m = INNER_PARENTHESES_PATTERN.matcher(input);
        if (m.matches()) {
            String found = m.group(1);
            List<String> options = List.of(found.substring(1, found.length() - 1).split(Pattern.quote("|")));
            replacementMap.put(replacementCounter, options);
            String nextString = input.replace(found, "{" + replacementCounter + "}");
            replacementCounter++;
            return replaceParentheses(nextString);
        }
        return input;
    }

    private void buildGraph(Pair<Integer, Integer> startingPosition, String stringWithoutParentheses) {
        graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        graph.addVertex(startingPosition.toString());
        queue.clear();
        queue.add(new GraphBuilderQueueElement(startingPosition, stringWithoutParentheses));
        while (!queue.isEmpty()) {
            buildGraphParts(queue.poll());
        }
    }

    private void buildGraphParts(GraphBuilderQueueElement poll) {
        Pair<Integer, Integer> currentPos = poll.pos;
        int index = 0;
        while (index < poll.navRegex.length()) {
            switch (poll.navRegex.charAt(index)) {
                case 'N':
                    currentPos = moveThroughDoorAndAdjustGraph(currentPos, Direction.NORTH);
                    index++;
                    break;
                case 'S':
                    currentPos = moveThroughDoorAndAdjustGraph(currentPos, Direction.SOUTH);
                    index++;
                    break;
                case 'W':
                    currentPos = moveThroughDoorAndAdjustGraph(currentPos, Direction.WEST);
                    index++;
                    break;
                case 'E':
                    currentPos = moveThroughDoorAndAdjustGraph(currentPos, Direction.EAST);
                    index++;
                    break;
                case '{':
                    int endIndex = poll.navRegex.indexOf("}");
                    int replacement = Integer.parseInt(poll.navRegex.substring(index + 1, endIndex));
                    for (String replacedValue : replacementMap.get(replacement)) {
                        queue.add(new GraphBuilderQueueElement(currentPos, replacedValue + poll.navRegex.substring(endIndex + 1)));
                    }
                    // all handled by adding new elements to queue
                    index = poll.navRegex.length();
                    break;
                default:
                    throw new IllegalArgumentException();

            }
        }
    }

    private Pair<Integer, Integer> moveThroughDoorAndAdjustGraph(Pair<Integer, Integer> currentPos, Direction dir) {
        Pair<Integer, Integer> oldPos = currentPos;
        currentPos = dir.move(oldPos, 1);
        graph.addVertex(currentPos.toString());
        Util.addEdge(graph, oldPos.toString(), currentPos.toString(), 1);
        return currentPos;
    }

    private ShortestPathAlgorithm.SingleSourcePaths<String, DefaultWeightedEdge> getShortestPaths(Pair<Integer, Integer> startingPosition) {
        DijkstraShortestPath<String, DefaultWeightedEdge> alg = new DijkstraShortestPath<>(graph);
        return alg.getPaths(startingPosition.toString());
    }

    private static class GraphBuilderQueueElement {
        private final Pair<Integer, Integer> pos;
        private final String navRegex;

        public GraphBuilderQueueElement(Pair<Integer, Integer> pos, String navRegex) {
            this.pos = pos;
            this.navRegex = navRegex;
        }
    }
}
