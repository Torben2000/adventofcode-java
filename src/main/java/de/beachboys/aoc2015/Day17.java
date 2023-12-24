package de.beachboys.aoc2015;

import de.beachboys.Day;
import de.beachboys.Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day17 extends Day {

    private final Map<Integer, Integer> countPerNumberOfContainers = new HashMap<>();

    public Object part1(List<String> input) {
        fillCountPerNumberOfContainersMap(input);
        return countPerNumberOfContainers.values().stream().mapToInt(Integer::intValue).sum();
    }

    public Object part2(List<String> input) {
        fillCountPerNumberOfContainersMap(input);
        return countPerNumberOfContainers.get(countPerNumberOfContainers.keySet().stream().mapToInt(Integer::intValue).min().orElseThrow());
    }

    private void fillCountPerNumberOfContainersMap(List<String> input) {
        int totalEggnog = Util.getIntValueFromUser("Total eggnog", 150, io);
        List<Integer> containers = input.stream().map(Integer::valueOf).collect(Collectors.toList());
        countPossibilitiesPerNumberOfContainers(totalEggnog, containers, 0);
    }

    private void countPossibilitiesPerNumberOfContainers(int totalEggnog, List<Integer> containers, int currentContainerCount) {
        if (containers.isEmpty()) {
            return;
        }
        Integer currentContainer = containers.getFirst();
        List<Integer> containersWithoutCurrentContainer = containers.subList(1, containers.size());

        countPossibilitiesPerNumberOfContainers(totalEggnog, containersWithoutCurrentContainer, currentContainerCount);

        int newContainerCount = currentContainerCount + 1;
        if (totalEggnog == currentContainer) {
            countPerNumberOfContainers.put(newContainerCount, countPerNumberOfContainers.getOrDefault(newContainerCount, 0) + 1);
        } else if (totalEggnog > currentContainer){
            countPossibilitiesPerNumberOfContainers(totalEggnog - currentContainer, containersWithoutCurrentContainer, newContainerCount);
        }
    }

}
