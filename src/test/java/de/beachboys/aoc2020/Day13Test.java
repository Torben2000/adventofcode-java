package de.beachboys.aoc2020;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;import java.util.List;
import java.util.stream.Stream;

public class Day13Test extends DayTest {

    private final Day day = new Day13();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("939",
                        "7,13,x,x,59,x,31,19"), 295, null)

        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("939",
                        "7,13,x,x,59,x,31,19"), 1068781, null),
                Arguments.of(List.of("939",
                        "17,x,13,19"), 3417, null),
                Arguments.of(List.of("939",
                        "67,7,59,61"), 754018, null),
                Arguments.of(List.of("939",
                        "67,x,7,59,61"), 779210, null),
                Arguments.of(List.of("939",
                        "67,7,x,59,61"), 1261476, null),
                Arguments.of(List.of("939",
                        "1789,37,47,1889"), 1202161486, null)

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