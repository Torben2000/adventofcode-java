package de.beachboys.aoc2019;

import de.beachboys.Day;
import de.beachboys.GraphConstructionHelper;
import de.beachboys.Util;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Day20 extends Day {

    public static final int MAX_LEVEL_PART_2 = 100;

    public Object part1(List<String> input) {
        return runLogic(input, this::getGraphConstructionHelperPart1);
    }

    public Object part2(List<String> input) {
        return runLogic(input, this::getGraphConstructionHelperPart2);
    }

    private GraphConstructionHelper getGraphConstructionHelperPart1(Map<Pair<Integer, Integer>, String> map, Map<String, Set<Pair<Integer, Integer>>> portals) {
        return new GraphConstructionHelperPart1(map, portals);
    }

    private GraphConstructionHelper getGraphConstructionHelperPart2(Map<Pair<Integer, Integer>, String> map, Map<String, Set<Pair<Integer, Integer>>> portals) {
        return new GraphConstructionHelperPart2(map, portals);
    }

    private Object runLogic(List<String> input, BiFunction<Map<Pair<Integer, Integer>, String>, Map<String, Set<Pair<Integer, Integer>>>, GraphConstructionHelper> helperBuilder) {
        Map<Pair<Integer, Integer>, String> map = Util.buildImageMap(input);

        setPortalsAsSinglePosition(map);
        Map<String, Set<Pair<Integer, Integer>>> portals = buildPortalsMap(map);

        Pair<Integer, Integer> startPosition = portals.get("AA").stream().findFirst().orElseThrow();
        GraphConstructionHelper helper = helperBuilder.apply(map, portals);
        Graph<String, DefaultWeightedEdge> graph = Util.buildGraphFromMap(map, startPosition, helper);

        io.logDebug(Util.printGraphAsDOT(graph, Map.of("[", "_", "]", "", " ", "", ",", "_")));

        String aaNode = graph.vertexSet().stream().filter(v -> v.startsWith("AA")).findFirst().orElseThrow();
        String zzNode = graph.vertexSet().stream().filter(v -> v.startsWith("ZZ")).findFirst().orElseThrow();
        DijkstraShortestPath<String, DefaultWeightedEdge> alg = new DijkstraShortestPath<>(graph);

        return (long) alg.getPath(aaNode, zzNode).getWeight();
    }

    private Map<String, Set<Pair<Integer, Integer>>> buildPortalsMap(Map<Pair<Integer, Integer>, String> map) {
        Map<String, Set<Pair<Integer, Integer>>> portals = new HashMap<>();
        map.entrySet().stream().filter(e -> e.getValue().matches("[A-Z]{2}")).forEach(e -> {
            if (!portals.containsKey(e.getValue())) {
                portals.put(e.getValue(), new HashSet<>());
            }
            portals.get(e.getValue()).add(e.getKey());
        });
        return portals;
    }

    private void setPortalsAsSinglePosition(Map<Pair<Integer, Integer>, String> map) {
        Set<Pair<Integer, Integer>> letterPositions =  map.keySet().stream().filter(k -> map.get(k).matches("[A-Z]")).collect(Collectors.toSet());
        Set<Pair<Integer, Integer>> processedLetterPositions = new HashSet<>();
        for (Pair<Integer, Integer> letterPosition : letterPositions) {
            if (processedLetterPositions.contains(letterPosition)) {
                continue;
            }
            String thisLetter = map.get(letterPosition);
            Triplet<Pair<Integer, Integer>, Boolean, Boolean> neighborPosition = getLetterNeighbor(map, letterPosition);

            String neighborLetter = map.get(neighborPosition.getValue0());
            String portalString = neighborPosition.getValue1() ? neighborLetter + thisLetter : thisLetter + neighborLetter;
            if (neighborPosition.getValue2()) {
                map.put(letterPosition, " ");
                map.put(neighborPosition.getValue0(), " ");
                map.put(Pair.with(2 * neighborPosition.getValue0().getValue0() - letterPosition.getValue0(), 2 * neighborPosition.getValue0().getValue1() - letterPosition.getValue1()), portalString);
            } else {
                map.put(letterPosition, " ");
                map.put(neighborPosition.getValue0(), " ");
                map.put(Pair.with(2 * letterPosition.getValue0() - neighborPosition.getValue0().getValue0(), 2 * letterPosition.getValue1() - neighborPosition.getValue0().getValue1()), portalString);
            }
            processedLetterPositions.add(letterPosition);
            processedLetterPositions.add(neighborPosition.getValue0());
        }
    }

    private Triplet<Pair<Integer, Integer>, Boolean, Boolean> getLetterNeighbor(Map<Pair<Integer, Integer>, String> map, Pair<Integer, Integer> letterPosition) {
        Pair<Integer, Integer> north = Pair.with(letterPosition.getValue0(), letterPosition.getValue1() - 1);
        Pair<Integer, Integer> south = Pair.with(letterPosition.getValue0(), letterPosition.getValue1() + 1);
        Pair<Integer, Integer> west = Pair.with(letterPosition.getValue0() - 1, letterPosition.getValue1());
        Pair<Integer, Integer> east = Pair.with(letterPosition.getValue0() + 1, letterPosition.getValue1());
        if (map.containsKey(north) && map.get(north).matches("[A-Z]")) {
            return Triplet.with(north, true, !".".equals(map.get(south)));
        } else if (map.containsKey(south) && map.get(south).matches("[A-Z]")) {
            return Triplet.with(south, false, !".".equals(map.get(north)));
        } else if (map.containsKey(west) && map.get(west).matches("[A-Z]")) {
            return Triplet.with(west, true, !".".equals(map.get(east)));
        } else if (map.containsKey(east) && map.get(east).matches("[A-Z]")) {
            return Triplet.with(east, false, !".".equals(map.get(west)));
        }
        throw new IllegalArgumentException("no neighbor found");
    }

    private static class GraphConstructionHelperPart1 extends GraphConstructionHelper {
        private final Map<String, Set<Pair<Integer, Integer>>> portals;

        public GraphConstructionHelperPart1(Map<Pair<Integer, Integer>, String> map, Map<String, Set<Pair<Integer, Integer>>> portals) {
            super(map);
            this.portals = portals;
        }

        public String getNodeName(Pair<Integer, Integer> nodePosition, String parentNode) {
            String newNodeName;
            if (".".equals(map.get(nodePosition))) {
                newNodeName = "crossing" + nodePosition;
            } else {
                newNodeName = map.get(nodePosition) + nodePosition;
            }
            return newNodeName;
        }

        public List<Pair<Integer, Integer>> getPossibleNavigationPositions(Pair<Integer, Integer> currentPosition) {
            List<Pair<Integer, Integer>> list = new ArrayList<>(super.getPossibleNavigationPositions(currentPosition));
            String mapValue = map.get(currentPosition);
            if (mapValue.matches("[A-Z]{2}")) {
                Set<Pair<Integer, Integer>> portal = portals.get(mapValue);
                portal.stream().filter(Predicate.not(currentPosition::equals)).forEach(list::add);
            }
            return list;
        }
    }

    private static class GraphConstructionHelperPart2 extends GraphConstructionHelper {
        private final Map<String, Set<Pair<Integer, Integer>>> portals;

        private final int maxX;
        private final int maxY;

        public GraphConstructionHelperPart2(Map<Pair<Integer, Integer>, String> map, Map<String, Set<Pair<Integer, Integer>>> portals) {
            super(map);
            this.portals = portals;
            maxX = portals.values().stream().flatMap(Collection::stream).map(Pair::getValue0).max(Integer::compareTo).orElseThrow();
            maxY = portals.values().stream().flatMap(Collection::stream).map(Pair::getValue1).max(Integer::compareTo).orElseThrow();
        }

        public String getNodeName(Pair<Integer, Integer> nodePosition, String parentNode) {
            int level = 0;
            String mapString = map.get(nodePosition);
            if (parentNode != null) {
                String[] splitParentNode = parentNode.split("_");
                level = Integer.parseInt(splitParentNode[1]);
                if (mapString.equals(splitParentNode[0])) {
                    level += isOuterPosition(nodePosition) ? 1 : -1;
                }
            }
            if (level < 0 || level > MAX_LEVEL_PART_2 || level > 0 && ("AA".equals(mapString) || "ZZ".equals(mapString))) {
                return null;
            }
            String newNodeName;
            if (".".equals(mapString)) {
                newNodeName = "crossing_" + level + "_" + nodePosition;
            } else {
                newNodeName = mapString + "_" + level + "_" + nodePosition;
            }
            return newNodeName;
        }

        private boolean isOuterPosition(Pair<Integer, Integer> nodePosition) {
            return nodePosition.getValue0() == 2 || nodePosition.getValue1() == 2 || nodePosition.getValue0() == maxX || nodePosition.getValue1() == maxY;
        }

        public List<Pair<Integer, Integer>> getPossibleNavigationPositions(Pair<Integer, Integer> currentPosition) {
            List<Pair<Integer, Integer>> list = new ArrayList<>(super.getPossibleNavigationPositions(currentPosition));
            String mapValue = map.get(currentPosition);
            if (mapValue.matches("[A-Z]{2}")) {
                Set<Pair<Integer, Integer>> portal = portals.get(mapValue);
                portal.stream().filter(Predicate.not(currentPosition::equals)).forEach(list::add);
            }
            return list;
        }
    }
}
