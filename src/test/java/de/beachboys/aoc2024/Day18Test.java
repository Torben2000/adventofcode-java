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

public class Day18Test extends DayTest {

    private final Day day = new Day18();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("5,4",
                        "4,2",
                        "4,5",
                        "3,0",
                        "2,1",
                        "6,3",
                        "2,4",
                        "1,5",
                        "0,6",
                        "3,3",
                        "2,6",
                        "5,1",
                        "1,2",
                        "5,5",
                        "2,5",
                        "6,5",
                        "1,4",
                        "0,4",
                        "6,4",
                        "1,1",
                        "6,1",
                        "1,0",
                        "0,5",
                        "1,6",
                        "2,0"), 22, new IOHelperForTests(List.of("12", "6"), null))
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("5,4",
                        "4,2",
                        "4,5",
                        "3,0",
                        "2,1",
                        "6,3",
                        "2,4",
                        "1,5",
                        "0,6",
                        "3,3",
                        "2,6",
                        "5,1",
                        "1,2",
                        "5,5",
                        "2,5",
                        "6,5",
                        "1,4",
                        "0,4",
                        "6,4",
                        "1,1",
                        "6,1",
                        "1,0",
                        "0,5",
                        "1,6",
                        "2,0"), "6,1", new IOHelperForTests(List.of("12", "6"), null))
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