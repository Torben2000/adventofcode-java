package de.beachboys.aoc2017;

import de.beachboys.Day;
import de.beachboys.Util;

import java.util.*;
import java.util.function.Consumer;

public class Day16 extends Day {

    public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    public Object part1(List<String> input) {
        int numberOfPrograms = Util.getIntValueFromUser("Number of programs", 16, io);
        StringBuilder programLine = new StringBuilder(ALPHABET.substring(0, numberOfPrograms));
        List<Consumer<StringBuilder>> moves = buildMoveList(input, numberOfPrograms);
        executeMoves(programLine, moves);
        return programLine.toString();
    }

    public Object part2(List<String> input) {
        int numberOfPrograms = Util.getIntValueFromUser("Number of programs", 16, io);
        StringBuilder programLine = new StringBuilder(ALPHABET.substring(0, numberOfPrograms));
        List<Consumer<StringBuilder>> moves = buildMoveList(input, numberOfPrograms);

        Map<String, Integer> cache = new HashMap<>();
        for (int i = 0; i < 1000000000; i++) {
            executeMoves(programLine, moves);
            String currentProgramLine = programLine.toString();
            if (cache.containsKey(currentProgramLine)) {
                int firstOccurrence = cache.get(currentProgramLine);
                int cycleLength = i - firstOccurrence;
                int indexToUse = (999999999 + firstOccurrence) % cycleLength;
                return cache.entrySet().stream().filter(entry -> indexToUse == entry.getValue()).map(Map.Entry::getKey).findFirst().orElseThrow();
            }
            cache.put(currentProgramLine, i);
        }
        return "not found";
    }

    private void executeMoves(StringBuilder programLine, List<Consumer<StringBuilder>> moves) {
        for (Consumer<StringBuilder> move : moves) {
            move.accept(programLine);
        }
    }

    private List<Consumer<StringBuilder>> buildMoveList(List<String> input, int numberOfPrograms) {
        List<Consumer<StringBuilder>> moves = new LinkedList<>();
        for (String move : input.get(0).split(",")) {
            switch (move.charAt(0)) {
                case 's':
                    moves.add(getMoveS(numberOfPrograms, numberOfPrograms - Integer.parseInt(move.substring(1))));
                    break;
                case 'x':
                    String[] splitIndices = move.substring(1).split("/");
                    moves.add(getMoveX(Integer.parseInt(splitIndices[0]), Integer.parseInt(splitIndices[1])));
                break;
                case 'p':
                    moves.add(getMoveP(move.substring(1, 2), move.substring(3, 4)));
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        }
        return moves;
    }

    private Consumer<StringBuilder> getMoveS(int numberOfPrograms, int startIndex) {
        return programLine -> programLine.insert(0, programLine.substring(startIndex)).setLength(numberOfPrograms);
    }

    private Consumer<StringBuilder> getMoveX(int program1Index, int program2Index) {
        return programLine -> {
            String program1 = programLine.substring(program1Index, program1Index + 1);
            String program2 = programLine.substring(program2Index, program2Index + 1);
            programLine.deleteCharAt(program1Index).insert(program1Index, program2);
            programLine.deleteCharAt(program2Index).insert(program2Index, program1);
        };
    }

    private Consumer<StringBuilder> getMoveP(String program1, String program2) {
        return programLine -> {
            int program1Index = programLine.indexOf(program1);
            int program2Index = programLine.indexOf(program2);
            programLine.deleteCharAt(program1Index).insert(program1Index, program2);
            programLine.deleteCharAt(program2Index).insert(program2Index, program1);
        };
    }

}
