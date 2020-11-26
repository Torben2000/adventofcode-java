package de.beachboys;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

public class Day04Test {

    private final Day day = new Day04();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("112233-112233"), 1),
                Arguments.of(List.of("223450-223450"), 0),
                Arguments.of(List.of("123789-123789"), 0)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("112233-112233"), 1),
                Arguments.of(List.of("123444-123444"), 0),
                Arguments.of(List.of("111122-111122"), 1)
        );
    }

    @ParameterizedTest
    @MethodSource("provideTestDataForPart1")
    public void testPart1(List<String> input, Object expected){
        Assertions.assertEquals(expected.toString(), day.part1(input).toString());
    }

    @ParameterizedTest
    @MethodSource("provideTestDataForPart2")
    public void testPart2(List<String> input, Object expected) {
        Assertions.assertEquals(expected.toString(), day.part2(input).toString());
    }

}