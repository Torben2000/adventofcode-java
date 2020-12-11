package de.beachboys.aoc2020;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;import java.util.List;
import java.util.stream.Stream;

public class Day11Test extends DayTest {

    private final Day day = new Day11();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("L.LL.LL.LL",
                        "LLLLLLL.LL",
                        "L.L.L..L..",
                        "LLLL.LL.LL",
                        "L.LL.LL.LL",
                        "L.LLLLL.LL",
                        "..L.L.....",
                        "LLLLLLLLLL",
                        "L.LLLLLL.L",
                        "L.LLLLL.LL"), 37, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("L.LL.LL.LL",
                        "LLLLLLL.LL",
                        "L.L.L..L..",
                        "LLLL.LL.LL",
                        "L.LL.LL.LL",
                        "L.LLLLL.LL",
                        "..L.L.....",
                        "LLLLLLLLLL",
                        "L.LLLLLL.L",
                        "L.LLLLL.LL"), 26, null)
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