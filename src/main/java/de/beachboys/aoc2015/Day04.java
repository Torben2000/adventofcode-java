package de.beachboys.aoc2015;

import de.beachboys.Day;
import de.beachboys.Util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class Day04 extends Day {

    public Object part1(List<String> input) {
        return runLogic(input, "00000");
    }

    public Object part2(List<String> input) {
        return runLogic(input, "000000");
    }

    private int runLogic(List<String> input, String requiredHashPrefix) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            String prefix = input.getFirst();
            int suffix = 0;
            String hash = "";
            while (!hash.startsWith(requiredHashPrefix)) {
                suffix++;
                md.update((prefix + suffix).getBytes());
                byte[] digest = md.digest();
                hash = Util.bytesToHex(digest);
            }
            return suffix;
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException();
        }
    }

}
