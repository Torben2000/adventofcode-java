package de.beachboys.aoc2019;

import de.beachboys.*;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day11 extends Day {

    private final IntcodeComputer computer = new IntcodeComputer();

    private final Map<Tuple2<Integer, Integer>, String> colorMap = new HashMap<>();

    private Tuple2<Integer, Integer> robotPosition = Tuple.tuple(0, 0);

    private Direction currentDirection = Direction.NORTH;

    private boolean isInNavigationMode = false;

    public Object part1(List<String> input) {
        IOHelper io = new IOHelper() {
            @Override
            public String getInput(String textToDisplay) {
                return getCurrentColor();
            }

            @Override
            public void logInfo(Object infoText) {
                super.logInfo(infoText);
                if (isInNavigationMode) {
                    turnRobot(infoText);
                    moveRobot();
                } else {
                    paintCurrentPosition(infoText);
                }
                isInNavigationMode = !isInNavigationMode;
            }

        };
        List<Long> list = Util.parseLongCsv(input.getFirst());
        computer.runLogic(new ArrayList<>(list), io);
        return colorMap.size();

    }

    private void paintCurrentPosition(Object infoText) {
        colorMap.put(robotPosition, infoText.toString());
    }

    private void turnRobot(Object turnInfo) {
        boolean turnLeft = turnInfo.toString().equals("0");
        currentDirection = currentDirection.turn(turnLeft);
    }

    private void moveRobot() {
        robotPosition = currentDirection.move(robotPosition, 1);
    }

    private String getCurrentColor() {
        if (colorMap.containsKey(robotPosition)) {
            return colorMap.get(robotPosition);
        }
        return "0";
    }

    public Object part2(List<String> input) {
        colorMap.put(Tuple.tuple(0, 0), "1");
        part1(input);
        return OCR.runOCRAndReturnOriginalOnError(Util.paintMap(colorMap, Map.of("1", "*")));
    }

}
