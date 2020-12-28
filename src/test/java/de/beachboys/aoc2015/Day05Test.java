package de.beachboys.aoc2015;

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
                Arguments.of(List.of("ugknbfddgicrmopn"), 1, null),
                Arguments.of(List.of("aaa"), 1, null),
                Arguments.of(List.of("jchzalrnumimnmhp"), 0, null),
                Arguments.of(List.of("haegwjzuvuyypxyu"), 0, null),
                Arguments.of(List.of("dvszwmarrgswjxmb"), 0, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("qjhvhtzxzqqjkmpb"), 1, null),
                Arguments.of(List.of("xxyxx"), 1, null),
                Arguments.of(List.of("uurcxstgmygtbstg"), 0, null),
                Arguments.of(List.of("ieodomkazucvgmuy"), 0, null)
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