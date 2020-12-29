package de.beachboys.aoc2015;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;

public class Day12Test extends DayTest {

    private final Day day = new Day12();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("[1,2,3]"), 6, null),
                Arguments.of(List.of("{\"a\":2,\"b\":4}"), 6, null),
                Arguments.of(List.of("[[[3]]]"), 3, null),
                Arguments.of(List.of("{\"a\":{\"b\":4},\"c\":-1}"), 3, null),
                Arguments.of(List.of("{\"a\":[-1,1]}"), 0, null),
                Arguments.of(List.of("[-1,{\"a\":12}]"), 11, null),
                Arguments.of(List.of("{}"), 0, null),
                Arguments.of(List.of("[]"), 0, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("[1,2,3]"), 6, null),
                Arguments.of(List.of("[1,{\"c\":\"red\",\"b\":2},3]"), 4, null),
                Arguments.of(List.of("{\"d\":\"red\",\"e\":[1,2,3,4],\"f\":5}"), 0, null),
                Arguments.of(List.of("[1,\"red\",5]"), 6, null)
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