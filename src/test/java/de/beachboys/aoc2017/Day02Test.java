package de.beachboys.aoc2017;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;

public class Day02Test extends DayTest {

    private final Day day = new Day02();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("5\t1\t9\t5",
                        "7\t5\t3",
                        "2\t4\t6\t8"), 18, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("5\t9\t2\t8",
                        "9\t4\t7\t3",
                        "3\t8\t6\t5"), 9, null)

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