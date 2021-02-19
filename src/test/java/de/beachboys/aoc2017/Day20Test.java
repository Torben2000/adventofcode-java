package de.beachboys.aoc2017;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;

public class Day20Test extends DayTest {

    private final Day day = new Day20();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("p=<3,0,0>, v=<2,0,0>, a=<-1,0,0>",
                        "p=<4,0,0>, v=<0,0,0>, a=<-2,0,0>"), 0, null)

        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("p=<-6,0,0>, v=<3,0,0>, a=<0,0,0>",
                        "p=<-4,0,0>, v=<2,0,0>, a=<0,0,0>",
                        "p=<-2,0,0>, v=<1,0,0>, a=<0,0,0>",
                        "p=<3,0,0>, v=<-1,0,0>, a=<0,0,0>"), 1, null)

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