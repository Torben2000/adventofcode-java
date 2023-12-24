package de.beachboys.aoc2023;

import de.beachboys.Day;
import de.beachboys.Direction;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.*;

public class Day16 extends Day {

    private Map<Tuple2<Integer, Integer>, String> map;
    private int width;
    private int height;

    public Object part1(List<String> input) {
        parseInput(input);
        return sendBeam(Tuple.tuple((Tuple.tuple(-1, 0)), Direction.EAST));
    }

    public Object part2(List<String> input) {
        parseInput(input);
        long result = 0;
        for (int i = 0; i < width; i++) {
            result = Math.max(result, sendBeam(Tuple.tuple((Tuple.tuple(i, -1)), Direction.SOUTH)));
            result = Math.max(result, sendBeam(Tuple.tuple((Tuple.tuple(i, height)), Direction.NORTH)));
        }
        for (int i = 0; i < height; i++) {
            result = Math.max(result, sendBeam(Tuple.tuple((Tuple.tuple(-1, i)), Direction.EAST)));
            result = Math.max(result, sendBeam(Tuple.tuple((Tuple.tuple(width, i)), Direction.WEST)));
        }
        return result;
    }

    private void parseInput(List<String> input) {
        map = Util.buildImageMap(input);
        width = input.getFirst().length();
        height = input.size();
    }

    private long sendBeam(Tuple2<Tuple2<Integer, Integer>, Direction> start) {
        Set<Tuple2<Tuple2<Integer, Integer>, Direction>> history = new HashSet<>();
        Deque<Tuple2<Tuple2<Integer, Integer>, Direction>> queue = new LinkedList<>();
        queue.add(start);


        while (!queue.isEmpty()) {
            Tuple2<Tuple2<Integer, Integer>, Direction> queueEntry = queue.poll();
            if (!history.contains(queueEntry)) {
                history.add(queueEntry);
                Tuple2<Integer, Integer> newPosition = queueEntry.v2.move(queueEntry.v1, 1);
                if (Util.isInRectangle(newPosition, Tuple.tuple(0, 0), Tuple.tuple(width - 1, height - 1))) {
                    switch(map.get(newPosition)) {
                        case ".":
                            queue.add(Tuple.tuple(newPosition, queueEntry.v2));
                            break;
                        case "/":
                            switch (queueEntry.v2) {
                                case EAST:
                                    queue.add(Tuple.tuple(newPosition, Direction.NORTH));
                                    break;
                                case NORTH:
                                    queue.add(Tuple.tuple(newPosition, Direction.EAST));
                                    break;
                                case WEST:
                                    queue.add(Tuple.tuple(newPosition, Direction.SOUTH));
                                    break;
                                case SOUTH:
                                    queue.add(Tuple.tuple(newPosition, Direction.WEST));
                                    break;
                            }
                            break;
                        case "\\":
                            switch (queueEntry.v2) {
                                case EAST:
                                    queue.add(Tuple.tuple(newPosition, Direction.SOUTH));
                                    break;
                                case SOUTH:
                                    queue.add(Tuple.tuple(newPosition, Direction.EAST));
                                    break;
                                case WEST:
                                    queue.add(Tuple.tuple(newPosition, Direction.NORTH));
                                    break;
                                case NORTH:
                                    queue.add(Tuple.tuple(newPosition, Direction.WEST));
                                    break;
                            }
                            break;
                        case "|":
                            switch (queueEntry.v2) {
                                case EAST, WEST:
                                    queue.add(Tuple.tuple(newPosition, Direction.NORTH));
                                    queue.add(Tuple.tuple(newPosition, Direction.SOUTH));
                                    break;
                                case SOUTH:
                                    queue.add(Tuple.tuple(newPosition, Direction.SOUTH));
                                    break;
                                case NORTH:
                                    queue.add(Tuple.tuple(newPosition, Direction.NORTH));
                                    break;
                            }
                            break;
                        case "-":
                            switch (queueEntry.v2) {
                                case EAST:
                                    queue.add(Tuple.tuple(newPosition, Direction.EAST));
                                    break;
                                case SOUTH, NORTH:
                                    queue.add(Tuple.tuple(newPosition, Direction.EAST));
                                    queue.add(Tuple.tuple(newPosition, Direction.WEST));
                                    break;
                                case WEST:
                                    queue.add(Tuple.tuple(newPosition, Direction.WEST));
                                    break;
                            }
                            break;

                    }
                }
            }
        }

        return history.stream().map(Tuple2::v1).distinct().count() - 1; // -1 for start
    }

}
