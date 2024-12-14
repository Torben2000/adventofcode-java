package de.beachboys.aoc2024;

import de.beachboys.Day;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day14 extends Day {

    private int bathroomWidth;
    private int bathroomHeight;

    public Object part1(List<String> input) {
        bathroomWidth = Util.getIntValueFromUser("Bathroom width", 101, io);
        bathroomHeight = Util.getIntValueFromUser("Bathroom height", 103, io);

        List<Tuple2<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>>> robots = parseInput(input);

        for (int i = 0; i < 100; i++) {
            robots = moveRobots(robots);
        }

        int c1 = 0;
        int c2 = 0;
        int c3 = 0;
        int c4 = 0;
        for (Tuple2<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>> robot : robots) {
            if (robot.v1.v1 < bathroomWidth/2) {
                if (robot.v1.v2 < bathroomHeight/2) {
                    c1++;
                } else if (robot.v1.v2 > bathroomHeight/2){
                    c2++;
                }
            } else if (robot.v1.v1 > bathroomWidth/2){
                if (robot.v1.v2 < bathroomHeight/2) {
                    c3++;
                } else if (robot.v1.v2 > bathroomHeight/2) {
                    c4++;
                }
            }
        }
        return c1 * c2 * c3 * c4;
    }

    public Object part2(List<String> input) {
        bathroomWidth = 101;
        bathroomHeight = 103;

        List<Tuple2<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>>> robots = parseInput(input);
        Map<List<Tuple2<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>>>, Long> history = new HashMap<>();

        for (long i = 1; i < Long.MAX_VALUE; i++) {
            robots = moveRobots(robots);
            if (history.containsKey(robots)) {
                return ("Cycle found before image frame was found, probably not real input data");
            }
            if (containsImage(robots)) {
                io.logInfo(Util.paintSet(robots.stream().map(t -> t.v1).collect(Collectors.toSet())));
                return i;
            }
            history.put(robots, i);
        }
        throw new IllegalArgumentException("Not even a cycle found, something is really wrong");
    }

    private List<Tuple2<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>>> moveRobots(List<Tuple2<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>>> robots) {
        List<Tuple2<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>>> newRobots = new ArrayList<>();
        for (Tuple2<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>> robot : robots) {
            int newXPosition = (robot.v1.v1 + robot.v2.v1) % bathroomWidth;
            if (newXPosition < 0) {
                newXPosition += bathroomWidth;
            }
            int newYPosition = (robot.v1.v2 + robot.v2.v2) % bathroomHeight;
            if (newYPosition < 0) {
                newYPosition += bathroomHeight;
            }
            newRobots.add(Tuple.tuple(Tuple.tuple(newXPosition, newYPosition), robot.v2));
        }
        return newRobots;
    }

    private boolean containsImage(List<Tuple2<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>>> robots) {
        int[] xCounts = new int[bathroomWidth];
        int[] yCounts = new int[bathroomHeight];
        Set<Tuple2<Integer, Integer>> robotPositions = robots.stream().map(r -> r.v1).collect(Collectors.toSet());
        for (Tuple2<Integer, Integer> robotPosition : robotPositions) {
            xCounts[robotPosition.v1]++;
            yCounts[robotPosition.v2]++;
        }
        long possibleFrameSidesLeftRight = Arrays.stream(xCounts).filter(i -> i > .3 * bathroomWidth).count();
        long possibleFrameSidesTopBottom = Arrays.stream(yCounts).filter(i -> i > .3 * bathroomHeight).count();

        return possibleFrameSidesLeftRight >= 2 && possibleFrameSidesTopBottom >= 2;
    }

    private static List<Tuple2<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>>> parseInput(List<String> input) {
        List<Tuple2<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>>> robots = new ArrayList<>();
        for (String line : input) {
            String[] split = line.split(Pattern.quote(" "));
            String[] positionAsString = split[0].substring(2).split(",");
            String[] velocityAsString = split[1].substring(2).split(",");
            Tuple2<Integer, Integer> position = Tuple.tuple(Integer.parseInt(positionAsString[0]), Integer.parseInt(positionAsString[1]));
            Tuple2<Integer, Integer> velocity = Tuple.tuple(Integer.parseInt(velocityAsString[0]), Integer.parseInt(velocityAsString[1]));

            robots.add(Tuple.tuple(position, velocity));
        }
        return robots;
    }

}
