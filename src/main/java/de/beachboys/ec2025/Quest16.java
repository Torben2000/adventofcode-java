package de.beachboys.ec2025;

import de.beachboys.Quest;
import de.beachboys.Util;

import java.util.ArrayList;
import java.util.List;

public class Quest16 extends Quest {

    @Override
    public Object part1(List<String> input) {
        return getNumberOfBlocks(Util.parseIntCsv(input.getFirst()), 90);
    }

    @Override
    public Object part2(List<String> input) {
        List<Integer> spell = identifySpell(Util.parseIntCsv(input.getFirst()));
        long result = 1;
        for (Integer spellEntry : spell) {
            result *= spellEntry;
        }
        return result;
    }

    @Override
    public Object part3(List<String> input) {
        List<Integer> spell = identifySpell(Util.parseIntCsv(input.getFirst()));
        long availableBlocks = 202520252025000L;
        long upperBoundColumns = availableBlocks;
        long lowerBoundColumns = 0L;

        while (true) {
            long columnNumberToCheck = (upperBoundColumns + lowerBoundColumns) / 2;
            if (columnNumberToCheck == lowerBoundColumns) {
                return columnNumberToCheck;
            }
            long blocks = getNumberOfBlocks(spell, columnNumberToCheck);
            if (blocks > availableBlocks) {
                upperBoundColumns = columnNumberToCheck;
            } else if (blocks < availableBlocks) {
                lowerBoundColumns = columnNumberToCheck;
            } else {
                return columnNumberToCheck;
            }
        }
    }

    private static List<Integer> identifySpell(List<Integer> blocksPerColumn) {
        List<Integer> remainingBlocksPerColumn = new ArrayList<>(blocksPerColumn);
        List<Integer> spell = new ArrayList<>();
        for (int i = 1; i <= remainingBlocksPerColumn.size(); i++) {
            if (remainingBlocksPerColumn.get(i - 1) > 0) {
                spell.add(i);
                for (int j = i; j <= remainingBlocksPerColumn.size(); j += i) {
                    remainingBlocksPerColumn.set(j - 1, remainingBlocksPerColumn.get(j - 1) - 1);
                }
            }
        }
        return spell;
    }

    private static long getNumberOfBlocks(List<Integer> spell, long columns) {
        long result = 0;
        for (Integer spellEntry : spell) {
            result += columns / spellEntry;
        }
        return result;
    }
}
