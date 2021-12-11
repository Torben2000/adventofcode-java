package de.beachboys.aoc2021;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;

public class Day11Test extends DayTest {

    private final Day day = new Day11();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("5483143223",
                        "2745854711",
                        "5264556173",
                        "6141336146",
                        "6357385478",
                        "4167524645",
                        "2176841721",
                        "6882881134",
                        "4846848554",
                        "5283751526"), 1656, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("5483143223",
                        "2745854711",
                        "5264556173",
                        "6141336146",
                        "6357385478",
                        "4167524645",
                        "2176841721",
                        "6882881134",
                        "4846848554",
                        "5283751526"), 195, null)
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