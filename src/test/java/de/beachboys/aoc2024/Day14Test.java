package de.beachboys.aoc2024;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import de.beachboys.IOHelperForTests;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;

public class Day14Test extends DayTest {

    private final Day day = new Day14();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("p=0,4 v=3,-3",
                        "p=6,3 v=-1,-3",
                        "p=10,3 v=-1,2",
                        "p=2,0 v=2,-1",
                        "p=0,0 v=1,3",
                        "p=3,0 v=-2,-2",
                        "p=7,6 v=-1,-3",
                        "p=3,0 v=-1,-2",
                        "p=9,3 v=2,3",
                        "p=7,3 v=-1,2",
                        "p=2,4 v=2,-3",
                        "p=9,5 v=-3,-3"), 12, new IOHelperForTests(List.of("7", "11"), null))
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("p=0,4 v=3,-3",
                        "p=6,3 v=-1,-3",
                        "p=10,3 v=-1,2",
                        "p=2,0 v=2,-1",
                        "p=0,0 v=1,3",
                        "p=3,0 v=-2,-2",
                        "p=7,6 v=-1,-3",
                        "p=3,0 v=-1,-2",
                        "p=9,3 v=2,3",
                        "p=7,3 v=-1,2",
                        "p=2,4 v=2,-3",
                        "p=9,5 v=-3,-3"), "Cycle found before image frame was found, probably not real input data", null)
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