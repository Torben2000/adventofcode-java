package de.beachboys.aoc2016;

import de.beachboys.Day;
import de.beachboys.OCR;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day08 extends Day {

    public Object part1(List<String> input) {
        Set<Tuple2<Integer, Integer>> pixels = buildPixelSet(input);
        return pixels.size();
    }

    public Object part2(List<String> input) {
        Set<Tuple2<Integer, Integer>> pixels = buildPixelSet(input);
        return OCR.runOCRAndReturnOriginalOnError(Util.paintSet(pixels));
    }

    private Set<Tuple2<Integer, Integer>> buildPixelSet(List<String> input) {
        int width = Util.getIntValueFromUser("Width", 50, io);
        int height = Util.getIntValueFromUser("Height", 6, io);
        Set<Tuple2<Integer, Integer>> pixels = new HashSet<>();
        Pattern rectPattern = Pattern.compile("rect ([0-9]+)x([0-9]+)");
        Pattern rotatePattern = Pattern.compile("rotate (row y|column x)=([0-9]+) by ([0-9]+)");
        for (String line : input) {
            if (line.startsWith("rect")) {
                Matcher m = rectPattern.matcher(line);
                if (m.matches()) {
                    int x = Integer.parseInt(m.group(1));
                    int y = Integer.parseInt(m.group(2));
                    for (int i = 0; i < x; i++) {
                        for (int j = 0; j < y; j++) {
                            pixels.add(Tuple.tuple(i, j));
                        }
                    }
                }
            } else {
                Matcher m = rotatePattern.matcher(line);
                if (m.matches()) {
                    boolean isRow = "row y".equals(m.group(1));
                    int target = Integer.parseInt(m.group(2));
                    int shift = Integer.parseInt(m.group(3));
                    Set<Tuple2<Integer, Integer>> newPixels = new HashSet<>();
                    for (Tuple2<Integer, Integer> pixel : pixels) {
                        if (target == (isRow ? pixel.v2 : pixel.v1)) {
                            int xShift = isRow ? shift : 0;
                            int yShift = isRow ? 0 : shift;
                            newPixels.add(Tuple.tuple((pixel.v1 + xShift) % width, (pixel.v2 + yShift) % height));
                        } else {
                            newPixels.add(pixel);
                        }
                    }
                    pixels = newPixels;
                }
            }
        }
        return pixels;
    }

}
