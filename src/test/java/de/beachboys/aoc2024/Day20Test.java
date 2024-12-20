package de.beachboys.aoc2024;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import de.beachboys.IOHelperForTests;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;

public class Day20Test extends DayTest {

    public static final List<String> TEST_MAZE = List.of("###############",
            "#...#...#.....#",
            "#.#.#.#.#.###.#",
            "#S#...#.#.#...#",
            "#######.#.#.###",
            "#######.#.#...#",
            "#######.#.###.#",
            "###..E#...#...#",
            "###.#######.###",
            "#...###...#...#",
            "#.#####.#.###.#",
            "#.#...#.#.#...#",
            "#.#.#.#.#.#.###",
            "#...#...#...###",
            "###############");
    private final Day day = new Day20();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(TEST_MAZE, 1, new IOHelperForTests(List.of("64", "2"), null)),
                Arguments.of(TEST_MAZE, 1+1, new IOHelperForTests(List.of("40", "2"), null)),
                Arguments.of(TEST_MAZE, 1+1+1, new IOHelperForTests(List.of("38", "2"), null)),
                Arguments.of(TEST_MAZE, 1+1+1+1, new IOHelperForTests(List.of("36", "2"), null)),
                Arguments.of(TEST_MAZE, 1+1+1+1+1, new IOHelperForTests(List.of("20", "2"), null)),
                Arguments.of(TEST_MAZE, 1+1+1+1+1+3, new IOHelperForTests(List.of("12", "2"), null)),
                Arguments.of(TEST_MAZE, 1+1+1+1+1+3+2, new IOHelperForTests(List.of("10", "2"), null)),
                Arguments.of(TEST_MAZE, 1+1+1+1+1+3+2+4, new IOHelperForTests(List.of("8", "2"), null)),
                Arguments.of(TEST_MAZE, 1+1+1+1+1+3+2+4+2, new IOHelperForTests(List.of("6", "2"), null)),
                Arguments.of(TEST_MAZE, 1+1+1+1+1+3+2+4+2+14, new IOHelperForTests(List.of("4", "2"), null)),
                Arguments.of(TEST_MAZE, 1+1+1+1+1+3+2+4+2+14+14, new IOHelperForTests(List.of("2", "2"), null))
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(TEST_MAZE, 3, new IOHelperForTests(List.of("76", "20"), null)),
                Arguments.of(TEST_MAZE, 3+4, new IOHelperForTests(List.of("74", "20"), null)),
                Arguments.of(TEST_MAZE, 3+4+22, new IOHelperForTests(List.of("72", "20"), null)),
                Arguments.of(TEST_MAZE, 3+4+22+12, new IOHelperForTests(List.of("70", "20"), null)),
                Arguments.of(TEST_MAZE, 3+4+22+12+14, new IOHelperForTests(List.of("68", "20"), null)),
                Arguments.of(TEST_MAZE, 3+4+22+12+14+12, new IOHelperForTests(List.of("66", "20"), null)),
                Arguments.of(TEST_MAZE, 3+4+22+12+14+12+19, new IOHelperForTests(List.of("64", "20"), null)),
                Arguments.of(TEST_MAZE, 3+4+22+12+14+12+19+20, new IOHelperForTests(List.of("62", "20"), null)),
                Arguments.of(TEST_MAZE, 3+4+22+12+14+12+19+20+23, new IOHelperForTests(List.of("60", "20"), null)),
                Arguments.of(TEST_MAZE, 3+4+22+12+14+12+19+20+23+25, new IOHelperForTests(List.of("58", "20"), null)),
                Arguments.of(TEST_MAZE, 3+4+22+12+14+12+19+20+23+25+39, new IOHelperForTests(List.of("56", "20"), null)),
                Arguments.of(TEST_MAZE, 3+4+22+12+14+12+19+20+23+25+39+29, new IOHelperForTests(List.of("54", "20"), null)),
                Arguments.of(TEST_MAZE, 3+4+22+12+14+12+19+20+23+25+39+29+31, new IOHelperForTests(List.of("52", "20"), null)),
                Arguments.of(TEST_MAZE, 3+4+22+12+14+12+19+20+23+25+39+29+31+32, new IOHelperForTests(List.of("50", "20"), null))

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