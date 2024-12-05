package de.beachboys.aoc2024;

import de.beachboys.Day;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.ArrayList;
import java.util.List;

public class Day05 extends Day {

    private final List<Tuple2<Integer, Integer>> rules = new ArrayList<>();
    private final List<List<Integer>> updates = new ArrayList<>();

    public Object part1(List<String> input) {
        parseInput(input);
        long result = 0;
        for (List<Integer> update : updates) {
            if (checkOrder(update)) {
                result += update.get(update.size()/2);
            }
        }
        return result;
    }

    public Object part2(List<String> input) {
        parseInput(input);
        long result = 0;
        for (List<Integer> update : updates) {
            if (!checkOrder(update)) {
                result += sortUpdate(update).get(update.size()/2);
            }
        }
        return result;
    }

    private List<Integer> sortUpdate(List<Integer> update) {
        return update.stream().sorted((left, right) -> {
            for (Tuple2<Integer, Integer> rule : rules) {
                int ruleLeft = rule.v1;
                int ruleRight = rule.v2;
                if (left == ruleLeft && right == ruleRight) {
                    return -1;
                } else if (left == ruleRight && right == ruleLeft) {
                    return 1;
                }
            }
            return 0;
        }).toList();
    }

    private boolean checkOrder(List<Integer> update) {
        boolean ordered = true;
        for (Tuple2<Integer, Integer> rule : rules) {
            int l = update.indexOf(rule.v1);
            int r = update.indexOf(rule.v2);
            if (r >= 0 && r < l) {
                ordered = false;
                break;
            }
        }
        return ordered;
    }

    private void parseInput(List<String> input) {
        rules.clear();
        updates.clear();
        boolean parsingModeSwitchedToUpdate = false;
        for (String line : input) {
            if (line.isEmpty()) {
                parsingModeSwitchedToUpdate = true;
            } else if (parsingModeSwitchedToUpdate) {
                updates.add(Util.parseIntCsv(line));
            } else {
                String[] split = line.split("\\|");
                rules.add(Tuple.tuple(Integer.parseInt(split[0]), Integer.parseInt(split[1])));
            }
        }
    }

}
