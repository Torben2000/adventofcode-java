package de.beachboys.aoc2019;

import de.beachboys.Day;
import org.javatuples.Triplet;

import java.util.ArrayList;
import java.util.List;

public class Day12 extends Day {

    public Object part1(List<String> input) {
        List<Triplet<Integer, Integer, Integer>> moons = new ArrayList<>();
        for (String moonInput : input) {
            String[] coords = moonInput.substring(1, moonInput.length() - 1).split(", ");
            moons.add(Triplet.with(Integer.valueOf(coords[0].split("=")[1]), Integer.valueOf(coords[1].split("=")[1]), Integer.valueOf(coords[2].split("=")[1])));
        }

        List<Triplet<Integer, Integer, Integer>> moonSpeeds = new ArrayList<>();
        moonSpeeds.add(Triplet.with(0, 0, 0));
        moonSpeeds.add(Triplet.with(0, 0, 0));
        moonSpeeds.add(Triplet.with(0, 0, 0));
        moonSpeeds.add(Triplet.with(0, 0, 0));

        int steps = Integer.parseInt(io.getInput("Number of steps: "));
        for (int i = 0; i < steps; i++) {
            applyGravity(moons, moonSpeeds);
            applyVelocity(moons, moonSpeeds);
            io.logDebug("Step " + i);
            io.logDebug("Moons: " + moons);
            io.logDebug("Moon speeds: " + moonSpeeds);
        }
        return calculateEnergy(moons, moonSpeeds);
    }

    private void applyGravity(List<Triplet<Integer, Integer, Integer>> moons, List<Triplet<Integer, Integer, Integer>> moonSpeeds) {
        for (int i = 0; i < moons.size(); i++) {
            for (int j = i + 1; j < moons.size(); j++) {
                int correctionValueX = getVelocityCorrectionValue(moons.get(i).getValue0(), moons.get(j).getValue0());
                moonSpeeds.set(i, moonSpeeds.get(i).setAt0(moonSpeeds.get(i).getValue0() + correctionValueX));
                moonSpeeds.set(j, moonSpeeds.get(j).setAt0(moonSpeeds.get(j).getValue0() - correctionValueX));
                int correctionValueY = getVelocityCorrectionValue(moons.get(i).getValue1(), moons.get(j).getValue1());
                moonSpeeds.set(i, moonSpeeds.get(i).setAt1(moonSpeeds.get(i).getValue1() + correctionValueY));
                moonSpeeds.set(j, moonSpeeds.get(j).setAt1(moonSpeeds.get(j).getValue1() - correctionValueY));
                int correctionValueZ = getVelocityCorrectionValue(moons.get(i).getValue2(), moons.get(j).getValue2());
                moonSpeeds.set(i, moonSpeeds.get(i).setAt2(moonSpeeds.get(i).getValue2() + correctionValueZ));
                moonSpeeds.set(j, moonSpeeds.get(j).setAt2(moonSpeeds.get(j).getValue2() - correctionValueZ));
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

    private void applyVelocity(List<Triplet<Integer, Integer, Integer>> moons, List<Triplet<Integer, Integer, Integer>> moonSpeeds) {
        for (int i = 0; i < moons.size(); i++) {
            int x = moons.get(i).getValue0() + moonSpeeds.get(i).getValue0();
            int y = moons.get(i).getValue1() + moonSpeeds.get(i).getValue1();
            int z = moons.get(i).getValue2() + moonSpeeds.get(i).getValue2();
            moons.set(i, Triplet.with(x, y, z));
        }
    }

    private Object calculateEnergy(List<Triplet<Integer, Integer, Integer>> moons, List<Triplet<Integer, Integer, Integer>> moonSpeeds) {
        long sum = 0L;
        for (int i = 0; i < moons.size(); i++) {
            Triplet<Integer, Integer, Integer> moon = moons.get(i);
            Triplet<Integer, Integer, Integer> moonSpeed = moonSpeeds.get(i);
            sum += (Math.abs(moon.getValue0()) + Math.abs(moon.getValue1()) + Math.abs(moon.getValue2())) * (Math.abs(moonSpeed.getValue0()) + Math.abs(moonSpeed.getValue1()) + Math.abs(moonSpeed.getValue2()));
        }
        return sum;
    }

    public Object part2(List<String> input) {
        return 2;
    }

}
