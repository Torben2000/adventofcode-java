package de.beachboys.aoc2021;

import de.beachboys.Day;
import de.beachboys.OCR;
import de.beachboys.Util;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day13 extends Day {

    private Set<Pair<Integer, Integer>> dots = new HashSet<>();
    private final List<Pair<Boolean, Integer>> foldInstructions = new ArrayList<>();

    public Object part1(List<String> input) {
        parseInput(input);
        return foldPaper(dots, foldInstructions.get(0)).size();
    }

    public Object part2(List<String> input) {
        parseInput(input);

        for (Pair<Boolean, Integer> foldInstruction : foldInstructions) {
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
                foldInstructions.add(Pair.with("x".equals(foldInstructionAsStrings[0]), Integer.parseInt(foldInstructionAsStrings[1])));
            } else {
                String[] dotCoordinatesAsStrings = line.split(",");
                dots.add(Pair.with(Integer.parseInt(dotCoordinatesAsStrings[0]), Integer.parseInt(dotCoordinatesAsStrings[1])));
            }
        }
    }

    private Set<Pair<Integer, Integer>> foldPaper(Set<Pair<Integer, Integer>> dots, Pair<Boolean, Integer> fold) {
        Set<Pair<Integer, Integer>> newDots = new HashSet<>();
        for (Pair<Integer, Integer> dot : dots) {
            if (fold.getValue0() && dot.getValue0() < fold.getValue1() || !fold.getValue0() && dot.getValue1() < fold.getValue1()) {
                newDots.add(Pair.with(dot.getValue0(), dot.getValue1()));
            } else if (fold.getValue0() && dot.getValue0() > fold.getValue1()) {
                newDots.add(Pair.with(fold.getValue1() - (dot.getValue0() - fold.getValue1()), dot.getValue1()));
            } else if (!fold.getValue0() && dot.getValue1() > fold.getValue1()) {
                newDots.add(Pair.with(dot.getValue0(), fold.getValue1() - (dot.getValue1() - fold.getValue1())));
            }
        }
        return newDots;
    }

}
