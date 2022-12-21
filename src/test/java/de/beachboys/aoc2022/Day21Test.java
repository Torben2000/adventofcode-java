package de.beachboys.aoc2022;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;

public class Day21Test extends DayTest {

    private final Day day = new Day21();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("root: pppw + sjmn",
                        "dbpl: 5",
                        "cczh: sllz + lgvd",
                        "zczc: 2",
                        "ptdq: humn - dvpt",
                        "dvpt: 3",
                        "lfqf: 4",
                        "humn: 5",
                        "ljgn: 2",
                        "sjmn: drzm * dbpl",
                        "sllz: 4",
                        "pppw: cczh / lfqf",
                        "lgvd: ljgn * ptdq",
                        "drzm: hmdt - zczc",
                        "hmdt: 32"), 152, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("root: pppw + sjmn",
                        "dbpl: 5",
                        "cczh: sllz + lgvd",
                        "zczc: 2",
                        "ptdq: humn - dvpt",
                        "dvpt: 3",
                        "lfqf: 4",
                        "humn: 5",
                        "ljgn: 2",
                        "sjmn: drzm * dbpl",
                        "sllz: 4",
                        "pppw: cczh / lfqf",
                        "lgvd: ljgn * ptdq",
                        "drzm: hmdt - zczc",
                        "hmdt: 32"), 301, null)
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