package de.beachboys.aoc2025;

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
                Arguments.of(List.of("aaa: you hhh",
                        "you: bbb ccc",
                        "bbb: ddd eee",
                        "ccc: ddd eee fff",
                        "ddd: ggg",
                        "eee: out",
                        "fff: out",
                        "ggg: out",
                        "hhh: ccc fff iii",
                        "iii: out"), 5, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("svr: aaa bbb",
                        "aaa: fft",
                        "fft: ccc",
                        "bbb: tty",
                        "tty: ccc",
                        "ccc: ddd eee",
                        "ddd: hub",
                        "hub: fff",
                        "eee: dac",
                        "dac: fff",
                        "fff: ggg hhh",
                        "ggg: out",
                        "hhh: out"), 2, null)
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