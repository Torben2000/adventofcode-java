package de.beachboys.aoc2016;

import de.beachboys.Day;
import org.javatuples.Pair;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.regex.Pattern;

public class Day07 extends Day {

    public Object part1(List<String> input) {
        return runLogic(input, this::supportsTLS);
    }

    public Object part2(List<String> input) {
        return runLogic(input, this::supportsSSL);
    }

    private int runLogic(List<String> input, BiPredicate<List<String>, List<String>> ipCheck) {
        int counter = 0;
        for (String ip : input) {
            Pair<List<String>, List<String>> hyperNetsAndSuperNets = parseIp(ip);
            if (ipCheck.test(hyperNetsAndSuperNets.getValue0(), hyperNetsAndSuperNets.getValue1())) {
                counter++;
            }
        }
        return counter;
    }

    private Pair<List<String>, List<String>> parseIp(String ip) {
        List<String> superNets = new ArrayList<>();
        List<String> hyperNets = new ArrayList<>();
        for (String subString : ip.split(Pattern.quote("["))) {
            if (subString.contains("]")) {
                String[] insideAndOutside = subString.split(Pattern.quote("]"));
                hyperNets.add(insideAndOutside[0]);
                superNets.add(insideAndOutside[1]);
            } else {
                superNets.add(subString);
            }
        }
        return Pair.with(hyperNets, superNets);
    }

    private boolean supportsTLS(List<String> hyperNets, List<String> superNets) {
        for (String hyperNet : hyperNets) {
            if (containsABBA(hyperNet)) {
                return false;
            }
        }
        for (String superNet : superNets) {
            if (containsABBA(superNet)) {
                return true;
            }
        }
        return false;
    }

    private boolean containsABBA(String netString) {
        for (int i = 0; i < netString.length() - 3; i++) {
            if (netString.charAt(i) == netString.charAt(i + 3) && netString.charAt(i + 1) == netString.charAt(i + 2) && netString.charAt(i) != netString.charAt(i + 1)) {
                return true;
            }
        }
        return false;
    }

    private boolean supportsSSL(List<String> superNets, List<String> hyperNets) {
        Set<Pair<Character, Character>> abPairs = new HashSet<>();
        for (String superNet : superNets) {
            abPairs.addAll(getABsForContainedABAs(superNet));
        }
        for (String hyperNet : hyperNets) {
            for (Pair<Character, Character> ab : abPairs) {
                if (containsBAB(hyperNet, ab)) {
                    return true;
                }
            }
        }

        return false;
    }

    private Set<Pair<Character, Character>> getABsForContainedABAs(String netString) {
        Set<Pair<Character, Character>> abPairs = new HashSet<>();
        for (int i = 0; i < netString.length() - 2; i++) {
            if (netString.charAt(i) == netString.charAt(i + 2) && netString.charAt(i) != netString.charAt(i + 1)) {
                abPairs.add(Pair.with(netString.charAt(i), netString.charAt(i + 1)));
            }
        }
        return abPairs;
    }

    private boolean containsBAB(String netString, Pair<Character, Character> ab) {
        for (int i = 0; i < netString.length() - 2; i++) {
            if (netString.charAt(i) == ab.getValue1() && netString.charAt(i + 1) == ab.getValue0() && netString.charAt(i + 2) == ab.getValue1()) {
                return true;
            }
        }
        return false;
    }

}
