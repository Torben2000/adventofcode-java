package de.beachboys.ec2024;

import de.beachboys.Direction;
import de.beachboys.Quest;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple2;

import java.util.*;

public class Quest03 extends Quest {

    public static final String DIGGABLE_SPOT = "#";
    public static final String NONDIGGABLE_SPOT = ".";

    @Override
    public Object part1(List<String> input) {
        return runLogic(input, false);
    }

    @Override
    public Object part2(List<String> input) {
        return runLogic(input, false);
    }

    @Override
    public Object part3(List<String> input) {
        return runLogic(input, true);
    }

    private static long runLogic(List<String> input, boolean includingDiagonally) {
        long result = 0;
        Map<Tuple2<Integer, Integer>, String> map = Util.buildImageMap(input);
        while (map.containsValue(DIGGABLE_SPOT)) {
            Map<Tuple2<Integer, Integer>, String> newMap = new HashMap<>();
            for (Tuple2<Integer, Integer> pos : map.keySet()) {
                if (DIGGABLE_SPOT.equals(map.get(pos))) {
                    result++;
                    boolean canDigFurther = true;
                    for (Tuple2<Integer, Integer> neighborPos : Direction.getDirectNeighbors(pos, includingDiagonally)) {
                        if (!map.containsKey(neighborPos) || NONDIGGABLE_SPOT.equals(map.get(neighborPos))) {
                            canDigFurther = false;
                            break;
                        }
                    }
                    if (canDigFurther) {
                        newMap.put(pos, DIGGABLE_SPOT);
                    } else {
                        newMap.put(pos, NONDIGGABLE_SPOT);
                    }
                } else {
                    newMap.put(pos, NONDIGGABLE_SPOT);
                }
            }
            map = newMap;
        }
       return result;
    }
}
