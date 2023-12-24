package de.beachboys.aoc2023;

import de.beachboys.Day;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple3;

import java.util.*;
import java.util.stream.Collectors;

public class Day22 extends Day {

    private Set<Set<Tuple3<Integer, Integer, Integer>>> bricks;
    private Map<Set<Tuple3<Integer, Integer, Integer>>, Set<Set<Tuple3<Integer, Integer, Integer>>>> lowerToUpperBricks;
    private Map<Set<Tuple3<Integer, Integer, Integer>>, Set<Set<Tuple3<Integer, Integer, Integer>>>> upperToLowerBricks;

    public Object part1(List<String> input) {
        parseInputMoveBricksAndStoreBrickRelation(input);

        long result = 0;
        for (Set<Tuple3<Integer, Integer, Integer>> brick : bricks) {
            boolean safeToRemove = true;
            for (Set<Tuple3<Integer, Integer, Integer>> upperBrick : lowerToUpperBricks.get(brick)) {
                Set<Set<Tuple3<Integer, Integer, Integer>>> upperBrickSupporters = upperToLowerBricks.get(upperBrick);
                if(upperBrickSupporters.size() == 1) {
                    safeToRemove = false;
                }
            }
            if (safeToRemove) {
                result++;
            }
        }
        return result;
    }

    public Object part2(List<String> input) {
        parseInputMoveBricksAndStoreBrickRelation(input);

        long result = 0;
        for (Set<Tuple3<Integer, Integer, Integer>> brick : bricks) {
            Set<Set<Tuple3<Integer, Integer, Integer>>> removedBricks = new HashSet<>();
            removedBricks.add(brick);
            Set<Set<Tuple3<Integer, Integer, Integer>>> bricksToCheck = new HashSet<>(lowerToUpperBricks.get(brick));
            while (!bricksToCheck.isEmpty()) {
                Set<Set<Tuple3<Integer, Integer, Integer>>> newBricksToCheck = new HashSet<>();
                for (Set<Tuple3<Integer, Integer, Integer>> upper : bricksToCheck) {
                    Set<Set<Tuple3<Integer, Integer, Integer>>> upperBrickSupporters = new HashSet<>(upperToLowerBricks.get(upper));
                    if (!upperBrickSupporters.isEmpty()) {
                        upperBrickSupporters.removeAll(removedBricks);
                        if (upperBrickSupporters.isEmpty()) {
                            removedBricks.add(upper);
                            newBricksToCheck.addAll(lowerToUpperBricks.get(upper));
                        }
                    }
                }
                bricksToCheck = newBricksToCheck;
            }
            result += removedBricks.size() - 1;
        }
        return result;
    }

    private void parseInputMoveBricksAndStoreBrickRelation(List<String> input) {
        parseInput(input);
        moveBricks();
        storeBrickRelation();
    }

    private void parseInput(List<String> input) {
        bricks = new HashSet<>();
        for (String line : input) {
            String[] brickEnds = line.split("~");
            String[] positionEnd1 = brickEnds[0].split(",");
            String[] positionEnd2 = brickEnds[1].split(",");
            Set<Tuple3<Integer, Integer, Integer>> brick = new HashSet<>();
            for (int x = Integer.parseInt(positionEnd1[0]); x <= Integer.parseInt(positionEnd2[0]); x++) {
                for (int y = Integer.parseInt(positionEnd1[1]); y <= Integer.parseInt(positionEnd2[1]); y++) {
                    for (int z = Integer.parseInt(positionEnd1[2]); z <= Integer.parseInt(positionEnd2[2]); z++) {
                        brick.add(Tuple.tuple(x, y, z));
                    }
                }
            }
            bricks.add(brick);
        }
    }

    private void moveBricks() {
        Set<Tuple3<Integer, Integer, Integer>> allCubes = new HashSet<>();
        for (Set<Tuple3<Integer, Integer, Integer>> brick : bricks) {
            allCubes.addAll(brick);
        }
        int maxZ = allCubes.stream().mapToInt(Tuple3::v3).max().orElseThrow();

        for (int z = 0; z <= maxZ; z++) {
            int finalZ = z;
            Set<Set<Tuple3<Integer, Integer, Integer>>> bricksAtZ = bricks.stream().filter(brick -> brick.stream().mapToInt(Tuple3::v3).min().orElseThrow() == finalZ).collect(Collectors.toSet());
            Set<Set<Tuple3<Integer, Integer, Integer>>> movedBricks = new HashSet<>();
            for (Set<Tuple3<Integer, Integer, Integer>> brick : bricksAtZ) {
                allCubes.removeAll(brick);
                boolean moving = true;
                while (moving) {
                    Set<Tuple3<Integer, Integer, Integer>> newBrick = new HashSet<>();
                    for (Tuple3<Integer, Integer, Integer> cube : brick) {
                        Tuple3<Integer, Integer, Integer> newCube = Tuple.tuple(cube.v1, cube.v2, cube.v3 - 1);
                        if (allCubes.contains(newCube) || newCube.v3 == 0) {
                            moving = false;
                            break;
                        }
                        newBrick.add(newCube);
                    }
                    if (moving) {
                        brick = newBrick;
                    }
                }
                allCubes.addAll(brick);
                movedBricks.add(brick);
            }
            bricks.removeAll(bricksAtZ);
            bricks.addAll(movedBricks);
        }
    }

    private void storeBrickRelation() {
        lowerToUpperBricks = new HashMap<>();
        upperToLowerBricks = new HashMap<>();
        for (Set<Tuple3<Integer, Integer, Integer>> brick : bricks) {
            lowerToUpperBricks.put(brick, new HashSet<>());
            upperToLowerBricks.put(brick, new HashSet<>());
        }
        for (Set<Tuple3<Integer, Integer, Integer>> brick : bricks) {
            for (Tuple3<Integer, Integer, Integer> cube : brick) {
                Tuple3<Integer, Integer, Integer> supportingCube = Tuple.tuple(cube.v1, cube.v2, cube.v3 - 1);
                for (Set<Tuple3<Integer, Integer, Integer>> otherBrick : bricks) {
                    if (otherBrick != brick && otherBrick.contains(supportingCube)) {
                        lowerToUpperBricks.get(otherBrick).add(brick);
                        upperToLowerBricks.get(brick).add(otherBrick);
                    }
                }
            }
        }
    }

}
