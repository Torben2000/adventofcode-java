package de.beachboys.ec2024;

import de.beachboys.IOHelper;
import de.beachboys.IOHelperForTests;
import de.beachboys.Quest;
import de.beachboys.QuestTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

public class Quest20Test extends QuestTest {

    private final Quest quest = new Quest20();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("#....S....#",
                        "#.........#",
                        "#---------#",
                        "#.........#",
                        "#..+.+.+..#",
                        "#.+-.+.++.#",
                        "#.........#"), 1045, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("####S####",
                        "#-.+++.-#",
                        "#.+.+.+.#",
                        "#-.+.+.-#",
                        "#A+.-.+C#",
                        "#.+-.-+.#",
                        "#.+.B.+.#",
                        "#########"), 24, null),
                Arguments.of(List.of("###############S###############",
                        "#+#..-.+.-++.-.+.--+.#+.#++..+#",
                        "#-+-.+-..--..-+++.+-+.#+.-+.+.#",
                        "#---.--+.--..++++++..+.-.#.-..#",
                        "#+-+.#+-.#-..+#.--.--.....-..##",
                        "#..+..-+-.-+.++..-+..+#-.--..-#",
                        "#.--.A.-#-+-.-++++....+..C-...#",
                        "#++...-..+-.+-..+#--..-.-+..-.#",
                        "#..-#-#---..+....#+#-.-.-.-+.-#",
                        "#.-+.#+++.-...+.+-.-..+-++..-.#",
                        "##-+.+--.#.++--...-+.+-#-+---.#",
                        "#.-.#+...#----...+-.++-+-.+#..#",
                        "#.---#--++#.++.+-+.#.--..-.+#+#",
                        "#+.+.+.+.#.---#+..+-..#-...---#",
                        "#-#.-+##+-#.--#-.-......-#..-##",
                        "#...+.-+..##+..+B.+.#-+-++..--#",
                        "###############################"), 78, null),
                Arguments.of(List.of("###############S###############",
                        "#-----------------------------#",
                        "#-------------+++-------------#",
                        "#-------------+++-------------#",
                        "#-------------+++-------------#",
                        "#-----------------------------#",
                        "#-----------------------------#",
                        "#-----------------------------#",
                        "#--A-----------------------C--#",
                        "#-----------------------------#",
                        "#-----------------------------#",
                        "#-----------------------------#",
                        "#-----------------------------#",
                        "#-----------------------------#",
                        "#-----------------------------#",
                        "#--------------B--------------#",
                        "#-----------------------------#",
                        "#-----------------------------#",
                        "###############################"), 206, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart3() {
        return Stream.of(
                Arguments.of(List.of("#......S......#",
                        "#-...+...-...+#",
                        "#.............#",
                        "#..+...-...+..#",
                        "#.............#",
                        "#-...-...+...-#",
                        "#.............#",
                        "#..#...+...+..#"), 1, new IOHelperForTests(List.of("1"), null)),
                Arguments.of(List.of("#......S......#",
                        "#-...+...-...+#",
                        "#.............#",
                        "#..+...-...+..#",
                        "#.............#",
                        "#-...-...+...-#",
                        "#.............#",
                        "#..#...+...+..#"), 2, new IOHelperForTests(List.of("2"), null)),
                Arguments.of(List.of("#......S......#",
                        "#-...+...-...+#",
                        "#.............#",
                        "#..+...-...+..#",
                        "#.............#",
                        "#-...-...+...-#",
                        "#.............#",
                        "#..#...+...+..#"), 3, new IOHelperForTests(List.of("3"), null)),
                Arguments.of(List.of("#......S......#",
                        "#-...+...-...+#",
                        "#.............#",
                        "#..+...-...+..#",
                        "#.............#",
                        "#-...-...+...-#",
                        "#.............#",
                        "#..#...+...+..#"), 4, new IOHelperForTests(List.of("4"), null)),
                Arguments.of(List.of("#......S......#",
                        "#-...+...-...+#",
                        "#.............#",
                        "#..+...-...+..#",
                        "#.............#",
                        "#-...-...+...-#",
                        "#.............#",
                        "#..#...+...+..#"), 5, new IOHelperForTests(List.of("5"), null)),
                Arguments.of(List.of("#......S......#",
                        "#-...+...-...+#",
                        "#.............#",
                        "#..+...-...+..#",
                        "#.............#",
                        "#-...-...+...-#",
                        "#.............#",
                        "#..#...+...+..#"), 6, new IOHelperForTests(List.of("6"), null)),
                Arguments.of(List.of("#......S......#",
                        "#-...+...-...+#",
                        "#.............#",
                        "#..+...-...+..#",
                        "#.............#",
                        "#-...-...+...-#",
                        "#.............#",
                        "#..#...+...+..#"), 7, new IOHelperForTests(List.of("7"), null)),
                Arguments.of(List.of("#......S......#",
                        "#-...+...-...+#",
                        "#.............#",
                        "#..+...-...+..#",
                        "#.............#",
                        "#-...-...+...-#",
                        "#.............#",
                        "#..#...+...+..#"), 9, new IOHelperForTests(List.of("8"), null)),
                Arguments.of(List.of("#......S......#",
                        "#-...+...-...+#",
                        "#.............#",
                        "#..+...-...+..#",
                        "#.............#",
                        "#-...-...+...-#",
                        "#.............#",
                        "#..#...+...+..#"), 10, new IOHelperForTests(List.of("9"), null)),
                Arguments.of(List.of("#......S......#",
                        "#-...+...-...+#",
                        "#.............#",
                        "#..+...-...+..#",
                        "#.............#",
                        "#-...-...+...-#",
                        "#.............#",
                        "#..#...+...+..#"), 11, new IOHelperForTests(List.of("10"), null)),
                Arguments.of(List.of("#......S......#",
                        "#-...+...-...+#",
                        "#.............#",
                        "#..+...-...+..#",
                        "#.............#",
                        "#-...-...+...-#",
                        "#.............#",
                        "#..#...+...+..#"), 190, new IOHelperForTests(List.of("100"), null)),
                Arguments.of(List.of("#......S......#",
                        "#-...+...-...+#",
                        "#.............#",
                        "#..+...-...+..#",
                        "#.............#",
                        "#-...-...+...-#",
                        "#.............#",
                        "#..#...+...+..#"), 1990, new IOHelperForTests(List.of("1000"), null)),
                Arguments.of(List.of("#......S......#",
                        "#-...+...-...+#",
                        "#.............#",
                        "#..+...-...+..#",
                        "#.............#",
                        "#-...-...+...-#",
                        "#.............#",
                        "#..#...+...+..#"), 19990, new IOHelperForTests(List.of("10000"), null)),
                Arguments.of(List.of("#......S......#",
                        "#-...+...-...+#",
                        "#.............#",
                        "#..+...-...+..#",
                        "#.............#",
                        "#-...-...+...-#",
                        "#.............#",
                        "#..#...+...+..#"), 199990, new IOHelperForTests(List.of("100000"), null)),
                Arguments.of(List.of("#......S......#",
                        "#-...+...-...+#",
                        "#.............#",
                        "#..+...-...+..#",
                        "#.............#",
                        "#-...-...+...-#",
                        "#.............#",
                        "#..#...+...+..#"), 768790, new IOHelperForTests(List.of("384400"), null))
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
