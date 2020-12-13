package de.beachboys;

import org.javatuples.Pair;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.dot.DOTExporter;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
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

    public static String paintMap(Map<Pair<Integer, Integer>, String> map) {
        Map<String, String> valuesToPaint = map.values().stream().distinct().collect(Collectors.toMap(Function.identity(), Function.identity()));
        return paintMap(map, valuesToPaint);
    }

    public static String paintMap(Map<Pair<Integer, Integer>, String> map, Map<String, String> valuesToPaint) {
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;
        for (Pair<Integer, Integer> point : map.keySet()) {
            minX = Math.min(minX, point.getValue0());
            minY = Math.min(minY, point.getValue1());
            maxX = Math.max(maxX, point.getValue0());
            maxY = Math.max(maxY, point.getValue1());
        }
        int width = maxX - minX + 1;
        int height = maxY - minY + 1;
        StringBuilder imageString = new StringBuilder();
        imageString.append(" ".repeat(width*height));
        for (Pair<Integer, Integer> point : map.keySet()) {
            int index = width * (point.getValue1() - minY) + (point.getValue0() - minX);
            imageString.replace(index, index + 1, map.get(point));
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

    public static Map<Pair<Integer, Integer>, String> buildImageMap(String imageWithLineBreaks) {
        return buildImageMap(parseToList(imageWithLineBreaks, "\n"));
    }

    public static Map<Pair<Integer, Integer>, String> buildImageMap(List<String> imageLines) {
        Map<Pair<Integer, Integer>, String> map = new HashMap<>();
        for (int j = 0; j < imageLines.size(); j++) {
            String line = imageLines.get(j);
            for (int i = 0; i < line.length(); i++) {
                map.put(Pair.with(i, j), line.substring(i, i + 1));
            }
        }
        return map;
    }

    public static long greatestCommonDivisor(long long1, long long2) {
        if (long1 == long2 || long2 == 0) {
            return long1;
        }
        return greatestCommonDivisor(long2, long1 % long2);
    }

    public static long leastCommonMultiple(long long1, long long2) {
        return long1 * (long2 / greatestCommonDivisor(long1, long2));
    }

    /**
     * Based on
     * https://www.geeksforgeeks.org/multiplicative-inverse-under-modulo-m/
     */
    public static long modInverse(long a, long m)
    {
        long m0 = m;
        long y = 0;
        long x = 1;

        if (m == 1) {
            return 0;
        }

        while (a > 1) {
            // q is quotient
            long q = a / m;

            long t = m;

            // m is remainder now, process
            // same as Euclid's algo
            m = a % m;
            a = t;
            t = y;

            // Update x and y
            y = x - q * y;
            x = t;
        }

        // Make x positive
        if (x < 0) {
            x += m0;
        }

        return x;
    }

    public static long chineseRemainderTheorem(List<Pair<Long, Long>> numbersAndRemainders) {
        long[] numbers = new long[numbersAndRemainders.size()];
        long[] remainders = new long[numbersAndRemainders.size()];
        for (int i = 0; i < numbersAndRemainders.size(); i++) {
            Pair<Long, Long> numberAndRemainder = numbersAndRemainders.get(i);
            numbers[i] = numberAndRemainder.getValue0();
            remainders[i] = numberAndRemainder.getValue1();
        }

        return chineseRemainderTheorem(numbers, remainders);
    }

    /**
     * Based on
     * https://www.geeksforgeeks.org/chinese-remainder-theorem-set-2-implementation/
     */
    private static long chineseRemainderTheorem(long[] numbers, long[] remainders) {
        long product = 1;
        for (long number : numbers) {
            product *= number;
        }

        long result = 0;
        for (int i = 0; i < numbers.length; i++) {
            long partialProduct = product / numbers[i];
            result += remainders[i] * modInverse(partialProduct, numbers[i]) * partialProduct;
        }

        // the normal algorithm just returns (result % product)...but somehow we need it...
        return product - (result % product);
    }


    public static String printGraphAsDOT(Graph<String, DefaultWeightedEdge> graph, Map<String, String> replacements) {
        DOTExporter<String, DefaultWeightedEdge> exporter = new DOTExporter<>(v -> {
            String vertexId = v;
            for (Map.Entry<String, String> replacement : replacements.entrySet()) {
                vertexId = vertexId.replace(replacement.getKey(), replacement.getValue());
            }
            return vertexId;
        });
        exporter.setEdgeAttributeProvider(e -> Map.of("label", DefaultAttribute.createAttribute(graph.getEdgeWeight(e))));
        Writer writer = new StringWriter();
        exporter.exportGraph(graph, writer);
        return writer.toString();
    }
}
