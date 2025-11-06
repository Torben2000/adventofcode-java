package de.beachboys.ec2025;

import de.beachboys.Quest;

import java.util.List;
import java.util.regex.Pattern;

public class Quest04 extends Quest {

    @Override
    public Object part1(List<String> input) {
        double firstGear = Double.parseDouble(input.getFirst());
        double lastGear = Double.parseDouble(input.getLast());

        return (long) Math.floor(2025 * firstGear / lastGear);
    }

    @Override
    public Object part2(List<String> input) {
        double firstGear = Double.parseDouble(input.getFirst());
        double lastGear = Double.parseDouble(input.getLast());

        return (long) Math.ceil(10000000000000L * lastGear / firstGear);
    }

    @Override
    public Object part3(List<String> input) {
        double firstGear = Double.parseDouble(input.getFirst());
        double lastGear = Double.parseDouble(input.getLast());

        double factor = 1.0;
        for (int i = 1; i < input.size() - 1; i++) {
            String[] split = input.get(i).split(Pattern.quote("|"));
            double leftConnection = Double.parseDouble(split[0]);
            double rightConnection = Double.parseDouble(split[1]);

            factor *= rightConnection;
            factor /= leftConnection;
        }
        return (long) Math.floor(100 * factor * firstGear / lastGear);
    }
}
