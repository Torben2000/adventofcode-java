package de.beachboys.aoc2017;

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
                Arguments.of(List.of("aa bb cc dd ee"), 1, null),
                Arguments.of(List.of("aa bb cc dd aa"), 0, null),
                Arguments.of(List.of("aa bb cc dd aaa"), 1, null),
                Arguments.of(List.of("aa bb cc dd ee",
                        "aa bb cc dd aaa"), 2, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("abcde fghij"), 1, null),
                Arguments.of(List.of("abcde xyz ecdab"), 0, null),
                Arguments.of(List.of("a ab abc abd abf abj"), 1, null),
                Arguments.of(List.of("iiii oiii ooii oooi oooo"), 1, null),
                Arguments.of(List.of("oiii ioii iioi iiio"), 0, null),
                Arguments.of(List.of("abcde fghij",
                        "iiii oiii ooii oooi oooo"), 2, null)
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