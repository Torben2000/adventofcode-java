package de.beachboys.aoc2016;

import de.beachboys.Day;
import de.beachboys.IOHelper;
import de.beachboys.Util;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Day25 extends Day {

    public Object part1(List<String> input) {
        int maxValue = Util.getIntValueFromUser("Max value to try", 10000, io);
        int loopLength = Util.getIntValueFromUser("Loop length to check", 20, io);

        for (int i = 0; i < maxValue; i++) {
            AssembunnyInterpreter interpreter = new AssembunnyInterpreter();
            interpreter.setValueToRegister(AssembunnyInterpreter.Register.A, i);
            final AtomicBoolean successValueHolder = new AtomicBoolean(false);
            IOHelper ioHelper = buildIOHelper(loopLength, interpreter, successValueHolder);
            interpreter.runProgram(input, ioHelper);
            if (successValueHolder.get()) {
                return i;
            }
        }
        return -1;
    }

    private IOHelper buildIOHelper(int loopLength, AssembunnyInterpreter interpreter, AtomicBoolean successValueHolder) {
        return new IOHelper() {

            int nextNumber = 0;
            int counter = 0;

            @Override
            public void logInfo(Object infoText) {
                if (infoText.equals(nextNumber)) {
                    counter++;
                    if (counter >= loopLength) {
                        successValueHolder.set(true);
                        interpreter.setKillSwitch(true);
                    }
                    nextNumber = 0 == nextNumber ? 1 : 0;
                } else {
                    interpreter.setKillSwitch(true);
                }

            }
        };
    }

    public Object part2(List<String> input) {
        return "There is no puzzle! :-)";
    }

}
