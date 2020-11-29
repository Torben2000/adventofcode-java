package de.beachboys.aoc2019;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import de.beachboys.IOHelperForTests;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

public class Day05Test extends DayTest {

    private final Day day = new Day05();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("1002,4,3,4,33"), "1002,4,3,4,99", null),
                Arguments.of(List.of("1101,100,-1,4,0"), "1101,100,-1,4,99", null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("3,9,8,9,10,9,4,9,99,-1,8"), null, new IOHelperForTests(List.of("8"), List.of("1"))),
                Arguments.of(List.of("3,9,8,9,10,9,4,9,99,-1,8"), null, new IOHelperForTests(List.of("5"), List.of("0"))),
                Arguments.of(List.of("3,9,8,9,10,9,4,9,99,-1,8"), null, new IOHelperForTests(List.of("9"), List.of("0"))),
                Arguments.of(List.of("3,9,7,9,10,9,4,9,99,-1,8"), null, new IOHelperForTests(List.of("8"), List.of("0"))),
                Arguments.of(List.of("3,9,7,9,10,9,4,9,99,-1,8"), null, new IOHelperForTests(List.of("5"), List.of("1"))),
                Arguments.of(List.of("3,9,7,9,10,9,4,9,99,-1,8"), null, new IOHelperForTests(List.of("9"), List.of("0"))),
                Arguments.of(List.of("3,3,1108,-1,8,3,4,3,99"), null, new IOHelperForTests(List.of("8"), List.of("1"))),
                Arguments.of(List.of("3,3,1108,-1,8,3,4,3,99"), null, new IOHelperForTests(List.of("5"), List.of("0"))),
                Arguments.of(List.of("3,3,1108,-1,8,3,4,3,99"), null, new IOHelperForTests(List.of("9"), List.of("0"))),
                Arguments.of(List.of("3,3,1107,-1,8,3,4,3,99"), null, new IOHelperForTests(List.of("8"), List.of("0"))),
                Arguments.of(List.of("3,3,1107,-1,8,3,4,3,99"), null, new IOHelperForTests(List.of("5"), List.of("1"))),
                Arguments.of(List.of("3,3,1107,-1,8,3,4,3,99"), null, new IOHelperForTests(List.of("9"), List.of("0"))),
                Arguments.of(List.of("3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99"), null, new IOHelperForTests(List.of("8"), List.of("1000"))),
                Arguments.of(List.of("3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99"), null, new IOHelperForTests(List.of("5"), List.of("999"))),
                Arguments.of(List.of("3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99"), null, new IOHelperForTests(List.of("9"), List.of("1001")))
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