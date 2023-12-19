package de.beachboys.aoc2023;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;

public class Day19Test extends DayTest {

    private final Day day = new Day19();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("px{a<2006:qkq,m>2090:A,rfg}",
                        "pv{a>1716:R,A}",
                        "lnx{m>1548:A,A}",
                        "rfg{s<537:gd,x>2440:R,A}",
                        "qs{s>3448:A,lnx}",
                        "qkq{x<1416:A,crn}",
                        "crn{x>2662:A,R}",
                        "in{s<1351:px,qqz}",
                        "qqz{s>2770:qs,m<1801:hdj,R}",
                        "gd{a>3333:R,R}",
                        "hdj{m>838:A,pv}",
                        "",
                        "{x=787,m=2655,a=1222,s=2876}",
                        "{x=1679,m=44,a=2067,s=496}",
                        "{x=2036,m=264,a=79,s=2244}",
                        "{x=2461,m=1339,a=466,s=291}",
                        "{x=2127,m=1623,a=2188,s=1013}"), 19114, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("px{a<2006:qkq,m>2090:A,rfg}",
                        "pv{a>1716:R,A}",
                        "lnx{m>1548:A,A}",
                        "rfg{s<537:gd,x>2440:R,A}",
                        "qs{s>3448:A,lnx}",
                        "qkq{x<1416:A,crn}",
                        "crn{x>2662:A,R}",
                        "in{s<1351:px,qqz}",
                        "qqz{s>2770:qs,m<1801:hdj,R}",
                        "gd{a>3333:R,R}",
                        "hdj{m>838:A,pv}",
                        "",
                        "{x=787,m=2655,a=1222,s=2876}",
                        "{x=1679,m=44,a=2067,s=496}",
                        "{x=2036,m=264,a=79,s=2244}",
                        "{x=2461,m=1339,a=466,s=291}",
                        "{x=2127,m=1623,a=2188,s=1013}"), 167409079868000L, null)
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