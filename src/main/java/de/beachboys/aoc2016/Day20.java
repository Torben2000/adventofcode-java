package de.beachboys.aoc2016;

import de.beachboys.Day;
import de.beachboys.Util;
import org.javatuples.Pair;

import java.util.*;

public class Day20 extends Day {

    final TreeMap<Long, Pair<Long, Long>> lowerBounds = new TreeMap<>();
    final TreeMap<Long, Pair<Long, Long>> upperBounds = new TreeMap<>();

    public Object part1(List<String> input) {
        fillTreeMaps(input);
        return lowerBounds.getOrDefault(0L, Pair.with(-1L, -1L)).getValue1() + 1;
    }

    public Object part2(List<String> input) {
        fillTreeMaps(input);
        long maxIp = Util.getLongValueFromUser("Maximum IP", 4294967295L, io);
        Long ipCount = lowerBounds.firstKey();
        for (Long upperBound : upperBounds.keySet()) {
            if (upperBound < maxIp) {
                Long nextRangeLowerBound = lowerBounds.ceilingKey(upperBound);
                if (nextRangeLowerBound == null) {
                    nextRangeLowerBound = maxIp + 1;
                }
                ipCount += nextRangeLowerBound - upperBound - 1;
            }
        }
        return ipCount;
    }

    private void fillTreeMaps(List<String> input) {
        for (String line : input) {
            Pair<Long, Long> range = parseLine(line);
            Map.Entry<Long, Pair<Long, Long>> lowerEntryComparedToUpperBound = lowerBounds.floorEntry(range.getValue1());
            Map.Entry<Long, Pair<Long, Long>> upperEntryComparedToLowerBound = upperBounds.ceilingEntry(range.getValue0());
            if (lowerEntryComparedToUpperBound == null || upperEntryComparedToLowerBound == null || lowerEntryComparedToUpperBound.getValue().getValue1() < range.getValue0()) {
                addToMaps(range);
            } else {
                replaceRangesWithMergedRange(getRangesToMerge(range, lowerEntryComparedToUpperBound, upperEntryComparedToLowerBound));
            }
        }
        mergeConnectedRanges();
    }

    private List<Pair<Long, Long>> getRangesToMerge(Pair<Long, Long> range, Map.Entry<Long, Pair<Long, Long>> lowerEntryComparedToUpperBound, Map.Entry<Long, Pair<Long, Long>> upperEntryComparedToLowerBound) {
        List<Pair<Long, Long>> rangesToMerge = new ArrayList<>(List.of(range, upperEntryComparedToLowerBound.getValue()));
        Map.Entry<Long, Pair<Long, Long>> currentEntry = lowerEntryComparedToUpperBound;
        while (currentEntry != null && !currentEntry.getValue().equals(upperEntryComparedToLowerBound.getValue())) {
            rangesToMerge.add(currentEntry.getValue());
            currentEntry = lowerBounds.lowerEntry(currentEntry.getKey());
        }
        return rangesToMerge;
    }

    private void replaceRangesWithMergedRange(List<Pair<Long, Long>> ranges) {
        Pair<Long, Long> mergedRange = ranges.get(0);
        for (int i = 1; i < ranges.size(); i++) {
            mergedRange = replaceRangesWithMergedRange(mergedRange, ranges.get(i));
        }
    }

    private Pair<Long, Long> replaceRangesWithMergedRange(Pair<Long, Long> range1, Pair<Long, Long> range2) {
        Pair<Long, Long> newEntry = combineRanges(range1, range2);
        removeFromMaps(range1);
        removeFromMaps(range2);
        addToMaps(newEntry);
        return newEntry;
    }

    private Pair<Long, Long> combineRanges(Pair<Long, Long> range1, Pair<Long, Long> range2) {
        return Pair.with(Math.min(range1.getValue0(), range2.getValue0()), Math.max(range1.getValue1(), range2.getValue1()));
    }

    private void removeFromMaps(Pair<Long, Long> range) {
        lowerBounds.remove(range.getValue0());
        upperBounds.remove(range.getValue1());
    }

    private void addToMaps(Pair<Long, Long> range) {
        lowerBounds.put(range.getValue0(), range);
        upperBounds.put(range.getValue1(), range);
    }

    private void mergeConnectedRanges() {
        List<Long> lowerBoundsToMerge = new ArrayList<>();
        for (Long lowerBound : lowerBounds.keySet()) {
            if (upperBounds.containsKey(lowerBound - 1)) {
                lowerBoundsToMerge.add(lowerBound);
            }
        }
        for (Long lowerBound : lowerBoundsToMerge) {
            replaceRangesWithMergedRange(lowerBounds.get(lowerBound), upperBounds.get(lowerBound - 1));
        }
    }

    private Pair<Long, Long> parseLine(String line) {
        String[] bounds = line.split("-");
        return Pair.with(Long.valueOf(bounds[0]), Long.valueOf(bounds[1]));
    }

}
