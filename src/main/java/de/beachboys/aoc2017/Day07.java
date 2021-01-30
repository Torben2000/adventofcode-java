package de.beachboys.aoc2017;

import de.beachboys.Day;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day07 extends Day {

    private final Map<String, Set<String>> programToProgramsOnTop = new HashMap<>();

    private final Map<String, Integer> weights = new HashMap<>();

    private final List<Integer> correctedWeights = new ArrayList<>();

    public Object part1(List<String> input) {
        return parseInputAndReturnProgramAtBottom(input);
    }

    public Object part2(List<String> input) {
        String programAtBottom = parseInputAndReturnProgramAtBottom(input);
        getWeightIncludingProgramsOnTop(programAtBottom);
        if (correctedWeights.size() != 1) {
            throw new IllegalStateException("this should not happen");
        }
        return correctedWeights.get(0);
    }

    private String parseInputAndReturnProgramAtBottom(List<String> input) {
        parseInput(input);
        return getProgramAtBottom();
    }

    private void parseInput(List<String> input) {
        Pattern p = Pattern.compile("([a-z]+) \\(([0-9]+)\\)( -> ([a-z, ]+)+)?");
        for (String line : input) {
            Matcher m = p.matcher(line);
            if (m.matches()) {
                String program = m.group(1);
                Integer weight = Integer.valueOf(m.group(2));
                weights.put(program, weight);
                if (m.group(4) != null) {
                    String[] rightSideParts = m.group(4).split(", ");
                    Set<String> programsOnTop = new HashSet<>(Arrays.asList(rightSideParts));
                    programToProgramsOnTop.put(program, programsOnTop);
                }
            }
        }
    }

    private String getProgramAtBottom() {
        Set<String> programAtTheBottom = new HashSet<>(programToProgramsOnTop.keySet());
        programToProgramsOnTop.values().forEach(programAtTheBottom::removeAll);
        if (programAtTheBottom.size() != 1) {
            throw new IllegalStateException("this should not happen");
        }
        return programAtTheBottom.stream().findFirst().orElseThrow();
    }

    private int getWeightIncludingProgramsOnTop(String program) {
        int totalWeight = weights.get(program);
        if (programToProgramsOnTop.containsKey(program)) {
            Map<String, Integer> weightsOfProgramsOnTop = new HashMap<>();
            for (String programOnTop : programToProgramsOnTop.get(program)) {
                int weightOfProgramOnTop = getWeightIncludingProgramsOnTop(programOnTop);
                weightsOfProgramsOnTop.put(programOnTop, weightOfProgramOnTop);
            }
            totalWeight += weightsOfProgramsOnTop.size() * getCorrectWeightForProgramOnTop(weightsOfProgramsOnTop);
        }
        return totalWeight;
    }

    private int getCorrectWeightForProgramOnTop(Map<String, Integer> weightsOfProgramsOnTop) {
        Set<Integer> distinctValues = new HashSet<>(weightsOfProgramsOnTop.values());
        int correctWeightForProgramOnTop = distinctValues.stream().findFirst().orElseThrow();
        if (distinctValues.size() == 2) {
            int differenceBetweenWeightWithProgramsOnTopAndIndividualWeight = 0;
            for (Integer weight : distinctValues) {
                List<String> matchingPrograms = weightsOfProgramsOnTop.entrySet().stream().filter(entry -> entry.getValue().equals(weight)).map(Map.Entry::getKey).collect(Collectors.toList());
                if (matchingPrograms.size() == 1) {
                    differenceBetweenWeightWithProgramsOnTopAndIndividualWeight = weight - weights.get(matchingPrograms.get(0));
                } else {
                    correctWeightForProgramOnTop = weight;
                }
            }
            correctedWeights.add(correctWeightForProgramOnTop - differenceBetweenWeightWithProgramsOnTopAndIndividualWeight);
        } else if (distinctValues.size() > 2) {
            throw new IllegalStateException("this should not happen");
        }
        return correctWeightForProgramOnTop;
    }

}
