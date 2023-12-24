package de.beachboys.aoc2019;

import de.beachboys.Day;
import de.beachboys.IOHelper;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day19 extends Day {

    private final Map<Tuple2<Integer, Integer>, String> imageMap = new HashMap<>();

    private final IntcodeComputer computer = new IntcodeComputer();

    private List<Long> program;

    private final IOHelper computerIo = buildCustomIo();

    private int inputCounterX = 0;

    private int inputCounterY = 0;

    private boolean returnX = true;

    public Object part1(List<String> input) {
        initComputer(input);

        int startOfBeam = 0;
        for (int j = 0; j < 50; j++) {
            boolean beamFoundInLine = false;
            for (int i = startOfBeam; i < 50; i++) {
                boolean inBeam = isInBeam(j, i);
                imageMap.put(Tuple.tuple(i, j), inBeam ? "#" : ".");
                if (!beamFoundInLine && inBeam) {
                    beamFoundInLine = true;
                    startOfBeam = i;
                } else if (beamFoundInLine && !inBeam) {
                    break;
                }
            }
        }
        io.logDebug(Util.paintMap(imageMap));
        return imageMap.values().stream().filter("#"::equals).count();
    }

    public Object part2(List<String> input) {
        initComputer(input);

        int shipSize = 100;
        // initially set to 1000 because it is close to the real result and thus fast (when running first, set it lower)
        int leftOfBeamLine = 1000;
        int leftOfBeamCol = leftOfBeamLine / 2;
        do {
            leftOfBeamLine++;
            while (!isInBeam(leftOfBeamLine, leftOfBeamCol)) {
                leftOfBeamCol++;
            }
        } while (!isInBeam(leftOfBeamLine - shipSize + 1, leftOfBeamCol + shipSize - 1));

        return leftOfBeamCol * 10000 + (leftOfBeamLine - shipSize + 1);
    }

    private boolean isInBeam(int lineToCheck, int colToCheck) {
        inputCounterX = colToCheck;
        inputCounterY = lineToCheck;
        runComputer(program, computerIo);
        return computer.getLastOutput() == 1;
    }

    private void runComputer(List<Long> list, IOHelper io) {
        computer.runLogic(new ArrayList<>(list), io);
    }

    private void initComputer(List<String> input) {
        program = Util.parseLongCsv(input.getFirst());
    }

    private IOHelper buildCustomIo() {
        return new IOHelper() {

            @Override
            public String getInput(String textToDisplay) {
                int returnValue = returnX ? inputCounterX : inputCounterY;
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
}
