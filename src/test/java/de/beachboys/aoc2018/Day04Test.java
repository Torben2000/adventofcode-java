package de.beachboys.aoc2018;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;

public class Day04Test extends DayTest {

    private final Day day = new Day04();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("[1518-11-01 00:00] Guard #10 begins shift",
                        "[1518-11-01 00:05] falls asleep",
                        "[1518-11-01 00:25] wakes up",
                        "[1518-11-01 00:30] falls asleep",
                        "[1518-11-01 00:55] wakes up",
                        "[1518-11-01 23:58] Guard #99 begins shift",
                        "[1518-11-02 00:40] falls asleep",
                        "[1518-11-02 00:50] wakes up",
                        "[1518-11-03 00:05] Guard #10 begins shift",
                        "[1518-11-03 00:24] falls asleep",
                        "[1518-11-03 00:29] wakes up",
                        "[1518-11-04 00:02] Guard #99 begins shift",
                        "[1518-11-04 00:36] falls asleep",
                        "[1518-11-04 00:46] wakes up",
                        "[1518-11-05 00:03] Guard #99 begins shift",
                        "[1518-11-05 00:45] falls asleep",
                        "[1518-11-05 00:55] wakes up"), 240, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("[1518-11-01 00:00] Guard #10 begins shift",
                        "[1518-11-01 00:05] falls asleep",
                        "[1518-11-01 00:25] wakes up",
                        "[1518-11-01 00:30] falls asleep",
                        "[1518-11-01 00:55] wakes up",
                        "[1518-11-01 23:58] Guard #99 begins shift",
                        "[1518-11-02 00:40] falls asleep",
                        "[1518-11-02 00:50] wakes up",
                        "[1518-11-03 00:05] Guard #10 begins shift",
                        "[1518-11-03 00:24] falls asleep",
                        "[1518-11-03 00:29] wakes up",
                        "[1518-11-04 00:02] Guard #99 begins shift",
                        "[1518-11-04 00:36] falls asleep",
                        "[1518-11-04 00:46] wakes up",
                        "[1518-11-05 00:03] Guard #99 begins shift",
                        "[1518-11-05 00:45] falls asleep",
                        "[1518-11-05 00:55] wakes up"), 4455, null)
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