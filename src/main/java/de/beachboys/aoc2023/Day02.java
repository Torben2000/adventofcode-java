package de.beachboys.aoc2023;

import de.beachboys.Day;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.ArrayList;
import java.util.List;

public class Day02 extends Day {

    public Object part1(List<String> input) {
        long result = 0;

        for (String line : input) {
            Game game = parseGame(line);
            boolean gamePossible = true;
            for (Tuple2<String, Integer> cubeSet : game.cubeSets) {
                if (!cubeSet.v1.equals("red") && !cubeSet.v1.equals("green") && !cubeSet.v1.equals("blue")
                        || cubeSet.v1.equals("red") && cubeSet.v2 > 12
                        || cubeSet.v1.equals("green") && cubeSet.v2 > 13
                        || cubeSet.v1.equals("blue") && cubeSet.v2 > 14) {
                    gamePossible = false;
                    break;
                }
            }
            if (gamePossible) {
                result += game.id;
            }
        }

        return result;
    }

    public Object part2(List<String> input) {
        long result = 0;

        for (String line : input) {
            Game game = parseGame(line);
            int minRedCubes = 0;
            int minBlueCubes = 0;
            int minGreenCubes = 0;
            for (Tuple2<String, Integer> cubeSet : game.cubeSets) {
                if (cubeSet.v1.equals("red")) {
                    minRedCubes = Math.max(minRedCubes, cubeSet.v2);
                }
                if (cubeSet.v1.equals("green")) {
                    minGreenCubes = Math.max(minGreenCubes, cubeSet.v2);
                }
                if (cubeSet.v1.equals("blue")) {
                    minBlueCubes = Math.max(minBlueCubes, cubeSet.v2);
                }

            }
            result += (long) minRedCubes * minGreenCubes * minBlueCubes;
        }

        return result;
    }

    private static Game parseGame(String line) {
        Game game = new Game();
        String[] gameIdAndCubeSets = line.split(":");
        game.id = Integer.parseInt(gameIdAndCubeSets[0].substring("Game ".length()));
        String[] multiColorCubeSets = gameIdAndCubeSets[1].trim().split("; ");
        game.cubeSets = new ArrayList<>();
        for (String multiColorCubeSet : multiColorCubeSets) {
            String[] singleColorCubeSets = multiColorCubeSet.split(", ");
            for (String singleColorCubeSet : singleColorCubeSets) {
                String[] numberAndColor = singleColorCubeSet.split(" ");
                game.cubeSets.add(Tuple.tuple(numberAndColor[1], Integer.parseInt(numberAndColor[0])));
            }
        }
        return game;
    }

    private static class Game {
        int id;
        List<Tuple2<String, Integer>> cubeSets;
    }

}
