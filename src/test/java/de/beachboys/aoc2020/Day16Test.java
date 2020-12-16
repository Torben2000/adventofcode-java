package de.beachboys.aoc2020;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;import java.util.List;
import java.util.stream.Stream;

public class Day16Test extends DayTest {

    private final Day day = new Day16();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("class: 1-3 or 5-7",
                        "row: 6-11 or 33-44",
                        "seat: 13-40 or 45-50",
                        "",
                        "your ticket:",
                        "7,1,14",
                        "",
                        "nearby tickets:",
                        "7,3,47",
                        "40,4,50",
                        "55,2,20",
                        "38,6,12"), 71, null)

        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("departure class: 0-1 or 4-19",
                        "row: 0-5 or 8-19",
                        "departure seat: 0-13 or 16-19",
                        "",
                        "your ticket:",
                        "11,12,13",
                        "",
                        "nearby tickets:",
                        "3,9,18",
                        "15,1,5",
                        "5,14,9"), 156, null),
                Arguments.of(List.of("departure class: 0-1 or 4-19",
                        "row: 0-5 or 8-19",
                        "seat: 0-13 or 16-19",
                        "",
                        "your ticket:",
                        "11,12,13",
                        "",
                        "nearby tickets:",
                        "3,9,18",
                        "15,1,5",
                        "5,14,9"), 12, null)

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