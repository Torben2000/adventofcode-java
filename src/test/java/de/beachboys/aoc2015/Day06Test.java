package de.beachboys.aoc2015;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;

public class Day06Test extends DayTest {

    private final Day day = new Day06();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("turn on 0,0 through 999,999"), 1000000, null),
                Arguments.of(List.of("toggle 0,0 through 999,0"), 1000, null),
                Arguments.of(List.of("turn on 0,0 through 999,999", "toggle 0,0 through 999,0"), 999000, null),
                Arguments.of(List.of("turn on 0,0 through 999,999", "turn off 499,499 through 500,500"), 999996, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("turn on 0,0 through 999,999"), 1000000, null),
                Arguments.of(List.of("toggle 0,0 through 999,0"), 2000, null),
                Arguments.of(List.of("turn on 0,0 through 999,999", "toggle 0,0 through 999,0"), 1002000, null),
                Arguments.of(List.of("turn on 0,0 through 999,999", "turn off 499,499 through 500,500"), 999996, null)
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