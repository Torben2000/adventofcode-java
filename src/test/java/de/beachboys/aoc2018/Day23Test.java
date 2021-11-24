package de.beachboys.aoc2018;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;

public class Day23Test extends DayTest {

    private final Day day = new Day23();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("pos=<0,0,0>, r=4",
                        "pos=<1,0,0>, r=1",
                        "pos=<4,0,0>, r=3",
                        "pos=<0,2,0>, r=1",
                        "pos=<0,5,0>, r=3",
                        "pos=<0,0,3>, r=1",
                        "pos=<1,1,1>, r=1",
                        "pos=<1,1,2>, r=1",
                        "pos=<1,3,1>, r=1"), 7, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("pos=<10,12,12>, r=2",
                        "pos=<12,14,12>, r=2",
                        "pos=<16,12,12>, r=4",
                        "pos=<14,14,14>, r=6",
                        "pos=<50,50,50>, r=200",
                        "pos=<10,10,10>, r=5"), 36, null)

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