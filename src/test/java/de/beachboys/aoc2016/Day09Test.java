package de.beachboys.aoc2016;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;

public class Day09Test extends DayTest {

    private final Day day = new Day09();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("ADVENT"), 6, null),
                Arguments.of(List.of("A(1x5)BC"), 7, null),
                Arguments.of(List.of("(3x3)XYZ"), 9, null),
                Arguments.of(List.of("A(2x2)BCD(2x2)EFG"), 11, null),
                Arguments.of(List.of("(6x1)(1x3)A"), 6, null),
                Arguments.of(List.of("X(8x2)(3x3)ABCY"), 18, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("ADVENT"), 6, null),
                Arguments.of(List.of("(3x3)XYZ"), 9, null),
                Arguments.of(List.of("X(8x2)(3x3)ABCY"), 20, null),
                Arguments.of(List.of("(27x12)(20x12)(13x14)(7x10)(1x12)A"), 241920, null),
                Arguments.of(List.of("(25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN"), 445, null)

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