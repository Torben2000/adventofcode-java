package de.beachboys.aoc2021;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;

public class Day10Test extends DayTest {

    private final Day day = new Day10();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("[({(<(())[]>[[{[]{<()<>>",
                        "[(()[<>])]({[<{<<[]>>(",
                        "{([(<{}[<>[]}>{[]{[(<()>",
                        "(((({<>}<{<{<>}{[]{[]{}",
                        "[[<[([]))<([[{}[[()]]]",
                        "[{[{({}]{}}([{[{{{}}([]",
                        "{<[[]]>}<{[{[{[]{()[[[]",
                        "[<(<(<(<{}))><([]([]()",
                        "<{([([[(<>()){}]>(<<{{",
                        "<{([{{}}[<[[[<>{}]]]>[]]"), 26397, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("[({(<(())[]>[[{[]{<()<>>",
                        "[(()[<>])]({[<{<<[]>>(",
                        "{([(<{}[<>[]}>{[]{[(<()>",
                        "(((({<>}<{<{<>}{[]{[]{}",
                        "[[<[([]))<([[{}[[()]]]",
                        "[{[{({}]{}}([{[{{{}}([]",
                        "{<[[]]>}<{[{[{[]{()[[[]",
                        "[<(<(<(<{}))><([]([]()",
                        "<{([([[(<>()){}]>(<<{{",
                        "<{([{{}}[<[[[<>{}]]]>[]]"), 288957, null)
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