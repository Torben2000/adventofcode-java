package de.beachboys.aoc2024;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;

public class Day07Test extends DayTest {

    private final Day day = new Day07();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("190: 10 19",
                        "3267: 81 40 27",
                        "83: 17 5",
                        "156: 15 6",
                        "7290: 6 8 6 15",
                        "161011: 16 10 13",
                        "192: 17 8 14",
                        "21037: 9 7 18 13",
                        "292: 11 6 16 20"), 3749, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("190: 10 19",
                        "3267: 81 40 27",
                        "83: 17 5",
                        "156: 15 6",
                        "7290: 6 8 6 15",
                        "161011: 16 10 13",
                        "192: 17 8 14",
                        "21037: 9 7 18 13",
                        "292: 11 6 16 20"), 11387, null)
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