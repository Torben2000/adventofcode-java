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

public class Day12Test extends DayTest {

    private final Day day = new Day12();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("<x=-1, y=0, z=2>",
                        "<x=2, y=-10, z=-7>",
                        "<x=4, y=-8, z=8>",
                        "<x=3, y=5, z=-1>"), 179, new IOHelperForTests(List.of("10"), null)),
                Arguments.of(List.of("<x=-8, y=-10, z=0>",
                        "<x=5, y=5, z=10>",
                        "<x=2, y=-7, z=3>",
                        "<x=9, y=-8, z=-3>"), 1940, new IOHelperForTests(List.of("100"), null))

        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("<x=-1, y=0, z=2>",
                        "<x=2, y=-10, z=-7>",
                        "<x=4, y=-8, z=8>",
                        "<x=3, y=5, z=-1>"), 2772, new IOHelperForTests(List.of("10"), null)),
                Arguments.of(List.of("<x=-8, y=-10, z=0>",
                        "<x=5, y=5, z=10>",
                        "<x=2, y=-7, z=3>",
                        "<x=9, y=-8, z=-3>"), 4686774924L, new IOHelperForTests(List.of("100"), null))

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
