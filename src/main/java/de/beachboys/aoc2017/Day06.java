package de.beachboys.aoc2017;

import de.beachboys.Day;
import de.beachboys.Util;

import java.util.*;
import java.util.stream.Collectors;

public class Day06 extends Day {

    private int steps = 0;

    private List<Integer> memory;

    private final Map<String, Integer> seenMemoryStates = new HashMap<>();

    public Object part1(List<String> input) {
        runLogic(input);
        return steps;
    }

    public Object part2(List<String> input) {
        runLogic(input);
        return steps - seenMemoryStates.get(getMemoryRepresentation());
    }

    private void runLogic(List<String> input) {
        memory = Util.parseToIntList(input.get(0), "\t");
        while (!seenMemoryStates.containsKey(getMemoryRepresentation())) {
            seenMemoryStates.put(getMemoryRepresentation(), steps);
            steps++;

            int maxBlocks = memory.stream().mapToInt(Integer::intValue).max().orElseThrow();
            int firstIndexWithMaxBlocks = memory.indexOf(maxBlocks);

            redistributeBlocks(maxBlocks, firstIndexWithMaxBlocks);
        }
    }

    private void redistributeBlocks(int blocksToRedistribute, int blockSourceIndex) {
        memory.set(blockSourceIndex, 0);
        int blocksToAddPerMemoryBank = blocksToRedistribute / memory.size();
        int indexOffsetToAddOneMoreBlock = blocksToRedistribute % memory.size();
        for (int i = 0; i < memory.size(); i++) {
            int blocksToAddForThisMemoryBank = blocksToAddPerMemoryBank;
            if (isIndexToAddMore(i, blockSourceIndex, indexOffsetToAddOneMoreBlock)) {
                blocksToAddForThisMemoryBank++;
            }
            memory.set(i, memory.get(i) + blocksToAddForThisMemoryBank);
        }
    }

    private boolean isIndexToAddMore(int index, int blockSourceIndex, int indexOffsetToAddOneMoreBlock) {
        return index > blockSourceIndex && index <= blockSourceIndex + indexOffsetToAddOneMoreBlock
                || index < blockSourceIndex && index <= (blockSourceIndex + indexOffsetToAddOneMoreBlock - memory.size());
    }

    private String getMemoryRepresentation() {
        return memory.stream().map(String::valueOf).collect(Collectors.joining(","));
    }

}
