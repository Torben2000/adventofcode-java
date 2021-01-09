package de.beachboys.aoc2019;

import de.beachboys.Day;
import de.beachboys.Util;
import org.javatuples.Triplet;

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
        Map<Integer, Triplet<Integer, Integer, Integer>> numbers = new HashMap<>();
        for (int i=0; i < layers.size(); i++) {
            String layerString = layers.get(i);
            Triplet<Integer, Integer, Integer> counts = new Triplet<>(0,0,0);
            for (int j=0; j <width*height; j++) {
                switch (layerString.substring(j, j+1)) {
                    case "0":
                        counts = counts.setAt0(counts.getValue0()+1);
                        break;
                    case "1":
                        counts = counts.setAt1(counts.getValue1()+1);
                        break;
                    case "2":
                        counts = counts.setAt2(counts.getValue2()+1);
                        break;
                }
            }
            numbers.put(i, counts);
        }

        int selectedLayer = 0;
        int minZeros = Integer.MAX_VALUE;
        for (Map.Entry<Integer, Triplet<Integer, Integer, Integer>> entry : numbers.entrySet()) {
            if (entry.getValue().getValue0() < minZeros) {
                selectedLayer = entry.getKey();
                minZeros = entry.getValue().getValue0();
            }
        }

        return numbers.get(selectedLayer).getValue1() * numbers.get(selectedLayer).getValue2();
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

        return Util.formatImage(imageString, width, height, Map.of("1", "*"));
    }

    private void setImageDimensionsFromUserInput() {
        width = Util.getIntValueFromUser("Width", width, io);
        height = Util.getIntValueFromUser("Height", height, io);
    }
}
