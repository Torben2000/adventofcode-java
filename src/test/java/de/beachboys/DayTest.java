package de.beachboys;

import org.junit.jupiter.api.Assertions;

import java.util.List;

public abstract class DayTest {

    protected void testPart1(Day day, List<String> input, Object expected, IOHelper io) {
        if (io != null) {
            day.io = io;
        }
        String result = day.part1(input).toString();
        if (expected != null) {
            Assertions.assertEquals(expected.toString(), result);
        }
    }

    protected void testPart2(Day day, List<String> input, Object expected, IOHelper io) {
        if (io != null) {
            day.io = io;
        }
        String result = day.part2(input).toString();
        if (expected != null) {
            Assertions.assertEquals(expected.toString(), result);
        }
    }

}
