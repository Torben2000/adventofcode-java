package de.beachboys.aoc2018;

import de.beachboys.Day;
import de.beachboys.Util;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day10 extends Day {

    public static final int MAX_LETTER_HEIGHT = 10;
    private int seconds;
    private String skyContent;

    public Object part1(List<String> input) {
        runLogic(input);
        return skyContent;
    }

    public Object part2(List<String> input) {
        runLogic(input);
        return seconds;
    }

    private void runLogic(List<String> input) {
        List<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> points = buildInitialPointsList(input);
        String userInput = "";
        skyContent = "";
        seconds = 0;

        while (!"s".equals(userInput)) {
            seconds++;
            List<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> newPoints = new ArrayList<>();
            int minY = Integer.MAX_VALUE;
            int maxY = Integer.MIN_VALUE;
            for (Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> point : points) {
                Pair<Integer, Integer> newPosition = Pair.with(point.getValue0().getValue0() + point.getValue1().getValue0(), point.getValue0().getValue1() + point.getValue1().getValue1());
                newPoints.add(Pair.with(newPosition, point.getValue1()));
                minY = Math.min(minY, newPosition.getValue1());
                maxY = Math.max(maxY, newPosition.getValue1());
            }
            points = newPoints;
            if (maxY - minY <= MAX_LETTER_HEIGHT) {
                fillAndPaintSkyContent(points);
                userInput = io.getInput("s to stop and get solution, just press Enter to continue and try next valid list of positions");
            }
        }
    }

    private void fillAndPaintSkyContent(List<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> points) {
        Map<Pair<Integer, Integer>, String> skyContentMap = new HashMap<>();
        for (Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> point : points) {
            skyContentMap.put(point.getValue0(), "*");
        }
        skyContent = Util.paintMap(skyContentMap);
        io.logDebug(skyContent);
    }

    private List<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> buildInitialPointsList(List<String> input) {
        Pattern p = Pattern.compile("position=< *(-*[0-9]+), *(-*[0-9]+)> velocity=< *(-*[0-9]+), *(-*[0-9]+)>");
        List<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> points = new ArrayList<>();
        for (String line : input) {
            Matcher m = p.matcher(line);
            if (m.matches()) {
                Pair<Integer, Integer> position = Pair.with(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)));
                Pair<Integer, Integer> velocity = Pair.with(Integer.parseInt(m.group(3)), Integer.parseInt(m.group(4)));
                points.add(Pair.with(position, velocity));
            }
        }
        return points;
    }

}
