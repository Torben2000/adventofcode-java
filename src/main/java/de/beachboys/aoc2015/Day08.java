package de.beachboys.aoc2015;

import de.beachboys.Day;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day08 extends Day {

    public Object part1(List<String> input) {
        int sum = 0;
        for (String line : input) {
            String shorterLine = line.substring(1, line.length() - 1);
            shorterLine = shorterLine.replaceAll(Pattern.quote("\\\""), Matcher.quoteReplacement("\""));
            // hex escaped characters are not correctly replaced, but it is not necessary for the task
            shorterLine = shorterLine.replaceAll(Pattern.quote("\\x") + "([0-9a-f]{2})", Matcher.quoteReplacement("'"));
            shorterLine = shorterLine.replaceAll(Pattern.quote("\\\\"), Matcher.quoteReplacement("\\"));
            sum += line.length() - shorterLine.length();
        }
        return sum;
    }

    public Object part2(List<String> input) {
        int sum = 0;
        for (String line : input) {
            String longerLine = line;
            longerLine = longerLine.replaceAll(Pattern.quote("\\"), Matcher.quoteReplacement("\\\\"));
            longerLine = longerLine.replaceAll(Pattern.quote("\""), Matcher.quoteReplacement("\\\""));
            longerLine = "\"" + longerLine + "\"";
            sum += longerLine.length() - line.length();
        }
        return sum;
    }

}
