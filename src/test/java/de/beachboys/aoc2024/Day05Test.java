package de.beachboys.aoc2024;

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
                Arguments.of(List.of("47|53",
                        "97|13",
                        "97|61",
                        "97|47",
                        "75|29",
                        "61|13",
                        "75|53",
                        "29|13",
                        "97|29",
                        "53|29",
                        "61|53",
                        "97|53",
                        "61|29",
                        "47|13",
                        "75|47",
                        "97|75",
                        "47|61",
                        "75|61",
                        "47|29",
                        "75|13",
                        "53|13",
                        "",
                        "75,47,61,53,29",
                        "97,61,53,29,13",
                        "75,29,13",
                        "75,97,47,61,53",
                        "61,13,29",
                        "97,13,75,29,47"), 143, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("47|53",
                        "97|13",
                        "97|61",
                        "97|47",
                        "75|29",
                        "61|13",
                        "75|53",
                        "29|13",
                        "97|29",
                        "53|29",
                        "61|53",
                        "97|53",
                        "61|29",
                        "47|13",
                        "75|47",
                        "97|75",
                        "47|61",
                        "75|61",
                        "47|29",
                        "75|13",
                        "53|13",
                        "",
                        "75,47,61,53,29",
                        "97,61,53,29,13",
                        "75,29,13",
                        "75,97,47,61,53",
                        "61,13,29",
                        "97,13,75,29,47"), 123, null)
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