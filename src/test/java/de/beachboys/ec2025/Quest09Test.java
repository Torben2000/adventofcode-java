package de.beachboys.ec2025;

import de.beachboys.IOHelper;
import de.beachboys.Quest;
import de.beachboys.QuestTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

public class Quest09Test extends QuestTest {

    private final Quest quest = new Quest09();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("1:CAAGCGCTAAGTTCGCTGGATGTGTGCCCGCG",
                        "2:CTTGAATTGGGCCGTTTACCTGGTTTAACCAT",
                        "3:CTAGCGCTGAGCTGGCTGCCTGGTTGACCGCG"), 414, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("1:GCAGGCGAGTATGATACCCGGCTAGCCACCCC",
                        "2:TCTCGCGAGGATATTACTGGGCCAGACCCCCC",
                        "3:GGTGGAACATTCGAAAGTTGCATAGGGTGGTG",
                        "4:GCTCGCGAGTATATTACCGAACCAGCCCCTCA",
                        "5:GCAGCTTAGTATGACCGCCAAATCGCGACTCA",
                        "6:AGTGGAACCTTGGATAGTCTCATATAGCGGCA",
                        "7:GGCGTAATAATCGGATGCTGCAGAGGCTGCTG"), 1245, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart3() {
        return Stream.of(
                Arguments.of(List.of("1:GCAGGCGAGTATGATACCCGGCTAGCCACCCC",
                        "2:TCTCGCGAGGATATTACTGGGCCAGACCCCCC",
                        "3:GGTGGAACATTCGAAAGTTGCATAGGGTGGTG",
                        "4:GCTCGCGAGTATATTACCGAACCAGCCCCTCA",
                        "5:GCAGCTTAGTATGACCGCCAAATCGCGACTCA",
                        "6:AGTGGAACCTTGGATAGTCTCATATAGCGGCA",
                        "7:GGCGTAATAATCGGATGCTGCAGAGGCTGCTG"), 12, null),
                Arguments.of(List.of("1:GCAGGCGAGTATGATACCCGGCTAGCCACCCC",
                        "2:TCTCGCGAGGATATTACTGGGCCAGACCCCCC",
                        "3:GGTGGAACATTCGAAAGTTGCATAGGGTGGTG",
                        "4:GCTCGCGAGTATATTACCGAACCAGCCCCTCA",
                        "5:GCAGCTTAGTATGACCGCCAAATCGCGACTCA",
                        "6:AGTGGAACCTTGGATAGTCTCATATAGCGGCA",
                        "7:GGCGTAATAATCGGATGCTGCAGAGGCTGCTG",
                        "8:GGCGTAAAGTATGGATGCTGGCTAGGCACCCG"), 36, null)
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
