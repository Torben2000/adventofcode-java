package de.beachboys.aoc2019;

import de.beachboys.Day;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;
import org.jooq.lambda.tuple.Tuple3;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day24 extends Day {

    private Set<Tuple3<Integer, Integer, Integer>> bugs;

    private static final int WIDTH = 5;

    private static final int HEIGHT = 5;

    public Object part1(List<String> input) {
        fillInitialBugsSet(input);

        Set<Long> ratings = new HashSet<>();
        while (true) {
            long rating = calculateRating();
            if (ratings.contains(rating)) {
                return rating;
            }
            ratings.add(rating);
            mutate(0, 0, this::getNeighborsPart1, (i, j) -> true);
        }
    }

    public Object part2(List<String> input) {
        int minutes = Util.getIntValueFromUser("Minutes to mutate", 200, io);
        fillInitialBugsSet(input);

        for (int i = 0; i < minutes; i++) {
            int minLevel = bugs.stream().mapToInt(Tuple3::v1).min().orElseThrow() - 1;
            int maxLevel = bugs.stream().mapToInt(Tuple3::v1).max().orElseThrow() + 1;
            mutate(minLevel, maxLevel, this::getNeighborsPart2, (x, y) -> x != WIDTH / 2 || y != HEIGHT / 2);
        }

       return bugs.size();
    }

    private void fillInitialBugsSet(List<String> input) {
        final Map<Tuple2<Integer, Integer>, String> map = Util.buildImageMap(input);
        bugs = map.keySet().stream().filter(p -> isBug(map.get(p))).map(p -> Tuple.tuple(0, p.v1, p.v2)).collect(Collectors.toSet());
    }

    private void mutate(int minLevel, int maxLevel, Function<Tuple3<Integer, Integer, Integer>, Set<Tuple3<Integer, Integer, Integer>>> neighborProvider, BiPredicate<Integer, Integer> validPositionPredicate) {
        Set<Tuple3<Integer, Integer, Integer>> newSet = new HashSet<>();
        for (int level = minLevel; level <= maxLevel; level++){
            for (int i = 0; i < WIDTH; i++) {
                for (int j = 0; j < HEIGHT; j++) {
                    if (validPositionPredicate.test(i, j)) {
                        Tuple3<Integer, Integer, Integer> position = Tuple.tuple(level, i, j);
                        int surroundingBugCount = getSurroundingBugCount(position, neighborProvider);
                        if (surroundingBugCount == 1 || surroundingBugCount == 2 && !bugs.contains(position)) {
                            newSet.add(position);
                        }
                    }
                }
            }
        }
        bugs = newSet;
    }

    private int getSurroundingBugCount(Tuple3<Integer, Integer, Integer> position, Function<Tuple3<Integer, Integer, Integer>, Set<Tuple3<Integer, Integer, Integer>>> neighborProvider) {
        int occupiedCounter = 0;
        for (Tuple3<Integer, Integer, Integer> neighbor : neighborProvider.apply(position)) {
            if (bugs.contains(neighbor)) {
                occupiedCounter++;
            }
        }
        return occupiedCounter;
    }

    private Set<Tuple3<Integer, Integer, Integer>> getNeighborsPart1(Tuple3<Integer, Integer, Integer> position) {
        return Set.of(Tuple.tuple(0, position.v2, position.v3 - 1),
                Tuple.tuple(0, position.v2, position.v3 + 1),
                Tuple.tuple(0, position.v2 - 1, position.v3),
                Tuple.tuple(0, position.v2 + 1, position.v3));
    }

    private Set<Tuple3<Integer, Integer, Integer>> getNeighborsPart2(Tuple3<Integer, Integer, Integer> pos) {
        Set<Tuple3<Integer, Integer, Integer>> neighbors = new HashSet<>();
        addLeftNeighborsToSet(pos, neighbors);
        addRightNeighborsToSet(pos, neighbors);
        addTopNeighborsToSet(pos, neighbors);
        addBottomNeighborsToSet(pos, neighbors);
        return neighbors;
    }

    private void addBottomNeighborsToSet(Tuple3<Integer, Integer, Integer> pos, Set<Tuple3<Integer, Integer, Integer>> neighbors) {
        addYNeighborsToSet(pos, neighbors, HEIGHT - 1, HEIGHT / 2 + 1, HEIGHT / 2 - 1, 0, pos.v3 + 1);
    }

    private void addTopNeighborsToSet(Tuple3<Integer, Integer, Integer> pos, Set<Tuple3<Integer, Integer, Integer>> neighbors) {
        addYNeighborsToSet(pos, neighbors, 0, HEIGHT / 2 - 1, HEIGHT / 2 + 1, HEIGHT - 1, pos.v3 - 1);
    }

    private void addLeftNeighborsToSet(Tuple3<Integer, Integer, Integer> pos, Set<Tuple3<Integer, Integer, Integer>> neighbors) {
        addXNeighborsToSet(pos, neighbors, 0, WIDTH / 2 - 1, WIDTH / 2 + 1, WIDTH - 1, pos.v2 - 1);
    }

    private void addRightNeighborsToSet(Tuple3<Integer, Integer, Integer> pos, Set<Tuple3<Integer, Integer, Integer>> neighbors) {
        addXNeighborsToSet(pos, neighbors, WIDTH - 1, WIDTH / 2 + 1, WIDTH / 2 - 1, 0, pos.v2 + 1);
    }

    private void addXNeighborsToSet(Tuple3<Integer, Integer, Integer> pos, Set<Tuple3<Integer, Integer, Integer>> neighbors, int outerBorderX, int outerBorderNeighborX, int innerBorderX, int innerBorderNeighborX, int sameLevelNeighborX) {
        if (pos.v2 == outerBorderX) {
            neighbors.add(Tuple.tuple(pos.v1 - 1, outerBorderNeighborX, 2));
        } else if (pos.v2 == innerBorderX && pos.v3 == 2) {
            for (int j = 0; j < 5; j++) {
                neighbors.add(Tuple.tuple(pos.v1 + 1, innerBorderNeighborX, j));
            }
        } else {
            neighbors.add(Tuple.tuple(pos.v1, sameLevelNeighborX, pos.v3));
        }
    }

    private void addYNeighborsToSet(Tuple3<Integer, Integer, Integer> pos, Set<Tuple3<Integer, Integer, Integer>> neighbors, int outerBorderY, int outerBorderNeighborY, int innerBorderY, int innerBorderNeighborY, int sameLevelNeighborY) {
        if (pos.v3 == outerBorderY) {
            neighbors.add(Tuple.tuple(pos.v1 - 1, 2, outerBorderNeighborY));
        } else if (pos.v3 == innerBorderY && pos.v2 == 2) {
            for (int j = 0; j < 5; j++) {
                neighbors.add(Tuple.tuple(pos.v1 + 1, j, innerBorderNeighborY));
            }
        } else {
            neighbors.add(Tuple.tuple(pos.v1, pos.v2, sameLevelNeighborY));
        }
    }

    private long calculateRating() {
        return bugs.stream().mapToLong(p -> (long) Math.pow(2, p.v3 * WIDTH + p.v2)).sum();
    }

    private boolean isBug(String mapValue) {
        return "#".equals(mapValue);
    }

}
