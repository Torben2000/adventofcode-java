package de.beachboys.aoc2021;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;

public class Day12Test extends DayTest {

    private final Day day = new Day12();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("start-A",
                        "start-b",
                        "A-c",
                        "A-b",
                        "b-d",
                        "A-end",
                        "b-end"), 10, null),
                Arguments.of(List.of("dc-end",
                        "HN-start",
                        "start-kj",
                        "dc-start",
                        "dc-HN",
                        "LN-dc",
                        "HN-end",
                        "kj-sa",
                        "kj-HN",
                        "kj-dc"), 19, null),
                Arguments.of(List.of("fs-end",
                        "he-DX",
                        "fs-he",
                        "start-DX",
                        "pj-DX",
                        "end-zg",
                        "zg-sl",
                        "zg-pj",
                        "pj-he",
                        "RW-he",
                        "fs-DX",
                        "pj-RW",
                        "zg-RW",
                        "start-pj",
                        "he-WI",
                        "zg-he",
                        "pj-fs",
                        "start-RW"), 226, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("start-A",
                        "start-b",
                        "A-c",
                        "A-b",
                        "b-d",
                        "A-end",
                        "b-end"), 36, null),
                Arguments.of(List.of("dc-end",
                        "HN-start",
                        "start-kj",
                        "dc-start",
                        "dc-HN",
                        "LN-dc",
                        "HN-end",
                        "kj-sa",
                        "kj-HN",
                        "kj-dc"), 103, null),
                Arguments.of(List.of("fs-end",
                        "he-DX",
                        "fs-he",
                        "start-DX",
                        "pj-DX",
                        "end-zg",
                        "zg-sl",
                        "zg-pj",
                        "pj-he",
                        "RW-he",
                        "fs-DX",
                        "pj-RW",
                        "zg-RW",
                        "start-pj",
                        "he-WI",
                        "zg-he",
                        "pj-fs",
                        "start-RW"), 3509, null)
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