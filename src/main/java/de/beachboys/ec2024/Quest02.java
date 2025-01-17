package de.beachboys.ec2024;

import de.beachboys.Direction;
import de.beachboys.Quest;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Quest02 extends Quest {

    @Override
    public Object part1(List<String> input) {
        long result = 0;
        String inscription = input.getLast();
        for (String word : parseWords(input)) {
            int index = 0;
            while (index < inscription.length()) {
                index = inscription.indexOf(word, index);
                if (index >= 0) {
                    result++;
                    index++;
                } else {
                    break;
                }
            }
        }
        return result;
    }

    @Override
    public Object part2(List<String> input) {
        List<String> words = parseWords(input);
        long result = 0;
        for (int i = 2; i < input.size(); i++) {
            String line = input.get(i);
            Set<Integer> runicSymbolPositions = new HashSet<>();
            for (String word : words) {
                String wordStart = word.substring(0, 1);
                for (int j = 0; j < line.length(); j++) {
                    if (wordStart.equals(line.substring(j, j+1))) {
                        boolean match = true;
                        Set<Integer> tempRunicSymbolPositions = new HashSet<>();
                        for (int k = 0; k < word.length(); k++) {
                            if ((j+k < line.length()) && word.substring(k, k+1).equals(line.substring(j+k, j+k+1))) {
                                tempRunicSymbolPositions.add(j+k);
                            } else {
                                match = false;
                                break;
                            }
                        }
                        if (match) {
                            runicSymbolPositions.addAll(tempRunicSymbolPositions);
                        }
                        match = true;
                        tempRunicSymbolPositions = new HashSet<>();
                        for (int k = 0; k < word.length(); k++) {
                            if ((j-k > 0) && word.substring(k, k+1).equals(line.substring(j-k, j-k+1))) {
                                tempRunicSymbolPositions.add(j-k);
                            } else {
                                match = false;
                                break;
                            }
                        }
                        if (match) {
                            runicSymbolPositions.addAll(tempRunicSymbolPositions);
                        }
                    }
                }
            }
            result += runicSymbolPositions.size();
        }
        return result;
    }

    @Override
    public Object part3(List<String> input) {
        List<String> words = parseWords(input);
        Map<Tuple2<Integer, Integer>, String> scaleGrid = Util.buildImageMap(input.subList(2, input.size()));
        int lineLength = input.getLast().length();
        Set<Tuple2<Integer, Integer>> scales = new HashSet<>();
        for (String word : words) {
            String wordStart = word.substring(0, 1);
            Set<Tuple2<Integer, Integer>> startPositions = scaleGrid.entrySet().stream().filter(e -> e.getValue().equals(wordStart)).map(Map.Entry::getKey).collect(Collectors.toSet());
            for (Tuple2<Integer, Integer> startPos : startPositions) {
                for (Direction value : Direction.values()) {
                    boolean match = true;
                    Set<Tuple2<Integer, Integer>> tempScalePositions = new HashSet<>();
                    for (int i = 0; i < word.length(); i++) {
                        Tuple2<Integer, Integer> newPos = value.move(startPos, i);
                        if (newPos.v1 < 0) {
                            newPos = Tuple.tuple(newPos.v1 + lineLength, newPos.v2);
                        }
                        if (newPos.v1 >= lineLength) {
                            newPos = Tuple.tuple(newPos.v1 - lineLength, newPos.v2);
                        }
                        if (!word.substring(i, i+1).equals(scaleGrid.get(newPos))) {
                            match = false;
                            break;
                        } else {
                            tempScalePositions.add(newPos);
                        }
                    }
                    if (match) {
                        scales.addAll(tempScalePositions);
                    }
                }
            }
        }
        return scales.size();
    }

    private static List<String> parseWords(List<String> input) {
        return Util.parseToList(input.getFirst().substring("WORDS:".length()), ",");
    }
}
