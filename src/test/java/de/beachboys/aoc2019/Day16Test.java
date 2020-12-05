package de.beachboys.aoc2019;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import de.beachboys.IOHelperForTests;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

public class Day16Test extends DayTest {

    private final Day day = new Day16();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("12345678"), "48226158", new IOHelperForTests(List.of("1"), null)),
                Arguments.of(List.of("12345678"), "34040438", new IOHelperForTests(List.of("2"), null)),
                Arguments.of(List.of("12345678"), "03415518", new IOHelperForTests(List.of("3"), null)),
                Arguments.of(List.of("80871224585914546619083218645595"), "24176176", new IOHelperForTests(List.of("100"), null)),
                Arguments.of(List.of("19617804207202209144916044189917"), "73745418", new IOHelperForTests(List.of("100"), null)),
                Arguments.of(List.of("69317163492948606335995924319873"), "52432133", new IOHelperForTests(List.of("100"), null))

        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("03036732577212944063491565474664"), 84462026, new IOHelperForTests(List.of("100"), null)),
                Arguments.of(List.of("02935109699940807407585447034323"), 78725270, new IOHelperForTests(List.of("100"), null)),
                Arguments.of(List.of("03081770884921959731165446850517"), 53553731, new IOHelperForTests(List.of("100"), null))

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