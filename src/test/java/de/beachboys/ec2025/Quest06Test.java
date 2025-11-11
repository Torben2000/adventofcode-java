package de.beachboys.ec2025;

import de.beachboys.IOHelper;
import de.beachboys.IOHelperForTests;
import de.beachboys.Quest;
import de.beachboys.QuestTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

public class Quest06Test extends QuestTest {

    private final Quest quest = new Quest06();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("ABabACacBCbca"), 5, null)

        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("ABabACacBCbca"), 11, null)

        );
    }

    private static Stream<Arguments> provideTestDataForPart3() {
        return Stream.of(
                Arguments.of(List.of("AABCBABCABCabcabcABCCBAACBCa"), 34, new IOHelperForTests(List.of("1", "10"), null)),
                Arguments.of(List.of("AABCBABCABCabcabcABCCBAACBCa"), 72, new IOHelperForTests(List.of("2", "10"), null)),
                Arguments.of(List.of("AABCBABCABCabcabcABCCBAACBCa"), 3442321, new IOHelperForTests(List.of("1000", "1000"), null))
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
