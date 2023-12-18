package de.beachboys.aoc2023;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;

public class Day18Test extends DayTest {

    private final Day day = new Day18();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("R 6 (#70c710)",
                        "D 5 (#0dc571)",
                        "L 2 (#5713f0)",
                        "D 2 (#d2c081)",
                        "R 2 (#59c680)",
                        "D 2 (#411b91)",
                        "L 5 (#8ceee2)",
                        "U 2 (#caa173)",
                        "L 1 (#1b58a2)",
                        "U 2 (#caa171)",
                        "R 2 (#7807d2)",
                        "U 3 (#a77fa3)",
                        "L 2 (#015232)",
                        "U 2 (#7a21e3)"), 62, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("R 6 (#70c710)",
                        "D 5 (#0dc571)",
                        "L 2 (#5713f0)",
                        "D 2 (#d2c081)",
                        "R 2 (#59c680)",
                        "D 2 (#411b91)",
                        "L 5 (#8ceee2)",
                        "U 2 (#caa173)",
                        "L 1 (#1b58a2)",
                        "U 2 (#caa171)",
                        "R 2 (#7807d2)",
                        "U 3 (#a77fa3)",
                        "L 2 (#015232)",
                        "U 2 (#7a21e3)"), 952408144115L, null)
        );
    }

    @ParameterizedTest
    @MethodSource("provideTestDataForPart1")
    public void testPart1(List<String> input, Object expected, IOHelper io) {
        testPart1(this.day, input, expected, io);
    }

    @ParameterizedTest
    @MethodSource("provideTestDataForPart2")
    public void testPart2(List<String> input, Object expected, IOHelper io) {
        testPart2(this.day, input, expected, io);
    }

}