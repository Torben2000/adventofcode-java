package de.beachboys.aoc2019;

import de.beachboys.Day;
import de.beachboys.IOHelper;
import de.beachboys.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day25 extends Day {

    private final IntcodeComputer computer = new IntcodeComputer();

    private List<Integer> inputCharacters = List.of();
    private int inputCharacterCounter = 0;

    private final List<String> collectEverythingAndMoveToCheckpoint = List.of("south",
            "take monolith",
            "east",
            "take asterisk",
            "west",
            "north",
            "west",
            "take coin",
            "north",
            "east",
            "take astronaut ice cream",
            "west",
            "south",
            "east",
            "north",
            "north",
            "take mutex",
            "west",
            "take astrolabe",
            "west",
            "take dehydrated water",
            "west",
            "take wreath",
            "east",
            "south",
            "east",
            "north");
    private int collectEverythingAndMoveToCheckpointCounter = 0;

    private final List<String> items = List.of("asterisk",
            "coin",
            "astronaut ice cream",
            "astrolabe",
            "dehydrated water",
            "wreath",
            "mutex",
            "monolith");
    private int tryItemsCounter = 0;
    private int currentItemCounter = 0;

    private boolean monitorOutput = false;
    private String output = "";

    public Object part1(List<String> input) {
        List<Long> list = Util.parseLongCsv(input.getFirst());
        runComputer(list);
        Matcher matcher = Pattern.compile("([0-9]+)").matcher(output);
        String returnValue = "Code not found";
        if (matcher.find()) {
            returnValue = matcher.group();
        }
        return returnValue;
    }

    public Object part2(List<String> input) {
        return "There is no puzzle! :-)";
    }

    private void runComputer(List<Long> list) {
        IOHelper io = new IOHelper() {

            @Override
            public String getInput(String textToDisplay) {
                if (inputCharacters.size() <= inputCharacterCounter) {
                    stopMonitoringOutput();
                    String input;
                    if (collectEverythingAndMoveToCheckpoint.size() > collectEverythingAndMoveToCheckpointCounter) {
                        input = collectEverythingAndMoveToCheckpoint.get(collectEverythingAndMoveToCheckpointCounter++);
                    } else if (tryItemsCounter < Math.pow(2, 8)) {
                        if (currentItemCounter == 8) {
                            input = "north";
                            currentItemCounter = 0;
                            tryItemsCounter++;
                            startMonitoringOutput();
                        } else {
                            input = (tryItemsCounter & 1 << currentItemCounter) > 0 ? "take " + items.get(currentItemCounter) : "drop " + items.get(currentItemCounter);
                            currentItemCounter++;
                        }
                    } else {
                        // just a fallback, should never occur
                        input = Day25.this.io.getInput("");
                    }
                    Day25.this.io.logDebug(input);
                    inputCharacters = (input + "\n").chars().boxed().collect(Collectors.toList());
                    inputCharacterCounter = 0;
                }
                return inputCharacters.get(inputCharacterCounter++).toString();
            }

            @Override
            public void logInfo(Object infoText) {
                int charCode = Integer.parseInt(infoText.toString());
                String string = Character.toString(charCode);
                Day25.this.io.logDebugWithoutLineBreak(string);
                if (monitorOutput) {
                    output += string;
                }
            }

            @Override
            public void logDebug(Object debugText) {
                // do nothing
            }
        };

        computer.runLogic(new ArrayList<>(list), io);
    }

    private void startMonitoringOutput() {
        monitorOutput = true;
        output = "";
    }

    private void stopMonitoringOutput() {
        monitorOutput = false;
    }

}
