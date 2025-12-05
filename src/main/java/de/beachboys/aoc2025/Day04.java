package de.beachboys.aoc2025;

import de.beachboys.Day;
import de.beachboys.Direction;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple2;

import java.util.*;

public class Day04 extends Day {

    public Object part1(List<String> input) {
        long result = 0;
        Set<Tuple2<Integer, Integer>> setOfRolls = Util.buildConwaySet(input, "@");
        for (Tuple2<Integer, Integer> roll : setOfRolls) {
            int neighnoringRolls = 0;
            for (Tuple2<Integer, Integer> directNeighbor : Direction.getDirectNeighbors(roll, true)) {
                if (setOfRolls.contains(directNeighbor)) {
                    neighnoringRolls++;
                }
            }
            if (neighnoringRolls < 4) {
                result++;
            }
        }
        return result;
    }

    public Object part2(List<String> input) {
        long result = 0;
        Set<Tuple2<Integer, Integer>> setOfRolls = Util.buildConwaySet(input, "@");
        boolean rollsRemoved = true;
        while (rollsRemoved) {
            rollsRemoved = false;
            Set<Tuple2<Integer, Integer>> newSet = new HashSet<>();
            for (Tuple2<Integer, Integer> pos : setOfRolls) {
                if (setOfRolls.contains(pos)) {
                    int neightboringRolls = 0;
                    for (Tuple2<Integer, Integer> directNeighbor : Direction.getDirectNeighbors(pos, true)) {
                        if (setOfRolls.contains(directNeighbor)) {
                            neightboringRolls++;
                        }
                    }
                    if (neightboringRolls < 4) {
                        result++;
                        rollsRemoved = true;
                    } else {
                        newSet.add(pos);
                    }
                }
            }
            setOfRolls = newSet;
        }
        return result;
    }

}
