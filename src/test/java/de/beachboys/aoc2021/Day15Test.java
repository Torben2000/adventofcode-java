package de.beachboys.aoc2021;

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
                Arguments.of(List.of("1163751742",
                        "1381373672",
                        "2136511328",
                        "3694931569",
                        "7463417111",
                        "1319128137",
                        "1359912421",
                        "3125421639",
                        "1293138521",
                        "2311944581"), 40, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("1163751742",
                        "1381373672",
                        "2136511328",
                        "3694931569",
                        "7463417111",
                        "1319128137",
                        "1359912421",
                        "3125421639",
                        "1293138521",
                        "2311944581"), 315, null)
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