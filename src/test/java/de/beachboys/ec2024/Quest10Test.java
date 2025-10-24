package de.beachboys.ec2024;

import de.beachboys.IOHelper;
import de.beachboys.Quest;
import de.beachboys.QuestTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

public class Quest10Test extends QuestTest {

    private final Quest quest = new Quest10();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("**PCBS**",
                        "**RLNW**",
                        "BV....PT",
                        "CR....HZ",
                        "FL....JW",
                        "SG....MN",
                        "**FTZV**",
                        "**GMJH**"), "PTBVRCZHFLJWGMNS", null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("**PCBS**",
                        "**RLNW**",
                        "BV....PT",
                        "CR....HZ",
                        "FL....JW",
                        "SG....MN",
                        "**FTZV**",
                        "**GMJH**"), 1851, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart3() {
        return Stream.of(
                Arguments.of(List.of("**XFZB**DCST**",
                        "**LWQK**GQJH**",
                        "?G....WL....DQ",
                        "BS....H?....CN",
                        "P?....KJ....TV",
                        "NM....Z?....SG",
                        "**NSHM**VKWZ**",
                        "**PJGV**XFNL**",
                        "WQ....?L....YS",
                        "FX....DJ....HV",
                        "?Y....WM....?J",
                        "TJ....YK....LP",
                        "**XRTK**BMSP**",
                        "**DWZN**GCJV**"), 3889, null)
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
