package de.beachboys.aoc2024;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;

public class Day04Test extends DayTest {

    private final Day day = new Day04();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("MMMSXXMASM",
                        "MSAMXMSMSA",
                        "AMXSXMAAMM",
                        "MSAMASMSMX",
                        "XMASAMXAMM",
                        "XXAMMXXAMA",
                        "SMSMSASXSS",
                        "SAXAMASAAA",
                        "MAMMMXMMMM",
                        "MXMXAXMASX"), "18", null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("MMMSXXMASM",
                        "MSAMXMSMSA",
                        "AMXSXMAAMM",
                        "MSAMASMSMX",
                        "XMASAMXAMM",
                        "XXAMMXXAMA",
                        "SMSMSASXSS",
                        "SAXAMASAAA",
                        "MAMMMXMMMM",
                        "MXMXAXMASX"), "9", null)
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