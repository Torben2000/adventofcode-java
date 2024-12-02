package de.beachboys.aoc2024;

import de.beachboys.Day;
import de.beachboys.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Day02 extends Day {

    public Object part1(List<String> input) {
        return runLogic(input, Day02::isReportSafePart1);
    }

    public Object part2(List<String> input) {
        return runLogic(input, Day02::isReportSafePart2);
    }

    private static long runLogic(List<String> input, Predicate<List<Integer>> checkForSafeReport) {
        long result = 0;
        for (String line : input) {
            List<Integer> report = Util.parseToIntList(line, " ");
            if (checkForSafeReport.test(report)) {
                result++;
            }
        }
        return result;
    }

    private static boolean isReportSafePart2(List<Integer> report) {
        boolean safeReport = isReportSafePart1(report);
        if (!safeReport) {
            for (int i = 0; i < report.size(); i++) {
                List<Integer> reportWithOneMissingLevel = new ArrayList<>(report);
                reportWithOneMissingLevel.remove(i);
                if (isReportSafePart1(reportWithOneMissingLevel)) {
                    safeReport = true;
                    break;
                }
            }
        }
        return safeReport;
    }

    private static boolean isReportSafePart1(List<Integer> report) {
        boolean allIncreasing = true;
        boolean allDecreasing = true;
        boolean allSafeDifference = true;
        for (int i = 0; i < report.size() - 1; i++) {
            int first = report.get(i);
            int second = report.get(i+1);
            if (first <= second) {
                allDecreasing = false;
            }
            if (first >= second) {
                allIncreasing = false;
            }
            int difference = Math.abs(first - second);
            if (difference < 1 || difference > 3) {
                allSafeDifference = false;
            }
        }
        return (allIncreasing || allDecreasing) && allSafeDifference;
    }

}
