package de.beachboys.aoc2023;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import de.beachboys.IOHelperForTests;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;

public class Day21Test extends DayTest {

    private final Day day = new Day21();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("...........",
                        ".....###.#.",
                        ".###.##..#.",
                        "..#.#...#..",
                        "....#.#....",
                        ".##..S####.",
                        ".##..#...#.",
                        ".......##..",
                        ".##.#.####.",
                        ".##..##.##.",
                        "..........."), 16, new IOHelperForTests(List.of("6"), null))


        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("...........",
                        ".....###.#.",
                        ".###.##..#.",
                        "..#.#...#..",
                        "....#.#....",
                        ".##..S####.",
                        ".##..#...#.",
                        ".......##..",
                        ".##.#.####.",
                        ".##..##.##.",
                        "..........."), 16, new IOHelperForTests(List.of("6"), null)),
                Arguments.of(List.of("...........",
                        ".....###.#.",
                        ".###.##..#.",
                        "..#.#...#..",
                        "....#.#....",
                        ".##..S####.",
                        ".##..#...#.",
                        ".......##..",
                        ".##.#.####.",
                        ".##..##.##.",
                        "..........."), 50, new IOHelperForTests(List.of("10"), null)),
                Arguments.of(List.of("...........",
                        ".....###.#.",
                        ".###.##..#.",
                        "..#.#...#..",
                        "....#.#....",
                        ".##..S####.",
                        ".##..#...#.",
                        ".......##..",
                        ".##.#.####.",
                        ".##..##.##.",
                        "..........."), 1594, new IOHelperForTests(List.of("50"), null)),
                Arguments.of(List.of("...........",
                        ".....###.#.",
                        ".###.##..#.",
                        "..#.#...#..",
                        "....#.#....",
                        ".##..S####.",
                        ".##..#...#.",
                        ".......##..",
                        ".##.#.####.",
                        ".##..##.##.",
                        "..........."), 6536, new IOHelperForTests(List.of("100"), null)),
                Arguments.of(List.of("...........",
                        ".....###.#.",
                        ".###.##..#.",
                        "..#.#...#..",
                        "....#.#....",
                        ".##..S####.",
                        ".##..#...#.",
                        ".......##..",
                        ".##.#.####.",
                        ".##..##.##.",
                        "..........."), 167004, new IOHelperForTests(List.of("500"), null)),
                Arguments.of(List.of("...........",
                        ".....###.#.",
                        ".###.##..#.",
                        "..#.#...#..",
                        "....#.#....",
                        ".##..S####.",
                        ".##..#...#.",
                        ".......##..",
                        ".##.#.####.",
                        ".##..##.##.",
                        "..........."), 668697, new IOHelperForTests(List.of("1000"), null)),
                Arguments.of(List.of("...........",
                        ".....###.#.",
                        ".###.##..#.",
                        "..#.#...#..",
                        "....#.#....",
                        ".##..S####.",
                        ".##..#...#.",
                        ".......##..",
                        ".##.#.####.",
                        ".##..##.##.",
                        "..........."), 16733044, new IOHelperForTests(List.of("5000"), null))
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