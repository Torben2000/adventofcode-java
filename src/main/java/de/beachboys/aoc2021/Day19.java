package de.beachboys.aoc2021;

import de.beachboys.Day;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day19 extends Day {

    private static final int REQUIRED_MATCHES = 12;
    private static final List<Function<Triplet<Integer, Integer, Integer>, Triplet<Integer, Integer, Integer>>> ROTATORS = new ArrayList<>();

    static {
        Function<Triplet<Integer, Integer, Integer>, Triplet<Integer, Integer, Integer>> rotateX = t -> Triplet.with(t.getValue0(), -t.getValue2(), t.getValue1());
        Function<Triplet<Integer, Integer, Integer>, Triplet<Integer, Integer, Integer>> rotateY = t -> Triplet.with(-t.getValue2(), t.getValue1(), t.getValue0());
        Function<Triplet<Integer, Integer, Integer>, Triplet<Integer, Integer, Integer>> rotateZ = t -> Triplet.with(-t.getValue1(), t.getValue0(), t.getValue2());
        for (int i = 0; i < 4; i++) {
            for (int k = 0; k < 4; k++) {
                Function<Triplet<Integer, Integer, Integer>, Triplet<Integer, Integer, Integer>> f = Function.identity();
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
                Function<Triplet<Integer, Integer, Integer>, Triplet<Integer, Integer, Integer>> f = rotateY;
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

    private List<Triplet<Integer, Integer, Integer>> normalizedScannerPositions = List.of();
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
                Triplet<Integer, Integer, Integer> scannerA = normalizedScannerPositions.get(i);
                Triplet<Integer, Integer, Integer> scannerB = normalizedScannerPositions.get(j);
                max = Math.max(max, Math.abs(scannerA.getValue0() - scannerB.getValue0()) + Math.abs(scannerA.getValue1() - scannerB.getValue1()) + Math.abs(scannerA.getValue2() - scannerB.getValue2()));
            }
        }
        return max;
    }

    private void runLogic(List<String> input) {
        Map<Integer, Set<Triplet<Integer, Integer, Integer>>> relativeBeaconPositions = parseInput(input);
        Map<Integer, Set<Triplet<Integer, Integer, Integer>>> relativeScannerPositions = getRelativeScannerPositions(relativeBeaconPositions);

        while (relativeBeaconPositions.size() != 1) {
            Map<Integer, Pair<Integer, Function<Triplet<Integer, Integer, Integer>, Triplet<Integer, Integer, Integer>>>> transformationMap = getTransformationMap(relativeBeaconPositions);
            relativeBeaconPositions = mergePositionsByTransformation(relativeBeaconPositions, transformationMap);
            relativeScannerPositions = mergePositionsByTransformation(relativeScannerPositions, transformationMap);
        }

        numberOfBeacons = relativeBeaconPositions.values().stream().findAny().orElseThrow().size();
        normalizedScannerPositions = new ArrayList<>(relativeScannerPositions.values().stream().findAny().orElseThrow());
    }

    private Map<Integer, Set<Triplet<Integer, Integer, Integer>>> getRelativeScannerPositions(Map<Integer, Set<Triplet<Integer, Integer, Integer>>> relativeBeaconPositions) {
        Map<Integer, Set<Triplet<Integer, Integer, Integer>>> relativeScannerPositions = new HashMap<>();
        for (int i = 0; i < relativeBeaconPositions.size(); i++) {
            relativeScannerPositions.put(i, Set.of(Triplet.with(0,0,0)));
        }
        return relativeScannerPositions;
    }

    private Map<Integer, Set<Triplet<Integer, Integer, Integer>>> parseInput(List<String> input) {
        Map<Integer, Set<Triplet<Integer, Integer, Integer>>> beaconPositionsPerScanner = new HashMap<>();
        Set<Triplet<Integer, Integer, Integer>> beacons = new HashSet<>();
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
                beacons.add(Triplet.with(x, y, z));
            }
        }
        return beaconPositionsPerScanner;
    }

    private Map<Integer, Pair<Integer, Function<Triplet<Integer, Integer, Integer>, Triplet<Integer, Integer, Integer>>>> getTransformationMap(Map<Integer, Set<Triplet<Integer, Integer, Integer>>> relativeBeaconPositions) {
        Map<Integer, Pair<Integer, Function<Triplet<Integer, Integer, Integer>, Triplet<Integer, Integer, Integer>>>> map = new HashMap<>();
        outer:
        for (int i : relativeBeaconPositions.keySet()) {
            for (int j : relativeBeaconPositions.keySet()) {
                if (i != j) {
                    Set<Triplet<Integer, Integer, Integer>> positions1 = relativeBeaconPositions.get(i);
                    Set<Triplet<Integer, Integer, Integer>> positions2 = relativeBeaconPositions.get(j);
                    for (Function<Triplet<Integer, Integer, Integer>, Triplet<Integer, Integer, Integer>> rotator : ROTATORS) {
                        Set<Triplet<Integer, Integer, Integer>> positions1Rotated = positions1.stream().map(rotator).collect(Collectors.toSet());
                        for (Triplet<Integer, Integer, Integer> pos2 : positions2) {
                            for (Triplet<Integer, Integer, Integer> pos1Rotated : positions1Rotated) {
                                Triplet<Integer, Integer, Integer> offset = Triplet.with(pos2.getValue0() - pos1Rotated.getValue0(), pos2.getValue1() - pos1Rotated.getValue1(), pos2.getValue2() - pos1Rotated.getValue2());
                                int matchCounter = 1;
                                for (Triplet<Integer, Integer, Integer> pos1RotatedToGetOffsetPosition : positions1Rotated) {
                                    Triplet<Integer, Integer, Integer> possiblePosition2 = Triplet.with(pos1RotatedToGetOffsetPosition.getValue0() + offset.getValue0(), pos1RotatedToGetOffsetPosition.getValue1() + offset.getValue1(), pos1RotatedToGetOffsetPosition.getValue2() + offset.getValue2());
                                    if (positions2.contains(possiblePosition2)) {
                                        matchCounter++;
                                        if (matchCounter == REQUIRED_MATCHES) {
                                            break;
                                        }
                                    }
                                }
                                if (matchCounter >= REQUIRED_MATCHES) {
                                    map.put(i, Pair.with(j, rotator.andThen(pos -> Triplet.with(pos.getValue0() + offset.getValue0(), pos.getValue1() + offset.getValue1(), pos.getValue2() + offset.getValue2()))));
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

    private Map<Integer, Set<Triplet<Integer, Integer, Integer>>> mergePositionsByTransformation(Map<Integer, Set<Triplet<Integer, Integer, Integer>>> relativePositions, Map<Integer, Pair<Integer, Function<Triplet<Integer, Integer, Integer>, Triplet<Integer, Integer, Integer>>>> map) {
        boolean changeHappened = true;
        while (relativePositions.size() > 1 && changeHappened) {
            changeHappened = false;
            Map<Integer, Set<Triplet<Integer, Integer, Integer>>> newPositions = new HashMap<>();
            for (int posId : relativePositions.keySet()) {
                Set<Triplet<Integer, Integer, Integer>> positions = relativePositions.get(posId);
                Pair<Integer, Function<Triplet<Integer, Integer, Integer>, Triplet<Integer, Integer, Integer>>> transformationTarget = map.get(posId);
                Integer targetPosId = transformationTarget.getValue0();
                if (posId < targetPosId || posId != map.get(targetPosId).getValue0()) {
                    Set<Triplet<Integer, Integer, Integer>> transformedPositions = positions.stream().map(pos -> transformationTarget.getValue1().apply(pos)).collect(Collectors.toSet());
                    newPositions.putIfAbsent(transformationTarget.getValue0(), new HashSet<>());
                    newPositions.get(transformationTarget.getValue0()).addAll(transformedPositions);
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
