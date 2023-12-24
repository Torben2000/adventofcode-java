package de.beachboys.aoc2020;

import de.beachboys.Day;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class Day04 extends Day {

    public Object part1(List<String> input) {
        List<Map<String, String>> passports = buildPassportList(input);
        return countValidPassports(passports, this::isValidPart1);
    }

    public Object part2(List<String> input) {
        List<Map<String, String>> passports = buildPassportList(input);
        return countValidPassports(passports, this::isValidPart2);
    }

    private long countValidPassports(List<Map<String, String>> passports, Predicate<Map<String, String>> isValidChecker) {
        return passports.stream().filter(isValidChecker).count();
    }

    private List<Map<String, String>> buildPassportList(List<String> input) {
        List<Map<String, String>> passports = new ArrayList<>();
        Map<String, String> currentPassport = new HashMap<>();
        for (String line : input) {
            if (line.isBlank()) {
                passports.add(currentPassport);
                currentPassport = new HashMap<>();
            } else {
                String[] values = line.split(" ");
                for (String value : values) {
                    String[] pair = value.split(":");
                    currentPassport.put(pair[0], pair[1]);
                }
            }
        }
        passports.add(currentPassport);
        return passports;
    }

    private boolean isValidPart1(Map<String, String> passport) {
        return passport.containsKey("byr") && passport.containsKey("iyr") && passport.containsKey("eyr")
                && passport.containsKey("hgt") && passport.containsKey("hcl") && passport.containsKey("ecl") && passport.containsKey("pid");
    }

    private boolean isValidPart2(Map<String, String> passport) {
        return checkYear(passport.get("byr"), 1920, 2002) && checkYear(passport.get("iyr"), 2010, 2020) && checkYear(passport.get("eyr"), 2020, 2030)
                && checkHeight(passport.get("hgt")) && checkHairColor(passport.get("hcl")) && checkEyeColor(passport.get("ecl")) && checkPassportId(passport.get("pid"));
    }

    private boolean checkYear(String yearAsString, int min, int max) {
        if (yearAsString == null) {
            return false;
        }
        try {
            int yearAsInt = Integer.parseInt(yearAsString);
            return yearAsInt >= min && yearAsInt <= max;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean checkHeight(String heightAsString) {
        if (heightAsString == null) {
            return false;
        }
        try {
            String heightWithoutUnit = heightAsString.substring(0, heightAsString.length() - 2);
            int heightAsInt = Integer.parseInt(heightWithoutUnit);
            String unit = heightAsString.substring(heightAsString.length() - 2);
            return switch (unit) {
                case "in" -> heightAsInt >= 59 && heightAsInt <= 76;
                case "cm" -> heightAsInt >= 150 && heightAsInt <= 193;
                default -> false;
            };
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean checkHairColor(String hairColor) {
        return hairColor != null && hairColor.matches("#[0-9a-f]{6}");
    }

    private boolean checkEyeColor(String eyeColor) {
        return "amb".equals(eyeColor) || "blu".equals(eyeColor) || "brn".equals(eyeColor)
                || "gry".equals(eyeColor) || "grn".equals(eyeColor) || "hzl".equals(eyeColor) || "oth".equals(eyeColor);
    }

    private boolean checkPassportId(String passportId) {
        if (passportId == null) {
            return false;
        }
        try {
            int passportIdAsInt = Integer.parseInt(passportId);
            return passportId.length() == 9 && passportIdAsInt > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
