package de.beachboys.aoc2019;

import de.beachboys.Day;
import de.beachboys.Util;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;
import org.jooq.lambda.tuple.Tuple4;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * This implementation does not perform as hoped for. I even made it slower for nicer reusability between part 1 and 2.
 * Part 1 still finishes with the real input in less than a minute on my machine, so I am fine with the current state.
 */
public class Day18 extends Day {

    @FunctionalInterface
    private interface GraphAndQueueInitializer {
        void apply(Map<Tuple2<Integer, Integer>, String> map, Set<String> allKeys, Tuple2<Integer, Integer> start);
    }

    public static final String ROOT = "@";
    public static final String WALL = "#";

    private int minSteps = Integer.MAX_VALUE;

    private final Map<Tuple2<String, String>, GraphPath<String, DefaultWeightedEdge>> pathCache = new HashMap<>();
    private final Map<Tuple2<String, String>, Set<String>> neededKeysCache = new HashMap<>();
    private final Map<Integer, DijkstraShortestPath<String, DefaultWeightedEdge>> dijkstraAlgCache = new HashMap<>();
    private final HashMap<Integer, Graph<String, DefaultWeightedEdge>> graphs = new HashMap<>();

    private final PriorityQueue<Tuple2<Integer, Integer>> queue = new PriorityQueue<>();
    private final Map<Integer, Tuple4<Map<Integer, String>, Integer, Integer, Set<String>>> queueItems = new HashMap<>();
    private final Map<Tuple4<Map<Integer, String>, Integer, Integer, Set<String>>, Tuple2<Integer, Integer>> queueItemReverse = new HashMap<>();
    private int queueId = 0;

    public Object part1(List<String> input) {
        return runLogic(input, this::buildGraphsAndInitialQueueForPart1);
    }

    public Object part2(List<String> input) {
        return runLogic(input, this::buildGraphsAndInitialQueueForPart2);
    }

    private Object runLogic(List<String> input, GraphAndQueueInitializer initializer) {
        Map<Tuple2<Integer, Integer>, String> map = Util.buildImageMap(input);

        Set<String> allKeys = map.values().stream().filter(this::isKey).collect(Collectors.toSet());

        Tuple2<Integer, Integer> start = map.entrySet().stream().filter(e -> ROOT.equals(e.getValue())).findFirst().orElseThrow().getKey();

        initializer.apply(map, allKeys, start);

        while (!queue.isEmpty()) {
            Tuple4<Map<Integer, String>, Integer, Integer, Set<String>> cur = queueItems.get(queue.poll().v2);
            findSolutionFromCurrentState(cur.v1, cur.v2, cur.v3, cur.v4);
        }
        return minSteps;
    }

    private void buildGraphsAndInitialQueueForPart1(Map<Tuple2<Integer, Integer>, String> map, Set<String> allKeys, Tuple2<Integer, Integer> start) {
        int graphId = 0;
        Graph<String, DefaultWeightedEdge> graph = Util.buildGraphFromMap(map, start);
        graphs.put(graphId, graph);
        simplifyGraph(graphId);
        io.logDebug(Util.printGraphAsDOT(graph, Map.of(ROOT, "root")));

        addQueueItem(Map.of(graphId, ROOT), graphId, 0, new HashSet<>(allKeys));
    }

    private void buildGraphsAndInitialQueueForPart2(Map<Tuple2<Integer, Integer>, String> map, Set<String> allKeys, Tuple2<Integer, Integer> start) {
        Map<Integer, Tuple2<Integer, Integer>> startPositions = manipulateMapForPart2(map, start);
        Map<Integer, String> initialNodeMap = Map.of(0, ROOT, 1, ROOT, 2, ROOT, 3, ROOT);

        for (int graphId = 0; graphId < 4; graphId++) {
            Tuple2<Integer, Integer> startPosition = startPositions.get(graphId);
            Graph<String, DefaultWeightedEdge> graph = Util.buildGraphFromMap(map, startPosition);
            graphs.put(graphId, graph);
            simplifyGraph(graphId);
            io.logDebug(Util.printGraphAsDOT(graph, Map.of(ROOT, "root")));
            addQueueItem(initialNodeMap, graphId, 0, new HashSet<>(allKeys));
        }
    }

    private Map<Integer, Tuple2<Integer, Integer>> manipulateMapForPart2(Map<Tuple2<Integer, Integer>, String> map, Tuple2<Integer, Integer> start) {
        int startX = start.v1;
        int startY = start.v2;
        Tuple2<Integer, Integer> start0 = Tuple.tuple(startX - 1, startY - 1);
        Tuple2<Integer, Integer> start1 = Tuple.tuple(startX - 1, startY + 1);
        Tuple2<Integer, Integer> start2 = Tuple.tuple(startX + 1, startY - 1);
        Tuple2<Integer, Integer> start3 = Tuple.tuple(startX + 1, startY + 1);
        map.put(start0, ROOT);
        map.put(Tuple.tuple(startX - 1, startY), WALL);
        map.put(start1, ROOT);
        map.put(Tuple.tuple(startX, startY - 1), WALL);
        map.put(Tuple.tuple(startX, startY), WALL);
        map.put(Tuple.tuple(startX, startY + 1), WALL);
        map.put(start2, ROOT);
        map.put(Tuple.tuple(startX + 1, startY), WALL);
        map.put(start3, ROOT);
        Map<Integer, Tuple2<Integer, Integer>> startPositions = new HashMap<>();
        startPositions.put(0, start0);
        startPositions.put(1, start1);
        startPositions.put(2, start2);
        startPositions.put(3, start3);
        return startPositions;
    }

    private void addQueueItem(Map<Integer, String> currentNodes, int graphId, int stepCount, Set<String> keysInInventory) {
        Tuple4<Map<Integer, String>, Integer, Integer, Set<String>> item = Tuple.tuple(currentNodes, graphId, stepCount, keysInInventory);
        Tuple2<Integer, Integer> existingEntry = queueItemReverse.get(item);
        if (existingEntry == null || existingEntry.v1 > stepCount) {
            if (existingEntry != null) {
                queue.remove(existingEntry);
            }
            int id = queueId++;
            Tuple2<Integer, Integer> key = Tuple.tuple(stepCount, id);
            queue.add(key);
            queueItems.put(id, item);
            queueItemReverse.put(item, key);
        }
    }

    private void findSolutionFromCurrentState(Map<Integer, String> currentNodes, Integer graphId, int currentStepCounter, Set<String> missingKeys) {
        String currentNode = currentNodes.get(graphId);
        Set<String> reachableKeysWithoutPickingUpAnother = missingKeys.stream()
                .filter(key -> isReachable(graphId, currentNode, key, missingKeys))
                .filter(key -> getPath(graphId, currentNode, key).getVertexList().stream().filter(Predicate.not(key::equals)).noneMatch(missingKeys::contains))
                .collect(Collectors.toSet());

        for (String newKey : reachableKeysWithoutPickingUpAnother) {
            Set<String> copyOfMissingKeys = new HashSet<>(missingKeys);
            Map<Integer, String> copyOfCurrentNodes = new HashMap<>(currentNodes);
            copyOfMissingKeys.remove(newKey);
            GraphPath<String, DefaultWeightedEdge> path = getPath(graphId, currentNode, newKey);
            int newSteps = currentStepCounter + (int) path.getWeight();
            if (minSteps > newSteps) {
                if (copyOfMissingKeys.isEmpty()) {
                    minSteps = newSteps;
                } else {
                    copyOfCurrentNodes.put(graphId, newKey);
                    for (Integer nextGraphId : copyOfCurrentNodes.keySet()) {
                        addQueueItem(copyOfCurrentNodes, nextGraphId, newSteps, copyOfMissingKeys);
                    }
                }
            }
        }
    }

    private GraphPath<String, DefaultWeightedEdge> getPath(Integer graphId, String from, String to) {
        Tuple2<String, String> key = Tuple.tuple(from, to);
        if (!pathCache.containsKey(key)) {

            pathCache.put(key, getDijkstraAlg(graphId).getPath(from, to));
        }
        return pathCache.get(key);
    }

    private Graph<String, DefaultWeightedEdge> getGraph(Integer graphId) {
        if (!graphs.containsKey(graphId)) {
            graphs.put(graphId, new SimpleWeightedGraph<>(DefaultWeightedEdge.class));
        }
        return graphs.get(graphId);
    }

    private DijkstraShortestPath<String, DefaultWeightedEdge> getDijkstraAlg(Integer graphId) {
        if (!dijkstraAlgCache.containsKey(graphId)) {
            dijkstraAlgCache.put(graphId, new DijkstraShortestPath<>(getGraph(graphId)));
        }
        return dijkstraAlgCache.get(graphId);
    }

    private boolean isReachable(Integer graphId, String from, String to, Set<String> missingKeys) {
        if (!getGraph(graphId).vertexSet().contains(to)) {
            return false;
        }
        Tuple2<String, String> cacheKey = Tuple.tuple(from, to);
        if (!neededKeysCache.containsKey(cacheKey)) {
            GraphPath<String, DefaultWeightedEdge> path = getPath(graphId, from, to);
            Set<String> neededKeys = path.getVertexList().stream().filter(this::isLock).map(String::toLowerCase).collect(Collectors.toSet());
            neededKeysCache.put(cacheKey, neededKeys);
        }
        Set<String> neededKeys = neededKeysCache.get(cacheKey);
        return neededKeys.stream().noneMatch(missingKeys::contains);
    }

    private void simplifyGraph(Integer graphId) {
        removeDeadEndsFromGraph(graphId);
        removeCrossingsWithNoUse(graphId);
        removeLocksThatAlwaysOpen(graphId);
    }

    private void removeDeadEndsFromGraph(Integer graphId) {
        Graph<String, DefaultWeightedEdge> graph = getGraph(graphId);
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

    private void removeCrossingsWithNoUse(Integer graphId) {
        Graph<String, DefaultWeightedEdge> graph = getGraph(graphId);
        Set<String> crossings = graph.vertexSet().stream().filter(this::isCrossing).collect(Collectors.toSet());
        removeVerticesIfSuperfluous(graph, crossings);
    }

    private void removeLocksThatAlwaysOpen(Integer graphId) {
        Graph<String, DefaultWeightedEdge> graph = getGraph(graphId);
        Set<String> locks = graph.vertexSet().stream().filter(this::isLock).filter(v -> getPath(graphId, v, ROOT).getVertexList().contains(v.toLowerCase())).collect(Collectors.toSet());
        removeVerticesIfSuperfluous(graph, locks);
    }

    private void removeVerticesIfSuperfluous(Graph<String, DefaultWeightedEdge> graph, Set<String> vertices) {
        for (String vertex : vertices) {
            Set<DefaultWeightedEdge> edges = graph.edgesOf(vertex);
            List<String> verticesToConnect = new ArrayList<>();
            double newWeight = 0.0;
            if (edges.size() == 2) {
                for (DefaultWeightedEdge e : edges) {
                    newWeight += graph.getEdgeWeight(e);
                    String target = Util.getOtherVertex(graph, e, vertex);
                    verticesToConnect.add(target);
                }
                graph.addEdge(verticesToConnect.get(0), verticesToConnect.get(1));
                graph.setEdgeWeight(verticesToConnect.get(0), verticesToConnect.get(1), newWeight);
                graph.removeVertex(vertex);
            }
        }
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

}
