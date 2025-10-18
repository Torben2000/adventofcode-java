package de.beachboys.ec2024;

import de.beachboys.Quest;

import java.util.Arrays;
import java.util.List;

public class Quest04 extends Quest {

    @Override
    public Object part1(List<String> input) {
        int[] nails = input.stream().mapToInt(Integer::valueOf).toArray();

        int minHeight = Arrays.stream(nails).min().orElseThrow();

        return Arrays.stream(nails).map(nail -> nail - minHeight).sum();
     }

    @Override
    public Object part2(List<String> input) {
         return part1(input);
    }

    @Override
    public Object part3(List<String> input) {
        long result = Long.MAX_VALUE;
        int[] nails = input.stream().mapToInt(Integer::valueOf).toArray();

        int minHeight = Arrays.stream(nails).min().orElseThrow();
        int maxHeight = Arrays.stream(nails).max().orElseThrow();

        for (int height = minHeight; height <= maxHeight; height++) {
            int currentHeight = height;
            result = Math.min(result, Arrays.stream(nails).map(nail -> Math.abs(nail - currentHeight)).sum());
        }

        return result;
    }
}
