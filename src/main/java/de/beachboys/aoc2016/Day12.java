package de.beachboys.aoc2016;

import de.beachboys.Day;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day12 extends Day {

    public Object part1(List<String> input) {
        return runLogic(input, 0);
    }

    public Object part2(List<String> input) {
        return runLogic(input, 1);
    }

    private Integer runLogic(List<String> input, int initialValueC) {
        AssembunnyInterpreter interpreter = new AssembunnyInterpreter();
        interpreter.setValueToRegister(AssembunnyInterpreter.Register.C, initialValueC);
        interpreter.runProgram(input, io);
        return interpreter.getValueFromRegister(AssembunnyInterpreter.Register.A);
    }
}
