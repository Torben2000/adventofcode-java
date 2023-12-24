package de.beachboys.aoc2020;

import de.beachboys.Day;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.*;
import java.util.stream.Collectors;

public class Day16 extends Day {

    public Object part1(List<String> input) {
        List<Rule> rules = new ArrayList<>();
        ParseMode mode = ParseMode.RULES;
        long sum = 0L;
        for (String line : input) {
            if (line.isEmpty()) {
                mode = (mode == ParseMode.RULES) ? ParseMode.MY_TICKET : ParseMode.NEARBY_TICKETS;
                continue;
            }
            if (line.startsWith("your") || line.startsWith("nearby")) {
                continue;
            }
            if (mode == ParseMode.RULES) {
                rules.add(parseRuleFromLine(line));
            } else if (mode == ParseMode.NEARBY_TICKETS) {
                List<Integer> ticket = Util.parseIntCsv(line);
                for (Integer value : ticket) {
                    if (!isValidValue(value, rules)) {
                        sum += value;
                    }
                }
            }
        }
        return sum;
    }

    public Object part2(List<String> input) {
        List<Rule> rules = new ArrayList<>();
        List<List<Integer>> validTickets = new ArrayList<>();
        List<Integer> myTicket = null;
        ParseMode mode = ParseMode.RULES;
        for (String line : input) {
            if (line.isEmpty()) {
                mode = (mode == ParseMode.RULES) ? ParseMode.MY_TICKET : ParseMode.NEARBY_TICKETS;
                continue;
            }
            if (line.startsWith("your") || line.startsWith("nearby")) {
                continue;
            }
            if (mode == ParseMode.RULES) {
                rules.add(parseRuleFromLine(line));
            } else if (mode == ParseMode.MY_TICKET) {
                myTicket = Util.parseIntCsv(line);
            } else {
                List<Integer> ticket = Util.parseIntCsv(line);
                if (isValidTicket(ticket, rules)) {
                    validTickets.add(ticket);
                }
            }
        }

        Map<Integer, Set<Integer>> possibleRulesForFields = prepareInitialRulesForFields(rules);

        removeImpossibleRulesForFieldsBasedOnTickets(rules, validTickets, possibleRulesForFields);

        removeImpossibleRulesForFieldsBasedOnDefinedRulesForFields(possibleRulesForFields);

        return getDepartureValueProduct(rules, myTicket, possibleRulesForFields);
    }

    private long getDepartureValueProduct(List<Rule> rules, List<Integer> myTicket, Map<Integer, Set<Integer>> possibleRulesForFields) {
        if (myTicket == null) {
            throw new IllegalArgumentException("My ticket not correctly parsed");
        }
        long result = 1L;
        for (int i = 0; i < rules.size(); i++) {
            String fieldName = rules.get(i).getFieldName();
            if (fieldName.startsWith("departure")) {
                int index = possibleRulesForFields.get(i).stream().findFirst().orElseThrow();
                result *= myTicket.get(index);
            }
        }
        return result;
    }

    private void removeImpossibleRulesForFieldsBasedOnDefinedRulesForFields(Map<Integer, Set<Integer>> possibleRulesForFields) {
        boolean changeDone = true;
        while (changeDone) {
            changeDone = false;
            List<Integer> uniqueIndices = possibleRulesForFields.values().stream().filter(set -> set.size() == 1).map(set -> set.stream().findFirst().orElseThrow()).collect(Collectors.toList());
            List<Set<Integer>> notUniqueSets = possibleRulesForFields.values().stream().filter(set -> set.size() != 1).collect(Collectors.toList());
            for (Set<Integer> values : notUniqueSets) {
                changeDone |= values.removeAll(uniqueIndices);
            }
        }
    }

    private void removeImpossibleRulesForFieldsBasedOnTickets(List<Rule> rules, List<List<Integer>> validTickets, Map<Integer, Set<Integer>> possibleRulesForFields) {
        for (List<Integer> ticket : validTickets) {
            for (int i = 0; i < ticket.size(); i++) {
                int value = ticket.get(i);
                for (int j = 0; j < rules.size(); j++) {
                    Rule rule = rules.get(j);
                    if (!valueFulfillsRule(value, rule)) {
                        possibleRulesForFields.get(j).remove(i);
                    }
                }
            }
        }
    }

    private Map<Integer, Set<Integer>> prepareInitialRulesForFields(List<Rule> rules) {
        Map<Integer, Set<Integer>> possibleRulesForFields = new HashMap<>();
        for (int i = 0; i < rules.size(); i++) {
            Set<Integer> possibleRulesForField = new HashSet<>();
            for (int j = 0; j < rules.size(); j++) {
                 possibleRulesForField.add(j);
            }
            possibleRulesForFields.put(i, possibleRulesForField);
        }
        return possibleRulesForFields;
    }

    private boolean isValidTicket(List<Integer> ticket, List<Rule> rules) {
        boolean validTicket = true;
        for (Integer value : ticket) {
            validTicket &= isValidValue(value, rules);
        }
        return validTicket;
    }

    private boolean isValidValue(Integer value, List<Rule> rules) {
        boolean validValue = false;
        for (Rule rule : rules) {
            validValue |= valueFulfillsRule(value, rule);
        }
        return validValue;
    }

    private boolean valueFulfillsRule(Integer value, Rule rule) {
        return value >= rule.getRange1().v1 && value <= rule.getRange1().v2
                || value >= rule.getRange2().v1 && value <= rule.getRange2().v2;
    }

    private Rule parseRuleFromLine(String line) {
        String[] fieldNameAndRanges = line.split(": ");
        String[] rangeStrings = fieldNameAndRanges[1].split(" or ");
        return new Rule(fieldNameAndRanges[0], parseRange(rangeStrings[0]), parseRange(rangeStrings[1]));
    }

    private Tuple2<Integer, Integer> parseRange(String rangeString) {
        String[] rangeSplit = rangeString.split("-");
        return Tuple.tuple(Integer.parseInt(rangeSplit[0]), Integer.parseInt(rangeSplit[1]));
    }

    private enum ParseMode {
        RULES, MY_TICKET, NEARBY_TICKETS
    }

    private static class Rule {
        private final String fieldName;
        private final Tuple2<Integer, Integer> range1;
        private final Tuple2<Integer, Integer> range2;

        public Rule(String value0, Tuple2<Integer, Integer> range1x, Tuple2<Integer, Integer> range2x) {
            fieldName = value0;
            range1 = range1x;
            range2 = range2x;
        }

        public String getFieldName() {
            return fieldName;
        }

        public Tuple2<Integer, Integer> getRange1() {
            return range1;
        }

        public Tuple2<Integer, Integer> getRange2() {
            return range2;
        }
    }
}
