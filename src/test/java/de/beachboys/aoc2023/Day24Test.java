package de.beachboys.aoc2023;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import de.beachboys.IOHelperForTests;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;

public class Day24Test extends DayTest {

    private final Day day = new Day24();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("19, 13, 30 @ -2,  1, -2",
                        "18, 19, 22 @ -1, -1, -2",
                        "20, 25, 34 @ -2, -2, -4",
                        "12, 31, 28 @ -1, -2, -1",
                        "20, 19, 15 @  1, -5, -3"), 2, new IOHelperForTests(List.of("7", "27"), null))
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("19, 13, 30 @ -2,  1, -2",
                        "18, 19, 22 @ -1, -1, -2",
                        "20, 25, 34 @ -2, -2, -4",
                        "12, 31, 28 @ -1, -2, -1",
                        "20, 19, 15 @  1, -5, -3"), 47, null)
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