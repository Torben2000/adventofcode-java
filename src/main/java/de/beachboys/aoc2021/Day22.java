package de.beachboys.aoc2021;

import de.beachboys.Day;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;
import org.jooq.lambda.tuple.Tuple3;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day22 extends Day {

    public Object part1(List<String> input) {
        List<Tuple2<Boolean, Cuboid>> steps = parseInput(input);

        Set<Tuple3<Integer, Integer, Integer>> cubeSet = new HashSet<>();
        for (Tuple2<Boolean, Cuboid> step : steps) {
            Cuboid cuboid = step.v2;
            for (int i = Math.max(cuboid.minX, -50); i <= Math.min(cuboid.maxX, 50); i++) {
                for (int j = Math.max(cuboid.minY, -50); j <= Math.min(cuboid.maxY, 50); j++) {
                    for (int k = Math.max(cuboid.minZ, -50); k <= Math.min(cuboid.maxZ, 50); k++) {
                        if (step.v1) {
                            cubeSet.add(Tuple.tuple(i, j, k));
                        } else {
                            cubeSet.remove(Tuple.tuple(i, j, k));
                        }
                    }
                }
            }
        }

        return cubeSet.size();
    }

    public Object part2(List<String> input) {
        List<Tuple2<Boolean, Cuboid>> steps = parseInput(input);

        Set<Cuboid> cuboidSet = new HashSet<>();
        for (Tuple2<Boolean, Cuboid> step : steps) {
            Cuboid cuboid = step.v2;
            Set<Cuboid> cuboidsToAdd = new HashSet<>();
            Set<Cuboid> cuboidsToRemove = new HashSet<>();
            for (Cuboid existingCuboid : cuboidSet) {
                if (cuboidsIntersect(cuboid, existingCuboid)) {
                    cuboidsToRemove.add(existingCuboid);
                    cuboidsToAdd.addAll(cuboidAMinusCuboidB(existingCuboid, cuboid));
                }
            }
            if (step.v1) {
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

        Tuple2<Integer, Integer> xBefore = Tuple.tuple(cuboidA.minX, Math.min(cuboidB.minX - 1, cuboidA.maxX));
        Tuple2<Integer, Integer> xInner = Tuple.tuple(Math.max(cuboidB.minX, cuboidA.minX), Math.min(cuboidB.maxX, cuboidA.maxX));
        Tuple2<Integer, Integer> xAfter = Tuple.tuple(Math.max(cuboidB.maxX + 1, cuboidA.minX), cuboidA.maxX);

        Tuple2<Integer, Integer> yBefore = Tuple.tuple(cuboidA.minY, Math.min(cuboidB.minY - 1, cuboidA.maxY));
        Tuple2<Integer, Integer> yInner = Tuple.tuple(Math.max(cuboidB.minY, cuboidA.minY), Math.min(cuboidB.maxY, cuboidA.maxY));
        Tuple2<Integer, Integer> yAfter = Tuple.tuple(Math.max(cuboidB.maxY + 1, cuboidA.minY), cuboidA.maxY);

        Tuple2<Integer, Integer> zBefore = Tuple.tuple(cuboidA.minZ, Math.min(cuboidB.minZ - 1, cuboidA.maxZ));
        Tuple2<Integer, Integer> zInner = Tuple.tuple(Math.max(cuboidB.minZ, cuboidA.minZ), Math.min(cuboidB.maxZ, cuboidA.maxZ));
        Tuple2<Integer, Integer> zAfter = Tuple.tuple(Math.max(cuboidB.maxZ + 1, cuboidA.minZ), cuboidA.maxZ);

        List<Tuple2<Integer, Integer>> xPairs = List.of(xBefore, xInner, xAfter);
        List<Tuple2<Integer, Integer>> yPairs = List.of(yBefore, yInner, yAfter);
        List<Tuple2<Integer, Integer>> zPairs = List.of(zBefore, zInner, zAfter);

        Cuboid innerCuboid = new Cuboid(xInner, yInner, zInner);

        for (Tuple2<Integer, Integer> xPair : xPairs) {
            for(Tuple2<Integer, Integer> yPair : yPairs) {
                for (Tuple2<Integer, Integer> zPair : zPairs) {
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

    private List<Tuple2<Boolean, Cuboid>> parseInput(List<String> input) {
        List<Tuple2<Boolean, Cuboid>> steps = new ArrayList<>();
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
                steps.add(Tuple.tuple(on, new Cuboid(minX, maxX, minY, maxY, minZ, maxZ)));
            }
        }
        return steps;
    }

    private static class Cuboid {

        final int minX;
        final int maxX;
        final int minY;
        final int maxY;
        final int minZ;
        final int maxZ;

        public Cuboid(int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {
            this.minX = minX;
            this.maxX = maxX;
            this.minY = minY;
            this.maxY = maxY;
            this.minZ = minZ;
            this.maxZ = maxZ;
        }

        public Cuboid(Tuple2<Integer, Integer> x, Tuple2<Integer, Integer> y, Tuple2<Integer, Integer> z) {
            this.minX = x.v1;
            this.maxX = x.v2;
            this.minY = y.v1;
            this.maxY = y.v2;
            this.minZ = z.v1;
            this.maxZ = z.v2;
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
