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
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Day20 extends Day {

    public Object part1(List<String> input) {
        Map<Pair<Integer, Integer>, String> map = Util.buildImageMap(input);

        Map<String, Set<Pair<Integer, Integer>>> portals = new HashMap<>();

        setPortalsAsSinglePosition(map);
        map.entrySet().stream().filter(e -> e.getValue().matches("[A-Z]{2}")).forEach(e -> {
            if (!portals.containsKey(e.getValue())) {
                portals.put(e.getValue(), new HashSet<>());
            }
            portals.get(e.getValue()).add(e.getKey());
        });

        Pair<Integer, Integer> startPosition = portals.get("AA").stream().findFirst().orElseThrow();
        GraphConstructionHelperPart1 provider = new GraphConstructionHelperPart1(map, portals);
        Graph<String, DefaultWeightedEdge> graph = Util.buildGraphFromMap(map, startPosition, provider);

        io.logDebug(Util.printGraphAsDOT(graph, Map.of("[", "_", "]", "", " ", "", ",", "_")));

        String aaNode = graph.vertexSet().stream().filter(v -> v.startsWith("AA")).findFirst().orElseThrow();
        String zzNode = graph.vertexSet().stream().filter(v -> v.startsWith("ZZ")).findFirst().orElseThrow();
        DijkstraShortestPath<String, DefaultWeightedEdge> alg = new DijkstraShortestPath<>(graph);


        return (long) alg.getPath(aaNode, zzNode).getWeight();
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

    public Object part2(List<String> input) {
        return 2;
    }

    private static class GraphConstructionHelperPart1 extends GraphConstructionHelper {
        private final Map<String, Set<Pair<Integer, Integer>>> portals;

        public GraphConstructionHelperPart1(Map<Pair<Integer, Integer>, String> map, Map<String, Set<Pair<Integer, Integer>>> portals) {
            super(map);
            this.portals = portals;
        }

        public String getNodeName(Pair<Integer, Integer> nodePosition) {
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
}
