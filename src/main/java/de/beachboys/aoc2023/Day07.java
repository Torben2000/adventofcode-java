package de.beachboys.aoc2023;

import de.beachboys.Day;
import org.javatuples.Triplet;

import java.util.*;

public class Day07 extends Day {

    public static final int FIVE_OF_A_KIND = 7;
    public static final int FOUR_OF_A_KIND = 6;
    public static final int FULL_HOUSE = 5;
    public static final int THREE_OF_A_KIND = 4;
    public static final int TWO_PAIR = 3;
    public static final int ONE_PAIR = 2;
    public static final int HIGH_CARD = 1;

    public Object part1(List<String> input) {
        return runLogic(input, false);
    }

    public Object part2(List<String> input) {
        return runLogic(input, true);
    }

    private static long runLogic(List<String> input, boolean useJoker) {
        long result = 0;
        List<Triplet<Integer, String, Integer>> list = new ArrayList<>();
        for (String line : input) {
            String[] splitLine = line.split(" ");
            int bid = Integer.parseInt(splitLine[1]);
            String cards = splitLine[0];
            int score = getScore(useJoker, cards);
            String tieBreakingHexString = getTieBreakingHexString(useJoker, cards);
            list.add(Triplet.with(score, tieBreakingHexString, bid));
        }

        List<Triplet<Integer, String, Integer>> sorted = list.stream().sorted().toList();
        for (int i = 0; i < sorted.size(); i++) {
            result += (long) (i + 1) * sorted.get(i).getValue2();
        }
        return result;
    }

    private static int getScore(boolean useJoker, String cards) {
        Map<Character, Integer> cardCounts = new HashMap<>();
        for (char card : cards.toCharArray()) {
            if (!cardCounts.containsKey(card)) {
                cardCounts.put(card, 0);
            }
            cardCounts.put(card, cardCounts.get(card) + 1);
        }
        int maxCount = cardCounts.values().stream().mapToInt(Integer::intValue).max().orElseThrow();
        int jokerCount = useJoker ? cardCounts.getOrDefault('J', 0) : 0;
        return getScoreValue(cardCounts.size(), maxCount, jokerCount);
    }

    private static int getScoreValue(int differentCards, int maxCount, int jokerCount) {
        int score = 0;
        if (differentCards == 1) {
            score = FIVE_OF_A_KIND;
        } else if (differentCards == 2) {
            if (maxCount == THREE_OF_A_KIND) {
                if (jokerCount > 0) {
                    score = FIVE_OF_A_KIND;
                } else {
                    score = FOUR_OF_A_KIND;
                }
            } else {
                if (jokerCount > 1) {
                    score = FIVE_OF_A_KIND;
                } else if (jokerCount == 1) {
                    score = FOUR_OF_A_KIND;
                } else {
                    score = FULL_HOUSE;
                }
            }
        } else if (differentCards == 3) {
            if (maxCount == TWO_PAIR) {
                if (jokerCount > 0) {
                    score = FOUR_OF_A_KIND;
                } else {
                    score = THREE_OF_A_KIND;
                }
            } else {
                if (jokerCount > 1) {
                    score = FOUR_OF_A_KIND;
                } else if (jokerCount == 1) {
                    score = FULL_HOUSE;
                } else {
                    score = TWO_PAIR;
                }
            }
        } else if (differentCards == 4) {
            if (jokerCount > 0) {
                score = THREE_OF_A_KIND;
            } else {
                score = ONE_PAIR;
            }
        } else if (differentCards == 5) {
            if (jokerCount > 0) {
                score = ONE_PAIR;
            } else {
                score = HIGH_CARD;
            }
        }
        return score;
    }

    private static String getTieBreakingHexString(boolean useJoker, String cards) {
        return cards.replaceAll("A", "e")
                .replaceAll("K", "d")
                .replaceAll("Q", "c")
                .replaceAll("J", useJoker ? "1" : "b")
                .replaceAll("T", "a");
    }

}
