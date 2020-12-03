package de.beachboys.aoc2020;

import de.beachboys.Day;

import java.util.List;

public class Day02 extends Day {

    public Object part1(List<String> input) {
        int validPasswords = 0;
        for (String inputs : input) {
            String[] policyAndPassword = inputs.split(":");
            String[] limitAndLetter = policyAndPassword[0].split(" ");
            String[] limits = limitAndLetter[0].split("-");
            String password = policyAndPassword[1].trim();
            String letter = limitAndLetter[1];
            int min = Integer.parseInt(limits[0]);
            int max = Integer.parseInt(limits[1]);

            int letterCount = 0;
            while (password.contains(letter)) {
                letterCount++;
                password = password.replaceFirst(letter, "");

            }

            if (min <= letterCount && letterCount <= max) {
                validPasswords++;
            }
        }
        return validPasswords;

    }

    public Object part2(List<String> input) {
        int validPasswords = 0;
        for (String inputs : input) {
            String[] policyAndPassword = inputs.split(":");
            String[] limitAndLetter = policyAndPassword[0].split(" ");
            String[] limits = limitAndLetter[0].split("-");
            String password = policyAndPassword[1].trim();
            char letter = limitAndLetter[1].charAt(0);
            int index1 = Integer.parseInt(limits[0]) - 1;
            int index2 = Integer.parseInt(limits[1]) - 1;

            if (password.charAt(index1) == letter ^ password.charAt(index2) == letter) {
                validPasswords++;
            }
        }
        return validPasswords;

    }

}
