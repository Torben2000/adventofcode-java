package de.beachboys.aoc2017;

import de.beachboys.Day;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day13 extends Day {

    private final Map<Integer, Integer> layerToRange = new HashMap<>();


    public Object part1(List<String> input) {
        parseInputToMap(input);
        return getSeverity();
    }

    public Object part2(List<String> input) {
        parseInputToMap(input);
        int maxLayer = getMaxLayer();
        int maxValue = 100000000;
        for (int delay = 0; delay < maxValue; delay++) {
            if (!checkForDetection(maxLayer, delay)) {
                return delay;
            }
        }
        return "not possible";
   }

    private int getSeverity() {
        int maxLayer = getMaxLayer();
        int sum = 0;
        for (int i = 0; i <= maxLayer; i++) {
            if (isDetected(i, 0)) {
                sum += i * layerToRange.get(i);
            }
        }
        return sum;
    }

    private boolean checkForDetection(int maxLayer, int delay) {
        boolean detected = false;
        for (int i = 0; i <= maxLayer; i++) {
            if (isDetected(i, delay)) {
                detected = true;
                break;
            }
        }
        return detected;
    }

    private int getMaxLayer() {
        return layerToRange.keySet().stream().mapToInt(Integer::intValue).max().orElseThrow();
    }

    private boolean isDetected(int layer, int delay) {
        return layerToRange.containsKey(layer) && (layer + delay) % (2 * (layerToRange.get(layer) - 1)) == 0;
    }

    private void parseInputToMap(List<String> input) {
        layerToRange.clear();
        Pattern p = Pattern.compile("([0-9]+): ([0-9]+)");
        for (String line : input) {
            Matcher m = p.matcher(line);
            if (m.matches()) {
                layerToRange.put(Integer.valueOf(m.group(1)), Integer.valueOf(m.group(2)));
            }
        }

    }

}
