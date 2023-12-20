package de.beachboys.aoc2023;

import de.beachboys.Day;
import de.beachboys.Util;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.*;

public class Day20 extends Day {

    private final Map<String, Pair<Character, List<String>>> rules = new HashMap<>();
    private final Map<String, Set<String>> inputs = new HashMap<>();
    private long high;
    private long low;
    private final Map<String, Integer> highPulseRoundMemory = new HashMap<>();
    private final Map<String, Boolean> stateFlipFlop = new HashMap<>();
    private final Map<String, Map<String, Boolean>> stateConjunction = new HashMap<>();

    public Object part1(List<String> input) {
        parseInput(input);
        runLogic(1000);
        return high * low;
    }

    public Object part2(List<String> input) {
        parseInput(input);
        int numOfButtonPresses = 100;
        while (numOfButtonPresses < 10000000) {
            runLogic(numOfButtonPresses);
            if (highPulseRoundMemory.isEmpty()) {
                return "no rx";
            }
            long returnValue = highPulseRoundMemory.values().stream().mapToLong(Long::valueOf).reduce(Util::leastCommonMultiple).orElseThrow();
            if (returnValue != 0) {
                return returnValue;
            }
            numOfButtonPresses *= 10;
        }
        throw new IllegalArgumentException();
    }

    private void runLogic(int numOfButtonPresses) {
        clearState();

        for (int i = 1; i <= numOfButtonPresses; i++) {
            Deque<Triplet<String,String, Boolean>> queue = new LinkedList<>();
            queue.add(Triplet.with("broadcaster".substring(1), null, false));
            while (!queue.isEmpty()) {
                Triplet<String, String, Boolean> queueEntry = queue.poll();
                if (queueEntry.getValue2()) {
                    high++;
                } else {
                    low++;
                }
                Pair<Character, List<String>> rule = rules.get(queueEntry.getValue0());
                if (rule != null){
                    switch (rule.getValue0()) {
                        case 'b' -> {
                            for (String s : rule.getValue1()) {
                                queue.add(Triplet.with(s, queueEntry.getValue0(), queueEntry.getValue2()));
                            }
                        }
                        case '%' -> {
                            if (!queueEntry.getValue2()) {
                                boolean state = stateFlipFlop.getOrDefault(queueEntry.getValue0(), false);
                                stateFlipFlop.put(queueEntry.getValue0(), !state);
                                for (String target : rule.getValue1()) {
                                    queue.add(Triplet.with(target, queueEntry.getValue0(), !state));
                                }
                            }
                        }
                        case '&' -> {
                            Map<String, Boolean> state = stateConjunction.get(queueEntry.getValue0());
                            state.put(queueEntry.getValue1(), queueEntry.getValue2());
                            boolean pulse = !state.values().stream().allMatch(b -> b);
                            if (pulse && highPulseRoundMemory.getOrDefault(queueEntry.getValue0(), -1) == 0) {
                                highPulseRoundMemory.put(queueEntry.getValue0(), i);
                            }
                            for (String target : rule.getValue1()) {
                                queue.add(Triplet.with(target, queueEntry.getValue0(), pulse));
                            }
                        }
                    }
                }
            }
        }
    }

    private void clearState() {
        stateFlipFlop.clear();
        stateConjunction.clear();
        for (Map.Entry<String, Set<String>> inputEntry : inputs.entrySet()) {
            if (rules.containsKey(inputEntry.getKey()) && rules.get(inputEntry.getKey()).getValue0() == '&') {
                Map<String, Boolean> inputStateMap = new HashMap<>();
                for (String source : inputEntry.getValue()) {
                    inputStateMap.put(source, false);
                }
                stateConjunction.put(inputEntry.getKey(), inputStateMap);
            }
        }
        high = 0;
        low = 0;
        highPulseRoundMemory.clear();
        if (inputs.containsKey("rx")) {
            // the module before rx is a conjunction, so we need to store when the modules before that send a high pulse
            for (String roundsToRemember : inputs.get(inputs.get("rx").stream().findFirst().orElseThrow())) {
                highPulseRoundMemory.put(roundsToRemember, 0);
            }
        }
    }

    private void parseInput(List<String> input) {
        rules.clear();
        inputs.clear();
        for (String line : input) {
            String[] rulesAsSplitString = line.split(" -> ");
            char type = rulesAsSplitString[0].charAt(0);
            String name = rulesAsSplitString[0].substring(1);
            List<String> targets = Arrays.stream(rulesAsSplitString[1].split(", ")).toList();
            rules.put(name, Pair.with(type, targets));

            for (String target : targets) {
                Set<String> sources = inputs.getOrDefault(target, new HashSet<>());
                sources.add(name);
                inputs.put(target, sources);
            }
        }
    }

}
