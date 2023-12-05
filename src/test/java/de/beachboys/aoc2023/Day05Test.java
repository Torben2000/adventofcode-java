package de.beachboys.aoc2023;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;

public class Day05Test extends DayTest {

    private final Day day = new Day05();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("seeds: 79 14 55 13",
                        "",
                        "seed-to-soil map:",
                        "50 98 2",
                        "52 50 48",
                        "",
                        "soil-to-fertilizer map:",
                        "0 15 37",
                        "37 52 2",
                        "39 0 15",
                        "",
                        "fertilizer-to-water map:",
                        "49 53 8",
                        "0 11 42",
                        "42 0 7",
                        "57 7 4",
                        "",
                        "water-to-light map:",
                        "88 18 7",
                        "18 25 70",
                        "",
                        "light-to-temperature map:",
                        "45 77 23",
                        "81 45 19",
                        "68 64 13",
                        "",
                        "temperature-to-humidity map:",
                        "0 69 1",
                        "1 0 69",
                        "",
                        "humidity-to-location map:",
                        "60 56 37",
                        "56 93 4"), 35, null)

        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("seeds: 79 14 55 13",
                        "",
                        "seed-to-soil map:",
                        "50 98 2",
                        "52 50 48",
                        "",
                        "soil-to-fertilizer map:",
                        "0 15 37",
                        "37 52 2",
                        "39 0 15",
                        "",
                        "fertilizer-to-water map:",
                        "49 53 8",
                        "0 11 42",
                        "42 0 7",
                        "57 7 4",
                        "",
                        "water-to-light map:",
                        "88 18 7",
                        "18 25 70",
                        "",
                        "light-to-temperature map:",
                        "45 77 23",
                        "81 45 19",
                        "68 64 13",
                        "",
                        "temperature-to-humidity map:",
                        "0 69 1",
                        "1 0 69",
                        "",
                        "humidity-to-location map:",
                        "60 56 37",
                        "56 93 4"), 46, null)

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