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

public class Day14Test extends DayTest {

    private final Day day = new Day14();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds.",
                        "Dancer can fly 16 km/s for 11 seconds, but then must rest for 162 seconds."), 1120, new IOHelperForTests(List.of("1000"), null))
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds.",
                        "Dancer can fly 16 km/s for 11 seconds, but then must rest for 162 seconds."), 689, new IOHelperForTests(List.of("1000"), null))
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