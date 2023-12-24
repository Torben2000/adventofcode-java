package de.beachboys.aoc2023;

import de.beachboys.Day;
import org.jooq.lambda.tuple.Range;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day19 extends Day {

    private final Map<String, List<Rule>> workflows = new HashMap<>();
    private final List<Part> parts = new ArrayList<>();

    public Object part1(List<String> input) {
        parseInput(input);

        long result = 0;
        for (Part part : parts) {
            String workflowName = "in";
            while (!"R".equals(workflowName) && !"A".equals(workflowName)) {
                workflowName = runWorkflow(workflowName, part);
            }
            if ("A".equals(workflowName)) {
                result += part.getSumOfRatings();
            }
        }
        return result;
    }

    public Object part2(List<String> input) {
        parseInput(input);

        Deque<Tuple2<String, RatingRanges>> queue = new LinkedList<>();
        Set<RatingRanges> acceptedRanges = new HashSet<>();
        queue.add(Tuple.tuple("in", new RatingRanges(1, 4000, 1, 4000, 1, 4000, 1, 4000)));

        while (!queue.isEmpty()) {
            Tuple2<String, RatingRanges> queueEntry = queue.poll();
            RatingRanges ranges = queueEntry.v2;
            if ("A".equals(queueEntry.v1)) {
                acceptedRanges.add(queueEntry.v2);
            } else if (!"R".equals(queueEntry.v1)) {
                List<Rule> rules = workflows.get(queueEntry.v1);
                for (Rule rule : rules) {
                    if (rule.hasCondition) {
                        Range<Integer> newRange = getNewRange(rule.comparator, rule.compareValue, ranges.getRange(rule.varName));
                        RatingRanges newRanges = new RatingRanges(ranges);
                        newRanges.setRange(rule.varName, newRange);
                        queue.add(Tuple.tuple(rule.resultingWorkflowName, newRanges));
                        ranges.setRange(rule.varName, getRemainingRange(ranges.getRange(rule.varName), newRange));
                    } else {
                        queue.add(Tuple.tuple(rule.resultingWorkflowName, ranges));
                    }
                }
            }
        }

        return acceptedRanges.stream().mapToLong(RatingRanges::getNumberOfPossibleCombinations).sum();
    }

    private void parseInput(List<String> input) {
        Pattern partPattern = Pattern.compile("\\{x=([0-9]+),m=([0-9]+),a=([0-9]+),s=([0-9]+)}");
        workflows.clear();
        parts.clear();

        boolean parsingModeSwitched = false;
        for (String line : input) {
            if (line.isEmpty()) {
                parsingModeSwitched = true;
                continue;
            }
            if (parsingModeSwitched) {
                Matcher matcher = partPattern.matcher(line);
                if (matcher.matches()) {
                    int x = Integer.parseInt(matcher.group(1));
                    int m = Integer.parseInt(matcher.group(2));
                    int a = Integer.parseInt(matcher.group(3));
                    int s = Integer.parseInt(matcher.group(4));
                    parts.add(new Part(x, m, a, s));
                }
            } else {
                String[] nameAndRules = line.split("\\{");
                String name = nameAndRules[0];
                String[] ruleStringsAsArray = nameAndRules[1].substring(0, nameAndRules[1].length() - 1).split(",");
                List<Rule> rules = Arrays.stream(ruleStringsAsArray).map(Rule::new).toList();
                workflows.put(name, rules);
            }
        }
    }

    private static Range<Integer> getRemainingRange(Range<Integer> oldRange, Range<Integer> newRange) {
        if (Objects.equals(oldRange.v1, newRange.v1)) {
            return Tuple.range(newRange.v2 + 1, oldRange.v2);
        } else {
            return Tuple.range(oldRange.v1, newRange.v1 - 1);
        }
    }

    private Range<Integer> getNewRange(char comparator, int compareValue, Range<Integer> oldRange) {
        if (comparator == '>') {
            return Tuple.range(Math.max(compareValue + 1, oldRange.v1), oldRange.v2);
        } else {
            return Tuple.range(oldRange.v1, Math.min(compareValue - 1, oldRange.v2));
        }
    }

    private String runWorkflow(String workflowName, Part part) {
        for (Rule rule : workflows.get(workflowName)) {
            if (!rule.hasCondition) {
                return rule.resultingWorkflowName;
            }
            int valueToCheck = part.getRating(rule.varName);
            if (rule.conditionFulfilled(valueToCheck)) {
                return rule.resultingWorkflowName;
            }
        }
        throw new IllegalArgumentException();
    }


    private static class Rule {
        boolean hasCondition;
        char varName;
        char comparator;
        int compareValue;
        String resultingWorkflowName;

        public Rule(String ruleString) {
            if (!ruleString.contains(":")) {
                hasCondition = false;
                resultingWorkflowName = ruleString;
            } else {
                hasCondition = true;
                String[] conditionAndResultingWorkflowName = ruleString.split(":");
                varName = conditionAndResultingWorkflowName[0].charAt(0);
                comparator = conditionAndResultingWorkflowName[0].charAt(1);
                compareValue = Integer.parseInt(conditionAndResultingWorkflowName[0].substring(2));
                resultingWorkflowName = conditionAndResultingWorkflowName[1];
            }
        }

        public boolean conditionFulfilled(int valueToCheck) {
            switch (comparator) {
                case '>' -> {return valueToCheck > compareValue;}
                case '<' -> {return valueToCheck < compareValue;}
            }
            throw new IllegalArgumentException();
        }

    }

    private static class Part {
        int x;
        int m;
        int a;
        int s;
        public int getRating(char varName) {
            switch (varName) {
                case 'x' -> {return x;}
                case 'm' -> {return m;}
                case 'a' -> {return a;}
                case 's' -> {return s;}
                default -> throw new IllegalArgumentException();
            }
        }

        public Part(int x, int m, int a, int s) {
            this.x = x;
            this.m = m;
            this.a = a;
            this.s = s;
        }

        public int getSumOfRatings() {
            return x+m+a+s;
        }
    }
    private static class RatingRanges {
        int minX;
        int maxX;
        int minM;
        int maxM;
        int minA;
        int maxA;
        int minS;
        int maxS;

        public RatingRanges(int minX, int maxX, int minM, int maxM, int minA, int maxA, int minS, int maxS) {
            this.minX = minX;
            this.maxX = maxX;
            this.minM = minM;
            this.maxM = maxM;
            this.minA = minA;
            this.maxA = maxA;
            this.minS = minS;
            this.maxS = maxS;
        }

        public RatingRanges(RatingRanges r) {
            this(r.minX, r.maxX, r.minM, r.maxM, r.minA, r.maxA, r.minS, r.maxS);
        }

        public Range<Integer> getRange(char varName) {
            switch (varName) {
                case 'x' -> {return Tuple.range(minX, maxX);}
                case 'm' -> {return Tuple.range(minM, maxM);}
                case 'a' -> {return Tuple.range(minA, maxA);}
                case 's' -> {return Tuple.range(minS, maxS);}
                default -> throw new IllegalArgumentException();
            }
        }

        public void setRange(char varName, Range<Integer> range) {
            setRange(varName, range.v1, range.v2);
        }

        public void setRange(char varName, int min, int max) {
            switch (varName) {
                case 'x' -> {
                    minX = min;
                    maxX = max;
                }
                case 'm' -> {
                    minM = min;
                    maxM = max;
                }
                case 'a' -> {
                    minA = min;
                    maxA = max;
                }
                case 's' -> {
                    minS = min;
                    maxS = max;
                }
                default -> throw new IllegalArgumentException();
            }
        }

        public long getNumberOfPossibleCombinations() {
            return (long) (maxX - minX + 1) * (maxM - minM + 1) * (maxA - minA + 1) * (maxS - minS + 1);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            RatingRanges ranges = (RatingRanges) o;
            return minX == ranges.minX && maxX == ranges.maxX && minM == ranges.minM && maxM == ranges.maxM && minA == ranges.minA && maxA == ranges.maxA && minS == ranges.minS && maxS == ranges.maxS;
        }

        @Override
        public int hashCode() {
            return Objects.hash(minX, maxX, minM, maxM, minA, maxA, minS, maxS);
        }
    }
}