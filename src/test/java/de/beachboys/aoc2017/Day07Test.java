package de.beachboys.aoc2017;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;

public class Day07Test extends DayTest {

    private final Day day = new Day07();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("pbga (66)",
                        "xhth (57)",
                        "ebii (61)",
                        "havc (66)",
                        "ktlj (57)",
                        "fwft (72) -> ktlj, cntj, xhth",
                        "qoyq (66)",
                        "padx (45) -> pbga, havc, qoyq",
                        "tknk (41) -> ugml, padx, fwft",
                        "jptl (61)",
                        "ugml (68) -> gyxo, ebii, jptl",
                        "gyxo (61)",
                        "cntj (57)"), "tknk", null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("pbga (66)",
                        "xhth (57)",
                        "ebii (61)",
                        "havc (66)",
                        "ktlj (57)",
                        "fwft (72) -> ktlj, cntj, xhth",
                        "qoyq (66)",
                        "padx (45) -> pbga, havc, qoyq",
                        "tknk (41) -> ugml, padx, fwft",
                        "jptl (61)",
                        "ugml (68) -> gyxo, ebii, jptl",
                        "gyxo (61)",
                        "cntj (57)"), 60, null)
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