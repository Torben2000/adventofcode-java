package de.beachboys.aoc2018;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;

public class Day25Test extends DayTest {

    private final Day day = new Day25();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("0,0,0,0",
                        " 3,0,0,0",
                        " 0,3,0,0",
                        " 0,0,3,0",
                        " 0,0,0,3",
                        " 0,0,0,6",
                        " 9,0,0,0",
                        "12,0,0,0"), 2, null),
                Arguments.of(List.of("-1,2,2,0",
                        "0,0,2,-2",
                        "0,0,0,-2",
                        "-1,2,0,0",
                        "-2,-2,-2,2",
                        "3,0,2,-1",
                        "-1,3,2,2",
                        "-1,0,-1,0",
                        "0,2,1,-2",
                        "3,0,0,0"), 4, null),
                Arguments.of(List.of("1,-1,0,1",
                        "2,0,-1,0",
                        "3,2,-1,0",
                        "0,0,3,1",
                        "0,0,-1,-1",
                        "2,3,-2,0",
                        "-2,2,0,0",
                        "2,-2,0,-1",
                        "1,-1,0,-1",
                        "3,2,0,2"), 3, null),
                Arguments.of(List.of("1,-1,-1,-2",
                        "-2,-2,0,1",
                        "0,2,1,3",
                        "-2,3,-2,1",
                        "0,2,3,-2",
                        "-1,-1,1,-2",
                        "0,-2,-1,0",
                        "-2,2,3,-1",
                        "1,2,2,0",
                        "-1,-2,0,-2"), 8, null)
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