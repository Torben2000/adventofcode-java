package de.beachboys.aoc2024;

import de.beachboys.Day;
import de.beachboys.Util;
import de.beachboys.Util.SolutionForSystemOfLinearEquation;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day13 extends Day {

    public Object part1(List<String> input) {
        return runLogic(input, 0);
    }

    public Object part2(List<String> input) {
        return runLogic(input, 10000000000000L);
    }

    private static long runLogic(List<String> input, long offset) {
        long result = 0;
        List<Machine> machines = parseInput(input);

        for (Machine m : machines) {
            List<Tuple2<List<Long>, Long>> system = new ArrayList<>();
            system.add(Tuple.tuple(List.of((long) m.ax, (long) m.bx), m.px + offset));
            system.add(Tuple.tuple(List.of((long) m.ay, (long) m.by), m.py + offset));

            List<SolutionForSystemOfLinearEquation> solution = Util.solveSystemOfLinearEquations(system);
            if (solution != null
                    && solution.getFirst().longValue() >= 0 && solution.getLast().longValue() >= 0
                    && solution.getFirst().remainder() == 0 && solution.getLast().remainder() == 0) {
                result += 3 * solution.getFirst().longValue() + solution.getLast().longValue();
            }
        }
        return result;
    }

    private static List<Machine> parseInput(List<String> input) {
        List<Machine> machines = new ArrayList<>();
        Pattern buttonPattern = Pattern.compile(".*: X\\+([0-9]+), Y\\+([0-9]+)");
        Pattern prizePattern = Pattern.compile("Prize: X=([0-9]+), Y=([0-9]+)");
        for (int i = 0; i < input.size(); i+=4) {
            int ax;
            int bx;
            int ay;
            int by;
            int px;
            int py;
            String line = input.get(i);
            Matcher m = buttonPattern.matcher(line);
            if (m.matches()) {
                ax = Integer.parseInt(m.group(1));
                ay = Integer.parseInt(m.group(2));
            } else {
                throw new IllegalArgumentException();
            }

            line = input.get(i+1);
            m = buttonPattern.matcher(line);
            if (m.matches()) {
                bx = Integer.parseInt(m.group(1));
                by = Integer.parseInt(m.group(2));
            } else {
                throw new IllegalArgumentException();
            }

            line = input.get(i+2);

            m = prizePattern.matcher(line);
            if (m.matches()) {
                px = Integer.parseInt(m.group(1));
                py = Integer.parseInt(m.group(2));

                machines.add(new Machine(ax, ay, bx, by, px, py));
            } else {
                throw new IllegalArgumentException();
            }
        }
        return machines;
    }

    private record Machine(int ax, int ay, int bx, int by, int px, int py) {}

}
