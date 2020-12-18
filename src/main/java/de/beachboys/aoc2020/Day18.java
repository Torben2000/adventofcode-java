package de.beachboys.aoc2020;

import de.beachboys.Day;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day18 extends Day {

    public Object part1(List<String> input) {
        long sum = 0L;
        for (String line : input) {
            sum += calculateLinePart1(line);
        }
        return sum;
    }

    private long calculateLinePart1(String line) {
        String currentLine = line;
        currentLine = applyPatternReplacementLogic(currentLine, "\\([^()]*\\)", match -> calculateLinePart1(match.substring(1, match.length() - 1)) + "");
        currentLine = applyPatternReplacementLogic(currentLine, "^[0-9]+ [*+] [0-9]+", match -> {
            String[] c = match.split(" ");
            return "*".equals(c[1]) ? (Long.parseLong(c[0]) * Long.parseLong(c[2])) + "" : (Long.parseLong(c[0]) + Long.parseLong(c[2])) + "";
        });
        return Long.parseLong(currentLine);
    }

    public Object part2(List<String> input) {
        long sum = 0L;
        for (String line : input) {
            sum += calculateLinePart2(line);
        }
        return sum;
    }

    private long calculateLinePart2(String line) {
        String currentLine = line;
        currentLine = applyPatternReplacementLogic(currentLine, "\\([^()]*\\)", match -> calculateLinePart2(match.substring(1, match.length() - 1)) + "");
        currentLine = applyPatternReplacementLogic(currentLine, "[0-9]+ \\+ [0-9]+", match -> Arrays.stream(match.split(" \\+ ")).mapToLong(Long::parseLong).sum() + "");
        currentLine = applyPatternReplacementLogic(currentLine, "^[0-9]+ \\* [0-9]+", match -> Arrays.stream(match.split( " \\* ")).mapToLong(Long::parseLong).reduce(1, (a, b) -> a * b) + "");
        return Long.parseLong(currentLine);
    }

    private String applyPatternReplacementLogic(String currentLine, String regex, Function<String, String> patternReplacement) {
        Pattern p = Pattern.compile(regex);
        boolean replacementHappened = true;
        while (replacementHappened) {
            replacementHappened = false;
            Matcher m = p.matcher(currentLine);
            while (m.find()) {
                replacementHappened = true;
                String substring = m.group();
                currentLine = currentLine.replaceFirst(Pattern.quote(substring), patternReplacement.apply(substring));
            }
        }
        return currentLine;
    }

}
