package de.beachboys.aoc2022;

import de.beachboys.Day;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day11 extends Day {

    public Object part1(List<String> input) {
        return runLogic(input, 20, true);
    }

    public Object part2(List<String> input) {
        return runLogic(input, 10000, false);
    }

    private static long runLogic(List<String> input, int rounds, boolean divideByThree) {
        List<Monkey> monkeys = parseMonkeyList(input);

        long simplifyingDivisor = 1;
        for (Monkey monkey : monkeys) {
            simplifyingDivisor *= monkey.divisorForTest;
        }

        for (int i = 0; i < rounds; i++) {
            for (Monkey monkey : monkeys) {
                for (Long item : monkey.items) {
                    long newValue = monkey.operation.apply(item) % simplifyingDivisor;
                    if (divideByThree) {
                        newValue /= 3;
                    }

                    if (newValue % monkey.divisorForTest == 0) {
                        monkeys.get(monkey.targetIfTestSucceeds).items.add(newValue);
                    } else {
                        monkeys.get(monkey.targetIfTestFails).items.add(newValue);
                    }

                    monkey.itemCounter++;
                }
                monkey.items.clear();
            }
        }

        List<Monkey> sortedMonkeys = monkeys.stream().sorted(Comparator.comparingLong(Monkey::getItemCounter).reversed()).collect(Collectors.toList());
        return sortedMonkeys.get(0).getItemCounter() * sortedMonkeys.get(1).getItemCounter();
    }

    private static List<Monkey> parseMonkeyList(List<String> input) {
        List<Monkey> monkeys = new ArrayList<>();
        for (int i = 0; i < input.size(); i += 7) {
            Monkey monkey = new Monkey();

            String items = input.get(i + 1).substring("  Starting items: ".length());
            for (String itemString : items.split(", ")) {
                monkey.items.add(Long.parseLong(itemString));
            }

            String operationString = input.get(i + 2).substring("  Operation: new = old ".length());
            if ("* old".equals(operationString)) {
                monkey.operation = (value -> value * value);
            } else if (operationString.startsWith("*")) {
                long operationArgument = Long.parseLong(operationString.substring("* ".length()));
                monkey.operation = (value -> value * operationArgument);
            } else {
                long operationArgument = Long.parseLong(operationString.substring("+ ".length()));
                monkey.operation = (value -> value + operationArgument);
            }

            String divisorString = input.get(i + 3).substring("  Test: divisible by ".length());
            monkey.divisorForTest = Integer.parseInt(divisorString);

            String trueString = input.get(i + 4).substring("    If true: throw to monkey ".length());
            monkey.targetIfTestSucceeds = Integer.parseInt(trueString);

            String falseString = input.get(i + 5).substring("    If false: throw to monkey ".length());
            monkey.targetIfTestFails = Integer.parseInt(falseString);

            monkeys.add(monkey);
        }
        return monkeys;
    }

    private static class Monkey {
        List<Long> items = new ArrayList<>();
        Function<Long, Long> operation;
        int divisorForTest;
        int targetIfTestSucceeds;
        int targetIfTestFails;
        long itemCounter = 0;

        public long getItemCounter() {
            return itemCounter;
        }
    }

}
