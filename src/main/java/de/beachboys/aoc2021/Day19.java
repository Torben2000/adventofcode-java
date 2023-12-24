package de.beachboys.aoc2021;

import de.beachboys.Day;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;
import org.jooq.lambda.tuple.Tuple3;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day19 extends Day {

    private static final int REQUIRED_MATCHES = 12;
    private static final List<Function<Tuple3<Integer, Integer, Integer>, Tuple3<Integer, Integer, Integer>>> ROTATORS = new ArrayList<>();

    static {
        Function<Tuple3<Integer, Integer, Integer>, Tuple3<Integer, Integer, Integer>> rotateX = t -> Tuple.tuple(t.v1, -t.v3, t.v2);
        Function<Tuple3<Integer, Integer, Integer>, Tuple3<Integer, Integer, Integer>> rotateY = t -> Tuple.tuple(-t.v3, t.v2, t.v1);
        Function<Tuple3<Integer, Integer, Integer>, Tuple3<Integer, Integer, Integer>> rotateZ = t -> Tuple.tuple(-t.v2, t.v1, t.v3);
        for (int i = 0; i < 4; i++) {
            for (int k = 0; k < 4; k++) {
                Function<Tuple3<Integer, Integer, Integer>, Tuple3<Integer, Integer, Integer>> f = Function.identity();
                for (int ii = 0; ii < i; ii++) {
                    f = f.andThen(rotateX);
                }
                for (int kk = 0; kk < k; kk++) {
                    f = f.andThen(rotateZ);
                }
                ROTATORS.add(f);
            }
        }
        for (int j = 0; j < 2; j++) {
            for (int k = 0; k < 4; k++) {
                Function<Tuple3<Integer, Integer, Integer>, Tuple3<Integer, Integer, Integer>> f = rotateY;
                for (int jj = 0; jj < j; jj++) {
                    f = f.andThen(rotateY).andThen(rotateY);
                }
                for (int kk = 0; kk < k; kk++) {
                    f = f.andThen(rotateZ);
                }
                ROTATORS.add(f);
            }
        }
    }

    private List<Tuple3<Integer, Integer, Integer>> normalizedScannerPositions = List.of();
    private int numberOfBeacons;

    public Object part1(List<String> input) {
        runLogic(input);
        return numberOfBeacons;
    }

    public Object part2(List<String> input) {
        runLogic(input);

        int max = 0;
        for (int i = 0; i < normalizedScannerPositions.size(); i++) {
            for (int j = i + 1; j < normalizedScannerPositions.size(); j++) {
                Tuple3<Integer, Integer, Integer> scannerA = normalizedScannerPositions.get(i);
                Tuple3<Integer, Integer, Integer> scannerB = normalizedScannerPositions.get(j);
                max = Math.max(max, Math.abs(scannerA.v1 - scannerB.v1) + Math.abs(scannerA.v2 - scannerB.v2) + Math.abs(scannerA.v3 - scannerB.v3));
            }
        }
        return max;
    }

    private void runLogic(List<String> input) {
        Map<Integer, Set<Tuple3<Integer, Integer, Integer>>> relativeBeaconPositions = parseInput(input);
        Map<Integer, Set<Tuple3<Integer, Integer, Integer>>> relativeScannerPositions = getRelativeScannerPositions(relativeBeaconPositions);

        while (relativeBeaconPositions.size() != 1) {
            Map<Integer, Tuple2<Integer, Function<Tuple3<Integer, Integer, Integer>, Tuple3<Integer, Integer, Integer>>>> transformationMap = getTransformationMap(relativeBeaconPositions);
            relativeBeaconPositions = mergePositionsByTransformation(relativeBeaconPositions, transformationMap);
            relativeScannerPositions = mergePositionsByTransformation(relativeScannerPositions, transformationMap);
        }

        numberOfBeacons = relativeBeaconPositions.values().stream().findAny().orElseThrow().size();
        normalizedScannerPositions = new ArrayList<>(relativeScannerPositions.values().stream().findAny().orElseThrow());
    }

    private Map<Integer, Set<Tuple3<Integer, Integer, Integer>>> getRelativeScannerPositions(Map<Integer, Set<Tuple3<Integer, Integer, Integer>>> relativeBeaconPositions) {
        Map<Integer, Set<Tuple3<Integer, Integer, Integer>>> relativeScannerPositions = new HashMap<>();
        for (int i = 0; i < relativeBeaconPositions.size(); i++) {
            relativeScannerPositions.put(i, Set.of(Tuple.tuple(0,0,0)));
        }
        return relativeScannerPositions;
    }

    private Map<Integer, Set<Tuple3<Integer, Integer, Integer>>> parseInput(List<String> input) {
        Map<Integer, Set<Tuple3<Integer, Integer, Integer>>> beaconPositionsPerScanner = new HashMap<>();
        Set<Tuple3<Integer, Integer, Integer>> beacons = new HashSet<>();
        int counter = 0;
        for (String line : input) {
            if (line.startsWith("--- scanner")) {
                beacons = new HashSet<>();
                beaconPositionsPerScanner.put(counter, beacons);
                counter++;
            } else if (!line.isBlank()) {
                String[] positionSplitString = line.split(",");
                int x = Integer.parseInt(positionSplitString[0]);
                int y = Integer.parseInt(positionSplitString[1]);
                int z = Integer.parseInt(positionSplitString[2]);
                beacons.add(Tuple.tuple(x, y, z));
            }
        }
        return beaconPositionsPerScanner;
    }

    private Map<Integer, Tuple2<Integer, Function<Tuple3<Integer, Integer, Integer>, Tuple3<Integer, Integer, Integer>>>> getTransformationMap(Map<Integer, Set<Tuple3<Integer, Integer, Integer>>> relativeBeaconPositions) {
        Map<Integer, Tuple2<Integer, Function<Tuple3<Integer, Integer, Integer>, Tuple3<Integer, Integer, Integer>>>> map = new HashMap<>();
        outer:
        for (int i : relativeBeaconPositions.keySet()) {
            for (int j : relativeBeaconPositions.keySet()) {
                if (i != j) {
                    Set<Tuple3<Integer, Integer, Integer>> positions1 = relativeBeaconPositions.get(i);
                    Set<Tuple3<Integer, Integer, Integer>> positions2 = relativeBeaconPositions.get(j);
                    for (Function<Tuple3<Integer, Integer, Integer>, Tuple3<Integer, Integer, Integer>> rotator : ROTATORS) {
                        Set<Tuple3<Integer, Integer, Integer>> positions1Rotated = positions1.stream().map(rotator).collect(Collectors.toSet());
                        for (Tuple3<Integer, Integer, Integer> pos2 : positions2) {
                            for (Tuple3<Integer, Integer, Integer> pos1Rotated : positions1Rotated) {
                                Tuple3<Integer, Integer, Integer> offset = Tuple.tuple(pos2.v1 - pos1Rotated.v1, pos2.v2 - pos1Rotated.v2, pos2.v3 - pos1Rotated.v3);
                                int matchCounter = 1;
                                for (Tuple3<Integer, Integer, Integer> pos1RotatedToGetOffsetPosition : positions1Rotated) {
                                    Tuple3<Integer, Integer, Integer> possiblePosition2 = Tuple.tuple(pos1RotatedToGetOffsetPosition.v1 + offset.v1, pos1RotatedToGetOffsetPosition.v2 + offset.v2, pos1RotatedToGetOffsetPosition.v3 + offset.v3);
                                    if (positions2.contains(possiblePosition2)) {
                                        matchCounter++;
                                        if (matchCounter == REQUIRED_MATCHES) {
                                            break;
                                        }
                                    }
                                }
                                if (matchCounter >= REQUIRED_MATCHES) {
                                    map.put(i, Tuple.tuple(j, rotator.andThen(pos -> Tuple.tuple(pos.v1 + offset.v1, pos.v2 + offset.v2, pos.v3 + offset.v3))));
                                    continue outer;
                                }
                            }
                        }
                    }
                }
            }
        }
        return map;
    }

    private Map<Integer, Set<Tuple3<Integer, Integer, Integer>>> mergePositionsByTransformation(Map<Integer, Set<Tuple3<Integer, Integer, Integer>>> relativePositions, Map<Integer, Tuple2<Integer, Function<Tuple3<Integer, Integer, Integer>, Tuple3<Integer, Integer, Integer>>>> map) {
        boolean changeHappened = true;
        while (relativePositions.size() > 1 && changeHappened) {
            changeHappened = false;
            Map<Integer, Set<Tuple3<Integer, Integer, Integer>>> newPositions = new HashMap<>();
            for (int posId : relativePositions.keySet()) {
                Set<Tuple3<Integer, Integer, Integer>> positions = relativePositions.get(posId);
                Tuple2<Integer, Function<Tuple3<Integer, Integer, Integer>, Tuple3<Integer, Integer, Integer>>> transformationTarget = map.get(posId);
                Integer targetPosId = transformationTarget.v1;
                if (posId < targetPosId || posId != map.get(targetPosId).v1) {
                    Set<Tuple3<Integer, Integer, Integer>> transformedPositions = positions.stream().map(transformationTarget.v2).collect(Collectors.toSet());
                    newPositions.putIfAbsent(transformationTarget.v1, new HashSet<>());
                    newPositions.get(transformationTarget.v1).addAll(transformedPositions);
                    changeHappened = true;
                } else {
                    newPositions.putIfAbsent(posId, new HashSet<>());
                    newPositions.get(posId).addAll(positions);
                }
            }
            relativePositions = newPositions;
        }
        return relativePositions;
    }


}
