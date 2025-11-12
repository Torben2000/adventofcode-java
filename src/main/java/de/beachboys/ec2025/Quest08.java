package de.beachboys.ec2025;

import de.beachboys.Quest;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Quest08 extends Quest {

    @Override
    public Object part1(List<String> input) {
        List<Integer> nails = Util.parseIntCsv(input.getFirst());
        int maxNail = nails.stream().mapToInt(Integer::intValue).max().orElseThrow();

        long result = 0;
        for (int i = 0; i < nails.size() - 1; i++) {
            if (Math.abs(nails.get(i) - nails.get(i + 1)) == maxNail/2) {
                result++;
            }
        }
        return result;
    }

    @Override
    public Object part2(List<String> input) {
        List<Integer> nails = Util.parseIntCsv(input.getFirst());
        List<Tuple2<Integer, Integer>> lines = new ArrayList<>();

        long result = 0;
        for (int i = 0; i < nails.size() - 1; i++) {
            Tuple2<Integer, Integer> newLine = getLineWithSortedNails(nails.get(i), nails.get(i + 1));
            for (Tuple2<Integer, Integer> existingLine : lines) {
                if (cutsEachOther(existingLine, newLine)) {
                    result++;
                }
            }
            lines.add(newLine);
        }
        return result;
    }

    @Override
    public Object part3(List<String> input) {
        List<Integer> nails = Util.parseIntCsv(input.getFirst());
        int maxNail = nails.stream().mapToInt(Integer::intValue).max().orElseThrow();

        List<Tuple2<Integer, Integer>> lines = new ArrayList<>();
        for (int j = 0; j < nails.size() - 1; j++) {
            lines.add(getLineWithSortedNails(nails.get(j), nails.get(j + 1)));
        }

        long maxCuts = 0;
        for (int j = 1; j < maxNail; j++) {
            for (int k = j+1; k <= maxNail; k++) {
                maxCuts = Math.max(maxCuts, getCuts(j, k, lines));
            }
        }
        return maxCuts;
    }

    private static long getCuts(int fromNail, int toNail, List<Tuple2<Integer, Integer>> lines) {
        Tuple2<Integer, Integer> lineToCut = getLineWithSortedNails(fromNail, toNail);
        long cuts = 0;
        for (Tuple2<Integer, Integer> line : lines) {
            if (isSameLine(lineToCut, line) || cutsEachOther(lineToCut, line)) {
                cuts++;
            }
        }
        return cuts;
    }

    private static boolean isSameLine(Tuple2<Integer, Integer> line1, Tuple2<Integer, Integer> line2) {
        return Objects.equals(line1.v1, line2.v1) && Objects.equals(line1.v2, line2.v2);
    }

    private static boolean cutsEachOther(Tuple2<Integer, Integer> line1, Tuple2<Integer, Integer> line2) {
        return line2.v1 > line1.v1 && line2.v1 < line1.v2 && line2.v2 > line1.v2
                || line2.v1 < line1.v1 && line2.v2 > line1.v1 && line2.v2 < line1.v2;
    }

    private static Tuple2<Integer, Integer> getLineWithSortedNails(int nail1, int nail2) {
        int firstNail = nail1;
        int secondNail = nail2;
        if (nail1 > nail2) {
            firstNail = nail2;
            secondNail = nail1;
        }
        return Tuple.tuple(firstNail, secondNail);
    }
}
