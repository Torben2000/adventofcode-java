package de.beachboys.aoc2018;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;

public class Day15Test extends DayTest {

    private final Day day = new Day15();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("#######",
                        "#.G...#",
                        "#...EG#",
                        "#.#.#G#",
                        "#..G#E#",
                        "#.....#" +
                        "#######"), 27730, null),
                Arguments.of(List.of("#######",
                        "#G..#E#",
                        "#E#E.E#",
                        "#G.##.#",
                        "#...#E#",
                        "#...E.#",
                        "#######"), 36334, null),
                Arguments.of(List.of("#######",
                        "#E..EG#",
                        "#.#G.E#",
                        "#E.##E#",
                        "#G..#.#",
                        "#..E#.#",
                        "#######"), 39514, null),
                Arguments.of(List.of("#######",
                        "#E.G#.#)",
                        "#.#G..#",
                        "#G.#.G#",
                        "#G..#.#",
                        "#...E.#",
                        "#######"), 27755, null),
                Arguments.of(List.of("#######",
                        "#.E...#",
                        "#.#..G#",
                        "#.###.#",
                        "#E#G#G#",
                        "#...#G#",
                        "#######"), 28944, null),
                Arguments.of(List.of("#########",
                        "#G......#",
                        "#.E.#...#",
                        "#..##..G#",
                        "#...##..#",
                        "#...#...#",
                        "#.G...G.#",
                        "#.....G.#",
                        "#########"), 18740, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("#######",
                        "#.G...#",
                        "#...EG#",
                        "#.#.#G#",
                        "#..G#E#",
                        "#.....#" +
                                "#######"), 4988, null),
                Arguments.of(List.of("#######",
                        "#E..EG#",
                        "#.#G.E#",
                        "#E.##E#",
                        "#G..#.#",
                        "#..E#.#",
                        "#######"), 31284, null),
                Arguments.of(List.of("#######",
                        "#E.G#.#)",
                        "#.#G..#",
                        "#G.#.G#",
                        "#G..#.#",
                        "#...E.#",
                        "#######"), 3478, null),
                Arguments.of(List.of("#######",
                        "#.E...#",
                        "#.#..G#",
                        "#.###.#",
                        "#E#G#G#",
                        "#...#G#",
                        "#######"), 6474, null),
                Arguments.of(List.of("#########",
                        "#G......#",
                        "#.E.#...#",
                        "#..##..G#",
                        "#...##..#",
                        "#...#...#",
                        "#.G...G.#",
                        "#.....G.#",
                        "#########"), 1140, null)
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