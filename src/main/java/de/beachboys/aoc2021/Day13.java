package de.beachboys.aoc2021;

import de.beachboys.Day;
import de.beachboys.OCR;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day13 extends Day {

    private Set<Tuple2<Integer, Integer>> dots = new HashSet<>();
    private final List<Tuple2<Boolean, Integer>> foldInstructions = new ArrayList<>();

    public Object part1(List<String> input) {
        parseInput(input);
        return foldPaper(dots, foldInstructions.get(0)).size();
    }

    public Object part2(List<String> input) {
        parseInput(input);

        for (Tuple2<Boolean, Integer> foldInstruction : foldInstructions) {
            dots = foldPaper(dots, foldInstruction);
        }

        return OCR.runOCRAndReturnOriginalOnError(Util.paintSet(dots));
    }

    private void parseInput(List<String> input) {
        dots.clear();
        foldInstructions.clear();
        boolean parseFoldInstructions = false;
        for (String line : input) {
            if (line.isBlank()) {
                parseFoldInstructions = true;
            } else if (parseFoldInstructions) {
                String[] foldInstructionAsStrings = line.substring("fold along ".length()).split("=");
                foldInstructions.add(Tuple.tuple("x".equals(foldInstructionAsStrings[0]), Integer.parseInt(foldInstructionAsStrings[1])));
            } else {
                String[] dotCoordinatesAsStrings = line.split(",");
                dots.add(Tuple.tuple(Integer.parseInt(dotCoordinatesAsStrings[0]), Integer.parseInt(dotCoordinatesAsStrings[1])));
            }
        }
    }

    private Set<Tuple2<Integer, Integer>> foldPaper(Set<Tuple2<Integer, Integer>> dots, Tuple2<Boolean, Integer> fold) {
        Set<Tuple2<Integer, Integer>> newDots = new HashSet<>();
        for (Tuple2<Integer, Integer> dot : dots) {
            if (fold.v1 && dot.v1 < fold.v2 || !fold.v1 && dot.v2 < fold.v2) {
                newDots.add(Tuple.tuple(dot.v1, dot.v2));
            } else if (fold.v1 && dot.v1 > fold.v2) {
                newDots.add(Tuple.tuple(fold.v2 - (dot.v1 - fold.v2), dot.v2));
            } else if (!fold.v1 && dot.v2 > fold.v2) {
                newDots.add(Tuple.tuple(dot.v1, fold.v2 - (dot.v2 - fold.v2)));
            }
        }
        return newDots;
    }

}
