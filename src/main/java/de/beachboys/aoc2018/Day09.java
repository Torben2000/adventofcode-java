package de.beachboys.aoc2018;

import de.beachboys.Day;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day09 extends Day {

    private static class Marble {

        private final long value;

        private Marble counterClockwise;
        private Marble clockwise;

        public Marble(long value) {
            this.value = value;
        }
    }

    private int numOfPlayers = 0;
    private long numOfMarbles = 0;

    public Object part1(List<String> input) {
        parseInput(input);
        return runGameAndReturnHighestScore();
    }

    public Object part2(List<String> input) {
        parseInput(input);
        numOfMarbles *= 100;
        return runGameAndReturnHighestScore();
    }

    private long runGameAndReturnHighestScore() {
        Marble currentMarble = new Marble(0);
        currentMarble.clockwise = currentMarble;
        currentMarble.counterClockwise = currentMarble;

        Map<Integer, Long> playerScore = new HashMap<>();
        for (int i = 1; i <= numOfMarbles; i++) {
            if (i % 23 == 0) {
                int currentPlayer = i % numOfPlayers;
                currentMarble = currentMarble.counterClockwise.counterClockwise.counterClockwise.counterClockwise.counterClockwise.counterClockwise;
                playerScore.merge(currentPlayer, i + removePreviousMarbleAndReturnScore(currentMarble), Long::sum);
            } else {
                currentMarble = insertMarbleAfterReferenceMarble(new Marble(i), currentMarble.clockwise);
            }
        }
        return playerScore.values().stream().mapToLong(Long::longValue).max().orElseThrow();
    }

    private long removePreviousMarbleAndReturnScore(Marble referenceMarble) {
        long removedMarbleScore = referenceMarble.counterClockwise.value;
        referenceMarble.counterClockwise = referenceMarble.counterClockwise.counterClockwise;
        referenceMarble.counterClockwise.clockwise = referenceMarble;
        return removedMarbleScore;
    }

    private Marble insertMarbleAfterReferenceMarble(Marble marble, Marble referenceMarble) {
        marble.clockwise = referenceMarble.clockwise;
        marble.counterClockwise = referenceMarble;
        marble.clockwise.counterClockwise = marble;
        marble.counterClockwise.clockwise = marble;
        return marble;
    }

    private void parseInput(List<String> input) {
        Pattern p = Pattern.compile("([0-9]+) players; last marble is worth ([0-9]+) points");
        Matcher m = p.matcher(input.get(0));
        if (m.matches()) {
            numOfPlayers = Integer.parseInt(m.group(1));
            numOfMarbles = Integer.parseInt(m.group(2));
        }
    }

}
