package de.beachboys.aoc2019;

import de.beachboys.Day;
import de.beachboys.IOHelper;
import de.beachboys.Util;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day13 extends Day {

    private enum Mode {
        X, Y, TILE
    }

    private Map<Pair<Integer, Integer>, String> tileMap = new HashMap<>();

    private IntcodeComputer computer = new IntcodeComputer();

    private Mode currentMode = Mode.X;

    private int currentX = -1;

    private int currentY = -1;

    private int currentScore = 0;

    private Pair<Integer, Integer> oldBallPosition;
    private Pair<Integer, Integer> ballPosition;
    private Pair<Integer, Integer> paddlePosition;

    public Object part1(List<String> input) {
        List<Long> list = Util.parseLongCsv(input.get(0));

        runComputer(list);

        return tileMap.values().stream().filter("2"::equals).count();
    }

    private void runComputer(List<Long> list) {
        IOHelper io = new IOHelper() {

            @Override
            public String getInput(String textToDisplay) {
                oldBallPosition = ballPosition;
                super.logDebug(Util.paintMap(tileMap, Map.of("1", "#", "2", "o", "3", "-", "4", "*")));
                ballPosition = getTilePosition("4");
                paddlePosition = getTilePosition("3");
                if (oldBallPosition == null) {
                    oldBallPosition = ballPosition;
                }
                int targetX = calculateTargetXValue(ballPosition, paddlePosition);
                if (targetX > paddlePosition.getValue0()) {
                    return "1";
                } else if (targetX < paddlePosition.getValue0()) {
                    return "-1";
                }
                return "0";
            }

            @Override
            public void logInfo(Object infoText) {
                int value = Integer.parseInt(infoText.toString());
                switch (currentMode) {
                    case X:
                        currentMode = Mode.Y;
                        Day13.this.currentX = value;
                        break;
                    case Y:
                        currentMode = Mode.TILE;
                        Day13.this.currentY = value;
                        break;
                    case TILE:
                        currentMode = Mode.X;
                        if (currentX == -1 && currentY == 0) {
                            currentScore = value;
                            super.logInfo("Current score: " + currentScore);
                        } else {
                            tileMap.put(Pair.with(Day13.this.currentX, currentY), String.valueOf(value));
                        }
                        break;
                }
             }

            @Override
            public void logDebug(Object debugText) {
                // do nothing
            }
        };

        computer.runLogic(new ArrayList<>(list), io);
    }

    private int calculateTargetXValue(Pair<Integer, Integer> ballPosition, Pair<Integer, Integer> paddlePosition) {
        Pair<Integer, Integer> nextBallPosition = Pair.with(ballPosition.getValue0() + (ballPosition.getValue0() - oldBallPosition.getValue0()), ballPosition.getValue1() + (ballPosition.getValue1() - oldBallPosition.getValue1()));
        int targetX = nextBallPosition.getValue0();
        if (hitsPanel(nextBallPosition)) {
            targetX = ballPosition.getValue0();
        } else if (hitsCorner(nextBallPosition) || (hitsSide(nextBallPosition) && hitsTop(nextBallPosition))) {
            targetX = oldBallPosition.getValue0();
        } else if (hitsLeftSide(nextBallPosition) && !hitsOpposingCorner(nextBallPosition)) {
            targetX += 2;
        } else if (hitsRightSide(nextBallPosition) && !hitsOpposingCorner(nextBallPosition)) {
            targetX -= 2;
        }
        return targetX;
    }

    private boolean hitsOpposingCorner(Pair<Integer, Integer> nextBallPosition) {
        return isBlockingTile(tileMap.get(Pair.with(oldBallPosition.getValue0(), nextBallPosition.getValue1())));
    }

    private boolean hitsRightSide(Pair<Integer, Integer> nextBallPosition) {
        return hitsSide(nextBallPosition) && nextBallPosition.getValue0() > ballPosition.getValue0();
    }

    private boolean hitsLeftSide(Pair<Integer, Integer> nextBallPosition) {
        return hitsSide(nextBallPosition) && nextBallPosition.getValue0() < ballPosition.getValue0();
    }

    private boolean hitsSide(Pair<Integer, Integer> nextBallPosition) {
        return isBlockingTile(tileMap.get(Pair.with(nextBallPosition.getValue0(), ballPosition.getValue1())));
    }

    private boolean hitsTop(Pair<Integer, Integer> nextBallPosition) {
        return isBlockingTile(tileMap.get(Pair.with(ballPosition.getValue0(), nextBallPosition.getValue1())));
    }

    private boolean isBlockingTile(String tile) {
        return "1".equals(tile) || "2".equals(tile);
    }

    private boolean hitsCorner(Pair<Integer, Integer> nextBallPosition) {
        return isBlockingTile(tileMap.get(nextBallPosition)) && !hitsSide(nextBallPosition) && !hitsTop(nextBallPosition);
    }

    private boolean hitsPanel(Pair<Integer, Integer> nextBallPosition) {
        return paddlePosition.getValue1().equals(nextBallPosition.getValue1());
    }

    private Pair<Integer, Integer> getTilePosition(String tileType) {
        return tileMap.keySet().stream().filter(key -> tileMap.get(key).equals(tileType)).findFirst().orElse(Pair.with(-1, -1));
    }

    public Object part2(List<String> input) {
        List<Long> list = Util.parseLongCsv(input.get(0));
        list.set(0, 2L);

        runComputer(list);

        return currentScore;
    }

}
