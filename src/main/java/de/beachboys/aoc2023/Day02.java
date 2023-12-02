package de.beachboys.aoc2023;

import de.beachboys.Day;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;

public class Day02 extends Day {

    public Object part1(List<String> input) {
        long result = 0;

        for (String line : input) {
            Game game = parseGame(line);
            boolean gamePossible = true;
            for (Pair<String, Integer> cubeSet : game.cubeSets) {
                if (!cubeSet.getValue0().equals("red") && !cubeSet.getValue0().equals("green") && !cubeSet.getValue0().equals("blue")
                        || cubeSet.getValue0().equals("red") && cubeSet.getValue1() > 12
                        || cubeSet.getValue0().equals("green") && cubeSet.getValue1() > 13
                        || cubeSet.getValue0().equals("blue") && cubeSet.getValue1() > 14) {
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
            for (Pair<String, Integer> cubeSet : game.cubeSets) {
                if (cubeSet.getValue0().equals("red")) {
                    minRedCubes = Math.max(minRedCubes, cubeSet.getValue1());
                }
                if (cubeSet.getValue0().equals("green")) {
                    minGreenCubes = Math.max(minGreenCubes, cubeSet.getValue1());
                }
                if (cubeSet.getValue0().equals("blue")) {
                    minBlueCubes = Math.max(minBlueCubes, cubeSet.getValue1());
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
                game.cubeSets.add(Pair.with(numberAndColor[1], Integer.parseInt(numberAndColor[0])));
            }
        }
        return game;
    }

    private static class Game {
        int id;
        List<Pair<String, Integer>> cubeSets;
    }

}
