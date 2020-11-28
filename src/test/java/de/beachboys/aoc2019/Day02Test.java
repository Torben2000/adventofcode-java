package de.beachboys.aoc2019;

import de.beachboys.Day;
import de.beachboys.aoc2019.Day02;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

public class Day02Test {

    private final Day day = new Day02();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("1,0,0,0,99"), "2,0,0,0,99"),
                Arguments.of(List.of("2,3,0,3,99"), "2,3,0,6,99"),
                Arguments.of(List.of("2,4,4,5,99,0"), "2,4,4,5,99,9801"),
                Arguments.of(List.of("1,1,1,4,99,5,6,0,99"), "30,1,1,4,2,5,6,0,99")
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
        );
    }

    @ParameterizedTest
    @MethodSource("provideTestDataForPart1")
    public void testPart1(List<String> input, Object expected){
        Assertions.assertEquals(expected.toString(), day.part1(input).toString());
    }

//    @ParameterizedTest
//    @MethodSource("provideTestDataForPart2")
//    public void testPart2(List<String> input, Object expected){
//        Assertions.assertEquals(expected.toString(), day.part2(input).toString());
//    }

}