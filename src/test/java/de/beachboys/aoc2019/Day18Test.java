package de.beachboys.aoc2019;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

public class Day18Test extends DayTest {

    private final Day day = new Day18();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("#########",
                        "#b.A.@.a#",
                        "#########"), 8, null),
                Arguments.of(List.of("########################",
                        "#f.D.E.e.C.b.A.@.a.B.c.#",
                        "######################.#",
                        "#d.....................#",
                        "########################"), 86, null),
                Arguments.of(List.of("########################",
                        "#...............b.C.D.f#",
                        "#.######################",
                        "#.....@.a.B.c.d.A.e.F.g#",
                        "########################"), 132, null),
                Arguments.of(List.of("#################",
                        "#i.G..c...e..H.p#",
                        "########.########",
                        "#j.A..b...f..D.o#",
                        "########@########",
                        "#k.E..a...g..B.n#",
                        "########.########",
                        "#l.F..d...h..C.m#",
                        "#################"), 136, null),
                Arguments.of(List.of("########################",
                        "#@..............ac.GI.b#",
                        "###d#e#f################",
                        "###A#B#C################",
                        "###g#h#i################",
                        "########################"), 81, null)

        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("inputlines"), 2, null)

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