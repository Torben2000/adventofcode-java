package de.beachboys.aoc2023;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;

public class Day20Test extends DayTest {

    private final Day day = new Day20();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("broadcaster -> a, b, c",
                        "%a -> b",
                        "%b -> c",
                        "%c -> inv",
                        "&inv -> a"), 32000000, null),
                Arguments.of(List.of("broadcaster -> a",
                        "%a -> inv, con",
                        "&inv -> b",
                        "%b -> con",
                        "&con -> output"), 11687500, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("broadcaster -> a, b, c",
                        "%a -> b",
                        "%b -> c",
                        "%c -> inv",
                        "&inv -> a"), "no rx", null),
                Arguments.of(List.of("broadcaster -> a",
                        "%a -> inv, con1",
                        "&inv -> b",
                        "%b -> con2",
                        "&con1 -> con",
                        "&con2 -> con",
                        "&con -> rx"), 6, null)
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