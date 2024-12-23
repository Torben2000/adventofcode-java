package de.beachboys.aoc2024;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;

public class Day23Test extends DayTest {

    private final Day day = new Day23();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("kh-tc",
                        "qp-kh",
                        "de-cg",
                        "ka-co",
                        "yn-aq",
                        "qp-ub",
                        "cg-tb",
                        "vc-aq",
                        "tb-ka",
                        "wh-tc",
                        "yn-cg",
                        "kh-ub",
                        "ta-co",
                        "de-co",
                        "tc-td",
                        "tb-wq",
                        "wh-td",
                        "ta-ka",
                        "td-qp",
                        "aq-cg",
                        "wq-ub",
                        "ub-vc",
                        "de-ta",
                        "wq-aq",
                        "wq-vc",
                        "wh-yn",
                        "ka-de",
                        "kh-ta",
                        "co-tc",
                        "wh-qp",
                        "tb-vc",
                        "td-yn"), 7, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("kh-tc",
                        "qp-kh",
                        "de-cg",
                        "ka-co",
                        "yn-aq",
                        "qp-ub",
                        "cg-tb",
                        "vc-aq",
                        "tb-ka",
                        "wh-tc",
                        "yn-cg",
                        "kh-ub",
                        "ta-co",
                        "de-co",
                        "tc-td",
                        "tb-wq",
                        "wh-td",
                        "ta-ka",
                        "td-qp",
                        "aq-cg",
                        "wq-ub",
                        "ub-vc",
                        "de-ta",
                        "wq-aq",
                        "wq-vc",
                        "wh-yn",
                        "ka-de",
                        "kh-ta",
                        "co-tc",
                        "wh-qp",
                        "tb-vc",
                        "td-yn"), "co,de,ka,ta", null)
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