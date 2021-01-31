package de.beachboys.aoc2017;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import de.beachboys.IOHelperForTests;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;

public class Day10Test extends DayTest {

    private final Day day = new Day10();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("3,4,1,5"), 12, new IOHelperForTests(List.of("5"), null))
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of(""), "a2582a3a0e66e6e86e3812dcb672a272", null),
                Arguments.of(List.of("AoC 2017"), "33efeb34ea91902bb2f59c9920caa6cd", null),
                Arguments.of(List.of("1,2,3"), "3efbe78a8d82f29979031a4aa0b16a9d", null),
                Arguments.of(List.of("1,2,4"), "63960835bcdc130f0b66d7ff4f6a5a8e", null)
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