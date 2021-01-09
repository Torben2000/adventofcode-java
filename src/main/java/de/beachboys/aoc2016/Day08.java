package de.beachboys.aoc2016;

import de.beachboys.Day;
import de.beachboys.Util;
import org.javatuples.Pair;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day08 extends Day {

    public Object part1(List<String> input) {
        Set<Pair<Integer, Integer>> pixels = buildPixelSet(input);
        return pixels.size();
    }

    public Object part2(List<String> input) {
        Set<Pair<Integer, Integer>> pixels = buildPixelSet(input);
        return Util.paintMap(pixels.stream().collect(Collectors.toMap(Function.identity(), p -> "*")));
    }

    private Set<Pair<Integer, Integer>> buildPixelSet(List<String> input) {
        int width = Util.getIntValueFromUser("Width", 50, io);
        int height = Util.getIntValueFromUser("Height", 6, io);
        Set<Pair<Integer, Integer>> pixels = new HashSet<>();
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
                            pixels.add(Pair.with(i, j));
                        }
                    }
                }
            } else {
                Matcher m = rotatePattern.matcher(line);
                if (m.matches()) {
                    boolean isRow = "row y".equals(m.group(1));
                    int target = Integer.parseInt(m.group(2));
                    int shift = Integer.parseInt(m.group(3));
                    Set<Pair<Integer, Integer>> newPixels = new HashSet<>();
                    for (Pair<Integer, Integer> pixel : pixels) {
                        if (target == (isRow ? pixel.getValue1() : pixel.getValue0())) {
                            int xShift = isRow ? shift : 0;
                            int yShift = isRow ? 0 : shift;
                            newPixels.add(Pair.with((pixel.getValue0() + xShift) % width, (pixel.getValue1() + yShift) % height));
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
