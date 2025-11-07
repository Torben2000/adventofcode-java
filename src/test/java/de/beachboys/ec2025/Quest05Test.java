package de.beachboys.ec2025;

import de.beachboys.IOHelper;
import de.beachboys.Quest;
import de.beachboys.QuestTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

public class Quest05Test extends QuestTest {

    private final Quest quest = new Quest05();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("58:5,3,7,8,9,10,4,5,7,8,8"), "581078", null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("1:2,4,1,1,8,2,7,9,8,6",
                        "2:7,9,9,3,8,3,8,8,6,8",
                        "3:4,7,6,9,1,8,3,7,2,2",
                        "4:6,4,2,1,7,4,5,5,5,8",
                        "5:2,9,3,8,3,9,5,2,1,4",
                        "6:2,4,9,6,7,4,1,7,6,8",
                        "7:2,3,7,6,2,2,4,1,4,2",
                        "8:5,1,5,6,8,3,1,8,3,9",
                        "9:5,7,7,3,7,2,3,8,6,7",
                        "10:4,1,9,3,8,5,4,3,5,5"), 77053, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart3() {
        return Stream.of(
                Arguments.of(List.of("1:7,1,9,1,6,9,8,3,7,2",
                        "2:6,1,9,2,9,8,8,4,3,1",
                        "3:7,1,9,1,6,9,8,3,8,3",
                        "4:6,1,9,2,8,8,8,4,3,1",
                        "5:7,1,9,1,6,9,8,3,7,3",
                        "6:6,1,9,2,8,8,8,4,3,5",
                        "7:3,7,2,2,7,4,4,6,3,1",
                        "8:3,7,2,2,7,4,4,6,3,7",
                        "9:3,7,2,2,7,4,1,6,3,7"), 260, null),
                Arguments.of(List.of("1:7,1,9,1,6,9,8,3,7,2",
                        "2:7,1,9,1,6,9,8,3,7,2"), 4, null)
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
