package de.beachboys.aoc2025;

import de.beachboys.Day;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;
import org.jooq.lambda.tuple.Tuple3;

import java.util.*;
import java.util.regex.Pattern;

public class Day08 extends Day {

    public Object part1(List<String> input) {
        List<Tuple3<Integer, Integer, Integer>> listOfJunctionBoxes = parseInput(input);
        Map<Long, Tuple2<Tuple3<Integer, Integer, Integer>, Tuple3<Integer, Integer, Integer>>> distances = getDistances(listOfJunctionBoxes);
        Map<Tuple3<Integer, Integer, Integer>, Integer> circuits = getInitialCircuits(listOfJunctionBoxes);

        int remainingConnectionsToMake = Util.getIntValueFromUser("Number of connections to make", 1000, io);

        for (long distance : distances.keySet().stream().sorted().toList()) {
            Tuple2<Tuple3<Integer, Integer, Integer>, Tuple3<Integer, Integer, Integer>> junctionBoxPair = distances.get(distance);
            Tuple3<Integer, Integer, Integer> one = junctionBoxPair.v1;
            Tuple3<Integer, Integer, Integer> two = junctionBoxPair.v2;
            int circuit1 = circuits.get(one);
            int circuit2 = circuits.get(two);
            if (!Objects.equals(circuit1, circuit2)) {
                for (Tuple3<Integer, Integer, Integer> junctionBox : circuits.keySet()) {
                    if (circuits.get(junctionBox) == circuit1) {
                        circuits.put(junctionBox, circuit2);
                    }
                }
            }

            remainingConnectionsToMake--;
            if (remainingConnectionsToMake == 0) {
                Map<Integer, Integer> count = new HashMap<>();
                for (int circuit : circuits.values()) {
                    count.put(circuit, count.getOrDefault(circuit, 0) + 1);
                }
                List<Integer> reverseSortedCounts = count.values().stream().sorted(Comparator.reverseOrder()).toList();
                return reverseSortedCounts.get(0) * reverseSortedCounts.get(1) * reverseSortedCounts.get(2);
            }

        }
        throw new IllegalArgumentException();
    }

    public Object part2(List<String> input) {
        List<Tuple3<Integer, Integer, Integer>> listOfJunctionBoxes = parseInput(input);
        Map<Long, Tuple2<Tuple3<Integer, Integer, Integer>, Tuple3<Integer, Integer, Integer>>> distances = getDistances(listOfJunctionBoxes);
        Map<Tuple3<Integer, Integer, Integer>, Integer> circuits = getInitialCircuits(listOfJunctionBoxes);

        for (long distance : distances.keySet().stream().sorted().toList()) {
            Tuple2<Tuple3<Integer, Integer, Integer>, Tuple3<Integer, Integer, Integer>> junctionBoxPair = distances.get(distance);
            Tuple3<Integer, Integer, Integer> one = junctionBoxPair.v1;
            Tuple3<Integer, Integer, Integer> two = junctionBoxPair.v2;
            int circuit1 = circuits.get(one);
            int circuit2 = circuits.get(two);
            if (!Objects.equals(circuit1, circuit2)) {
                for (Tuple3<Integer, Integer, Integer> junctionBox : circuits.keySet()) {
                    if (circuits.get(junctionBox) == circuit1) {
                        circuits.put(junctionBox, circuit2);
                    }
                }
                if (new HashSet<>(circuits.values()).size() == 1) {
                    return one.v1 * two.v1;
                }
            }
        }
        throw new IllegalArgumentException();
    }

    private static Map<Tuple3<Integer, Integer, Integer>, Integer> getInitialCircuits(List<Tuple3<Integer, Integer, Integer>> listOfJunctionBoxes) {
        Map<Tuple3<Integer, Integer, Integer>, Integer> circuits = new HashMap<>();
        for (int j = 0; j < listOfJunctionBoxes.size(); j++) {
            circuits.put(listOfJunctionBoxes.get(j), j);
        }
        return circuits;
    }

    private static Map<Long, Tuple2<Tuple3<Integer, Integer, Integer>, Tuple3<Integer, Integer, Integer>>> getDistances(List<Tuple3<Integer, Integer, Integer>> listOfJunctionBoxes) {
        Map<Long, Tuple2<Tuple3<Integer, Integer, Integer>, Tuple3<Integer, Integer, Integer>>> distances = new HashMap<>();
        for (int i = 0; i < listOfJunctionBoxes.size(); i++) {
            for (int j = i + 1; j < listOfJunctionBoxes.size(); j++) {
                Tuple3<Integer, Integer, Integer> one = listOfJunctionBoxes.get(i);
                Tuple3<Integer, Integer, Integer> two = listOfJunctionBoxes.get(j);
                long d = (long)(one.v1 - two.v1) * (one.v1 - two.v1) + (long)(one.v2 - two.v2) * (one.v2 - two.v2) + (long)(one.v3 - two.v3) * (one.v3-two.v3);
                distances.put(d, Tuple.tuple(one, two));
            }
        }
        return distances;
    }

    private static List<Tuple3<Integer, Integer, Integer>> parseInput(List<String> input) {
        List<Tuple3<Integer, Integer, Integer>> listOfJunctionBoxes = new ArrayList<>();
        for (String line : input) {
            String[] split = line.split(Pattern.quote(","));
            Tuple3<Integer, Integer, Integer> j = Tuple.tuple(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
            listOfJunctionBoxes.add(j);
        }
        return listOfJunctionBoxes;
    }

}
