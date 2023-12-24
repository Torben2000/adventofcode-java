package de.beachboys.aoc2019;

import de.beachboys.Day;
import de.beachboys.IOHelper;
import de.beachboys.OCR;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day11 extends Day {

    private enum Direction {
        UP, DOWN, RIGHT, LEFT
    }

    private final IntcodeComputer computer = new IntcodeComputer();

    private final Map<Tuple2<Integer, Integer>, String> colorMap = new HashMap<>();

    private Tuple2<Integer, Integer> robotPosition = Tuple.tuple(0, 0);

    private Direction currentDirection = Direction.UP;

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
        switch (currentDirection) {
            case UP:
                currentDirection = turnLeft ? Direction.LEFT : Direction.RIGHT;
                break;
            case RIGHT:
                currentDirection = turnLeft ? Direction.UP : Direction.DOWN;
                break;
            case DOWN:
                currentDirection = turnLeft ? Direction.RIGHT : Direction.LEFT;
                break;
            case LEFT:
                currentDirection = turnLeft ? Direction.DOWN : Direction.UP;
                break;
        }
    }

    private void moveRobot() {
        switch (currentDirection) {
            case UP:
                robotPosition = Tuple.tuple(robotPosition.v1, robotPosition.v2 - 1);
                break;
            case RIGHT:
                robotPosition = Tuple.tuple(robotPosition.v1 + 1, robotPosition.v2);
                break;
            case DOWN:
                robotPosition = Tuple.tuple(robotPosition.v1, robotPosition.v2 + 1);
                break;
            case LEFT:
                robotPosition = Tuple.tuple(robotPosition.v1 - 1, robotPosition.v2);
                break;
        }
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
