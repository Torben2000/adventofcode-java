package de.beachboys.aoc2023;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;

public class Day08Test extends DayTest {

    private final Day day = new Day08();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("LLR",
                        "",
                        "AAA = (BBB, BBB)",
                        "BBB = (AAA, ZZZ)",
                        "ZZZ = (ZZZ, ZZZ)"), 6, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("LR",
                        "",
                        "11A = (11B, XXX)",
                        "11B = (XXX, 11Z)",
                        "11Z = (11B, XXX)",
                        "22A = (22B, XXX)",
                        "22B = (22C, 22C)",
                        "22C = (22Z, 22Z)",
                        "22Z = (22B, 22B)",
                        "XXX = (XXX, XXX)"), 6, null)
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