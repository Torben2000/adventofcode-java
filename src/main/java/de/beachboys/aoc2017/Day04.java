package de.beachboys.aoc2017;

import de.beachboys.Day;
import de.beachboys.Util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day04 extends Day {

    public Object part1(List<String> input) {
        return runLogic(input, word -> word);
    }

    public Object part2(List<String> input) {
        return runLogic(input, word -> word.codePoints().sorted().collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString());
    }

    private int runLogic(List<String> input, Function<String, String> modifyWordForDuplicityCheck) {
        int numOfValidPassphrases = 0;
        for (String passPhrase : input) {
            List<String> wordList = Util.parseToList(passPhrase, " ").stream().map(modifyWordForDuplicityCheck).collect(Collectors.toList());
            Set<String> wordSet = new HashSet<>(wordList);
            if (wordList.size() == wordSet.size()) {
                numOfValidPassphrases++;
            }
        }
        return numOfValidPassphrases;
    }

}
