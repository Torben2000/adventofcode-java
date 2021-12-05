package de.beachboys.aoc2021;

import de.beachboys.Day;
import org.javatuples.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day05 extends Day {

    public Object part1(List<String> input) {
        return runLogic(input, false);
    }

    public Object part2(List<String> input) {
        return runLogic(input, true);
    }

    private long runLogic(List<String> input, boolean diagonalsCount) {
        Map<Pair<Integer, Integer>, Integer> counts = new HashMap<>();
        Pattern p = Pattern.compile("([0-9]+),([0-9]+) -> ([0-9]+),([0-9]+)");
        for (String line : input) {
            Matcher m = p.matcher(line);
            if (m.matches()) {
                int x1 = Integer.parseInt(m.group(1));
                int y1 = Integer.parseInt(m.group(2));
                int x2 = Integer.parseInt(m.group(3));
                int y2 = Integer.parseInt(m.group(4));

                int minX = Math.min(x1, x2);
                int minY = Math.min(y1, y2);
                int maxX = Math.max(x1, x2);
                int maxY = Math.max(y1, y2);

                if (isHorizontalOrVerticalLine(minX, minY, maxX, maxY)) {
                    for (int i = minX; i <= maxX; i++) {
                        for (int j = minY; j <= maxY; j++) {
                            Pair<Integer, Integer> pos = Pair.with(i, j);
                            counts.put(pos, counts.getOrDefault(pos, 0) + 1);
                        }
                    }
                } else if (diagonalsCount) {
                    int y;
                    int yModifier;
                    if (xAndYGrowInSync(x1, y1, x2, y2, minX, minY)) {
                        y = minY;
                        yModifier = 1;
                    } else {
                        y = maxY;
                        yModifier = -1;
                    }
                    for (int i = minX; i <= maxX; i++) {
                        Pair<Integer, Integer> pos = Pair.with(i, y);
                        counts.put(pos, counts.getOrDefault(pos, 0) + 1);
                        y += yModifier;
                    }
                }
            }

        }
        return counts.values().stream().filter(i -> i > 1).count();
    }

    private boolean isHorizontalOrVerticalLine(int minX, int minY, int maxX, int maxY) {
        return minX == maxX || minY == maxY;
    }

    private boolean xAndYGrowInSync(int x1, int y1, int x2, int y2, int minX, int minY) {
        return minX == x1 && minY == y1 || minX == x2 && minY == y2;
    }

}
