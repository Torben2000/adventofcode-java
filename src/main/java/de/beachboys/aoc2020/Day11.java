package de.beachboys.aoc2020;

import de.beachboys.Day;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day11 extends Day {

    @FunctionalInterface
    private interface OccupiedSeatPredicate {

        boolean test(Map<Tuple2<Integer, Integer>, String> map, Tuple2<Integer, Integer> position, int xOffset, int yOffset);

    }

    public static final String OCCUPIED_SEAT = "#";
    public static final String EMPTY_SEAT = "L";

    public Object part1(List<String> input) {
        return getOccupiedSeatsFromStableState(input, 4, this::hasAdjacentOccupiedSeat);
    }

    public Object part2(List<String> input) {
        return getOccupiedSeatsFromStableState(input, 5, this::hasVisibleOccupiedSeat);
    }

    private long getOccupiedSeatsFromStableState(List<String> input, int requiredOccSeatsForSwitch, OccupiedSeatPredicate hasOccupiedSeatInOffsetDirection) {
        Map<Tuple2<Integer, Integer>, String> map = Util.buildImageMap(input);
        boolean changedSeat = true;
        while (changedSeat) {
            changedSeat = false;
            Map<Tuple2<Integer, Integer>, String> newMap = new HashMap<>(map);
            for (Tuple2<Integer, Integer> position : map.keySet()) {
                String value = map.get(position);
                int occCount = getSurroundingOccupiedSeatCount(map, position, hasOccupiedSeatInOffsetDirection);
                if (isEmptySeat(value) && occCount == 0) {
                    newMap.put(position, OCCUPIED_SEAT);
                    changedSeat = true;
                } else if (isOccupiedSeat(value) && occCount >= requiredOccSeatsForSwitch) {
                    newMap.put(position, EMPTY_SEAT);
                    changedSeat = true;
                }
            }
            map = newMap;
        }
        return map.values().stream().filter(this::isOccupiedSeat).count();
    }

    private int getSurroundingOccupiedSeatCount(Map<Tuple2<Integer, Integer>, String> map, Tuple2<Integer, Integer> position, OccupiedSeatPredicate hasOccupiedSeatInOffsetDirection) {
        int occupiedCounter = 0;
        for (int xOffset = -1; xOffset <= 1; xOffset++) {
            for (int yOffset = -1; yOffset <= 1; yOffset++) {
                if (xOffset != 0 || yOffset != 0) {
                    if (hasOccupiedSeatInOffsetDirection.test(map, position, xOffset, yOffset)) {
                        occupiedCounter++;
                    }
                }
            }
        }
        return occupiedCounter;
    }

    private boolean hasAdjacentOccupiedSeat(Map<Tuple2<Integer, Integer>, String> map, Tuple2<Integer, Integer> position, int xOffset, int yOffset) {
        Tuple2<Integer, Integer> adjacentPosition = Tuple.tuple(position.v1 + xOffset, position.v2 + yOffset);
        String value = map.get(adjacentPosition);
        return isOccupiedSeat(value);
    }

    private boolean hasVisibleOccupiedSeat(Map<Tuple2<Integer, Integer>, String> map, Tuple2<Integer, Integer> position, int xOffset, int yOffset) {
        for (int offsetFactor = 1; offsetFactor < map.size(); offsetFactor++) {
            Tuple2<Integer, Integer> visiblePosition = Tuple.tuple(position.v1 + offsetFactor * xOffset, position.v2 + offsetFactor * yOffset);
            String value = map.get(visiblePosition);
            if (!isInPlane(value) || isEmptySeat(value)) {
                return false;
            } else if (isOccupiedSeat(value)) {
                return true;
            }
        }
        return false;
    }

    private boolean isInPlane(String value) {
        return value != null;
    }

    private boolean isOccupiedSeat(String value) {
        return OCCUPIED_SEAT.equals(value);
    }

    private boolean isEmptySeat(String value) {
        return EMPTY_SEAT.equals(value);
    }

}
