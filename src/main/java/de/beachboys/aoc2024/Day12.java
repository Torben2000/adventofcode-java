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
            Set<Tuple2<Tuple2<Integer, Integer>, Direction>> unvisitedEdges = getEdgesOfRegion(region);
            int totalSides = 0;
            while (!unvisitedEdges.isEmpty()) {
                Tuple2<Tuple2<Integer, Integer>, Direction> initialPosAndDir = unvisitedEdges.stream().findAny().orElseThrow();
                Tuple2<Integer, Integer> initialPos = initialPosAndDir.v1;
                Direction initialDir = initialPosAndDir.v2.turnRight();

                Tuple2<Integer, Integer> pos = initialPos;
                Direction dir = initialDir;
                int sides = 0;
                while (!initialPos.equals(pos) || !initialDir.equals(dir) || sides == 0) {
                    unvisitedEdges.remove(Tuple.tuple(pos, dir.turnLeft()));
                    Tuple2<Integer, Integer> next = dir.move(pos, 1);
                    Tuple2<Integer, Integer> diagonallyLeft = dir.moveDiagonallyLeft(pos, 1);
                    if (region.contains(diagonallyLeft)) {
                        dir = dir.turnLeft();
                        pos = diagonallyLeft;
                        sides++;
                    } else if (!region.contains(next)) {
                        dir = dir.turnRight();
                        sides++;
                    } else {
                        pos = next;
                    }
                }
                totalSides += sides;
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
