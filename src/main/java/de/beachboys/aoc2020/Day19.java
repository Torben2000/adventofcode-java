package de.beachboys.aoc2020;

import de.beachboys.Day;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.*;
import java.util.stream.Collectors;

public class Day19 extends Day {

    private final Map<Tuple2<Integer, String>, Boolean> cache = new HashMap<>();
    private final Map<Integer, Set<String>> patterns = new HashMap<>();

    public Object part2(List<String> input) {
        return runLogic(input, () -> {
            patterns.put(8, Set.of("42", "42 8"));
            patterns.put(11, Set.of("42 31", "42 11 31"));
        });
    }

    public Object part1(List<String> input) {
        return runLogic(input, () -> {});
    }

    private Object runLogic(List<String> input, Runnable patternManipulator) {
        boolean matchMode = false;
        int counter = 0;
        for (String line : input) {
            if (line.isEmpty()) {
                matchMode = true;
                patternManipulator.run();
            } else if (matchMode) {
                if (matchesPattern(0, line)) {
                    counter++;
                }
            } else {
                parsePattern(line);
            }
        }
        return counter;
    }

    private void parsePattern(String lineToParse) {
        String[] indexAndContent = lineToParse.split(": ");
        Integer index = Integer.valueOf(indexAndContent[0]);
        String[] contentParts = indexAndContent[1].split(" \\| ");
        patterns.put(index, Arrays.stream(contentParts).map(s -> s.replaceAll("\"", "")).collect(Collectors.toSet()));
    }

    private boolean matchesPattern(int patternIndex, String possibleMatch) {
        Tuple2<Integer, String> cacheKey = Tuple.tuple(patternIndex, possibleMatch);
        if (cache.containsKey(cacheKey)) {
            return cache.get(cacheKey);
        }

        Set<String> patternsForIndex = patterns.get(patternIndex);

        boolean returnValue = false;
        patternLoop :
        for (String pattern : patternsForIndex) {
            if (pattern.equals(possibleMatch)) {
                returnValue = true;
                break;
            }
            if ("a".equals(pattern) || "b".equals(pattern)) {
                break;
            }

            String[] referencedPatternsAsString = pattern.split(" ");
            if (referencedPatternsAsString.length > possibleMatch.length()) {
                break;
            }
            List<Integer> referencedPatterns = Arrays.stream(referencedPatternsAsString).map(Integer::valueOf).toList();
            switch (referencedPatternsAsString.length) {
                case 1:
                    boolean isMatchCase1 = matchesPattern(referencedPatterns.getFirst(), possibleMatch);
                    if (isMatchCase1) {
                        returnValue = true;
                        break patternLoop;
                    }
                    break;
                case 2:
                    for (int j = 1; j < possibleMatch.length(); j++) {
                        boolean isMatchCase2 = matchesPattern(referencedPatterns.get(0), possibleMatch.substring(0, j)) && matchesPattern(referencedPatterns.get(1), possibleMatch.substring(j));
                        if (isMatchCase2) {
                            returnValue = true;
                            break patternLoop;
                        }
                    }
                    break;
                case 3:
                    for (int j = 1; j < possibleMatch.length() - 1; j++) {
                        if (matchesPattern(referencedPatterns.get(0), possibleMatch.substring(0, j))) {
                            for (int k = j + 1; k < possibleMatch.length(); k++) {
                                boolean isMatchCase3 = matchesPattern(referencedPatterns.get(1), possibleMatch.substring(j, k)) && matchesPattern(referencedPatterns.get(2), possibleMatch.substring(k));
                                if (isMatchCase3) {
                                    returnValue = true;
                                    break patternLoop;
                                }
                            }
                        }
                    }
                    break;
            }

        }

        cache.put(cacheKey, returnValue);
        return returnValue;
    }

}
