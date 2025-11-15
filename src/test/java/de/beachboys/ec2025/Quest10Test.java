package de.beachboys.ec2025;

import de.beachboys.IOHelper;
import de.beachboys.IOHelperForTests;
import de.beachboys.Quest;
import de.beachboys.QuestTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

public class Quest10Test extends QuestTest {

    private final Quest quest = new Quest10();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("...SSS.......",
                        ".S......S.SS.",
                        "..S....S...S.",
                        "..........SS.",
                        "..SSSS...S...",
                        ".....SS..S..S",
                        "SS....D.S....",
                        "S.S..S..S....",
                        "....S.......S",
                        ".SSS..SS.....",
                        ".........S...",
                        ".......S....S",
                        "SS.....S..S.."), 27, new IOHelperForTests(List.of("3"), null))

        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("...SSS##.....",
                        ".S#.##..S#SS.",
                        "..S.##.S#..S.",
                        ".#..#S##..SS.",
                        "..SSSS.#.S.#.",
                        ".##..SS.#S.#S",
                        "SS##.#D.S.#..",
                        "S.S..S..S###.",
                        ".##.S#.#....S",
                        ".SSS.#SS..##.",
                        "..#.##...S##.",
                        ".#...#.S#...S",
                        "SS...#.S.#S.."), 27, new IOHelperForTests(List.of("3"), null))

        );
    }

    private static Stream<Arguments> provideTestDataForPart3() {
        return Stream.of(
                Arguments.of(List.of("SSS",
                        "..#",
                        "#.#",
                        "#D."), 15, null),
                Arguments.of(List.of("SSS",
                        "..#",
                        "..#",
                        ".##",
                        ".D#"), 8, null),
                Arguments.of(List.of("..S..",
                        ".....",
                        "..#..",
                        ".....",
                        "..D.."), 44, null),
                Arguments.of(List.of(".SS.S",
                        "#...#",
                        "...#.",
                        "##..#",
                        ".####",
                        "##D.#"), 4406, null),
                Arguments.of(List.of("SSS.S",
                        ".....",
                        "#.#.#",
                        ".#.#.",
                        "#.D.#"), 13033988838L, null)

        );
    }

    @ParameterizedTest
    @MethodSource("provideTestDataForPart1")
    public void testPart1(List<String> input, Object expected, IOHelper io) {
        testPart1(this.quest, input, expected, io);
    }

    @ParameterizedTest
    @MethodSource("provideTestDataForPart2")
    public void testPart2(List<String> input, Object expected, IOHelper io) {
        testPart2(this.quest, input, expected, io);
    }

    @ParameterizedTest
    @MethodSource("provideTestDataForPart3")
    public void testPart3(List<String> input, Object expected, IOHelper io) {
        testPart3(this.quest, input, expected, io);
    }

}
