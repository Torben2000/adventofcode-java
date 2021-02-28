package de.beachboys.aoc2018;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import de.beachboys.IOHelperForTests;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;

public class Day10Test extends DayTest {

    private final Day day = new Day10();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("position=< 9,  1> velocity=< 0,  2>",
                        "position=< 7,  0> velocity=<-1,  0>",
                        "position=< 3, -2> velocity=<-1,  1>",
                        "position=< 6, 10> velocity=<-2, -1>",
                        "position=< 2, -4> velocity=< 2,  2>",
                        "position=<-6, 10> velocity=< 2, -2>",
                        "position=< 1,  8> velocity=< 1, -1>",
                        "position=< 1,  7> velocity=< 1,  0>",
                        "position=<-3, 11> velocity=< 1, -2>",
                        "position=< 7,  6> velocity=<-1, -1>",
                        "position=<-2,  3> velocity=< 1,  0>",
                        "position=<-4,  3> velocity=< 2,  0>",
                        "position=<10, -3> velocity=<-1,  1>",
                        "position=< 5, 11> velocity=< 1, -2>",
                        "position=< 4,  7> velocity=< 0, -1>",
                        "position=< 8, -2> velocity=< 0,  1>",
                        "position=<15,  0> velocity=<-2,  0>",
                        "position=< 1,  6> velocity=< 1,  0>",
                        "position=< 8,  9> velocity=< 0, -1>",
                        "position=< 3,  3> velocity=<-1,  1>",
                        "position=< 0,  5> velocity=< 0, -1>",
                        "position=<-2,  2> velocity=< 2,  0>",
                        "position=< 5, -2> velocity=< 1,  2>",
                        "position=< 1,  4> velocity=< 2,  1>",
                        "position=<-2,  7> velocity=< 2, -2>",
                        "position=< 3,  6> velocity=<-1, -1>",
                        "position=< 5,  0> velocity=< 1,  0>",
                        "position=<-6,  0> velocity=< 2,  0>",
                        "position=< 5,  9> velocity=< 1, -2>",
                        "position=<14,  7> velocity=<-2,  0>",
                        "position=<-3,  6> velocity=< 2, -1>"), "*   *  ***\n" +
                        "*   *   * \n" +
                        "*   *   * \n" +
                        "*****   * \n" +
                        "*   *   * \n" +
                        "*   *   * \n" +
                        "*   *   * \n" +
                        "*   *  ***\n", new IOHelperForTests(List.of("", "s"), null))
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("position=< 9,  1> velocity=< 0,  2>",
                        "position=< 7,  0> velocity=<-1,  0>",
                        "position=< 3, -2> velocity=<-1,  1>",
                        "position=< 6, 10> velocity=<-2, -1>",
                        "position=< 2, -4> velocity=< 2,  2>",
                        "position=<-6, 10> velocity=< 2, -2>",
                        "position=< 1,  8> velocity=< 1, -1>",
                        "position=< 1,  7> velocity=< 1,  0>",
                        "position=<-3, 11> velocity=< 1, -2>",
                        "position=< 7,  6> velocity=<-1, -1>",
                        "position=<-2,  3> velocity=< 1,  0>",
                        "position=<-4,  3> velocity=< 2,  0>",
                        "position=<10, -3> velocity=<-1,  1>",
                        "position=< 5, 11> velocity=< 1, -2>",
                        "position=< 4,  7> velocity=< 0, -1>",
                        "position=< 8, -2> velocity=< 0,  1>",
                        "position=<15,  0> velocity=<-2,  0>",
                        "position=< 1,  6> velocity=< 1,  0>",
                        "position=< 8,  9> velocity=< 0, -1>",
                        "position=< 3,  3> velocity=<-1,  1>",
                        "position=< 0,  5> velocity=< 0, -1>",
                        "position=<-2,  2> velocity=< 2,  0>",
                        "position=< 5, -2> velocity=< 1,  2>",
                        "position=< 1,  4> velocity=< 2,  1>",
                        "position=<-2,  7> velocity=< 2, -2>",
                        "position=< 3,  6> velocity=<-1, -1>",
                        "position=< 5,  0> velocity=< 1,  0>",
                        "position=<-6,  0> velocity=< 2,  0>",
                        "position=< 5,  9> velocity=< 1, -2>",
                        "position=<14,  7> velocity=<-2,  0>",
                        "position=<-3,  6> velocity=< 2, -1>"), 3, new IOHelperForTests(List.of("", "s"), null))

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