package de.beachboys.aoc2023;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;

public class Day17Test extends DayTest {

    private final Day day = new Day17();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("2413432311323",
                        "3215453535623",
                        "3255245654254",
                        "3446585845452",
                        "4546657867536",
                        "1438598798454",
                        "4457876987766",
                        "3637877979653",
                        "4654967986887",
                        "4564679986453",
                        "1224686865563",
                        "2546548887735",
                        "4322674655533"), 102, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("2413432311323",
                        "3215453535623",
                        "3255245654254",
                        "3446585845452",
                        "4546657867536",
                        "1438598798454",
                        "4457876987766",
                        "3637877979653",
                        "4654967986887",
                        "4564679986453",
                        "1224686865563",
                        "2546548887735",
                        "4322674655533"), 94, null),
                Arguments.of(List.of("111111111111",
                                "999999999991",
                                "999999999991",
                                "999999999991",
                                "999999999991"), 71, null)
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