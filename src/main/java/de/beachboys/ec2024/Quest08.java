package de.beachboys.ec2024;

import de.beachboys.Quest;
import de.beachboys.Util;

import java.util.LinkedList;
import java.util.List;

public class Quest08 extends Quest {

    @Override
    public Object part1(List<String> input) {
        int availableBlocks = Integer.parseInt(input.getFirst());
        int usedBlocks = 0;
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            int width = 2 * i + 1;
            usedBlocks += width;
            if (usedBlocks >= availableBlocks) {
                return (usedBlocks - availableBlocks) * width;
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Object part2(List<String> input) {
        int priests = Integer.parseInt(input.getFirst());
        int priestAcolytes = Util.getIntValueFromUser("Priest acolytes", 1111, io);
        int availableBlocks = Util.getIntValueFromUser("Available blocks", 20240000, io);

        int usedBlocks = 0;
        int thickness = 1;
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            int width = 2 * i + 1;
            usedBlocks += thickness * width;
            if (usedBlocks >= availableBlocks) {
                return (usedBlocks - availableBlocks) * width;
            }
            thickness = (thickness * priests) % priestAcolytes;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Object part3(List<String> input) {
        int priests = Integer.parseInt(input.getFirst());
        int priestAcolytes = Util.getIntValueFromUser("Priest acolytes", 10, io);
        int availableBlocks = Util.getIntValueFromUser("Available blocks", 202400000, io);
        int priestsModAcolytes = priests % priestAcolytes;
        int usedBlocksWhenFilled = 0;
        int thickness = 1;
        List<Integer> thicknessesFromLeftToMiddle = new LinkedList<>();
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            thicknessesFromLeftToMiddle.addFirst(thickness);
            int width = 2 * i + 1;
            usedBlocksWhenFilled += thickness * width;
            if (usedBlocksWhenFilled >= availableBlocks) {
                int usedBlocks = usedBlocksWhenFilled;
                int sumOfPreviouesThicknesses = thicknessesFromLeftToMiddle.getFirst();
                for (int j = 1; j < thicknessesFromLeftToMiddle.size(); j++) {
                    int currentThickness = thicknessesFromLeftToMiddle.get(j);
                    int blocksToRemove = (priestsModAcolytes * width * (currentThickness + sumOfPreviouesThicknesses)) % priestAcolytes;
                    if (j == thicknessesFromLeftToMiddle.size() - 1) {
                        usedBlocks -= blocksToRemove;
                    } else {
                        usedBlocks -= 2 * blocksToRemove;
                    }
                    sumOfPreviouesThicknesses += currentThickness;
                }
                if (usedBlocks > availableBlocks) {
                    return usedBlocks - availableBlocks;
                }
            }
            thickness = (thickness * priestsModAcolytes) % priestAcolytes + priestAcolytes;
        }
        throw new IllegalArgumentException();
    }
}
