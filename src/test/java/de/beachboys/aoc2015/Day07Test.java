package de.beachboys.aoc2015;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;

public class Day07Test extends DayTest {

    private final Day day = new Day07();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("123 -> a",
                        "456 -> y",
                        "a AND y -> d",
                        "a OR y -> e",
                        "a LSHIFT 2 -> f",
                        "y RSHIFT 2 -> g",
                        "NOT a -> h",
                        "NOT y -> i"), 123, null),
                Arguments.of(List.of("123 -> x",
                        "456 -> a",
                        "x AND a -> d",
                        "x OR a -> e",
                        "x LSHIFT 2 -> f",
                        "a RSHIFT 2 -> g",
                        "NOT x -> h",
                        "NOT a -> i"), 456, null),
                Arguments.of(List.of("123 -> x",
                        "456 -> y",
                        "x AND y -> a",
                        "x OR y -> e",
                        "x LSHIFT 2 -> f",
                        "y RSHIFT 2 -> g",
                        "NOT x -> h",
                        "NOT y -> i"), 72, null),
                Arguments.of(List.of("123 -> x",
                        "456 -> y",
                        "x AND y -> d",
                        "x OR y -> a",
                        "x LSHIFT 2 -> f",
                        "y RSHIFT 2 -> g",
                        "NOT x -> h",
                        "NOT y -> i"), 507, null),
                Arguments.of(List.of("123 -> x",
                        "456 -> y",
                        "x AND y -> d",
                        "x OR y -> e",
                        "x LSHIFT 2 -> a",
                        "y RSHIFT 2 -> g",
                        "NOT x -> h",
                        "NOT y -> i"), 492, null),
                Arguments.of(List.of("123 -> x",
                        "456 -> y",
                        "x AND y -> d",
                        "x OR y -> e",
                        "x LSHIFT 2 -> f",
                        "y RSHIFT 2 -> a",
                        "NOT x -> h",
                        "NOT y -> i"), 114, null),
                Arguments.of(List.of("123 -> x",
                        "456 -> y",
                        "x AND y -> d",
                        "x OR y -> e",
                        "x LSHIFT 2 -> f",
                        "y RSHIFT 2 -> g",
                        "NOT x -> a",
                        "NOT y -> i"), 65412, null),
                Arguments.of(List.of("123 -> x",
                        "456 -> y",
                        "x AND y -> d",
                        "x OR y -> e",
                        "x LSHIFT 2 -> f",
                        "y RSHIFT 2 -> g",
                        "NOT x -> h",
                        "NOT y -> a"), 65079, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("123 -> x",
                        "456 -> b",
                        "x AND y -> d",
                        "x OR y -> e",
                        "x LSHIFT 2 -> f",
                        "y RSHIFT 2 -> g",
                        "NOT x -> h",
                        "NOT b -> a"), 456, null)
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