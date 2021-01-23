package de.beachboys.aoc2016;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;

public class Day22Test extends DayTest {

    private final Day day = new Day22();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("Filesystem            Size  Used  Avail  Use%",
                        "/dev/grid/node-x0-y0   10T    8T     2T   80%",
                        "/dev/grid/node-x0-y1   11T    6T     5T   54%",
                        "/dev/grid/node-x0-y2   32T   28T     4T   87%",
                        "/dev/grid/node-x1-y0    9T    7T     2T   77%",
                        "/dev/grid/node-x1-y1    8T    0T     8T    0%",
                        "/dev/grid/node-x1-y2   11T    7T     4T   63%",
                        "/dev/grid/node-x2-y0   10T    6T     4T   60%",
                        "/dev/grid/node-x2-y1    9T    8T     1T   88%",
                        "/dev/grid/node-x2-y2    9T    6T     3T   66%"), 7, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("Filesystem            Size  Used  Avail  Use%",
                        "/dev/grid/node-x0-y0   10T    8T     2T   80%",
                        "/dev/grid/node-x0-y1   11T    6T     5T   54%",
                        "/dev/grid/node-x0-y2   32T   28T     4T   87%",
                        "/dev/grid/node-x1-y0    9T    7T     2T   77%",
                        "/dev/grid/node-x1-y1    8T    0T     8T    0%",
                        "/dev/grid/node-x1-y2   11T    7T     4T   63%",
                        "/dev/grid/node-x2-y0   10T    6T     4T   60%",
                        "/dev/grid/node-x2-y1    9T    8T     1T   88%",
                        "/dev/grid/node-x2-y2    9T    6T     3T   66%"), 7, null)
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