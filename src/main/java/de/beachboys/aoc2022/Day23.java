package de.beachboys.aoc2022;

import de.beachboys.Day;
import de.beachboys.Direction;
import de.beachboys.Util;
import org.javatuples.Pair;

import java.util.*;

public class Day23 extends Day {

    public Object part1(List<String> input) {
        return getNumberOfEmptySpacesOrNumberOfRoundsAfterWhichMovementStopped(input, 10);
    }
    public Object part2(List<String> input) {
        return getNumberOfEmptySpacesOrNumberOfRoundsAfterWhichMovementStopped(input, Integer.MAX_VALUE);
    }


    private int getNumberOfEmptySpacesOrNumberOfRoundsAfterWhichMovementStopped(List<String> input, int rounds) {
        Set<Pair<Integer, Integer>> setOfElves = Util.buildConwaySet(input, "#");
        Deque<Direction> directionsToCheck = new LinkedList<>();
        directionsToCheck.add(Direction.NORTH);
        directionsToCheck.add(Direction.SOUTH);
        directionsToCheck.add(Direction.WEST);
        directionsToCheck.add(Direction.EAST);

        for (int round = 1; round <= rounds; round++) {
            Set<Pair<Integer, Integer>> newSet = new HashSet<>();
            Map<Pair<Integer, Integer>, Set<Pair<Integer, Integer>>> proposalsToOriginalPositions = new HashMap<>();
            for (Pair<Integer, Integer> elf : setOfElves) {
                boolean hasOtherElfAround = hasOtherElfAround(setOfElves, elf);
                boolean proposesMove = false;
                if (hasOtherElfAround) {
                    for (Direction direction : directionsToCheck) {
                        if (!proposesMove && hasNoElvesInDirection(setOfElves, elf, direction)) {
                            Pair<Integer, Integer> proposal = direction.move(elf, 1);
                            Set<Pair<Integer, Integer>> originalPositionsForProposal = proposalsToOriginalPositions.getOrDefault(proposal, new HashSet<>());
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
            for (Map.Entry<Pair<Integer, Integer>, Set<Pair<Integer, Integer>>> proposalToOriginalPositions : proposalsToOriginalPositions.entrySet()) {
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


        int minX= setOfElves.stream().mapToInt(Pair::getValue0).min().orElseThrow();
        int maxX= setOfElves.stream().mapToInt(Pair::getValue0).max().orElseThrow();
        int minY= setOfElves.stream().mapToInt(Pair::getValue1).min().orElseThrow();
        int maxY= setOfElves.stream().mapToInt(Pair::getValue1).max().orElseThrow();
        return (maxX - minX + 1) * (maxY - minY + 1) - setOfElves.size();
    }

    private static boolean hasOtherElfAround(Set<Pair<Integer, Integer>> setOfElves, Pair<Integer, Integer> elf) {
        for (int x = elf.getValue0() - 1; x <= elf.getValue0() + 1 ; x++) {
            for (int y = elf.getValue1() - 1; y <= elf.getValue1() + 1; y++) {
                if ((x != elf.getValue0() || y != elf.getValue1()) && setOfElves.contains(Pair.with(x, y))) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean hasNoElvesInDirection(Set<Pair<Integer, Integer>> setOfElves, Pair<Integer, Integer> elf, Direction direction) {
        Pair<Integer, Integer> straight = direction.move(elf, 1);
        Pair<Integer, Integer> diagonal1;
        Pair<Integer, Integer> diagonal2;
        if (straight.getValue0().equals(elf.getValue0())) {
            diagonal1 = Pair.with(straight.getValue0() - 1, straight.getValue1());
            diagonal2 = Pair.with(straight.getValue0() + 1, straight.getValue1());
        } else {
            diagonal1 = Pair.with(straight.getValue0(), straight.getValue1() - 1);
            diagonal2 = Pair.with(straight.getValue0(), straight.getValue1() + 1);
        }
        return !setOfElves.contains(straight) && !setOfElves.contains(diagonal1) && !setOfElves.contains(diagonal2);
    }

}
