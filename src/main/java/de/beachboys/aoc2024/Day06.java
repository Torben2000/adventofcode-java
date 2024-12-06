package de.beachboys.aoc2024;

import de.beachboys.Day;
import de.beachboys.Direction;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.*;

public class Day06 extends Day {

    Tuple2<Integer, Integer> bottomRightCorner;
    Tuple2<Integer, Integer> initialGuardPos;

    public Object part1(List<String> input) {
        Map<Tuple2<Integer, Integer>, String> map = parseInputAndInitVars(input);

        return getVisitedPositions(map).size();
    }

    public Object part2(List<String> input) {
        Map<Tuple2<Integer, Integer>, String> map = parseInputAndInitVars(input);

        long result = 0;
        for (Tuple2<Integer, Integer> obstacle : getVisitedPositions(map)) {
            Map<Tuple2<Integer, Integer>, String> mapWithObstacle = new HashMap<>(map);
            mapWithObstacle.put(obstacle, "#");
            if (getVisitedPositions(mapWithObstacle).isEmpty()) {
                result++;
            }
        }
        return result;
    }

    private Map<Tuple2<Integer, Integer>, String> parseInputAndInitVars(List<String> input) {
        Map<Tuple2<Integer, Integer>, String> map = Util.buildImageMap(input);
        initialGuardPos = map.entrySet().stream().filter(e -> e.getValue().equals("^")).map(Map.Entry::getKey).toList().getFirst();
        bottomRightCorner = Tuple.tuple(input.getFirst().length() - 1, input.size() - 1);
        return map;
    }

    private Set<Tuple2<Integer, Integer>> getVisitedPositions(Map<Tuple2<Integer, Integer>, String> map) {
        Tuple2<Integer, Integer> guard = initialGuardPos;
        Direction guardDir = Direction.NORTH;
        Set<Tuple2<Tuple2<Integer, Integer>, Direction>> visited = new HashSet<>();
        while (Util.isInRectangle(guard, Tuple.tuple(0,0), bottomRightCorner)) {
            Tuple2<Integer, Integer> nextGuardPos = guardDir.move(guard, 1);
            while ("#".equals(map.get(nextGuardPos))) {
                guardDir = guardDir.turnRight();
                nextGuardPos = guardDir.move(guard, 1);
            }
            if (!visited.add(Tuple.tuple(guard, guardDir))) {
                //cycle detected => return empty set
                return Set.of();
            }
            guard = guardDir.move(guard, 1);
        }
        return new HashSet<>(visited.stream().map(e -> e.v1).toList());
    }

}
