package de.beachboys.aoc2019;

import de.beachboys.Day;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Day10 extends Day {

    Tuple2<Integer, Integer> station = Tuple.tuple(-1, -1);

    public Object part1(List<String> input) {
        boolean[][] isAsteroid = buildAsteroidMap(input);
        long maxAsteroids = 0L;
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < isAsteroid[i].length; j++) {
                if (isAsteroid[i][j]) {
                    boolean[][] asteroidsInSight = getAsteroidsInLineOfSight(isAsteroid, i, j);
                    long numOfAsteroidsInSight = 0L;
                    //noinspection ForLoopReplaceableByForEach
                    for (int x = 0; x < asteroidsInSight.length; x++) {
                        for (int y = 0; y < asteroidsInSight[i].length; y++) {
                            if (asteroidsInSight[x][y]) {
                                numOfAsteroidsInSight++;
                            }
                        }
                    }
                    if (maxAsteroids < numOfAsteroidsInSight) {
                        maxAsteroids = numOfAsteroidsInSight;
                        station = Tuple.tuple(i, j);
                        io.logDebug(station  + ": " + numOfAsteroidsInSight);
                    }
                }
            }
        }
        io.logInfo("Found station: " + station.v1 + "," + station.v2);
        return maxAsteroids;
    }

    private boolean[][] buildAsteroidMap(List<String> input) {
        boolean[][] isAsteroid = new boolean[input.getFirst().length()][input.size()];
        for (int y = 0; y < input.size(); y++) {
            String currentLine = input.get(y);
            for (int x = 0; x < currentLine.length(); x++) {
                isAsteroid[x][y] = currentLine.charAt(x) == '#';
            }
        }
        return isAsteroid;
    }

    private boolean[][] getAsteroidsInLineOfSight(boolean[][] isAsteroid, int i, int j) {
        boolean[][] checkedAsteroids = new boolean[isAsteroid.length][isAsteroid[0].length];
        boolean[][] asteroidsInSight = new boolean[isAsteroid.length][isAsteroid[0].length];


        for (int stepX = 0; stepX >= -i; stepX--) {
            for (int stepY = 0; stepY >= -j; stepY--) {
                updateAsteroidsInSight(asteroidsInSight, isAsteroid, i, j, checkedAsteroids, stepX, stepY);

            }
        }
        for (int stepX = 0; stepX >= -i; stepX--) {
            for (int stepY = 0; stepY < isAsteroid[i].length - j; stepY++) {
                updateAsteroidsInSight(asteroidsInSight, isAsteroid, i, j, checkedAsteroids, stepX, stepY);

            }
        }
        for (int stepX = 0; stepX < isAsteroid.length - i; stepX++) {
            for (int stepY = 0; stepY < isAsteroid[i].length - j; stepY++) {
                updateAsteroidsInSight(asteroidsInSight, isAsteroid, i, j, checkedAsteroids, stepX, stepY);
            }
        }
        for (int stepX = 0; stepX < isAsteroid.length - i; stepX++) {
            for (int stepY = 0; stepY >= -j; stepY--) {
                updateAsteroidsInSight(asteroidsInSight, isAsteroid, i, j, checkedAsteroids, stepX, stepY);
            }
        }
        return asteroidsInSight;
    }

    private void updateAsteroidsInSight(boolean[][] asteroidsInSight, boolean[][] isAsteroid, int i, int j, boolean[][] checkedAsteroids, int stepX, int stepY) {
        if (stepX == 0 && stepY == 0) {
            return;
        }
        int x = i;
        int y = j;
        boolean asteroidFoundInLineOfSight = false;
        while (isInRange(isAsteroid, x, y)) {
            x += stepX;
            y += stepY;
            if (isInRange(isAsteroid, x, y) && !checkedAsteroids[x][y]) {
                checkedAsteroids[x][y] = true;
                if (!asteroidFoundInLineOfSight && isAsteroid[x][y]) {
                    asteroidsInSight[x][y] = true;
                    asteroidFoundInLineOfSight = true;
                }
            }
        }
    }

    private boolean isInRange(boolean[][] isAsteroid, int x, int y) {
        return x >= 0 && y >= 0 && x < isAsteroid.length && y < isAsteroid[0].length;
    }

    public Object part2(List<String> input) {
        boolean[][] isAsteroid = buildAsteroidMap(input);

        // find station
        part1(input);

        int asteroidIndex = Integer.parseInt(io.getInput("Asteroid index:"));
        int currentIndex = 0;
        boolean asteroidsFound = true;
        while (asteroidsFound) {
            boolean[][] asteroidsInSight = getAsteroidsInLineOfSight(isAsteroid, station.v1, station.v2);
            Map<Double, Tuple2<Integer, Integer>> orderedAsteroidsInSight = new TreeMap<>();
            for (int i = 0; i < asteroidsInSight.length; i++) {
                for (int j = 0; j < asteroidsInSight[i].length; j++) {
                    if (asteroidsInSight[i][j]) {
                        orderedAsteroidsInSight.put(getOrderValueOfAsteroid(station, i, j), Tuple.tuple(i, j));
                        isAsteroid[i][j] = false;
                    }
                }
            }
            for (Map.Entry<Double, Tuple2<Integer, Integer>> entry : orderedAsteroidsInSight.entrySet()) {
                currentIndex++;
                if (currentIndex == asteroidIndex) {
                    return entry.getValue().v1*100+entry.getValue().v2;
                }

            }
            asteroidsFound = !orderedAsteroidsInSight.isEmpty();
        }
        return"error";
    }

    private Double getOrderValueOfAsteroid(Tuple2<Integer, Integer> station, int x, int y) {
        int diffX = x-station.v1;
        int diffY = y-station.v2;
        return -1 * Math.atan2(diffX, diffY);
    }

}
