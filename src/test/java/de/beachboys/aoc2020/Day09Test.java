package de.beachboys.aoc2020;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import de.beachboys.IOHelperForTests;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;import java.util.List;
import java.util.stream.Stream;

public class Day09Test extends DayTest {

    private final Day day = new Day09();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("35",
                        "20",
                        "15",
                        "25",
                        "47",
                        "40",
                        "62",
                        "55",
                        "65",
                        "95",
                        "102",
                        "117",
                        "150",
                        "182",
                        "127",
                        "219",
                        "299",
                        "277",
                        "309",
                        "576"), 127, new IOHelperForTests(List.of("5"), null))

        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("35",
                        "20",
                        "15",
                        "25",
                        "47",
                        "40",
                        "62",
                        "55",
                        "65",
                        "95",
                        "102",
                        "117",
                        "150",
                        "182",
                        "127",
                        "219",
                        "299",
                        "277",
                        "309",
                        "576"), 62, new IOHelperForTests(List.of("5"), null))

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