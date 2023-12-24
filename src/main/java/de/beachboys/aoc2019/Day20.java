package de.beachboys.aoc2019;

import de.beachboys.Day;
import de.beachboys.GraphConstructionHelper;
import de.beachboys.Util;
import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;
import org.jooq.lambda.tuple.Tuple3;

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

    private GraphConstructionHelper getGraphConstructionHelperPart1(Map<Tuple2<Integer, Integer>, String> map, Map<String, Set<Tuple2<Integer, Integer>>> portals) {
        return new GraphConstructionHelperPart1(map, portals);
    }

    private GraphConstructionHelper getGraphConstructionHelperPart2(Map<Tuple2<Integer, Integer>, String> map, Map<String, Set<Tuple2<Integer, Integer>>> portals) {
        return new GraphConstructionHelperPart2(map, portals);
    }

    private Object runLogic(List<String> input, BiFunction<Map<Tuple2<Integer, Integer>, String>, Map<String, Set<Tuple2<Integer, Integer>>>, GraphConstructionHelper> helperBuilder) {
        Map<Tuple2<Integer, Integer>, String> map = Util.buildImageMap(input);

        setPortalsAsSinglePosition(map);
        Map<String, Set<Tuple2<Integer, Integer>>> portals = buildPortalsMap(map);

        Tuple2<Integer, Integer> startPosition = portals.get("AA").stream().findFirst().orElseThrow();
        GraphConstructionHelper helper = helperBuilder.apply(map, portals);
        Graph<String, DefaultWeightedEdge> graph = Util.buildGraphFromMap(map, startPosition, helper);

        io.logDebug(Util.printGraphAsDOT(graph, Map.of("(", "_", ")", "", " ", "", ",", "_")));

        String aaNode = graph.vertexSet().stream().filter(v -> v.startsWith("AA")).findFirst().orElseThrow();
        String zzNode = graph.vertexSet().stream().filter(v -> v.startsWith("ZZ")).findFirst().orElseThrow();
        DijkstraShortestPath<String, DefaultWeightedEdge> alg = new DijkstraShortestPath<>(graph);

        return (long) alg.getPath(aaNode, zzNode).getWeight();
    }

    private Map<String, Set<Tuple2<Integer, Integer>>> buildPortalsMap(Map<Tuple2<Integer, Integer>, String> map) {
        Map<String, Set<Tuple2<Integer, Integer>>> portals = new HashMap<>();
        map.entrySet().stream().filter(e -> e.getValue().matches("[A-Z]{2}")).forEach(e -> {
            if (!portals.containsKey(e.getValue())) {
                portals.put(e.getValue(), new HashSet<>());
            }
            portals.get(e.getValue()).add(e.getKey());
        });
        return portals;
    }

    private void setPortalsAsSinglePosition(Map<Tuple2<Integer, Integer>, String> map) {
        Set<Tuple2<Integer, Integer>> letterPositions =  map.keySet().stream().filter(k -> map.get(k).matches("[A-Z]")).collect(Collectors.toSet());
        Set<Tuple2<Integer, Integer>> processedLetterPositions = new HashSet<>();
        for (Tuple2<Integer, Integer> letterPosition : letterPositions) {
            if (processedLetterPositions.contains(letterPosition)) {
                continue;
            }
            String thisLetter = map.get(letterPosition);
            Tuple3<Tuple2<Integer, Integer>, Boolean, Boolean> neighborPosition = getLetterNeighbor(map, letterPosition);

            String neighborLetter = map.get(neighborPosition.v1);
            String portalString = neighborPosition.v2 ? neighborLetter + thisLetter : thisLetter + neighborLetter;
            if (neighborPosition.v3) {
                map.put(letterPosition, " ");
                map.put(neighborPosition.v1, " ");
                map.put(Tuple.tuple(2 * neighborPosition.v1.v1 - letterPosition.v1, 2 * neighborPosition.v1.v2 - letterPosition.v2), portalString);
            } else {
                map.put(letterPosition, " ");
                map.put(neighborPosition.v1, " ");
                map.put(Tuple.tuple(2 * letterPosition.v1 - neighborPosition.v1.v1, 2 * letterPosition.v2 - neighborPosition.v1.v2), portalString);
            }
            processedLetterPositions.add(letterPosition);
            processedLetterPositions.add(neighborPosition.v1);
        }
    }

    private Tuple3<Tuple2<Integer, Integer>, Boolean, Boolean> getLetterNeighbor(Map<Tuple2<Integer, Integer>, String> map, Tuple2<Integer, Integer> letterPosition) {
        Tuple2<Integer, Integer> north = Tuple.tuple(letterPosition.v1, letterPosition.v2 - 1);
        Tuple2<Integer, Integer> south = Tuple.tuple(letterPosition.v1, letterPosition.v2 + 1);
        Tuple2<Integer, Integer> west = Tuple.tuple(letterPosition.v1 - 1, letterPosition.v2);
        Tuple2<Integer, Integer> east = Tuple.tuple(letterPosition.v1 + 1, letterPosition.v2);
        if (map.containsKey(north) && map.get(north).matches("[A-Z]")) {
            return Tuple.tuple(north, true, !".".equals(map.get(south)));
        } else if (map.containsKey(south) && map.get(south).matches("[A-Z]")) {
            return Tuple.tuple(south, false, !".".equals(map.get(north)));
        } else if (map.containsKey(west) && map.get(west).matches("[A-Z]")) {
            return Tuple.tuple(west, true, !".".equals(map.get(east)));
        } else if (map.containsKey(east) && map.get(east).matches("[A-Z]")) {
            return Tuple.tuple(east, false, !".".equals(map.get(west)));
        }
        throw new IllegalArgumentException("no neighbor found");
    }

    private static class GraphConstructionHelperPart1 extends GraphConstructionHelper {
        private final Map<String, Set<Tuple2<Integer, Integer>>> portals;

        public GraphConstructionHelperPart1(Map<Tuple2<Integer, Integer>, String> map, Map<String, Set<Tuple2<Integer, Integer>>> portals) {
            super(map);
            this.portals = portals;
        }

        public String getNodeName(Tuple2<Integer, Integer> nodePosition, String parentNode) {
            String newNodeName;
            if (".".equals(map.get(nodePosition))) {
                newNodeName = "crossing" + nodePosition;
            } else {
                newNodeName = map.get(nodePosition) + nodePosition;
            }
            return newNodeName;
        }

        public List<Tuple2<Integer, Integer>> getPossibleNavigationPositions(Tuple2<Integer, Integer> currentPosition) {
            List<Tuple2<Integer, Integer>> list = new ArrayList<>(super.getPossibleNavigationPositions(currentPosition));
            String mapValue = map.get(currentPosition);
            if (mapValue.matches("[A-Z]{2}")) {
                Set<Tuple2<Integer, Integer>> portal = portals.get(mapValue);
                portal.stream().filter(Predicate.not(currentPosition::equals)).forEach(list::add);
            }
            return list;
        }
    }

    private static class GraphConstructionHelperPart2 extends GraphConstructionHelper {
        private final Map<String, Set<Tuple2<Integer, Integer>>> portals;

        private final int maxX;
        private final int maxY;

        public GraphConstructionHelperPart2(Map<Tuple2<Integer, Integer>, String> map, Map<String, Set<Tuple2<Integer, Integer>>> portals) {
            super(map);
            this.portals = portals;
            maxX = portals.values().stream().flatMap(Collection::stream).map(Tuple2::v1).max(Integer::compareTo).orElseThrow();
            maxY = portals.values().stream().flatMap(Collection::stream).map(Tuple2::v2).max(Integer::compareTo).orElseThrow();
        }

        public String getNodeName(Tuple2<Integer, Integer> nodePosition, String parentNode) {
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

        private boolean isOuterPosition(Tuple2<Integer, Integer> nodePosition) {
            return nodePosition.v1 == 2 || nodePosition.v2 == 2 || nodePosition.v1 == maxX || nodePosition.v2 == maxY;
        }

        public List<Tuple2<Integer, Integer>> getPossibleNavigationPositions(Tuple2<Integer, Integer> currentPosition) {
            List<Tuple2<Integer, Integer>> list = new ArrayList<>(super.getPossibleNavigationPositions(currentPosition));
            String mapValue = map.get(currentPosition);
            if (mapValue.matches("[A-Z]{2}")) {
                Set<Tuple2<Integer, Integer>> portal = portals.get(mapValue);
                portal.stream().filter(Predicate.not(currentPosition::equals)).forEach(list::add);
            }
            return list;
        }
    }
}
