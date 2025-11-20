package de.beachboys.ec2025;

import de.beachboys.Direction;
import de.beachboys.Quest;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.*;

public class Quest14 extends Quest {

    @Override
    public Object part1(List<String> input) {
        return part1And2(input, 10);
    }

    @Override
    public Object part2(List<String> input) {
        return part1And2(input, 2025);
    }

    @Override
    public Object part3(List<String> input) {
        int xMax = 34;
        int yMax = 34;
        Set<Tuple2<Integer, Integer>> patternRaw = Util.buildConwaySet(input, "#");
        Set<Tuple2<Integer, Integer>> activePattern = new HashSet<>();
        Set<Tuple2<Integer, Integer>> inactivePattern = new HashSet<>();
        int xOffset = (xMax - input.getFirst().length()) / 2;
        int yOffset = (yMax - input.size()) / 2;
        for (int x = 0; x < input.getFirst().length(); x++) {
            for (int y = 0; y < input.size(); y++) {
                if (patternRaw.contains(Tuple.tuple(x, y))) {
                    activePattern.add(Tuple.tuple(x + xOffset, y + yOffset));
                } else {
                    inactivePattern.add(Tuple.tuple(x + xOffset, y + yOffset));
                }
            }

        }


        Map<Integer, Integer> activeTilesInMatchingRound = new HashMap<>();
        Map<Set<Tuple2<Integer, Integer>>, Integer> history = new HashMap<>();
        Set<Tuple2<Integer, Integer>> state = new HashSet<>();
        int totalRounds = 1000000000;

        for (int currentRound = 1; currentRound <= totalRounds; currentRound++) {
            state = runRound(state, xMax, yMax);

            if (isPatternMatch(state, activePattern, inactivePattern)) {
                activeTilesInMatchingRound.put(currentRound, state.size());
            }

            if (history.containsKey(state)) {
                long firstOccurrence = history.get(state);
                long cycleLength = currentRound - firstOccurrence;
                long cycles = (totalRounds - firstOccurrence) / cycleLength;
                long indexToUse = totalRounds - (cycles * cycleLength);

                long beforeFirstOccurrence = 0;
                long inCycle = 0;
                long afterCycle = 0;
                for (int round : activeTilesInMatchingRound.keySet()) {
                    int value = activeTilesInMatchingRound.get(round);
                    if (round < firstOccurrence) {
                        beforeFirstOccurrence += value;
                    } else {
                        inCycle += value;
                        if (round <= indexToUse) {
                            afterCycle += value;
                        }
                    }
                }
                return beforeFirstOccurrence + cycles * inCycle + afterCycle;
            }
            history.put(state, currentRound);
        }
        return activeTilesInMatchingRound.values().stream().mapToInt(Integer::intValue).sum();
    }

    private static long part1And2(List<String> input, int numRounds) {
        int xMax = input.getFirst().length();
        int yMax = input.size();
        Set<Tuple2<Integer, Integer>> state = Util.buildConwaySet(input, "#");
        long result = 0;
        for (int i = 0; i < numRounds; i++) {
            state = runRound(state, xMax, yMax);
            result += state.size();
        }
        return result;
    }

    private static Set<Tuple2<Integer, Integer>> runRound(Set<Tuple2<Integer, Integer>> state, int xMax, int yMax) {
        Set<Tuple2<Integer, Integer>> newSet = new HashSet<>();
        for (int x = 0; x < xMax; x++) {
            for (int y = 0; y < yMax; y++) {
                Tuple2<Integer, Integer> pos = Tuple.tuple(x, y);
                boolean oddDiagonalNeigbourCount = false;
                for (Direction dir : Direction.values()) {
                    if (state.contains(dir.moveDiagonallyLeft(pos, 1))) {
                        oddDiagonalNeigbourCount = !oddDiagonalNeigbourCount;
                    }
                }
                if (state.contains(pos) && oddDiagonalNeigbourCount
                        || !state.contains(pos) && !oddDiagonalNeigbourCount) {
                    newSet.add(pos);
                }
            }
        }
        return newSet;
    }

    private static boolean isPatternMatch(Set<Tuple2<Integer, Integer>> state, Set<Tuple2<Integer, Integer>> activePattern, Set<Tuple2<Integer, Integer>> inactivePattern) {
        boolean match = true;
        for (Tuple2<Integer, Integer> tile : activePattern) {
            if (!state.contains(tile)) {
                match = false;
                break;
            }
        }
        if (match) {
            for (Tuple2<Integer, Integer> tile : inactivePattern) {
                if (state.contains(tile)){
                    match = false;
                    break;
                }
            }
        }
        return match;
    }
}
