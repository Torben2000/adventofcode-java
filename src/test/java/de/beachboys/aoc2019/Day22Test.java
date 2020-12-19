package de.beachboys.aoc2019;

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
                Arguments.of(List.of("deal with increment 7",
                        "deal into new stack",
                        "deal into new stack"), 7, new IOHelperForTests(List.of("10", "1"), null)),
                Arguments.of(List.of("deal with increment 7",
                        "deal into new stack",
                        "deal into new stack"), 5, new IOHelperForTests(List.of("10", "5"), null)),
                Arguments.of(List.of("deal with increment 7",
                        "deal into new stack",
                        "deal into new stack"), 1, new IOHelperForTests(List.of("10", "3"), null)),
                Arguments.of(List.of("cut 6",
                        "deal with increment 7",
                        "deal into new stack"), 8, new IOHelperForTests(List.of("10", "9"), null)),
                Arguments.of(List.of("deal with increment 7",
                        "deal with increment 9",
                        "cut -2"), 3, new IOHelperForTests(List.of("10", "7"), null)),
                Arguments.of(List.of("deal into new stack",
                        "cut -2",
                        "deal with increment 7",
                        "cut 8",
                        "cut -4",
                        "deal with increment 7",
                        "cut 3",
                        "deal with increment 9",
                        "deal with increment 3",
                        "cut -1"), 8, new IOHelperForTests(List.of("10", "3"), null)),
                Arguments.of(List.of("deal into new stack",
                        "cut -2",
                        "deal with increment 7",
                        "cut 8",
                        "cut -4",
                        "deal with increment 7",
                        "cut 3",
                        "deal with increment 9",
                        "deal with increment 3",
                        "cut -1"), 0, new IOHelperForTests(List.of("10", "9"), null))
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("cut -8737",
                        "deal with increment 36",
                        "deal into new stack",
                        "deal with increment 32",
                        "cut -3856",
                        "deal with increment 27",
                        "deal into new stack",
                        "cut 8319",
                        "deal with increment 15",
                        "deal into new stack",
                        "deal with increment 53",
                        "cut 2157",
                        "deal with increment 3",
                        "deal into new stack",
                        "cut 9112",
                        "deal with increment 59",
                        "cut 957",
                        "deal with increment 28",
                        "cut -9423",
                        "deal with increment 51",
                        "deal into new stack",
                        "deal with increment 8",
                        "cut 3168",
                        "deal with increment 16",
                        "cut 6558",
                        "deal with increment 32",
                        "deal into new stack",
                        "cut -8246",
                        "deal with increment 40",
                        "cut 4405",
                        "deal with increment 9",
                        "cut -2225",
                        "deal with increment 36",
                        "cut -5080",
                        "deal with increment 59",
                        "cut -648",
                        "deal with increment 64",
                        "cut -1845",
                        "deal into new stack",
                        "cut -7726",
                        "deal with increment 44",
                        "cut 1015",
                        "deal with increment 12",
                        "cut 960",
                        "deal with increment 30",
                        "deal into new stack",
                        "deal with increment 65",
                        "deal into new stack",
                        "deal with increment 27",
                        "cut 6877",
                        "deal with increment 5",
                        "deal into new stack",
                        "cut -3436",
                        "deal with increment 63",
                        "deal into new stack",
                        "deal with increment 71",
                        "deal into new stack",
                        "deal with increment 7",
                        "cut -9203",
                        "deal with increment 38",
                        "cut 9008",
                        "deal with increment 59",
                        "deal into new stack",
                        "deal with increment 13",
                        "cut 5979",
                        "deal with increment 55",
                        "cut 9483",
                        "deal with increment 65",
                        "cut -9250",
                        "deal with increment 75",
                        "deal into new stack",
                        "cut -1830",
                        "deal with increment 55",
                        "deal into new stack",
                        "deal with increment 67",
                        "cut -8044",
                        "deal into new stack",
                        "cut 8271",
                        "deal with increment 51",
                        "cut 6002",
                        "deal into new stack",
                        "deal with increment 47",
                        "cut 3638",
                        "deal with increment 18",
                        "cut -785",
                        "deal with increment 63",
                        "cut -2460",
                        "deal with increment 25",
                        "cut 5339",
                        "deal with increment 61",
                        "cut -5777",
                        "deal with increment 54",
                        "deal into new stack",
                        "cut 8075",
                        "deal into new stack",
                        "deal with increment 22",
                        "cut 3443",
                        "deal with increment 34",
                        "cut 5193",
                        "deal with increment 3"), 74132511136410L, new IOHelperForTests(List.of("119315717514047", "101741582076661", "2020"), null))
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