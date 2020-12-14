package de.beachboys.aoc2019;

import de.beachboys.Day;
import de.beachboys.IOHelper;
import de.beachboys.Util;
import org.javatuples.Pair;

import java.util.*;

public class Day19 extends Day {

    private final Map<Pair<Integer, Integer>, String> imageMap = new HashMap<>();

    private final IntcodeComputer computer = new IntcodeComputer();

    private int inputCounterX = 0;

    private int inputCounterY = 0;

    private boolean returnX = true;

    public Object part1(List<String> input) {
        IOHelper customIo = buildCustomIo();

        List<Long> list = Util.parseLongCsv(input.get(0));
        for (int j = 0; j < 50; j++) {
            for (int i = 0; i <  50; i++) {
                inputCounterX = i;
                inputCounterY = j;
                runComputer(list, customIo);
                imageMap.put(Pair.with(inputCounterX, inputCounterY), computer.getLastOutput() + "");
            }
        }
        io.logDebug(Util.paintMap(imageMap, Map.of("0", ".", "1", "#")));
        return imageMap.values().stream().filter("1"::equals).count();
    }

    public Object part2(List<String> input) {
        return 2;
    }

    private IOHelper buildCustomIo() {
        return new IOHelper() {

            @Override
            public String getInput(String textToDisplay) {
                long returnValue = returnX ? inputCounterX : inputCounterY;
                returnX = !returnX;
                return "" + returnValue;
            }

            @Override
            public void logDebug(Object debugText) {
                // do nothing
            }

            @Override
            public void logInfo(Object infoText) {
                // do nothing
            }
        };
    }

    private void runComputer(List<Long> list, IOHelper io) {
        computer.runLogic(new ArrayList<>(list), io);
    }
}
