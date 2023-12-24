package de.beachboys.aoc2020;

import de.beachboys.Day;
import de.beachboys.Direction;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.List;

public class Day12 extends Day {


    public Object part1(List<String> input) {
        Tuple2<Long, Long> pos = Tuple.tuple(0L, 0L);
        Direction curDir = Direction.EAST;
        for (String command : input) {
            String op = command.substring(0, 1);
            int intValue = Integer.parseInt(command.substring(1));
            switch (op) {
                case "N":
                    pos = Tuple.tuple(pos.v1, pos.v2 - intValue);
                    break;
                case "S":
                    pos = Tuple.tuple(pos.v1, pos.v2 + intValue);
                    break;
                case "E":
                    pos = Tuple.tuple(pos.v1 + intValue, pos.v2);
                    break;
                case "W":
                    pos = Tuple.tuple(pos.v1 - intValue, pos.v2);
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
        return Math.abs(pos.v1) + Math.abs(pos.v2);
    }

    public Object part2(List<String> input) {
        Tuple2<Long, Long>  pos = Tuple.tuple(0L, 0L);
        Tuple2<Long, Long>  wayPoint = Tuple.tuple(10L, -1L);
        for (String command : input) {
            String op = command.substring(0, 1);
            int intValue = Integer.parseInt(command.substring(1));
            switch (op) {
                case "N":
                    wayPoint = Tuple.tuple(wayPoint.v1, wayPoint.v2 - intValue);
                    break;
                case "S":
                    wayPoint = Tuple.tuple(wayPoint.v1, wayPoint.v2 + intValue);
                    break;
                case "E":
                    wayPoint = Tuple.tuple(wayPoint.v1 + intValue, wayPoint.v2);
                    break;
                case "W":
                    wayPoint = Tuple.tuple(wayPoint.v1 - intValue, wayPoint.v2);
                    break;
                case "L":
                    wayPoint = turnWayPoint(true, wayPoint, intValue / 90);
                    break;
                case "R":
                    wayPoint = turnWayPoint(false, wayPoint, intValue / 90);
                    break;
                case "F":
                    pos = Tuple.tuple(pos.v1 + intValue * wayPoint.v1,  pos.v2 + intValue * wayPoint.v2);
                    break;
            }
        }
        return Math.abs(pos.v1) + Math.abs(pos.v2);
    }

    private Direction turnShip(boolean turnLeft, Direction currentDirection, int turnCount) {
        Direction newDirection = currentDirection;
        for (int i = 0; i < turnCount; i++) {
            newDirection = turnShip(turnLeft, newDirection);
        }
        return newDirection;
    }

    private Tuple2<Long, Long> turnWayPoint(boolean turnLeft, Tuple2<Long, Long> currentWayPoint, int turnCount) {
        Tuple2<Long, Long> newWayPoint = currentWayPoint;
        for (int i = 0; i < turnCount; i++) {
            newWayPoint = turnWayPoint(turnLeft, newWayPoint);
        }
        return newWayPoint;
    }

    private Direction turnShip(boolean turnLeft, Direction currentDirection) {
        return switch (currentDirection) {
            case NORTH -> turnLeft ? Direction.WEST : Direction.EAST;
            case EAST -> turnLeft ? Direction.NORTH : Direction.SOUTH;
            case SOUTH -> turnLeft ? Direction.EAST : Direction.WEST;
            case WEST -> turnLeft ? Direction.SOUTH : Direction.NORTH;
        };
    }

    private Tuple2<Long, Long> turnWayPoint(boolean turnLeft, Tuple2<Long, Long> currentWayPoint) {
        long newX = turnLeft ? currentWayPoint.v2 : currentWayPoint.v2 * -1;
        long newY = turnLeft ? currentWayPoint.v1 * -1 : currentWayPoint.v1;
        return Tuple.tuple(newX, newY);
    }

    private Tuple2<Long, Long> moveInDirection(Tuple2<Long, Long> currentPosition, Direction direction, Integer distance) {
        return switch (direction) {
            case NORTH -> Tuple.tuple(currentPosition.v1, currentPosition.v2 - distance);
            case EAST -> Tuple.tuple(currentPosition.v1 + distance, currentPosition.v2);
            case SOUTH -> Tuple.tuple(currentPosition.v1, currentPosition.v2 + distance);
            case WEST -> Tuple.tuple(currentPosition.v1 - distance, currentPosition.v2);
        };
    }

}
