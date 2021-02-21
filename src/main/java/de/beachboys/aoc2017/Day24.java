package de.beachboys.aoc2017;

import de.beachboys.Day;
import org.javatuples.Pair;

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

    private void fillStrongestBridgeByLengthBruteForce(int connector, List<Pair<Integer, Integer>> components, int currentLength, int currentStrength) {
        for (Pair<Integer, Integer> component : components) {
            if (component.getValue0() == connector || component.getValue1() == connector) {
                List<Pair<Integer, Integer>> otherComponents = new ArrayList<>(components);
                otherComponents.remove(component);
                int newConnector = component.getValue0() == connector ? component.getValue1() : component.getValue0();
                int newStrength = currentStrength + component.getValue0() + component.getValue1();
                fillStrongestBridgeByLengthBruteForce(newConnector, otherComponents, currentLength + 1, newStrength);
            }
        }
        strongestBridgeByLength.put(currentLength, Math.max(strongestBridgeByLength.getOrDefault(currentLength, 0), currentStrength));
    }

    private List<Pair<Integer, Integer>> parseComponents(List<String> input) {
        List<Pair<Integer, Integer>> components = new ArrayList<>();
        for (String line : input) {
            String[] split = line.split("/");
            components.add(Pair.with(Integer.valueOf(split[0]), Integer.valueOf(split[1])));
        }
        return components;
    }

}
