package de.beachboys.ec2025;

import de.beachboys.IOHelper;
import de.beachboys.Quest;
import de.beachboys.QuestTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

public class Quest04Test extends QuestTest {

    private final Quest quest = new Quest04();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("128",
                        "64",
                        "32",
                        "16",
                        "8"), 32400, null),
                Arguments.of(List.of("102",
                        "75",
                        "50",
                        "35",
                        "13"), 15888, null)
       );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("128",
                        "64",
                        "32",
                        "16",
                        "8"), 625000000000L, null),
                Arguments.of(List.of("102",
                        "75",
                        "50",
                        "35",
                        "13"), 1274509803922L, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart3() {
        return Stream.of(
                Arguments.of(List.of("5",
                        "5|10",
                        "10|20",
                        "5"), 400, null),
                Arguments.of(List.of("5",
                        "7|21",
                        "18|36",
                        "27|27",
                        "10|50",
                        "10|50",
                        "11"), 6818, null)
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
