package de.beachboys.aoc2022;

import de.beachboys.Day;
import org.javatuples.Triplet;

import java.util.*;

public class Day18 extends Day {

    private int minX;
    private int maxX;
    private int minY;
    private int maxY;
    private int minZ;
    private int maxZ;
    private Set<Triplet<Integer, Integer, Integer>> dropletCubes;

    public Object part1(List<String> input) {
        parseDropletCubesAndFillMinMax(input);
        return getNumberOfSurfaces(dropletCubes);
    }

    public Object part2(List<String> input) {
        parseDropletCubesAndFillMinMax(input);
        Set<Triplet<Integer, Integer, Integer>> innerAirCubes = getInnerAirCubes();

        return getNumberOfSurfaces(dropletCubes) - getNumberOfSurfaces(innerAirCubes);

    }

    private int getNumberOfSurfaces(Set<Triplet<Integer, Integer, Integer>> cubes) {
        int result = 0;
        for (Triplet<Integer, Integer, Integer> cube : cubes) {
            result += 6 - getNumberOfNeighbors(cube, cubes);
        }
        return result;
    }

    private int getNumberOfNeighbors(Triplet<Integer, Integer, Integer> cube, Set<Triplet<Integer, Integer, Integer>> possibleNeighbors) {
        int neighbors = 0;
        if (possibleNeighbors.contains(Triplet.with(cube.getValue0() - 1, cube.getValue1(), cube.getValue2()))) {
            neighbors++;
        }
        if (possibleNeighbors.contains(Triplet.with(cube.getValue0() + 1, cube.getValue1(), cube.getValue2()))) {
            neighbors++;
        }
        if (possibleNeighbors.contains(Triplet.with(cube.getValue0(), cube.getValue1() - 1, cube.getValue2()))) {
            neighbors++;
        }
        if (possibleNeighbors.contains(Triplet.with(cube.getValue0(), cube.getValue1() + 1, cube.getValue2()))) {
            neighbors++;
        }
        if (possibleNeighbors.contains(Triplet.with(cube.getValue0(), cube.getValue1(), cube.getValue2() - 1))) {
            neighbors++;
        }
        if (possibleNeighbors.contains(Triplet.with(cube.getValue0(), cube.getValue1(), cube.getValue2() + 1))) {
            neighbors++;
        }
        return neighbors;
    }

    private Set<Triplet<Integer, Integer, Integer>> getInnerAirCubes() {
        Set<Triplet<Integer, Integer, Integer>> allAirCubes = new HashSet<>();
        for (int i = minX - 1; i <= maxX + 1; i++) {
            for (int j = minY - 1; j <= maxY + 1; j++) {
                for (int k = minZ - 1; k <= maxZ + 1; k++) {
                    Triplet<Integer, Integer, Integer> air = Triplet.with(i, j, k);
                    if (!dropletCubes.contains(air)) {
                        allAirCubes.add(air);
                    }
                }
            }
        }

        Set<Triplet<Integer, Integer, Integer>> outsideAirCubes = new HashSet<>();
        outsideAirCubes.add(Triplet.with(minX - 1, minY - 1, minZ - 1));
        boolean change = true;
        while (change) {
            change = false;
            for (Triplet<Integer, Integer, Integer> airCube : allAirCubes) {
                if (!outsideAirCubes.contains(airCube) && getNumberOfNeighbors(airCube, outsideAirCubes) > 0) {
                    outsideAirCubes.add(airCube);
                    change = true;
                }
            }
        }

        Set<Triplet<Integer, Integer, Integer>> innerAirCubes = new HashSet<>(allAirCubes);
        innerAirCubes.removeAll(outsideAirCubes);
        return innerAirCubes;
    }

    private void parseDropletCubesAndFillMinMax(List<String> input) {
        dropletCubes = new HashSet<>();
        for (String line : input) {
            String[] split = line.split(",");
            dropletCubes.add(Triplet.with(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2])));
        }

        minX = dropletCubes.stream().map(Triplet::getValue0).min(Integer::compareTo).orElseThrow();
        maxX = dropletCubes.stream().map(Triplet::getValue0).max(Integer::compareTo).orElseThrow();
        minY = dropletCubes.stream().map(Triplet::getValue1).min(Integer::compareTo).orElseThrow();
        maxY = dropletCubes.stream().map(Triplet::getValue1).max(Integer::compareTo).orElseThrow();
        minZ = dropletCubes.stream().map(Triplet::getValue2).min(Integer::compareTo).orElseThrow();
        maxZ = dropletCubes.stream().map(Triplet::getValue2).max(Integer::compareTo).orElseThrow();
    }

}
