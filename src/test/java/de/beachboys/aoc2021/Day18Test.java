package de.beachboys.aoc2021;

import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

public class Day18Test extends DayTest {

    private final Day18 day = new Day18();

    private static Stream<Arguments> provideTestDataForExplode() {
        return Stream.of(
                Arguments.of("[[[[[9,8],1],2],3],4]", "[[[[0,9],2],3],4]"),
                Arguments.of("[7,[6,[5,[4,[3,2]]]]]", "[7,[6,[5,[7,0]]]]"),
                Arguments.of("[[6,[5,[4,[3,2]]]],1]", "[[6,[5,[7,0]]],3]"),
                Arguments.of("[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]", "[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]"),
                Arguments.of("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]", "[[3,[2,[8,0]]],[9,[5,[7,0]]]]"),
                Arguments.of("[[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]", "[[[[0,7],4],[7,[[8,4],9]]],[1,1]]"),
                Arguments.of("[[[[0,7],4],[7,[[8,4],9]]],[1,1]]", "[[[[0,7],4],[15,[0,13]]],[1,1]]"),
                Arguments.of("[[[[0,7],4],[[7,8],[0,[6,7]]]],[1,1]]", "[[[[0,7],4],[[7,8],[6,0]]],[8,1]]")
        );

    }
    @ParameterizedTest
    @MethodSource("provideTestDataForExplode")
    public void testExplode(String snailfishNumber, String expectedSnailfishNumber) {
        Assertions.assertEquals(expectedSnailfishNumber, day.explode(snailfishNumber));
    }

    private static Stream<Arguments> provideTestDataForSplit() {
        return Stream.of(
                Arguments.of("[[[[0,7],4],[15,[0,13]]],[1,1]]", "[[[[0,7],4],[[7,8],[0,13]]],[1,1]]"),
                Arguments.of("[[[[0,7],4],[[7,8],[0,13]]],[1,1]]", "[[[[0,7],4],[[7,8],[0,[6,7]]]],[1,1]]")
        );
    }

    @ParameterizedTest
    @MethodSource("provideTestDataForSplit")
    public void testSplit(String snailfishNumber, String expectedSnailfishNumber) {
        Assertions.assertEquals(expectedSnailfishNumber, day.split(snailfishNumber));
    }

    private static Stream<Arguments> provideTestDataForAdd() {
        return Stream.of(
                Arguments.of(List.of("[[[[4,3],4],4],[7,[[8,4],9]]]",
                        "[1,1]"), "[[[[0,7],4],[[7,8],[6,0]]],[8,1]]"),
                Arguments.of(List.of("[1,1]",
                        "[2,2]",
                        "[3,3]",
                        "[4,4]"), "[[[[1,1],[2,2]],[3,3]],[4,4]]"),
                Arguments.of(List.of("[1,1]",
                        "[2,2]",
                        "[3,3]",
                        "[4,4]",
                        "[5,5]"), "[[[[3,0],[5,3]],[4,4]],[5,5]]"),
                Arguments.of(List.of("[1,1]",
                        "[2,2]",
                        "[3,3]",
                        "[4,4]",
                        "[5,5]",
                        "[6,6]"), "[[[[5,0],[7,4]],[5,5]],[6,6]]"),
                Arguments.of(List.of("[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]",
                        "[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]",
                        "[[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]",
                        "[[[[2,4],7],[6,[0,5]]],[[[6,8],[2,8]],[[2,1],[4,5]]]]",
                        "[7,[5,[[3,8],[1,4]]]]",
                        "[[2,[2,2]],[8,[8,1]]]",
                        "[2,9]",
                        "[1,[[[9,3],9],[[9,0],[0,7]]]]",
                        "[[[5,[7,4]],7],1]",
                        "[[[[4,2],2],6],[8,7]]"), "[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]"),
                Arguments.of(List.of("[[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]",
                        "[[[5,[2,8]],4],[5,[[9,9],0]]]",
                        "[6,[[[6,2],[5,6]],[[7,6],[4,7]]]]",
                        "[[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]",
                        "[[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]",
                        "[[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]",
                        "[[[[5,4],[7,7]],8],[[8,3],8]]",
                        "[[9,3],[[9,9],[6,[4,9]]]]",
                        "[[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]",
                        "[[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]"), "[[[[6,6],[7,6]],[[7,7],[7,0]]],[[[7,7],[7,7]],[[7,8],[9,9]]]]")
        );
    }

    @ParameterizedTest
    @MethodSource("provideTestDataForAdd")
    public void testSumUp(List<String> snailfishNumbers, String expectedSnailfishNumber) {
        Assertions.assertEquals(expectedSnailfishNumber, day.sumUp(snailfishNumbers));
    }

    private static Stream<Arguments> provideTestDataForGetMagnitude() {
        return Stream.of(
                Arguments.of("[[1,2],[[3,4],5]]", 143),
                Arguments.of("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]", 1384),
                Arguments.of("[[[[1,1],[2,2]],[3,3]],[4,4]]", 445),
                Arguments.of("[[[[3,0],[5,3]],[4,4]],[5,5]]", 791),
                Arguments.of("[[[[5,0],[7,4]],[5,5]],[6,6]]", 1137),
                Arguments.of("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]", 3488)
        );
    }

    @ParameterizedTest
    @MethodSource("provideTestDataForGetMagnitude")
    public void testGetMagnitude(String snailFishNumber, int expectedMagnitude) {
        Assertions.assertEquals(expectedMagnitude, day.getMagnitude(snailFishNumber));
    }

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("[[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]",
                        "[[[5,[2,8]],4],[5,[[9,9],0]]]",
                        "[6,[[[6,2],[5,6]],[[7,6],[4,7]]]]",
                        "[[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]",
                        "[[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]",
                        "[[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]",
                        "[[[[5,4],[7,7]],8],[[8,3],8]]",
                        "[[9,3],[[9,9],[6,[4,9]]]]",
                        "[[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]",
                        "[[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]"), 4140, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("[[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]",
                        "[[[5,[2,8]],4],[5,[[9,9],0]]]",
                        "[6,[[[6,2],[5,6]],[[7,6],[4,7]]]]",
                        "[[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]",
                        "[[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]",
                        "[[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]",
                        "[[[[5,4],[7,7]],8],[[8,3],8]]",
                        "[[9,3],[[9,9],[6,[4,9]]]]",
                        "[[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]",
                        "[[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]"), 3993, null)
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