package de.beachboys.aoc2023;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;

public class Day10Test extends DayTest {

    private final Day day = new Day10();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of(".....",
                        ".S-7.",
                        ".|.|.",
                        ".L-J.",
                        "....."), 4, null),
                Arguments.of(List.of("-L|F7",
                        "7S-7|",
                        "L|7||",
                        "-L-J|",
                        "L|-JF"), 4, null),
                Arguments.of(List.of("..F7.",
                        ".FJ|.",
                        "SJ.L7",
                        "|F--J",
                        "LJ..."), 8, null),
                Arguments.of(List.of("7-F7-",
                        ".FJ|7",
                        "SJLL7",
                        "|F--J",
                        "LJ.LJ"), 8, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("...........",
                        ".S-------7.",
                        ".|F-----7|.",
                        ".||.....||.",
                        ".||.....||.",
                        ".|L-7.F-J|.",
                        ".|..|.|..|.",
                        ".L--J.L--J.",
                        "..........."), 4, null),
                Arguments.of(List.of(".F----7F7F7F7F-7....",
                        ".|F--7||||||||FJ....",
                        ".||.FJ||||||||L7....",
                        "FJL7L7LJLJ||LJ.L-7..",
                        "L--J.L7...LJS7F-7L7.",
                        "....F-J..F7FJ|L7L7L7",
                        "....L7.F7||L7|.L7L7|",
                        ".....|FJLJ|FJ|F7|.LJ",
                        "....FJL-7.||.||||...",
                        "....L---J.LJ.LJLJ..."), 8, null),
                Arguments.of(List.of("FF7FSF7F7F7F7F7F---7",
                        "L|LJ||||||||||||F--J",
                        "FL-7LJLJ||||||LJL-77",
                        "F--JF--7||LJLJ7F7FJ-",
                        "L---JF-JLJ.||-FJLJJ7",
                        "|F|F-JF---7F7-L7L|7|",
                        "|FFJF7L7F-JF7|JL---7",
                        "7-L-JL7||F7|L7F-7F7|",
                        "L.L7LFJ|||||FJL7||LJ",
                        "L7JLJL-JLJLJL--JLJ.L"), 10, null)
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