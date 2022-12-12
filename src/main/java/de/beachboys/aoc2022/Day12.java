package de.beachboys.aoc2022;

import de.beachboys.Day;
import de.beachboys.Direction;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day12 extends Day {

    public Object part1(List<String> input) {
        Map<Character, List<Pair<Integer, Integer>>> characterToPositionMap = getCharacterToPositionMap(input);
        Map<Pair<Integer, Integer>, Pair<Character, Integer>> positionToCharacterDistanceMap = getPositionToCharacterDistanceMap(characterToPositionMap);

        return positionToCharacterDistanceMap.get(characterToPositionMap.get('S').get(0)).getValue1();
    }

    public Object part2(List<String> input) {
        Map<Character, List<Pair<Integer, Integer>>> characterToPositionMap = getCharacterToPositionMap(input);
        Map<Pair<Integer, Integer>, Pair<Character, Integer>> positionToCharacterDistanceMap = getPositionToCharacterDistanceMap(characterToPositionMap);

        int result = positionToCharacterDistanceMap.get(characterToPositionMap.get('S').get(0)).getValue1();
        for (Pair<Integer, Integer> position : characterToPositionMap.get('a')) {
            result = Math.min(result, positionToCharacterDistanceMap.get(position).getValue1());
        }
        return result;
    }

    private static Map<Pair<Integer, Integer>, Pair<Character, Integer>> getPositionToCharacterDistanceMap(Map<Character, List<Pair<Integer, Integer>>> characterToPositionMap) {
        Map<Pair<Integer, Integer>, Pair<Character, Integer>> positionToCharacterDistanceMap = new HashMap<>();

        Pair<Integer, Integer> end = characterToPositionMap.get('E').get(0);
        positionToCharacterDistanceMap.put(end, Pair.with('z', 0));

        boolean anyChangeOccurred = true;
        while (anyChangeOccurred) {
            anyChangeOccurred = false;
            for (char character = 'z'; character >= 'a'; character--) {
                List<Pair<Integer, Integer>> positionsOfCharacter = characterToPositionMap.get(character);
                boolean changeForCharacterOccurred = true;
                while (changeForCharacterOccurred) {
                    changeForCharacterOccurred = updatePositionToDistanceMapForCharacter(positionToCharacterDistanceMap, character, positionsOfCharacter);
                    anyChangeOccurred |= changeForCharacterOccurred;
                }
            }
        }

        Pair<Integer, Integer> pos = characterToPositionMap.get('S').get(0);
        positionToCharacterDistanceMap.put(pos, Pair.with('S', getLowestDistanceValue(positionToCharacterDistanceMap, 'a', pos)));

        return positionToCharacterDistanceMap;
    }

    private static boolean updatePositionToDistanceMapForCharacter(Map<Pair<Integer, Integer>, Pair<Character, Integer>> positionToCharacterDistanceMap, char character, List<Pair<Integer, Integer>> positionsOfCharacter) {
        boolean changeForCharacterOccurred;
        changeForCharacterOccurred = false;
        for (Pair<Integer, Integer> position : positionsOfCharacter) {
            int low = getLowestDistanceValue(positionToCharacterDistanceMap, character, position);

            if (positionToCharacterDistanceMap.containsKey(position)) {
                int oldLow = positionToCharacterDistanceMap.get(position).getValue1();
                low = Math.min(low, oldLow);
                if (oldLow > low) {
                    changeForCharacterOccurred = true;
                }
            } else {
                changeForCharacterOccurred = true;
            }

            positionToCharacterDistanceMap.put(position, Pair.with(character, low));
        }
        return changeForCharacterOccurred;
    }

    private static int getLowestDistanceValue(Map<Pair<Integer, Integer>, Pair<Character, Integer>> positionToCharacterDistanceMap, char character, Pair<Integer, Integer> position) {
        int low = Integer.MAX_VALUE / 2;
        for (Pair<Integer, Integer> directNeighbor : Direction.getDirectNeighbors(position)) {
            if (positionToCharacterDistanceMap.containsKey(directNeighbor)) {
                Pair<Character, Integer> pair = positionToCharacterDistanceMap.get(directNeighbor);
                if (character >= pair.getValue0() - 1) {
                    low = Math.min(low, pair.getValue1() + 1);
                }
            }
        }
        return low;
    }

    private static Map<Character, List<Pair<Integer, Integer>>> getCharacterToPositionMap(List<String> input) {
        Map<Character, List<Pair<Integer, Integer>>> characterToPositionMap = new HashMap<>();
        for (int i = 0; i < input.size(); i++) {
            String line = input.get(i);
            for (int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);
                List<Pair<Integer, Integer>> list = characterToPositionMap.getOrDefault(c, new ArrayList<>());
                list.add(Pair.with(j, i));
                characterToPositionMap.put(c, list);
            }
        }
        return characterToPositionMap;
    }

}
