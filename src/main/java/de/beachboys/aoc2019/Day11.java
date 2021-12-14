package de.beachboys.aoc2019;

import de.beachboys.Day;
import de.beachboys.IOHelper;
import de.beachboys.OCR;
import de.beachboys.Util;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day11 extends Day {

    private enum Direction {
        UP, DOWN, RIGHT, LEFT
    }

    private final IntcodeComputer computer = new IntcodeComputer();

    private final Map<Pair<Integer, Integer>, String> colorMap = new HashMap<>();

    private Pair<Integer, Integer> robotPosition = Pair.with(0, 0);

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
        List<Long> list = Util.parseLongCsv(input.get(0));
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
                robotPosition = robotPosition.setAt1(robotPosition.getValue1() - 1);
                break;
            case RIGHT:
                robotPosition = robotPosition.setAt0(robotPosition.getValue0() + 1);
                break;
            case DOWN:
                robotPosition = robotPosition.setAt1(robotPosition.getValue1() + 1);
                break;
            case LEFT:
                robotPosition = robotPosition.setAt0(robotPosition.getValue0() - 1);
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
        colorMap.put(Pair.with(0, 0), "1");
        part1(input);
        return OCR.runOCRAndReturnOriginalOnError(Util.paintMap(colorMap, Map.of("1", "*")));
    }

}
