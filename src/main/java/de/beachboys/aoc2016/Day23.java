package de.beachboys.aoc2016;

import de.beachboys.Day;

import java.util.List;

public class Day23 extends Day {

    public Object part1(List<String> input) {
        AssembunnyInterpreter interpreter = new AssembunnyInterpreter(input);
        interpreter.setValueToRegister(AssembunnyInterpreter.Register.A, 7);
        interpreter.runProgram();
        return interpreter.getValueFromRegister(AssembunnyInterpreter.Register.A);
    }

    public Object part2(List<String> input) {
        AssembunnyInterpreter interpreter = new AssembunnyInterpreter(input);
        interpreter.setValueToRegister(AssembunnyInterpreter.Register.A, 12);
        interpreter.runProgram();
        return interpreter.getValueFromRegister(AssembunnyInterpreter.Register.A);
    }

}
