package de.beachboys.aoc2023;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;

public class Day01Test extends DayTest {

    private final Day day = new Day01();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("1abc2",
                        "pqr3stu8vwx",
                        "a1b2c3d4e5f",
                        "treb7uchet"), 142, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("1abc2",
                        "pqr3stu8vwx",
                        "a1b2c3d4e5f",
                        "treb7uchet"), 142, null),
                Arguments.of(List.of("two1nine",
                        "eightwothree",
                        "abcone2threexyz",
                        "xtwone3four",
                        "4nineeightseven2",
                        "zoneight234",
                        "7pqrstsixteen"), 281, null)

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