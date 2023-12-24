package de.beachboys;

import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class GraphConstructionHelper {

    private final Set<String> unpassableMapValues;
    protected final Map<Tuple2<Integer, Integer>, String> map;

    public GraphConstructionHelper(Map<Tuple2<Integer, Integer>, String> map) {
        this(map, Set.of("#", " "));
    }

    public GraphConstructionHelper(Map<Tuple2<Integer, Integer>, String> map, Set<String> unpassableMapValues) {
        this.map = map;
        this.unpassableMapValues = unpassableMapValues;
    }

    public List<Tuple2<Integer, Integer>> getPossibleNavigationPositions(Tuple2<Integer, Integer> currentPosition) {
        return List.of(Tuple.tuple(currentPosition.v1, currentPosition.v2 - 1),
                Tuple.tuple(currentPosition.v1, currentPosition.v2 + 1),
                Tuple.tuple(currentPosition.v1 - 1, currentPosition.v2),
                Tuple.tuple(currentPosition.v1 + 1, currentPosition.v2));
    }

    public String getNodeName(Tuple2<Integer, Integer> nodePosition, String parentNode) {
        String newNodeName;
        if (".".equals(map.get(nodePosition))) {
            newNodeName = "crossing_" + nodePosition.v1 + "_" + nodePosition.v2;
        } else {
            newNodeName = map.get(nodePosition);
        }
        return newNodeName;
    }

    public boolean createNodeForPosition(Tuple2<Integer, Integer> currentPosition) {
        return !map.get(currentPosition).matches("\\.");
    }

    public boolean isPossibleNextStep(Tuple2<Integer, Integer> pos) {
        return !unpassableMapValues.contains(map.get(pos));
    }

}
