package de.beachboys.aoc2016;

import de.beachboys.Day;
import de.beachboys.Util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class Day05 extends Day {

    public Object part1(List<String> input) {
        StringBuilder password = new StringBuilder();
        String prefix = input.getFirst();
        long suffix = 0L;
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException();
        }
        for (int i = 0; i < 8; i++) {
            String hashValue = "";
            while (!hashValue.startsWith("00000")) {
                suffix++;
                hashValue = Util.bytesToHex(md5.digest((prefix + suffix).getBytes()));
            }
            password.append(hashValue.substring(5, 6).toLowerCase());
        }

        return password;
    }

    public Object part2(List<String> input) {
        StringBuilder password = new StringBuilder("________");
        String prefix = input.getFirst();
        long suffix = 0L;
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException();
        }
        while (password.toString().contains("_")) {
            String hashValue = "";
            while (!hashValue.startsWith("00000") || Integer.parseInt(hashValue.substring(5, 6), 16) > 7) {
                suffix++;
                hashValue = Util.bytesToHex(md5.digest((prefix + suffix).getBytes()));
            }
            int index = Integer.parseInt(hashValue.substring(5, 6));
            if (password.charAt(index) == '_') {
                password.replace(index, index + 1, hashValue.substring(6, 7).toLowerCase());
            }
        }

        return password;
    }

}
