package de.beachboys.aoc2024;

import de.beachboys.Day;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple4;

import java.util.*;

public class Day22 extends Day {

    public Object part1(List<String> input) {
        return getSecretNumbers(input).stream().map(List::getLast).mapToLong(Integer::longValue).sum();
    }

    public Object part2(List<String> input) {
        Map<Tuple4<Integer, Integer, Integer, Integer>, Long> bananasByChangePattern = new HashMap<>();

        for (List<Integer> priceList : getPriceLists(getSecretNumbers(input))) {
            Set<Tuple4<Integer, Integer, Integer, Integer>> seenKeys = new HashSet<>();
            for (int i = 0; i < priceList.size() - 4; i++) {
                Tuple4<Integer, Integer, Integer, Integer> key = Tuple.tuple(
                        priceList.get(i+1) - priceList.get(i),
                        priceList.get(i+2) - priceList.get(i+1),
                        priceList.get(i+3) - priceList.get(i+2),
                        priceList.get(i+4) - priceList.get(i+3));

                if (!seenKeys.contains(key)) {
                    seenKeys.add(key);
                    long bananas = priceList.get(i+4);
                    if (bananasByChangePattern.containsKey(key)) {
                        bananas += bananasByChangePattern.get(key);
                    }
                    bananasByChangePattern.put(key, bananas);
                }
            }
        }
        return bananasByChangePattern.values().stream().mapToLong(Long::longValue).max().orElseThrow();
    }

    private static List<List<Integer>> getPriceLists(List<List<Integer>> secretNumberLists) {
        List<List<Integer>> priceLists = new ArrayList<>();
        for (List<Integer> secretNumberList : secretNumberLists) {
            priceLists.add(secretNumberList.stream().map(i -> i % 10).toList());
        }
        return priceLists;
    }

    private List<List<Integer>> getSecretNumbers(List<String> input) {
        int secretNumbersToGenerate = Util.getIntValueFromUser("Secret numbers to generate", 2000, io);

        List<List<Integer>> secretNumberLists = new ArrayList<>();
        for (String line : input) {
            int secretNumber = Integer.parseInt(line);

            List<Integer> secretNumbers = new ArrayList<>();
            secretNumbers.add(secretNumber);
            for (int i = 0; i < secretNumbersToGenerate; i++) {
                secretNumber = getNextSecretNumber(secretNumber);
                secretNumbers.add(secretNumber);
            }
            secretNumberLists.add(secretNumbers);
        }
        return secretNumberLists;
    }

    private int getNextSecretNumber(int secretNumber) {
        int nextSecretNumber = mixAndPrune(secretNumber * 64, secretNumber);
        nextSecretNumber = mixAndPrune(nextSecretNumber / 32, nextSecretNumber);
        nextSecretNumber = mixAndPrune(nextSecretNumber * 2048, nextSecretNumber);
        return nextSecretNumber;
    }

    private int mixAndPrune(int value, int secretNumber) {
        return Util.modPositive(value ^ secretNumber, 16777216);
    }

}
