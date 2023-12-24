package de.beachboys.aoc2016;

import de.beachboys.Day;

import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

public class Day19 extends Day {

    private static class Elf {

        public final int number;
        public Elf leftElfWithPresent;

        public Elf(int number) {
            this.number = number;
        }

        @Override
        public String toString() {
            return number + "";
        }
    }

    private int numberOfElves;
    private Elf firstElf;
    private Elf neighborOfMiddleElf;

    public Object part1(List<String> input) {
        return runLogic(input, () -> true, () -> firstElf);
    }

    public Object part2(List<String> input) {
        return runLogic(input, () -> numberOfElves % 2 == 1, () -> neighborOfMiddleElf);
    }

    private Object runLogic(List<String> input, BooleanSupplier continueWithNextElf, Supplier<Elf> getNeighborOfInitialElfToRemove) {
        buildElfList(input);
        Elf currentELf = getNeighborOfInitialElfToRemove.get();
        while (numberOfElves > 1) {
            currentELf.leftElfWithPresent = currentELf.leftElfWithPresent.leftElfWithPresent;
            if (continueWithNextElf.getAsBoolean()) {
                currentELf = currentELf.leftElfWithPresent;
            }
            numberOfElves--;
        }
        return currentELf.number;
    }

    private void buildElfList(List<String> input) {
        numberOfElves = Integer.parseInt(input.getFirst());
        Elf previousElf = new Elf(-1);
        for (int i = 1; i <= numberOfElves; i++) {
            Elf elf = new Elf(i);
            if (firstElf == null) {
                firstElf = elf;
            } else {
                previousElf.leftElfWithPresent = elf;
            }
            if (neighborOfMiddleElf == null && i == numberOfElves / 2) {
                neighborOfMiddleElf = elf;
            }
            previousElf = elf;
        }
        previousElf.leftElfWithPresent = firstElf;
    }

}
