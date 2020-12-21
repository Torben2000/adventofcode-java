package de.beachboys.aoc2019;

import de.beachboys.Day;

import java.util.*;
import java.util.function.Function;

public class Day22 extends Day {

    public Object part1(List<String> input) {
        long deckSize = 10007;
        String deckSizeAsInput = io.getInput("Deck size (default 10007):");
        if (!deckSizeAsInput.isEmpty()) {
            deckSize = Long.parseLong(deckSizeAsInput);
        }
        long positionOfCard = 2019;
        String positionOfCardAsInput = io.getInput("Position of card (default 2019):");
        if (!positionOfCardAsInput.isEmpty()) {
            positionOfCard = Long.parseLong(positionOfCardAsInput);
        }

        List<Function<Long, Long>> ops = new ArrayList<>();

        long finalDeckSize = deckSize;
        for (String line : input) {
            if (line.startsWith("deal into")) {
                ops.add(pos -> finalDeckSize - pos - 1);
            } else if (line.startsWith("cut")) {
                final int cut = Integer.parseInt(line.substring(4));
                ops.add(pos -> (pos - cut + finalDeckSize) % finalDeckSize);
            } else {
                final int increment = Integer.parseInt(line.substring("deal with increment ".length()));
                ops.add(pos -> (pos * increment) % finalDeckSize);
            }
        }

        for (Function<Long, Long> op : ops) {
            positionOfCard = op.apply(positionOfCard);
        }


        return positionOfCard;
    }
    public Object part2(List<String> input) {
        return 2;
    }

}
