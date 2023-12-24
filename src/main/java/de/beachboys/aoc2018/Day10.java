package de.beachboys.aoc2018;

import de.beachboys.Day;
import de.beachboys.OCR;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

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
        List<Tuple2<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>>> points = buildInitialPointsList(input);
        String userInput = "";
        skyContent = "";
        seconds = 0;

        while (!"s".equals(userInput)) {
            seconds++;
            List<Tuple2<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>>> newPoints = new ArrayList<>();
            int minY = Integer.MAX_VALUE;
            int maxY = Integer.MIN_VALUE;
            for (Tuple2<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>> point : points) {
                Tuple2<Integer, Integer> newPosition = Tuple.tuple(point.v1.v1 + point.v2.v1, point.v1.v2 + point.v2.v2);
                newPoints.add(Tuple.tuple(newPosition, point.v2));
                minY = Math.min(minY, newPosition.v2);
                maxY = Math.max(maxY, newPosition.v2);
            }
            points = newPoints;
            if (maxY - minY <= MAX_LETTER_HEIGHT) {
                fillAndPaintSkyContent(points);
                userInput = io.getInput("s to stop and get solution, just press Enter to continue and try next valid list of positions");
            }
        }
    }

    private void fillAndPaintSkyContent(List<Tuple2<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>>> points) {
        Map<Tuple2<Integer, Integer>, String> skyContentMap = new HashMap<>();
        for (Tuple2<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>> point : points) {
            skyContentMap.put(point.v1, "*");
        }
        skyContent = OCR.runOCRAndReturnOriginalOnError(Util.paintMap(skyContentMap));
        io.logDebug(skyContent);
    }

    private List<Tuple2<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>>> buildInitialPointsList(List<String> input) {
        Pattern p = Pattern.compile("position=< *(-*[0-9]+), *(-*[0-9]+)> velocity=< *(-*[0-9]+), *(-*[0-9]+)>");
        List<Tuple2<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>>> points = new ArrayList<>();
        for (String line : input) {
            Matcher m = p.matcher(line);
            if (m.matches()) {
                Tuple2<Integer, Integer> position = Tuple.tuple(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)));
                Tuple2<Integer, Integer> velocity = Tuple.tuple(Integer.parseInt(m.group(3)), Integer.parseInt(m.group(4)));
                points.add(Tuple.tuple(position, velocity));
            }
        }
        return points;
    }

}
