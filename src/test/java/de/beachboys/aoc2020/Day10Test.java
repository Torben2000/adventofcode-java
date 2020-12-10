package de.beachboys.aoc2020;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;import java.util.List;
import java.util.stream.Stream;

public class Day10Test extends DayTest {

    private final Day day = new Day10();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("16",
                        "10",
                        "15",
                        "5",
                        "1",
                        "11",
                        "7",
                        "19",
                        "6",
                        "12",
                        "4"), 35, null),
                Arguments.of(List.of("28",
                        "33",
                        "18",
                        "42",
                        "31",
                        "14",
                        "46",
                        "20",
                        "48",
                        "47",
                        "24",
                        "23",
                        "49",
                        "45",
                        "19",
                        "38",
                        "39",
                        "11",
                        "1",
                        "32",
                        "25",
                        "35",
                        "8",
                        "17",
                        "7",
                        "9",
                        "4",
                        "2",
                        "34",
                        "10",
                        "3"), 220, null)

        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("16",
                        "10",
                        "15",
                        "5",
                        "1",
                        "11",
                        "7",
                        "19",
                        "6",
                        "12",
                        "4"), 8, null),
                Arguments.of(List.of("28",
                        "33",
                        "18",
                        "42",
                        "31",
                        "14",
                        "46",
                        "20",
                        "48",
                        "47",
                        "24",
                        "23",
                        "49",
                        "45",
                        "19",
                        "38",
                        "39",
                        "11",
                        "1",
                        "32",
                        "25",
                        "35",
                        "8",
                        "17",
                        "7",
                        "9",
                        "4",
                        "2",
                        "34",
                        "10",
                        "3"), 19208, null)
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