package de.beachboys.aoc2024;

import de.beachboys.Day;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day23 extends Day {

    Map<String, Set<String>> mapOfConnectedComputers = new HashMap<>();

    public Object part1(List<String> input) {
        parseInput(input);

        Set<Set<String>> setsOfThreeWithT = new HashSet<>();
        for (String computer : mapOfConnectedComputers.keySet()) {
            if (computer.startsWith("t")) {
                Set<String> connectedComputers = mapOfConnectedComputers.get(computer);
                for (String connectedComputer : connectedComputers) {
                    for (String thirdComputer : mapOfConnectedComputers.get(connectedComputer)) {
                        if (connectedComputers.contains(thirdComputer)) {
                            Set<String> setOfThreeWithT = new HashSet<>();
                            setOfThreeWithT.add(computer);
                            setOfThreeWithT.add(connectedComputer);
                            setOfThreeWithT.add(thirdComputer);
                            setsOfThreeWithT.add(setOfThreeWithT);
                        }
                    }
                }
            }
        }
        return setsOfThreeWithT.size();
    }

    public Object part2(List<String> input) {
        parseInput(input);

        Set<Set<String>> setsOfConnectedComputers = getSetsOfConnectedComputers();

        Set<String> resultSet = Set.of();
        for (Set<String> setOfConnectedComputers : setsOfConnectedComputers) {
            if (setOfConnectedComputers.size() > resultSet.size()) {
                resultSet = setOfConnectedComputers;
            }
        }
        return setsOfConnectedComputers.stream().max(Comparator.comparingInt(Set::size)).orElseThrow().stream().sorted().collect(Collectors.joining(","));
    }

    private Set<Set<String>> getSetsOfConnectedComputers() {
        Set<Set<String>> setsOfConnectedComputers = new HashSet<>();
        for (String computer : mapOfConnectedComputers.keySet()) {
            Set<String> connectedComputers = mapOfConnectedComputers.get(computer);
            for (String connectedComputer : connectedComputers) {
                Set<String> setOfComputersConnectedToBoth = new HashSet<>(connectedComputers);
                setOfComputersConnectedToBoth.retainAll(mapOfConnectedComputers.get(connectedComputer));

                Set<String> setOfConnectedComputers = new HashSet<>();
                for (String computerConnectedToBoth : setOfComputersConnectedToBoth) {
                    if (mapOfConnectedComputers.get(computerConnectedToBoth).containsAll(setOfConnectedComputers)) {
                        setOfConnectedComputers.add(computerConnectedToBoth);
                    }
                }

                setOfConnectedComputers.add(computer);
                setOfConnectedComputers.add(connectedComputer);
                setsOfConnectedComputers.add(setOfConnectedComputers);
           }
        }
        return setsOfConnectedComputers;
    }

    private void parseInput(List<String> input) {
        for (String line : input) {
            String[] split = line.split(Pattern.quote("-"));

            Set<String> connectedComputers1 = mapOfConnectedComputers.getOrDefault(split[0], new HashSet<>());
            connectedComputers1.add(split[1]);
            mapOfConnectedComputers.put(split[0], connectedComputers1);

            Set<String> connectedComputers2 = mapOfConnectedComputers.getOrDefault(split[1], new HashSet<>());
            connectedComputers2.add(split[0]);
            mapOfConnectedComputers.put(split[1], connectedComputers2);
        }
    }

}
