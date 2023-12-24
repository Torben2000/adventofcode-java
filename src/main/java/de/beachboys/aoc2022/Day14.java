package de.beachboys.aoc2022;

import de.beachboys.Day;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.*;

public class Day14 extends Day {

    public Object part1(List<String> input) {
        return runLogic(input, true);
    }

    public Object part2(List<String> input) {
        return runLogic(input, false);
    }

    private static int runLogic(List<String> input, boolean stopBelowLowestLine) {
        Set<Tuple2<Integer, Integer>> rocks = parseRocks(input);
        int lowestLineOfRocks = rocks.stream().map(Tuple2::v2).max(Integer::compareTo).orElseThrow();

        Set<Tuple2<Integer, Integer>> sands = new HashSet<>();
        while (true) {
            Tuple2<Integer, Integer> newSandPosition = Tuple.tuple(500, 0);
            Tuple2<Integer, Integer> oldSandPosition = null;
            while (!newSandPosition.equals(oldSandPosition)) {
                oldSandPosition = newSandPosition;
                if (oldSandPosition.v2 > lowestLineOfRocks) {
                    if (stopBelowLowestLine) {
                        return sands.size();
                    }
                    break;
                }
                for (int xOffset : new int[]{0, -1, 1}) {
                    Tuple2<Integer, Integer> possibleNewSandPosition = Tuple.tuple(oldSandPosition.v1 + xOffset, oldSandPosition.v2 + 1);
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

    private static Set<Tuple2<Integer, Integer>> parseRocks(List<String> input) {
        Set<Tuple2<Integer, Integer>> rocks = new HashSet<>();
        for (String line : input) {
            String[] cornersAsStrings = line.split(" -> ");
            List<Tuple2<Integer, Integer>> corners = new ArrayList<>();
            for (String cornerAsString : cornersAsStrings) {
                String[] cornerAsStringArray = cornerAsString.split(",");
                corners.add(Tuple.tuple(Integer.parseInt(cornerAsStringArray[0]), Integer.parseInt(cornerAsStringArray[1])));
            }
            for (int j = 0; j < corners.size() - 1; j++) {
                rocks.addAll(Util.drawLine(corners.get(j), corners.get(j + 1)));
            }
        }
        return rocks;
    }

    private static void paintRocksAndSands(Set<Tuple2<Integer, Integer>> rocks, Set<Tuple2<Integer, Integer>> sands) {
        Map<Tuple2<Integer, Integer>, String> map = new HashMap<>();
        for (Tuple2<Integer, Integer> rock : rocks) {
            map.put(rock, "*");
        }
        for (Tuple2<Integer, Integer> rock : sands) {
            map.put(rock, "o");
        }
        System.out.println(Util.paintMap(map));
    }

}
