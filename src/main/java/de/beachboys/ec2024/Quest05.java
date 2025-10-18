package de.beachboys.ec2024;

import com.google.common.collect.Lists;
import de.beachboys.Quest;
import de.beachboys.Util;

import java.util.*;

public class Quest05 extends Quest {

    @Override
    public Object part1(List<String> input) {
        List<List<Integer>> dancers = parseDancers(input);
        for (int round = 1; round < 10; round++) {
             dance(round, dancers);
        }
        return dance(10, dancers);
    }

    @Override
    public Object part2(List<String> input) {
        Map<Long, Integer> shouts = new HashMap<>();
        List<List<Integer>> dancers = parseDancers(input);

        for (int round = 1; round < Integer.MAX_VALUE; round++) {
            long shout = dance(round, dancers);
            if (shouts.containsKey(shout)) {
                int shoutCounter = shouts.get(shout);
                if (shoutCounter == 2023) {
                    return round * shout;
                } else {
                    shouts.put(shout, shoutCounter+1);
                }
            } else {
                shouts.put(shout, 1);
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Object part3(List<String> input) {
        List<List<Integer>> dancers = parseDancers(input);

        Set<List<List<Integer>>> history = new HashSet<>();
        long maxShout = 0;
        for (int round = 1; round < Integer.MAX_VALUE; round++) {
            maxShout = Math.max(maxShout, dance(round, dancers));
            List<List<Integer>> state = new ArrayList<>();
            for (List<Integer> dancer : dancers) {
                state.add(new ArrayList<>(dancer));
            }
            if (history.contains(state)) {
                break;
            }
            history.add(state);
        }
        return maxShout;
    }

    private static List<List<Integer>> parseDancers(List<String> input) {
        List<List<Integer>> dancers = new ArrayList<>();
        for (String line : input) {
            List<Integer> lineAsList = Util.parseToIntList(line, " ");
            if (dancers.isEmpty()) {
                for (int i = 0; i < lineAsList.size(); i++) {
                    dancers.add(Lists.newLinkedList());
                }
            }
            for (int i = 0; i < lineAsList.size(); i++) {
                dancers.get(i).add(lineAsList.get(i));
            }
        }
        return dancers;
    }

    private long dance(int round, List<List<Integer>> dancers) {
        int currentColumn = (round-1) % dancers.size();
        int dancer = dancers.get(currentColumn).removeFirst();
        int targetColumn = (currentColumn+1) % dancers.size();
        int clapsFromStartPosition = dancer;
        while (clapsFromStartPosition > 2 * dancers.get(targetColumn).size()) {
            clapsFromStartPosition -= dancers.get(targetColumn).size() * 2;
        }
        if (clapsFromStartPosition <= dancers.get(targetColumn).size()) {
            dancers.get(targetColumn).add(clapsFromStartPosition - 1, dancer);
        } else {
            dancers.get(targetColumn).add(2 * dancers.get(targetColumn).size() - clapsFromStartPosition + 1, dancer);
        }
        return dancers.stream().map(List::getFirst).map(String::valueOf).reduce(String::concat).map(Long::parseLong).orElseThrow();
    }
}
