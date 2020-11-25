package de.beachboys;

import java.util.*;

public class Day03 implements Day {

    private Map<String, Integer> wire1Dist = new HashMap<>();
    private Map<String, Integer> wire2Dist = new HashMap<>();

    @Override
    public String part1(List<String> input) {
        Set<String> crossings = getCrossings(input);

        int returnValue = Integer.MAX_VALUE;

        for (String coord : crossings) {
            String[] val = coord.split("x");
            int dist = Math.abs(Integer.parseInt(val[0])) + Math.abs(Integer.parseInt(val[1]));
            returnValue = Math.min(returnValue, dist);
        }

        return returnValue + "";
    }

    @Override
    public String part2(List<String> input) {
        Set<String> crossings = getCrossings(input);

        int returnValue = Integer.MAX_VALUE;

        for (String coord : crossings) {
            String[] val = coord.split("x");
            int dist = wire1Dist.get(coord) + wire2Dist.get(coord);
            returnValue = Math.min(returnValue, dist);
        }

        return returnValue + "";
    }

    private Set<String> getCrossings(List<String> input) {
        wire1Dist.clear();
        wire2Dist.clear();
        List<String> wire1 = Arrays.asList(input.get(0).split(","));
        List<String> wire2 = Arrays.asList(input.get(1).split(","));

        Set<String> coordinatesWire1 = getWireSet(wire1, wire1Dist);
        Set<String> coordinatesWire2 = getWireSet(wire2, wire2Dist);

        coordinatesWire1.retainAll(coordinatesWire2);
        return coordinatesWire1;
    }

    private Set<String> getWireSet(List<String> wire, Map<String, Integer> wireDist) {
        Set<String> coordinatesWire1 = new HashSet<>();
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
                coordinatesWire1.add(x + "x" + y);
                if (!wireDist.containsKey(x + "x" + y)) {
                    wireDist.put(x + "x" + y, distCounter);
                }
            }
        }
        return coordinatesWire1;
    }

}
