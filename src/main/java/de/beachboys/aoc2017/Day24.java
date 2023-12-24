package de.beachboys.aoc2017;

import de.beachboys.Day;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day24 extends Day {

    private final Map<Integer, Integer> strongestBridgeByLength = new HashMap<>();

    public Object part1(List<String> input) {
        fillStrongestBridgeByLength(input);
        return strongestBridgeByLength.values().stream().mapToInt(Integer::intValue).max().orElseThrow();
    }

    public Object part2(List<String> input) {
        fillStrongestBridgeByLength(input);
        return strongestBridgeByLength.get(strongestBridgeByLength.keySet().stream().mapToInt(Integer::intValue).max().orElseThrow());
    }

    private void fillStrongestBridgeByLength(List<String> input) {
        fillStrongestBridgeByLengthBruteForce(0, parseComponents(input), 0, 0);
    }

    private void fillStrongestBridgeByLengthBruteForce(int connector, List<Tuple2<Integer, Integer>> components, int currentLength, int currentStrength) {
        for (Tuple2<Integer, Integer> component : components) {
            if (component.v1 == connector || component.v2 == connector) {
                List<Tuple2<Integer, Integer>> otherComponents = new ArrayList<>(components);
                otherComponents.remove(component);
                int newConnector = component.v1 == connector ? component.v2 : component.v1;
                int newStrength = currentStrength + component.v1 + component.v2;
                fillStrongestBridgeByLengthBruteForce(newConnector, otherComponents, currentLength + 1, newStrength);
            }
        }
        strongestBridgeByLength.put(currentLength, Math.max(strongestBridgeByLength.getOrDefault(currentLength, 0), currentStrength));
    }

    private List<Tuple2<Integer, Integer>> parseComponents(List<String> input) {
        List<Tuple2<Integer, Integer>> components = new ArrayList<>();
        for (String line : input) {
            String[] split = line.split("/");
            components.add(Tuple.tuple(Integer.valueOf(split[0]), Integer.valueOf(split[1])));
        }
        return components;
    }

}
