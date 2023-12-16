package de.beachboys.aoc2023;

import de.beachboys.Day;
import de.beachboys.Direction;
import de.beachboys.Util;
import org.javatuples.Pair;

import java.util.*;

public class Day16 extends Day {

    private Map<Pair<Integer, Integer>, String> map;
    private int width;
    private int height;

    public Object part1(List<String> input) {
        parseInput(input);
        return sendBeam(Pair.with((Pair.with(-1, 0)), Direction.EAST));
    }

    public Object part2(List<String> input) {
        parseInput(input);
        long result = 0;
        for (int i = 0; i < width; i++) {
            result = Math.max(result, sendBeam(Pair.with((Pair.with(i, -1)), Direction.SOUTH)));
            result = Math.max(result, sendBeam(Pair.with((Pair.with(i, height)), Direction.NORTH)));
        }
        for (int i = 0; i < height; i++) {
            result = Math.max(result, sendBeam(Pair.with((Pair.with(-1, i)), Direction.EAST)));
            result = Math.max(result, sendBeam(Pair.with((Pair.with(width, i)), Direction.WEST)));
        }
        return result;
    }

    private void parseInput(List<String> input) {
        map = Util.buildImageMap(input);
        width = input.getFirst().length();
        height = input.size();
    }

    private long sendBeam(Pair<Pair<Integer, Integer>, Direction> start) {
        Set<Pair<Pair<Integer, Integer>, Direction>> history = new HashSet<>();
        Deque<Pair<Pair<Integer, Integer>, Direction>> queue = new LinkedList<>();
        queue.add(start);


        while (!queue.isEmpty()) {
            Pair<Pair<Integer, Integer>, Direction> queueEntry = queue.poll();
            if (!history.contains(queueEntry)) {
                history.add(queueEntry);
                Pair<Integer, Integer> newPosition = queueEntry.getValue1().move(queueEntry.getValue0(), 1);
                if (Util.isInRectangle(newPosition, Pair.with(0, 0), Pair.with(width - 1, height - 1))) {
                    switch(map.get(newPosition)) {
                        case ".":
                            queue.add(Pair.with(newPosition, queueEntry.getValue1()));
                            break;
                        case "/":
                            switch (queueEntry.getValue1()) {
                                case EAST:
                                    queue.add(Pair.with(newPosition, Direction.NORTH));
                                    break;
                                case NORTH:
                                    queue.add(Pair.with(newPosition, Direction.EAST));
                                    break;
                                case WEST:
                                    queue.add(Pair.with(newPosition, Direction.SOUTH));
                                    break;
                                case SOUTH:
                                    queue.add(Pair.with(newPosition, Direction.WEST));
                                    break;
                            }
                            break;
                        case "\\":
                            switch (queueEntry.getValue1()) {
                                case EAST:
                                    queue.add(Pair.with(newPosition, Direction.SOUTH));
                                    break;
                                case SOUTH:
                                    queue.add(Pair.with(newPosition, Direction.EAST));
                                    break;
                                case WEST:
                                    queue.add(Pair.with(newPosition, Direction.NORTH));
                                    break;
                                case NORTH:
                                    queue.add(Pair.with(newPosition, Direction.WEST));
                                    break;
                            }
                            break;
                        case "|":
                            switch (queueEntry.getValue1()) {
                                case EAST, WEST:
                                    queue.add(Pair.with(newPosition, Direction.NORTH));
                                    queue.add(Pair.with(newPosition, Direction.SOUTH));
                                    break;
                                case SOUTH:
                                    queue.add(Pair.with(newPosition, Direction.SOUTH));
                                    break;
                                case NORTH:
                                    queue.add(Pair.with(newPosition, Direction.NORTH));
                                    break;
                            }
                            break;
                        case "-":
                            switch (queueEntry.getValue1()) {
                                case EAST:
                                    queue.add(Pair.with(newPosition, Direction.EAST));
                                    break;
                                case SOUTH, NORTH:
                                    queue.add(Pair.with(newPosition, Direction.EAST));
                                    queue.add(Pair.with(newPosition, Direction.WEST));
                                    break;
                                case WEST:
                                    queue.add(Pair.with(newPosition, Direction.WEST));
                                    break;
                            }
                            break;

                    }
                }
            }
        }

        return history.stream().map(Pair::getValue0).distinct().count() - 1; // -1 for start
    }

}
