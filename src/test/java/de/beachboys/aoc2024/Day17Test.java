package de.beachboys.aoc2024;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import de.beachboys.IOHelperForTests;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;

public class Day17Test extends DayTest {

    private final Day day = new Day17();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("Register A: 0",
                        "Register B: 0",
                        "Register C: 9",
                        "",
                        "Program: 2,6"), "", new IOHelperForTests(null, List.of("0", "1", "9"))),
                Arguments.of(List.of("Register A: 10",
                        "Register B: 0",
                        "Register C: 0",
                        "",
                        "Program: 5,0,5,1,5,4"), "0,1,2", new IOHelperForTests(null, List.of("10", "0", "0"))),
                Arguments.of(List.of("Register A: 2024",
                        "Register B: 0",
                        "Register C: 0",
                        "",
                        "Program: 0,1,5,4,3,0"), "4,2,5,6,7,7,7,7,3,1,0", new IOHelperForTests(null, List.of("0", "0", "0"))),
                Arguments.of(List.of("Register A: 0",
                        "Register B: 29",
                        "Register C: 0",
                        "",
                        "Program: 1,7"), "", new IOHelperForTests(null, List.of("0", "26", "0"))),
                Arguments.of(List.of("Register A: 0",
                        "Register B: 2024",
                        "Register C: 43690",
                        "",
                        "Program: 4,0"), "", new IOHelperForTests(null, List.of("0", "44354", "43690"))),
                Arguments.of(List.of("Register A: 729",
                        "Register B: 0",
                        "Register C: 0",
                        "",
                        "Program: 0,1,5,4,3,0"), "4,6,3,5,6,3,5,2,1,0", new IOHelperForTests(null, List.of("0", "0", "0")))
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(

                Arguments.of(List.of("Register A: 2024",
                        "Register B: 0",
                        "Register C: 0",
                        "",
                        "Program: 0,3,5,4,3,0"), 117440, null)
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