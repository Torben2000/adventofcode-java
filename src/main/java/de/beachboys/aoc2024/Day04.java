package de.beachboys.aoc2024;

import de.beachboys.Day;
import de.beachboys.Direction;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple2;

import java.util.List;
import java.util.Map;

public class Day04 extends Day {

    public Object part1(List<String> input) {
        Map<Tuple2<Integer, Integer>, String> map = Util.buildImageMap(input);
        List<Tuple2<Integer, Integer>> xList = map.entrySet().stream().filter(e -> e.getValue().equals("X")).map(Map.Entry::getKey).toList();

        long result = 0;

        for (Tuple2<Integer, Integer> x : xList) {
            for (Direction dir : Direction.values()) {
                if ("M".equals(map.get(dir.move(x, 1)))
                        && "A".equals(map.get(dir.move(x, 2)))
                        && "S".equals(map.get(dir.move(x, 3)))) {
                    result++;
                }
                if ("M".equals(map.get(dir.moveDiagonallyLeft(x, 1)))
                        && "A".equals(map.get(dir.moveDiagonallyLeft(x, 2)))
                        && "S".equals(map.get(dir.moveDiagonallyLeft(x, 3)))) {
                    result++;
                }
            }
        }

        return result;
    }

    public Object part2(List<String> input) {
        Map<Tuple2<Integer, Integer>, String> map = Util.buildImageMap(input);
        List<Tuple2<Integer, Integer>> aList = map.entrySet().stream().filter(e -> e.getValue().equals("A")).map(Map.Entry::getKey).toList();

        long result = 0;

        for (Tuple2<Integer, Integer> a : aList) {
            int diagonalMasCount = 0;
            for (Direction dir : Direction.values()) {
                if ("M".equals(map.get(dir.moveDiagonallyLeft(a, 1)))
                        && "S".equals(map.get(dir.getOpposite().moveDiagonallyLeft(a, 1)))) {
                    diagonalMasCount++;
                }
            }
            if (diagonalMasCount == 2) {
                result++;
            }
        }

        return result;
    }

}
