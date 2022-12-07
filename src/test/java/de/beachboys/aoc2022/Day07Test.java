package de.beachboys.aoc2022;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;

public class Day07Test extends DayTest {

    private final Day day = new Day07();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("$ cd /",
                        "$ ls",
                        "dir a",
                        "14848514 b.txt",
                        "8504156 c.dat",
                        "dir d",
                        "$ cd a",
                        "$ ls",
                        "dir e",
                        "29116 f",
                        "2557 g",
                        "62596 h.lst",
                        "$ cd e",
                        "$ ls",
                        "584 i",
                        "$ cd ..",
                        "$ cd ..",
                        "$ cd d",
                        "$ ls",
                        "4060174 j",
                        "8033020 d.log",
                        "5626152 d.ext",
                        "7214296 k"), 95437, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("$ cd /",
                        "$ ls",
                        "dir a",
                        "14848514 b.txt",
                        "8504156 c.dat",
                        "dir d",
                        "$ cd a",
                        "$ ls",
                        "dir e",
                        "29116 f",
                        "2557 g",
                        "62596 h.lst",
                        "$ cd e",
                        "$ ls",
                        "584 i",
                        "$ cd ..",
                        "$ cd ..",
                        "$ cd d",
                        "$ ls",
                        "4060174 j",
                        "8033020 d.log",
                        "5626152 d.ext",
                        "7214296 k"), 24933642, null)
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