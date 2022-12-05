package de.beachboys.aoc2022;

import de.beachboys.Day;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day05 extends Day {


    private static final Pattern REARRANGEMENT_PATTERN = Pattern.compile("move ([0-9]+) from ([0-9]+) to ([0-9]+)");
    private static final List<Deque<Character>> crateStacks = new ArrayList<>();

    public Object part1(List<String> input) {
        return runLogic(input, false);
    }

    public Object part2(List<String> input) {
        return runLogic(input, true);
    }

    private static String runLogic(List<String> input, boolean isCrateMover9001) {
        crateStacks.clear();
        for (int i = 0; i < (input.get(0).length() + 1) / 4; i++) {
            crateStacks.add(new LinkedList<>());
        }
        boolean parsedInitialState = false;
        for (String line : input) {
            if (line.isEmpty() || line.charAt(1) == '1') {
                parsedInitialState = true;
            } else if (parsedInitialState) {
                rearrangeCrates(line, isCrateMover9001);
            } else {
                parseInitialStateLine(line);
            }
        }
        return crateStacks.stream().map(Deque::getFirst).map(Object::toString).reduce(String::concat).orElseThrow();
    }

    private static void rearrangeCrates(String line, boolean isCrateMover9001) {
        Matcher m = REARRANGEMENT_PATTERN.matcher(line);
        if (m.matches()) {
            int quantity = Integer.parseInt(m.group(1));
            int from = Integer.parseInt(m.group(2)) - 1;
            int to = Integer.parseInt(m.group(3)) - 1;
            Deque<Character> tempStack = new LinkedList<>();
            for (int i = 0; i < quantity; i++) {
                tempStack.add(crateStacks.get(from).pollFirst());
            }
            while (!tempStack.isEmpty()) {
                crateStacks.get(to).addFirst(isCrateMover9001 ? tempStack.pollLast() : tempStack.pollFirst());
            }
        }
    }

    private static void parseInitialStateLine(String line) {
        for (int i = 0; i < crateStacks.size(); i++) {
            char c = line.charAt(i * 4 + 1);
            if (c != ' ') {
                crateStacks.get(i).add(c);
            }
        }
    }

}
