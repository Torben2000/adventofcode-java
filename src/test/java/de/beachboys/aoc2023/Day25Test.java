package de.beachboys.aoc2023;

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
                Arguments.of(List.of("jqt: rhn xhk nvd",
                        "rsh: frs pzl lsr",
                        "xhk: hfx",
                        "cmg: qnr nvd lhk bvb",
                        "rhn: xhk bvb hfx",
                        "bvb: xhk hfx",
                        "pzl: lsr hfx nvd",
                        "qnr: nvd",
                        "ntq: jqt hfx bvb xhk",
                        "nvd: lhk",
                        "lsr: lhk",
                        "rzs: qnr cmg lsr rsh",
                        "frs: qnr lhk lsr"), 54, null)
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