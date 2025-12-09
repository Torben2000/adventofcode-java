package de.beachboys.aoc2025;

import de.beachboys.Day;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.regex.Pattern;

public class Day09 extends Day {

    private final List<Tuple2<Integer, Integer>> redTiles = new ArrayList<>();

    public Object part1(List<String> input) {
        return runLogic(input, (t1, t2) -> true);
    }

    public Object part2(List<String> input) {
        return runLogic(input, this::isRectangleInRedTilesPolygon);
    }

    private long runLogic(List<String> input, BiPredicate<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>> isAllowedRectangle) {
        parseInput(input);
        long result = 0;
        for (int i = 0; i < redTiles.size(); i++) {
            Tuple2<Integer, Integer> t1 = redTiles.get(i);
            for (int j = i + 1; j < redTiles.size(); j++) {
                Tuple2<Integer, Integer> t2 = redTiles.get(j);
                if (isAllowedRectangle.test(t1, t2)) {
                    long area = getBoxArea(t1, t2);
                    if (area > result) {
                        result = area;
                    }
                }
            }
        }
        return result;
    }

    private boolean isRectangleInRedTilesPolygon(Tuple2<Integer, Integer> cornerOfRectangle, Tuple2<Integer, Integer> oppositeCornerOfRectangle) {
        for (int k = 0; k < redTiles.size(); k++) {
            Tuple2<Integer, Integer> lineStart = redTiles.get(k);
            Tuple2<Integer, Integer> lineEnd = redTiles.get((k + 1) % redTiles.size());
            if (lineCrossesRectangleBorders(lineStart, lineEnd, cornerOfRectangle, oppositeCornerOfRectangle)) {
                return false;
            }
        }
        return true;
    }

    private static boolean lineCrossesRectangleBorders(Tuple2<Integer, Integer> lineStart, Tuple2<Integer, Integer> lineEnd, Tuple2<Integer, Integer> cornerOfRectangle, Tuple2<Integer, Integer> oppositeCornerOfRectangle) {
        return Math.min(lineStart.v1, lineEnd.v1) < Math.max(cornerOfRectangle.v1, oppositeCornerOfRectangle.v1)
                && Math.max(lineStart.v1, lineEnd.v1) > Math.min(cornerOfRectangle.v1, oppositeCornerOfRectangle.v1)
                && Math.min(lineStart.v2, lineEnd.v2) < Math.max(cornerOfRectangle.v2, oppositeCornerOfRectangle.v2)
                && Math.max(lineStart.v2, lineEnd.v2) > Math.min(cornerOfRectangle.v2, oppositeCornerOfRectangle.v2);
    }

    private static long getBoxArea(Tuple2<Integer, Integer> t1, Tuple2<Integer, Integer> t2) {
        return (long) (Math.abs(t1.v1 - t2.v1) + 1) * (long)(Math.abs(t1.v2 - t2.v2) + 1);
    }

    private void parseInput(List<String> input) {
        redTiles.clear();
        for (String line : input) {
            String[] split = line.split(Pattern.quote(","));
            redTiles.add(Tuple.tuple(Integer.parseInt(split[0]), Integer.parseInt(split[1])));
        }
    }


}
