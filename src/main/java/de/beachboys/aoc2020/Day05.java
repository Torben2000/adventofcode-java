package de.beachboys.aoc2020;

import de.beachboys.Day;

import java.util.List;
import java.util.TreeSet;

public class Day05 extends Day {

    private final TreeSet<Integer> seatIds = new TreeSet<>();

    public Object part1(List<String> input) {
        buildSeatIdTreeMap(input);

        return seatIds.last();
    }

    public Object part2(List<String> input) {
        buildSeatIdTreeMap(input);

        int oldSeatId = Integer.MIN_VALUE;
        for (Integer currentSeatId : seatIds) {
            if ((oldSeatId + 2) == currentSeatId) {
                return currentSeatId - 1;
            }
            oldSeatId = currentSeatId;
        }
        return Integer.MIN_VALUE;
    }

    private void buildSeatIdTreeMap(List<String> input) {
        for (String seatString : input) {
            String binaryString = seatString.replaceAll("F", "0").replaceAll("B", "1")
                    .replaceAll("L", "0").replaceAll("R", "1");
            seatIds.add(Integer.parseInt(binaryString, 2));
        }
        io.logDebug(seatIds);
    }

}
