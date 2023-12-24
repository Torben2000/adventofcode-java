package de.beachboys.aoc2018;

import de.beachboys.Day;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple3;

import java.util.List;
import java.util.function.IntBinaryOperator;

public class Day11 extends Day {

    private final int[][] summedAreaTable = new int[301][301];

    public Object part1(List<String> input) {
        IntBinaryOperator getMaxSquareSize = (i, j) -> Math.min(3, Math.min(300 - i, 300 - j));
        Tuple3<Integer, Integer, Integer> bestPositionWithSize = runLogicAndGetBestPositionWithSize(input, 3, getMaxSquareSize);
        return bestPositionWithSize.v1 + "," + bestPositionWithSize.v2;
    }

    public Object part2(List<String> input) {
        IntBinaryOperator getMaxSquareSize = (i, j) -> Math.min(300 - i, 300 - j);
        Tuple3<Integer, Integer, Integer> bestPositionWithSize = runLogicAndGetBestPositionWithSize(input, 1, getMaxSquareSize);
        return bestPositionWithSize.v1 + "," + bestPositionWithSize.v2 + "," + bestPositionWithSize.v3;
    }

    private Tuple3<Integer, Integer, Integer> runLogicAndGetBestPositionWithSize(List<String> input, int minSquareSize, IntBinaryOperator getMaxSquareSize) {
        fillSummedAreaTable(input);
        Tuple3<Integer, Integer, Integer> bestPositionWithSize = Tuple.tuple(0, 0, 0);
        int maxValue = Integer.MIN_VALUE;
        for (int i = 0; i < 300; i++) {
            for (int j = 0; j < 300; j++) {
                int maxSquareSize = getMaxSquareSize.applyAsInt(i, j);
                for (int squareSize = minSquareSize; squareSize <= maxSquareSize; squareSize++) {
                    int value = getValue(i, j, squareSize);
                    if (value > maxValue) {
                        maxValue = value;
                        bestPositionWithSize = Tuple.tuple(i + 1, j + 1, squareSize);
                    }
                }
            }
        }
        return bestPositionWithSize;
    }

    private void fillSummedAreaTable(List<String> input) {
        int serialNumber = Integer.parseInt(input.get(0));
        summedAreaTable[0][0] = 0;
        for (int i = 1; i <= 300; i++) {
            summedAreaTable[0][i] = 0;
            summedAreaTable[i][0] = 0;
            for (int j = 1; j <= 300; j++) {
                int powerLevel = getPowerLevel(serialNumber, i, j);
                summedAreaTable[i][j] = powerLevel + summedAreaTable[i - 1][j] + summedAreaTable[i][j - 1] - summedAreaTable[i - 1][j - 1];
            }
        }
    }

    private int getPowerLevel(int serialNumber, int i, int j) {
        int rackId = i + 10;
        int powerLevel = rackId * j;
        powerLevel += serialNumber;
        powerLevel *= rackId;
        powerLevel /= 100;
        powerLevel %= 10;
        powerLevel -= 5;
        return powerLevel;
    }

    private int getValue(int i, int j, int squareSize) {
        return summedAreaTable[i][j] + summedAreaTable[i + squareSize][j + squareSize] - summedAreaTable[i + squareSize][j] - summedAreaTable[i][j + squareSize];
    }

}
