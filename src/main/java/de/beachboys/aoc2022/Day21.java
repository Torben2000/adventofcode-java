package de.beachboys.aoc2022;

import de.beachboys.Day;
import org.javatuples.Triplet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public class Day21 extends Day {

    private final Map<String, Long> numbers = new HashMap<>();
    private final Map<String, Triplet<BiFunction<Long, Long, Long>, String, String>> operationsWithParameters = new HashMap<>();
    private final Map<String, BiFunction<Long, Long, Long>> reverseOperationsToCalculateParameter1 = new HashMap<>();
    private final Map<String, BiFunction<Long, Long, Long>> reverseOperationsToCalculateParameter2 = new HashMap<>();

    public Object part1(List<String> input) {
        parseInput(input);
        return getNumberOfMonkey("root");
    }

    public Object part2(List<String> input) {
        parseInput(input);

        numbers.remove("humn");
        Triplet<BiFunction<Long, Long, Long>, String, String> operationOfRoot = operationsWithParameters.get("root");
        String monkey1 = operationOfRoot.getValue1();
        String monkey2 = operationOfRoot.getValue2();
        long compareValue;
        boolean numberOfMonkey1Known = false;
        try {
            compareValue = getNumberOfMonkey(monkey1);
            numberOfMonkey1Known = true;
        } catch (NullPointerException e) {
            compareValue = getNumberOfMonkey(monkey2);
        }

        return getNumberToYell(compareValue, numberOfMonkey1Known ? monkey2 : monkey1);
    }

    private Long getNumberOfMonkey(String monkey) {
        if (numbers.containsKey(monkey)) {
            return numbers.get(monkey);
        }
        Triplet<BiFunction<Long, Long, Long>, String, String> operation = operationsWithParameters.get(monkey);
        Long value = operation.getValue0().apply(getNumberOfMonkey(operation.getValue1()), getNumberOfMonkey(operation.getValue2()));
        numbers.put(monkey, value);
        return value;
    }

    private Long getNumberToYell(long compareValue, String monkeyToYellCompareValue) {
        if ("humn".equals(monkeyToYellCompareValue)) {
            return compareValue;
        }
        Triplet<BiFunction<Long, Long, Long>, String, String> operation = operationsWithParameters.get(monkeyToYellCompareValue);
        try {
            BiFunction<Long, Long, Long> reverseOperationToCalculateParameter2 = reverseOperationsToCalculateParameter2.get(monkeyToYellCompareValue);
            long newCompareValue = reverseOperationToCalculateParameter2.apply(compareValue, getNumberOfMonkey(operation.getValue1()));
            return getNumberToYell(newCompareValue, operation.getValue2());
        } catch (NullPointerException e) {
            BiFunction<Long, Long, Long> reverseOperationToCalculateParameter1 = reverseOperationsToCalculateParameter1.get(monkeyToYellCompareValue);
            long newCompareValue = reverseOperationToCalculateParameter1.apply(compareValue, getNumberOfMonkey(operation.getValue2()));
            return getNumberToYell(newCompareValue, operation.getValue1());
        }
    }

    private void parseInput(List<String> input) {
        numbers.clear();
        operationsWithParameters.clear();
        reverseOperationsToCalculateParameter1.clear();
        reverseOperationsToCalculateParameter2.clear();
        for (String line : input) {
            String[] split = line.split(" ");
            String monkey = split[0].substring(0, 4);
            try {
                Long value = Long.valueOf(split[1]);
                numbers.put(monkey, value);
            } catch (NumberFormatException e) {
                BiFunction<Long, Long, Long> operation;
                BiFunction<Long, Long, Long> reverseOperationForParameter1;
                BiFunction<Long, Long, Long> reverseOperationForParameter2;
                String monkey1 = split[1];
                String monkey2 = split[3];
                switch (split[2]) {
                    case "+":
                        operation = (l1, l2) -> l1 + l2;
                        reverseOperationForParameter1 = (l1, l2) -> l1 - l2;
                        reverseOperationForParameter2 = (l1, l2) -> l1 - l2;
                        break;
                    case "-":
                        operation = (l1, l2) -> l1 - l2;
                        reverseOperationForParameter1 = (l1, l2) -> l1 + l2;
                        reverseOperationForParameter2 = (l1, l2) -> l2 - l1;
                        break;
                    case "*":
                        operation = (l1, l2) -> l1 * l2;
                        reverseOperationForParameter1 = (l1, l2) -> l1 / l2;
                        reverseOperationForParameter2 = (l1, l2) -> l1 / l2;
                        break;
                    case "/":
                        operation = (l1, l2) -> l1 / l2;
                        reverseOperationForParameter1 = (l1, l2) -> l1 * l2;
                        reverseOperationForParameter2 = (l1, l2) -> l2 / l1;
                        break;
                    default:
                        throw new IllegalArgumentException();

                }
                operationsWithParameters.put(monkey, Triplet.with(operation, monkey1, monkey2));
                reverseOperationsToCalculateParameter1.put(monkey, reverseOperationForParameter1);
                reverseOperationsToCalculateParameter2.put(monkey, reverseOperationForParameter2);
            }
        }
    }

}
