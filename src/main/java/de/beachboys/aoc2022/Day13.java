package de.beachboys.aoc2022;

import de.beachboys.Day;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day13 extends Day {

    public Object part1(List<String> input) {
        long result = 0;

        List<Tuple2<JSONArray, JSONArray>> pairs = new ArrayList<>();
        for (int i = 0; i < input.size(); i += 3) {
            JSONArray e1 = new JSONArray(input.get(i));
            JSONArray e2 = new JSONArray(input.get(i + 1));
            pairs.add(Tuple.tuple(e1, e2));
        }

        for (int i = 0; i < pairs.size(); i++) {
            Tuple2<JSONArray, JSONArray> pair = pairs.get(i);
            if (compare(pair.v1, pair.v2) < 0) {
                result += i + 1;
            }
        }

        return result;
    }

    public Object part2(List<String> input) {
        List<JSONArray> allPackets = new ArrayList<>();
        for (int i = 0; i < input.size(); i += 3) {
            allPackets.add(new JSONArray(input.get(i)));
            allPackets.add(new JSONArray(input.get(i + 1)));
        }
        JSONArray divider1 = new JSONArray("[[2]]");
        JSONArray divider2 = new JSONArray("[[6]]");
        allPackets.add(divider1);
        allPackets.add(divider2);

        List<JSONArray> sortedPackets = allPackets.stream().sorted(this::compare).collect(Collectors.toList());
        return (sortedPackets.indexOf(divider1) + 1) * (sortedPackets.indexOf(divider2) + 1);
    }

    public int compare(Object o1, Object o2) {
        if (o1 instanceof Integer && o2 instanceof Integer) {
            return Integer.compare((Integer) o1, (Integer) o2);
        } else if (o1 instanceof Integer && o2 instanceof JSONArray) {
            JSONArray tempElement = new JSONArray(List.of(o1));
            return compare(tempElement, o2);
        } else if (o1 instanceof JSONArray && o2 instanceof Integer) {
            JSONArray tempElement = new JSONArray(List.of(o2));
            return compare(o1, tempElement);
        } else if (o1 instanceof JSONArray && o2 instanceof JSONArray){
            JSONArray array1 = (JSONArray) o1;
            JSONArray array2 = (JSONArray) o2;
            for (int i = 0; i < array1.length(); i++) {
                if (array2.length() >= i + 1) {
                    int c = compare(array1.get(i), array2.get(i));
                    if (c != 0) {
                        return c;
                    }
                } else {
                    return 1;
                }
            }
            if (array2.length() > array1.length()) {
                return -1;
            }
        } else {
            throw new IllegalArgumentException();
        }
        return 0;
    }

}
