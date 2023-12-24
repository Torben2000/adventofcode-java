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

public class Day13 extends Day {

    private enum Mode {
        X, Y, TILE
    }

    private final Map<Tuple2<Integer, Integer>, String> tileMap = new HashMap<>();

    private final IntcodeComputer computer = new IntcodeComputer();

    private Mode currentMode = Mode.X;

    private int currentX = -1;

    private int currentY = -1;

    private int currentScore = 0;

    private Tuple2<Integer, Integer> oldBallPosition;
    private Tuple2<Integer, Integer> ballPosition;
    private Tuple2<Integer, Integer> paddlePosition;

    public Object part1(List<String> input) {
        List<Long> list = Util.parseLongCsv(input.getFirst());

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
                int targetX = calculateTargetXValue(ballPosition);
                if (targetX > paddlePosition.v1) {
                    return "1";
                } else if (targetX < paddlePosition.v1) {
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
                            tileMap.put(Tuple.tuple(Day13.this.currentX, currentY), String.valueOf(value));
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

    private int calculateTargetXValue(Tuple2<Integer, Integer> ballPosition) {
        Tuple2<Integer, Integer> nextBallPosition = Tuple.tuple(ballPosition.v1 + (ballPosition.v1 - oldBallPosition.v1), ballPosition.v2 + (ballPosition.v2 - oldBallPosition.v2));
        int targetX = nextBallPosition.v1;
        if (hitsPanel(nextBallPosition)) {
            targetX = ballPosition.v1;
        } else if (hitsCorner(nextBallPosition) || (hitsSide(nextBallPosition) && hitsTop(nextBallPosition))) {
            targetX = oldBallPosition.v1;
        } else if (hitsLeftSide(nextBallPosition) && !hitsOpposingCorner(nextBallPosition)) {
            targetX += 2;
        } else if (hitsRightSide(nextBallPosition) && !hitsOpposingCorner(nextBallPosition)) {
            targetX -= 2;
        }
        return targetX;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean hitsOpposingCorner(Tuple2<Integer, Integer> nextBallPosition) {
        return isBlockingTile(tileMap.get(Tuple.tuple(oldBallPosition.v1, nextBallPosition.v2)));
    }

    private boolean hitsRightSide(Tuple2<Integer, Integer> nextBallPosition) {
        return hitsSide(nextBallPosition) && nextBallPosition.v1 > ballPosition.v1;
    }

    private boolean hitsLeftSide(Tuple2<Integer, Integer> nextBallPosition) {
        return hitsSide(nextBallPosition) && nextBallPosition.v1 < ballPosition.v1;
    }

    private boolean hitsSide(Tuple2<Integer, Integer> nextBallPosition) {
        return isBlockingTile(tileMap.get(Tuple.tuple(nextBallPosition.v1, ballPosition.v2)));
    }

    private boolean hitsTop(Tuple2<Integer, Integer> nextBallPosition) {
        return isBlockingTile(tileMap.get(Tuple.tuple(ballPosition.v1, nextBallPosition.v2)));
    }

    private boolean isBlockingTile(String tile) {
        return "1".equals(tile) || "2".equals(tile);
    }

    private boolean hitsCorner(Tuple2<Integer, Integer> nextBallPosition) {
        return isBlockingTile(tileMap.get(nextBallPosition)) && !hitsSide(nextBallPosition) && !hitsTop(nextBallPosition);
    }

    private boolean hitsPanel(Tuple2<Integer, Integer> nextBallPosition) {
        return paddlePosition.v2.equals(nextBallPosition.v2);
    }

    private Tuple2<Integer, Integer> getTilePosition(String tileType) {
        return tileMap.keySet().stream().filter(key -> tileMap.get(key).equals(tileType)).findFirst().orElse(Tuple.tuple(-1, -1));
    }

    public Object part2(List<String> input) {
        List<Long> list = Util.parseLongCsv(input.getFirst());
        list.set(0, 2L);

        runComputer(list);

        return currentScore;
    }

}
