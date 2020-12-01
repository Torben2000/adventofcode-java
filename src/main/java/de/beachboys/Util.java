package de.beachboys;

import org.javatuples.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class Util {

    public static List<String> parseToList(String input, String separator) {
        return Arrays.asList(input.split(separator));
    }

    public static List<Integer> parseToIntList(String input, String separator) {
        return Arrays.stream(input.split(separator)).map(Integer::valueOf).collect(Collectors.toList());
    }

    public static List<Long> parseToLongList(String input, String separator) {
        return Arrays.stream(input.split(separator)).map(Long::valueOf).collect(Collectors.toList());
    }

    public static List<String> parseCsv(String input) {
        return parseToList(input, ",");
    }

    public static List<Integer> parseIntCsv(String input) {
        return parseToIntList(input, ",");
    }

    public static List<Long> parseLongCsv(String input) {
        return parseToLongList(input, ",");
    }

    public static String paintMap(Map<Pair<Integer, Integer>, String> colorMap, Map<String, String> valuesToPaint) {
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;
        for (Pair<Integer, Integer> point : colorMap.keySet()) {
            minX = Math.min(minX, point.getValue0());
            minY = Math.min(minY, point.getValue1());
            maxX = Math.max(maxX, point.getValue0());
            maxY = Math.max(maxY, point.getValue1());
        }
        int width = maxX - minX + 1;
        int height = maxY - minY + 1;
        StringBuilder imageString = new StringBuilder();
        imageString.append(" ".repeat(width*height));
        for (Pair<Integer, Integer> point : colorMap.keySet()) {
            int index = width * (point.getValue1() - minY) + (point.getValue0() - minX);
            imageString.replace(index, index + 1, colorMap.get(point));
        }
        return formatImage(imageString.toString(), width, height, valuesToPaint);
    }

    public static String formatImage(String imageString, int width, int height, Map<String, String> valuesToPaint) {
        final StringBuilder newImageString = new StringBuilder();
        if (valuesToPaint != null) {
            imageString.chars()
                    .forEach(i -> {
                        String charAsString = String.valueOf((char) i);
                        newImageString.append(valuesToPaint.getOrDefault(charAsString, " "));
                    });
        } else {
            newImageString.append(imageString);
        }
        return formatImage(newImageString.toString(), width, height);
    }

    public static String formatImage(String imageString, int width, int height) {
        StringBuilder returnValue = new StringBuilder();
        for (int i = 0; i < height; i++) {
            returnValue.append(imageString, i*width, (i+1)*width);
            returnValue.append("\n");
        }
        return returnValue.toString();
    }

}
