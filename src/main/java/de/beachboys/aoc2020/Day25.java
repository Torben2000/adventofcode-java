package de.beachboys.aoc2020;

import de.beachboys.Day;

import java.util.List;

public class Day25 extends Day {

    public Object part1(List<String> input) {
        long publicKeyCard = Long.parseLong(input.get(0));
        long publicKeyDoor = Long.parseLong(input.get(1));

        long loopSizeCard = getLoopSize(publicKeyCard);
        long loopSizeDoor = getLoopSize(publicKeyDoor);

        long encryptionKey1 = getEncryptionKey(publicKeyCard, loopSizeDoor);
        long encryptionKey2 = getEncryptionKey(publicKeyDoor, loopSizeCard);

        if (encryptionKey1 == encryptionKey2) {
            return encryptionKey1;
        }
        throw new IllegalArgumentException();
    }

    private long getEncryptionKey(long publicKey, long loopSize) {
        long encryptionKey1 = 1;
        for (long l = 0; l < loopSize; l++) {
            encryptionKey1 = (encryptionKey1 * publicKey) % 20201227;
        }
        return encryptionKey1;
    }

    private long getLoopSize(long publicKey) {
        long v = 1;
        long loopSize = 0;
        while (v != publicKey) {
            loopSize++;
            v = (v * 7) % 20201227;
        }
        return loopSize;
    }

    public Object part2(List<String> input) {
        return 2;
    }

}
