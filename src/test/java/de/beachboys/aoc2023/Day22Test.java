package de.beachboys.aoc2023;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;

public class Day22Test extends DayTest {

    private final Day day = new Day22();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("1,0,1~1,2,1",
                        "0,0,2~2,0,2",
                        "0,2,3~2,2,3",
                        "0,0,4~0,2,4",
                        "2,0,5~2,2,5",
                        "0,1,6~2,1,6",
                        "1,1,8~1,1,9"), 5, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("1,0,1~1,2,1",
                        "0,0,2~2,0,2",
                        "0,2,3~2,2,3",
                        "0,0,4~0,2,4",
                        "2,0,5~2,2,5",
                        "0,1,6~2,1,6",
                        "1,1,8~1,1,9"), 7, null)
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