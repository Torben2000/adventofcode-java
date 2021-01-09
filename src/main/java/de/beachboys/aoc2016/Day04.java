package de.beachboys.aoc2016;

import com.google.common.collect.ComparisonChain;
import de.beachboys.Day;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day04 extends Day {

    public Object part1(List<String> input) {
        return runLogic(input, (roomName, sectorId) -> true);
    }

    public Object part2(List<String> input) {
        return runLogic(input, this::isNorthPoleRoom);
    }

    private int runLogic(List<String> input, BiPredicate<String, Integer> isRelevantRoom) {
        int sum = 0;
        for (String line : input) {
            sum += getSectorIdIfRelevantRoom(line, isRelevantRoom);
        }
        return sum;
    }

    private int getSectorIdIfRelevantRoom(String roomId, BiPredicate<String, Integer> isRelevantRoom) {
        Matcher m = Pattern.compile("([a-z-]+)-([0-9]+)\\[([a-z]+)]").matcher(roomId);
        if (m.matches()) {
            String roomName = m.group(1);
            int sectorId = Integer.parseInt(m.group(2));
            String checksum = m.group(3);
            if (isRealRoom(roomName, checksum) && isRelevantRoom.test(roomName, sectorId)) {
                return sectorId;
            }
        }
        return 0;
    }

    private boolean isNorthPoleRoom(String roomName, int sectorId) {
        String realRoomName = getRealRoomName(roomName, sectorId);
        return realRoomName.contains("northpole");
    }

    private String getRealRoomName(String roomName, int sectorId) {
        int shift = sectorId % 26;
        StringBuilder realRoomNameBuilder = new StringBuilder();
        for (char character : roomName.toCharArray()) {
            if (character == '-') {
                realRoomNameBuilder.append(" ");
            } else {
                int newCharacter = character + shift;
                if (newCharacter > 'z') {
                    newCharacter = newCharacter - 26;
                }
                realRoomNameBuilder.append((char) newCharacter);
            }
        }
        String realRoomName = realRoomNameBuilder.toString();
        io.logDebug(realRoomName);
        return realRoomName;
    }

    private boolean isRealRoom(String roomName, String checksum) {
        Map<Character, Integer> charCounter = new HashMap<>();
        for (char character : roomName.toCharArray()) {
            if (character != '-') {
                charCounter.put(character, charCounter.getOrDefault(character, 0) + 1);
            }
        }
        List<Character> sortedCharacters = charCounter.entrySet().stream().sorted((e1, e2) ->
                ComparisonChain.start()
                        .compare(e2.getValue(), e1.getValue())
                        .compare(e1.getKey(), e2.getKey())
                        .result())
                .map(Map.Entry::getKey).collect(Collectors.toList());
        for (int i = 0; i < checksum.length(); i++) {
            if (checksum.charAt(i) != sortedCharacters.get(i)) {
                return false;
            }
        }
        return true;
    }

}
