package de.beachboys.aoc2015;

import de.beachboys.Day;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.IntSupplier;

public class Day07 extends Day {

    private final Map<String, IntSupplier> rules = new HashMap<>();

    private final Map<String, Integer> cache = new HashMap<>();

    public Object part1(List<String> input) {
        initRules(input);
        return getValue("a");
    }

    public Object part2(List<String> input) {
        initRules(input);
        int valueA = getValue("a");
        cache.clear();
        rules.put("b", () -> valueA);
        return getValue("a");
    }

    private void initRules(List<String> input) {
        for (String ruleAsString : input) {
            String[] ruleAndTarget = ruleAsString.split(" -> ");
            String target = ruleAndTarget[1];
            rules.put(target, buildSupplier(ruleAndTarget[0]));
        }
    }

    private IntSupplier buildSupplier(String rule) {
        if (rule.contains(" OR ")) {
            String[] params = rule.split(" OR ");
            return () -> getValue(params[0]) | getValue(params[1]);
        } else if (rule.contains(" AND ")) {
            String[] params = rule.split(" AND ");
            return () -> getValue(params[0]) & getValue(params[1]);
        } else if (rule.contains("NOT ")) {
            String param = rule.substring("NOT ".length());
            return () -> 65535 ^ getValue(param);
        } else if (rule.contains(" LSHIFT ")) {
            String[] params = rule.split(" LSHIFT ");
            return () -> getValue(params[0]) << Integer.parseInt(params[1]);
        } else if (rule.contains(" RSHIFT ")) {
            String[] params = rule.split(" RSHIFT ");
            return () -> getValue(params[0]) >> Integer.parseInt(params[1]);
        }
        return () -> getValue(rule);
    }

    private int getValue(String target) {
        if (!cache.containsKey(target)) {
            cache.put(target, rules.getOrDefault(target, () -> Integer.parseInt(target)).getAsInt());
        }
        return cache.get(target);
    }

}
