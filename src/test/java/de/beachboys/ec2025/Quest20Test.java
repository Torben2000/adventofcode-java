package de.beachboys.ec2025;

import de.beachboys.IOHelper;
import de.beachboys.Quest;
import de.beachboys.QuestTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

public class Quest20Test extends QuestTest {

    private final Quest quest = new Quest20();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("T#TTT###T##",
                        ".##TT#TT##.",
                        "..T###T#T..",
                        "...##TT#...",
                        "....T##....",
                        ".....#....."), 7, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("TTTTTTTTTTTTTTTTT",
                        ".TTTT#T#T#TTTTTT.",
                        "..TT#TTTETT#TTT..",
                        "...TT#T#TTT#TT...",
                        "....TTT#T#TTT....",
                        ".....TTTTTT#.....",
                        "......TT#TT......",
                        ".......#TT.......",
                        "........S........"), 32, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart3() {
        return Stream.of(
                Arguments.of(List.of("T####T#TTT##T##T#T#",
                        ".T#####TTTT##TTT##.",
                        "..TTTT#T###TTTT#T..",
                        "...T#TTT#ETTTT##...",
                        "....#TT##T#T##T....",
                        ".....#TT####T#.....",
                        "......T#TT#T#......",
                        ".......T#TTT.......",
                        "........TT#........",
                        ".........S........."), 23, null)
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
