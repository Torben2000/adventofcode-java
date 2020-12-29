package de.beachboys.aoc2015;

import de.beachboys.Day;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day16 extends Day {

    public Object part1(List<String> input) {
        return runLogic(input, this::isValidPropertyPart1);
    }

    public Object part2(List<String> input) {
        return runLogic(input, this::isValidPropertyPart2);
    }

    private Object runLogic(List<String> input, BiPredicate<Map.Entry<String, Integer>, Map<String, Integer>> isValidProperty) {
        Map<String, Integer> tickerTape = getTickerTape();
        Map<Integer, Map<String, Integer>> aunts = parseAunts(input);
        return findCorrectAunt(aunts, tickerTape, isValidProperty);
    }

    private int findCorrectAunt(Map<Integer, Map<String, Integer>> aunts, Map<String, Integer> tickerTape, BiPredicate<Map.Entry<String, Integer>, Map<String, Integer>> isValidProperty) {
        return aunts.entrySet().stream().filter(entry -> {
            boolean match = true;
            for (Map.Entry<String, Integer> auntProperty : entry.getValue().entrySet()) {
                if (!isValidProperty.test(auntProperty, tickerTape)) {
                    match = false;
                }
            }
            return match;
        }).mapToInt(Map.Entry::getKey).findFirst().orElseThrow();
    }

    private boolean isValidPropertyPart1(Map.Entry<String, Integer> auntProperty, Map<String, Integer> tickerTape) {
        return auntProperty.getValue().equals(tickerTape.get(auntProperty.getKey()));
    }

    private boolean isValidPropertyPart2(Map.Entry<String, Integer> auntProperty, Map<String, Integer> tickerTape) {
        String key = auntProperty.getKey();
        Integer value = auntProperty.getValue();
        Integer tickerValue = tickerTape.get(key);
        if ("trees".equals(key) || "cats".equals(key)) {
            return value > tickerValue;
        }
        if ("pomeranians".equals(key) || "goldfish".equals(key)) {
            return value < tickerValue;
        }
        return value.equals(tickerValue);
    }

    private Map<String, Integer> getTickerTape() {
        return Map.of(
                "children", 3,
                "cats", 7,
                "samoyeds", 2,
                "pomeranians", 3,
                "akitas", 0,
                "vizslas", 0,
                "goldfish", 5,
                "trees", 3,
                "cars", 2,
                "perfumes", 1
        );
    }

    private Map<Integer, Map<String, Integer>> parseAunts(List<String> input) {
        Map<Integer, Map<String, Integer>> aunts = new HashMap<>();
        for (String line : input) {
            Matcher m = Pattern.compile("Sue ([0-9]+): ([a-z]+): ([0-9]+), ([a-z]+): ([0-9]+), ([a-z]+): ([0-9]+)").matcher(line);
            if (m.matches()) {
                int number = Integer.parseInt(m.group(1));
                Map<String, Integer> map = Map.of(
                        m.group(2), Integer.parseInt(m.group(3)),
                        m.group(4), Integer.parseInt(m.group(5)),
                        m.group(6), Integer.parseInt(m.group(7)));
                aunts.put(number, map);
            }
        }
        return aunts;
    }

}
