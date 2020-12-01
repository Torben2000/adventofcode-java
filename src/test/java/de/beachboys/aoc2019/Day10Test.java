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

public class Day10Test extends DayTest {

    private final Day day = new Day10();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of(".#..#",
                        ".....",
                        "#####",
                        "....#",
                        "...##"), 8, new IOHelperForTests(null, List.of("Found station: 3,4"))),
                Arguments.of(List.of("......#.#.",
                        "#..#.#....",
                        "..#######.",
                        ".#.#.###..",
                        ".#..#.....",
                        "..#....#.#",
                        "#..#....#.",
                        ".##.#..###",
                        "##...#..#.",
                        ".#....####"), 33, new IOHelperForTests(null, List.of("Found station: 5,8"))),
                Arguments.of(List.of("#.#...#.#.",
                        ".###....#.",
                        ".#....#...",
                        "##.#.#.#.#",
                        "....#.#.#.",
                        ".##..###.#",
                        "..#...##..",
                        "..##....##",
                        "......#...",
                        ".####.###."), 35, new IOHelperForTests(null, List.of("Found station: 1,2"))),
                Arguments.of(List.of(".#..#..###",
                        "####.###.#",
                        "....###.#.",
                        "..###.##.#",
                        "##.##.#.#.",
                        "....###..#",
                        "..#.#..#.#",
                        "#..#.#.###",
                        ".##...##.#",
                        ".....#.#.."), 41, new IOHelperForTests(null, List.of("Found station: 6,3"))),
                Arguments.of(List.of(".#..##.###...#######",
                        "##.############..##.",
                        ".#.######.########.#",
                        ".###.#######.####.#.",
                        "#####.##.#.##.###.##",
                        "..#####..#.#########",
                        "####################",
                        "#.####....###.#.#.##",
                        "##.#################",
                        "#####.##.###..####..",
                        "..######..##.#######",
                        "####.##.####...##..#",
                        ".#####..#.######.###",
                        "##...#.##########...",
                        "#.##########.#######",
                        ".####.#.###.###.#.##",
                        "....##.##.###..#####",
                        ".#.#.###########.###",
                        "#.#.#.#####.####.###",
                        "###.##.####.##.#..##"), 210, new IOHelperForTests(null, List.of("Found station: 11,13")))

        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        List<String> map = List.of(".#..##.###...#######",
                "##.############..##.",
                ".#.######.########.#",
                ".###.#######.####.#.",
                "#####.##.#.##.###.##",
                "..#####..#.#########",
                "####################",
                "#.####....###.#.#.##",
                "##.#################",
                "#####.##.###..####..",
                "..######..##.#######",
                "####.##.####...##..#",
                ".#####..#.######.###",
                "##...#.##########...",
                "#.##########.#######",
                ".####.#.###.###.#.##",
                "....##.##.###..#####",
                ".#.#.###########.###",
                "#.#.#.#####.####.###",
                "###.##.####.##.#..##");
        return Stream.of(
                Arguments.of(map, 1112, new IOHelperForTests(List.of("1"), List.of("Found station: 11,13"))),
                Arguments.of(map, 1201, new IOHelperForTests(List.of("2"), List.of("Found station: 11,13"))),
                Arguments.of(map, 1202, new IOHelperForTests(List.of("3"), List.of("Found station: 11,13"))),
                Arguments.of(map, 1208, new IOHelperForTests(List.of("10"), List.of("Found station: 11,13"))),
                Arguments.of(map, 1600, new IOHelperForTests(List.of("20"), List.of("Found station: 11,13"))),
                Arguments.of(map, 1609, new IOHelperForTests(List.of("50"), List.of("Found station: 11,13"))),
                Arguments.of(map, 1016, new IOHelperForTests(List.of("100"), List.of("Found station: 11,13"))),
                Arguments.of(map, 906, new IOHelperForTests(List.of("199"), List.of("Found station: 11,13"))),
                Arguments.of(map, 802, new IOHelperForTests(List.of("200"), List.of("Found station: 11,13"))),
                Arguments.of(map, 1009, new IOHelperForTests(List.of("201"), List.of("Found station: 11,13"))),
                Arguments.of(map, 1101, new IOHelperForTests(List.of("299"), List.of("Found station: 11,13")))
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