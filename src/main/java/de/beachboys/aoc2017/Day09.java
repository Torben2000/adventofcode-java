package de.beachboys.aoc2017;

import de.beachboys.Day;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day09 extends Day {

    public static final String GARBAGE_REGEX = "<([^>]*)>";

    public Object part1(List<String> input) {
        String stream = getStreamWithoutCanceledCharacters(input);
        stream = stream.replaceAll(GARBAGE_REGEX, "");
        int sum = 0;
        int openGroups = 0;
        for (char character : stream.toCharArray()) {
            switch (character) {
                case '{':
                    openGroups++;
                    break;
                case '}':
                    sum += openGroups;
                    openGroups--;
                default:
                    //do nothing
                    break;
            }
        }
        if (openGroups != 0) {
            throw new IllegalStateException("malformed stream");
        }
        return sum;
    }

    public Object part2(List<String> input) {
        String stream = getStreamWithoutCanceledCharacters(input);
        Pattern p = Pattern.compile(GARBAGE_REGEX);
        Matcher m = p.matcher(stream);
        int sum = 0;
        while (m.find()) {
            sum += m.group(1).length();
        }
        return sum;
    }

    private String getStreamWithoutCanceledCharacters(List<String> input) {
        return input.get(0).replaceAll("!.", "");
    }

}
