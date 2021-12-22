package de.beachboys.aoc2021;

import de.beachboys.Day;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day22 extends Day {

    public Object part1(List<String> input) {
        List<Pair<Boolean, Cuboid>> steps = parseInput(input);

        Set<Triplet<Integer, Integer, Integer>> cubeSet = new HashSet<>();
        for (Pair<Boolean, Cuboid> step : steps) {
            Cuboid cuboid = step.getValue1();
            for (int i = Math.max(cuboid.minX, -50); i <= Math.min(cuboid.maxX, 50); i++) {
                for (int j = Math.max(cuboid.minY, -50); j <= Math.min(cuboid.maxY, 50); j++) {
                    for (int k = Math.max(cuboid.minZ, -50); k <= Math.min(cuboid.maxZ, 50); k++) {
                        if (step.getValue0()) {
                            cubeSet.add(Triplet.with(i, j, k));
                        } else {
                            cubeSet.remove(Triplet.with(i, j, k));
                        }
                    }
                }
            }
        }

        return cubeSet.size();
    }

    public Object part2(List<String> input) {
        List<Pair<Boolean, Cuboid>> steps = parseInput(input);

        Set<Cuboid> cuboidSet = new HashSet<>();
        for (Pair<Boolean, Cuboid> step : steps) {
            Cuboid cuboid = step.getValue1();
            Set<Cuboid> cuboidsToAdd = new HashSet<>();
            Set<Cuboid> cuboidsToRemove = new HashSet<>();
            for (Cuboid existingCuboid : cuboidSet) {
                if (cuboidsIntersect(cuboid, existingCuboid)) {
                    cuboidsToRemove.add(existingCuboid);
                    cuboidsToAdd.addAll(cuboidAMinusCuboidB(existingCuboid, cuboid));
                }
            }
            if (step.getValue0()) {
                cuboidsToAdd.add(cuboid);
            }
            cuboidSet.removeAll(cuboidsToRemove);
            cuboidSet.addAll(cuboidsToAdd);
        }
        long cubeCount = 0L;
        for (Cuboid cuboid : cuboidSet) {
            cubeCount += (long) (cuboid.maxX - cuboid.minX + 1) * (cuboid.maxY - cuboid.minY + 1) * (cuboid.maxZ - cuboid.minZ + 1);
        }
        return cubeCount;
    }

    private boolean cuboidsIntersect(Cuboid cuboidA, Cuboid cuboidB) {
        return cuboidA.minX <= cuboidB.maxX && cuboidA.maxX >= cuboidB.minX
                && cuboidA.minY <= cuboidB.maxY && cuboidA.maxY >= cuboidB.minY
                && cuboidA.minZ <= cuboidB.maxZ && cuboidA.maxZ >= cuboidB.minZ;
    }

    private Set<Cuboid> cuboidAMinusCuboidB(Cuboid cuboidA, Cuboid cuboidB) {
        Set<Cuboid> cuboids = new HashSet<>();

        Pair<Integer, Integer> xBefore = Pair.with(cuboidA.minX, Math.min(cuboidB.minX - 1, cuboidA.maxX));
        Pair<Integer, Integer> xInner = Pair.with(Math.max(cuboidB.minX, cuboidA.minX), Math.min(cuboidB.maxX, cuboidA.maxX));
        Pair<Integer, Integer> xAfter = Pair.with(Math.max(cuboidB.maxX + 1, cuboidA.minX), cuboidA.maxX);

        Pair<Integer, Integer> yBefore = Pair.with(cuboidA.minY, Math.min(cuboidB.minY - 1, cuboidA.maxY));
        Pair<Integer, Integer> yInner = Pair.with(Math.max(cuboidB.minY, cuboidA.minY), Math.min(cuboidB.maxY, cuboidA.maxY));
        Pair<Integer, Integer> yAfter = Pair.with(Math.max(cuboidB.maxY + 1, cuboidA.minY), cuboidA.maxY);

        Pair<Integer, Integer> zBefore = Pair.with(cuboidA.minZ, Math.min(cuboidB.minZ - 1, cuboidA.maxZ));
        Pair<Integer, Integer> zInner = Pair.with(Math.max(cuboidB.minZ, cuboidA.minZ), Math.min(cuboidB.maxZ, cuboidA.maxZ));
        Pair<Integer, Integer> zAfter = Pair.with(Math.max(cuboidB.maxZ + 1, cuboidA.minZ), cuboidA.maxZ);

        List<Pair<Integer, Integer>> xPairs = List.of(xBefore, xInner, xAfter);
        List<Pair<Integer, Integer>> yPairs = List.of(yBefore, yInner, yAfter);
        List<Pair<Integer, Integer>> zPairs = List.of(zBefore, zInner, zAfter);

        Cuboid innerCuboid = new Cuboid(xInner, yInner, zInner);

        for (Pair<Integer, Integer> xPair : xPairs) {
            for(Pair<Integer, Integer> yPair : yPairs) {
                for (Pair<Integer, Integer> zPair : zPairs) {
                    cuboids.add(new Cuboid(xPair, yPair, zPair));
                }
            }
        }

        return cuboids.stream().filter(newCuboid -> isValidCuboid(newCuboid) && cuboidAWithinCuboidB(newCuboid, cuboidA) && !innerCuboid.equals(newCuboid)).collect(Collectors.toSet());
    }

    private boolean isValidCuboid(Cuboid cuboid) {
        return cuboid.minX <= cuboid.maxX && cuboid.minY <= cuboid.maxY && cuboid.minZ <= cuboid.maxZ;
    }

    private boolean cuboidAWithinCuboidB(Cuboid cuboidA, Cuboid cuboidB) {
        return cuboidA.minX >= cuboidB.minX && cuboidA.maxX <= cuboidB.maxX
                && cuboidA.minY >= cuboidB.minY && cuboidA.maxY <= cuboidB.maxY
                && cuboidA.minZ >= cuboidB.minZ && cuboidA.maxZ <= cuboidB.maxZ;
    }

    private List<Pair<Boolean, Cuboid>> parseInput(List<String> input) {
        List<Pair<Boolean, Cuboid>> steps = new ArrayList<>();
        Pattern linePattern = Pattern.compile("(on|off) x=([-0-9]+)..([-0-9]+),y=([-0-9]+)..([-0-9]+),z=([-0-9]+)..([-0-9]+)");
        for (String line : input) {
            Matcher m = linePattern.matcher(line);
            if (m.matches()) {
                boolean on = "on".equals(m.group(1));
                int minX = Integer.parseInt(m.group(2));
                int maxX = Integer.parseInt(m.group(3));
                int minY = Integer.parseInt(m.group(4));
                int maxY = Integer.parseInt(m.group(5));
                int minZ = Integer.parseInt(m.group(6));
                int maxZ = Integer.parseInt(m.group(7));
                steps.add(Pair.with(on, new Cuboid(minX, maxX, minY, maxY, minZ, maxZ)));
            }
        }
        return steps;
    }

    private static class Cuboid {

        int minX;
        int maxX;
        int minY;
        int maxY;
        int minZ;
        int maxZ;

        public Cuboid(int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {
            this.minX = minX;
            this.maxX = maxX;
            this.minY = minY;
            this.maxY = maxY;
            this.minZ = minZ;
            this.maxZ = maxZ;
        }

        public Cuboid(Pair<Integer, Integer> x, Pair<Integer, Integer> y, Pair<Integer, Integer> z) {
            this.minX = x.getValue0();
            this.maxX = x.getValue1();
            this.minY = y.getValue0();
            this.maxY = y.getValue1();
            this.minZ = z.getValue0();
            this.maxZ = z.getValue1();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Cuboid cuboid = (Cuboid) o;
            return minX == cuboid.minX && maxX == cuboid.maxX && minY == cuboid.minY && maxY == cuboid.maxY && minZ == cuboid.minZ && maxZ == cuboid.maxZ;
        }

        @Override
        public int hashCode() {
            return Objects.hash(minX, maxX, minY, maxY, minZ, maxZ);
        }
    }


}
