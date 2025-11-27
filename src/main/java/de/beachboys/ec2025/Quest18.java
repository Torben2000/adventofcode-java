package de.beachboys.ec2025;

import de.beachboys.Quest;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.*;

public class Quest18 extends Quest {

    private final Map<Integer, Integer> plantsToThickness = new HashMap<>();
    private final Map<Integer, Set<Tuple2<Integer, Integer>>> plantsToBranches = new HashMap<>();
    private final Set<Integer> plantsWithFreeBranches = new HashSet<>();
    private final List<List<Integer>> testCases = new ArrayList<>();
    private final Map<Integer, Long> energyCache = new HashMap<>();

    @Override
    public Object part1(List<String> input) {
        parseInput(input);
        return getLastPlantEnergy(null);
    }

    @Override
    public Object part2(List<String> input) {
        parseInput(input);

        long result = 0;
        for (List<Integer> testCase : testCases) {
            result += getLastPlantEnergy(testCase);
        }
        return result;
    }

    @Override
    public Object part3(List<String> input) {
        parseInput(input);

        long maxEnergy = 0;
        for (List<Integer> possibleMaxEnergyTestCase : getPossibleMaxEnergyTestCases()) {
            maxEnergy = Math.max(maxEnergy, getLastPlantEnergy(possibleMaxEnergyTestCase));
        }

        long testCaseEnergySum = 0;
        int numTestCasesWithEnergy = 0;
        for (List<Integer> testCase : testCases) {
            long val = getLastPlantEnergy(testCase);
            if (val > 0) {
                testCaseEnergySum += val;
                numTestCasesWithEnergy++;
            }
        }
        return numTestCasesWithEnergy * maxEnergy - testCaseEnergySum;
    }

    private long getLastPlantEnergy(List<Integer> optionalTestCase) {
        energyCache.clear();
        return getEnergy(plantsToThickness.size(), optionalTestCase);
    }

    private long getEnergy(int plant, List<Integer> optionalTestCase) {
        if (energyCache.containsKey(plant)) {
            return energyCache.get(plant);
        }
        if (plantsWithFreeBranches.contains(plant)) {
            return optionalTestCase == null ? 1 : optionalTestCase.get(plant - 1);
        }
        long energy = 0;
        for (Tuple2<Integer, Integer> branch : plantsToBranches.get(plant)) {
            energy += getEnergy(branch.v1, optionalTestCase) * branch.v2;
        }
        if (energy < plantsToThickness.get(plant)) {
            energy = 0;
        }
        energyCache.put(plant, energy);
        return energy;
    }

    private List<List<Integer>> getPossibleMaxEnergyTestCases() {
        List<Integer> maxEnergyTestCasePattern = new ArrayList<>(plantsWithFreeBranches.size());
        for (int plantWithFreeBranch : plantsWithFreeBranches.stream().sorted().toList()) {
            maxEnergyTestCasePattern.add(plantWithFreeBranch - 1, getTestCaseValueForPlant(plantWithFreeBranch));
        }

        List<List<Integer>> possibleMaxEnergyTestCases = new ArrayList<>();
        possibleMaxEnergyTestCases.add(maxEnergyTestCasePattern);
        for (int i = 0; i < plantsWithFreeBranches.size(); i++) {
            if (maxEnergyTestCasePattern.get(i) == null) {
                List<List<Integer>> newPossibleMaxEnergyTestCases = new ArrayList<>();
                for (List<Integer> currentMaxEnergyTestCasePattern : possibleMaxEnergyTestCases) {
                    List<Integer> newMaxEnergyTestCase0 = new ArrayList<>(currentMaxEnergyTestCasePattern);
                    newMaxEnergyTestCase0.set(i, 0);
                    newPossibleMaxEnergyTestCases.add(newMaxEnergyTestCase0);
                    List<Integer> newMaxEnergyTestCase1 = new ArrayList<>(currentMaxEnergyTestCasePattern);
                    newMaxEnergyTestCase1.set(i, 1);
                    newPossibleMaxEnergyTestCases.add(newMaxEnergyTestCase1);
                }
                possibleMaxEnergyTestCases = newPossibleMaxEnergyTestCases;
            }
        }
        return possibleMaxEnergyTestCases;
    }

    private Integer getTestCaseValueForPlant(int plantWithFreeBranch) {
        Integer value = -1;
        for (Set<Tuple2<Integer, Integer>> branches : plantsToBranches.values()) {
            for (Tuple2<Integer, Integer> branch : branches) {
                if (branch.v1 == plantWithFreeBranch) {
                    if (branch.v2 > 0) {
                        if (value == null || value == 0) {
                            value = null;
                        } else {
                            value = 1;
                        }
                    } else {
                        if (value == null || value == 1) {
                            value = null;
                        } else {
                            value = 0;
                        }

                    }
                }
            }
        }
        return value;
    }

    private void parseInput(List<String> input) {
        plantsToThickness.clear();
        plantsWithFreeBranches.clear();
        plantsToBranches.clear();
        testCases.clear();

        int currentPlant = 0;
        for (String line : input) {
            if (line.startsWith("Plant")) {
                String[] split = line.split(" ");
                currentPlant = Integer.parseInt(split[1]);
                int thickness = Integer.parseInt(split[4].substring(0, split[4].length() - 1));
                plantsToThickness.put(currentPlant, thickness);
            } else if (line.startsWith("- free")) {
                plantsWithFreeBranches.add(currentPlant);
            } else if (line.startsWith("- branch")) {
                String[] split = line.split(" ");
                int otherPlant = Integer.parseInt(split[4]);
                int thickness = Integer.parseInt(split[7]);
                Set<Tuple2<Integer, Integer>> branches = plantsToBranches.getOrDefault(currentPlant, new HashSet<>());
                branches.add(Tuple.tuple(otherPlant, thickness));
                plantsToBranches.put(currentPlant, branches);
            } else if (line.startsWith("1") || line.startsWith("0")) {
                testCases.add(Util.parseToIntList(line, " "));
            }
        }
    }

}
