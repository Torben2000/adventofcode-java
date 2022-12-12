package de.beachboys.aoc2022;

import de.beachboys.Day;
import de.beachboys.Util;
import org.javatuples.Pair;

import java.util.*;
import java.util.function.BiPredicate;

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

        BiPredicate<Pair<Integer, Integer>, Pair<Integer, Integer>> canGoFromPositionToNeighbor = ((position, neighbor) -> {
            String neighborString = map.getOrDefault(neighbor, "");
            if (!neighborString.isEmpty()) {
                char neighborChar = neighborString.charAt(0);
                char currentChar = map.get(position).charAt(0);
                return neighborChar <= currentChar + 1;
            }
            return false;
        });

        return Util.getShortestPath(start, end, canGoFromPositionToNeighbor, ((positionDistance, position, neighbor) -> positionDistance + 1));
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
