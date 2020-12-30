package de.beachboys.aoc2015;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import de.beachboys.IOHelperForTests;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;

public class Day22Test extends DayTest {

    private final Day day = new Day22();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("Hit Points: 13",
                        "Damage: 8"), 226, new IOHelperForTests(List.of("10", "250"), null)),
                Arguments.of(List.of("Hit Points: 14",
                        "Damage: 8"), 641, new IOHelperForTests(List.of("10", "250"), null))
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("Hit Points: 13",
                        "Damage: 8"), 226, new IOHelperForTests(List.of("17", "250"), null)),
                Arguments.of(List.of("Hit Points: 14",
                        "Damage: 8"), 568, new IOHelperForTests(List.of("17", "250"), null))
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