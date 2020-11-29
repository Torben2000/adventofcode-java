package de.beachboys.aoc2019;

import de.beachboys.Day;
import de.beachboys.Util;
import org.javatuples.Pair;

import java.util.*;

public class Day03 extends Day {

    private final Map<Pair<Integer, Integer>, Integer> wire1Dist = new HashMap<>();
    private final Map<Pair<Integer, Integer>, Integer> wire2Dist = new HashMap<>();

    @Override
    public Object part1(List<String> input) {
        Set<Pair<Integer, Integer>> crossings = getCrossings(input);

        int returnValue = Integer.MAX_VALUE;

        for (Pair<Integer, Integer> coord : crossings) {
            int dist = Math.abs(coord.getValue0()) + Math.abs(coord.getValue1());
            returnValue = Math.min(returnValue, dist);
        }

        return returnValue;
    }

    @Override
    public Object part2(List<String> input) {
        Set<Pair<Integer, Integer>> crossings = getCrossings(input);

        int returnValue = Integer.MAX_VALUE;

        for (Pair<Integer, Integer> coord : crossings) {
            int dist = wire1Dist.get(coord) + wire2Dist.get(coord);
            returnValue = Math.min(returnValue, dist);
        }

        return returnValue;
    }

    private Set<Pair<Integer, Integer>> getCrossings(List<String> input) {
        wire1Dist.clear();
        wire2Dist.clear();
        List<String> wire1 = Util.parseCsv(input.get(0));
        List<String> wire2 = Util.parseCsv(input.get(1));

        Set<Pair<Integer, Integer>> coordinatesWire1 = getWireSet(wire1, wire1Dist);
        Set<Pair<Integer, Integer>> coordinatesWire2 = getWireSet(wire2, wire2Dist);

        coordinatesWire1.retainAll(coordinatesWire2);
        return coordinatesWire1;
    }

    private Set<Pair<Integer, Integer>> getWireSet(List<String> wire, Map<Pair<Integer, Integer>, Integer> wireDist) {
        Set<Pair<Integer, Integer>> coordinatesWire1 = new HashSet<>();
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
                Pair<Integer, Integer> coord = Pair.with(x, y);
                coordinatesWire1.add(coord);
                if (!wireDist.containsKey(coord)) {
                    wireDist.put(coord, distCounter);
                }
            }
        }
        return coordinatesWire1;
    }

}
