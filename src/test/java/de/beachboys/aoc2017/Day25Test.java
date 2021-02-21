package de.beachboys.aoc2017;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;

public class Day25Test extends DayTest {

    private final Day day = new Day25();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("Begin in state A.",
                        "Perform a diagnostic checksum after 6 steps.",
                        "",
                        "In state A:",
                        "  If the current value is 0:",
                        "    - Write the value 1.",
                        "    - Move one slot to the right.",
                        "    - Continue with state B.",
                        "  If the current value is 1:",
                        "    - Write the value 0.",
                        "    - Move one slot to the left.",
                        "    - Continue with state B.",
                        "",
                        "In state B:",
                        "  If the current value is 0:",
                        "    - Write the value 1.",
                        "    - Move one slot to the left.",
                        "    - Continue with state A.",
                        "  If the current value is 1:",
                        "    - Write the value 1.",
                        "    - Move one slot to the right.",
                        "    - Continue with state A."), 3, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("Nothing to see here"), "There is no puzzle! :-)", null)
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