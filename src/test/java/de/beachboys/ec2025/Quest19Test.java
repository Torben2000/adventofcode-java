package de.beachboys.ec2025;

import de.beachboys.IOHelper;
import de.beachboys.Quest;
import de.beachboys.QuestTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

public class Quest19Test extends QuestTest {

    private final Quest quest = new Quest19();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("7,7,2",
                        "12,0,4",
                        "15,5,3",
                        "24,1,6",
                        "28,5,5",
                        "40,8,2"), 24, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("7,7,2",
                        "7,1,3",
                        "12,0,4",
                        "15,5,3",
                        "24,1,6",
                        "28,5,5",
                        "40,3,3",
                        "40,8,2"), 22, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart3() {
        return Stream.of(
                Arguments.of(List.of("7,7,2",
                        "7,1,3",
                        "12,0,4",
                        "15,5,3",
                        "24,1,6",
                        "28,5,5",
                        "40,3,3",
                        "40,8,2"), 22, null)
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
