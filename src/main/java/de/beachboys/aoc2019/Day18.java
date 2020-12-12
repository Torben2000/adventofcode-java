package de.beachboys.aoc2019;

import de.beachboys.Day;
import de.beachboys.Util;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Day18 extends Day {

    public static final String ROOT = "@";
    private final Graph<String, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
    private int crossingCounter = 0;
    private final DijkstraShortestPath<String, DefaultWeightedEdge> dijkstraAlg = new DijkstraShortestPath<>(graph);

    private int minSteps = Integer.MAX_VALUE;

    private final Map<Pair<String, String>, GraphPath<String, DefaultWeightedEdge>> pathCache = new HashMap<>();
    private final Map<Pair<String, String>, Set<String>> neededKeysCache = new HashMap<>();

    private final PriorityQueue<Pair<Integer, Integer>> queue = new PriorityQueue<>();
    private final Map<Integer, Triplet<Integer, String, Set<String>>> queueItems = new HashMap<>();
    private final Map<Triplet<Integer, String, Set<String>>, Pair<Integer, Integer>> queueItemReverse = new HashMap<>();
    private int queueId = 0;

    public Object part1(List<String> input) {
        Map<Pair<Integer, Integer>, String> map = Util.buildImageMap(input);

        Set<String> allKeys = map.values().stream().filter(this::isKey).collect(Collectors.toSet());

        Pair<Integer, Integer> start = map.entrySet().stream().filter(e -> ROOT.equals(e.getValue())).findFirst().orElseThrow().getKey();
        if (Pair.with(40, 40).equals(start)) {
            buildGraphForInputWithSpecialCenter(map);
        } else {
            buildGraph(map, start, findPossibleNextSteps(map, start, null), null, 0);
        }
        simplifyGraph();
        io.logDebug(Util.printGraph(graph, Map.of(ROOT, "root")));

        addQueueItem(ROOT, 0, new HashSet<>(allKeys));

        while (!queue.isEmpty()) {
            Triplet<Integer, String, Set<String>> cur = queueItems.get(queue.poll().getValue1());
            findSolutionFromCurrentState(cur.getValue1(), cur.getValue0(), cur.getValue2());
        }
        return minSteps;
    }

    private void buildGraphForInputWithSpecialCenter(Map<Pair<Integer, Integer>, String> map) {
        Pair<Integer, Integer> topLeft = Pair.with(39, 39);
        Pair<Integer, Integer> topCenter = Pair.with(40, 39);
        Pair<Integer, Integer> topRight = Pair.with(41, 39);
        Pair<Integer, Integer> centerLeft = Pair.with(39, 40);
        Pair<Integer, Integer> centerRight = Pair.with(41, 40);
        Pair<Integer, Integer> bottomLeft = Pair.with(39, 41);
        Pair<Integer, Integer> bottomCenter = Pair.with(40, 41);
        Pair<Integer, Integer> bottomRight = Pair.with(41, 41);

        String topLeftVertex = buildGraph(map, topLeft, findPossibleNextSteps(map, topLeft, Set.of(topCenter, centerLeft)), null, 0);
        String topRightVertex = buildGraph(map, topRight, findPossibleNextSteps(map, topRight, Set.of(topCenter, centerRight)), null, 0);
        String bottomLeftVertex = buildGraph(map, bottomLeft, findPossibleNextSteps(map, bottomLeft, Set.of(centerLeft, bottomCenter)), null, 0);
        String bottomRightVertex = buildGraph(map, bottomRight, findPossibleNextSteps(map, bottomRight, Set.of(centerRight, bottomCenter)), null, 0);

        graph.addVertex(ROOT);
        addEdge(ROOT, topLeftVertex, 2);
        addEdge(ROOT, topRightVertex, 2);
        addEdge(ROOT, bottomRightVertex, 2);
        addEdge(ROOT, bottomLeftVertex, 2);
        addEdge(topLeftVertex, topRightVertex, 2);
        addEdge(topRightVertex, bottomRightVertex, 2);
        addEdge(bottomRightVertex, bottomLeftVertex, 2);
        addEdge(bottomLeftVertex, topLeftVertex, 2);
    }

    private void addQueueItem(String currentNode, int stepCount, Set<String> keysInInventory) {
        Triplet<Integer, String, Set<String>> item = Triplet.with(stepCount, currentNode, keysInInventory);
        Pair<Integer, Integer> existingEntry = queueItemReverse.get(item);
        if (existingEntry == null || existingEntry.getValue0() > stepCount) {
            if (existingEntry != null) {
                queue.remove(existingEntry);
            }
            int id = queueId++;
            Pair<Integer, Integer> key = Pair.with(stepCount, id);
            queue.add(key);
            queueItems.put(id, item);
            queueItemReverse.put(item, key);
        }
    }

    private void findSolutionFromCurrentState(String currentNode, int currentStepCounter, Set<String> missingKeys) {
        Set<String> reachableKeysWithoutPickingUpAnother = missingKeys.stream()
                .filter(key -> isReachable(currentNode, key, missingKeys))
                .filter(key -> getPath(currentNode, key).getVertexList().stream().filter(Predicate.not(key::equals)).noneMatch(missingKeys::contains))
                .collect(Collectors.toSet());

        for (String newKey : reachableKeysWithoutPickingUpAnother) {
            Set<String> copyOfMissingKeys = new HashSet<>(missingKeys);
            copyOfMissingKeys.remove(newKey);
            GraphPath<String, DefaultWeightedEdge> path = getPath(currentNode, newKey);
            int newSteps = currentStepCounter + (int) path.getWeight();
            if (minSteps > newSteps) {
                if (copyOfMissingKeys.isEmpty()) {
                    minSteps = newSteps;
                } else {
                    addQueueItem(newKey, newSteps, copyOfMissingKeys);
                }
            }
        }
    }

    private GraphPath<String, DefaultWeightedEdge> getPath(String from, String to) {
        Pair<String, String> key = Pair.with(from, to);
        if (!pathCache.containsKey(key)) {
            pathCache.put(key, dijkstraAlg.getPath(from, to));
        }
        return pathCache.get(key);
    }

    private boolean isReachable(String from, String to, Set<String> missingKeys) {
        Pair<String, String> cacheKey = Pair.with(from, to);
        if (!neededKeysCache.containsKey(cacheKey)) {
            GraphPath<String, DefaultWeightedEdge> path = getPath(from, to);
            Set<String> neededKeys = path.getVertexList().stream().filter(this::isLock).map(String::toLowerCase).collect(Collectors.toSet());
            neededKeysCache.put(cacheKey, neededKeys);
        }
        Set<String> neededKeys = neededKeysCache.get(cacheKey);
        return neededKeys.stream().noneMatch(missingKeys::contains);
    }

    private String buildGraph(Map<Pair<Integer, Integer>, String> map, Pair<Integer, Integer> nodePosition, List<Pair<Integer, Integer>> nextSteps, String parentNode, int distanceToParent) {
        String newNodeName;
        if (".".equals(map.get(nodePosition))) {
            newNodeName = "crossing" + crossingCounter++;
        } else {
            newNodeName = map.get(nodePosition);
        }
        graph.addVertex(newNodeName);
        if (parentNode != null) {
            addEdge(parentNode, newNodeName, distanceToParent + 1);
        }
        for (Pair<Integer, Integer> nextStep : nextSteps) {
            int stepCounter = 0;
            Pair<Integer, Integer> previousPosition = nodePosition;
            Pair<Integer, Integer> currentPosition = nextStep;

            while (true) {
                List<Pair<Integer, Integer>> nextNextSteps = findPossibleNextSteps(map, currentPosition, Set.of(previousPosition));
                if (map.get(currentPosition).matches("[A-za-z]") || nextNextSteps.size() > 1) {
                    buildGraph(map, currentPosition, nextNextSteps, newNodeName, stepCounter);
                    break;
                } else if (nextNextSteps.size() == 1) {
                    stepCounter++;
                    previousPosition = currentPosition;
                    currentPosition = nextNextSteps.get(0);
                } else {
                    break;
                }
            }
        }
        return newNodeName;
    }

    private List<Pair<Integer, Integer>> findPossibleNextSteps(Map<Pair<Integer, Integer>, String> map, Pair<Integer, Integer> start, Set<Pair<Integer, Integer>> sources) {
        return List.of(Pair.with(start.getValue0(), start.getValue1() - 1),
                Pair.with(start.getValue0(), start.getValue1() + 1),
                Pair.with(start.getValue0() - 1, start.getValue1()),
                Pair.with(start.getValue0() + 1, start.getValue1()))
                .stream()
                .filter(pos -> (sources == null || !sources.contains(pos)) && map.get(pos) != null && !"#".equals(map.get(pos)))
                .collect(Collectors.toList());
    }

    private void addEdge(String from, String to, int weight) {
        graph.addEdge(from, to);
        graph.setEdgeWeight(from, to, weight);
    }

    private void simplifyGraph() {
        removeDeadEndsFromGraph();
        removeCrossingsWithNoUse();
        removeLocksThatAlwaysOpen();
    }

    private void removeDeadEndsFromGraph() {
        boolean vertexRemoved = true;
        while (vertexRemoved) {
            vertexRemoved = false;
            Set<String> crossingsAndLocks = graph.vertexSet().stream().filter(v -> isLock(v) || isCrossing(v)).collect(Collectors.toSet());
            for (String vertex : crossingsAndLocks) {
                if (graph.edgesOf(vertex).size() <= 1) {
                    graph.removeVertex(vertex);
                    vertexRemoved = true;
                }
            }
        }
    }

    private void removeCrossingsWithNoUse() {
        Set<String> crossings = graph.vertexSet().stream().filter(this::isCrossing).collect(Collectors.toSet());
        removeVerticesIfSuperfluous(crossings);
    }

    private void removeLocksThatAlwaysOpen() {
        Set<String> locks = graph.vertexSet().stream().filter(this::isLock).filter(v -> getPath(v, ROOT).getVertexList().contains(v.toLowerCase())).collect(Collectors.toSet());
        removeVerticesIfSuperfluous(locks);
    }

    private void removeVerticesIfSuperfluous(Set<String> vertices) {
        for (String vertex : vertices) {
            Set<DefaultWeightedEdge> edges = graph.edgesOf(vertex);
            List<String> verticesToConnect = new ArrayList<>();
            double newWeight = 0.0;
            if (edges.size() == 2) {
                for (DefaultWeightedEdge e : edges) {
                    newWeight += graph.getEdgeWeight(e);
                    String target = getOtherVertex(e, vertex);
                    verticesToConnect.add(target);
                }
                graph.addEdge(verticesToConnect.get(0), verticesToConnect.get(1));
                graph.setEdgeWeight(verticesToConnect.get(0), verticesToConnect.get(1), newWeight);
                graph.removeVertex(vertex);
            }
        }
    }

    private String getOtherVertex(DefaultWeightedEdge edge, String vertex) {
        String target = graph.getEdgeTarget(edge);
        if (vertex.equals(target)) {
            target = graph.getEdgeSource(edge);
        }
        return target;
    }

    private boolean isCrossing(String imageValue) {
        return imageValue.startsWith("crossing");
    }

    private boolean isKey(String imageValue) {
        return imageValue.matches("[a-z]");
    }

    private boolean isLock(String imageValue) {
        return imageValue.matches("[A-Z]");
    }

    public Object part2(List<String> input) {
        return 2;
    }

}
