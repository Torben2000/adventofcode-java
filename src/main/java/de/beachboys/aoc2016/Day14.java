package de.beachboys.aoc2016;

import de.beachboys.Day;
import de.beachboys.Util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Day14 extends Day {

    private final MessageDigest md5;
    private final List<Integer> found = new LinkedList<>();
    private final Map<Character, List<Integer>> candidates = new HashMap<>();
    private int maxIndex = Integer.MAX_VALUE;
    private String salt;
    private Function<String, String> manipulateHashValue = hashValue -> hashValue;

    public Day14() {
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException();
        }
        for (char character : "0123456789abcdef".toCharArray()) {
            candidates.put(character, new LinkedList<>());
        }
    }

    public Object part1(List<String> input) {
        return runLogic(input);
    }

    public Object part2(List<String> input) {
        manipulateHashValue = hashValue -> {
            String localHashValue = hashValue;
            for (int i = 0; i < 2016; i++) {
                localHashValue = Util.bytesToHex(md5.digest(localHashValue.getBytes()));
            }
            return localHashValue;
        };
        return runLogic(input);
    }

    private int runLogic(List<String> input) {
        salt = input.getFirst();
        int index = 0;
        while (index < maxIndex) {
            handleIndex(++index);
        }
        return found.stream().sorted().toList().get(63);
    }

    private void handleIndex(int index) {
        String hashValue = Util.bytesToHex(md5.digest((salt + index).getBytes()));
        hashValue = manipulateHashValue.apply(hashValue);
        char currentChar = '@';
        char currentCharCounter = 0;
        boolean tripletFound = false;
        char[] charArray = hashValue.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];
            if (c == currentChar) {
                currentCharCounter++;
            }
            if (c != currentChar || i == charArray.length - 1) {
                if (currentCharCounter >= 5) {
                    handleFiveCharactersInRow(index, currentChar);
                }
                if (!tripletFound && currentCharCounter >= 3) {
                    candidates.get(currentChar).add(index);
                    tripletFound = true;
                }
                currentCharCounter = 1;
                currentChar = c;
            }
        }
    }

    private void handleFiveCharactersInRow(int index, char c) {
        List<Integer> candidateSet = candidates.get(c);
        List<Integer> valid = candidateSet.stream().filter(l -> l >= index - 1000).toList();
        found.addAll(valid);
        if (found.size() >= 64 && maxIndex == Integer.MAX_VALUE) {
            maxIndex = index;
        }
        candidateSet.clear();
    }

}
