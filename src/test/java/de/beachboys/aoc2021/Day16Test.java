package de.beachboys.aoc2021;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;

public class Day16Test extends DayTest {

    private final Day day = new Day16();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("D2FE28"), 6, null),
                Arguments.of(List.of("38006F45291200"), 9, null),
                Arguments.of(List.of("EE00D40C823060"), 14, null),
                Arguments.of(List.of("8A004A801A8002F478"), 16, null),
                Arguments.of(List.of("620080001611562C8802118E34"), 12, null),
                Arguments.of(List.of("C0015000016115A2E0802F182340"), 23, null),
                Arguments.of(List.of("A0016C880162017C3686B18A3D4780"), 31, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("C200B40A82"), 3, null),
                Arguments.of(List.of("04005AC33890"), 54, null),
                Arguments.of(List.of("880086C3E88112"), 7, null),
                Arguments.of(List.of("CE00C43D881120"), 9, null),
                Arguments.of(List.of("D8005AC2A8F0"), 1, null),
                Arguments.of(List.of("F600BC2D8F"), 0, null),
                Arguments.of(List.of("9C005AC2F8F0"), 0, null),
                Arguments.of(List.of("9C0141080250320F1802104A08"), 1, null)
        );
    }

    @ParameterizedTest
    @MethodSource("provideTestDataForPart1")
    public void testPart1(List<String> input, Object expected, IOHelper io) {
        testPart1(this.day, input, expected, io);
    }

    @ParameterizedTest
    @MethodSource("provideTestDataForPart2")
    public void testPart2(List<String> input, Object expected, IOHelper io) {
        testPart2(this.day, input, expected, io);
    }

}