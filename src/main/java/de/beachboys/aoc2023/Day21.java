package de.beachboys.aoc2023;

import de.beachboys.Day;
import de.beachboys.Direction;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.*;
import java.util.stream.Collectors;

public class Day21 extends Day {

    private Map<Tuple2<Integer, Integer>, String> map;
    private Tuple2<Integer, Integer> startPos;
    private int widthHeight;
    private Set<Tuple2<Integer, Integer>> gardenPlots;
    private int numOfReachablePositionsWithEvenCoordinateSum;
    private int numOfReachablePositionsWithOddCoordinateSum;

    public Object part1(List<String> input) {
        return runLogic(input, 64);
    }

    public Object part2(List<String> input) {
        return runLogic(input, 26501365);
    }

    private long runLogic(List<String> input, int totalStepsDefaultValue) {
        int totalSteps = Util.getIntValueFromUser("Exact total steps", totalStepsDefaultValue, io);
        parseInput(input);

        if (totalSteps <= 100) {
            return getReachablePlotsAndRequiredMinSteps(startPos, totalSteps, false).v1.size();
        }

        int distanceInAreas = totalSteps / widthHeight;
        int remainingSteps = totalSteps - distanceInAreas * widthHeight;

        Map<String, Set<Tuple2<Tuple2<Integer, Integer>, Integer>>> startingPoints = initStartingPointsWithDistanceFromStartByDirection();
        Map<Tuple2<String, Integer>, Integer> reachablePlotsInEdgeAreasByDirectionAndDifferenceToDistance = new HashMap<>();
        for (String direction : startingPoints.keySet()) {
            Set<Tuple2<Tuple2<Integer, Integer>, Integer>> startPlotsWithDistanceFromStart = startingPoints.get(direction);
            for (int i = 0; i < 3; i++) {
                Set<Tuple2<Integer, Integer>> reachablePlots = new HashSet<>();
                for (Tuple2<Tuple2<Integer, Integer>, Integer> startPlotWithDistanceFromStart : startPlotsWithDistanceFromStart) {
                    int stepsInArea = remainingSteps - startPlotWithDistanceFromStart.v2 + i * widthHeight;
                    if (stepsInArea < 0) {
                        continue;
                    }
                    reachablePlots.addAll(getReachablePlotsAndRequiredMinSteps(startPlotWithDistanceFromStart.v1, stepsInArea, true).v1);
                }
                if (!reachablePlots.isEmpty()) {
                    reachablePlotsInEdgeAreasByDirectionAndDifferenceToDistance.put(Tuple.tuple(direction, i), reachablePlots.size());
                }
            }
        }

        return getResult(reachablePlotsInEdgeAreasByDirectionAndDifferenceToDistance, distanceInAreas, totalSteps);
    }

    private long getResult(Map<Tuple2<String, Integer>, Integer> reachablePlotsInEdgeAreasByDirectionAndDifferenceToDistance, int distanceInAreas, int totalSteps) {
        long result = getReachablePlotsFromEdgeAreas(reachablePlotsInEdgeAreasByDirectionAndDifferenceToDistance, distanceInAreas);
        result += getReachablePlotsFromFullyVisitedAreas(distanceInAreas, totalSteps);
        return result;
    }

    private static long getReachablePlotsFromEdgeAreas(Map<Tuple2<String, Integer>, Integer> reachablePlotsInEdgeAreasByDirectionAndDifferenceToDistance, int distanceInAreas) {
        long result = 0;
        for (Tuple2<String, Integer> directionAndDifferenceToDistance : reachablePlotsInEdgeAreasByDirectionAndDifferenceToDistance.keySet()) {
            int value = reachablePlotsInEdgeAreasByDirectionAndDifferenceToDistance.get(directionAndDifferenceToDistance);
            if (directionAndDifferenceToDistance.v1.length() == 1) {
                // n, e, s, w
                result += value;
            } else {
                // ne, nw, se, sw
                result += (long) (distanceInAreas + 1 - directionAndDifferenceToDistance.v2) * value;
            }
        }
        return result;
    }

    private long getReachablePlotsFromFullyVisitedAreas(int distanceInAreas, int totalSteps) {
        long evenCount = 1;
        long oddCount = 0;
        for (int i = 0; i <= distanceInAreas - 1; i++) {
            if (i % 2 == 0) {
                evenCount += i * 4L;
            } else {
                oddCount += i * 4L;
            }
        }
        // remove the 4 tips as they are already handled by the edges (and in fact in some of the examples they are not fully filled)
        if (evenCount > oddCount) {
            evenCount -= 4;
        } else{
            oddCount -= 4;
        }
        long result = 0;
        if (totalSteps % 2 == 0) {
            result += evenCount * numOfReachablePositionsWithEvenCoordinateSum;
            result += oddCount * numOfReachablePositionsWithOddCoordinateSum;
        } else {
            result += evenCount * numOfReachablePositionsWithOddCoordinateSum;
            result += oddCount * numOfReachablePositionsWithEvenCoordinateSum;
        }
        return result;
    }

    private Tuple2<Set<Tuple2<Integer, Integer>>, Integer> getReachablePlotsAndRequiredMinSteps(Tuple2<Integer, Integer> start, int maxTotalSteps, boolean stayInCurrentArea) {
        Set<Tuple2<Integer, Integer>> currentPlots = new HashSet<>();
        currentPlots.add(start);
        int totalStepModulo = maxTotalSteps % 2;
        int reachableSize = numOfReachablePositionsWithOddCoordinateSum;
        if (((start.v1 + start.v2) % 2) == totalStepModulo) {
            reachableSize = numOfReachablePositionsWithEvenCoordinateSum;
        }
        for (int currentStep = 1; currentStep <= maxTotalSteps; currentStep++) {
            Set<Tuple2<Integer, Integer>> newCurrentPlots = new HashSet<>();
            for (Tuple2<Integer, Integer> plot : currentPlots) {
                for (Direction dir : Direction.values()) {
                    Tuple2<Integer, Integer> newPlot = dir.move(plot, 1);
                    Tuple2<Integer, Integer> newPlotToCheck = newPlot;
                    if (!stayInCurrentArea) {
                        newPlotToCheck = Util.getNormalizedPositionOnRepeatingPattern(newPlot, widthHeight, widthHeight);
                    }
                    if (!map.getOrDefault(newPlotToCheck, "#").equals("#")) {
                        newCurrentPlots.add(newPlot);
                    }
                }
            }
            currentPlots = newCurrentPlots;
            if (stayInCurrentArea && currentStep % 2 == totalStepModulo && currentPlots.size() == reachableSize) {
                return Tuple.tuple(currentPlots, currentStep);
            }
        }
        return Tuple.tuple(currentPlots, maxTotalSteps);
    }

    private Map<String, Set<Tuple2<Tuple2<Integer, Integer>, Integer>>> initStartingPointsWithDistanceFromStartByDirection() {
        Map<String, Set<Tuple2<Tuple2<Integer, Integer>, Integer>>> startingPoints = new HashMap<>();
        if (gardenPlots.stream().filter(p -> p.v1.equals(startPos.v1)).count() < widthHeight) {
            // examples
            startingPoints.put("w", Set.of(Tuple.tuple(Tuple.tuple(widthHeight - 1, 0), widthHeight), Tuple.tuple(Tuple.tuple(widthHeight - 1, widthHeight - 1), widthHeight)));
            startingPoints.put("e", Set.of(Tuple.tuple(Tuple.tuple(0, 0), widthHeight), Tuple.tuple(Tuple.tuple(0, widthHeight - 1), 15)));
            startingPoints.put("n", Set.of(Tuple.tuple(Tuple.tuple(0, widthHeight - 1), widthHeight), Tuple.tuple(Tuple.tuple(widthHeight - 1, widthHeight - 1), widthHeight)));
            startingPoints.put("s", Set.of(Tuple.tuple(Tuple.tuple(0, 0), widthHeight), Tuple.tuple(Tuple.tuple(widthHeight - 1, 0), 15)));
            startingPoints.put("nw", Set.of(Tuple.tuple(Tuple.tuple(widthHeight - 1, widthHeight - 1), widthHeight +1)));
            startingPoints.put("ne", Set.of(Tuple.tuple(Tuple.tuple(0, widthHeight - 1), widthHeight +1)));
            startingPoints.put("sw", Set.of(Tuple.tuple(Tuple.tuple(widthHeight - 1, 0), widthHeight +1)));
            startingPoints.put("se", Set.of(Tuple.tuple(Tuple.tuple(0, 0), 16)));
        } else {
            // real input
            startingPoints.put("w", Set.of(Tuple.tuple(Tuple.tuple(widthHeight - 1, (widthHeight - 1) / 2), (widthHeight + 1) / 2)));
            startingPoints.put("e", Set.of(Tuple.tuple(Tuple.tuple(0, (widthHeight - 1) / 2), (widthHeight + 1) / 2)));
            startingPoints.put("n", Set.of(Tuple.tuple(Tuple.tuple((widthHeight - 1) / 2, widthHeight - 1), (widthHeight + 1) / 2)));
            startingPoints.put("s", Set.of(Tuple.tuple(Tuple.tuple((widthHeight - 1) / 2, 0), (widthHeight + 1) / 2)));
            startingPoints.put("nw", Set.of(Tuple.tuple(Tuple.tuple(widthHeight - 1, widthHeight - 1), widthHeight +1)));
            startingPoints.put("ne", Set.of(Tuple.tuple(Tuple.tuple(0, widthHeight - 1), widthHeight +1)));
            startingPoints.put("sw", Set.of(Tuple.tuple(Tuple.tuple(widthHeight - 1, 0), widthHeight +1)));
            startingPoints.put("se", Set.of(Tuple.tuple(Tuple.tuple(0, 0), widthHeight +1)));
        }
        return startingPoints;
    }

    private void parseInput(List<String> input) {
        map = Util.buildImageMap(input);
        startPos = map.entrySet().stream().filter(e->e.getValue().equals("S")).map(Map.Entry::getKey).findFirst().orElseThrow();
        map.put(startPos, ".");

        gardenPlots = map.entrySet().stream().filter(e->e.getValue().equals(".")).map(Map.Entry::getKey).collect(Collectors.toSet());
        Set<Tuple2<Integer, Integer>> unreachableGardenPlots = new HashSet<>();
        for (Tuple2<Integer, Integer> pos : gardenPlots) {
            boolean isolated = true;
            for (Direction dir : Direction.values()) {
                if (map.getOrDefault(dir.move(pos, 1), ".").equals(".")) {
                    isolated = false;
                    break;
                }
            }
            if (isolated) {
                unreachableGardenPlots.add(pos);
            }
        }
        if (!unreachableGardenPlots.isEmpty()) {
            gardenPlots = new HashSet<>(gardenPlots);
            gardenPlots.removeAll(unreachableGardenPlots);
        }

        widthHeight = input.getFirst().length();
        if (widthHeight != input.size()) {
            throw new IllegalArgumentException();
        }

        Set<Tuple2<Integer, Integer>> reachableEven = gardenPlots.stream().filter(p -> (p.v1 + p.v2) % 2 == 0).collect(Collectors.toSet());
        Set<Tuple2<Integer, Integer>> reachableOdd = gardenPlots.stream().filter(p -> (p.v1 + p.v2) % 2 == 1).collect(Collectors.toSet());
        numOfReachablePositionsWithEvenCoordinateSum = reachableEven.size();
        numOfReachablePositionsWithOddCoordinateSum = reachableOdd.size();
    }

}
