package de.beachboys.aoc2022;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;

public class Day18Test extends DayTest {

    private final Day day = new Day18();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("1,1,1",
                        "2,1,1"), 10, null),
                Arguments.of(List.of("2,2,2",
                        "1,2,2",
                        "3,2,2",
                        "2,1,2",
                        "2,3,2",
                        "2,2,1",
                        "2,2,3",
                        "2,2,4",
                        "2,2,6",
                        "1,2,5",
                        "3,2,5",
                        "2,1,5",
                        "2,3,5"), 64, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("2,2,2",
                        "1,2,2",
                        "3,2,2",
                        "2,1,2",
                        "2,3,2",
                        "2,2,1",
                        "2,2,3",
                        "2,2,4",
                        "2,2,6",
                        "1,2,5",
                        "3,2,5",
                        "2,1,5",
                        "2,3,5"), 58, null)
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