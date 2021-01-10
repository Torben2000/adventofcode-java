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

public class Day10Test extends DayTest {

    private final Day day = new Day10();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("value 5 goes to bot 2",
                        "bot 2 gives low to bot 1 and high to bot 0",
                        "value 3 goes to bot 1",
                        "bot 1 gives low to output 1 and high to bot 0",
                        "bot 0 gives low to output 2 and high to output 0",
                        "value 2 goes to bot 2"), 2, new IOHelperForTests(List.of("2", "5"), null)),
                Arguments.of(List.of("value 5 goes to bot 2",
                        "bot 2 gives low to bot 1 and high to bot 0",
                        "value 3 goes to bot 1",
                        "bot 1 gives low to output 1 and high to bot 0",
                        "bot 0 gives low to output 2 and high to output 0",
                        "value 2 goes to bot 2"), 1, new IOHelperForTests(List.of("2", "3"), null)),
                Arguments.of(List.of("value 5 goes to bot 2",
                        "bot 2 gives low to bot 1 and high to bot 0",
                        "value 3 goes to bot 1",
                        "bot 1 gives low to output 1 and high to bot 0",
                        "bot 0 gives low to output 2 and high to output 0",
                        "value 2 goes to bot 2"), 0, new IOHelperForTests(List.of("3", "5"), null))

        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("value 5 goes to bot 2",
                        "bot 2 gives low to bot 1 and high to bot 0",
                        "value 3 goes to bot 1",
                        "bot 1 gives low to output 1 and high to bot 0",
                        "bot 0 gives low to output 2 and high to output 0",
                        "value 2 goes to bot 2"), 30, null)

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