package de.beachboys.aoc2019;

import de.beachboys.IOHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class IntcodeComputer {

    private long lastOutput = 0;
    private long relBaseOffset = 0;

    public int runLogic(List<Long> list, IOHelper io) {
        return runLogic(list, io, false, 0);
    }

    public int runLogic(List<Long> list, IOHelper io, boolean loopMode, int startIndex) {
        int currentIndex = startIndex;
        long opcode;
        while(true) {
            opcode = list.get(currentIndex);
            List<Long> modes = new ArrayList<>();
            modes.add(opcode % 100);
            opcode = opcode /100;
            while (opcode != 0) {
                modes.add(opcode % 10);
                opcode = opcode / 10;
            }
            switch (modes.get(0).intValue()) {
                case 99:
                    return -1;
                case 1:
                    setValue(list, currentIndex + 3, getValue(list, currentIndex + 1, getMode(modes, 1)) + getValue(list, currentIndex + 2, getMode(modes, 2)), getMode(modes, 3));
                    currentIndex += 4;
                    break;
                case 2:
                    setValue(list, currentIndex + 3, getValue(list, currentIndex + 1, getMode(modes, 1)) * getValue(list, currentIndex + 2, getMode(modes, 2)), getMode(modes, 3));
                    currentIndex += 4;
                    break;
                case 3:
                    Scanner in = new Scanner(System.in);
                    setValue(list, currentIndex + 1, Long.parseLong(io.getInput("Input needed")), getMode(modes, 1));
                    currentIndex += 2;
                    break;
                case 4:
                    io.logDebug("output:");
                    long output = getValue(list, currentIndex + 1, getMode(modes, 1));
                    io.logInfo(output);
                    this.lastOutput = output;
                    currentIndex += 2;
                    if (loopMode) {
                        return currentIndex;
                    }
                    break;
                case 5:
                    if (getValue(list, currentIndex + 1, getMode(modes, 1)) != 0) {
                        currentIndex = (int) getValue(list, currentIndex + 2, getMode(modes, 2));
                    } else {
                        currentIndex += 3;
                    }
                    break;
                case 6:
                    if (getValue(list, currentIndex + 1, getMode(modes, 1)) == 0) {
                        currentIndex = (int) getValue(list, currentIndex + 2, getMode(modes, 2));
                    } else {
                        currentIndex += 3;
                    }
                    break;
                case 7:
                    if (getValue(list, currentIndex + 1, getMode(modes, 1)) < getValue(list, currentIndex + 2, getMode(modes, 2))) {
                        setValue(list, currentIndex + 3, 1L, getMode(modes, 3));
                    } else {
                        setValue(list, currentIndex + 3, 0L, getMode(modes, 3));
                    }
                    currentIndex += 4;
                    break;
                case 8:
                    if (getValue(list, currentIndex + 1, getMode(modes, 1)) == getValue(list, currentIndex + 2, getMode(modes, 2))) {
                        setValue(list, currentIndex + 3, 1L, getMode(modes, 3));
                    } else {
                        setValue(list, currentIndex + 3, 0L, getMode(modes, 3));
                    }
                    currentIndex += 4;
                    break;
                case 9:
                    relBaseOffset += getValue(list, currentIndex + 1, getMode(modes, 1));
                    currentIndex += 2;
                    break;
                default:
            }
        }
    }

    private void setValue(List<Long> list, int index, long value, long mode) {
        long indexToUse = getValueIndexSafe(list, index);
        if (mode == 2) {
            indexToUse += relBaseOffset;
        }
        if (list.size() <= indexToUse) {
            for (int i=list.size(); i <= indexToUse+1; i++) {
                list.add(0L);
            }
        }
        list.set((int) indexToUse, value);
    }

    private long getMode(List<Long> modes, int index) {
        return modes.size() > index ? modes.get(index) : -1;
    }

    private long getValue(List<Long> list, int index, long mode) {
        if (mode == 0 || mode == -1) {
            return getValueIndexSafe(list, (int) getValueIndexSafe(list, index));
        } else if (mode == 1) {
            return getValueIndexSafe(list, index);
        } else if (mode == 2) {
            return getValueIndexSafe(list, (int) (getValueIndexSafe(list, index) + relBaseOffset));
        } else {
            throw new IllegalArgumentException("unknown mode: " + mode);
        }
    }

    private long getValueIndexSafe(List<Long> list, int index) {
        if (index > list.size()) {
            return 0;
        }
        return list.get(index);
    }

    public long getLastOutput() {
        return lastOutput;
    }
}
