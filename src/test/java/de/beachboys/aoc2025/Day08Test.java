package de.beachboys.aoc2025;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import de.beachboys.IOHelperForTests;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;

public class Day08Test extends DayTest {

    private final Day day = new Day08();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("162,817,812",
                        "57,618,57",
                        "906,360,560",
                        "592,479,940",
                        "352,342,300",
                        "466,668,158",
                        "542,29,236",
                        "431,825,988",
                        "739,650,466",
                        "52,470,668",
                        "216,146,977",
                        "819,987,18",
                        "117,168,530",
                        "805,96,715",
                        "346,949,466",
                        "970,615,88",
                        "941,993,340",
                        "862,61,35",
                        "984,92,344",
                        "425,690,689"), 40, new IOHelperForTests(List.of("10"), null))
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("162,817,812",
                        "57,618,57",
                        "906,360,560",
                        "592,479,940",
                        "352,342,300",
                        "466,668,158",
                        "542,29,236",
                        "431,825,988",
                        "739,650,466",
                        "52,470,668",
                        "216,146,977",
                        "819,987,18",
                        "117,168,530",
                        "805,96,715",
                        "346,949,466",
                        "970,615,88",
                        "941,993,340",
                        "862,61,35",
                        "984,92,344",
                        "425,690,689"), 25272, null)
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