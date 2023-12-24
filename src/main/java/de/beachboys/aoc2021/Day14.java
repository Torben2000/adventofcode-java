package de.beachboys.aoc2021;

import de.beachboys.Day;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day14 extends Day {

    public Object part1(List<String> input) {
        return runLogic(input, 10);
    }

    public Object part2(List<String> input) {
        return runLogic(input, 40);
    }

    private long runLogic(List<String> input, int steps) {
        String template = input.getFirst();
        Map<String, String> rules = parseRules(input);

        Map<String, Long> pairCount = new HashMap<>();
        for (int i = 0; i < template.length() - 1; i++) {
            String pair = template.substring(i, i + 2);
            pairCount.put(pair, pairCount.getOrDefault(pair, 0L) + 1);
        }

        for (int i = 0; i < steps; i++) {
            Map<String, Long> newPairCount = new HashMap<>(pairCount);
            pairCount.forEach((oldPair, count) -> {
                String insertedCharacter = rules.get(oldPair);
                String newPair1 = oldPair.charAt(0) + insertedCharacter;
                String newPair2 = insertedCharacter + oldPair.charAt(1);
                newPairCount.put(oldPair, newPairCount.getOrDefault(oldPair, 0L) - count);
                newPairCount.put(newPair1, newPairCount.getOrDefault(newPair1, 0L) + count);
                newPairCount.put(newPair2, newPairCount.getOrDefault(newPair2, 0L) + count);
            });
            pairCount = newPairCount;
        }

        Map<Character, Long> characterDoubleCount = new HashMap<>();
        pairCount.forEach((pair, count) -> {
            char c1 = pair.charAt(0);
            char c2 = pair.charAt(1);
            characterDoubleCount.put(c1, characterDoubleCount.getOrDefault(c1, 0L) + count);
            characterDoubleCount.put(c2, characterDoubleCount.getOrDefault(c2, 0L) + count);
        });
        // first and last character only appear in one pair => add them a second time
        characterDoubleCount.put(template.charAt(0), characterDoubleCount.getOrDefault(template.charAt(0), 0L) + 1);
        characterDoubleCount.put(template.charAt(template.length() - 1), characterDoubleCount.getOrDefault(template.charAt(template.length() - 1), 0L) + 1);

        long characterDoubleCountMax = characterDoubleCount.values().stream().mapToLong(Long::valueOf).max().orElseThrow();
        long characterDoubleCountMin = characterDoubleCount.values().stream().mapToLong(Long::valueOf).min().orElseThrow();
        return characterDoubleCountMax / 2 - characterDoubleCountMin / 2;
    }

    private Map<String, String> parseRules(List<String> input) {
        Map<String, String> rules = new HashMap<>();
        for (int i = 2; i < input.size(); i++) {
            String[] line = input.get(i).split(" -> ");
            rules.put(line[0], line[1]);
        }
        return rules;
    }

}
