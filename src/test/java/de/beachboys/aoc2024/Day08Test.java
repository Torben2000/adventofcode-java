package de.beachboys.aoc2024;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;

public class Day08Test extends DayTest {

    private final Day day = new Day08();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("............",
                        "........0...",
                        ".....0......",
                        ".......0....",
                        "....0.......",
                        "......A.....",
                        "............",
                        "............",
                        "........A...",
                        ".........A..",
                        "............",
                        "............"), 14, null),
                Arguments.of(List.of("..........",
                        "..........",
                        "..........",
                        "....a.....",
                        "..........",
                        ".....a....",
                        "..........",
                        "..........",
                        "..........",
                        ".........."), 2, null),
                Arguments.of(List.of("..........",
                        "..........",
                        "..........",
                        "....a.....",
                        "........a.",
                        ".....a....",
                        "..........",
                        "..........",
                        "..........",
                        ".........."), 4, null),
                Arguments.of(List.of("..........",
                        "..........",
                        "..........",
                        "....a.....",
                        "........a.",
                        ".....a....",
                        "..........",
                        "......A...",
                        "..........",
                        ".........."), 4, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("............",
                        "........0...",
                        ".....0......",
                        ".......0....",
                        "....0.......",
                        "......A.....",
                        "............",
                        "............",
                        "........A...",
                        ".........A..",
                        "............",
                        "............"), 34, null),
                Arguments.of(List.of("T.........",
                        "...T......",
                        ".T........",
                        "..........",
                        "..........",
                        "..........",
                        "..........",
                        "..........",
                        "..........",
                        ".........."), 9, null)

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