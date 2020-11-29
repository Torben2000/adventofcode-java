package de.beachboys.aoc2019;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class IntcodeComputer {

    public void runLogic(List<Integer> list) {
        int currentIndex = 0;
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
                    return;
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
                    System.out.println("Input needed");
                    String input = in.nextLine();
                    list.set(list.get(currentIndex + 1), Integer.parseInt(input));
                    currentIndex += 2;
                    break;
                case 4:
                    System.out.println("output: " + getValue(list, currentIndex + 1, getMode(modes, 1)));
                    currentIndex += 2;
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

}
