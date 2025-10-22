package de.beachboys.ec2024;

import de.beachboys.Direction;
import de.beachboys.Quest;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple2;

import java.util.*;

public class Quest13 extends Quest {

    @Override
    public Object part1(List<String> input) {
        Map<Tuple2<Integer, Integer>, String> map = Util.buildImageMap(input);
        List<Tuple2<Integer, Integer>> start = map.entrySet().stream().filter(e -> "S".equals(e.getValue())).map(Map.Entry::getKey).toList();
        Tuple2<Integer, Integer> end = map.entrySet().stream().filter(e -> "E".equals(e.getValue())).map(Map.Entry::getKey).findFirst().orElseThrow();

        Set<Tuple2<Integer, Integer>> seen = new HashSet<>();
        Map<Integer, List<Tuple2<Integer, Integer>>> queue = new HashMap<>();
        queue.put(0, start);

        for (int curTime = 0; curTime < Integer.MAX_VALUE; curTime++) {
            List<Tuple2<Integer, Integer>> positions = queue.get(curTime);
            if (positions != null) {
                for (Tuple2<Integer, Integer> pos : positions) {
                    if (end.equals(pos)) {
                        return curTime;
                    }
                    seen.add(pos);
                    for (Direction dir : Direction.values()) {
                        Tuple2<Integer, Integer> newPos = dir.move(pos, 1);
                        if (!seen.contains(newPos) && map.containsKey(newPos) && !"#".equals(map.get(newPos))) {
                            int newTime = curTime + 1 + getLevelDiff(map.get(pos), map.get(newPos));
                            List<Tuple2<Integer, Integer>> newPositions = queue.getOrDefault(newTime, new LinkedList<>());
                            newPositions.add(newPos);
                            queue.put(newTime, newPositions);
                        }
                    }
                }
            }

        }
        throw new IllegalArgumentException();
    }

    private int getLevelDiff(String mapString1, String mapString2) {
        int mapLevel1;
        try {
            mapLevel1 = Integer.parseInt(mapString1);
        } catch (NumberFormatException e) {
            mapLevel1 = 0;
        }
        int mapLevel2;
        try {
            mapLevel2 = Integer.parseInt(mapString2);
        } catch (NumberFormatException e) {
            mapLevel2 = 0;
        }

        int lowerLevel = Math.min(mapLevel1, mapLevel2);
        int higherLevel = Math.max(mapLevel1, mapLevel2);
        if (higherLevel - lowerLevel <= 5) {
            return higherLevel - lowerLevel;
        }
        return (lowerLevel + 10) - higherLevel;
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
