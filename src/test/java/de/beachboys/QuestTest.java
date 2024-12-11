package de.beachboys;

import org.junit.jupiter.api.Assertions;

import java.util.List;

public abstract class QuestTest {

    protected void testPart1(Quest quest, List<String> input, Object expected, IOHelper io) {
        if (io != null) {
            quest.io = io;
        }
        String result = quest.part1(input).toString();
        if (expected != null) {
            Assertions.assertEquals(expected.toString(), result);
        }
    }

    protected void testPart2(Quest quest, List<String> input, Object expected, IOHelper io) {
        if (io != null) {
            quest.io = io;
        }
        String result = quest.part2(input).toString();
        if (expected != null) {
            Assertions.assertEquals(expected.toString(), result);
        }
    }

    protected void testPart3(Quest quest, List<String> input, Object expected, IOHelper io) {
        if (io != null) {
            quest.io = io;
        }
        String result = quest.part3(input).toString();
        if (expected != null) {
            Assertions.assertEquals(expected.toString(), result);
        }
    }

}
