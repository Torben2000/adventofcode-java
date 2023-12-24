package de.beachboys.aoc2022;

import de.beachboys.Day;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple3;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day16 extends Day {

    private static final Pattern LINE_PATTERN = Pattern.compile("Valve ([A-Z]+) has flow rate=([0-9]+); tunnel(s*) lead(s*) to valve(s*) (.*)");
    private final Map<Tuple3<String, Integer, Set<String>>, Integer> cache = new HashMap<>();
    private final Map<String, Integer> flowValues = new HashMap<>();
    private final Map<String, Map<String, Integer>> distances = new HashMap<>();
    public Object part1(List<String> input) {
        fillDistancesAndFlowValues(input);
        return getMaxReleasedPressure("AA", 30, new HashSet<>(flowValues.keySet()));
    }

    public Object part2(List<String> input) {
        fillDistancesAndFlowValues(input);

        List<Set<String>> possibilitiesOfValvesToOpenByOneWorker = getPossibilitiesOfValvesToOpenByOneWorker();
        int result = 0;
        for (Set<String> valvesToOpenByOneWorker : possibilitiesOfValvesToOpenByOneWorker) {
            Set<String> valvesToOpenByOtherWorker = new HashSet<>(flowValues.keySet());
            valvesToOpenByOtherWorker.removeAll(valvesToOpenByOneWorker);
            result = Math.max(result, getMaxReleasedPressure("AA", 26, valvesToOpenByOneWorker) + getMaxReleasedPressure("AA", 26, valvesToOpenByOtherWorker));
        }
        return result;
    }

    private List<Set<String>> getPossibilitiesOfValvesToOpenByOneWorker() {
        List<Set<String>> possibilitiesFromLastRound = List.of(Set.of());
        List<Set<String>> allPossibilities = new ArrayList<>(possibilitiesFromLastRound);
        Set<String> valves = flowValues.keySet();
        for (int numberOfValves = 1; numberOfValves <= valves.size() / 2; numberOfValves++) {
            List<Set<String>> currentPossibilities = new ArrayList<>();
            for (Set<String> possibilityFromLastRound : possibilitiesFromLastRound) {
                String lastValve = possibilityFromLastRound.stream().max(String::compareTo).orElse("AA");
                for (String valve : valves) {
                    if (lastValve.compareTo(valve) < 0) {
                        Set<String> possibility = new HashSet<>(possibilityFromLastRound);
                        possibility.add(valve);
                        currentPossibilities.add(possibility);
                    }
                }
            }
            allPossibilities.addAll(currentPossibilities);
            possibilitiesFromLastRound = currentPossibilities;
        }
        return allPossibilities;
    }

    private int getMaxReleasedPressure(String currentValve, int remainingTime, Set<String> closedValves) {
        if (remainingTime <= 0) {
            return 0;
        }
        Tuple3<String, Integer, Set<String>> cacheKey = Tuple.tuple(currentValve, remainingTime, closedValves);
        if (!cache.containsKey(cacheKey)) {
            int returnValue = 0;
            HashSet<String> newClosedValves = new HashSet<>(closedValves);
            newClosedValves.remove(currentValve);
            for (String nextValve : newClosedValves) {
                returnValue = Math.max(returnValue, getMaxReleasedPressure(nextValve, remainingTime - distances.get(currentValve).get(nextValve) - 1, newClosedValves));
            }
            cache.put(cacheKey, returnValue + flowValues.getOrDefault(currentValve, 0) * remainingTime);
        }
        return cache.get(cacheKey);
    }

    private void fillDistancesAndFlowValues(List<String> input) {
        fillDistanceWithNeighborsAndFlowValues(input);
        fillDistancesBetweenAllValves();
        removeUnnecessaryValvesFromDistances();

        cache.clear();
    }

    private void fillDistanceWithNeighborsAndFlowValues(List<String> input) {
        for (String line : input) {
            Matcher m = LINE_PATTERN.matcher(line);
            if (m.matches()) {
                String valve = m.group(1);
                int flow = Integer.parseInt(m.group(2));
                List<String> neighborValves = Arrays.stream(m.group(6).split(", ")).collect(Collectors.toList());
                if (flow > 0) {
                    flowValues.put(valve, flow);
                }
                Map<String, Integer> distancesFromValve = new HashMap<>();
                for (String neighborValve : neighborValves) {
                    distancesFromValve.put(neighborValve, 1);
                }
                distances.put(valve, distancesFromValve);
            }
        }
    }

    private void fillDistancesBetweenAllValves() {
        boolean change = true;
        while (change) {
            change = false;
            for (String valve : distances.keySet()) {
                for (String otherValve : distances.keySet()) {
                    if (!valve.equals(otherValve)) {
                        Map<String, Integer> distancesFromValve = distances.get(valve);
                        if (!distancesFromValve.containsKey(otherValve)) {
                            int distance = 10000;
                            for (Map.Entry<String, Integer> connectingValveWithDistance : distancesFromValve.entrySet()) {
                                distance = Math.min(distance, connectingValveWithDistance.getValue() + distances.get(connectingValveWithDistance.getKey()).getOrDefault(otherValve, 10000));
                            }
                            if (distance < 10000) {
                                change = true;
                                distancesFromValve.put(otherValve, distance);
                            }
                        }
                    }
                }
            }
        }
    }

    private void removeUnnecessaryValvesFromDistances() {
        Set<String> unnecessaryValves = new HashSet<>(distances.keySet());
        unnecessaryValves.removeAll(flowValues.keySet());
        for (String unneededValve : unnecessaryValves) {
            if (!"AA".equals(unneededValve)) {
                distances.remove(unneededValve);
            }
            for (Map<String, Integer> value : distances.values()) {
                value.remove(unneededValve);
            }
        }
    }

}
