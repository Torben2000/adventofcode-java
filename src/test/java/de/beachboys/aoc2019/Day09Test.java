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

public class Day09Test extends DayTest {

    private final Day day = new Day09();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99"), 99, new IOHelperForTests(List.of(), List.of(109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99))),
                Arguments.of(List.of("1102,34915192,34915192,7,4,7,99,0"), 1219070632396864L, null),
                Arguments.of(List.of("104,1125899906842624,99"), 1125899906842624L, null),
                Arguments.of(List.of("109,19,203,-13,204,-13,0"), 99, new IOHelperForTests(List.of("99"), List.of(99)))

        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("inputlines"), 2, null)

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