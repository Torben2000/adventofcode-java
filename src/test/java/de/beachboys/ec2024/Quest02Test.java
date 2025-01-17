package de.beachboys.ec2024;

import de.beachboys.IOHelper;
import de.beachboys.Quest;
import de.beachboys.QuestTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

public class Quest02Test extends QuestTest {

    private final Quest quest = new Quest02();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("WORDS:THE,OWE,MES,ROD,HER",
                        "",
                        "AWAKEN THE POWER ADORNED WITH THE FLAMES BRIGHT IRE"), 4, null),
                Arguments.of(List.of("WORDS:THE,OWE,MES,ROD,HER",
                        "",
                        "THE FLAME SHIELDED THE HEART OF THE KINGS"), 3, null),
                Arguments.of(List.of("WORDS:THE,OWE,MES,ROD,HER",
                        "",
                        "POWE PO WER P OWE R"), 2, null),
                Arguments.of(List.of("WORDS:THE,OWE,MES,ROD,HER",
                        "",
                        "THERE IS THE END"), 3, null)

        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("WORDS:THE,OWE,MES,ROD,HER,QAQ",
                        "",
                        "AWAKEN THE POWE ADORNED WITH THE FLAMES BRIGHT IRE",
                        "THE FLAME SHIELDED THE HEART OF THE KINGS",
                        "POWE PO WER P OWE R",
                        "THERE IS THE END",
                        "QAQAQ"), 42, null)

        );
    }

    private static Stream<Arguments> provideTestDataForPart3() {
        return Stream.of(
                Arguments.of(List.of("WORDS:THE,OWE,MES,ROD,RODEO",
                        "",
                        "HELWORLT",
                        "ENIGWDXL",
                        "TRODEOAL"), 10, null)

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
