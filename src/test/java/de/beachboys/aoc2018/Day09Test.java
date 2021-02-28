package de.beachboys.aoc2018;

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
                Arguments.of(List.of("9 players; last marble is worth 25 points"), 32, null),
                Arguments.of(List.of("10 players; last marble is worth 1618 points"), 8317, null),
                Arguments.of(List.of("13 players; last marble is worth 7999 points"), 146373, null),
                Arguments.of(List.of("17 players; last marble is worth 1104 points"), 2764, null),
                Arguments.of(List.of("21 players; last marble is worth 6111 points"), 54718, null),
                Arguments.of(List.of("30 players; last marble is worth 5807 points"), 37305, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("9 players; last marble is worth 25 points"), 22563, null),
                Arguments.of(List.of("10 players; last marble is worth 1618 points"), 74765078, null),
                Arguments.of(List.of("13 players; last marble is worth 7999 points"), 1406506154, null),
                Arguments.of(List.of("17 players; last marble is worth 1104 points"), 20548882, null),
                Arguments.of(List.of("21 players; last marble is worth 6111 points"), 507583214, null),
                Arguments.of(List.of("30 players; last marble is worth 5807 points"), 320997431, null)
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