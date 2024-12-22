package de.beachboys.aoc2024;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import de.beachboys.IOHelperForTests;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;

public class Day22Test extends DayTest {

    private final Day day = new Day22();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("123"), 15887950, new IOHelperForTests(List.of("1"), null)),
                Arguments.of(List.of("123"), 16495136, new IOHelperForTests(List.of("2"), null)),
                Arguments.of(List.of("123"), 527345, new IOHelperForTests(List.of("3"), null)),
                Arguments.of(List.of("123"), 704524, new IOHelperForTests(List.of("4"), null)),
                Arguments.of(List.of("123"), 1553684, new IOHelperForTests(List.of("5"), null)),
                Arguments.of(List.of("123"), 12683156, new IOHelperForTests(List.of("6"), null)),
                Arguments.of(List.of("123"), 11100544, new IOHelperForTests(List.of("7"), null)),
                Arguments.of(List.of("123"), 12249484, new IOHelperForTests(List.of("8"), null)),
                Arguments.of(List.of("123"), 7753432, new IOHelperForTests(List.of("9"), null)),
                Arguments.of(List.of("123"), 5908254, new IOHelperForTests(List.of("10"), null)),
                Arguments.of(List.of("1"), 8685429, new IOHelperForTests(List.of("2000"), null)),
                Arguments.of(List.of("10"), 4700978, new IOHelperForTests(List.of("2000"), null)),
                Arguments.of(List.of("100"), 15273692, new IOHelperForTests(List.of("2000"), null)),
                Arguments.of(List.of("2024"), 8667524, new IOHelperForTests(List.of("2000"), null)),
                Arguments.of(List.of("1",
                        "10",
                        "100",
                        "2024"), 37327623, new IOHelperForTests(List.of("2000"), null))
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("123"), 6, new IOHelperForTests(List.of("10"), null)),
                Arguments.of(List.of("1",
                        "2",
                        "3",
                        "2024"), 23, new IOHelperForTests(List.of("2000"), null))
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