package de.beachboys.aoc2023;

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
                Arguments.of(List.of("Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53",
                        "Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19",
                        "Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1",
                        "Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83",
                        "Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36",
                        "Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11"), 13, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53",
                        "Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19",
                        "Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1",
                        "Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83",
                        "Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36",
                        "Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11"), 30, null)
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