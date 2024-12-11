package de.beachboys.aoc2024;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;

public class Day10Test extends DayTest {

    private final Day day = new Day10();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("0123",
                        "1234",
                        "8765",
                        "9876"), 1, null),
                Arguments.of(List.of("...0...",
                        "...1...",
                        "...2...",
                        "6543456",
                        "7.....7",
                        "8.....8",
                        "9.....9"), 2, null),
                Arguments.of(List.of("..90..9",
                        "...1.98",
                        "...2..7",
                        "6543456",
                        "765.987",
                        "876....",
                        "987...."), 4, null),
                Arguments.of(List.of("10..9..",
                        "2...8..",
                        "3...7..",
                        "4567654",
                        "...8..3",
                        "...9..2",
                        ".....01"), 3, null),
                Arguments.of(List.of("89010123",
                        "78121874",
                        "87430965",
                        "96549874",
                        "45678903",
                        "32019012",
                        "01329801",
                        "10456732"), 36, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of(".....0.",
                        "..4321.",
                        "..5..2.",
                        "..6543.",
                        "..7..4.",
                        "..8765.",
                        "..9...."), 3, null),
                Arguments.of(List.of("..90..9",
                        "...1.98",
                        "...2..7",
                        "6543456",
                        "765.987",
                        "876....",
                        "987...."), 13, null),
                Arguments.of(List.of("012345",
                        "123456",
                        "234567",
                        "345678",
                        "4.6789",
                        "56789."), 227, null),
                Arguments.of(List.of("89010123",
                        "78121874",
                        "87430965",
                        "96549874",
                        "45678903",
                        "32019012",
                        "01329801",
                        "10456732"), 81, null)
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