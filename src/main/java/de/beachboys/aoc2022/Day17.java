package de.beachboys.aoc2022;

import de.beachboys.Day;
import de.beachboys.Direction;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.*;
import java.util.stream.Collectors;

public class Day17 extends Day {

    Map<PatternKey, Tuple2<Integer, Integer>> patterns = new HashMap<>();

    Map<Integer, Integer> heights = new HashMap<>();

    public Object part1(List<String> input) {
        return runLogic(input, 2022, false);
    }
    public Object part2(List<String> input) {
        return runLogic(input, 1000000000000L, true);
    }


    private long runLogic(List<String> input, long target, boolean optimizeForLargeNumbers) {
        List<Set<Tuple2<Integer, Integer>>> rockShapes = getRockShapes();
        List<Direction> jetDirections = Util.parseToList(input.get(0), "").stream().map(Direction::fromString).collect(Collectors.toList());
        Set<Tuple2<Integer, Integer>> restingRocks = prepareRestingRocksWithBase();


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
            Set<Tuple2<Integer, Integer>> rock = getRockPreparedForFalling(rockShapes, currentRockShape, towerHeight);
            boolean rockIsDown = false;
            while (!rockIsDown) {
                currentJetDirection = (currentJetDirection + 1) % jetDirections.size();
                Direction jetDirection = jetDirections.get(currentJetDirection);
                Set<Tuple2<Integer, Integer>> rockAfterJetOfGas = applyJetOfGas(rock, jetDirection, restingRocks);

                Set<Tuple2<Integer, Integer>> fallenRock = new HashSet<>();
                for (Tuple2<Integer, Integer> rockPart : rockAfterJetOfGas) {
                    Tuple2<Integer, Integer> fallenRockPart = Direction.SOUTH.move(rockPart, 1);
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
                Set<Tuple2<Integer, Integer>> rockPatternFromBaseLine = restingRocks.stream().filter(rockPart -> rockPart.v2 < finalBaseLine).map(rockPart -> Tuple.tuple(rockPart.v1, rockPart.v2 - finalBaseLine)).collect(Collectors.toSet());

                PatternKey patternKey = new PatternKey(rockPatternFromBaseLine, currentRockShape, currentJetDirection);
                if (patterns.containsKey(patternKey)) {
                    Tuple2<Integer, Integer> res = patterns.get(patternKey);
                    int diffCounter = rockCounter - res.v2;
                    int diffHeight = Math.abs(lastKnownBaseLine - res.v1);
                    long roundsOfSamePatterns = target / diffCounter;
                    int rocksOnTopOfCalculatedTower = (int) (target % diffCounter) - res.v2;
                    if (rocksOnTopOfCalculatedTower > 0) {
                        int otherHeight = heights.get(res.v2 + rocksOnTopOfCalculatedTower);
                        return roundsOfSamePatterns * diffHeight + otherHeight;
                    } else {
                        int otherHeight = heights.get(rockCounter + rocksOnTopOfCalculatedTower);
                        return (roundsOfSamePatterns - 1) * diffHeight + otherHeight;
                    }
                } else {
                    patterns.put(patternKey, Tuple.tuple(lastKnownBaseLine, rockCounter));
                }
            }

            highestLine = restingRocks.stream().map(Tuple2::v2).min(Integer::compareTo).orElseThrow();
            towerHeight = -highestLine + 1;
            rockCounter++;
        }

        return towerHeight;
    }

    private static int updateLastKnownBaseLine(Set<Tuple2<Integer, Integer>> restingRocks, int highestLine, int lastKnownBaseLine) {
        for (int line = highestLine; line < lastKnownBaseLine; line++) {
            int finalLine = line;
            Set<Integer> rockPartsInLine = restingRocks.stream().filter(p -> p.v2 == finalLine).map(Tuple2::v1).collect(Collectors.toSet());
            // this pattern works for both the example and the real data
            if (rockPartsInLine.size() == 6 && !rockPartsInLine.contains(0)) {
                lastKnownBaseLine = line;
                break;
            }
        }
        return lastKnownBaseLine;
    }

    private static boolean isValidRockPartPosition(Tuple2<Integer, Integer> rockPart, Set<Tuple2<Integer, Integer>> fallenRocks) {
        return !fallenRocks.contains(rockPart) && Util.isInRectangle(rockPart, Tuple.tuple(0, Integer.MIN_VALUE), Tuple.tuple(6, 0));
    }

    private static Set<Tuple2<Integer, Integer>> applyJetOfGas(Set<Tuple2<Integer, Integer>> rock, Direction jetDirection, Set<Tuple2<Integer, Integer>> fallenRocks) {
        Set<Tuple2<Integer, Integer>> rockShapeAfterJetOfGas = new HashSet<>();
        for (Tuple2<Integer, Integer> rockPart : rock) {
            Tuple2<Integer, Integer> movedRockPart = jetDirection.move(rockPart, 1);
            if (isValidRockPartPosition(movedRockPart, fallenRocks)) {
                rockShapeAfterJetOfGas.add(movedRockPart);
            } else {
                rockShapeAfterJetOfGas = rock;
                break;
            }
        }
        return rockShapeAfterJetOfGas;
    }

    private static Set<Tuple2<Integer, Integer>> getRockPreparedForFalling(List<Set<Tuple2<Integer, Integer>>> rockShapes, int currentRockShape, int towerHeight) {
        Set<Tuple2<Integer, Integer>> movedUpStone = new HashSet<>();
        Set<Tuple2<Integer, Integer>> stone = rockShapes.get(currentRockShape);
        for (Tuple2<Integer, Integer> s : stone) {
            Tuple2<Integer, Integer> s2 = Direction.NORTH.move(s, towerHeight + 3);
                movedUpStone.add(s2);
        }
        stone = movedUpStone;
        return stone;
    }

    private static Set<Tuple2<Integer, Integer>> prepareRestingRocksWithBase() {
        Set<Tuple2<Integer, Integer>> restingRocks = new HashSet<>();
        restingRocks.add(Tuple.tuple(-1, 1));
        restingRocks.add(Tuple.tuple(0, 1));
        restingRocks.add(Tuple.tuple(1, 1));
        restingRocks.add(Tuple.tuple(2, 1));
        restingRocks.add(Tuple.tuple(3, 1));
        restingRocks.add(Tuple.tuple(4, 1));
        restingRocks.add(Tuple.tuple(5, 1));
        restingRocks.add(Tuple.tuple(6, 1));
        restingRocks.add(Tuple.tuple(7, 1));
        return restingRocks;
    }

    private static List<Set<Tuple2<Integer, Integer>>> getRockShapes() {
        List<Set<Tuple2<Integer, Integer>>> rockShapes = new ArrayList<>();
        rockShapes.add(Set.of(Tuple.tuple(2, 0), Tuple.tuple(3, 0), Tuple.tuple(4, 0), Tuple.tuple(5, 0)));
        rockShapes.add(Set.of(Tuple.tuple(3, -2), Tuple.tuple(2, -1), Tuple.tuple(3, -1), Tuple.tuple(4, -1), Tuple.tuple(3, 0)));
        rockShapes.add(Set.of(Tuple.tuple(4, -2), Tuple.tuple(4, -1), Tuple.tuple(2, 0), Tuple.tuple(3, 0), Tuple.tuple(4, 0)));
        rockShapes.add(Set.of(Tuple.tuple(2, 0), Tuple.tuple(2, -1), Tuple.tuple(2, -2), Tuple.tuple(2, -3)));
        rockShapes.add(Set.of(Tuple.tuple(2, 0), Tuple.tuple(3, 0), Tuple.tuple(2, -1), Tuple.tuple(3, -1)));
        return rockShapes;
    }

    private static class PatternKey {

        Set<Tuple2<Integer, Integer>> pattern;
        int currentRockShape;
        int currentJetDirection;

        public PatternKey(Set<Tuple2<Integer, Integer>> pattern, int currentRockShape, int currentJetDirection) {
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
