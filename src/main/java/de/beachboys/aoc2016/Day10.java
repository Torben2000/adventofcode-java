package de.beachboys.aoc2016;

import de.beachboys.Day;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day10 extends Day {

    enum TargetType {
        BOT, OUTPUT
    }

    private final Map<Integer, Set<Integer>> botInputs = new HashMap<>();
    private final Map<Integer, Integer> outputValues = new HashMap<>();

    public Object part1(List<String> input) {
        Integer input1 = Util.getIntValueFromUser("First chip value", 61, io);
        Integer input2 = Util.getIntValueFromUser("Second chip value", 17, io);
        runLogic(input);
        return botInputs.entrySet().stream().filter(e -> e.getValue().equals(Set.of(input1, input2))).map(Map.Entry::getKey).findFirst().orElseThrow();
    }

    public Object part2(List<String> input) {
        runLogic(input);
        return outputValues.get(0) * outputValues.get(1) * outputValues.get(2);
    }

    private void runLogic(List<String> input) {
        Map<Integer, Tuple2<Tuple2<TargetType, Integer>, Tuple2<TargetType, Integer>>> botRules = buildRulesAndFillInitialInput(input);

        Set<Integer> processedBots = new HashSet<>();
        while (processedBots.size() < botRules.size()) {
            Set<Integer> botsToProcess = botInputs.entrySet().stream().filter(e -> !processedBots.contains(e.getKey()) && e.getValue().size() == 2).map(Map.Entry::getKey).collect(Collectors.toSet());
            for (Integer bot : botsToProcess) {
                Integer lowValue = botInputs.get(bot).stream().min(Comparator.naturalOrder()).orElseThrow();
                Integer highValue = botInputs.get(bot).stream().max(Comparator.naturalOrder()).orElseThrow();
                setTargetValue(botRules.get(bot).v1, lowValue);
                setTargetValue(botRules.get(bot).v2, highValue);
                processedBots.add(bot);
            }
        }
    }

    private Map<Integer, Tuple2<Tuple2<TargetType, Integer>, Tuple2<TargetType, Integer>>> buildRulesAndFillInitialInput(List<String> input) {
        Map<Integer, Tuple2<Tuple2<TargetType, Integer>, Tuple2<TargetType, Integer>>> botRules = new HashMap<>();
        Pattern inputPattern = Pattern.compile("value ([0-9]+) goes to bot ([0-9]+)");
        Pattern rulePattern = Pattern.compile("bot ([0-9]+) gives low to (bot|output) ([0-9]+) and high to (bot|output) ([0-9]+)");
        for (String line : input) {
            if (line.startsWith("bot")) {
                Matcher m = rulePattern.matcher(line);
                if (m.matches()) {
                    int bot = Integer.parseInt(m.group(1));
                    TargetType lowTargetType = TargetType.valueOf(m.group(2).toUpperCase());
                    int lowTarget = Integer.parseInt(m.group(3));
                    TargetType highTargetType = TargetType.valueOf(m.group(4).toUpperCase());
                    int highTarget = Integer.parseInt(m.group(5));
                    botRules.put(bot, Tuple.tuple(Tuple.tuple(lowTargetType, lowTarget), Tuple.tuple(highTargetType, highTarget)));
                    botInputs.putIfAbsent(bot, new HashSet<>());
                }
            } else {
                Matcher m = inputPattern.matcher(line);
                if (m.matches()) {
                    int value = Integer.parseInt(m.group(1));
                    int bot = Integer.parseInt(m.group(2));
                    botInputs.putIfAbsent(bot, new HashSet<>());
                    botInputs.get(bot).add(value);
                }
            }
        }
        return botRules;
    }

    private void setTargetValue(Tuple2<TargetType, Integer> target, Integer value) {
        if (TargetType.BOT.equals(target.v1)) {
            botInputs.get(target.v2).add(value);
        } else {
            outputValues.put(target.v2, value);
        }
    }

}
