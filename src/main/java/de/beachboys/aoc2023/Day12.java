package de.beachboys.aoc2023;

import de.beachboys.Day;
import de.beachboys.Util;
import org.javatuples.Triplet;

import java.util.*;

public class Day12 extends Day {

    public Object part1(List<String> input) {
        long result = 0;

        for (String line : input) {
            String[] recordsAndDamagedGroups = line.split(" ");
            List<Integer> damagedGroups = Util.parseIntCsv(recordsAndDamagedGroups[1]);
            result += getNumberOfPossibleArrangements(recordsAndDamagedGroups[0], damagedGroups);
        }

        return result;
    }


    public Object part2(List<String> input) {
        long result = 0;

        for (String line : input) {
            String[] recordsAndDamagedGroupsSimple = line.split(" ");
            List<Integer> damagedGroupsSimple = Util.parseIntCsv(recordsAndDamagedGroupsSimple[1]);
            StringBuilder recordsStringBuilder = new StringBuilder();
            List<Integer> damagedGroups = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                recordsStringBuilder.append(recordsAndDamagedGroupsSimple[0]).append("?");
                damagedGroups.addAll(damagedGroupsSimple);
            }
            String records = recordsStringBuilder.substring(0, recordsStringBuilder.length() - 1);
            result += getNumberOfPossibleArrangements(records, damagedGroups);
        }

        return result;
    }

    private final Map<Triplet<Integer, Integer, Integer>, Long> cache = new HashMap<>();

    private long getNumberOfPossibleArrangements(String records, List<Integer> damagedGroups) {
        cache.clear();
        return getNumberOfPossibleArrangements(records, damagedGroups, 0, 0, 0);
    }
    private long getNumberOfPossibleArrangements(String records, List<Integer> damagedGroups, int currentRecordIndex, int currentGroupIndex, int currentDamagedCounter) {
        Triplet<Integer, Integer, Integer> key = Triplet.with(currentRecordIndex, currentGroupIndex, currentDamagedCounter);
        if (cache.containsKey(key)) {
            return cache.get(key);
        }

        long currentResult = 0;
        if (currentRecordIndex == records.length()) {
            if (currentGroupIndex == damagedGroups.size() && currentDamagedCounter == 0
                    || currentGroupIndex + 1 == damagedGroups.size() && currentDamagedCounter == damagedGroups.get(currentGroupIndex)) {
                currentResult = 1;
            }
        } else {
            char record = records.charAt(currentRecordIndex);
            if (record != '.') {
                currentResult += getNumberOfPossibleArrangements(records, damagedGroups, currentRecordIndex + 1, currentGroupIndex, currentDamagedCounter + 1);
            }
            if (record != '#') {
                if (currentDamagedCounter == 0) {
                    currentResult += getNumberOfPossibleArrangements(records, damagedGroups, currentRecordIndex + 1, currentGroupIndex, currentDamagedCounter);
                } else if (damagedGroups.size() > currentGroupIndex && damagedGroups.get(currentGroupIndex) == currentDamagedCounter){
                    currentResult += getNumberOfPossibleArrangements(records, damagedGroups, currentRecordIndex + 1, currentGroupIndex + 1, 0);
                }
            }
        }
        cache.put(key, currentResult);
        return currentResult;
    }
}
