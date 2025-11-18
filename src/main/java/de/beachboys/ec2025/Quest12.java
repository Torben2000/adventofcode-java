package de.beachboys.ec2025;

import de.beachboys.Direction;
import de.beachboys.Quest;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Quest12 extends Quest {

    @Override
    public Object part1(List<String> input) {
        Map<Tuple2<Integer, Integer>, Integer> map = Util.buildIntImageMap(input);
        return destroyBarrels(map, Set.of(Tuple.tuple(0, 0)), Set.of()).size();
    }

    @Override
    public Object part2(List<String> input) {
        Map<Tuple2<Integer, Integer>, Integer> map = Util.buildIntImageMap(input);
        return destroyBarrels(map, Set.of(Tuple.tuple(0, 0), Tuple.tuple(input.getFirst().length() - 1, input.size() - 1)), Set.of()).size();
    }

    @Override
    public Object part3(List<String> input) {
        Map<Tuple2<Integer, Integer>, Integer> map = Util.buildIntImageMap(input);

        Set<Tuple2<Integer, Integer>> allDestroyedBarrels = new HashSet<>();
        for (int i = 0; i < 3; i++) {
            Set<Tuple2<Integer, Integer>> maxSetOfDestroyedBarrels = Set.of();
            for (int x = 0; x < input.getFirst().length(); x++) {
                for (int y = 0; y < input.size(); y++) {
                    Tuple2<Integer, Integer> start = Tuple.tuple(x, y);
                    Set<Tuple2<Integer, Integer>> destroyedBarrels = destroyBarrels(map, Set.of(start), allDestroyedBarrels);
                    if (maxSetOfDestroyedBarrels.size() < destroyedBarrels.size()) {
                        maxSetOfDestroyedBarrels = destroyedBarrels;
                    }
                }
            }
            allDestroyedBarrels.addAll(maxSetOfDestroyedBarrels);
        }

        return allDestroyedBarrels.size();
    }

    private static Set<Tuple2<Integer, Integer>> destroyBarrels(Map<Tuple2<Integer, Integer>, Integer> map, Set<Tuple2<Integer, Integer>> startBarrels, Set<Tuple2<Integer, Integer>> alreadyDestroyedBarrels) {
        Set<Tuple2<Integer, Integer>> destroyed = new HashSet<>();
        Set<Tuple2<Integer, Integer>> ignited = startBarrels;
        while (!ignited.isEmpty()) {
            destroyed.addAll(ignited);
            Set<Tuple2<Integer, Integer>> newIgnited = new HashSet<>();
            for (Tuple2<Integer, Integer> ignitedBarrel : ignited) {
                int barrelSize = map.get(ignitedBarrel);
                for (Tuple2<Integer, Integer> directNeighbor : Direction.getDirectNeighbors(ignitedBarrel)) {
                    if (!alreadyDestroyedBarrels.contains(directNeighbor) && !destroyed.contains(directNeighbor) && map.containsKey(directNeighbor) &&  map.get(directNeighbor) <= barrelSize) {
                        newIgnited.add(directNeighbor);
                    }
                }
            }
            ignited = newIgnited;
        }
        return destroyed;
    }
}
