package de.beachboys.aoc2020;

import de.beachboys.Day;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Day10 extends Day {

    public Object part1(List<String> input) {
        TreeSet<Long> set = input.stream().map(Long::valueOf).collect(Collectors.toCollection(TreeSet::new));
        long numberOfJumpsByOne = 0L;
        long numberOfJumpsByThree = 0L;
        long deviceJolt = set.last() + 3;
        set.add(deviceJolt);

        long currentJoltValue = 0L;
        while (!set.isEmpty()) {
            @SuppressWarnings("ConstantConditions") long adapterJolt = set.pollFirst();
            if (adapterJolt - 1 == currentJoltValue) {
                numberOfJumpsByOne++;
            } else if (adapterJolt - 3 == currentJoltValue) {
                numberOfJumpsByThree++;
            }
            currentJoltValue = adapterJolt;
        }

        return numberOfJumpsByOne * numberOfJumpsByThree;
    }

    public Object part2(List<String> input) {
        TreeSet<Long> set = input.stream().map(Long::valueOf).collect(Collectors.toCollection(TreeSet::new));
        set.add(0L);

        List<List<Long>> blocks = buildAdapterBlocks(set);

        long number = 1L;
        for (List<Long> block : blocks) {
            long counter = 1L;
            int size = block.size();
            for (int startPosition = 0; startPosition < size - 1; startPosition++) {
                for (int stepSize = 2; stepSize < size; stepSize++) {
                    counter += getPossibilitiesForStartPositionAndStepSize(block, startPosition, stepSize);
                }
            }
            number *=counter;
        }
        return number;
    }

    private long getPossibilitiesForStartPositionAndStepSize(List<Long> block, int startPosition, int stepSize) {
        long possibleSteps = 0L;
        int currentPosition = startPosition;
        while (currentPosition + stepSize < block.size()) {
            if (block.get(currentPosition + stepSize) - block.get(currentPosition) <= 3) {
                possibleSteps++;
            }
            currentPosition = currentPosition + stepSize;
        }
        return (long) Math.pow(2, possibleSteps - 1);
    }

    private List<List<Long>> buildAdapterBlocks(TreeSet<Long> set) {
        List<List<Long>> blocks = new ArrayList<>();
        List<Long> block = new ArrayList<>();
        long currentJoltValue = 0L;
        while (!set.isEmpty()) {
            @SuppressWarnings("ConstantConditions") long adapterJolt = set.pollFirst();
            if (adapterJolt - 3 == currentJoltValue) {
                blocks.add(block);
                block = new ArrayList<>();
            }
            block.add(adapterJolt);
            currentJoltValue = adapterJolt;
        }
        blocks.add(block);
        return blocks;
    }

}
