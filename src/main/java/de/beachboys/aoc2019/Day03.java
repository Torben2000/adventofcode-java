package de.beachboys.aoc2019;

import de.beachboys.Day;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.*;

public class Day03 extends Day {

    private final Map<Tuple2<Integer, Integer>, Integer> wire1Dist = new HashMap<>();
    private final Map<Tuple2<Integer, Integer>, Integer> wire2Dist = new HashMap<>();

    @Override
    public Object part1(List<String> input) {
        Set<Tuple2<Integer, Integer>> crossings = getCrossings(input);

        int returnValue = Integer.MAX_VALUE;

        for (Tuple2<Integer, Integer> position : crossings) {
            int dist = Math.abs(position.v1) + Math.abs(position.v2);
            returnValue = Math.min(returnValue, dist);
        }

        return returnValue;
    }

    @Override
    public Object part2(List<String> input) {
        Set<Tuple2<Integer, Integer>> crossings = getCrossings(input);

        int returnValue = Integer.MAX_VALUE;

        for (Tuple2<Integer, Integer> position : crossings) {
            int dist = wire1Dist.get(position) + wire2Dist.get(position);
            returnValue = Math.min(returnValue, dist);
        }

        return returnValue;
    }

    private Set<Tuple2<Integer, Integer>> getCrossings(List<String> input) {
        wire1Dist.clear();
        wire2Dist.clear();
        List<String> wire1 = Util.parseCsv(input.get(0));
        List<String> wire2 = Util.parseCsv(input.get(1));

        Set<Tuple2<Integer, Integer>> coordinatesWire1 = getWireSet(wire1, wire1Dist);
        Set<Tuple2<Integer, Integer>> coordinatesWire2 = getWireSet(wire2, wire2Dist);

        coordinatesWire1.retainAll(coordinatesWire2);
        return coordinatesWire1;
    }

    private Set<Tuple2<Integer, Integer>> getWireSet(List<String> wire, Map<Tuple2<Integer, Integer>, Integer> wireDist) {
        Set<Tuple2<Integer, Integer>> coordinatesWire1 = new HashSet<>();
        int x = 0;
        int y = 0;
        int distCounter = 0;
        for (String command : wire) {
            String dir = command.substring(0, 1);
            int length = Integer.parseInt(command.substring(1));
            for (int i = 0; i < length; i++) {
                distCounter ++;
                switch (dir) {
                    case "U":
                        y++;
                        break;
                    case "D":
                        y--;
                        break;
                    case "L":
                        x--;
                        break;
                    case "R":
                        x++;
                        break;
                }
                Tuple2<Integer, Integer> position = Tuple.tuple(x, y);
                coordinatesWire1.add(position);
                if (!wireDist.containsKey(position)) {
                    wireDist.put(position, distCounter);
                }
            }
        }
        return coordinatesWire1;
    }

}
