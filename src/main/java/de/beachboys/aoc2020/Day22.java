package de.beachboys.aoc2020;

import de.beachboys.Day;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple4;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Day22 extends Day {

    final LinkedList<Integer> completePlayer1Deck = new LinkedList<>();
    final LinkedList<Integer> completePlayer2Deck = new LinkedList<>();

    public Object part1(List<String> input) {
        return playGameAndGetWinnerScore(input, this::isPlayerOneWinnerPart1);
    }

    public Object part2(List<String> input) {
        return playGameAndGetWinnerScore(input, this::isPlayerOneWinnerPart2);
    }

    private Object playGameAndGetWinnerScore(List<String> input, Predicate<Tuple4<LinkedList<Integer>, Integer, LinkedList<Integer>, Integer>> isPlayerOneWinner) {
        buildCardDecks(input);
        boolean playerOneWins = playGame(completePlayer1Deck, completePlayer2Deck, isPlayerOneWinner);
        LinkedList<Integer> winningDeck = playerOneWins ? completePlayer1Deck : completePlayer2Deck;
        return getValue(winningDeck);
    }

    private void buildCardDecks(List<String> input) {
        boolean parsePlayer2 = false;
        for (String line : input) {
            if (line.isEmpty()) {
                parsePlayer2 = true;
                continue;
            }
            if (line.startsWith("Player")) {
                continue;
            }
            if (parsePlayer2) {
                completePlayer2Deck.add(Integer.parseInt(line));
            } else {
                completePlayer1Deck.add(Integer.parseInt(line));
            }
        }
    }

    private boolean playGame(LinkedList<Integer> player1Deck, LinkedList<Integer> player2Deck, Predicate<Tuple4<LinkedList<Integer>, Integer, LinkedList<Integer>, Integer>> isPlayerOneWinner) {
        Set<String> player1History = new HashSet<>();
        Set<String> player2History = new HashSet<>();
        while (!(player1Deck.isEmpty() || player2Deck.isEmpty())) {
            if (isCycleDetected(player1Deck, player2Deck, player1History, player2History)) {
                return true;
            }
            @SuppressWarnings("ConstantConditions") int player1Card = player1Deck.poll();
            @SuppressWarnings("ConstantConditions") int player2Card = player2Deck.poll();
            if (isPlayerOneWinner.test(Tuple.tuple(player1Deck, player1Card, player2Deck, player2Card))) {
                player1Deck.add(player1Card);
                player1Deck.add(player2Card);
            } else {
                player2Deck.add(player2Card);
                player2Deck.add(player1Card);
            }
        }
        return player2Deck.isEmpty();
    }

    private boolean isCycleDetected(LinkedList<Integer> player1Deck, LinkedList<Integer> player2Deck, Set<String> player1History, Set<String> player2History) {
        boolean cycleDetected = false;
        String historyIdPlayer1 = player1Deck.stream().map(i -> i + "").collect(Collectors.joining("-"));
        String historyIdPlayer2 = player2Deck.stream().map(i -> i + "").collect(Collectors.joining("-"));
        if (player1History.contains(historyIdPlayer1) || player2History.contains(historyIdPlayer2)) {
            cycleDetected = true;
        }
        player1History.add(historyIdPlayer1);
        player2History.add(historyIdPlayer2);
        return cycleDetected;
    }

    private boolean isPlayerOneWinnerPart1(Tuple4<LinkedList<Integer>, Integer, LinkedList<Integer>, Integer> currentState) {
        int player1Card = currentState.v2;
        int player2Card = currentState.v4;
        return player1Card > player2Card;
    }

    private boolean isPlayerOneWinnerPart2(Tuple4<LinkedList<Integer>, Integer, LinkedList<Integer>, Integer> currentState) {
        LinkedList<Integer> player1Deck = currentState.v1;
        LinkedList<Integer> player2Deck = currentState.v3;
        int player1Card = currentState.v2;
        int player2Card = currentState.v4;
        if (player1Card <= player1Deck.size() && player2Card <= player2Deck.size()) {
            LinkedList<Integer> player1SubDeck = new LinkedList<>(player1Deck.subList(0, player1Card));
            LinkedList<Integer> player2SubDeck = new LinkedList<>(player2Deck.subList(0, player2Card));
            return playGame(player1SubDeck, player2SubDeck, this::isPlayerOneWinnerPart2);
        }
        return player1Card > player2Card;
    }

    private int getValue(LinkedList<Integer> deck) {
        int sum = 0;
        for (int i = 0; i < deck.size(); i++) {
            sum += deck.get(i) * (deck.size() - i);
        }
        return sum;
    }

}
