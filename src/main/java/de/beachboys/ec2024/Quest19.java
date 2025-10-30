package de.beachboys.ec2024;

import de.beachboys.Quest;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Quest19 extends Quest {

    private List<Boolean> messageKey;
    private int rotationPointYMax;
    private int rotationPointXMax;

    @Override
    public Object part1(List<String> input) {
        Map<Tuple2<Integer, Integer>, String> map = parseInputAndReturnMap(input);
        runRotationRound(map);
        return extractResult(map);
    }

    @Override
    public Object part2(List<String> input) {
        Map<Tuple2<Integer, Integer>, String> map = parseInputAndReturnMap(input);
        for (int i = 0; i < 100; i++) {
            runRotationRound(map);
        }
        return extractResult(map);
    }

    @Override
    public Object part3(List<String> input) {
        int numberOfRounds = Util.getIntValueFromUser("Number of rounds", 1048576000, io);
        Map<Tuple2<Integer, Integer>, String> map = parseInputAndReturnMap(input);

        Map<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>> positionMap = new HashMap<>();
        for (Tuple2<Integer, Integer> pos : map.keySet()) {
            positionMap.put(pos, pos);
        }
        runRotationRound(positionMap);

        List<Long> primeFactors = Util.getPrimeFactors(numberOfRounds);
        for (Long primeFactor : primeFactors) {
            positionMap = combineRounds(positionMap, primeFactor.intValue());
        }

        // needs to be called an odd number of times to work
        if (primeFactors.size() % 2 == 0) {
            positionMap = combineRounds(positionMap, 1);
        }

        Map<Tuple2<Integer, Integer>, String> finalMap = new HashMap<>();
        for (Tuple2<Integer, Integer> pos : positionMap.keySet()) {
            finalMap.put(positionMap.get(pos), map.get(pos));
        }
        return extractResult(finalMap);

    }

    private <T> void runRotationRound(Map<Tuple2<Integer, Integer>, T> map) {
        int messageKeyIndex = 0;
        for (int y = 1; y < rotationPointYMax; y++) {
            for (int x = 1; x < rotationPointXMax; x++) {
                rotateAroundPoint(map, Tuple.tuple(x, y), messageKey.get(messageKeyIndex));
                messageKeyIndex = (messageKeyIndex + 1) % messageKey.size();
            }
        }
    }

    private <T> void rotateAroundPoint(Map<Tuple2<Integer, Integer>, T> map, Tuple2<Integer, Integer> rotationPoint, boolean leftRotation) {
        T storedValue = map.get(Tuple.tuple(rotationPoint.v1-1, rotationPoint.v2-1));
        if (leftRotation) {
            map.put(Tuple.tuple(rotationPoint.v1-1, rotationPoint.v2-1), map.get(Tuple.tuple(rotationPoint.v1, rotationPoint.v2-1)));
            map.put(Tuple.tuple(rotationPoint.v1, rotationPoint.v2-1), map.get(Tuple.tuple(rotationPoint.v1+1, rotationPoint.v2-1)));
            map.put(Tuple.tuple(rotationPoint.v1+1, rotationPoint.v2-1), map.get(Tuple.tuple(rotationPoint.v1+1, rotationPoint.v2)));
            map.put(Tuple.tuple(rotationPoint.v1+1, rotationPoint.v2), map.get(Tuple.tuple(rotationPoint.v1+1, rotationPoint.v2+1)));
            map.put(Tuple.tuple(rotationPoint.v1+1, rotationPoint.v2+1), map.get(Tuple.tuple(rotationPoint.v1, rotationPoint.v2+1)));
            map.put(Tuple.tuple(rotationPoint.v1, rotationPoint.v2+1), map.get(Tuple.tuple(rotationPoint.v1-1, rotationPoint.v2+1)));
            map.put(Tuple.tuple(rotationPoint.v1-1, rotationPoint.v2+1), map.get(Tuple.tuple(rotationPoint.v1-1, rotationPoint.v2)));
            map.put(Tuple.tuple(rotationPoint.v1-1, rotationPoint.v2), storedValue);
        } else {
            map.put(Tuple.tuple(rotationPoint.v1-1, rotationPoint.v2-1), map.get(Tuple.tuple(rotationPoint.v1-1, rotationPoint.v2)));
            map.put(Tuple.tuple(rotationPoint.v1-1, rotationPoint.v2), map.get(Tuple.tuple(rotationPoint.v1-1, rotationPoint.v2+1)));
            map.put(Tuple.tuple(rotationPoint.v1-1, rotationPoint.v2+1), map.get(Tuple.tuple(rotationPoint.v1, rotationPoint.v2+1)));
            map.put(Tuple.tuple(rotationPoint.v1, rotationPoint.v2+1), map.get(Tuple.tuple(rotationPoint.v1+1, rotationPoint.v2+1)));
            map.put(Tuple.tuple(rotationPoint.v1+1, rotationPoint.v2+1), map.get(Tuple.tuple(rotationPoint.v1+1, rotationPoint.v2)));
            map.put(Tuple.tuple(rotationPoint.v1+1, rotationPoint.v2), map.get(Tuple.tuple(rotationPoint.v1+1, rotationPoint.v2-1)));
            map.put(Tuple.tuple(rotationPoint.v1+1, rotationPoint.v2-1), map.get(Tuple.tuple(rotationPoint.v1, rotationPoint.v2-1)));
            map.put(Tuple.tuple(rotationPoint.v1, rotationPoint.v2-1), storedValue);
        }
    }

    private Map<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>> combineRounds(Map<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>> positionMap, int n) {
        Map<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>> combinedMap = new HashMap<>();
        for (Tuple2<Integer, Integer> pos : positionMap.keySet()) {
            combinedMap.put(pos, pos);
        }
        for (int i = 0; i < n; i++) {
            Map<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>> tempMap = new HashMap<>();
            for (Tuple2<Integer, Integer> pos : positionMap.keySet()) {
                tempMap.put(positionMap.get(pos), combinedMap.get(pos));
            }
            combinedMap = tempMap;
        }
        return combinedMap;
    }

    private String extractResult(Map<Tuple2<Integer, Integer>, String> map) {
        String mapAsString = Util.paintMap(map);
        return mapAsString.substring(mapAsString.indexOf('>') + 1, mapAsString.indexOf('<'));
    }

    private Map<Tuple2<Integer, Integer>, String> parseInputAndReturnMap(List<String> input) {
        messageKey = input.getFirst().chars().mapToObj(c -> 'L' == c).toList();
        rotationPointYMax = input.size() - 1 - 2;
        rotationPointXMax = input.get(2).length() - 1;
        return Util.buildImageMap(input.subList(2, input.size()));
    }


}
