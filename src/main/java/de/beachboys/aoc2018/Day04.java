package de.beachboys.aoc2018;

import de.beachboys.Day;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.ToIntFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day04 extends Day {

    public Object part1(List<String> input) {
        return runLogic(input, this::getSum);
    }

    public Object part2(List<String> input) {
        return runLogic(input, this::getMax);
    }

    private int runLogic(List<String> input, ToIntFunction<Collection<Integer>> getRelevantAsleepMinutesFromAllAsleepMinutes) {
        Map<Integer, Map<Integer, Integer>> asleepMinutes = parseInput(input);
        Map<Integer, Integer> guardToAsleepMinutes = asleepMinutes.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> getRelevantAsleepMinutesFromAllAsleepMinutes.applyAsInt(entry.getValue().values())));
        int guardWithMostAsleepMinutes = guardToAsleepMinutes.entrySet().stream().max(Comparator.comparingInt(Map.Entry::getValue)).orElseThrow().getKey();
        int minuteWhenGuardIsMostAsleep = asleepMinutes.get(guardWithMostAsleepMinutes).entrySet().stream().max(Comparator.comparingInt(Map.Entry::getValue)).orElseThrow().getKey();
        return guardWithMostAsleepMinutes * minuteWhenGuardIsMostAsleep;
    }

    private int getSum(Collection<Integer> values) {
        return values.stream().mapToInt(Integer::intValue).sum();
    }

    private int getMax(Collection<Integer> values) {
        return values.stream().mapToInt(Integer::intValue).max().orElseThrow();
    }

    private Map<Integer, Map<Integer, Integer>> parseInput(List<String> input) {
        List<String> sortedInput = new ArrayList<>(input);
        sortedInput.sort(String::compareTo);
        Pattern guardPattern = Pattern.compile("\\[(.*)] Guard #([0-9]+) begins shift");
        Pattern asleepPattern = Pattern.compile("\\[(.*)] falls asleep");
        Pattern wakePattern = Pattern.compile("\\[(.*)] wakes up");
        Map<Integer, Map<Integer, Integer>> asleepMinutes = new HashMap<>();
        int guard = 0;
        int asleepMinute = 0;
        for (String line : sortedInput) {
            Matcher guardMatcher = guardPattern.matcher(line);
            if (guardMatcher.matches()) {
                guard = Integer.parseInt(guardMatcher.group(2));
            } else {
                Matcher asleepMatcher = asleepPattern.matcher(line);
                if (asleepMatcher.matches()) {
                    asleepMinute = getMinutes(asleepMatcher.group(1));
                } else {
                    Matcher wakeMatcher = wakePattern.matcher(line);
                    if (wakeMatcher.matches()) {
                        int wakeMinute = getMinutes(wakeMatcher.group(1));
                        asleepMinutes.putIfAbsent(guard, new HashMap<>());
                        Map<Integer, Integer> guardAsleepMinutes = asleepMinutes.get(guard);
                        for (int minute = asleepMinute; minute < wakeMinute; minute++) {
                            guardAsleepMinutes.merge(minute, 1, Integer::sum);
                        }
                    }
                }
            }
        }
        return asleepMinutes;
    }

    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm");
    private int getMinutes(String dateString) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(DATE_FORMAT.parse(dateString));
            return calendar.get(Calendar.MINUTE);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

}
