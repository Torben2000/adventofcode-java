package de.beachboys.aoc2024;

import de.beachboys.Day;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day25 extends Day {

    private final List<Set<Tuple2<Integer, Integer>>> keys = new ArrayList<>();
    private final List<Set<Tuple2<Integer, Integer>>> locks = new ArrayList<>();

    public Object part1(List<String> input) {
        parse(input);

        long result = 0;
        for (Set<Tuple2<Integer, Integer>> key : keys) {
            for (Set<Tuple2<Integer, Integer>> lock : locks) {
                if (isKeyFittingIntoLock(key, lock)) {
                    result++;
                }
            }
        }
        return result;
    }

    private static boolean isKeyFittingIntoLock(Set<Tuple2<Integer, Integer>> key, Set<Tuple2<Integer, Integer>> lock) {
        HashSet<Tuple2<Integer, Integer>> keyAndLockOverlap = new HashSet<>(lock);
        keyAndLockOverlap.retainAll(key);
        return keyAndLockOverlap.isEmpty();
    }

    private void parse(List<String> input) {
        keys.clear();
        locks.clear();
        int startOfNextLockOrKey = 0;
        for (int i = 0; i < input.size(); i++) {
            String line = input.get(i);
            if (line.isEmpty()) {
                parseLockOrKey(input, startOfNextLockOrKey, i);
                startOfNextLockOrKey = i+1;
            }
        }
        parseLockOrKey(input, startOfNextLockOrKey, input.size());
    }

    private void parseLockOrKey(List<String> input, int fromIndex, int toIndex) {
        List<String> lockOrKeyInput = input.subList(fromIndex, toIndex);
        Set<Tuple2<Integer, Integer>> lockOrKey = Util.buildConwaySet(lockOrKeyInput, "#");
        if (lockOrKey.stream().filter(t -> t.v2 == 0).count() == 5) {
            locks.add(lockOrKey);
        } else {
            keys.add(lockOrKey);
        }
    }

    public Object part2(List<String> input) {
        return "There is no puzzle! :-)";
    }

}
