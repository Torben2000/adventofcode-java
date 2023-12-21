package de.beachboys.aoc2023;

import de.beachboys.Day;
import de.beachboys.Direction;
import de.beachboys.Util;
import org.javatuples.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class Day21 extends Day {

    private Map<Pair<Integer, Integer>, String> map;
    private Pair<Integer, Integer> startPos;
    private int widthHeight;
    private Set<Pair<Integer, Integer>> gardenPlots;
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
            return getReachablePlotsAndRequiredMinSteps(startPos, totalSteps, false).getValue0().size();
        }

        int distanceInAreas = totalSteps / widthHeight;
        int remainingSteps = totalSteps - distanceInAreas * widthHeight;

        Map<String, Set<Pair<Pair<Integer, Integer>, Integer>>> startingPoints = initStartingPointsWithDistanceFromStartByDirection();
        Map<Pair<String, Integer>, Integer> reachablePlotsInEdgeAreasByDirectionAndDifferenceToDistance = new HashMap<>();
        for (String direction : startingPoints.keySet()) {
            Set<Pair<Pair<Integer, Integer>, Integer>> startPlotsWithDistanceFromStart = startingPoints.get(direction);
            for (int i = 0; i < 3; i++) {
                Set<Pair<Integer, Integer>> reachablePlots = new HashSet<>();
                for (Pair<Pair<Integer, Integer>, Integer> startPlotWithDistanceFromStart : startPlotsWithDistanceFromStart) {
                    int stepsInArea = remainingSteps - startPlotWithDistanceFromStart.getValue1() + i * widthHeight;
                    if (stepsInArea < 0) {
                        continue;
                    }
                    reachablePlots.addAll(getReachablePlotsAndRequiredMinSteps(startPlotWithDistanceFromStart.getValue0(), stepsInArea, true).getValue0());
                }
                if (!reachablePlots.isEmpty()) {
                    reachablePlotsInEdgeAreasByDirectionAndDifferenceToDistance.put(Pair.with(direction, i), reachablePlots.size());
                }
            }
        }

        return getResult(reachablePlotsInEdgeAreasByDirectionAndDifferenceToDistance, distanceInAreas, totalSteps);
    }

    private long getResult(Map<Pair<String, Integer>, Integer> reachablePlotsInEdgeAreasByDirectionAndDifferenceToDistance, int distanceInAreas, int totalSteps) {
        long result = getReachablePlotsFromEdgeAreas(reachablePlotsInEdgeAreasByDirectionAndDifferenceToDistance, distanceInAreas);
        result += getReachablePlotsFromFullyVisitedAreas(distanceInAreas, totalSteps);
        return result;
    }

    private static long getReachablePlotsFromEdgeAreas(Map<Pair<String, Integer>, Integer> reachablePlotsInEdgeAreasByDirectionAndDifferenceToDistance, int distanceInAreas) {
        long result = 0;
        for (Pair<String, Integer> directionAndDifferenceToDistance : reachablePlotsInEdgeAreasByDirectionAndDifferenceToDistance.keySet()) {
            int value = reachablePlotsInEdgeAreasByDirectionAndDifferenceToDistance.get(directionAndDifferenceToDistance);
            if (directionAndDifferenceToDistance.getValue0().length() == 1) {
                // n, e, s, w
                result += value;
            } else {
                // ne, nw, se, sw
                result += (long) (distanceInAreas + 1 - directionAndDifferenceToDistance.getValue1()) * value;
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

    private Pair<Set<Pair<Integer, Integer>>, Integer> getReachablePlotsAndRequiredMinSteps(Pair<Integer, Integer> start, int maxTotalSteps, boolean stayInCurrentArea) {
        Set<Pair<Integer, Integer>> currentPlots = new HashSet<>();
        currentPlots.add(start);
        int totalStepModulo = maxTotalSteps % 2;
        int reachableSize = numOfReachablePositionsWithOddCoordinateSum;
        if (((start.getValue0() + start.getValue1()) % 2) == totalStepModulo) {
            reachableSize = numOfReachablePositionsWithEvenCoordinateSum;
        }
        for (int currentStep = 1; currentStep <= maxTotalSteps; currentStep++) {
            Set<Pair<Integer, Integer>> newCurrentPlots = new HashSet<>();
            for (Pair<Integer, Integer> plot : currentPlots) {
                for (Direction dir : Direction.values()) {
                    Pair<Integer, Integer> newPlot = dir.move(plot, 1);
                    Pair<Integer, Integer> newPlotToCheck = newPlot;
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
                return Pair.with(currentPlots, currentStep);
            }
        }
        return Pair.with(currentPlots, maxTotalSteps);
    }

    private Map<String, Set<Pair<Pair<Integer, Integer>, Integer>>> initStartingPointsWithDistanceFromStartByDirection() {
        Map<String, Set<Pair<Pair<Integer, Integer>, Integer>>> startingPoints = new HashMap<>();
        if (gardenPlots.stream().filter(p -> p.getValue0().equals(startPos.getValue0())).count() < widthHeight) {
            // examples
            startingPoints.put("w", Set.of(Pair.with(Pair.with(widthHeight - 1, 0), widthHeight), Pair.with(Pair.with(widthHeight - 1, widthHeight - 1), widthHeight)));
            startingPoints.put("e", Set.of(Pair.with(Pair.with(0, 0), widthHeight), Pair.with(Pair.with(0, widthHeight - 1), 15)));
            startingPoints.put("n", Set.of(Pair.with(Pair.with(0, widthHeight - 1), widthHeight), Pair.with(Pair.with(widthHeight - 1, widthHeight - 1), widthHeight)));
            startingPoints.put("s", Set.of(Pair.with(Pair.with(0, 0), widthHeight), Pair.with(Pair.with(widthHeight - 1, 0), 15)));
            startingPoints.put("nw", Set.of(Pair.with(Pair.with(widthHeight - 1, widthHeight - 1), widthHeight +1)));
            startingPoints.put("ne", Set.of(Pair.with(Pair.with(0, widthHeight - 1), widthHeight +1)));
            startingPoints.put("sw", Set.of(Pair.with(Pair.with(widthHeight - 1, 0), widthHeight +1)));
            startingPoints.put("se", Set.of(Pair.with(Pair.with(0, 0), 16)));
        } else {
            // real input
            startingPoints.put("w", Set.of(Pair.with(Pair.with(widthHeight - 1, (widthHeight - 1) / 2), (widthHeight + 1) / 2)));
            startingPoints.put("e", Set.of(Pair.with(Pair.with(0, (widthHeight - 1) / 2), (widthHeight + 1) / 2)));
            startingPoints.put("n", Set.of(Pair.with(Pair.with((widthHeight - 1) / 2, widthHeight - 1), (widthHeight + 1) / 2)));
            startingPoints.put("s", Set.of(Pair.with(Pair.with((widthHeight - 1) / 2, 0), (widthHeight + 1) / 2)));
            startingPoints.put("nw", Set.of(Pair.with(Pair.with(widthHeight - 1, widthHeight - 1), widthHeight +1)));
            startingPoints.put("ne", Set.of(Pair.with(Pair.with(0, widthHeight - 1), widthHeight +1)));
            startingPoints.put("sw", Set.of(Pair.with(Pair.with(widthHeight - 1, 0), widthHeight +1)));
            startingPoints.put("se", Set.of(Pair.with(Pair.with(0, 0), widthHeight +1)));
        }
        return startingPoints;
    }

    private void parseInput(List<String> input) {
        map = Util.buildImageMap(input);
        startPos = map.entrySet().stream().filter(e->e.getValue().equals("S")).map(Map.Entry::getKey).findFirst().orElseThrow();
        map.put(startPos, ".");

        gardenPlots = map.entrySet().stream().filter(e->e.getValue().equals(".")).map(Map.Entry::getKey).collect(Collectors.toSet());
        Set<Pair<Integer, Integer>> unreachableGardenPlots = new HashSet<>();
        for (Pair<Integer, Integer> pos : gardenPlots) {
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

        Set<Pair<Integer, Integer>> reachableEven = gardenPlots.stream().filter(p -> (p.getValue0() + p.getValue1()) % 2 == 0).collect(Collectors.toSet());
        Set<Pair<Integer, Integer>> reachableOdd = gardenPlots.stream().filter(p -> (p.getValue0() + p.getValue1()) % 2 == 1).collect(Collectors.toSet());
        numOfReachablePositionsWithEvenCoordinateSum = reachableEven.size();
        numOfReachablePositionsWithOddCoordinateSum = reachableOdd.size();
    }

}
