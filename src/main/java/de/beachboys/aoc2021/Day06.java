package de.beachboys.aoc2021;

import de.beachboys.Day;
import de.beachboys.Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day06 extends Day {

    public Object part1(List<String> input) {
        return runLogic(input, 80);
    }

    public Object part2(List<String> input) {
        return runLogic(input, 256);
    }

    private long runLogic(List<String> input, int days) {
        List<Integer> initialState = Util.parseIntCsv(input.get(0));
        Map<Integer, Long> timerToLanternFishCount = new HashMap<>();
        for (Integer i : initialState) {
            timerToLanternFishCount.put(i, timerToLanternFishCount.getOrDefault(i, 0L) + 1);
        }

        for (int i = 0; i < days; i++) {
            Map<Integer, Long> newTimerToLanternFishCount = new HashMap<>();
            newTimerToLanternFishCount.put(8, timerToLanternFishCount.getOrDefault(0, 0L));
            newTimerToLanternFishCount.put(6, timerToLanternFishCount.getOrDefault(0, 0L));
            for (int j = 1; j < 9; j++) {
                newTimerToLanternFishCount.put(j - 1, newTimerToLanternFishCount.getOrDefault(j - 1, 0L) + timerToLanternFishCount.getOrDefault(j, 0L));
            }
            timerToLanternFishCount = newTimerToLanternFishCount;
        }

        return timerToLanternFishCount.values().stream().mapToLong(aLong -> aLong).sum();
    }

}
