package de.beachboys.aoc2018;

import de.beachboys.Day;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day07 extends Day {

    private final Set<Tuple2<String, String>> rules = new HashSet<>();

    public Object part1(List<String> input) {
        parseRules(input);
        Set<String> remainingSteps = buildInitialRemainingSteps();

        StringBuilder order = new StringBuilder();
        while (!remainingSteps.isEmpty()) {
            TreeSet<String> possibilities = getPossibilitiesPart1(remainingSteps);
            String nextStep = possibilities.first();
            order.append(nextStep);
            remainingSteps.remove(nextStep);
        }

        return order.toString();
    }

    public Object part2(List<String> input) {
        int workers = Util.getIntValueFromUser("Number of workers", 5, io);
        int durationOffset = Util.getIntValueFromUser("Step duration offset", 60, io);

        parseRules(input);
        Set<String> remainingSteps = buildInitialRemainingSteps();
        TreeMap<Integer, String> runningSteps = new TreeMap<>();
        int timestamp = 0;

        while (!remainingSteps.isEmpty()) {
            TreeSet<String> possibilities = getPossibilitiesPart2(remainingSteps, runningSteps);
            if (runningSteps.size() < workers && possibilities.size() > 0) {
                String nextStep = possibilities.first();
                runningSteps.put(buildTargetTimestamp(timestamp, durationOffset, nextStep), nextStep);
            } else {
                Map.Entry<Integer, String> endingStep = runningSteps.pollFirstEntry();
                remainingSteps.remove(endingStep.getValue());
                timestamp = endingStep.getKey();
            }
        }

        return timestamp;
    }

    private TreeSet<String> getPossibilitiesPart1(Set<String> remainingSteps) {
        TreeSet<String> possibilities = new TreeSet<>(remainingSteps);
        for (Tuple2<String, String> rule : rules) {
            if (remainingSteps.contains(rule.v1)) {
                possibilities.remove(rule.v2);
            }
        }
        return possibilities;
    }

    private TreeSet<String> getPossibilitiesPart2(Set<String> remainingSteps, TreeMap<Integer, String> runningSteps) {
        TreeSet<String> possibilities = new TreeSet<>(remainingSteps);
        for (Tuple2<String, String> rule : rules) {
            possibilities.removeAll(runningSteps.values());
            if (remainingSteps.contains(rule.v1) || runningSteps.containsValue(rule.v1)) {
                possibilities.remove(rule.v2);
            }
        }
        return possibilities;
    }

    private int buildTargetTimestamp(int timestamp, int durationOffset, String step) {
        int letterValue = step.charAt(0) - 'A' + 1;
        return timestamp + durationOffset + letterValue;
    }

    private Set<String> buildInitialRemainingSteps() {
        Set<String> remainingSteps = new HashSet<>();
        rules.forEach(pair -> {
            remainingSteps.add(pair.v1);
            remainingSteps.add(pair.v2);
        });
        return remainingSteps;
    }

    private void parseRules(List<String> input) {
        rules.clear();
        Pattern p = Pattern.compile("Step ([A-Z]) must be finished before step ([A-Z]) can begin.");
        for (String line : input) {
            Matcher m = p.matcher(line);
            if (m.matches()) {
                rules.add(Tuple.tuple(m.group(1), m.group(2)));
            }
        }
    }

}
