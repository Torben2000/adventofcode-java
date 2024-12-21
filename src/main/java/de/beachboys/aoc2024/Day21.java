package de.beachboys.aoc2024;

import de.beachboys.Day;
import de.beachboys.Direction;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.*;

public class Day21 extends Day {

    private final List<Map<String, Long>> robotInputLengthCache = new ArrayList<>();
    private final Map<Tuple2<String, String>, Set<String>> inputForKeyPressFromCurrentPosition = new HashMap<>();

    public Object part1(List<String> input) {
        return runLogic(input, 2+1);
    }

    public Object part2(List<String> input) {
        return runLogic(input, 25+1);
    }

    private long runLogic(List<String> input, int numberOfRobots) {
        initCaches(numberOfRobots);

        long result = 0;
        for (String line : input) {
            long minimumInputLength = getInputLength(line, numberOfRobots);
            result += Long.parseLong(line.substring(0, line.length() - 1)) * minimumInputLength;
        }
        return result;
    }

    private long getInputLength(String requiredButtonPresses, int robotsRemainingInLine) {
        if (robotsRemainingInLine == 0) {
            return requiredButtonPresses.length();
        }
        Map<String, Long> cache = robotInputLengthCache.get(robotsRemainingInLine);
        if (cache.containsKey(requiredButtonPresses)) {
            return cache.get(requiredButtonPresses);
        }
        String currentKey = "A";
        long inputLength = 0;
        for (String nextKey : requiredButtonPresses.split("")) {
            long currentInputLength = Long.MAX_VALUE;
            for (String inputForNextRobot : inputForKeyPressFromCurrentPosition.get(Tuple.tuple(currentKey, nextKey))) {
                currentInputLength = Math.min(currentInputLength, getInputLength(inputForNextRobot, robotsRemainingInLine - 1));
            }
            inputLength += currentInputLength;
            currentKey = nextKey;
        }
        cache.put(requiredButtonPresses, inputLength);
        return inputLength;
    }

    private void initCaches(int numberOfRobots) {
        List<String> numPad = List.of("789", "456", "123", " 0A");
        fillInputForKeyPressCache(Util.buildImageMap(numPad));

        List<String> dirPad = List.of(" ^A", "<v>");
        fillInputForKeyPressCache(Util.buildImageMap(dirPad));

        for (int robotIndex = 0; robotIndex <= numberOfRobots; robotIndex++) {
            robotInputLengthCache.add(new HashMap<>());
        }
    }

    private void fillInputForKeyPressCache(Map<Tuple2<Integer, Integer>, String> padMap) {
        for (String currentPosition : padMap.values()) {
            for (String keyToPress : padMap.values()) {
                if (!" ".equals(currentPosition) && !" ".equals(keyToPress)) {
                    inputForKeyPressFromCurrentPosition.put(Tuple.tuple(currentPosition, keyToPress), getFastestInputVariants(currentPosition, keyToPress, padMap));
                }
            }
        }
    }

    private Set<String> getFastestInputVariants(String currentPosition, String keyToPress, Map<Tuple2<Integer, Integer>, String> mapToUse) {
        Tuple2<Integer, Integer> current = mapToUse.entrySet().stream().filter(e -> currentPosition.equals(e.getValue())).map(Map.Entry::getKey).findFirst().orElseThrow();
        Tuple2<Integer, Integer> goal = mapToUse.entrySet().stream().filter(e -> keyToPress.equals(e.getValue())).map(Map.Entry::getKey).findFirst().orElseThrow();
        int xDiff = goal.v1 - current.v1;
        int yDiff = goal.v2 - current.v2;
        String horizontalInput = ">".repeat(Math.max(xDiff, 0)) + "<".repeat(Math.max(-xDiff, 0));
        String verticalInput = "v".repeat(Math.max(yDiff, 0)) + "^".repeat(Math.max(-yDiff, 0));

        Set<String> variants = new HashSet<>();
        Set<String> directionInputStrings = new HashSet<>();
        directionInputStrings.add(verticalInput + horizontalInput);
        directionInputStrings.add(horizontalInput + verticalInput);
        for (String directionInputString : directionInputStrings) {
            boolean possible = true;
            if (!directionInputString.isEmpty()) {
                Tuple2<Integer, Integer> pos = current;
                for (String nextKey : directionInputString.split("")) {
                    pos = Direction.fromString(nextKey).move(pos, 1);
                    if (" ".equals(mapToUse.get(pos))) {
                        possible = false;
                        break;
                    }
                }
            }
            if (possible) {
                variants.add(directionInputString+"A");
            }
        }
        return variants;
    }

}
