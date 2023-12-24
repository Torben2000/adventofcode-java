package de.beachboys.aoc2021;

import de.beachboys.Day;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;
import org.jooq.lambda.tuple.Tuple4;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day21 extends Day {

    private final Map<Integer, Integer> throwPossibilitiesPart2 = new HashMap<>();
    private final Map<Tuple4<Integer, Integer, Long, Long>, Tuple2<Long, Long>> winningUniversesCache = new HashMap<>();

    public Object part1(List<String> input) {
        int positionPlayer1 = Integer.parseInt(input.get(0).substring("Player 1 starting position: ".length()));
        int positionPlayer2 = Integer.parseInt(input.get(1).substring("Player 2 starting position: ".length()));

        long scorePlayer1 = 0;
        long scorePlayer2 = 0;
        long loserScore;
        long diceCount = 0;
        int nextDieThrow = 1;
        while (true) {
            diceCount += 3;
            positionPlayer1 = updatePosition(positionPlayer1, throwThreeDicePart1(nextDieThrow));
            scorePlayer1 = updateScore(scorePlayer1, positionPlayer1);
            if (scorePlayer1 >= 1000) {
                loserScore = scorePlayer2;
                break;
            }
            nextDieThrow = (nextDieThrow + 3) % 100;

            diceCount += 3;
            positionPlayer2 = updatePosition(positionPlayer2, throwThreeDicePart1(nextDieThrow));
            scorePlayer2 = updateScore(scorePlayer2, positionPlayer2);
            if (scorePlayer2 >= 1000) {
                loserScore = scorePlayer1;
                break;
            }
            nextDieThrow = (nextDieThrow + 3) % 100;
        }
        return loserScore * diceCount;
    }

    public Object part2(List<String> input) {
        int positionPlayer1 = Integer.parseInt(input.get(0).substring("Player 1 starting position: ".length()));
        int positionPlayer2 = Integer.parseInt(input.get(1).substring("Player 2 starting position: ".length()));

        throwPossibilitiesPart2.clear();
        for (int i = 1; i < 4; i++) {
            for (int j = 1; j < 4; j++) {
                for (int k = 1; k < 4; k++) {
                    int throwResult = i + j + k;
                    throwPossibilitiesPart2.put(throwResult, throwPossibilitiesPart2.getOrDefault(throwResult, 0) + 1);
                }
            }
        }

        winningUniversesCache.clear();
        Tuple2<Long, Long> result = getWinningUniverses(positionPlayer1, positionPlayer2, 0, 0);
        if (result.v1 > result.v2) {
            return result.v1;
        }
        return result.v2;
    }

    private int throwThreeDicePart1(int nextDieThrow) {
        return (nextDieThrow + nextDieThrow + 1 + nextDieThrow + 2) % 100;
    }

    private Tuple2<Long, Long> getWinningUniverses(int positionOfNextPlayer, int positionOfOtherPlayer, long scoreOfNextPlayer, long scoreOfOtherPlayer) {
        Tuple4<Integer, Integer, Long, Long> cacheKey = Tuple.tuple(positionOfNextPlayer, positionOfOtherPlayer, scoreOfNextPlayer, scoreOfOtherPlayer);
        if (winningUniversesCache.containsKey(cacheKey)) {
            return winningUniversesCache.get(cacheKey);
        }
        long winningUniversesOfNextPlayer = 0;
        long winningUniversesOfOtherPlayer = 0;
        for (Map.Entry<Integer, Integer> throwPossibility : throwPossibilitiesPart2.entrySet()) {
            int newPosition = updatePosition(positionOfNextPlayer, throwPossibility.getKey());
            long newScore = updateScore(scoreOfNextPlayer, newPosition);
            if (newScore >= 21) {
                winningUniversesOfNextPlayer += throwPossibility.getValue();
            } else {
                Tuple2<Long, Long> res = getWinningUniverses(positionOfOtherPlayer, newPosition, scoreOfOtherPlayer, newScore);
                winningUniversesOfNextPlayer += throwPossibility.getValue() * res.v2;
                winningUniversesOfOtherPlayer += throwPossibility.getValue() * res.v1;
            }
        }
        Tuple2<Long, Long> result = Tuple.tuple(winningUniversesOfNextPlayer, winningUniversesOfOtherPlayer);
        winningUniversesCache.put(cacheKey, result);
        return result;
    }

    private int updatePosition(int currentPosition, int diceThrowResult) {
        return (currentPosition + diceThrowResult) % 10;
    }

    private long updateScore(long currentScore, int newPosition) {
        long newScore = currentScore + newPosition;
        if (newPosition == 0) {
            newScore += 10;
        }
        return newScore;
    }

}
