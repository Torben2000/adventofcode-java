package de.beachboys.aoc2016;

import de.beachboys.Day;
import de.beachboys.Direction;
import de.beachboys.Util;
import org.javatuples.Pair;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.function.Predicate;

public class Day17 extends Day {

    public Object part1(List<String> input) {
        Predicate<String> handleMovementValueAndDecideIfResult = movement -> true;
        return runLogic(input, handleMovementValueAndDecideIfResult);
    }

    public Object part2(List<String> input) {
        Set<Integer> numberOfStepsForPossibilities = new HashSet<>();
        Predicate<String> handleMovementValueAndDecideIfResult = movement -> {
            numberOfStepsForPossibilities.add(movement.length());
            return false;
        };
        runLogic(input, handleMovementValueAndDecideIfResult);
        return numberOfStepsForPossibilities.stream().mapToInt(Integer::intValue).max().orElseThrow();
    }

    private String runLogic(List<String> input, Predicate<String> handleMovementValueAndDecideIfResult) {
        String prefix = input.get(0);
        MessageDigest md5 = getMd5MessageDigest();
        Map<Direction, String> movementMap = Map.of(Direction.NORTH, "U", Direction.SOUTH, "D", Direction.WEST, "L", Direction.EAST, "R");
        Deque<Pair<Pair<Integer, Integer>, String>> queue = new LinkedList<>();

        queue.add(Pair.with(Pair.with(0, 0), ""));
        while (!queue.isEmpty()) {
            Pair<Pair<Integer, Integer>, String> queueEntry = queue.poll();
            String hashValue = Util.bytesToHex(md5.digest((prefix + queueEntry.getValue1()).getBytes()));
            for (int i = 0; i < 4; i++) {
                if (hashValue.charAt(i) > 'a') {
                    Direction direction = Direction.values()[i];
                    Pair<Integer, Integer> target = direction.move(queueEntry.getValue0(), 1);
                    String movement = queueEntry.getValue1() + movementMap.get(direction);
                    if (target.getValue0() == 3 && target.getValue1() == 3) {
                        if (handleMovementValueAndDecideIfResult.test(movement)) {
                            return movement;
                        }
                    } else if (target.getValue0() >= 0 && target.getValue1() >= 0 && target.getValue0() <= 3 && target.getValue1() <= 3) {
                        queue.add(Pair.with(target, movement));
                    }
                }
            }
        }
        return null;
    }

    private MessageDigest getMd5MessageDigest() {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException();
        }
        return md5;
    }

}
