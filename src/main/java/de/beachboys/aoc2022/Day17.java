package de.beachboys.aoc2022;

import de.beachboys.Day;
import de.beachboys.Direction;
import de.beachboys.Util;
import org.javatuples.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class Day17 extends Day {

    Map<PatternKey, Pair<Integer, Integer>> patterns = new HashMap<>();

    Map<Integer, Integer> heights = new HashMap<>();

    public Object part1(List<String> input) {
        return runLogic(input, 2022, false);
    }
    public Object part2(List<String> input) {
        return runLogic(input, 1000000000000L, true);
    }


    private long runLogic(List<String> input, long target, boolean optimizeForLargeNumbers) {
        List<Set<Pair<Integer, Integer>>> rockShapes = getRockShapes();
        List<Direction> jetDirections = Util.parseToList(input.get(0), "").stream().map(Direction::fromString).collect(Collectors.toList());
        Set<Pair<Integer, Integer>> restingRocks = prepareRestingRocksWithBase();


        int highestLine = 1;
        int towerHeight = 0;
        int rockCounter = 0;
        int currentJetDirection = -1;
        int currentRockShape;

        int lastKnownBaseLine = 1;
        patterns.clear();
        heights.clear();

        while (rockCounter < target) {
            currentRockShape = rockCounter % 5;
            Set<Pair<Integer, Integer>> rock = getRockPreparedForFalling(rockShapes, currentRockShape, towerHeight);
            boolean rockIsDown = false;
            while (!rockIsDown) {
                currentJetDirection = (currentJetDirection + 1) % jetDirections.size();
                Direction jetDirection = jetDirections.get(currentJetDirection);
                Set<Pair<Integer, Integer>> rockAfterJetOfGas = applyJetOfGas(rock, jetDirection, restingRocks);

                Set<Pair<Integer, Integer>> fallenRock = new HashSet<>();
                for (Pair<Integer, Integer> rockPart : rockAfterJetOfGas) {
                    Pair<Integer, Integer> fallenRockPart = Direction.SOUTH.move(rockPart, 1);
                    if (isValidRockPartPosition(fallenRockPart, restingRocks)) {
                        fallenRock.add(fallenRockPart);
                    } else {
                        rockIsDown = true;
                        restingRocks.addAll(rockAfterJetOfGas);
                        break;
                    }
                }

                rock = fallenRock;
            }

            if (optimizeForLargeNumbers) {
                heights.put(rockCounter, towerHeight);
                lastKnownBaseLine = updateLastKnownBaseLine(restingRocks, highestLine, lastKnownBaseLine);

                int finalBaseLine = lastKnownBaseLine;
                Set<Pair<Integer, Integer>> rockPatternFromBaseLine = restingRocks.stream().filter(rockPart -> rockPart.getValue1() < finalBaseLine).map(rockPart -> Pair.with(rockPart.getValue0(), rockPart.getValue1() - finalBaseLine)).collect(Collectors.toSet());

                PatternKey patternKey = new PatternKey(rockPatternFromBaseLine, currentRockShape, currentJetDirection);
                if (patterns.containsKey(patternKey)) {
                    Pair<Integer, Integer> res = patterns.get(patternKey);
                    int diffCounter = rockCounter - res.getValue1();
                    int diffHeight = Math.abs(lastKnownBaseLine - res.getValue0());
                    long roundsOfSamePatterns = target / diffCounter;
                    int rocksOnTopOfCalculatedTower = (int) (target % diffCounter) - res.getValue1();
                    if (rocksOnTopOfCalculatedTower > 0) {
                        int otherHeight = heights.get(res.getValue1() + rocksOnTopOfCalculatedTower);
                        return roundsOfSamePatterns * diffHeight + otherHeight;
                    } else {
                        int otherHeight = heights.get(rockCounter + rocksOnTopOfCalculatedTower);
                        return (roundsOfSamePatterns - 1) * diffHeight + otherHeight;
                    }
                } else {
                    patterns.put(patternKey, Pair.with(lastKnownBaseLine, rockCounter));
                }
            }

            highestLine = restingRocks.stream().map(Pair::getValue1).min(Integer::compareTo).orElseThrow();
            towerHeight = -highestLine + 1;
            rockCounter++;
        }

        return towerHeight;
    }

    private static int updateLastKnownBaseLine(Set<Pair<Integer, Integer>> restingRocks, int highestLine, int lastKnownBaseLine) {
        for (int line = highestLine; line < lastKnownBaseLine; line++) {
            int finalLine = line;
            Set<Integer> rockPartsInLine = restingRocks.stream().filter(p -> p.getValue1() == finalLine).map(Pair::getValue0).collect(Collectors.toSet());
            // this pattern works for both the example and the real data
            if (rockPartsInLine.size() == 6 && !rockPartsInLine.contains(0)) {
                lastKnownBaseLine = line;
                break;
            }
        }
        return lastKnownBaseLine;
    }

    private static boolean isValidRockPartPosition(Pair<Integer, Integer> rockPart, Set<Pair<Integer, Integer>> fallenRocks) {
        return !fallenRocks.contains(rockPart) && Util.isInRectangle(rockPart, Pair.with(0, Integer.MIN_VALUE), Pair.with(6, 0));
    }

    private static Set<Pair<Integer, Integer>> applyJetOfGas(Set<Pair<Integer, Integer>> rock, Direction jetDirection, Set<Pair<Integer, Integer>> fallenRocks) {
        Set<Pair<Integer, Integer>> rockShapeAfterJetOfGas = new HashSet<>();
        for (Pair<Integer, Integer> rockPart : rock) {
            Pair<Integer, Integer> movedRockPart = jetDirection.move(rockPart, 1);
            if (isValidRockPartPosition(movedRockPart, fallenRocks)) {
                rockShapeAfterJetOfGas.add(movedRockPart);
            } else {
                rockShapeAfterJetOfGas = rock;
                break;
            }
        }
        return rockShapeAfterJetOfGas;
    }

    private static Set<Pair<Integer, Integer>> getRockPreparedForFalling(List<Set<Pair<Integer, Integer>>> rockShapes, int currentRockShape, int towerHeight) {
        Set<Pair<Integer, Integer>> movedUpStone = new HashSet<>();
        Set<Pair<Integer, Integer>> stone = rockShapes.get(currentRockShape);
        for (Pair<Integer, Integer> s : stone) {
            Pair<Integer, Integer> s2 = Direction.NORTH.move(s, towerHeight + 3);
                movedUpStone.add(s2);
        }
        stone = movedUpStone;
        return stone;
    }

    private static Set<Pair<Integer, Integer>> prepareRestingRocksWithBase() {
        Set<Pair<Integer, Integer>> restingRocks = new HashSet<>();
        restingRocks.add(Pair.with(-1, 1));
        restingRocks.add(Pair.with(0, 1));
        restingRocks.add(Pair.with(1, 1));
        restingRocks.add(Pair.with(2, 1));
        restingRocks.add(Pair.with(3, 1));
        restingRocks.add(Pair.with(4, 1));
        restingRocks.add(Pair.with(5, 1));
        restingRocks.add(Pair.with(6, 1));
        restingRocks.add(Pair.with(7, 1));
        return restingRocks;
    }

    private static List<Set<Pair<Integer, Integer>>> getRockShapes() {
        List<Set<Pair<Integer, Integer>>> rockShapes = new ArrayList<>();
        rockShapes.add(Set.of(Pair.with(2, 0), Pair.with(3, 0), Pair.with(4, 0), Pair.with(5, 0)));
        rockShapes.add(Set.of(Pair.with(3, -2), Pair.with(2, -1), Pair.with(3, -1), Pair.with(4, -1), Pair.with(3, 0)));
        rockShapes.add(Set.of(Pair.with(4, -2), Pair.with(4, -1), Pair.with(2, 0), Pair.with(3, 0), Pair.with(4, 0)));
        rockShapes.add(Set.of(Pair.with(2, 0), Pair.with(2, -1), Pair.with(2, -2), Pair.with(2, -3)));
        rockShapes.add(Set.of(Pair.with(2, 0), Pair.with(3, 0), Pair.with(2, -1), Pair.with(3, -1)));
        return rockShapes;
    }

    private static class PatternKey {

        Set<Pair<Integer, Integer>> pattern;
        int currentRockShape;
        int currentJetDirection;

        public PatternKey(Set<Pair<Integer, Integer>> pattern, int currentRockShape, int currentJetDirection) {
            this.pattern = pattern;
            this.currentRockShape = currentRockShape;
            this.currentJetDirection = currentJetDirection;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PatternKey patternKey = (PatternKey) o;
            return currentRockShape == patternKey.currentRockShape && currentJetDirection == patternKey.currentJetDirection && Objects.equals(pattern, patternKey.pattern);
        }

        @Override
        public int hashCode() {
            return Objects.hash(pattern, currentRockShape, currentJetDirection);
        }
    }
}
