package de.beachboys.ec2025;

import de.beachboys.Quest;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.LinkedList;
import java.util.List;

public class Quest13 extends Quest {

    @Override
    public Object part1(List<String> input) {
        List<Integer> numberList = new LinkedList<>();
        numberList.add(1);
        int dialPosition = 0;

        boolean addRight = true;
        for (String line : input) {
            if (addRight) {
                numberList.add(Integer.parseInt(line));
            } else {
                numberList.addFirst(Integer.parseInt(line));
                dialPosition++;
            }
            addRight = !addRight;
        }

        dialPosition += 2025;
        return numberList.get(dialPosition % numberList.size());
    }

    @Override
    public Object part2(List<String> input) {
    return part2And3(input, 20252025L);
    }

    @Override
    public Object part3(List<String> input) {
        return part2And3(input, 202520252025L);
    }

    private static long part2And3(List<String> input, long numberOfPositionTurns) {
        List<Tuple2<Integer, Integer>> numberRanges = new LinkedList<>();
        numberRanges.add(Tuple.tuple(1, 1));
        long initialDialRangePosition = 0;
        long numberCount = 1;

        boolean addRight = true;
        for (String line : input) {
            String[] split = line.split("-");
            int left = Integer.parseInt(split[0]);
            int right = Integer.parseInt(split[1]);
            if (addRight) {
                numberRanges.add(Tuple.tuple(left, right));
            } else {
                numberRanges.addFirst(Tuple.tuple(right, left));
                initialDialRangePosition += 1;
            }
            addRight = !addRight;
            numberCount += right - left + 1;
        }

        long numberPositionFromCurrentRange = 0;
        numberPositionFromCurrentRange += numberOfPositionTurns;
        numberPositionFromCurrentRange = (numberPositionFromCurrentRange % numberCount);

        for (int i = 0; i < numberRanges.size(); i++) {
            Tuple2<Integer, Integer> currentRange = numberRanges.get((int) ((i + initialDialRangePosition) % numberRanges.size()));
            int rangeSize = Math.abs(currentRange.v2 - currentRange.v1) + 1;
            if (numberPositionFromCurrentRange < rangeSize) {
                if (currentRange.v2 > currentRange.v1) {
                    return currentRange.v1 + numberPositionFromCurrentRange;
                } else {
                    return currentRange.v1 - numberPositionFromCurrentRange;
                }
            }
            numberPositionFromCurrentRange -= rangeSize;
        }
        throw new IllegalArgumentException();
    }
}
