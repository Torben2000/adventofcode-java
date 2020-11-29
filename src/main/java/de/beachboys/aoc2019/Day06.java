package de.beachboys.aoc2019;

import de.beachboys.Day;
import de.beachboys.Util;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Day06 extends Day {

    public Object part1(List<String> input) {
        Map<String, String> orbits = buildOrbitMap(input);
        return orbits.keySet().stream().mapToInt(e -> getIndirectOrbits(e, orbits)).sum();
    }

    private Map<String, String> buildOrbitMap(List<String> input) {
        Map<String, String> orbits = new HashMap<>();
        for (String line : input) {
            List<String> orbitPair = Util.parseToList(line, "\\)");
            orbits.put(orbitPair.get(1), orbitPair.get(0));
        }
        return orbits;
    }

    private int getIndirectOrbits(String planet, Map<String, String> orbits) {
        if (planet.equals("COM")) {
            return 0;
        }
        return getIndirectOrbits(orbits.get(planet), orbits) + 1;
    }

    public Object part2(List<String> input) {
        Map<String, String> orbits = buildOrbitMap(input);
        List<String> myPath = getOrbitPath("YOU", new LinkedList<>(), orbits);
        List<String> santasPath = getOrbitPath("SAN", new LinkedList<>(), orbits);
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < myPath.size(); i++) {
            for (int j = 0; j < santasPath.size(); j++) {
                if (myPath.get(i).equals(santasPath.get(j))) {
                    min = Math.min(min, i + j);
                }
            }

        }
        return min;
    }

    private List<String> getOrbitPath(String start, List<String> currentPath, Map<String, String> orbits) {
        if (start.equals("COM")) {
            return currentPath;
        }
        String parent = orbits.get(start);
        currentPath.add(parent);
        return getOrbitPath(parent, currentPath, orbits);
    }

}
