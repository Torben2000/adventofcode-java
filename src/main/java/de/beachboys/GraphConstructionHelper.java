package de.beachboys;

import org.javatuples.Pair;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class GraphConstructionHelper {

    private final Set<String> unpassableMapValues;
    protected final Map<Pair<Integer, Integer>, String> map;

    public GraphConstructionHelper(Map<Pair<Integer, Integer>, String> map) {
        this(map, Set.of("#", " "));
    }

    public GraphConstructionHelper(Map<Pair<Integer, Integer>, String> map, Set<String> unpassableMapValues) {
        this.map = map;
        this.unpassableMapValues = unpassableMapValues;
    }

    public List<Pair<Integer, Integer>> getPossibleNavigationPositions(Pair<Integer, Integer> currentPosition) {
        return List.of(Pair.with(currentPosition.getValue0(), currentPosition.getValue1() - 1),
                Pair.with(currentPosition.getValue0(), currentPosition.getValue1() + 1),
                Pair.with(currentPosition.getValue0() - 1, currentPosition.getValue1()),
                Pair.with(currentPosition.getValue0() + 1, currentPosition.getValue1()));
    }

    public String getNodeName(Pair<Integer, Integer> nodePosition, String parentNode) {
        String newNodeName;
        if (".".equals(map.get(nodePosition))) {
            newNodeName = "crossing_" + nodePosition.getValue0() + "_" + nodePosition.getValue1();
        } else {
            newNodeName = map.get(nodePosition);
        }
        return newNodeName;
    }

    public boolean createNodeForPosition(Pair<Integer, Integer> currentPosition) {
        return !map.get(currentPosition).matches("\\.");
    }

    public boolean isPossibleNextStep(Pair<Integer, Integer> pos, Set<Pair<Integer, Integer>> sources) {
        return !unpassableMapValues.contains(map.get(pos));
    }

}
