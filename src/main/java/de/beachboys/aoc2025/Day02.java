package de.beachboys.aoc2025;

import de.beachboys.Day;

import java.util.List;
import java.util.regex.Pattern;

public class Day02 extends Day {

    public Object part1(List<String> input) {
        long result = 0;
        String[] ranges = input.getFirst().split(Pattern.quote(","));
        for (String range : ranges) {
            String[] splitRange = range.split(Pattern.quote("-"));
            long low = Long.parseLong(splitRange[0]);
            long high = Long.parseLong(splitRange[1]);
            for (long id = low; id <= high; id++) {
                String idAsString = String.valueOf(id);
                if (idAsString.substring(0, idAsString.length() / 2).equals(idAsString.substring(idAsString.length() / 2))) {
                    result += id;
                }

            }
        }
        return result;
    }

    public Object part2(List<String> input) {
        long result = 0;
        String[] ranges = input.getFirst().split(Pattern.quote(","));
        for (String range : ranges) {
            String[] splitRange = range.split(Pattern.quote("-"));
            long low = Long.parseLong(splitRange[0]);
            long high = Long.parseLong(splitRange[1]);
            for (long id = low; id <= high; id++) {
                String idAsString = String.valueOf(id);
                for (int i = 1; i < idAsString.length(); i++) {
                    if (idAsString.length() % i == 0) {
                        boolean isInvalid = true;
                        String firstPart = idAsString.substring(0, i);
                        for (int j = 1; j < idAsString.length() / i; j++) {
                            String otherPart = idAsString.substring(j * i, (j + 1) * i);
                            if (!otherPart.equals(firstPart)) {
                                isInvalid = false;
                                break;
                            }
                        }
                        if (isInvalid) {
                            result += id;
                            break;
                        }
                    }
                }
            }
        }
        return result;
    }

}
