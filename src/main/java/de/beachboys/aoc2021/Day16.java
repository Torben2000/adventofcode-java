package de.beachboys.aoc2021;

import de.beachboys.Day;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.beachboys.aoc2021.Day16.ParseMode.*;

public class Day16 extends Day {

    enum ParseMode {
        VERSION(3), TYPE(3), ID(1), PACKET_LENGTH(15), PACKET_COUNT(11), VALUE(5);

        final int length;

        ParseMode(int length) {
            this.length = length;
        }
    }

    private long versionSum = 0L;

    public Object part1(List<String> input) {
        runLogic(input);
        return versionSum;
    }

    public Object part2(List<String> input) {
        return runLogic(input).getValue1();
    }

    private Pair<Integer, Long> runLogic(List<String> input) {
        versionSum = 0;
        String bits = parseBitsFromHex(input.get(0));
        return parsePacket(bits, 0);
    }

    private Pair<Integer, Long> parsePacket(String bits, int pointer) {
        int localPointer = pointer;
        ParseMode parseMode = VERSION;
        int packetType = 0;
        List<Long> values = new ArrayList<>();
        boolean packetEndReached = false;

        while (!packetEndReached) {
            int value = Integer.valueOf(bits.substring(localPointer, localPointer + parseMode.length), 2);
            localPointer += parseMode.length;
            switch (parseMode) {
                case VERSION:
                    versionSum += value;
                    parseMode = TYPE;
                    break;
                case TYPE:
                    packetType = value;
                    parseMode = ID;
                    if (packetType == 4) {
                        parseMode = VALUE;
                    }
                    break;
                case VALUE:
                    long curValue = value % 16;
                    values.add(curValue);
                    if (value < 16) {
                        packetEndReached = true;
                    }
                    break;
                case ID:
                    if (value == 0) {
                        parseMode = PACKET_LENGTH;
                    } else {
                        parseMode = PACKET_COUNT;
                    }
                    break;
                case PACKET_LENGTH:
                    int packetEnd = localPointer + value;
                    while (localPointer < packetEnd) {
                        Pair<Integer, Long> packet = parsePacket(bits, localPointer);
                        localPointer = packet.getValue0();
                        values.add(packet.getValue1());
                    }
                    packetEndReached = true;
                    break;
                case PACKET_COUNT:
                    for (int i = 0; i < value; i++) {
                        Pair<Integer, Long> packet = parsePacket(bits, localPointer);
                        localPointer = packet.getValue0();
                        values.add(packet.getValue1());
                    }
                    packetEndReached = true;
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        }
        return Pair.with(localPointer, calculatePacketValue(packetType, values));
    }

    private long calculatePacketValue(int packetType, List<Long> values) {
        switch (packetType) {
            case 0:
                return values.stream().reduce(0L, Long::sum);
            case 1:
                return values.stream().reduce(1L, (l1, l2) -> l1 * l2);
            case 2:
                return values.stream().reduce(Long.MAX_VALUE, Math::min);
            case 3:
                return values.stream().reduce(Long.MIN_VALUE, Math::max);
            case 4:
                return values.stream().reduce(0L, (l1, l2) -> l1 * 16 + l2);
            case 5:
                return values.get(0) > values.get(1) ? 1 : 0;
            case 6:
                return values.get(0) < values.get(1) ? 1 : 0;
            case 7:
                return values.get(0).equals(values.get(1)) ? 1 : 0;
            default:
                throw new IllegalArgumentException();
        }
    }

    private String parseBitsFromHex(String input) {
        StringBuilder output = new StringBuilder();
        Map<Character, String> replacements = new HashMap<>();
        replacements.put('0', "0000");
        replacements.put('1', "0001");
        replacements.put('2', "0010");
        replacements.put('3', "0011");
        replacements.put('4', "0100");
        replacements.put('5', "0101");
        replacements.put('6', "0110");
        replacements.put('7', "0111");
        replacements.put('8', "1000");
        replacements.put('9', "1001");
        replacements.put('A', "1010");
        replacements.put('B', "1011");
        replacements.put('C', "1100");
        replacements.put('D', "1101");
        replacements.put('E', "1110");
        replacements.put('F', "1111");
        for (char c : input.toCharArray()) {
            output.append(replacements.get(c));
        }
        return output.toString();
    }

}
