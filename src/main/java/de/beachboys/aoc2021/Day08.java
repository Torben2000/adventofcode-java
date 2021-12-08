package de.beachboys.aoc2021;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import de.beachboys.Day;
import de.beachboys.Util;

import java.util.*;

public class Day08 extends Day {

    public Object part1(List<String> input) {
        long count = 0L;
        List<Integer> numbers = getListOfOutputNumbers(input);
        for (Integer number : numbers) {
            String numberString = String.valueOf(number);
            for (char c : numberString.toCharArray()) {
                switch (c) {
                    case '1':
                    case '4':
                    case '7':
                    case '8':
                        count++;
                        break;
                    default:
                        // do nothing
                }
            }
        }

        return count;

    }

    public Object part2(List<String> input) {
        return getListOfOutputNumbers(input).stream().mapToLong(value -> value).sum();
    }

    private List<Integer> getListOfOutputNumbers(List<String> input) {
        List<Integer> numbers = new ArrayList<>();
        for (String line : input) {
            BiMap<Integer, Set<Character>> digits = HashBiMap.create();

            String[] splitPatternsAndOutput = line.split("\\|");
            List<Set<Character>> patterns = new ArrayList<>();
            for (String pattern : Util.parseToList(splitPatternsAndOutput[0], " ")) {
                if (!pattern.isBlank()) {
                    patterns.add(getCharacters(pattern));
                }
            }

            List<String> outputValues = new ArrayList<>(Util.parseToList(splitPatternsAndOutput[1], " "));
            outputValues.removeIf(String::isBlank);


            while (digits.size() < 10) {
                for (Set<Character> pattern : patterns) {
                    if (!digits.containsValue(pattern)) {
                        switch (pattern.size()) {
                            case 2:
                                digits.put(1, pattern);
                                break;
                            case 3:
                                digits.put(7, pattern);
                                break;
                            case 7:
                                digits.put(8, pattern);
                                break;
                            case 4:
                                digits.put(4, pattern);
                                break;
                            case 5:
                                if (digits.containsKey(1) && digits.containsKey(4)) {
                                    Set<Character> setCopy = new HashSet<>(pattern);
                                    if (!digits.containsKey(2)) {
                                        setCopy.removeAll(digits.get(4));
                                        if (setCopy.size() == 3) {
                                            digits.put(2, pattern);
                                        }
                                    } else if (!digits.containsKey(3)) {
                                        setCopy.removeAll(digits.get(1));
                                        if (setCopy.size() == 3) {
                                            digits.put(3, pattern);
                                        }
                                    } else {
                                        digits.put(5, pattern);
                                    }
                                }
                                break;
                            case 6:
                                if (digits.containsKey(1) && digits.containsKey(4)) {
                                    HashSet<Character> setCopy = new HashSet<>(pattern);
                                    if (!digits.containsKey(6)) {
                                        setCopy.removeAll(digits.get(1));
                                        if (setCopy.size() == 5) {
                                            digits.put(6, pattern);
                                        }
                                    } else if (!digits.containsKey(9)) {
                                        setCopy.removeAll(digits.get(4));
                                        if (setCopy.size() == 2) {
                                            digits.put(9, pattern);
                                        }
                                    } else {
                                        digits.put(0, pattern);
                                    }
                                }
                                break;
                            default:
                                throw new IllegalArgumentException();
                        }
                    }
                }
            }
            numbers.add(buildOutputNumber(digits.inverse(), outputValues));
        }
        return numbers;
    }

    private int buildOutputNumber(Map<Set<Character>, Integer> digitMap, List<String> outputValues) {
        int number = 0;
        for (String num : outputValues) {
            Set<Character> set = getCharacters(num);
            number *= 10;
            number += digitMap.get(set);
        }
        return number;
    }

    private Set<Character> getCharacters(String num) {
        HashSet<Character> set = new HashSet<>();
        for (int i = 0; i < num.length(); i++) {
            char c = num.charAt(i);
            set.add(c);
        }
        return set;
    }

}
