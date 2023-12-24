package de.beachboys.aoc2020;

import de.beachboys.Day;
import de.beachboys.Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day15 extends Day {

    public Object part1(List<String> input) {
        List<Long> initialNumberList = Util.parseLongCsv(input.getFirst());
        return getLastNumberSpoken(initialNumberList, 2020);
    }

    public Object part2(List<String> input) {
        List<Long> initialNumberList = Util.parseLongCsv(input.getFirst());
        return getLastNumberSpoken(initialNumberList, 30000000);
    }

    protected long getLastNumberSpoken(List<Long> initialNumberList, int numberOfTurns) {
        Map<Long, Integer> numberToLastTurn = new HashMap<>();
        for (int i = 1; i < initialNumberList.size(); i++) {
            numberToLastTurn.put(initialNumberList.get(i - 1), i);
        }
        long lastNumber = initialNumberList.getLast();
        for (int i = initialNumberList.size(); i < numberOfTurns; i++) {
            long newNumber = 0;
            if (numberToLastTurn.containsKey(lastNumber)) {
                int lastTurnNumberWasSpoken = numberToLastTurn.get(lastNumber);
                newNumber = i - lastTurnNumberWasSpoken;
            }
            numberToLastTurn.put(lastNumber, i);
            lastNumber = newNumber;
        }
        return lastNumber;
    }

}
