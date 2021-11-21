package de.beachboys.aoc2018;

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
                Arguments.of(List.of("x=495, y=2..7",
                        "y=7, x=495..501",
                        "x=501, y=3..7",
                        "x=498, y=2..4",
                        "x=506, y=1..2",
                        "x=498, y=10..13",
                        "x=504, y=10..13",
                        "y=13, x=498..504"), 57, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("x=495, y=2..7",
                        "y=7, x=495..501",
                        "x=501, y=3..7",
                        "x=498, y=2..4",
                        "x=506, y=1..2",
                        "x=498, y=10..13",
                        "x=504, y=10..13",
                        "y=13, x=498..504"), 29, null)
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