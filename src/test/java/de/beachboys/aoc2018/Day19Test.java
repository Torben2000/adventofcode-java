package de.beachboys.aoc2018;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;

public class Day19Test extends DayTest {

    private final Day day = new Day19();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("#ip 0",
                        "seti 5 0 1",
                        "seti 6 0 2",
                        "addi 0 1 0",
                        "addr 1 2 3",
                        "setr 1 0 0",
                        "seti 8 0 4",
                        "seti 9 0 5"), 7, null),
                Arguments.of(List.of("#ip 3",
                        "addi 3 16 3",
                        "seti 1 5 2",
                        "seti 1 5 4",
                        "mulr 2 4 1",
                        "eqrr 1 5 1",
                        "addr 1 3 3",
                        "addi 3 1 3",
                        "addr 2 0 0",
                        "addi 4 1 4",
                        "gtrr 4 5 1",
                        "addr 3 1 3",
                        "seti 2 7 3",
                        "addi 2 1 2",
                        "gtrr 2 5 1",
                        "addr 1 3 3",
                        "seti 1 4 3",
                        "mulr 3 3 3",
                        "addi 5 2 5",
                        "addr 5 0 5",
                        "mulr 5 5 5",
                        "seti 0 4 3"), 7, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("#ip 3",
                        "addi 3 16 3",
                        "seti 1 5 2",
                        "seti 1 5 4",
                        "mulr 2 4 1",
                        "eqrr 1 5 1",
                        "addr 1 3 3",
                        "addi 3 1 3",
                        "addr 2 0 0",
                        "addi 4 1 4",
                        "gtrr 4 5 1",
                        "addr 3 1 3",
                        "seti 2 7 3",
                        "addi 2 1 2",
                        "gtrr 2 5 1",
                        "addr 1 3 3",
                        "seti 1 4 3",
                        "mulr 3 3 3",
                        "addi 5 2 5",
                        "addr 5 0 5",
                        "mulr 5 5 5",
                        "seti 0 4 3"), 13, null)
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