package de.beachboys.aoc2024;

import de.beachboys.Day;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day11 extends Day {

    private final Map<Tuple2<Long, Integer>, Long> cache = new HashMap<>();

    public Object part1(List<String> input) {
        return runLogic(input, 25);
    }

    public Object part2(List<String> input) {
        return runLogic(input, 75);
    }

    private long runLogic(List<String> input, int numberOfBlinks) {
        long result = 0;
        List<Long> stones = Util.parseToLongList(input.getFirst(), " ");
        for (Long stone : stones) {
            result+= getNumberOfStones(stone, numberOfBlinks);
        }
        return result;
    }

    private long getNumberOfStones(long stone, int numberOfBlinks) {
        Tuple2<Long, Integer> cacheKey = Tuple.tuple(stone, numberOfBlinks);
        if (!cache.containsKey(cacheKey)) {
            long numberOfStones;
            if (numberOfBlinks == 0) {
                numberOfStones = 1;
            } else if (stone == 0L) {
                numberOfStones = getNumberOfStones(1L, numberOfBlinks - 1);
            } else {
                String s = String.valueOf(stone);
                if (s.length() % 2 == 0) {
                    numberOfStones = getNumberOfStones(Long.parseLong(s.substring(0, s.length() / 2)), numberOfBlinks - 1)
                            + getNumberOfStones(Long.parseLong(s.substring(s.length() / 2)), numberOfBlinks - 1);

                } else {
                    numberOfStones = getNumberOfStones(stone * 2024, numberOfBlinks - 1);
                }
            }
            cache.put(cacheKey, numberOfStones);
        }
        return cache.get(cacheKey);
    }

}
