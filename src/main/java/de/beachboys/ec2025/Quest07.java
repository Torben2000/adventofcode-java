package de.beachboys.ec2025;

import de.beachboys.Quest;
import de.beachboys.Util;

import java.util.*;
import java.util.regex.Pattern;

public class Quest07 extends Quest {

    private List<String> names;
    private Map<String, List<String>> rules;
    @Override
    public Object part1(List<String> input) {
        parseInput(input);

        for (String name : names) {
            if (isValidName(name)) {
                return name;
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Object part2(List<String> input) {
        parseInput(input);

        long result = 0;
        for (int i = 0; i < names.size(); i++) {
            String name = names.get(i);
            if (isValidName(name)) {
                result += i + 1;
            }
        }
        return result;
    }

    @Override
    public Object part3(List<String> input) {
        parseInput(input);

        Set<String> res = new HashSet<>();
        for (String name : names) {
            if (isValidName(name)) {
                res.addAll(getAllPossibleNames(name));
            }
        }
        return res.size();
    }

    private boolean isValidName(String name) {
        for (int i = 0; i < name.length() - 1; i++) {
            String letter = name.substring(i, i + 1);
            String nextLetter = name.substring(i + 1, i + 2);
            if (!rules.containsKey(letter) || !rules.get(letter).contains(nextLetter)) {
                return false;
            }
        }
        return true;
    }

    private Set<String> getAllPossibleNames(String prefix) {
        if (prefix.length() > 11) {
            return Set.of();
        }
        Set<String> result = new HashSet<>();
        if (prefix.length() >= 7) {
            result.add(prefix);
        }
        String lastLetterOfPrefix = prefix.substring(prefix.length() - 1);
        if (rules.containsKey(lastLetterOfPrefix)) {
            for (String nextLetter : rules.get(lastLetterOfPrefix)) {
                result.addAll(getAllPossibleNames(prefix + nextLetter));
            }
        }
        return result;
    }

    private void parseInput(List<String> input) {
        names = Util.parseCsv(input.getFirst());

        rules = new HashMap<>();
        for (int i = 2; i < input.size(); i++) {
            String ruleLine = input.get(i);
            String[] splitRule = ruleLine.split(Pattern.quote(" > "));
            rules.put(splitRule[0], Util.parseCsv(splitRule[1]));
        }
    }
}
