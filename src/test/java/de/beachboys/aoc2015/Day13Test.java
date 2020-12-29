package de.beachboys.aoc2015;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;

public class Day13Test extends DayTest {

    private final Day day = new Day13();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("Alice would gain 54 happiness units by sitting next to Bob.",
                        "Alice would lose 79 happiness units by sitting next to Carol.",
                        "Alice would lose 2 happiness units by sitting next to David.",
                        "Bob would gain 83 happiness units by sitting next to Alice.",
                        "Bob would lose 7 happiness units by sitting next to Carol.",
                        "Bob would lose 63 happiness units by sitting next to David.",
                        "Carol would lose 62 happiness units by sitting next to Alice.",
                        "Carol would gain 60 happiness units by sitting next to Bob.",
                        "Carol would gain 55 happiness units by sitting next to David.",
                        "David would gain 46 happiness units by sitting next to Alice.",
                        "David would lose 7 happiness units by sitting next to Bob.",
                        "David would gain 41 happiness units by sitting next to Carol."), 330, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("Alice would gain 54 happiness units by sitting next to Bob.",
                        "Alice would lose 79 happiness units by sitting next to Carol.",
                        "Alice would lose 2 happiness units by sitting next to David.",
                        "Bob would gain 83 happiness units by sitting next to Alice.",
                        "Bob would lose 7 happiness units by sitting next to Carol.",
                        "Bob would lose 63 happiness units by sitting next to David.",
                        "Carol would lose 62 happiness units by sitting next to Alice.",
                        "Carol would gain 60 happiness units by sitting next to Bob.",
                        "Carol would gain 55 happiness units by sitting next to David.",
                        "David would gain 46 happiness units by sitting next to Alice.",
                        "David would lose 7 happiness units by sitting next to Bob.",
                        "David would gain 41 happiness units by sitting next to Carol."), 286, null)
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