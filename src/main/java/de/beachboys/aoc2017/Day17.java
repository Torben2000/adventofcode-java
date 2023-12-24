package de.beachboys.aoc2017;

import de.beachboys.Day;

import java.util.List;

public class Day17 extends Day {

    private static class Element {
        Element next = this;
        final int value;

        Element(int value) {
            this.value = value;
        }
    }


    public Object part1(List<String> input) {
        int stepSize = Integer.parseInt(input.getFirst());
        Element currentElement = new Element(0);
        for (int i = 1; i <= 2017; i++) {
            int neededSteps = stepSize % i;
            for (int j = 0; j < neededSteps; j++) {
                currentElement = currentElement.next;
            }
            Element newElement = new Element(i);
            newElement.next = currentElement.next;
            currentElement.next = newElement;
            currentElement = newElement;
        }
        return currentElement.next.value;
    }

    public Object part2(List<String> input) {
        int stepSize = Integer.parseInt(input.getFirst());
        int currentReturnValue = 0;
        int currentIndex = 0;
        for (int i = 1; i <= 50000000; i++) {
            currentIndex = ((currentIndex + stepSize) % i) + 1;
            if (currentIndex == 1) {
                currentReturnValue = i;
            }
        }
        return currentReturnValue;
    }

}
