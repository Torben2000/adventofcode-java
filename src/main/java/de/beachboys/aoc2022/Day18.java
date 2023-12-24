package de.beachboys.aoc2022;

import de.beachboys.Day;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple3;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day18 extends Day {

    private int minX;
    private int maxX;
    private int minY;
    private int maxY;
    private int minZ;
    private int maxZ;
    private Set<Tuple3<Integer, Integer, Integer>> dropletCubes;

    public Object part1(List<String> input) {
        parseDropletCubesAndFillMinMax(input);
        return getNumberOfSurfaces(dropletCubes);
    }

    public Object part2(List<String> input) {
        parseDropletCubesAndFillMinMax(input);
        Set<Tuple3<Integer, Integer, Integer>> innerAirCubes = getInnerAirCubes();

        return getNumberOfSurfaces(dropletCubes) - getNumberOfSurfaces(innerAirCubes);

    }

    private int getNumberOfSurfaces(Set<Tuple3<Integer, Integer, Integer>> cubes) {
        int result = 0;
        for (Tuple3<Integer, Integer, Integer> cube : cubes) {
            result += 6 - getNumberOfNeighbors(cube, cubes);
        }
        return result;
    }

    private int getNumberOfNeighbors(Tuple3<Integer, Integer, Integer> cube, Set<Tuple3<Integer, Integer, Integer>> possibleNeighbors) {
        int neighbors = 0;
        if (possibleNeighbors.contains(Tuple.tuple(cube.v1 - 1, cube.v2, cube.v3))) {
            neighbors++;
        }
        if (possibleNeighbors.contains(Tuple.tuple(cube.v1 + 1, cube.v2, cube.v3))) {
            neighbors++;
        }
        if (possibleNeighbors.contains(Tuple.tuple(cube.v1, cube.v2 - 1, cube.v3))) {
            neighbors++;
        }
        if (possibleNeighbors.contains(Tuple.tuple(cube.v1, cube.v2 + 1, cube.v3))) {
            neighbors++;
        }
        if (possibleNeighbors.contains(Tuple.tuple(cube.v1, cube.v2, cube.v3 - 1))) {
            neighbors++;
        }
        if (possibleNeighbors.contains(Tuple.tuple(cube.v1, cube.v2, cube.v3 + 1))) {
            neighbors++;
        }
        return neighbors;
    }

    private Set<Tuple3<Integer, Integer, Integer>> getInnerAirCubes() {
        Set<Tuple3<Integer, Integer, Integer>> allAirCubes = new HashSet<>();
        for (int i = minX - 1; i <= maxX + 1; i++) {
            for (int j = minY - 1; j <= maxY + 1; j++) {
                for (int k = minZ - 1; k <= maxZ + 1; k++) {
                    Tuple3<Integer, Integer, Integer> air = Tuple.tuple(i, j, k);
                    if (!dropletCubes.contains(air)) {
                        allAirCubes.add(air);
                    }
                }
            }
        }

        Set<Tuple3<Integer, Integer, Integer>> outsideAirCubes = new HashSet<>();
        outsideAirCubes.add(Tuple.tuple(minX - 1, minY - 1, minZ - 1));
        boolean change = true;
        while (change) {
            change = false;
            for (Tuple3<Integer, Integer, Integer> airCube : allAirCubes) {
                if (!outsideAirCubes.contains(airCube) && getNumberOfNeighbors(airCube, outsideAirCubes) > 0) {
                    outsideAirCubes.add(airCube);
                    change = true;
                }
            }
        }

        Set<Tuple3<Integer, Integer, Integer>> innerAirCubes = new HashSet<>(allAirCubes);
        innerAirCubes.removeAll(outsideAirCubes);
        return innerAirCubes;
    }

    private void parseDropletCubesAndFillMinMax(List<String> input) {
        dropletCubes = new HashSet<>();
        for (String line : input) {
            String[] split = line.split(",");
            dropletCubes.add(Tuple.tuple(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2])));
        }

        minX = dropletCubes.stream().map(Tuple3::v1).min(Integer::compareTo).orElseThrow();
        maxX = dropletCubes.stream().map(Tuple3::v1).max(Integer::compareTo).orElseThrow();
        minY = dropletCubes.stream().map(Tuple3::v2).min(Integer::compareTo).orElseThrow();
        maxY = dropletCubes.stream().map(Tuple3::v2).max(Integer::compareTo).orElseThrow();
        minZ = dropletCubes.stream().map(Tuple3::v3).min(Integer::compareTo).orElseThrow();
        maxZ = dropletCubes.stream().map(Tuple3::v3).max(Integer::compareTo).orElseThrow();
    }

}
