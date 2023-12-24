package de.beachboys.aoc2015;

import de.beachboys.Day;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Day24 extends Day {

    private List<Long> allPackages;

    private final List<List<Long>> possibilitiesForFirstDepartment = new ArrayList<>();

    private long weightPerDepartment;

    private int minFirstDepartmentPackageCount = Integer.MAX_VALUE;

    public Object part1(List<String> input) {
        int numberOfDepartments = 3;
        return runLogic(input, numberOfDepartments);
    }

    public Object part2(List<String> input) {
        return runLogic(input, 4);
    }

    private Object runLogic(List<String> input, int numberOfDepartments) {
        allPackages = input.stream().map(Long::valueOf).sorted(Comparator.reverseOrder()).toList();

        weightPerDepartment = allPackages.stream().mapToLong(Long::longValue).sum() / numberOfDepartments;

        fillPossibilitiesForFirstDepartment(List.of(), allPackages, numberOfDepartments);

        return possibilitiesForFirstDepartment.stream().filter(list -> list.size() == minFirstDepartmentPackageCount).mapToLong(list -> list.stream().reduce(1L, (a, b) -> a * b)).min().orElseThrow();
    }

    private void fillPossibilitiesForFirstDepartment(List<Long> packagesInFirstDepartment, List<Long> possiblePackages, int totalDepartments) {
        if (possiblePackages.isEmpty() || minFirstDepartmentPackageCount < packagesInFirstDepartment.size()) {
            return;
        }
        Long currentPackage = possiblePackages.getFirst();
        List<Long> packagesWithoutCurrentPackage = possiblePackages.subList(1, possiblePackages.size());

        List<Long> newPackagesInFirstDepartment = new ArrayList<>(packagesInFirstDepartment);
        newPackagesInFirstDepartment.add(currentPackage);
        long newDepartmentWeight = newPackagesInFirstDepartment.stream().mapToLong(Long::longValue).sum();
        if (weightPerDepartment == newDepartmentWeight) {
            List<Long> remainingPackages = new ArrayList<>(allPackages);
            remainingPackages.removeAll(newPackagesInFirstDepartment);
            if (isPossibleToDistribute(List.of(), remainingPackages, newPackagesInFirstDepartment, totalDepartments - 1)) {
                possibilitiesForFirstDepartment.add(newPackagesInFirstDepartment);
                minFirstDepartmentPackageCount = Math.min(minFirstDepartmentPackageCount, newPackagesInFirstDepartment.size());
            }
        } else if (weightPerDepartment > newDepartmentWeight){
            fillPossibilitiesForFirstDepartment(newPackagesInFirstDepartment, packagesWithoutCurrentPackage, totalDepartments);
        }

        fillPossibilitiesForFirstDepartment(packagesInFirstDepartment, packagesWithoutCurrentPackage, totalDepartments);
    }

    private boolean isPossibleToDistribute(List<Long> packagesInDepartment, List<Long> possiblePackages, List<Long> packagesInEarlierDepartments, int remainingDepartments) {
        if (possiblePackages.isEmpty()) {
            return false;
        }
        Long currentPackage = possiblePackages.getFirst();
        List<Long> packagesWithoutCurrentPackage = possiblePackages.subList(1, possiblePackages.size());


        List<Long> newPackagesInDepartment = new ArrayList<>(packagesInDepartment);
        newPackagesInDepartment.add(currentPackage);
        long newDepartmentWeight = newPackagesInDepartment.stream().mapToLong(Long::longValue).sum();
        if (weightPerDepartment == newDepartmentWeight) {
            if (remainingDepartments == 2) {
                return true;
            }
            List<Long> newPackagesInEarlierDepartments = new ArrayList<>(packagesInEarlierDepartments);
            newPackagesInEarlierDepartments.addAll(newPackagesInDepartment);
            List<Long> newPossiblePackages = new ArrayList<>(allPackages);
            newPossiblePackages.removeAll(newPackagesInEarlierDepartments);
            if (isPossibleToDistribute(List.of(), newPossiblePackages, newPackagesInEarlierDepartments, remainingDepartments - 1)) {
                return true;
            }
        } else if (weightPerDepartment > newDepartmentWeight){
            if (isPossibleToDistribute(newPackagesInDepartment, packagesWithoutCurrentPackage, packagesInEarlierDepartments, remainingDepartments)) {
                return true;
            }
        }

        return isPossibleToDistribute(packagesInDepartment, packagesWithoutCurrentPackage, packagesInEarlierDepartments, remainingDepartments);
    }

}
