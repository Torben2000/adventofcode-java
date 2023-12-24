package de.beachboys.aoc2022;

import de.beachboys.Day;
import de.beachboys.Direction;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.*;

public class Day23 extends Day {

    public Object part1(List<String> input) {
        return getNumberOfEmptySpacesOrNumberOfRoundsAfterWhichMovementStopped(input, 10);
    }
    public Object part2(List<String> input) {
        return getNumberOfEmptySpacesOrNumberOfRoundsAfterWhichMovementStopped(input, Integer.MAX_VALUE);
    }


    private int getNumberOfEmptySpacesOrNumberOfRoundsAfterWhichMovementStopped(List<String> input, int rounds) {
        Set<Tuple2<Integer, Integer>> setOfElves = Util.buildConwaySet(input, "#");
        Deque<Direction> directionsToCheck = new LinkedList<>();
        directionsToCheck.add(Direction.NORTH);
        directionsToCheck.add(Direction.SOUTH);
        directionsToCheck.add(Direction.WEST);
        directionsToCheck.add(Direction.EAST);

        for (int round = 1; round <= rounds; round++) {
            Set<Tuple2<Integer, Integer>> newSet = new HashSet<>();
            Map<Tuple2<Integer, Integer>, Set<Tuple2<Integer, Integer>>> proposalsToOriginalPositions = new HashMap<>();
            for (Tuple2<Integer, Integer> elf : setOfElves) {
                boolean hasOtherElfAround = hasOtherElfAround(setOfElves, elf);
                boolean proposesMove = false;
                if (hasOtherElfAround) {
                    for (Direction direction : directionsToCheck) {
                        if (!proposesMove && hasNoElvesInDirection(setOfElves, elf, direction)) {
                            Tuple2<Integer, Integer> proposal = direction.move(elf, 1);
                            Set<Tuple2<Integer, Integer>> originalPositionsForProposal = proposalsToOriginalPositions.getOrDefault(proposal, new HashSet<>());
                            originalPositionsForProposal.add(elf);
                            proposalsToOriginalPositions.put(proposal, originalPositionsForProposal);
                            proposesMove = true;
                        }
                    }
                }
                if (!hasOtherElfAround || !proposesMove) {
                    newSet.add(elf);
                }
            }
            boolean elfMoved = false;
            for (Map.Entry<Tuple2<Integer, Integer>, Set<Tuple2<Integer, Integer>>> proposalToOriginalPositions : proposalsToOriginalPositions.entrySet()) {
                if (proposalToOriginalPositions.getValue().size() == 1) {
                    newSet.add(proposalToOriginalPositions.getKey());
                    elfMoved = true;
                } else {
                    newSet.addAll(proposalToOriginalPositions.getValue());
                }
            }
            if (!elfMoved) {
                return round;
            }
            setOfElves = newSet;
            directionsToCheck.add(directionsToCheck.pollFirst());
        }


        int minX= setOfElves.stream().mapToInt(Tuple2::v1).min().orElseThrow();
        int maxX= setOfElves.stream().mapToInt(Tuple2::v1).max().orElseThrow();
        int minY= setOfElves.stream().mapToInt(Tuple2::v2).min().orElseThrow();
        int maxY= setOfElves.stream().mapToInt(Tuple2::v2).max().orElseThrow();
        return (maxX - minX + 1) * (maxY - minY + 1) - setOfElves.size();
    }

    private static boolean hasOtherElfAround(Set<Tuple2<Integer, Integer>> setOfElves, Tuple2<Integer, Integer> elf) {
        for (int x = elf.v1 - 1; x <= elf.v1 + 1 ; x++) {
            for (int y = elf.v2 - 1; y <= elf.v2 + 1; y++) {
                if ((x != elf.v1 || y != elf.v2) && setOfElves.contains(Tuple.tuple(x, y))) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean hasNoElvesInDirection(Set<Tuple2<Integer, Integer>> setOfElves, Tuple2<Integer, Integer> elf, Direction direction) {
        Tuple2<Integer, Integer> straight = direction.move(elf, 1);
        Tuple2<Integer, Integer> diagonal1;
        Tuple2<Integer, Integer> diagonal2;
        if (straight.v1.equals(elf.v1)) {
            diagonal1 = Tuple.tuple(straight.v1 - 1, straight.v2);
            diagonal2 = Tuple.tuple(straight.v1 + 1, straight.v2);
        } else {
            diagonal1 = Tuple.tuple(straight.v1, straight.v2 - 1);
            diagonal2 = Tuple.tuple(straight.v1, straight.v2 + 1);
        }
        return !setOfElves.contains(straight) && !setOfElves.contains(diagonal1) && !setOfElves.contains(diagonal2);
    }

}
