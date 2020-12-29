package de.beachboys.aoc2015;

import de.beachboys.Day;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day12 extends Day {

    public Object part1(List<String> input) {
        String json = input.get(0);
        return getSumOfAllInts(json);
    }

    public Object part2(List<String> input) {
        String json = input.get(0);
        while (json.contains(":\"red\"")) {
            int foundIndex = json.indexOf(":\"red\"");
            int objectStartIndex = json.substring(0, foundIndex).lastIndexOf("{");
            int objectEndIndex = json.indexOf("}", objectStartIndex);
            if (objectEndIndex < foundIndex) {
                // delete sub-object before "red"
                json = json.substring(0, objectStartIndex) + "[]" + json.substring(objectEndIndex + 1);
            } else {
                int subObjectStartIndex = json.substring(foundIndex, objectEndIndex).lastIndexOf("{");
                if (subObjectStartIndex > 0) {
                    // delete sub-object after "red"
                    json = json.substring(0, foundIndex + subObjectStartIndex) + "[]" + json.substring(objectEndIndex + 1);
                } else {
                    // delete object with "red"
                    json = json.substring(0, objectStartIndex) + "[]" + json.substring(objectEndIndex + 1);
                }
            }
        }
        return getSumOfAllInts(json);
    }

    private int getSumOfAllInts(String json) {
        int sum = 0;
        Matcher matcher = Pattern.compile("-*[0-9]+").matcher(json);
        while (matcher.find()) {
            sum += Integer.parseInt(matcher.group());
        }
        return sum;
    }

}
