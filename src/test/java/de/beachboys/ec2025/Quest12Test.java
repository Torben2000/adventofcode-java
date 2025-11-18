package de.beachboys.ec2025;

import de.beachboys.IOHelper;
import de.beachboys.Quest;
import de.beachboys.QuestTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

public class Quest12Test extends QuestTest {

    private final Quest quest = new Quest12();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("989611",
                        "857782",
                        "746543",
                        "766789"), 16, null)

        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("9589233445",
                        "9679121695",
                        "8469121876",
                        "8352919876",
                        "7342914327",
                        "7234193437",
                        "6789193538",
                        "6781219648",
                        "5691219769",
                        "5443329859"), 58, null)

        );
    }

    private static Stream<Arguments> provideTestDataForPart3() {
        return Stream.of(
                Arguments.of(List.of("5411",
                        "3362",
                        "5235",
                        "3112"), 14, null),
                Arguments.of(List.of("41951111131882511179",
                        "32112222211508122215",
                        "31223333322105122219",
                        "31234444432147511128",
                        "91223333322176021892",
                        "60112222211166431583",
                        "04661111166111111746",
                        "01111119042122222177",
                        "41222108881233333219",
                        "71222127839122222196",
                        "56111026279711111507"), 133, null)

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
