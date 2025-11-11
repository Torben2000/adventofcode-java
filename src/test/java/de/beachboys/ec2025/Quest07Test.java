package de.beachboys.ec2025;

import de.beachboys.IOHelper;
import de.beachboys.Quest;
import de.beachboys.QuestTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

public class Quest07Test extends QuestTest {

    private final Quest quest = new Quest07();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("Oronris,Urakris,Oroneth,Uraketh",
                        "",
                        "r > a,i,o",
                        "i > p,w",
                        "n > e,r",
                        "o > n,m",
                        "k > f,r",
                        "a > k",
                        "U > r",
                        "e > t",
                        "O > r",
                        "t > h"), "Oroneth", null)

        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("Xanverax,Khargyth,Nexzeth,Helther,Braerex,Tirgryph,Kharverax",
                        "",
                        "r > v,e,a,g,y",
                        "a > e,v,x,r",
                        "e > r,x,v,t",
                        "h > a,e,v",
                        "g > r,y",
                        "y > p,t",
                        "i > v,r",
                        "K > h",
                        "v > e",
                        "B > r",
                        "t > h",
                        "N > e",
                        "p > h",
                        "H > e",
                        "l > t",
                        "z > e",
                        "X > a",
                        "n > v",
                        "x > z",
                        "T > i"), 23, null)

        );
    }

    private static Stream<Arguments> provideTestDataForPart3() {
        return Stream.of(
                Arguments.of(List.of("Xaryt",
                        "",
                        "X > a,o",
                        "a > r,t",
                        "r > y,e,a",
                        "h > a,e,v",
                        "t > h",
                        "v > e",
                        "y > p,t"), 25, null),
                Arguments.of(List.of("Khara,Xaryt,Noxer,Kharax",
                        "",
                        "r > v,e,a,g,y",
                        "a > e,v,x,r,g",
                        "e > r,x,v,t",
                        "h > a,e,v",
                        "g > r,y",
                        "y > p,t",
                        "i > v,r",
                        "K > h",
                        "v > e",
                        "B > r",
                        "t > h",
                        "N > e",
                        "p > h",
                        "H > e",
                        "l > t",
                        "z > e",
                        "X > a",
                        "n > v",
                        "x > z",
                        "T > i"), 1154, null)

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
