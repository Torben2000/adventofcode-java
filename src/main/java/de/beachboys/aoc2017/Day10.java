package de.beachboys.aoc2017;

import de.beachboys.Day;
import de.beachboys.Util;

import java.util.ArrayList;
import java.util.List;

public class Day10 extends Day {

    private int listLength = 256;
    private int skipSize = 0;
    private int currentPosition = 0;

    private List<Integer> list = new ArrayList<>();

    public Object part1(List<String> input) {
        listLength = Util.getIntValueFromUser("List length", 256, io);
        setInitialValues();
        List<Integer> inputLengths = Util.parseIntCsv(input.get(0));
        knotHash(inputLengths);
        return list.get(0) * list.get(1);
    }

    public Object part2(List<String> input) {
        setInitialValues();
        List<Integer> inputLengths = buildInputLengthsPart2(input);
        for (int i = 0; i < 64; i++) {
            knotHash(inputLengths);
        }
        List<Integer> denseHash = buildDenseHash();
        return buildHexString(denseHash);
    }

    private void knotHash(List<Integer> inputLengths) {
        List<Integer> oldList;
        for (Integer reverseLength : inputLengths) {
            oldList = list;
            list = new ArrayList<>(list);
            for (int i = 0; i < reverseLength; i++) {
                list.set((currentPosition + i) % listLength, oldList.get((currentPosition + reverseLength - 1 - i) % listLength));
            }
            currentPosition = (currentPosition + reverseLength + skipSize) % listLength;
            skipSize++;
        }
    }

    private List<Integer> buildInputLengthsPart2(List<String> input) {
        List<Integer> inputLengths = new ArrayList<>();
        for (int character : input.get(0).toCharArray()) {
            inputLengths.add(character);
        }
        inputLengths.addAll(List.of(17, 31, 73, 47, 23));
        return inputLengths;
    }

    private List<Integer> buildDenseHash() {
        List<Integer> denseHash = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
             denseHash.add(list.subList(i * 16, (i + 1) * 16).stream().reduce(0, (a, b) -> a ^ b));
        }
        return denseHash;
    }

    private String buildHexString(List<Integer> denseHash) {
        StringBuilder hex = new StringBuilder();
        denseHash.forEach(i -> hex.append(String.format("%02x", i)));
        return hex.toString();
    }

    private void setInitialValues() {
        list.clear();
        for (int i = 0; i < listLength; i++) {
            list.add(i);
        }
        currentPosition = 0;
        skipSize = 0;
    }

}
