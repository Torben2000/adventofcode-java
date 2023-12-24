package de.beachboys.aoc2017;

import de.beachboys.Day;
import de.beachboys.Util;

import java.util.List;

public class Day10 extends Day {

    public Object part1(List<String> input) {
        KnotHash knotHash = new KnotHash(Util.getIntValueFromUser("List length", 256, io));
        List<Integer> inputLengths = Util.parseIntCsv(input.getFirst());
        knotHash.knotHashRound(inputLengths);
        return knotHash.getList().get(0) * knotHash.getList().get(1);
    }

    public Object part2(List<String> input) {
        KnotHash knotHash = new KnotHash();
        return knotHash.knotHashToHex(input.getFirst());
    }

}
