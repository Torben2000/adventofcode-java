package de.beachboys.aoc2022;

import de.beachboys.Day;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day03 extends Day {

    public Object part1(List<String> input) {
        long result = 0;
        for (String rucksackLine : input) {
            Set<Character> firstCompartment = new HashSet<>();
            Set<Character> secondCompartment = new HashSet<>();
            for (int j = 0; j < rucksackLine.length(); j++) {
                Character c = rucksackLine.charAt(j);
                if (j < rucksackLine.length() / 2) {
                    firstCompartment.add(c);
                } else {
                    secondCompartment.add(c);
                }
            }
            for (Character c : firstCompartment) {
                if (secondCompartment.contains(c)) {
                    result += getCharacterValue(c);
                }
            }

        }
        return result;
    }

    public Object part2(List<String> input) {
        long result = 0;
        for (int i = 0; i < input.size(); i += 3) {
            String rucksackLine1 = input.get(i);
            String rucksackLine2 = input.get(i + 1);
            String rucksackLine3 = input.get(i + 2);
            Set<Character> rucksack1 = new HashSet<>();
            Set<Character> rucksack2 = new HashSet<>();
            Set<Character> rucksack3 = new HashSet<>();
            for (int j = 0; j < rucksackLine1.length(); j++) {
                rucksack1.add(rucksackLine1.charAt(j));
            }
            for (int j = 0; j < rucksackLine2.length(); j++) {
                rucksack2.add(rucksackLine2.charAt(j));
            }
            for (int j = 0; j < rucksackLine3.length(); j++) {
                rucksack3.add(rucksackLine3.charAt(j));
            }
            for (Character c : rucksack1) {
                if (rucksack2.contains(c) && rucksack3.contains(c)) {
                    result += getCharacterValue(c);
                }
            }

        }
        return result;
    }

    private static int getCharacterValue(char c) {
        int value;
        if (c >= 'a' && c <= 'z') {
            value = c - 'a' + 1;
        } else {
            value = c - 'A' + 27;
        }
        return value;
    }

}
