package de.beachboys;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

public class Day01Test {

    private final Day day = new Day01();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("12"), "2"),
                Arguments.of(List.of("14"), "2"),
                Arguments.of(List.of("1969"), "654"),
                Arguments.of(List.of("100756"), "33583")
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("14"), "2"),
                Arguments.of(List.of("1969"), "966"),
                Arguments.of(List.of("100756"), "50346")
        );
    }

    @ParameterizedTest
    @MethodSource("provideTestDataForPart1")
    public void testPart1(List<String> input, String expected){
        Assertions.assertEquals(expected, day.part1(input));
    }

    @ParameterizedTest
    @MethodSource("provideTestDataForPart2")
    public void testPart2(List<String> input, String expected){
        Assertions.assertEquals(expected, day.part2(input));
    }

}