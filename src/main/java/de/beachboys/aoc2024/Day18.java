package de.beachboys.aoc2024;

import de.beachboys.Day;
import de.beachboys.Direction;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.*;
import java.util.regex.Pattern;

public class Day18 extends Day {

    private final HashMap<Tuple2<Integer, Integer>, String> map = new HashMap<>();
    private Tuple2<Integer, Integer> start;
    private Tuple2<Integer, Integer> end;
    private int initialBytes;

    public Object part1(List<String> input) {
        init(input);
        return findShortestPath();
    }

    public Object part2(List<String> input) {
        init(input);
        for (int i = initialBytes; i < input.size(); i++) {
            putByteIntoMap(input, i);
            if (findShortestPath() == -1) {
                return input.get(i);
            }
        }
        throw new IllegalArgumentException();
    }

    private void init(List<String> input) {
        initialBytes = Util.getIntValueFromUser("Initial number of bytes to fall", 1024, io);
        int maxXY = Util.getIntValueFromUser("Max x or y value", 70, io);

        parseInitialMap(input, maxXY, initialBytes);

        start = Tuple.tuple(0,0);
        end = Tuple.tuple(maxXY, maxXY);
    }

    private int findShortestPath() {
        Deque<Tuple2<Tuple2<Integer, Integer>, Integer>> queue = new LinkedList<>();
        queue.add(Tuple.tuple(start, 0));
        Set<Tuple2<Integer, Integer>> history = new HashSet<>();
        while (!queue.isEmpty()) {
            Tuple2<Tuple2<Integer, Integer>, Integer> e = queue.poll();
            if (end.equals(e.v1)) {
                return e.v2;
            }
            for (Direction dir : Direction.values()) {
                Tuple2<Integer, Integer> newPos = dir.move(e.v1, 1);
                if (!history.contains(newPos) && ".".equals(map.get(newPos))) {
                    history.add(newPos);
                    queue.add(Tuple.tuple(newPos, e.v2+1));
                }
            }
        }
        return -1;
    }

    private void parseInitialMap(List<String> input, int maxXY, int initialBytes) {
        map.clear();
        for (int i = 0; i <= maxXY; i++) {
            for (int j = 0; j <= maxXY; j++) {
                map.put(Tuple.tuple(i, j), ".");
            }
        }
        for (int i = 0; i < initialBytes; i++) {
            putByteIntoMap(input, i);
        }
    }

    private void putByteIntoMap(List<String> input, int i) {
        String line = input.get(i);
        String[] split = line.split(Pattern.quote(","));
        map.put(Tuple.tuple(Integer.valueOf(split[0]), Integer.valueOf(split[1])), "#");
    }

}
