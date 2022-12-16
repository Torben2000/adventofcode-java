package de.beachboys.aoc2022;

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
                Arguments.of(List.of("Valve AA has flow rate=0; tunnels lead to valves DD, II, BB",
                        "Valve BB has flow rate=13; tunnels lead to valves CC, AA",
                        "Valve CC has flow rate=2; tunnels lead to valves DD, BB",
                        "Valve DD has flow rate=20; tunnels lead to valves CC, AA, EE",
                        "Valve EE has flow rate=3; tunnels lead to valves FF, DD",
                        "Valve FF has flow rate=0; tunnels lead to valves EE, GG",
                        "Valve GG has flow rate=0; tunnels lead to valves FF, HH",
                        "Valve HH has flow rate=22; tunnel leads to valve GG",
                        "Valve II has flow rate=0; tunnels lead to valves AA, JJ",
                        "Valve JJ has flow rate=21; tunnel leads to valve II"), 1651, null)

        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("Valve AA has flow rate=0; tunnels lead to valves DD, II, BB",
                        "Valve BB has flow rate=13; tunnels lead to valves CC, AA",
                        "Valve CC has flow rate=2; tunnels lead to valves DD, BB",
                        "Valve DD has flow rate=20; tunnels lead to valves CC, AA, EE",
                        "Valve EE has flow rate=3; tunnels lead to valves FF, DD",
                        "Valve FF has flow rate=0; tunnels lead to valves EE, GG",
                        "Valve GG has flow rate=0; tunnels lead to valves FF, HH",
                        "Valve HH has flow rate=22; tunnel leads to valve GG",
                        "Valve II has flow rate=0; tunnels lead to valves AA, JJ",
                        "Valve JJ has flow rate=21; tunnel leads to valve II"), 1707, null)

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