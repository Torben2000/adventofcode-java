package de.beachboys.aoc2023;

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
                Arguments.of(List.of("???.### 1,1,3",
                        ".??..??...?##. 1,1,3",
                        "?#?#?#?#?#?#?#? 1,3,1,6",
                        "????.#...#... 4,1,1",
                        "????.######..#####. 1,6,5",
                        "?###???????? 3,2,1"), 21, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("???.### 1,1,3",
                        ".??..??...?##. 1,1,3",
                        "?#?#?#?#?#?#?#? 1,3,1,6",
                        "????.#...#... 4,1,1",
                        "????.######..#####. 1,6,5",
                        "?###???????? 3,2,1"), 525152, null)
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