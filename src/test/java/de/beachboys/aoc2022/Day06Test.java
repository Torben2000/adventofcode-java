package de.beachboys.aoc2022;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;

public class Day06Test extends DayTest {

    private final Day day = new Day06();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("mjqjpqmgbljsphdztnvjfqwrcgsmlb"), 7, null),
                Arguments.of(List.of("bvwbjplbgvbhsrlpgdmjqwftvncz"), 5, null),
                Arguments.of(List.of("nppdvjthqldpwncqszvftbrmjlhg"), 6, null),
                Arguments.of(List.of("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"), 10, null),
                Arguments.of(List.of("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"), 11, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("mjqjpqmgbljsphdztnvjfqwrcgsmlb"), 19, null),
                Arguments.of(List.of("bvwbjplbgvbhsrlpgdmjqwftvncz"), 23, null),
                Arguments.of(List.of("nppdvjthqldpwncqszvftbrmjlhg"), 23, null),
                Arguments.of(List.of("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"), 29, null),
                Arguments.of(List.of("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"), 26, null)
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