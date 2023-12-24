package de.beachboys.aoc2021;

import de.beachboys.Day;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class Day17 extends Day {

    public Object part1(List<String> input) {
        Set<Tuple2<Integer, Integer>> hits = runLogic(input);
        int maxY = hits.stream().mapToInt(Tuple2::v2).max().orElseThrow();
        return maxY * (maxY + 1) / 2;
     }

    public Object part2(List<String> input) {
        return runLogic(input).size();
    }

    private Set<Tuple2<Integer, Integer>> runLogic(List<String> input) {
        String[] xy = input.get(0).substring("target area: x=".length()).split(", y=");
        String[]xS = xy[0].split(Pattern.quote(".."));
        String[]yS = xy[1].split(Pattern.quote(".."));
        int minX = Integer.parseInt(xS[0]);
        int maxX = Integer.parseInt(xS[1]);
        int minY = Integer.parseInt(yS[0]);
        int maxY = Integer.parseInt(yS[1]);

        Set<Tuple2<Integer, Integer>> hits = new HashSet<>();
        for (int i = 1; i <= maxX; i++) {
            for (int j = minY; j <= -minY; j++) {
                int xPos = 0;
                int speedX = i;
                int yPos = 0;
                int speedY = j;
                while (xPos <= maxX && yPos >= minY) {
                    xPos += speedX;
                    yPos += speedY;
                    if (minY <= yPos && maxY >= yPos && minX <= xPos && maxX >= xPos) {
                        hits.add(Tuple.tuple(i, j));
                        break;
                    }
                    speedY -= 1;
                    speedX = Math.max(0, speedX - 1);
                }
            }
        }
        return hits;
    }

}
