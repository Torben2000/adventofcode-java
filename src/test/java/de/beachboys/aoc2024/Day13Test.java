package de.beachboys.aoc2024;

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
                Arguments.of(List.of("Button A: X+94, Y+34",
                        "Button B: X+22, Y+67",
                        "Prize: X=8400, Y=5400",
                        "",
                        "Button A: X+26, Y+66",
                        "Button B: X+67, Y+21",
                        "Prize: X=12748, Y=12176",
                        "",
                        "Button A: X+17, Y+86",
                        "Button B: X+84, Y+37",
                        "Prize: X=7870, Y=6450",
                        "",
                        "Button A: X+69, Y+23",
                        "Button B: X+27, Y+71",
                        "Prize: X=18641, Y=10279"), 480, null)

        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("Button A: X+94, Y+34",
                        "Button B: X+22, Y+67",
                        "Prize: X=8400, Y=5400",
                        "",
                        "Button A: X+26, Y+66",
                        "Button B: X+67, Y+21",
                        "Prize: X=12748, Y=12176",
                        "",
                        "Button A: X+17, Y+86",
                        "Button B: X+84, Y+37",
                        "Prize: X=7870, Y=6450",
                        "",
                        "Button A: X+69, Y+23",
                        "Button B: X+27, Y+71",
                        "Prize: X=18641, Y=10279"), 875318608908L, null)
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