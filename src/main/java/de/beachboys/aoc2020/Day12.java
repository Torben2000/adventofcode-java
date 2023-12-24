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
                case "N", "W", "E", "S":
                    pos = Direction.fromString(op).moveLong(pos, intValue);
                    break;
                case "L":
                    curDir = curDir.turnLeft( intValue / 90);
                    break;
                case "R":
                    curDir = curDir.turnRight( intValue / 90);
                    break;
                case "F":
                    pos = curDir.moveLong(pos, intValue);
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
                case "N", "W", "E", "S":
                    wayPoint = Direction.fromString(op).moveLong(wayPoint, intValue);
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

    private Tuple2<Long, Long> turnWayPoint(boolean turnLeft, Tuple2<Long, Long> currentWayPoint, int turnCount) {
        Tuple2<Long, Long> newWayPoint = currentWayPoint;
        for (int i = 0; i < turnCount; i++) {
            newWayPoint = turnWayPoint(turnLeft, newWayPoint);
        }
        return newWayPoint;
    }

    private Tuple2<Long, Long> turnWayPoint(boolean turnLeft, Tuple2<Long, Long> currentWayPoint) {
        long newX = turnLeft ? currentWayPoint.v2 : currentWayPoint.v2 * -1;
        long newY = turnLeft ? currentWayPoint.v1 * -1 : currentWayPoint.v1;
        return Tuple.tuple(newX, newY);
    }

}
