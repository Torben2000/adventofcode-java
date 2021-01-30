package de.beachboys.aoc2017;

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
                Arguments.of(List.of("{}"), 1, null),
                Arguments.of(List.of("{{{}}}"), 6, null),
                Arguments.of(List.of("{{},{}}"), 5, null),
                Arguments.of(List.of("{{{},{},{{}}}}"), 16, null),
                Arguments.of(List.of("{<a>,<a>,<a>,<a>}"), 1, null),
                Arguments.of(List.of("{{<ab>},{<ab>},{<ab>},{<ab>}}"), 9, null),
                Arguments.of(List.of("{{<!!>},{<!!>},{<!!>},{<!!>}}"), 9, null),
                Arguments.of(List.of("{{<a!>},{<a!>},{<a!>},{<ab>}}"), 3, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("<>"), 0, null),
                Arguments.of(List.of("<random characters>"), 17, null),
                Arguments.of(List.of("<<<<>"), 3, null),
                Arguments.of(List.of("<{!>}>"), 2, null),
                Arguments.of(List.of("<!!>"), 0, null),
                Arguments.of(List.of("<!!!>>"), 0, null),
                Arguments.of(List.of("<{o\"i!a,<{i<a>"), 10, null)
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