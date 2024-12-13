package de.beachboys.aoc2024;

import de.beachboys.Day;

import java.math.BigDecimal;
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
            long a1 = m.ax;
            long a2 = m.ay;
            long b1 = m.bx;
            long b2 = m.by;
            long c1 = m.px + offset;
            long c2 = m.py + offset;

            long det = a1 * b2 - a2 * b1;
            if (det == 0) {
                continue;
            }
            BigDecimal[] x1 = BigDecimal.valueOf(c1 * b2 - c2 * b1).divideAndRemainder(BigDecimal.valueOf(det));
            BigDecimal[] x2 = BigDecimal.valueOf(a1 * c2 - a2 * c1).divideAndRemainder(BigDecimal.valueOf(det));

            if (x1[0].longValue() >= 0 && x2[0].longValue() >= 0 && x1[1].longValue() == 0 && x2[1].longValue() == 0) {
                result += 3 * x1[0].longValue() + x2[0].longValue();
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
