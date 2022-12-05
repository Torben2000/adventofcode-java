package de.beachboys.aoc2022;

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
                Arguments.of(List.of("    [D]    ",
                        "[N] [C]    ",
                        "[Z] [M] [P]",
                        " 1   2   3 ",
                        "",
                        "move 1 from 2 to 1",
                        "move 3 from 1 to 3",
                        "move 2 from 2 to 1",
                        "move 1 from 1 to 2"), "CMZ", null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("    [D]    ",
                        "[N] [C]    ",
                        "[Z] [M] [P]",
                        " 1   2   3 ",
                        "",
                        "move 1 from 2 to 1",
                        "move 3 from 1 to 3",
                        "move 2 from 2 to 1",
                        "move 1 from 1 to 2"), "MCD", null)
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