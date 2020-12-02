package de.beachboys.aoc2019;

import de.beachboys.Day;
import de.beachboys.Util;

import java.util.ArrayList;
import java.util.List;

public class Day12 extends Day {

    public Object part1(List<String> input) {
        List<Integer> moonsX = new ArrayList<>();
        List<Integer> moonsY = new ArrayList<>();
        List<Integer> moonsZ = new ArrayList<>();
        for (String moonInput : input) {
            String[] coords = moonInput.substring(1, moonInput.length() - 1).split(", ");
            moonsX.add(Integer.valueOf(coords[0].split("=")[1]));
            moonsY.add(Integer.valueOf(coords[1].split("=")[1]));
            moonsZ.add(Integer.valueOf(coords[2].split("=")[1]));
        }

        List<Integer> moonSpeedsX = new ArrayList<>();
        List<Integer> moonSpeedsY = new ArrayList<>();
        List<Integer> moonSpeedsZ = new ArrayList<>();
        for (int i = 0; i < moonsX.size(); i++) {
            moonSpeedsX.add(0);
            moonSpeedsY.add(0);
            moonSpeedsZ.add(0);
        }

        int steps = Integer.parseInt(io.getInput("Number of steps: "));
        for (int i = 0; i < steps; i++) {
            applyGravity(moonsX, moonSpeedsX);
            applyGravity(moonsY, moonSpeedsY);
            applyGravity(moonsZ, moonSpeedsZ);
            applyVelocity(moonsX, moonSpeedsX);
            applyVelocity(moonsY, moonSpeedsY);
            applyVelocity(moonsZ, moonSpeedsZ);
        }
        return calculateEnergy(moonsX, moonsY, moonsZ, moonSpeedsX, moonSpeedsY, moonSpeedsZ);
    }

    private void applyGravity(List<Integer> moons, List<Integer> moonSpeeds) {
        for (int i = 0; i < moons.size(); i++) {
            for (int j = i + 1; j < moons.size(); j++) {
                int correctionValue = getVelocityCorrectionValue(moons.get(i), moons.get(j));
                moonSpeeds.set(i, moonSpeeds.get(i) + correctionValue);
                moonSpeeds.set(j, moonSpeeds.get(j) - correctionValue);
            }
        }
    }

    private int getVelocityCorrectionValue(Integer positionMoon1, Integer positionMoon2) {
        int correctionValue = 0;
        if (positionMoon1 < positionMoon2) {
            correctionValue = 1;
        } else if (positionMoon1 > positionMoon2) {
            correctionValue = -1;
        }
        return correctionValue;
    }

    private void applyVelocity(List<Integer> moons, List<Integer> moonSpeeds) {
        for (int i = 0; i < moons.size(); i++) {
            moons.set(i, moons.get(i) + moonSpeeds.get(i));
        }
    }

    private Object calculateEnergy(List<Integer> moonsX, List<Integer> moonsY, List<Integer> moonsZ, List<Integer> moonSpeedsX, List<Integer> moonSpeedsY, List<Integer> moonSpeedsZ) {
        long sum = 0L;
        for (int i = 0; i < moonsX.size(); i++) {
            sum += (Math.abs(moonsX.get(i)) + Math.abs(moonsY.get(i)) + Math.abs(moonsZ.get(i))) * (Math.abs(moonSpeedsX.get(i)) + Math.abs(moonSpeedsY.get(i)) + Math.abs(moonSpeedsZ.get(i)));
        }
        return sum;
    }

    public Object part2(List<String> input) {
        List<Integer> moonsX = new ArrayList<>();
        List<Integer> moonsY = new ArrayList<>();
        List<Integer> moonsZ = new ArrayList<>();
        for (String moonInput : input) {
            String[] coords = moonInput.substring(1, moonInput.length() - 1).split(", ");
            moonsX.add(Integer.valueOf(coords[0].split("=")[1]));
            moonsY.add(Integer.valueOf(coords[1].split("=")[1]));
            moonsZ.add(Integer.valueOf(coords[2].split("=")[1]));
        }

        List<Integer> moonSpeedsX = new ArrayList<>();
        List<Integer> moonSpeedsY = new ArrayList<>();
        List<Integer> moonSpeedsZ = new ArrayList<>();
        for (int i = 0; i < moonsX.size(); i++) {
            moonSpeedsX.add(0);
            moonSpeedsY.add(0);
            moonSpeedsZ.add(0);
        }

        long lcm1 = Util.leastCommonMultiple(calculateNumberOfStepsBeforeRepeating(moonsX, moonSpeedsX), calculateNumberOfStepsBeforeRepeating(moonsY, moonSpeedsY));
        return Util.leastCommonMultiple(lcm1, calculateNumberOfStepsBeforeRepeating(moonsZ, moonSpeedsZ));
    }

    private long calculateNumberOfStepsBeforeRepeating(List<Integer> moons, List<Integer> moonSpeeds) {
        long counter = 0L;
        List<Integer> moonsCopy = new ArrayList<>(moons);
        List<Integer> moonSpeedsCopy = new ArrayList<>(moonSpeeds);
        do {
            applyGravity(moons, moonSpeeds);
            applyVelocity(moons, moonSpeeds);
            counter++;
        } while (!moonsCopy.equals(moons) || !moonSpeedsCopy.equals(moonSpeeds));
        return counter;
    }

}
