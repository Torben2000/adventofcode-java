package de.beachboys.aoc2019;

import de.beachboys.Day;
import de.beachboys.OCR;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day08 extends Day {

    int width = 25;
    int height = 6;

    public Object part1(List<String> input) {
        setImageDimensionsFromUserInput();
        List<String> layers = getLayers(input);
        Map<Integer, Tuple3<Integer, Integer, Integer>> numbers = new HashMap<>();
        for (int i=0; i < layers.size(); i++) {
            String layerString = layers.get(i);
            Tuple3<Integer, Integer, Integer> counts = new Tuple3<>(0,0,0);
            for (int j=0; j <width*height; j++) {
                switch (layerString.substring(j, j+1)) {
                    case "0":
                        counts = Tuple.tuple(counts.v1+1, counts.v2, counts.v3);
                        break;
                    case "1":
                        counts = Tuple.tuple(counts.v1, counts.v2+1, counts.v3);
                        break;
                    case "2":
                        counts = Tuple.tuple(counts.v1, counts.v2, counts.v3+1);
                        break;
                }
            }
            numbers.put(i, counts);
        }

        int selectedLayer = 0;
        int minZeros = Integer.MAX_VALUE;
        for (Map.Entry<Integer, Tuple3<Integer, Integer, Integer>> entry : numbers.entrySet()) {
            if (entry.getValue().v1 < minZeros) {
                selectedLayer = entry.getKey();
                minZeros = entry.getValue().v1;
            }
        }

        return numbers.get(selectedLayer).v2 * numbers.get(selectedLayer).v3;
    }

    private List<String> getLayers(List<String> input) {
        String realInput = input.get(0);
        List<String> layers = new ArrayList<>();
        while (!realInput.isEmpty()) {
            layers.add(realInput.substring(0, width*height));
            realInput = realInput.substring(width*height);
        }
        return layers;
    }

    public Object part2(List<String> input) {
        setImageDimensionsFromUserInput();
        List<String> layers = getLayers(input);

        String imageString = layers.get(0);
        for (String layer : layers) {
            for (int j=0; j <width*height; j++) {
                if (imageString.charAt(j) == '2') {
                    imageString = imageString.substring(0, j) + layer.charAt(j) + imageString.substring(j+1);
                }
            }
        }

        return OCR.runOCRAndReturnOriginalOnError(Util.formatImage(imageString, width, height, Map.of("1", "*")));
    }

    private void setImageDimensionsFromUserInput() {
        width = Util.getIntValueFromUser("Width", width, io);
        height = Util.getIntValueFromUser("Height", height, io);
    }
}
