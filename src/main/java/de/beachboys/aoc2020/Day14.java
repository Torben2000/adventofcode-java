package de.beachboys.aoc2020;

import de.beachboys.Day;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day14 extends Day {

    public Object part1(List<String> input) {
        Map<Long, Long> valueMap = new HashMap<>();
        Boolean[] mask = new Boolean[0];
        for (String line : input) {
            if (line.startsWith("mask = ")) {
                mask = parseMask(line);
            } else {
                Tuple2<Long, Long> memLine = parseMemLine(line);
                valueMap.put(memLine.v1, adjustValueWithMask(mask, memLine.v2));
            }
        }
        return getSumOfValues(valueMap);
    }

    public Object part2(List<String> input) {
        Map<Long, Long> valueMap = new HashMap<>();
        Boolean[] mask = new Boolean[0];
        for (String line : input) {
            if (line.startsWith("mask = ")) {
                mask = parseMask(line);
            } else {
                Tuple2<Long, Long> memLine = parseMemLine(line);
                long index = memLine.v1;

                List<Integer> floatingBits = new ArrayList<>();
                for (int i = 0; i < mask.length; i++) {
                    int bitIndex = mask.length - 1 - i;
                    if (mask[i] != null) {
                        if (mask[i]) {
                            index = setTo1(index, bitIndex);
                        }
                    } else {
                        floatingBits.add(i);
                    }
                }

                for (int bitMask = 0; bitMask < Math.pow(2, floatingBits.size()); bitMask++) {
                    long indexToSet = index;
                    for (int i = 0; i < floatingBits.size(); i++) {
                        int bitIndex = mask.length - 1 - floatingBits.get(i);
                        int bitMaskBitIndex = floatingBits.size() - i - 1;
                        if (is1AtIndex(bitMask, bitMaskBitIndex)) {
                            indexToSet = setTo1(indexToSet, bitIndex);
                        } else {
                            indexToSet = setTo0(indexToSet, bitIndex);
                        }
                    }
                    valueMap.put(indexToSet, memLine.v2);
                }
            }
        }
        return getSumOfValues(valueMap);
    }

    private long adjustValueWithMask(Boolean[] mask, long value) {
        long returnValue = value;
        for (int i = 0; i < mask.length; i++) {
            int bitIndex = mask.length - 1 - i;
            if (mask[i] != null) {
                if (mask[i]) {
                    returnValue = setTo1(returnValue, bitIndex);
                } else {
                    returnValue = setTo0(returnValue, bitIndex);
                }
            }
        }
        return returnValue;
    }

    private long setTo1(long value, int bitIndex) {
        return value | (1L << bitIndex);
    }

    private long setTo0(long value, int bitIndex) {
        if (is1AtIndex(value, bitIndex)) {
            value = value ^ (1L << bitIndex);
        }
        return value;
    }

    private boolean is1AtIndex(long bitMask, int bitMaskBitIndex) {
        return (bitMask & (1L << bitMaskBitIndex)) != 0;
    }

    private Boolean[] parseMask(String line) {
        Boolean[] mask;
        String maskS = line.substring("mask = ".length());
        mask = new Boolean[maskS.length()];
        for (int i = 0; i < mask.length; i++) {
            switch (maskS.charAt(i)) {
                case '1':
                    mask[i] = true;
                    break;
                case '0':
                    mask[i] = false;
                    break;
            }
        }
        return mask;
    }

    private Tuple2<Long, Long> parseMemLine(String line) {
        String[] a = line.split(" = ");
        long index = Integer.parseInt(a[0].substring(4, a[0].length() - 1));
        long value = Long.parseLong(a[1]);
        return Tuple.tuple(index, value);
    }

    private long getSumOfValues(Map<Long, Long> valueMap) {
        return valueMap.values().stream().mapToLong(i -> i).sum();
    }

}
