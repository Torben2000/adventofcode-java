package de.beachboys.aoc2015;

import de.beachboys.Day;
import de.beachboys.Util;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day14 extends Day {

    public Object part1(List<String> input) {
        int raceDuration = getRaceDuration();
        List<Reindeer> reindeers = getReindeerList(input);

        int maxDistance = 0;
        for (Reindeer reindeer : reindeers) {
            int totalDistance = getTotalDistance(raceDuration, reindeer);
            maxDistance = Math.max(maxDistance, totalDistance);
        }
        return maxDistance;
    }

    public Object part2(List<String> input) {
        int raceDuration = getRaceDuration();
        List<Reindeer> reindeers = getReindeerList(input);

        Map<String, Integer> points = new HashMap<>();
        for (int currentTimestamp = 1; currentTimestamp <= raceDuration; currentTimestamp++) {
            int maxDistance = 0;
            Set<String> leadingReindeers = new HashSet<>();
            for (Reindeer reindeer : reindeers) {
                int totalDistance = getTotalDistance(currentTimestamp, reindeer);
                if (totalDistance > maxDistance) {
                    maxDistance = totalDistance;
                    leadingReindeers.clear();
                }
                if (totalDistance >= maxDistance) {
                    leadingReindeers.add(reindeer.getName());
                }
            }
            for (String leadingReindeer : leadingReindeers) {
                points.put(leadingReindeer, points.getOrDefault(leadingReindeer, 0) + 1);
            }
        }
        return points.values().stream().mapToInt(Integer::intValue).max().orElseThrow();
    }

    private List<Reindeer> getReindeerList(List<String> input) {
        List<Reindeer> reindeers = new ArrayList<>();
        for (String line : input) {
            Matcher matcher = Pattern.compile("(.*) can fly ([0-9]+) km/s for ([0-9]+) seconds, but then must rest for ([0-9]+) seconds.").matcher(line);
            if (matcher.matches()) {
                String name = matcher.group(1);
                int speed = Integer.parseInt(matcher.group(2));
                int flyDuration = Integer.parseInt(matcher.group(3));
                int restDuration = Integer.parseInt(matcher.group(4));
                reindeers.add(new Reindeer(name, speed, flyDuration, restDuration));
            } else {
                throw new IllegalArgumentException();
            }
        }
        return reindeers;
    }

    private int getRaceDuration() {
        return Util.getIntValueFromUser("Length of race", 2503, io);
    }

    private int getTotalDistance(int raceDuration, Reindeer reindeer) {
        int cycleTime = reindeer.getFlyDuration() + reindeer.getRestDuration();
        int completeCycles = raceDuration / cycleTime;
        int remainingTime = raceDuration % cycleTime;
        return (completeCycles * reindeer.getFlyDuration() + Math.min(remainingTime, reindeer.getFlyDuration())) * reindeer.getSpeed();
    }

    private static class Reindeer {
        private final String name;
        private final int speed;
        private final int flyDuration;
        private final int restDuration;

        private Reindeer(String name, int speed, int flyDuration, int restDuration) {
            this.name = name;
            this.speed = speed;
            this.flyDuration = flyDuration;
            this.restDuration = restDuration;
        }

        public String getName() {
            return name;
        }

        public int getSpeed() {
            return speed;
        }

        public int getFlyDuration() {
            return flyDuration;
        }

        public int getRestDuration() {
            return restDuration;
        }
    }
}
