package de.beachboys.ec2025;

import de.beachboys.Direction;
import de.beachboys.Quest;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.*;

public class Quest17 extends Quest {

    private Map<Tuple2<Integer, Integer>, String> map;
    private Tuple2<Integer, Integer> volcano;

    @Override
    public Object part1(List<String> input) {
        parseInput(input);

        int result = 0;
        for (Tuple2<Integer, Integer> cell : getDestroyedCells(10)) {
            if (!"@".equals(map.get(cell))) {
                result += Integer.parseInt(map.get(cell));
            }
        }
        return result;
    }

    @Override
    public Object part2(List<String> input) {
        parseInput(input);

        int maxSum = 0;
        int radiusForMaxSum = 0;
        for (int radius = 0; radius <= input.size() / 2; radius++) {
            int sumForRadius = 0;

            for (Tuple2<Integer, Integer> cell : getDestroyedCells(radius)) {
                if (!"@".equals(map.get(cell)) && !".".equals(map.get(cell))) {
                    sumForRadius += Integer.parseInt(map.get(cell));
                    map.put(cell, ".");
                }
            }
            if (sumForRadius > maxSum) {
                maxSum = sumForRadius;
                radiusForMaxSum = radius;
            }

        }
        return radiusForMaxSum * maxSum;
    }

    @Override
    public Object part3(List<String> input) {
        parseInput(input);
        Tuple2<Integer, Integer> start = map.entrySet().stream().filter(e -> "S".equals(e.getValue())).map(Map.Entry::getKey).findFirst().orElseThrow();

        for (int radius = 0; radius <= input.size() / 2; radius++) {
            for (Tuple2<Integer, Integer> objects : getDestroyedCells(radius)) {
                if (!"@".equals(map.get(objects))) {
                    map.put(objects, ".");
                }
            }

            Integer timeToCloseLoopIfPossible = getTimeToCloseLoop(start, (radius+1)*30);
            if (timeToCloseLoopIfPossible != null) {
                return timeToCloseLoopIfPossible * radius;
            }
        }
        throw new IllegalArgumentException();
    }

    private Set<Tuple2<Integer, Integer>> getDestroyedCells(int radius) {
        Set<Tuple2<Integer, Integer>> destroyedCells = new HashSet<>();
        for (int xOffset = -radius; xOffset <= radius; xOffset++) {
            for (int yOffset = -radius; yOffset <= radius; yOffset++) {
                Tuple2<Integer, Integer> cell = Tuple.tuple(volcano.v1 + xOffset, volcano.v2 + yOffset);
                if (map.containsKey(cell) && ((volcano.v1 - cell.v1) * (volcano.v1 - cell.v1) + (volcano.v2 - cell.v2) * (volcano.v2 - cell.v2) <= radius * radius)) {
                    destroyedCells.add(cell);
                }
            }
        }
        return destroyedCells;
    }

    private Integer getTimeToCloseLoop(Tuple2<Integer, Integer> start, int maxTime) {
        Map<Integer, Set<Tuple2<Tuple2<Integer, Integer>, Direction>>> queue = new HashMap<>();
        queue.put(0, Set.of(Tuple.tuple(start, Direction.WEST)));
        Set<Tuple2<Tuple2<Integer, Integer>, Direction>> seen = new HashSet<>();

        for (int curTime = 0; curTime < maxTime; curTime++) {
            Set<Tuple2<Tuple2<Integer, Integer>, Direction>> positionsWithGeneralDirections = queue.get(curTime);
            if (positionsWithGeneralDirections != null) {
                for (Tuple2<Tuple2<Integer, Integer>, Direction> posWithDir : positionsWithGeneralDirections) {
                    seen.add(posWithDir);
                    for (Direction dir : Direction.values()) {
                        Tuple2<Integer, Integer> newPos = dir.move(posWithDir.v1, 1);
                        if (map.containsKey(newPos) && !".".equals(map.get(newPos))&& !"@".equals(map.get(newPos))) {
                            if (start.equals(newPos)) {
                                if (posWithDir.v2 == Direction.NORTH) {
                                    return curTime;
                                } else {
                                    continue;
                                }
                            }
                            Tuple2<Tuple2<Integer, Integer>, Direction> newPosWithDir = Tuple.tuple(newPos, getNewGeneralDirection(posWithDir, newPos));
                            if (!seen.contains(newPosWithDir)) {
                                int newTime = curTime + Integer.parseInt(map.get(newPos));
                                Set<Tuple2<Tuple2<Integer, Integer>, Direction>> newPositionsWithGeneralDirections = queue.getOrDefault(newTime, new HashSet<>());
                                newPositionsWithGeneralDirections.add(newPosWithDir);
                                queue.put(newTime, newPositionsWithGeneralDirections);
                            }
                        }
                    }
                }
            }

        }
        return null;
    }

    private Direction getNewGeneralDirection(Tuple2<Tuple2<Integer, Integer>, Direction> positionWithGeneralDirection, Tuple2<Integer, Integer> newPosition) {
        if (positionWithGeneralDirection.v2 == Direction.EAST && Objects.equals(newPosition.v2, volcano.v2) && newPosition.v1 > volcano.v1) {
            return Direction.NORTH;
        } else if (positionWithGeneralDirection.v2 == Direction.SOUTH && Objects.equals(newPosition.v1, volcano.v1) && newPosition.v2 > volcano.v2) {
            return Direction.EAST;
        } else if (positionWithGeneralDirection.v2 == Direction.WEST && Objects.equals(newPosition.v2, volcano.v2) && newPosition.v1 < volcano.v1) {
            return Direction.SOUTH;
        }
        return positionWithGeneralDirection.v2;
    }

    private void parseInput(List<String> input) {
        map = Util.buildImageMap(input);
        volcano = map.entrySet().stream().filter(e -> "@".equals(e.getValue())).map(Map.Entry::getKey).findFirst().orElseThrow();
    }
}
