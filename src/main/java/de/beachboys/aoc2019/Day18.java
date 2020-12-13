package de.beachboys.aoc2019;

import de.beachboys.Day;
import de.beachboys.Util;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * This implementation does not perform as hoped for. I even made it slower for nicer reuseability between part 1 and 2.
 * Part 1 still finishes with the real input in less than a minute on my machine, so I am fine with the current state.
 */
public class Day18 extends Day {

    @FunctionalInterface
    private interface GraphAndQueueInitializer {
        void apply(Map<Pair<Integer, Integer>, String> map, Set<String> allKeys, Pair<Integer, Integer> start);
    }

    public static final String ROOT = "@";
    public static final String WALL = "#";
    private int crossingCounter = 0;

    private int minSteps = Integer.MAX_VALUE;

    private final Map<Pair<String, String>, GraphPath<String, DefaultWeightedEdge>> pathCache = new HashMap<>();
    private final Map<Pair<String, String>, Set<String>> neededKeysCache = new HashMap<>();
    private final Map<Integer, DijkstraShortestPath<String, DefaultWeightedEdge>> dijkstraAlgCache = new HashMap<>();
    private final HashMap<Integer, Graph<String, DefaultWeightedEdge>> graphs = new HashMap<>();

    private final PriorityQueue<Pair<Integer, Integer>> queue = new PriorityQueue<>();
    private final Map<Integer, Quartet<Map<Integer, String>, Integer, Integer, Set<String>>> queueItems = new HashMap<>();
    private final Map<Quartet<Map<Integer, String>, Integer, Integer, Set<String>>, Pair<Integer, Integer>> queueItemReverse = new HashMap<>();
    private int queueId = 0;

    public Object part1(List<String> input) {
        return runLogic(input, this::buildGraphsAndInitialQueueForPart1);
    }

    public Object part2(List<String> input) {
        return runLogic(input, this::buildGraphsAndInitialQueueForPart2);
    }

    private Object runLogic(List<String> input, GraphAndQueueInitializer initializer) {
        Map<Pair<Integer, Integer>, String> map = Util.buildImageMap(input);

        Set<String> allKeys = map.values().stream().filter(this::isKey).collect(Collectors.toSet());

        Pair<Integer, Integer> start = map.entrySet().stream().filter(e -> ROOT.equals(e.getValue())).findFirst().orElseThrow().getKey();

        initializer.apply(map, allKeys, start);

        while (!queue.isEmpty()) {
            Quartet<Map<Integer, String>, Integer, Integer, Set<String>> cur = queueItems.get(queue.poll().getValue1());
            findSolutionFromCurrentState(cur.getValue0(), cur.getValue1(), cur.getValue2(), cur.getValue3());
        }
        return minSteps;
    }

    private void buildGraphsAndInitialQueueForPart1(Map<Pair<Integer, Integer>, String> map, Set<String> allKeys, Pair<Integer, Integer> start) {
        int graphId = 0;
        Graph<String, DefaultWeightedEdge> graph = getGraph(graphId);
        if (Pair.with(40, 40).equals(start)) {
            buildGraphForInputWithSpecialCenter(graph, map);
        } else {
            buildGraph(graph, map, start, findPossibleNextSteps(map, start, null), null, 0);
        }
        simplifyGraph(graphId);
        io.logDebug(Util.printGraphAsDOT(graph, Map.of(ROOT, "root")));

        addQueueItem(Map.of(graphId, ROOT), graphId, 0, new HashSet<>(allKeys));
    }

    private void buildGraphsAndInitialQueueForPart2(Map<Pair<Integer, Integer>, String> map, Set<String> allKeys, Pair<Integer, Integer> start) {
        Map<Integer, Pair<Integer, Integer>> startPositions = manipulateMapForPart2(map, start);
        Map<Integer, String> initialNodeMap = Map.of(0, ROOT, 1, ROOT, 2, ROOT, 3, ROOT);

        for (int graphId = 0; graphId < 4; graphId++) {
            Graph<String, DefaultWeightedEdge> graph = getGraph(graphId);
            Pair<Integer, Integer> startPosition = startPositions.get(graphId);
            buildGraph(graph, map, startPosition, findPossibleNextSteps(map, startPosition, null), null, 0);
            simplifyGraph(graphId);
            io.logDebug(Util.printGraphAsDOT(graph, Map.of(ROOT, "root")));
            addQueueItem(initialNodeMap, graphId, 0, new HashSet<>(allKeys));
        }
    }

    private void buildGraphForInputWithSpecialCenter(Graph<String, DefaultWeightedEdge> graph, Map<Pair<Integer, Integer>, String> map) {
        Pair<Integer, Integer> topLeft = Pair.with(39, 39);
        Pair<Integer, Integer> topCenter = Pair.with(40, 39);
        Pair<Integer, Integer> topRight = Pair.with(41, 39);
        Pair<Integer, Integer> centerLeft = Pair.with(39, 40);
        Pair<Integer, Integer> centerRight = Pair.with(41, 40);
        Pair<Integer, Integer> bottomLeft = Pair.with(39, 41);
        Pair<Integer, Integer> bottomCenter = Pair.with(40, 41);
        Pair<Integer, Integer> bottomRight = Pair.with(41, 41);

        String topLeftVertex = buildGraph(graph, map, topLeft, findPossibleNextSteps(map, topLeft, Set.of(topCenter, centerLeft)), null, 0);
        String topRightVertex = buildGraph(graph, map, topRight, findPossibleNextSteps(map, topRight, Set.of(topCenter, centerRight)), null, 0);
        String bottomLeftVertex = buildGraph(graph, map, bottomLeft, findPossibleNextSteps(map, bottomLeft, Set.of(centerLeft, bottomCenter)), null, 0);
        String bottomRightVertex = buildGraph(graph, map, bottomRight, findPossibleNextSteps(map, bottomRight, Set.of(centerRight, bottomCenter)), null, 0);

        graph.addVertex(ROOT);
        addEdge(graph, ROOT, topLeftVertex, 2);
        addEdge(graph, ROOT, topRightVertex, 2);
        addEdge(graph, ROOT, bottomRightVertex, 2);
        addEdge(graph, ROOT, bottomLeftVertex, 2);
        addEdge(graph, topLeftVertex, topRightVertex, 2);
        addEdge(graph, topRightVertex, bottomRightVertex, 2);
        addEdge(graph, bottomRightVertex, bottomLeftVertex, 2);
        addEdge(graph, bottomLeftVertex, topLeftVertex, 2);
    }

    private Map<Integer, Pair<Integer, Integer>> manipulateMapForPart2(Map<Pair<Integer, Integer>, String> map, Pair<Integer, Integer> start) {
        int startX = start.getValue0();
        int startY = start.getValue1();
        Pair<Integer, Integer> start0 = Pair.with(startX - 1, startY - 1);
        Pair<Integer, Integer> start1 = Pair.with(startX - 1, startY + 1);
        Pair<Integer, Integer> start2 = Pair.with(startX + 1, startY - 1);
        Pair<Integer, Integer> start3 = Pair.with(startX + 1, startY + 1);
        map.put(start0, ROOT);
        map.put(Pair.with(startX - 1, startY), WALL);
        map.put(start1, ROOT);
        map.put(Pair.with(startX, startY - 1), WALL);
        map.put(Pair.with(startX, startY), WALL);
        map.put(Pair.with(startX, startY + 1), WALL);
        map.put(start2, ROOT);
        map.put(Pair.with(startX + 1, startY), WALL);
        map.put(start3, ROOT);
        Map<Integer, Pair<Integer, Integer>> startPositions = new HashMap<>();
        startPositions.put(0, start0);
        startPositions.put(1, start1);
        startPositions.put(2, start2);
        startPositions.put(3, start3);
        return startPositions;
    }

    private void addQueueItem(Map<Integer, String> currentNodes, int graphId, int stepCount, Set<String> keysInInventory) {
        Quartet<Map<Integer, String>, Integer, Integer, Set<String>> item = Quartet.with(currentNodes, graphId, stepCount, keysInInventory);
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
        Pair<String, String> key = Pair.with(from, to);
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
        Pair<String, String> cacheKey = Pair.with(from, to);
        if (!neededKeysCache.containsKey(cacheKey)) {
            GraphPath<String, DefaultWeightedEdge> path = getPath(graphId, from, to);
            Set<String> neededKeys = path.getVertexList().stream().filter(this::isLock).map(String::toLowerCase).collect(Collectors.toSet());
            neededKeysCache.put(cacheKey, neededKeys);
        }
        Set<String> neededKeys = neededKeysCache.get(cacheKey);
        return neededKeys.stream().noneMatch(missingKeys::contains);
    }

    private String buildGraph(Graph<String, DefaultWeightedEdge> graph, Map<Pair<Integer, Integer>, String> map, Pair<Integer, Integer> nodePosition, List<Pair<Integer, Integer>> nextSteps, String parentNode, int distanceToParent) {
        String newNodeName;
        if (".".equals(map.get(nodePosition))) {
            newNodeName = "crossing" + crossingCounter++;
        } else {
            newNodeName = map.get(nodePosition);
        }
        graph.addVertex(newNodeName);
        if (parentNode != null) {
            addEdge(graph, parentNode, newNodeName, distanceToParent + 1);
        }
        for (Pair<Integer, Integer> nextStep : nextSteps) {
            int stepCounter = 0;
            Pair<Integer, Integer> previousPosition = nodePosition;
            Pair<Integer, Integer> currentPosition = nextStep;

            while (true) {
                List<Pair<Integer, Integer>> nextNextSteps = findPossibleNextSteps(map, currentPosition, Set.of(previousPosition));
                if (map.get(currentPosition).matches("[A-za-z]") || nextNextSteps.size() > 1) {
                    buildGraph(graph, map, currentPosition, nextNextSteps, newNodeName, stepCounter);
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
                .filter(pos -> (sources == null || !sources.contains(pos)) && map.get(pos) != null && !WALL.equals(map.get(pos)))
                .collect(Collectors.toList());
    }

    private void addEdge(Graph<String, DefaultWeightedEdge> graph, String from, String to, int weight) {
        graph.addEdge(from, to);
        graph.setEdgeWeight(from, to, weight);
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
                    String target = getOtherVertex(graph, e, vertex);
                    verticesToConnect.add(target);
                }
                graph.addEdge(verticesToConnect.get(0), verticesToConnect.get(1));
                graph.setEdgeWeight(verticesToConnect.get(0), verticesToConnect.get(1), newWeight);
                graph.removeVertex(vertex);
            }
        }
    }

    private String getOtherVertex(Graph<String, DefaultWeightedEdge> graph, DefaultWeightedEdge edge, String vertex) {
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

}
