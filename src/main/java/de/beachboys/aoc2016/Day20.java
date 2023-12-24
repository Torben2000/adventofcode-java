package de.beachboys.aoc2016;

import de.beachboys.Day;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Day20 extends Day {

    final TreeMap<Long, Tuple2<Long, Long>> lowerBounds = new TreeMap<>();
    final TreeMap<Long, Tuple2<Long, Long>> upperBounds = new TreeMap<>();

    public Object part1(List<String> input) {
        fillTreeMaps(input);
        return lowerBounds.getOrDefault(0L, Tuple.tuple(-1L, -1L)).v2 + 1;
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
            Tuple2<Long, Long> range = parseLine(line);
            Map.Entry<Long, Tuple2<Long, Long>> lowerEntryComparedToUpperBound = lowerBounds.floorEntry(range.v2);
            Map.Entry<Long, Tuple2<Long, Long>> upperEntryComparedToLowerBound = upperBounds.ceilingEntry(range.v1);
            if (lowerEntryComparedToUpperBound == null || upperEntryComparedToLowerBound == null || lowerEntryComparedToUpperBound.getValue().v2 < range.v1) {
                addToMaps(range);
            } else {
                replaceRangesWithMergedRange(getRangesToMerge(range, lowerEntryComparedToUpperBound, upperEntryComparedToLowerBound));
            }
        }
        mergeConnectedRanges();
    }

    private List<Tuple2<Long, Long>> getRangesToMerge(Tuple2<Long, Long> range, Map.Entry<Long, Tuple2<Long, Long>> lowerEntryComparedToUpperBound, Map.Entry<Long, Tuple2<Long, Long>> upperEntryComparedToLowerBound) {
        List<Tuple2<Long, Long>> rangesToMerge = new ArrayList<>(List.of(range, upperEntryComparedToLowerBound.getValue()));
        Map.Entry<Long, Tuple2<Long, Long>> currentEntry = lowerEntryComparedToUpperBound;
        while (currentEntry != null && !currentEntry.getValue().equals(upperEntryComparedToLowerBound.getValue())) {
            rangesToMerge.add(currentEntry.getValue());
            currentEntry = lowerBounds.lowerEntry(currentEntry.getKey());
        }
        return rangesToMerge;
    }

    private void replaceRangesWithMergedRange(List<Tuple2<Long, Long>> ranges) {
        Tuple2<Long, Long> mergedRange = ranges.getFirst();
        for (int i = 1; i < ranges.size(); i++) {
            mergedRange = replaceRangesWithMergedRange(mergedRange, ranges.get(i));
        }
    }

    private Tuple2<Long, Long> replaceRangesWithMergedRange(Tuple2<Long, Long> range1, Tuple2<Long, Long> range2) {
        Tuple2<Long, Long> newEntry = combineRanges(range1, range2);
        removeFromMaps(range1);
        removeFromMaps(range2);
        addToMaps(newEntry);
        return newEntry;
    }

    private Tuple2<Long, Long> combineRanges(Tuple2<Long, Long> range1, Tuple2<Long, Long> range2) {
        return Tuple.tuple(Math.min(range1.v1, range2.v1), Math.max(range1.v2, range2.v2));
    }

    private void removeFromMaps(Tuple2<Long, Long> range) {
        lowerBounds.remove(range.v1);
        upperBounds.remove(range.v2);
    }

    private void addToMaps(Tuple2<Long, Long> range) {
        lowerBounds.put(range.v1, range);
        upperBounds.put(range.v2, range);
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

    private Tuple2<Long, Long> parseLine(String line) {
        String[] bounds = line.split("-");
        return Tuple.tuple(Long.valueOf(bounds[0]), Long.valueOf(bounds[1]));
    }

}
