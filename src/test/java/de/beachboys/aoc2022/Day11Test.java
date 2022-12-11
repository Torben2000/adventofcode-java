package de.beachboys.aoc2022;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;

public class Day11Test extends DayTest {

    private final Day day = new Day11();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("Monkey 0:",
                        "  Starting items: 79, 98",
                        "  Operation: new = old * 19",
                        "  Test: divisible by 23",
                        "    If true: throw to monkey 2",
                        "    If false: throw to monkey 3",
                        "",
                        "Monkey 1:",
                        "  Starting items: 54, 65, 75, 74",
                        "  Operation: new = old + 6",
                        "  Test: divisible by 19",
                        "    If true: throw to monkey 2",
                        "    If false: throw to monkey 0",
                        "",
                        "Monkey 2:",
                        "  Starting items: 79, 60, 97",
                        "  Operation: new = old * old",
                        "  Test: divisible by 13",
                        "    If true: throw to monkey 1",
                        "    If false: throw to monkey 3",
                        "",
                        "Monkey 3:",
                        "  Starting items: 74",
                        "  Operation: new = old + 3",
                        "  Test: divisible by 17",
                        "    If true: throw to monkey 0",
                        "    If false: throw to monkey 1"), 10605, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("Monkey 0:",
                        "  Starting items: 79, 98",
                        "  Operation: new = old * 19",
                        "  Test: divisible by 23",
                        "    If true: throw to monkey 2",
                        "    If false: throw to monkey 3",
                        "",
                        "Monkey 1:",
                        "  Starting items: 54, 65, 75, 74",
                        "  Operation: new = old + 6",
                        "  Test: divisible by 19",
                        "    If true: throw to monkey 2",
                        "    If false: throw to monkey 0",
                        "",
                        "Monkey 2:",
                        "  Starting items: 79, 60, 97",
                        "  Operation: new = old * old",
                        "  Test: divisible by 13",
                        "    If true: throw to monkey 1",
                        "    If false: throw to monkey 3",
                        "",
                        "Monkey 3:",
                        "  Starting items: 74",
                        "  Operation: new = old + 3",
                        "  Test: divisible by 17",
                        "    If true: throw to monkey 0",
                        "    If false: throw to monkey 1"), 2713310158L, null)
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