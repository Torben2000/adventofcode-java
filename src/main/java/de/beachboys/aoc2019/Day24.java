package de.beachboys.aoc2019;

import de.beachboys.Day;
import de.beachboys.Util;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day24 extends Day {

    private Set<Triplet<Integer, Integer, Integer>> bugs;

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
        int minutes = 200;
        String minutesAsInput = io.getInput("Minutes to mutate (default 200):");
        if (!minutesAsInput.isEmpty()) {
            minutes = Integer.parseInt(minutesAsInput);
        }
        fillInitialBugsSet(input);

        for (int i = 0; i < minutes; i++) {
            int minLevel = bugs.stream().mapToInt(Triplet::getValue0).min().orElseThrow() - 1;
            int maxLevel = bugs.stream().mapToInt(Triplet::getValue0).max().orElseThrow() + 1;
            mutate(minLevel, maxLevel, this::getNeighborsPart2, (x, y) -> x != WIDTH / 2 || y != HEIGHT / 2);
        }

       return bugs.size();
    }

    private void fillInitialBugsSet(List<String> input) {
        final Map<Pair<Integer, Integer>, String> map = Util.buildImageMap(input);
        bugs = map.keySet().stream().filter(p -> isBug(map.get(p))).map(p -> Triplet.with(0, p.getValue0(), p.getValue1())).collect(Collectors.toSet());
    }

    private void mutate(int minLevel, int maxLevel, Function<Triplet<Integer, Integer, Integer>, Set<Triplet<Integer, Integer, Integer>>> neighborProvider, BiPredicate<Integer, Integer> validPositionPredicate) {
        Set<Triplet<Integer, Integer, Integer>> newSet = new HashSet<>();
        for (int level = minLevel; level <= maxLevel; level++){
            for (int i = 0; i < WIDTH; i++) {
                for (int j = 0; j < HEIGHT; j++) {
                    if (validPositionPredicate.test(i, j)) {
                        Triplet<Integer, Integer, Integer> position = Triplet.with(level, i, j);
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

    private int getSurroundingBugCount(Triplet<Integer, Integer, Integer> position, Function<Triplet<Integer, Integer, Integer>, Set<Triplet<Integer, Integer, Integer>>> neighborProvider) {
        int occupiedCounter = 0;
        for (Triplet<Integer, Integer, Integer> neighbor : neighborProvider.apply(position)) {
            if (bugs.contains(neighbor)) {
                occupiedCounter++;
            }
        }
        return occupiedCounter;
    }

    private Set<Triplet<Integer, Integer, Integer>> getNeighborsPart1(Triplet<Integer, Integer, Integer> position) {
        return Set.of(Triplet.with(0, position.getValue1(), position.getValue2() - 1),
                Triplet.with(0, position.getValue1(), position.getValue2() + 1),
                Triplet.with(0, position.getValue1() - 1, position.getValue2()),
                Triplet.with(0, position.getValue1() + 1, position.getValue2()));
    }

    private Set<Triplet<Integer, Integer, Integer>> getNeighborsPart2(Triplet<Integer, Integer, Integer> pos) {
        Set<Triplet<Integer, Integer, Integer>> neighbors = new HashSet<>();
        addLeftNeighborsToSet(pos, neighbors);
        addRightNeighborsToSet(pos, neighbors);
        addTopNeighborsToSet(pos, neighbors);
        addBottomNeighborsToSet(pos, neighbors);
        return neighbors;
    }

    private void addBottomNeighborsToSet(Triplet<Integer, Integer, Integer> pos, Set<Triplet<Integer, Integer, Integer>> neighbors) {
        addYNeighborsToSet(pos, neighbors, HEIGHT - 1, HEIGHT / 2 + 1, HEIGHT / 2 - 1, 0, pos.getValue2() + 1);
    }

    private void addTopNeighborsToSet(Triplet<Integer, Integer, Integer> pos, Set<Triplet<Integer, Integer, Integer>> neighbors) {
        addYNeighborsToSet(pos, neighbors, 0, HEIGHT / 2 - 1, HEIGHT / 2 + 1, HEIGHT - 1, pos.getValue2() - 1);
    }

    private void addLeftNeighborsToSet(Triplet<Integer, Integer, Integer> pos, Set<Triplet<Integer, Integer, Integer>> neighbors) {
        addXNeighborsToSet(pos, neighbors, 0, WIDTH / 2 - 1, WIDTH / 2 + 1, WIDTH - 1, pos.getValue1() - 1);
    }

    private void addRightNeighborsToSet(Triplet<Integer, Integer, Integer> pos, Set<Triplet<Integer, Integer, Integer>> neighbors) {
        addXNeighborsToSet(pos, neighbors, WIDTH - 1, WIDTH / 2 + 1, WIDTH / 2 - 1, 0, pos.getValue1() + 1);
    }

    private void addXNeighborsToSet(Triplet<Integer, Integer, Integer> pos, Set<Triplet<Integer, Integer, Integer>> neighbors, int outerBorderX, int outerBorderNeighborX, int innerBorderX, int innerBorderNeighborX, int sameLevelNeighborX) {
        if (pos.getValue1() == outerBorderX) {
            neighbors.add(Triplet.with(pos.getValue0() - 1, outerBorderNeighborX, 2));
        } else if (pos.getValue1() == innerBorderX && pos.getValue2() == 2) {
            for (int j = 0; j < 5; j++) {
                neighbors.add(Triplet.with(pos.getValue0() + 1, innerBorderNeighborX, j));
            }
        } else {
            neighbors.add(Triplet.with(pos.getValue0(), sameLevelNeighborX, pos.getValue2()));
        }
    }

    private void addYNeighborsToSet(Triplet<Integer, Integer, Integer> pos, Set<Triplet<Integer, Integer, Integer>> neighbors, int outerBorderY, int outerBorderNeighborY, int innerBorderY, int innerBorderNeighborY, int sameLevelNeighborY) {
        if (pos.getValue2() == outerBorderY) {
            neighbors.add(Triplet.with(pos.getValue0() - 1, 2, outerBorderNeighborY));
        } else if (pos.getValue2() == innerBorderY && pos.getValue1() == 2) {
            for (int j = 0; j < 5; j++) {
                neighbors.add(Triplet.with(pos.getValue0() + 1, j, innerBorderNeighborY));
            }
        } else {
            neighbors.add(Triplet.with(pos.getValue0(), pos.getValue1(), sameLevelNeighborY));
        }
    }

    private long calculateRating() {
        return bugs.stream().mapToLong(p -> (long) Math.pow(2, p.getValue2() * WIDTH + p.getValue1())).sum();
    }

    private boolean isBug(String mapValue) {
        return "#".equals(mapValue);
    }

}
