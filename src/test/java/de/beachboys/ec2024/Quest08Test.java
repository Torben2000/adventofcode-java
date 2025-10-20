package de.beachboys.ec2024;

import de.beachboys.IOHelper;
import de.beachboys.IOHelperForTests;
import de.beachboys.Quest;
import de.beachboys.QuestTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

public class Quest08Test extends QuestTest {

    private final Quest quest = new Quest08();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("13"), 21, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("3"), 27, new IOHelperForTests(List.of("5", "50"), null))
        );
    }

    private static Stream<Arguments> provideTestDataForPart3() {
        return Stream.of(
                Arguments.of(List.of("2"), 2, new IOHelperForTests(List.of("5", "160"), null)),
                Arguments.of(List.of("2"), 12, new IOHelperForTests(List.of("5", "150"), null)),
                Arguments.of(List.of("2"), 3, new IOHelperForTests(List.of("5", "16"), null)),
                Arguments.of(List.of("2"), 30, new IOHelperForTests(List.of("5", "37"), null)),
                Arguments.of(List.of("2"), 100, new IOHelperForTests(List.of("5", "391"), null))
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
