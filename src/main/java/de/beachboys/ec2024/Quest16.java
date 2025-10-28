package de.beachboys.ec2024;

import de.beachboys.Quest;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Quest16 extends Quest {

    private List<List<String>> catsOnWheels = new ArrayList<>();
    private List<Integer> positionChangesPerLeverPull;
    private final Map<Tuple2<List<Integer>, Integer>, Tuple2<Long, Long>> maxMinCoinsCache = new HashMap<>();

    @Override
    public Object part1(List<String> input) {
        List<Integer> wheelPositions = parseInputAndInitWheelPositions(input);

        for (int i = 0; i < 100; i++) {
            pullRightLeverAndUpdateWheelPositions(wheelPositions);
        }

        StringBuilder returnString = new StringBuilder();
        for (int i = 0; i < catsOnWheels.size(); i++) {
            returnString.append(catsOnWheels.get(i).get(wheelPositions.get(i)));
            returnString.append(" ");
        }
        return returnString.substring(0, returnString.length() - 1);
    }

    @Override
    public Object part2(List<String> input) {
        List<Integer> wheelPositions = parseInputAndInitWheelPositions(input);

        long totalLeverPulls = 202420242024L;

        long cycleLength = 1;
        for (List<String> cat : catsOnWheels) {
            cycleLength = Util.leastCommonMultiple(cycleLength, cat.size());
        }
        long numCycles = totalLeverPulls / cycleLength;
        long additionalLeverPulls = totalLeverPulls % cycleLength;

        long additionalScore = 0;
        long cycleScore = 0;
        for (int i = 0; i < cycleLength; i++) {
            pullRightLeverAndUpdateWheelPositions(wheelPositions);

            cycleScore += getByteCoins(wheelPositions);

            if (i + 1 == additionalLeverPulls) {
                additionalScore = cycleScore;
            }
        }

        return numCycles * cycleScore + additionalScore;
    }

    @Override
    public Object part3(List<String> input) {
        List<Integer> wheelPositions = parseInputAndInitWheelPositions(input);
        maxMinCoinsCache.clear();
        Tuple2<Long, Long> result = getMaxAndMinByteCoins(wheelPositions, 256);
        return result.v1 + " " + result.v2;
    }

    private Tuple2<Long, Long> getMaxAndMinByteCoins(List<Integer> wheelPositions, int remaining) {
        if (remaining == 0) {
            return Tuple.tuple(0L, 0L);
        }
        Tuple2<List<Integer>, Integer> cacheKey = Tuple.tuple(wheelPositions, remaining);
        if (maxMinCoinsCache.containsKey(cacheKey)) {
            return maxMinCoinsCache.get(cacheKey);
        }

        List<Integer> newWheelPositionsNormal = new ArrayList<>();
        List<Integer> newWheelPositionsPlusOne = new ArrayList<>();
        List<Integer> newWheelPositionsMinusOne = new ArrayList<>();
        for (int j = 0; j < positionChangesPerLeverPull.size(); j++) {
            int newPosition = (wheelPositions.get(j) + positionChangesPerLeverPull.get(j)) % catsOnWheels.get(j).size();
            newWheelPositionsNormal.add(newPosition);
            newWheelPositionsPlusOne.add((newPosition+1) % catsOnWheels.get(j).size());
            newWheelPositionsMinusOne.add((newPosition + catsOnWheels.get(j).size() - 1) % catsOnWheels.get(j).size());
        }

        Tuple2<Long, Long> maxAndMinByteCoinsNormal = getMaxAndMinByteCoins(newWheelPositionsNormal, remaining - 1);
        Tuple2<Long, Long> maxAndMinByteCoinsPlusOne = getMaxAndMinByteCoins(newWheelPositionsPlusOne, remaining - 1);
        Tuple2<Long, Long> maxAndMinByteCoinsMinusOne = getMaxAndMinByteCoins(newWheelPositionsMinusOne, remaining - 1);

        long byteCoinsNormal = getByteCoins(newWheelPositionsNormal);
        long byteCoinsPlusOne = getByteCoins(newWheelPositionsPlusOne);
        long byteCoinsMinusOne = getByteCoins(newWheelPositionsMinusOne);

        long valueMax = Math.max(byteCoinsNormal + maxAndMinByteCoinsNormal.v1, Math.max(byteCoinsPlusOne + maxAndMinByteCoinsPlusOne.v1, byteCoinsMinusOne + maxAndMinByteCoinsMinusOne.v1));
        long valueMin = Math.min(byteCoinsNormal + maxAndMinByteCoinsNormal.v2, Math.min(byteCoinsPlusOne + maxAndMinByteCoinsPlusOne.v2, byteCoinsMinusOne + maxAndMinByteCoinsMinusOne.v2));
        Tuple2<Long, Long> value = Tuple.tuple(valueMax, valueMin);
        maxMinCoinsCache.put(cacheKey, value);
        return value;
    }

    private long getByteCoins(List<Integer> wheelPositions) {
        long returnValue = 0;
        Map<Character, Integer> charCounter = new HashMap<>();
        for (int i = 0; i < wheelPositions.size(); i++) {
            String cat = catsOnWheels.get(i).get(wheelPositions.get(i));
            charCounter.put(cat.charAt(0), charCounter.getOrDefault(cat.charAt(0), 0) + 1);
            charCounter.put(cat.charAt(2), charCounter.getOrDefault(cat.charAt(2), 0) + 1);
        }
        for (Integer count : charCounter.values()) {
            if (count >= 3) {
                returnValue += count - 2;
            }
        }
        return returnValue;
    }

    private void pullRightLeverAndUpdateWheelPositions(List<Integer> wheelPositions) {
        for (int j = 0; j < positionChangesPerLeverPull.size(); j++) {
            wheelPositions.set(j, (wheelPositions.get(j) + positionChangesPerLeverPull.get(j)) % catsOnWheels.get(j).size());
        }
    }
    private List<Integer> parseInputAndInitWheelPositions(List<String> input) {
        positionChangesPerLeverPull = Util.parseIntCsv(input.getFirst());

        catsOnWheels = new ArrayList<>();
        for (int i = 0; i < positionChangesPerLeverPull.size(); i++) {
            catsOnWheels.add(new ArrayList<>());
        }
        for (int i = 2; i < input.size(); i++) {
            String line = input.get(i);
            for (int j = 0; j < (line.length() + 1) / 4; j++) {
                String cat = line.substring(j * 4, j * 4 + 3);
                if (!"   ".equals(cat)) {
                    catsOnWheels.get(j).add(cat);
                }
            }
        }

        for (int i = 0; i < positionChangesPerLeverPull.size(); i++) {
            positionChangesPerLeverPull.set(i, positionChangesPerLeverPull.get(i) % catsOnWheels.get(i).size());
        }

        List<Integer> wheelPositions = new ArrayList<>();
        for (int i = 0; i < positionChangesPerLeverPull.size(); i++) {
            wheelPositions.add(0);
        }
        return wheelPositions;
    }
}
