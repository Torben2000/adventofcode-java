package de.beachboys.aoc2020;

import de.beachboys.Day;
import org.javatuples.Pair;

import java.util.List;

public class Day12 extends Day {

    private enum Direction {
        NORTH, SOUTH, EAST, WEST
    }

    public Object part1(List<String> input) {
        Pair<Long, Long>  pos = Pair.with(0L, 0L);
        Direction curDir = Direction.EAST;
        for (String command : input) {
            String op = command.substring(0, 1);
            int intValue = Integer.parseInt(command.substring(1));
            switch (op) {
                case "N":
                    pos = pos.setAt1(pos.getValue1() - intValue);
                    break;
                case "S":
                    pos = pos.setAt1(pos.getValue1() + intValue);
                    break;
                case "E":
                    pos = pos.setAt0(pos.getValue0() + intValue);
                    break;
                case "W":
                    pos = pos.setAt0(pos.getValue0() - intValue);
                    break;
                case "L":
                    curDir = turnShip(true, curDir, intValue / 90);
                    break;
                case "R":
                    curDir = turnShip(false, curDir, intValue / 90);
                    break;
                case "F":
                    pos = moveInDirection(pos, curDir, intValue);
                    break;
            }
        }
        return Math.abs(pos.getValue0()) + Math.abs(pos.getValue1());
    }

    public Object part2(List<String> input) {
        Pair<Long, Long>  pos = Pair.with(0L, 0L);
        Pair<Long, Long>  wayPoint = Pair.with(10L, -1L);
        for (String command : input) {
            String op = command.substring(0, 1);
            int intValue = Integer.parseInt(command.substring(1));
            switch (op) {
                case "N":
                    wayPoint = wayPoint.setAt1(wayPoint.getValue1() - intValue);
                    break;
                case "S":
                    wayPoint = wayPoint.setAt1(wayPoint.getValue1() + intValue);
                    break;
                case "E":
                    wayPoint = wayPoint.setAt0(wayPoint.getValue0() + intValue);
                    break;
                case "W":
                    wayPoint = wayPoint.setAt0(wayPoint.getValue0() - intValue);
                    break;
                case "L":
                    wayPoint = turnWayPoint(true, wayPoint, intValue / 90);
                    break;
                case "R":
                    wayPoint = turnWayPoint(false, wayPoint, intValue / 90);
                    break;
                case "F":
                    pos = Pair.with(pos.getValue0() + intValue * wayPoint.getValue0(),  pos.getValue1() + intValue * wayPoint.getValue1());
                    break;
            }
        }
        return Math.abs(pos.getValue0()) + Math.abs(pos.getValue1());
    }

    private Direction turnShip(boolean turnLeft, Direction currentDirection, int turnCount) {
        Direction newDirection = currentDirection;
        for (int i = 0; i < turnCount; i++) {
            newDirection = turnShip(turnLeft, newDirection);
        }
        return newDirection;
    }

    private Pair<Long, Long> turnWayPoint(boolean turnLeft, Pair<Long, Long> currentWayPoint, int turnCount) {
        Pair<Long, Long> newWayPoint = currentWayPoint;
        for (int i = 0; i < turnCount; i++) {
            newWayPoint = turnWayPoint(turnLeft, newWayPoint);
        }
        return newWayPoint;
    }

    private Direction turnShip(boolean turnLeft, Direction currentDirection) {
        Direction newDirection = currentDirection;
        switch (currentDirection) {
            case NORTH:
                newDirection = turnLeft ? Direction.WEST : Direction.EAST;
                break;
            case EAST:
                newDirection = turnLeft ? Direction.NORTH : Direction.SOUTH;
                break;
            case SOUTH:
                newDirection = turnLeft ? Direction.EAST : Direction.WEST;
                break;
            case WEST:
                newDirection = turnLeft ? Direction.SOUTH : Direction.NORTH;
                break;
        }
        return newDirection;
    }

    private Pair<Long, Long> turnWayPoint(boolean turnLeft, Pair<Long, Long> currentWayPoint) {
        long newX = turnLeft ? currentWayPoint.getValue1() : currentWayPoint.getValue1() * -1;
        long newY = turnLeft ? currentWayPoint.getValue0() * -1 : currentWayPoint.getValue0();
        return Pair.with(newX, newY);
    }

    private Pair<Long, Long> moveInDirection(Pair<Long, Long> currentPosition, Direction direction, Integer distance) {
        switch (direction) {
            case NORTH:
                return currentPosition.setAt1(currentPosition.getValue1() - distance);
            case EAST:
                return currentPosition.setAt0(currentPosition.getValue0() + distance);
            case SOUTH:
                return currentPosition.setAt1(currentPosition.getValue1() + distance);
            case WEST:
                return currentPosition.setAt0(currentPosition.getValue0() - distance);
        }
        return currentPosition;
    }

}
