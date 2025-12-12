package de.beachboys.aoc2025;

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
                Arguments.of(List.of("0:",
                        "###",
                        "##.",
                        "##.",
                        "",
                        "1:",
                        "###",
                        "##.",
                        ".##",
                        "",
                        "2:",
                        ".##",
                        "###",
                        "##.",
                        "",
                        "3:",
                        "##.",
                        "###",
                        "##.",
                        "",
                        "4:",
                        "###",
                        "#..",
                        "###",
                        "",
                        "5:",
                        "###",
                        ".#.",
                        "###",
                        "",
                        "4x4: 0 0 0 0 2 0",
                        "12x5: 1 0 1 0 2 2",
                        "12x5: 1 0 1 0 3 2"), 2, new IOHelperForTests(List.of("1"), null))
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("Nothing to see here"), "There is no puzzle! :-)", null)
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