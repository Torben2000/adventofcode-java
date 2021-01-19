package de.beachboys.aoc2016;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import de.beachboys.IOHelperForTests;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;

public class Day21Test extends DayTest {

    private final Day day = new Day21();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("swap position 4 with position 0",
                        "swap letter d with letter b",
                        "reverse positions 0 through 4",
                        "rotate left 1 step",
                        "move position 1 to position 4",
                        "move position 3 to position 0",
                        "rotate based on position of letter b",
                        "rotate based on position of letter d"), "decab", new IOHelperForTests(List.of("abcde"), null))
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("swap position 4 with position 0",
                        "swap letter d with letter b",
                        "reverse positions 0 through 4",
                        "rotate left 1 step",
                        "move position 1 to position 4",
                        "move position 3 to position 0",
                        "rotate based on position of letter b",
                        "rotate based on position of letter d"), "deabc", new IOHelperForTests(List.of("decab"), null))
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