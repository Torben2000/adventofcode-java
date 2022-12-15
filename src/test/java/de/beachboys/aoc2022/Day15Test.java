package de.beachboys.aoc2022;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import de.beachboys.IOHelperForTests;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;

public class Day15Test extends DayTest {

    private final Day day = new Day15();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("Sensor at x=2, y=18: closest beacon is at x=-2, y=15",
                        "Sensor at x=9, y=16: closest beacon is at x=10, y=16",
                        "Sensor at x=13, y=2: closest beacon is at x=15, y=3",
                        "Sensor at x=12, y=14: closest beacon is at x=10, y=16",
                        "Sensor at x=10, y=20: closest beacon is at x=10, y=16",
                        "Sensor at x=14, y=17: closest beacon is at x=10, y=16",
                        "Sensor at x=8, y=7: closest beacon is at x=2, y=10",
                        "Sensor at x=2, y=0: closest beacon is at x=2, y=10",
                        "Sensor at x=0, y=11: closest beacon is at x=2, y=10",
                        "Sensor at x=20, y=14: closest beacon is at x=25, y=17",
                        "Sensor at x=17, y=20: closest beacon is at x=21, y=22",
                        "Sensor at x=16, y=7: closest beacon is at x=15, y=3",
                        "Sensor at x=14, y=3: closest beacon is at x=15, y=3",
                        "Sensor at x=20, y=1: closest beacon is at x=15, y=3"), 26, new IOHelperForTests(List.of("10"), null))
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("Sensor at x=2, y=18: closest beacon is at x=-2, y=15",
                        "Sensor at x=9, y=16: closest beacon is at x=10, y=16",
                        "Sensor at x=13, y=2: closest beacon is at x=15, y=3",
                        "Sensor at x=12, y=14: closest beacon is at x=10, y=16",
                        "Sensor at x=10, y=20: closest beacon is at x=10, y=16",
                        "Sensor at x=14, y=17: closest beacon is at x=10, y=16",
                        "Sensor at x=8, y=7: closest beacon is at x=2, y=10",
                        "Sensor at x=2, y=0: closest beacon is at x=2, y=10",
                        "Sensor at x=0, y=11: closest beacon is at x=2, y=10",
                        "Sensor at x=20, y=14: closest beacon is at x=25, y=17",
                        "Sensor at x=17, y=20: closest beacon is at x=21, y=22",
                        "Sensor at x=16, y=7: closest beacon is at x=15, y=3",
                        "Sensor at x=14, y=3: closest beacon is at x=15, y=3",
                        "Sensor at x=20, y=1: closest beacon is at x=15, y=3"), 56000011, new IOHelperForTests(List.of("20"), null))
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