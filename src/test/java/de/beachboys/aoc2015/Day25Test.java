package de.beachboys.aoc2015;

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
                Arguments.of(List.of("To continue, please consult the code grid in the manual. Enter the code at row 6, column 6."), 27995004, null),
                Arguments.of(List.of("To continue, please consult the code grid in the manual. Enter the code at row 3, column 6."), 16474243, null),
                Arguments.of(List.of("To continue, please consult the code grid in the manual. Enter the code at row 6, column 3."), 25397450, null),
                Arguments.of(List.of("To continue, please consult the code grid in the manual. Enter the code at row 4, column 5."), 10600672, null),
                Arguments.of(List.of("To continue, please consult the code grid in the manual. Enter the code at row 2, column 6."), 4041754, null)
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