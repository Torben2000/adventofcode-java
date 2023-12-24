package de.beachboys.aoc2023;

import de.beachboys.Day;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        List<Tuple3<Integer, String, Integer>> list = new ArrayList<>();
        for (String line : input) {
            String[] splitLine = line.split(" ");
            int bid = Integer.parseInt(splitLine[1]);
            String cards = splitLine[0];
            int score = getScore(useJoker, cards);
            String tieBreakingHexString = getTieBreakingHexString(useJoker, cards);
            list.add(Tuple.tuple(score, tieBreakingHexString, bid));
        }

        List<Tuple3<Integer, String, Integer>> sorted = list.stream().sorted().toList();
        for (int i = 0; i < sorted.size(); i++) {
            result += (long) (i + 1) * sorted.get(i).v3;
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

        int jokerCount = 0;
        if (useJoker) {
            jokerCount = cardCounts.getOrDefault('J', 0);
            cardCounts.remove('J');
        }

        int maxCount = cardCounts.values().stream().mapToInt(Integer::intValue).max().orElse(0);
        return getScoreValue(cardCounts.size(), maxCount + jokerCount);
    }

    private static int getScoreValue(int differentNormalCards, int maxCount) {
        int score = 0;
        if (differentNormalCards <= 1) {
            score = FIVE_OF_A_KIND;
        } else if (differentNormalCards == 2) {
            if (maxCount == 4) {
                score = FOUR_OF_A_KIND;
            } else {
                score = FULL_HOUSE;
            }
        } else if (differentNormalCards == 3) {
            if (maxCount == 3) {
                score = THREE_OF_A_KIND;
            } else {
                score = TWO_PAIR;
            }
        } else if (differentNormalCards == 4) {
            score = ONE_PAIR;
        } else if (differentNormalCards == 5) {
            score = HIGH_CARD;
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
