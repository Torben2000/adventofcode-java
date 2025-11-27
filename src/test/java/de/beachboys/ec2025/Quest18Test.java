package de.beachboys.ec2025;

import de.beachboys.IOHelper;
import de.beachboys.Quest;
import de.beachboys.QuestTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

public class Quest18Test extends QuestTest {

    private final Quest quest = new Quest18();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("Plant 1 with thickness 1:",
                        "- free branch with thickness 1",
                        "",
                        "Plant 2 with thickness 1:",
                        "- free branch with thickness 1",
                        "",
                        "Plant 3 with thickness 1:",
                        "- free branch with thickness 1",
                        "",
                        "Plant 4 with thickness 17:",
                        "- branch to Plant 1 with thickness 15",
                        "- branch to Plant 2 with thickness 3",
                        "",
                        "Plant 5 with thickness 24:",
                        "- branch to Plant 2 with thickness 11",
                        "- branch to Plant 3 with thickness 13",
                        "",
                        "Plant 6 with thickness 15:",
                        "- branch to Plant 3 with thickness 14",
                        "",
                        "Plant 7 with thickness 10:",
                        "- branch to Plant 4 with thickness 15",
                        "- branch to Plant 5 with thickness 21",
                        "- branch to Plant 6 with thickness 34"), 774, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("Plant 1 with thickness 1:",
                        "- free branch with thickness 1",
                        "",
                        "Plant 2 with thickness 1:",
                        "- free branch with thickness 1",
                        "",
                        "Plant 3 with thickness 1:",
                        "- free branch with thickness 1",
                        "",
                        "Plant 4 with thickness 10:",
                        "- branch to Plant 1 with thickness -25",
                        "- branch to Plant 2 with thickness 17",
                        "- branch to Plant 3 with thickness 12",
                        "",
                        "Plant 5 with thickness 14:",
                        "- branch to Plant 1 with thickness 14",
                        "- branch to Plant 2 with thickness -26",
                        "- branch to Plant 3 with thickness 15",
                        "",
                        "Plant 6 with thickness 150:",
                        "- branch to Plant 4 with thickness 5",
                        "- branch to Plant 5 with thickness 6",
                        "",
                        "",
                        "1 0 1",
                        "0 0 1",
                        "0 1 1"), 324, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart3() {
        return Stream.of(
                Arguments.of(List.of("Plant 1 with thickness 1:",
                        "- free branch with thickness 1",
                        "",
                        "Plant 2 with thickness 1:",
                        "- free branch with thickness 1",
                        "",
                        "Plant 3 with thickness 1:",
                        "- free branch with thickness 1",
                        "",
                        "Plant 4 with thickness 1:",
                        "- free branch with thickness 1",
                        "",
                        "Plant 5 with thickness 8:",
                        "- branch to Plant 1 with thickness -8",
                        "- branch to Plant 2 with thickness 11",
                        "- branch to Plant 3 with thickness 13",
                        "- branch to Plant 4 with thickness -7",
                        "",
                        "Plant 6 with thickness 7:",
                        "- branch to Plant 1 with thickness 14",
                        "- branch to Plant 2 with thickness -9",
                        "- branch to Plant 3 with thickness 12",
                        "- branch to Plant 4 with thickness 9",
                        "",
                        "Plant 7 with thickness 23:",
                        "- branch to Plant 5 with thickness 17",
                        "- branch to Plant 6 with thickness 18",
                        "",
                        "",
                        "0 1 0 0",
                        "0 1 0 1",
                        "0 1 1 1",
                        "1 1 0 1"), 946, null)
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
