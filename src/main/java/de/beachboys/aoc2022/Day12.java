package de.beachboys.aoc2022;

import de.beachboys.Day;
import de.beachboys.Direction;
import de.beachboys.Util;
import org.javatuples.Pair;

import java.util.*;

public class Day12 extends Day {

    public Object part1(List<String> input) {
        return runLogic(input, Set.of('S'));
    }

    public Object part2(List<String> input) {
        return runLogic(input, Set.of('S', 'a'));
   }

    private static int runLogic(List<String> input, Set<Character> startCharacters) {
        Map<Pair<Integer, Integer>, String> map = Util.buildImageMap(input);
        Map<Character, Set<Pair<Integer, Integer>>> characterToPositionMap = getCharacterToPositionMap(input);

        Set<Pair<Integer, Integer>> start = new HashSet<>();
        for (Character startCharacter : startCharacters) {
            start.addAll(characterToPositionMap.get(startCharacter));
        }
        Set<Pair<Integer, Integer>> end = characterToPositionMap.get('E');

        for (Pair<Integer, Integer> ePosition : characterToPositionMap.get('E')) {
            map.put(ePosition, "z");
        }
        for (Pair<Integer, Integer> sPosition : characterToPositionMap.get('S')) {
            map.put(sPosition, "a");
        }

        Map<Pair<Integer, Integer>, Integer> distances = new HashMap<>();
        Map<Integer, Set<Pair<Integer, Integer>>> queue = new HashMap<>();
        queue.put(0, start);
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            for (Pair<Integer, Integer> pos : queue.getOrDefault(i, Set.of())) {
                if (end.contains(pos)) {
                    return i;
                }
                char currentChar = map.get(pos).charAt(0);
                for (Pair<Integer, Integer> directNeighbor : Direction.getDirectNeighbors(pos)) {
                    if (!distances.containsKey(directNeighbor)) {
                        String neighborString = map.getOrDefault(directNeighbor, "");
                        if (!neighborString.isEmpty()) {
                            char neighborChar = neighborString.charAt(0);
                            if (neighborChar <= currentChar + 1) {
                                Set<Pair<Integer, Integer>> set = queue.getOrDefault(i + 1, new HashSet<>());
                                set.add(directNeighbor);
                                queue.put(i + 1, set);
                                distances.put(directNeighbor, i + 1);
                            }
                        }
                    }
                }
            }
        }

        return Integer.MAX_VALUE;
    }

    private static Map<Character, Set<Pair<Integer, Integer>>> getCharacterToPositionMap(List<String> input) {
        Map<Character, Set<Pair<Integer, Integer>>> characterToPositionMap = new HashMap<>();
        for (int i = 0; i < input.size(); i++) {
            String line = input.get(i);
            for (int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);
                Set<Pair<Integer, Integer>> set = characterToPositionMap.getOrDefault(c, new HashSet<>());
                set.add(Pair.with(j, i));
                characterToPositionMap.put(c, set);
            }
        }
        return characterToPositionMap;
    }

}
