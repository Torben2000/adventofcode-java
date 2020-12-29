package de.beachboys.aoc2015;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;

public class Day16Test extends DayTest {

    private final Day day = new Day16();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("Sue 38: cars: 10, akitas: 5, vizslas: 8",
                        "Sue 39: akitas: 5, trees: 9, children: 2",
                        "Sue 40: vizslas: 0, cats: 7, akitas: 0",
                        "Sue 41: cars: 9, trees: 10, perfumes: 8",
                        "Sue 42: akitas: 4, trees: 2, goldfish: 3",
                        "Sue 43: goldfish: 1, cats: 1, akitas: 8",
                        "Sue 44: goldfish: 8, akitas: 9, vizslas: 4",
                        "Sue 45: perfumes: 3, goldfish: 4, trees: 0",
                        "Sue 240: goldfish: 9, trees: 1, perfumes: 1",
                        "Sue 241: cars: 2, pomeranians: 1, samoyeds: 2",
                        "Sue 242: akitas: 2, trees: 3, cars: 4",
                        "Sue 243: vizslas: 6, akitas: 2, samoyeds: 7",
                        "Sue 244: trees: 0, perfumes: 5, cars: 7",
                        "Sue 245: goldfish: 10, perfumes: 5, vizslas: 8"), 40, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("Sue 38: cars: 10, akitas: 5, vizslas: 8",
                        "Sue 39: akitas: 5, trees: 9, children: 2",
                        "Sue 40: vizslas: 0, cats: 7, akitas: 0",
                        "Sue 41: cars: 9, trees: 10, perfumes: 8",
                        "Sue 42: akitas: 4, trees: 2, goldfish: 3",
                        "Sue 43: goldfish: 1, cats: 1, akitas: 8",
                        "Sue 44: goldfish: 8, akitas: 9, vizslas: 4",
                        "Sue 45: perfumes: 3, goldfish: 4, trees: 0",
                        "Sue 240: goldfish: 9, trees: 1, perfumes: 1",
                        "Sue 241: cars: 2, pomeranians: 1, samoyeds: 2",
                        "Sue 242: akitas: 2, trees: 3, cars: 4",
                        "Sue 243: vizslas: 6, akitas: 2, samoyeds: 7",
                        "Sue 244: trees: 0, perfumes: 5, cars: 7",
                        "Sue 245: goldfish: 10, perfumes: 5, vizslas: 8"), 241, null)
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