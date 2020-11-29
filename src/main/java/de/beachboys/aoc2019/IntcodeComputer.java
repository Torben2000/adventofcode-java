package de.beachboys.aoc2019;

import de.beachboys.IOHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class IntcodeComputer {

    private int lastOutput = 0;

    public int runLogic(List<Integer> list, IOHelper io) {
        return runLogic(list, io, false, 0);
    }

    public int runLogic(List<Integer> list, IOHelper io, boolean loopMode, int startIndex) {
        int currentIndex = startIndex;
        int opcode;
        while(true) {
            opcode = list.get(currentIndex);
            List<Integer> modes = new ArrayList<>();
            modes.add(opcode % 100);
            opcode = opcode /100;
            while (opcode != 0) {
                modes.add(opcode % 10);
                opcode = opcode / 10;
            }
            switch (modes.get(0)) {
                case 99:
                    return -1;
                case 1:
                    list.set(list.get(currentIndex + 3), getValue(list, currentIndex + 1, getMode(modes, 1)) + getValue(list, currentIndex + 2, getMode(modes, 2)));
                    currentIndex += 4;
                    break;
                case 2:
                    list.set(list.get(currentIndex + 3), getValue(list, currentIndex + 1, getMode(modes, 1)) * getValue(list, currentIndex + 2, getMode(modes, 2)));
                    currentIndex += 4;
                    break;
                case 3:
                    Scanner in = new Scanner(System.in);
                    list.set(list.get(currentIndex + 1), Integer.parseInt(io.getInput("Input needed")));
                    currentIndex += 2;
                    break;
                case 4:
                    io.logDebug("output:");
                    int output = getValue(list, currentIndex + 1, getMode(modes, 1));
                    io.logInfo(output);
                    this.lastOutput = output;
                    currentIndex += 2;
                    if (loopMode) {
                        return currentIndex;
                    }
                    break;
                case 5:
                    if (getValue(list, currentIndex + 1, getMode(modes, 1)) != 0) {
                        currentIndex = getValue(list, currentIndex + 2, getMode(modes, 2));
                    } else {
                        currentIndex += 3;
                    }
                    break;
                case 6:
                    if (getValue(list, currentIndex + 1, getMode(modes, 1)) == 0) {
                        currentIndex = getValue(list, currentIndex + 2, getMode(modes, 2));
                    } else {
                        currentIndex += 3;
                    }
                    break;
                case 7:
                    if (getValue(list, currentIndex + 1, getMode(modes, 1)) < getValue(list, currentIndex + 2, getMode(modes, 2))) {
                        list.set(list.get(currentIndex + 3), 1);
                    } else {
                        list.set(list.get(currentIndex + 3), 0);
                    }
                    currentIndex += 4;
                    break;
                case 8:
                    if (getValue(list, currentIndex + 1, getMode(modes, 1)) == getValue(list, currentIndex + 2, getMode(modes, 2))) {
                        list.set(list.get(currentIndex + 3), 1);
                    } else {
                        list.set(list.get(currentIndex + 3), 0);
                    }
                    currentIndex += 4;
                    break;
                default:
            }
        }
    }

    private int getMode(List<Integer> modes, int index) {
        return modes.size() > index ? modes.get(index) : 0;
    }

    private int getValue(List<Integer> list, int index, int mode) {
        if (mode == 0) {
            return list.get(list.get(index));
        } else {
            return list.get(index);
        }
    }

    public int getLastOutput() {
        return lastOutput;
    }
}
