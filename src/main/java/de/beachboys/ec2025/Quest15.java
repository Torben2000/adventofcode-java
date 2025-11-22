package de.beachboys.ec2025;

import de.beachboys.Direction;
import de.beachboys.Quest;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.*;

public class Quest15 extends Quest {

    private List<Tuple2<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>>> walls;
    private final Tuple2<Integer, Integer> start = Tuple.tuple(0, 0);
    private Tuple2<Integer, Integer> end;
    private List<Integer> sortedRelevantX;
    private List<Integer> sortedRelevantY;

    @Override
    public Object part1(List<String> input) {
        parseInput(input);
        fillSortedRelevantCoordinates();

        Tuple2<Integer, Integer> startIndex = Tuple.tuple(sortedRelevantX.indexOf(start.v1), sortedRelevantY.indexOf(start.v2));
        Tuple2<Integer, Integer> endIndex = Tuple.tuple(sortedRelevantX.indexOf(end.v1), sortedRelevantY.indexOf(end.v2));

        Set<Tuple2<Integer, Integer>> seen = new HashSet<>();
        Map<Long, Set<Tuple2<Integer, Integer>>> queue = new HashMap<>();
        queue.put(0L, Set.of(startIndex));

        for (long pathLength = 0; pathLength < Long.MAX_VALUE; pathLength++) {
            Set<Tuple2<Integer, Integer>> indexes = queue.get(pathLength);
            if (indexes != null) {
                for (Tuple2<Integer, Integer> index : indexes) {
                    if (endIndex.equals(index)) {
                        return pathLength;
                    }
                    seen.add(index);
                    for (Direction dir : Direction.values()) {
                        Tuple2<Integer, Integer> newIndex = dir.move(index, 1);
                        if (!seen.contains(newIndex) && newIndex.v1 >= 0 && newIndex.v1 < sortedRelevantX.size() && newIndex.v2 >= 0 && newIndex.v2 < sortedRelevantY.size()) {
                            Tuple2<Integer, Integer> realPos = Tuple.tuple(sortedRelevantX.get(newIndex.v1), sortedRelevantY.get(newIndex.v2));
                            if (startIndex.equals(newIndex) || endIndex.equals(newIndex) || !isInWall(realPos)) {
                                Tuple2<Integer, Integer> oldRealPos = Tuple.tuple(sortedRelevantX.get(index.v1), sortedRelevantY.get(index.v2));
                                long newPathLength = pathLength + Util.getManhattanDistance(oldRealPos, realPos);
                                Set<Tuple2<Integer, Integer>> newIndexes = queue.getOrDefault(newPathLength, new HashSet<>());
                                newIndexes.add(newIndex);
                                queue.put(newPathLength, newIndexes);
                            }
                        }
                    }
                }
            }
        }
        throw new IllegalArgumentException();
    }

    private boolean isInWall(Tuple2<Integer, Integer> pos) {
        for (Tuple2<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>> wall : walls) {
            if (pos.v1 >= Math.min(wall.v1.v1, wall.v2.v1)
                    && pos.v1 <= Math.max(wall.v1.v1, wall.v2.v1)
                    && pos.v2 >= Math.min(wall.v1.v2, wall.v2.v2)
                    && pos.v2 <= Math.max(wall.v1.v2, wall.v2.v2)) {
                return true;
            }
        }
        return false;
    }

    private void parseInput(List<String> input) {
        walls = new ArrayList<>();
        Tuple2<Integer, Integer> pos = start;
        Direction dir = Direction.NORTH;
        for (String instruction : Util.parseCsv(input.getFirst())) {
            int wallLength = Integer.parseInt(instruction.substring(1));
            dir = "L".equals(instruction.substring(0, 1)) ? dir.turnLeft() : dir.turnRight();
            Tuple2<Integer, Integer> newPos = dir.move(pos, wallLength);
            walls.add(Tuple.tuple(pos, newPos));
            pos = newPos;
        }
        end = pos;
    }

    private void fillSortedRelevantCoordinates() {
        Set<Integer> relevantX = new HashSet<>();
        Set<Integer> relevantY = new HashSet<>();
        for (Tuple2<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>> wall : walls) {
            relevantX.add(wall.v1.v1);
            relevantX.add(wall.v1.v1 + 1);
            relevantX.add(wall.v1.v1 - 1);
            relevantX.add(wall.v2.v1);
            relevantX.add(wall.v2.v1 + 1);
            relevantX.add(wall.v2.v1 - 1);
            relevantY.add(wall.v1.v2);
            relevantY.add(wall.v1.v2 + 1);
            relevantY.add(wall.v1.v2 - 1);
            relevantY.add(wall.v2.v2);
            relevantY.add(wall.v2.v2 + 1);
            relevantY.add(wall.v2.v2 - 1);
        }
        sortedRelevantX = new ArrayList<>(relevantX);
        sortedRelevantY = new ArrayList<>(relevantY);
        sortedRelevantX.sort(Integer::compareTo);
        sortedRelevantY.sort(Integer::compareTo);
    }

    @Override
    public Object part2(List<String> input) {
        return part1(input);
    }

    @Override
    public Object part3(List<String> input) {
        return part2(input);
    }
}
