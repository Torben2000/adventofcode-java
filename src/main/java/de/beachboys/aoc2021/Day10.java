package de.beachboys.aoc2021;

import de.beachboys.Day;

import java.util.*;

public class Day10 extends Day {

    private static final Map<Character, Character> matchingOpeningBrackets = new HashMap<>();
    private static final Map<Character, Integer> scoresPart1 = new HashMap<>();
    private static final Map<Character, Integer> scoresPart2 = new HashMap<>();

    static {
        matchingOpeningBrackets.put(')', '(');
        matchingOpeningBrackets.put(']', '[');
        matchingOpeningBrackets.put('}', '{');
        matchingOpeningBrackets.put('>', '<');

        scoresPart1.put(')', 3);
        scoresPart1.put(']', 57);
        scoresPart1.put('}', 1197);
        scoresPart1.put('>', 25137);

        scoresPart2.put('(', 1);
        scoresPart2.put('[', 2);
        scoresPart2.put('{', 3);
        scoresPart2.put('<', 4);
    }

    private long syntaxErrorScore = 0L;
    private final List<Long> incompleteLineScores = new ArrayList<>();

    public Object part1(List<String> input) {
        runLogic(input);

        return syntaxErrorScore;
    }

    public Object part2(List<String> input) {
        runLogic(input);

        incompleteLineScores.sort(Comparator.naturalOrder());
        return incompleteLineScores.get(incompleteLineScores.size()/2);
    }

    private void runLogic(List<String> input) {
        syntaxErrorScore = 0L;
        incompleteLineScores.clear();
        for (String line : input) {
            Deque<Character> stack = new LinkedList<>();
            boolean syntaxError = false;
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                if (isOpeningBracket(c)) {
                    stack.add(c);
                } else {
                    if (matchingOpeningBrackets.get(c).equals(stack.peekLast())) {
                        stack.removeLast();
                    } else {
                        syntaxErrorScore += scoresPart1.get(c);
                        syntaxError = true;
                        break;
                    }
                }
            }

            if (!syntaxError) {
                long lineScore = 0L;
                while (!stack.isEmpty()) {
                    lineScore *= 5;
                    lineScore += scoresPart2.get(stack.removeLast());
                }
                incompleteLineScores.add(lineScore);
            }
        }
    }

    private boolean isOpeningBracket(char c) {
        return c == '(' || c == '[' || c == '{' || c == '<';
    }

}
