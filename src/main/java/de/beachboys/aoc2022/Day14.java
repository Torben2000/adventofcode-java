package de.beachboys.aoc2022;

import de.beachboys.Day;
import de.beachboys.Util;
import org.javatuples.Pair;

import java.util.*;

public class Day14 extends Day {

    public Object part1(List<String> input) {
        return runLogic(input, true);
    }

    public Object part2(List<String> input) {
        return runLogic(input, false);
    }

    private static int runLogic(List<String> input, boolean stopBelowLowestLine) {
        Set<Pair<Integer, Integer>> rocks = parseRocks(input);
        int lowestLineOfRocks = rocks.stream().map(Pair::getValue1).max(Integer::compareTo).orElseThrow();

        Set<Pair<Integer, Integer>> sands = new HashSet<>();
        while (true) {
            Pair<Integer, Integer> newSandPosition = Pair.with(500, 0);
            Pair<Integer, Integer> oldSandPosition = null;
            while (!newSandPosition.equals(oldSandPosition)) {
                oldSandPosition = newSandPosition;
                if (oldSandPosition.getValue1() > lowestLineOfRocks) {
                    if (stopBelowLowestLine) {
                        return sands.size();
                    }
                    break;
                }
                for (int xOffset : new int[]{0, -1, 1}) {
                    Pair<Integer, Integer> possibleNewSandPosition = Pair.with(oldSandPosition.getValue0() + xOffset, oldSandPosition.getValue1() + 1);
                    if (!rocks.contains(possibleNewSandPosition) && !sands.contains(possibleNewSandPosition)) {
                        newSandPosition = possibleNewSandPosition;
                        break;
                    }
                }
            }
            if (sands.contains(newSandPosition)) {
                break;
            }
            sands.add(newSandPosition);
        }

        paintRocksAndSands(rocks, sands);

        return sands.size();
    }

    private static Set<Pair<Integer, Integer>> parseRocks(List<String> input) {
        Set<Pair<Integer, Integer>> rocks = new HashSet<>();
        for (String line : input) {
            String[] cornersAsStrings = line.split(" -> ");
            List<Pair<Integer, Integer>> corners = new ArrayList<>();
            for (String cornerAsString : cornersAsStrings) {
                String[] cornerAsStringArray = cornerAsString.split(",");
                corners.add(Pair.with(Integer.parseInt(cornerAsStringArray[0]), Integer.parseInt(cornerAsStringArray[1])));
            }
            for (int j = 0; j < corners.size() - 1; j++) {
                Pair<Integer, Integer> corner1 = corners.get(j);
                Pair<Integer, Integer> corner2 = corners.get(j + 1);
                if (corner1.getValue0().equals(corner2.getValue0())) {
                    for (int k = Math.min(corner1.getValue1(), corner2.getValue1()); k <= Math.max(corner1.getValue1(), corner2.getValue1()); k++) {
                        rocks.add(Pair.with(corner1.getValue0(), k));
                    }
                } else {
                    for (int k = Math.min(corner1.getValue0(), corner2.getValue0()); k <= Math.max(corner1.getValue0(), corner2.getValue0()); k++) {
                        rocks.add(Pair.with(k, corner1.getValue1()));
                    }
                }
            }
        }
        return rocks;
    }

    private static void paintRocksAndSands(Set<Pair<Integer, Integer>> rocks, Set<Pair<Integer, Integer>> sands) {
        Map<Pair<Integer, Integer>, String> map = new HashMap<>();
        for (Pair<Integer, Integer> rock : rocks) {
            map.put(rock, "*");
        }
        for (Pair<Integer, Integer> rock : sands) {
            map.put(rock, "o");
        }
        System.out.println(Util.paintMap(map));
    }

}
