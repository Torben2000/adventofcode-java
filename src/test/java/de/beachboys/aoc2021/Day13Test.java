package de.beachboys.aoc2021;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;

public class Day13Test extends DayTest {

    private final Day day = new Day13();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("6,10",
                        "0,14",
                        "9,10",
                        "0,3",
                        "10,4",
                        "4,11",
                        "6,0",
                        "6,12",
                        "4,1",
                        "0,13",
                        "10,12",
                        "3,4",
                        "3,0",
                        "8,4",
                        "1,10",
                        "2,14",
                        "8,10",
                        "9,0",
                        "",
                        "fold along y=7",
                        "fold along x=5"), 17, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("6,10",
                        "0,14",
                        "9,10",
                        "0,3",
                        "10,4",
                        "4,11",
                        "6,0",
                        "6,12",
                        "4,1",
                        "0,13",
                        "10,12",
                        "3,4",
                        "3,0",
                        "8,4",
                        "1,10",
                        "2,14",
                        "8,10",
                        "9,0",
                        "",
                        "fold along y=7",
                        "fold along x=5"),
                        "*****\n" +
                        "*   *\n" +
                        "*   *\n" +
                        "*   *\n" +
                        "*****\n", null)
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