package de.beachboys.ec2024;

import de.beachboys.Quest;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.*;

public class Quest10 extends Quest {

    private Map<Tuple2<Integer, Integer>, String> symbolsOnShrine;
    @Override
    public Object part1(List<String> input) {
        symbolsOnShrine = Util.buildImageMap(input);
        return getRunicWordAndQuestionMarkReplaced(0, 0).v1;
    }

    @Override
    public Object part2(List<String> input) {
        symbolsOnShrine = Util.buildImageMap(input);
        long result = 0;
        for (int yGridIndex = 0; yGridIndex <= input.size() / 9; yGridIndex++) {
            for (int xGridIndex = 0; xGridIndex <= input.getFirst().length() / 9; xGridIndex++) {
                result += getPower(getRunicWordAndQuestionMarkReplaced(xGridIndex * 9, yGridIndex * 9).v1);
            }
        }
        return result;
    }

    @Override
    public Object part3(List<String> input) {
        symbolsOnShrine = Util.buildImageMap(input);
        Map<Tuple2<Integer, Integer>, String> runicWords = new HashMap<>();
        boolean questionMarkWasReplaced = true;
        while (questionMarkWasReplaced) {
            questionMarkWasReplaced = false;
            for (int yGridIndex = 0; yGridIndex < input.size() / 6; yGridIndex++) {
                for (int xGridIndex = 0; xGridIndex < input.getFirst().length() / 6; xGridIndex++) {
                    String oldValue = runicWords.get(Tuple.tuple(xGridIndex, yGridIndex));
                    if (oldValue == null || oldValue.isEmpty()) {
                        Tuple2<String, Boolean> runicWordAndQuestionMarkReplaced = getRunicWordAndQuestionMarkReplaced(xGridIndex * 6, yGridIndex * 6);
                        if (runicWordAndQuestionMarkReplaced.v2) {
                            questionMarkWasReplaced = true;
                        }
                        runicWords.put(Tuple.tuple(xGridIndex, yGridIndex), runicWordAndQuestionMarkReplaced.v1);
                    }
                }
            }
        }
        long result = 0;
        for (int yGridIndex = 0; yGridIndex < input.size() / 6; yGridIndex++) {
            for (int xGridIndex = 0; xGridIndex < input.getFirst().length() / 6; xGridIndex++) {
                result += getPower(runicWords.get(Tuple.tuple(xGridIndex, yGridIndex)));
            }
        }
        return result;

    }
    private Tuple2<String, Boolean> getRunicWordAndQuestionMarkReplaced(int xOffset, int yOffset) {
        boolean questionMarkWasReplaced = false;
        boolean change = true;
        while (change) {
            change = false;
            for (int y = 2 + yOffset; y < 6 + yOffset; y++) {
                for (int x = 2 + xOffset; x < 6 + xOffset; x++) {
                    if (".".equals(symbolsOnShrine.get(Tuple.tuple(x, y)))) {
                        Set<String> possibleSymbolsX = new HashSet<>();
                        Set<String> possibleSymbolsY = new HashSet<>();
                        for (int k = 0; k < 2; k++) {
                            possibleSymbolsX.add(symbolsOnShrine.get(Tuple.tuple(x, k + yOffset)));
                            possibleSymbolsY.add(symbolsOnShrine.get(Tuple.tuple(k + xOffset, y)));
                            possibleSymbolsX.add(symbolsOnShrine.get(Tuple.tuple(x, k + 6 + yOffset)));
                            possibleSymbolsY.add(symbolsOnShrine.get(Tuple.tuple(k + 6 + xOffset, y)));
                        }

                        HashSet<String> commonSymbols = new HashSet<>(possibleSymbolsX);
                        commonSymbols.retainAll(possibleSymbolsY);
                        if (commonSymbols.size() == 1 && !commonSymbols.contains("?")) {
                            symbolsOnShrine.put(Tuple.tuple(x, y), commonSymbols.stream().findFirst().orElseThrow());
                            change = true;
                        } else if (!possibleSymbolsX.contains("?")) {
                            for (int k = 2 + yOffset; k < 6 + yOffset; k++) {
                                String usedSymbol = symbolsOnShrine.get(Tuple.tuple(x, k));
                                if (!".".equals(usedSymbol)) {
                                    possibleSymbolsX.remove(usedSymbol);
                                }
                            }
                            if (possibleSymbolsX.size() == 1) {
                                String symbol = possibleSymbolsX.stream().findFirst().orElseThrow();
                                symbolsOnShrine.put(Tuple.tuple(x, y), symbol);
                                if (possibleSymbolsY.size() == 4 && possibleSymbolsY.contains("?")) {
                                    replaceQuestionMarkAtPosition(Tuple.tuple(xOffset, y), symbol);
                                    replaceQuestionMarkAtPosition(Tuple.tuple(xOffset + 1, y), symbol);
                                    replaceQuestionMarkAtPosition(Tuple.tuple(xOffset + 6, y), symbol);
                                    replaceQuestionMarkAtPosition(Tuple.tuple(xOffset + 7, y), symbol);
                                    questionMarkWasReplaced = true;
                                }
                                change = true;
                            }
                        } else if (!possibleSymbolsY.contains("?")) {
                            for (int k = 2 + xOffset; k < 6 + xOffset; k++) {
                                String usedSymbol = symbolsOnShrine.get(Tuple.tuple(k, y));
                                if (!".".equals(usedSymbol)) {
                                    possibleSymbolsY.remove(usedSymbol);
                                }
                            }
                            if (possibleSymbolsY.size() == 1) {
                                String symbol = possibleSymbolsY.stream().findFirst().orElseThrow();
                                symbolsOnShrine.put(Tuple.tuple(x, y), symbol);
                                if (possibleSymbolsX.size() == 4 && possibleSymbolsX.contains("?")) {
                                    replaceQuestionMarkAtPosition(Tuple.tuple(x, yOffset), symbol);
                                    replaceQuestionMarkAtPosition(Tuple.tuple(x, yOffset + 1), symbol);
                                    replaceQuestionMarkAtPosition(Tuple.tuple(x, yOffset + 6), symbol);
                                    replaceQuestionMarkAtPosition(Tuple.tuple(x, yOffset + 7), symbol);
                                    questionMarkWasReplaced = true;
                                }
                                change = true;
                            }
                        }
                    }
                }
            }
        }

        StringBuilder runicWord = new StringBuilder();
        for (int y = 2 + yOffset; y < 6 + yOffset; y++) {
            for (int x = 2 + xOffset; x < 6 + xOffset; x++) {
                runicWord.append(symbolsOnShrine.get(Tuple.tuple(x, y)));
            }
        }

        if (runicWord.toString().contains(".")) {
            return Tuple.tuple("", questionMarkWasReplaced);
        }
        return Tuple.tuple(runicWord.toString(), questionMarkWasReplaced);
    }

    private void replaceQuestionMarkAtPosition(Tuple2<Integer, Integer> pos, String symbol) {
        if ("?".equals(symbolsOnShrine.get(pos))) {
            symbolsOnShrine.put(pos, symbol);
        }
    }

    private static int getPower(String runicWord) {
        int power = 0;
        char[] charArray = runicWord.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            power += (i + 1) * (charArray[i] - 'A' + 1);
        }
        return power;
    }
}
