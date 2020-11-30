package de.beachboys.aoc2019;

import de.beachboys.Day;
import de.beachboys.IOHelper;
import de.beachboys.Util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day07 extends Day {

    private IntcodeComputer computer = new IntcodeComputer();
    private List<String> inputList;
    private int inputIndex = 0;
    private boolean outputSent;

    public Object part1(List<String> input) {
        inputList = new ArrayList<>(List.of("", "", "", "", "", "", "", "", "", "", "", ""));
        inputIndex = 0;
        List<Long> list = Util.parseLongCsv(input.get(0));

        IOHelper io = new IOHelper() {
            @Override
            public String getInput(String textToDisplay) {
                return getCurrentInput();
            }
        };

        long max = 0;
        for (int a1 = 0; a1 < 5; a1++) {
            for (int a2 = 0; a2 < 5; a2++) {
                if (a1 == a2) {
                    continue;
                }
                for (int a3 = 0; a3 < 5; a3++) {
                    if (a1 == a3 || a2 == a3) {
                        continue;
                    }
                    for (int a4 = 0; a4 < 5; a4++) {
                        if (a1 == a4 || a2 == a4 || a3 == a4) {
                            continue;
                        }
                        for (int a5 = 0; a5 < 5; a5++) {
                            if (a1 == a5 || a2 == a5 || a3 == a5 || a4 == a5) {
                                continue;
                            }
                            setInitialInput(a1, a2, a3, a4, a5);

                            for (int i = 0; i < 5; i++) {
                                computer.runLogic(new ArrayList<>(list), io);
                                inputList.set((i + 1) * 2 + 1, "" + computer.getLastOutput());
                            }
                            max = Math.max(max, computer.getLastOutput());
                        }
                    }
                }
            }
        }
        return max;
    }

    private String getCurrentInput() { return inputList.get(inputIndex++);
    }

    public Object part2(List<String> input) {
        inputList = new ArrayList<>(List.of("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""));
        inputIndex = 0;
        List<Long> list = Util.parseLongCsv(input.get(0));

        IOHelper io = new IOHelper() {
            @Override
            public String getInput(String textToDisplay) {
                return getCurrentInput();
            }

        };

        long max = 0;
        for (int a1 = 5; a1 < 10; a1++) {
            for (int a2 = 5; a2 < 10; a2++) {
                if (a1 == a2) {
                    continue;
                }
                for (int a3 = 5; a3 < 10; a3++) {
                    if (a1 == a3 || a2 == a3) {
                        continue;
                    }
                    for (int a4 = 5; a4 < 10; a4++) {
                        if (a1 == a4 || a2 == a4 || a3 == a4) {
                            continue;
                        }
                        for (int a5 = 5; a5 < 10; a5++) {
                            if (a1 == a5 || a2 == a5 || a3 == a5 || a4 == a5) {
                                continue;
                            }
                            setInitialInput(a1, a2, a3, a4, a5);

                            List<List<Long>> inputLists = new ArrayList<>();
                            List<Integer> startIndexes = new ArrayList<>();
                            for (int i = 0; i < 5; i++) {
                                inputLists.add(new ArrayList<>(list));
                                startIndexes.add(0);
                            }


                            boolean hasHalted = false;
                            while (!hasHalted) {
                                for (int i = 0; i < 5; i++) {
                                    int currentPosition = computer.runLogic(inputLists.get(i), io, true, startIndexes.get(i));
                                    if (currentPosition < 0) {
                                        hasHalted = true;
                                    }
                                    startIndexes.set(i, currentPosition);
                                    // first loop is different from others => prepare for both versions
                                    inputList.set((i + 1) * 2 + 1, "" + computer.getLastOutput());
                                    inputList.set(i + 12, "" + computer.getLastOutput());
                                }
                                // first loop is different => put input for others into other area of list
                                inputIndex = 11;
                            }
                            max = Math.max(max, computer.getLastOutput());
                        }
                    }
                }
            }
        }
        return max;
    }

    private void setInitialInput(int a1, int a2, int a3, int a4, int a5) {
        inputList.set(0, a1 + "");
        inputList.set(1, "0");
        inputList.set(2, a2 + "");
        inputList.set(3, "0");
        inputList.set(4, a3 + "");
        inputList.set(5, "0");
        inputList.set(6, a4 + "");
        inputList.set(7, "0");
        inputList.set(8, a5 + "");
        inputList.set(9, "0");
        inputIndex = 0;
    }

}
