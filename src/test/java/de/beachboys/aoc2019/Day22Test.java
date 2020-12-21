package de.beachboys.aoc2019;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import de.beachboys.IOHelperForTests;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

public class Day22Test extends DayTest {

    private final Day day = new Day22();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("deal with increment 7",
                        "deal into new stack",
                        "deal into new stack"), 7, new IOHelperForTests(List.of("10", "1"), null)),
                Arguments.of(List.of("deal with increment 7",
                        "deal into new stack",
                        "deal into new stack"), 5, new IOHelperForTests(List.of("10", "5"), null)),
                Arguments.of(List.of("deal with increment 7",
                        "deal into new stack",
                        "deal into new stack"), 1, new IOHelperForTests(List.of("10", "3"), null)),
                Arguments.of(List.of("cut 6",
                        "deal with increment 7",
                        "deal into new stack"), 8, new IOHelperForTests(List.of("10", "9"), null)),
                Arguments.of(List.of("deal with increment 7",
                        "deal with increment 9",
                        "cut -2"), 3, new IOHelperForTests(List.of("10", "7"), null)),
                Arguments.of(List.of("deal into new stack",
                        "cut -2",
                        "deal with increment 7",
                        "cut 8",
                        "cut -4",
                        "deal with increment 7",
                        "cut 3",
                        "deal with increment 9",
                        "deal with increment 3",
                        "cut -1"), 8, new IOHelperForTests(List.of("10", "3"), null)),
                Arguments.of(List.of("deal into new stack",
                        "cut -2",
                        "deal with increment 7",
                        "cut 8",
                        "cut -4",
                        "deal with increment 7",
                        "cut 3",
                        "deal with increment 9",
                        "deal with increment 3",
                        "cut -1"), 0, new IOHelperForTests(List.of("10", "9"), null))
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("inputLines"), 2, null)

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