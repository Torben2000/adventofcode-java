package de.beachboys;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

public class Day03Test {

    private final Day day = new Day03();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("R75,D30,R83,U83,L12,D49,R71,U7,L72",
                        "U62,R66,U55,R34,D71,R55,D58,R83"), "159"),
                Arguments.of(List.of("R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51",
                        "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7"), "135")
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("R75,D30,R83,U83,L12,D49,R71,U7,L72",
                        "U62,R66,U55,R34,D71,R55,D58,R83"), "610"),
                Arguments.of(List.of("R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51",
                        "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7"), "410")
        );
    }

    @ParameterizedTest
    @MethodSource("provideTestDataForPart1")
    public void testPart1(List<String> input, String expected){
        Assertions.assertEquals(expected, day.part1(input));
    }

    @ParameterizedTest
    @MethodSource("provideTestDataForPart2")
    public void testPart2(List<String> input, String expected) {
        Assertions.assertEquals(expected, day.part2(input));
    }

}