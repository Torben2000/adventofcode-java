package de.beachboys.aoc2024;

import de.beachboys.Day;
import de.beachboys.Direction;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.*;

public class Day12 extends Day {

    public Object part1(List<String> input) {
        long result = 0;
        for (Set<Tuple2<Integer, Integer>> region : parseRegions(input)) {
            result += (long) region.size() * getEdgesOfRegion(region).size();
        }
        return result;
    }

    public Object part2(List<String> input) {
        long result = 0;
        for (Set<Tuple2<Integer, Integer>> region : parseRegions(input)) {
            Set<Tuple2<Tuple2<Integer, Integer>, Direction>> edges = getEdgesOfRegion(region);
            int totalSides = edges.size();
            for (Tuple2<Tuple2<Integer, Integer>, Direction> edge : edges) {
                if (edges.contains(Tuple.tuple(edge.v2.turnLeft().move(edge.v1, 1), edge.v2))) {
                    totalSides--;
                }
            }
            result += (long) region.size() * totalSides;
        }
        return result;
    }

    private static Set<Tuple2<Tuple2<Integer, Integer>, Direction>> getEdgesOfRegion(Set<Tuple2<Integer, Integer>> region) {
        Set<Tuple2<Tuple2<Integer, Integer>, Direction>> edges = new HashSet<>();
        for (Tuple2<Integer, Integer> pos : region) {
            for (Direction dir : Direction.values()) {
                if (!region.contains(dir.move(pos, 1))) {
                    edges.add(Tuple.tuple(pos, dir));
                }
            }
        }
        return edges;
    }

    private static List<Set<Tuple2<Integer, Integer>>> parseRegions(List<String> input) {
        Map<Tuple2<Integer, Integer>, String> map = Util.buildImageMap(input);
        List<Set<Tuple2<Integer, Integer>>> regions = new ArrayList<>();
        Set<Tuple2<Integer, Integer>> positionsNotYetInRegions = new HashSet<>(map.keySet());
        while (!positionsNotYetInRegions.isEmpty()) {
            Set<Tuple2<Integer, Integer>> region = new HashSet<>();
            Deque<Tuple2<Integer, Integer>> checkForCurrentRegion = new LinkedList<>();
            checkForCurrentRegion.add(positionsNotYetInRegions.stream().findAny().orElseThrow());
            while (!checkForCurrentRegion.isEmpty()) {
                Tuple2<Integer, Integer> pos = checkForCurrentRegion.poll();
                String plantType = map.get(pos);
                for (Direction dir : Direction.values()) {
                    Tuple2<Integer, Integer> possibleNextPosition = dir.move(pos, 1);
                    if (plantType.equals(map.get(possibleNextPosition)) && !region.contains(possibleNextPosition) && !checkForCurrentRegion.contains(possibleNextPosition)) {
                        checkForCurrentRegion.add(possibleNextPosition);
                    }
                }
                region.add(pos);
                positionsNotYetInRegions.remove(pos);
            }
            regions.add(region);
        }
        return regions;
    }

}
