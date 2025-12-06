package de.beachboys.aoc2025;

import de.beachboys.Day;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Day06 extends Day {

    public Object part1(List<String> input) {
        List<List<String>> problems = new ArrayList<>();
        for (String line : input) {
            while (line.contains("  ")) {
                line = line.replaceAll(" {2}", " ");
            }
            line = line.trim();

            String[] split = line.split(Pattern.quote(" "));
            for (int i1 = 0; i1 < split.length; i1++) {
                if (problems.size() <= i1) {
                    problems.add(new ArrayList<>());
                }
                problems.get(i1).add(split[i1]);
            }
        }

        long result = 0;
        for (List<String> problem : problems) {
            long problemResult = 0;
            if ("+".equals(problem.getLast())) {
                for (int i = 0; i < problem.size() - 1; i++) {
                    problemResult += Long.parseLong(problem.get(i));
                }
            } else {
                problemResult = 1;
                for (int i = 0; i < problem.size() - 1; i++) {
                    problemResult *= Long.parseLong(problem.get(i));
                }
            }
            result += problemResult;
        }
        return result;
    }

    public Object part2(List<String> input) {
        List<List<Character>> columns = new ArrayList<>();
        for (String line : input) {
            for (int i = 0; i < line.length(); i++) {
                if (columns.size() <= i) {
                    columns.add(new ArrayList<>());
                }
                columns.get(i).add(line.charAt(i));
            }
        }

        long result = 0;
        boolean problemIsAdd = true;
        long problemResult = 0;
        for (List<Character> column : columns) {
            if ('+' == column.getLast()) {
                result += problemResult;
                problemResult = 0;
                problemIsAdd = true;

            } else if ('*' == column.getLast()){
                result += problemResult;
                problemResult = 1;
                problemIsAdd = false;
            }

            StringBuilder columnNum = new StringBuilder();
            for (int i = 0; i < column.size()-1; i++) {
                char c = column.get(i);
                if (c != ' ') {
                    columnNum.append(c);
                }
            }
            if (!columnNum.isEmpty()) {
                if (problemIsAdd) {
                    problemResult += Long.parseLong(columnNum.toString());
                } else {
                    problemResult *= Long.parseLong(columnNum.toString());
                }
            }
        }
        result += problemResult;
        return result;
    }

}
