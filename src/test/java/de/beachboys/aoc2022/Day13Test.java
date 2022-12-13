package de.beachboys.aoc2022;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;

public class Day13Test extends DayTest {

    private final Day day = new Day13();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("[1,1,3,1,1]",
                        "[1,1,5,1,1]",
                        "",
                        "[[1],[2,3,4]]",
                        "[[1],4]",
                        "",
                        "[9]",
                        "[[8,7,6]]",
                        "",
                        "[[4,4],4,4]",
                        "[[4,4],4,4,4]",
                        "",
                        "[7,7,7,7]",
                        "[7,7,7]",
                        "",
                        "[]",
                        "[3]",
                        "",
                        "[[[]]]",
                        "[[]]",
                        "",
                        "[1,[2,[3,[4,[5,6,7]]]],8,9]",
                        "[1,[2,[3,[4,[5,6,0]]]],8,9]"), 13, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("[1,1,3,1,1]",
                        "[1,1,5,1,1]",
                        "",
                        "[[1],[2,3,4]]",
                        "[[1],4]",
                        "",
                        "[9]",
                        "[[8,7,6]]",
                        "",
                        "[[4,4],4,4]",
                        "[[4,4],4,4,4]",
                        "",
                        "[7,7,7,7]",
                        "[7,7,7]",
                        "",
                        "[]",
                        "[3]",
                        "",
                        "[[[]]]",
                        "[[]]",
                        "",
                        "[1,[2,[3,[4,[5,6,7]]]],8,9]",
                        "[1,[2,[3,[4,[5,6,0]]]],8,9]"), 140, null)
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