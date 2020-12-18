package de.beachboys.aoc2020;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;import java.util.List;
import java.util.stream.Stream;

public class Day18Test extends DayTest {

    private final Day day = new Day18();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("1 + (2 * 3) + (4 * (5 + 6))"), 51, null),
                Arguments.of(List.of("2 * 3 + (4 * 5)"), 26, null),
                Arguments.of(List.of("5 + (8 * 3 + 9 + 3 * 4 * 3)"), 437, null),
                Arguments.of(List.of("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))"), 12240, null),
                Arguments.of(List.of("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2"), 13632, null),
                Arguments.of(List.of("2 * 3 + 2 * 3 * 7 * 5"), 840, null),
                Arguments.of(List.of("(3 * 6 + 3 + 7 + 5) + ((2 * 3 + 2 * 3 * 7 * 5) + 6 + 6 * 9 * 7 * 8)"), 429441, null)

        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("1 + (2 * 3) + (4 * (5 + 6))"), 51, null),
                Arguments.of(List.of("2 * 3 + (4 * 5)"), 46, null),
                Arguments.of(List.of("5 + (8 * 3 + 9 + 3 * 4 * 3)"), 1445, null),
                Arguments.of(List.of("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))"), 669060, null),
                Arguments.of(List.of("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2"), 23340, null)

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