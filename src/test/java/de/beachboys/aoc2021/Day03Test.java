package de.beachboys.aoc2021;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;

public class Day03Test extends DayTest {

    private final Day day = new Day03();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("00100",
                        "11110",
                        "10110",
                        "10111",
                        "10101",
                        "01111",
                        "00111",
                        "11100",
                        "10000",
                        "11001",
                        "00010",
                        "01010"), 198, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("00100",
                        "11110",
                        "10110",
                        "10111",
                        "10101",
                        "01111",
                        "00111",
                        "11100",
                        "10000",
                        "11001",
                        "00010",
                        "01010"), 230, null)
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