package de.beachboys.aoc2016;

import de.beachboys.Day;

import java.util.List;

public class Day23 extends Day {

    public Object part1(List<String> input) {
        return runLogic(input, 7);
    }

    public Object part2(List<String> input) {
        return runLogic(input, 12);
    }

    private int runLogic(List<String> input, int initialValueA) {
        AssembunnyInterpreter interpreter = new AssembunnyInterpreter();
        interpreter.setValueToRegister(AssembunnyInterpreter.Register.A, initialValueA);
        interpreter.runProgram(input, io);
        return interpreter.getValueFromRegister(AssembunnyInterpreter.Register.A);
    }

}
