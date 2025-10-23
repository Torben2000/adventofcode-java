package de.beachboys.ec2024;

import de.beachboys.IOHelper;
import de.beachboys.Quest;
import de.beachboys.QuestTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

public class Quest14Test extends QuestTest {

    private final Quest quest = new Quest14();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("U5,R3,D2,L5,U4,R5,D2"), 7, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("U5,R3,D2,L5,U4,R5,D2",
                        "U6,L1,D2,R3,U2,L1"), 32, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart3() {
        return Stream.of(
                Arguments.of(List.of("U5,R3,D2,L5,U4,R5,D2",
                        "U6,L1,D2,R3,U2,L1"), 5, null),
                Arguments.of(List.of("U20,L1,B1,L2,B1,R2,L1,F1,U1",
                        "U10,F1,B1,R1,L1,B1,L1,F1,R2,U1",
                        "U30,L2,F1,R1,B1,R1,F2,U1,F1",
                        "U25,R1,L2,B1,U1,R2,F1,L2",
                        "U16,L1,B1,L1,B3,L1,B1,F1"), 46, null)
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
