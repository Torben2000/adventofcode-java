package de.beachboys.aoc2025;

import de.beachboys.Day;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Day05 extends Day {

    public Object part1(List<String> input) {
        long result = 0;
        List<Tuple2<Long, Long>> ranges = new ArrayList<>();
        for (String line : input) {
            if (line.contains("-")) {
                String[] split = line.split(Pattern.quote("-"));
                ranges.add(Tuple.tuple(Long.parseLong(split[0]), Long.parseLong(split[1])));
            } else if (!line.isEmpty()){
                long id = Long.parseLong(line);
                for (Tuple2<Long, Long> range : ranges) {
                    if (isInRange(range, id)) {
                        result++;
                        break;
                    }
                }
            }
        }
        return result;
    }

    public Object part2(List<String> input) {
        List<Tuple2<Long, Long>> ranges = new ArrayList<>();
        for (String line : input) {
            if (line.contains("-")) {
                String[] split = line.split(Pattern.quote("-"));
                ranges.add(Tuple.tuple(Long.parseLong(split[0]), Long.parseLong(split[1])));
            }
        }

        List<Tuple2<Long, Long>> mergedRanges = new ArrayList<>();
        for (int i = 0; i < ranges.size(); i++) {
            boolean mergedIntoOtherRange = false;
            Tuple2<Long, Long> range = ranges.get(i);
            for (int j = i + 1; j < ranges.size(); j++) {
                Tuple2<Long, Long> otherRange = ranges.get(j);
                if (rangesIntersect(range, otherRange)) {
                    Tuple2<Long, Long> mergedRange = Tuple.tuple(Math.min(range.v1, otherRange.v1), Math.max(range.v2, otherRange.v2));
                    ranges.set(j, mergedRange);
                    mergedIntoOtherRange = true;
                    break;
                }

            }
            if (!mergedIntoOtherRange) {
                mergedRanges.add(range);
            }
        }

        long result = 0;
        for (Tuple2<Long, Long> range : mergedRanges) {
            result += range.v2 - range.v1+1;
        }
        return result;
    }

    private static boolean isInRange(Tuple2<Long, Long> range, long id) {
        return id >= range.v1 && id <= range.v2;
    }

    private static boolean rangesIntersect(Tuple2<Long, Long> range1, Tuple2<Long, Long> range2) {
        return range1.v1 <= range2.v2 && range2.v1 <= range1.v2;
    }

}
