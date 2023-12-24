package de.beachboys.aoc2016;

import de.beachboys.Day;

import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day09 extends Day {

    private Function<String, Long> getRepeatedStringLength;

    public Object part1(List<String> input) {
        getRepeatedStringLength = repeatedString -> (long) repeatedString.length();
        return getLength(input.getFirst());
    }

    public Object part2(List<String> input) {
        getRepeatedStringLength = this::getLength;
        return getLength(input.getFirst());
    }

    private long getLength(String compressed) {
        Pattern p = Pattern.compile("(\\(([0-9]+)x([0-9]+)\\)).*");
        int position = 0;
        while (position < compressed.length()) {
            if (compressed.charAt(position) == '(') {
                Matcher m = p.matcher(compressed.substring(position));
                if (m.matches()) {
                    int markerLength = m.group(1).length();
                    int numCharacters = Integer.parseInt(m.group(2));
                    int repeat = Integer.parseInt(m.group(3));
                    String substringToRepeat = compressed.substring(position + markerLength, position + markerLength + numCharacters);
                    String followingSubstring = compressed.substring(position + markerLength + numCharacters);
                    return position + repeat * getRepeatedStringLength.apply(substringToRepeat) + getLength(followingSubstring);
                }
            } else {
                position++;
            }
        }
        return position;
    }

}
