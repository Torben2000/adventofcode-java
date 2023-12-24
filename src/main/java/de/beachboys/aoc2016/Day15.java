package de.beachboys.aoc2016;

import de.beachboys.Day;
import de.beachboys.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day15 extends Day {

    private record Disc(int index, int size, int position) {}

    public Object part1(List<String> input) {
        List<Disc> discs = parseDiscs(input);
        return runLogic(discs);
    }

    public Object part2(List<String> input) {
        List<Disc> discs = parseDiscs(input);
        discs.add(new Disc(discs.stream().mapToInt(disc -> disc.index).max().orElseThrow() + 1, 11, 0));
        return runLogic(discs);
    }

    private long runLogic(List<Disc> discs) {
        long currentTime = 0;
        long currentFactor = 1;
        for (Disc disc : discs) {
            int offset = Math.floorMod((disc.index + disc.position) * -1, disc.size);
            while (currentTime % disc.size != offset) {
                currentTime += currentFactor;
            }
            currentFactor = Util.leastCommonMultiple(currentFactor, disc.size);
        }
        return currentTime;
    }

    private List<Disc> parseDiscs(List<String> input) {
        List<Disc> discs = new ArrayList<>();
        Pattern p = Pattern.compile("Disc #([0-9]+) has ([0-9]+) positions; at time=([0-9]+), it is at position ([0-9]+).");
        for (String line : input) {
            Matcher m = p.matcher(line);
            if (m.matches()) {
                int index = Integer.parseInt(m.group(1));
                int size = Integer.parseInt(m.group(2));
                int position = Math.floorMod(Integer.parseInt(m.group(4)) - Integer.parseInt(m.group(3)), size);
                discs.add(new Disc(index, size, position));
            }
        }
        return discs;
    }

}
