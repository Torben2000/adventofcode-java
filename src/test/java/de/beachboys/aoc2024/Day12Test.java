package de.beachboys.aoc2024;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;

public class Day12Test extends DayTest {

    private final Day day = new Day12();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("AAAA",
                        "BBCD",
                        "BBCC",
                        "EEEC"), 140, null),
                Arguments.of(List.of("OOOOO",
                        "OXOXO",
                        "OOOOO",
                        "OXOXO",
                        "OOOOO"), 772, null),
                Arguments.of(List.of("RRRRIICCFF",
                        "RRRRIICCCF",
                        "VVRRRCCFFF",
                        "VVRCCCJFFF",
                        "VVVVCJJCFE",
                        "VVIVCCJJEE",
                        "VVIIICJJEE",
                        "MIIIIIJJEE",
                        "MIIISIJEEE",
                        "MMMISSJEEE"), 1930, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("AAAA",
                        "BBCD",
                        "BBCC",
                        "EEEC"), 80, null),
                Arguments.of(List.of("OOOOO",
                        "OXOXO",
                        "OOOOO",
                        "OXOXO",
                        "OOOOO"), 436, null),
                Arguments.of(List.of("EEEEE",
                        "EXXXX",
                        "EEEEE",
                        "EXXXX",
                        "EEEEE"), 236, null),
                Arguments.of(List.of("AAAAAA",
                        "AAABBA",
                        "AAABBA",
                        "ABBAAA",
                        "ABBAAA",
                        "AAAAAA"), 368, null),
                Arguments.of(List.of("RRRRIICCFF",
                        "RRRRIICCCF",
                        "VVRRRCCFFF",
                        "VVRCCCJFFF",
                        "VVVVCJJCFE",
                        "VVIVCCJJEE",
                        "VVIIICJJEE",
                        "MIIIIIJJEE",
                        "MIIISIJEEE",
                        "MMMISSJEEE"), 1206, null)
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