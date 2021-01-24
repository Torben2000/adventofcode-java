package de.beachboys.aoc2016;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import de.beachboys.IOHelperForTests;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;

public class Day25Test extends DayTest {

    private final Day day = new Day25();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("cpy a 0",
                        "out a",
                        "out 1",
                        "out a",
                        "out 1",
                        "out a",
                        "out 1",
                        "out a",
                        "out 1",
                        "out a",
                        "out 1",
                        "out a",
                        "out 1",
                        "out a",
                        "out 1",
                        "out a",
                        "out 1",
                        "out a",
                        "out 1",
                        "out a",
                        "out 1",
                        "out a"), 0, new IOHelperForTests(List.of("10", "20"), null)),
                Arguments.of(List.of("cpy a 1",
                        "out a",
                        "out 1",
                        "out a",
                        "out 1",
                        "out a",
                        "out 1",
                        "out a",
                        "out 1",
                        "out a",
                        "out 1",
                        "out a",
                        "out 1",
                        "out a",
                        "out 1",
                        "out a",
                        "out 1",
                        "out a",
                        "out 1",
                        "out a"), -1, new IOHelperForTests(List.of("10", "20"), null)),
                Arguments.of(List.of("cpy a 0",
                        "out a",
                        "out 1",
                        "out a",
                        "out 1",
                        "out a",
                        "out 1",
                        "out a"), -1, new IOHelperForTests(List.of("10", "20"), null)),
                Arguments.of(List.of("cpy a 0",
                        "out a",
                        "out 1",
                        "out a",
                        "out 1",
                        "out a",
                        "out 1",
                        "out a"), 0, new IOHelperForTests(List.of("10", "7"), null))
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("Nothing to see here"), "There is no puzzle! :-)", null)
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