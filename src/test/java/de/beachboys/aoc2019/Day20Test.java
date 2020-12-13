package de.beachboys.aoc2019;

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
                Arguments.of(List.of("         A           ",
                        "         A           ",
                        "  #######.#########  ",
                        "  #######.........#  ",
                        "  #######.#######.#  ",
                        "  #######.#######.#  ",
                        "  #######.#######.#  ",
                        "  #####  B    ###.#  ",
                        "BC...##  C    ###.#  ",
                        "  ##.##       ###.#  ",
                        "  ##...DE  F  ###.#  ",
                        "  #####    G  ###.#  ",
                        "  #########.#####.#  ",
                        "DE..#######...###.#  ",
                        "  #.#########.###.#  ",
                        "FG..#########.....#  ",
                        "  ###########.#####  ",
                        "             Z       ",
                        "             Z       "), 23, null),
                Arguments.of(List.of("                   A               ",
                        "                   A               ",
                        "  #################.#############  ",
                        "  #.#...#...................#.#.#  ",
                        "  #.#.#.###.###.###.#########.#.#  ",
                        "  #.#.#.......#...#.....#.#.#...#  ",
                        "  #.#########.###.#####.#.#.###.#  ",
                        "  #.............#.#.....#.......#  ",
                        "  ###.###########.###.#####.#.#.#  ",
                        "  #.....#        A   C    #.#.#.#  ",
                        "  #######        S   P    #####.#  ",
                        "  #.#...#                 #......VT",
                        "  #.#.#.#                 #.#####  ",
                        "  #...#.#               YN....#.#  ",
                        "  #.###.#                 #####.#  ",
                        "DI....#.#                 #.....#  ",
                        "  #####.#                 #.###.#  ",
                        "ZZ......#               QG....#..AS",
                        "  ###.###                 #######  ",
                        "JO..#.#.#                 #.....#  ",
                        "  #.#.#.#                 ###.#.#  ",
                        "  #...#..DI             BU....#..LF",
                        "  #####.#                 #.#####  ",
                        "YN......#               VT..#....QG",
                        "  #.###.#                 #.###.#  ",
                        "  #.#...#                 #.....#  ",
                        "  ###.###    J L     J    #.#.###  ",
                        "  #.....#    O F     P    #.#...#  ",
                        "  #.###.#####.#.#####.#####.###.#  ",
                        "  #...#.#.#...#.....#.....#.#...#  ",
                        "  #.#####.###.###.#.#.#########.#  ",
                        "  #...#.#.....#...#.#.#.#.....#.#  ",
                        "  #.###.#####.###.###.#.#.#######  ",
                        "  #.#.........#...#.............#  ",
                        "  #########.###.###.#############  ",
                        "           B   J   C               ",
                        "           U   P   P               "), 58, null)

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