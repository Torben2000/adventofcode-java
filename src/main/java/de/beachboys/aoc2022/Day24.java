package de.beachboys.aoc2022;

import de.beachboys.Day;
import de.beachboys.Direction;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.*;

public class Day24 extends Day {

    int maxX;
    int maxY;

    int modulo;
    private final Map<Integer, Set<Tuple2<Integer, Integer>>> blizzards = new HashMap<>();
    private Tuple2<Integer, Integer> start;
    private Tuple2<Integer, Integer> goal;


    public Object part1(List<String> input) {
        parseInput(input);
        return findWay(start, goal, 0);
    }

    public Object part2(List<String> input) {
        parseInput(input);
        int initialWay = findWay(start, goal, 0);
        int backToStart = findWay(goal, start, initialWay);
        return findWay(start, goal, backToStart);
    }

    private void parseInput(List<String> input) {
        maxX = input.get(0).length() - 1;
        maxY = input.size() - 1;
        modulo = (int) Util.leastCommonMultiple(maxX-1, maxY-1);
        start = Tuple.tuple(1, 0);
        goal = Tuple.tuple(maxX - 1, maxY);

        Map<Tuple2<Integer, Integer>, String> map = Util.buildImageMap(input);
        Set<Tuple2<Integer, Integer>> north = new HashSet<>();
        Set<Tuple2<Integer, Integer>> south = new HashSet<>();
        Set<Tuple2<Integer, Integer>> west = new HashSet<>();
        Set<Tuple2<Integer, Integer>> east = new HashSet<>();
        for (Map.Entry<Tuple2<Integer, Integer>, String> mapEntry : map.entrySet()) {
            switch (mapEntry.getValue()) {
                case "^":
                    north.add(mapEntry.getKey());
                    break;
                case "v":
                    south.add(mapEntry.getKey());
                    break;
                case "<":
                    west.add(mapEntry.getKey());
                    break;
                case ">":
                    east.add(mapEntry.getKey());
                    break;
            }
        }

        blizzards.clear();
        for (int i = 0; i < modulo; i++) {
            Set<Tuple2<Integer, Integer>> allBlizzards = new HashSet<>();
            allBlizzards.addAll(moveBlizzards(north, Direction.NORTH, i));
            allBlizzards.addAll(moveBlizzards(south, Direction.SOUTH, i));
            allBlizzards.addAll(moveBlizzards(west, Direction.WEST, i));
            allBlizzards.addAll(moveBlizzards(east, Direction.EAST, i));
            blizzards.put(i, allBlizzards);
        }
    }

    private int findWay(Tuple2<Integer, Integer> start, Tuple2<Integer, Integer> goal, int startMinute) {
        Deque<State> queue = new LinkedList<>();
        Set<State> seen = new HashSet<>();
        queue.add(new State(start, startMinute));
        while (!queue.isEmpty()) {
            State state = queue.poll();
            int minuteAfterMove = state.minutes + 1;
            Set<Tuple2<Integer, Integer>> movedBlizzards = blizzards.get(minuteAfterMove % modulo);
            for (Direction dir : Direction.values()) {
                Tuple2<Integer, Integer> newPos = dir.move(state.pos, 1);
                if (newPos.equals(goal)) {
                    return minuteAfterMove;
                }
                if (newPos.v1 > 0 && newPos.v1 < maxX && newPos.v2 > 0 && newPos.v2 < maxY && !movedBlizzards.contains(newPos)) {
                    addNewStateToQueue(newPos, minuteAfterMove, queue, seen);
                }
            }
            if (!movedBlizzards.contains(state.pos)) {
                addNewStateToQueue(state.pos, minuteAfterMove, queue, seen);
            }
        }
        throw new IllegalArgumentException();
    }

    private void addNewStateToQueue(Tuple2<Integer, Integer> newPos, int minuteAfterMove, Deque<State> queue, Set<State> seen) {
        State newState = new State(newPos, minuteAfterMove);
        if (!seen.contains(newState)) {
            queue.add(newState);
            seen.add(newState);
        }
    }

    private Set<Tuple2<Integer, Integer>> moveBlizzards(Set<Tuple2<Integer, Integer>> blizzardsToMove, Direction dir, int steps) {
        Set<Tuple2<Integer, Integer>> movedBlizzards = new HashSet<>();
        for (Tuple2<Integer, Integer> blizzard : blizzardsToMove) {
            Tuple2<Integer, Integer> movedBlizzard = dir.move(blizzard, steps);
            while (movedBlizzard.v1 <= 0) {
                movedBlizzard = Tuple.tuple(movedBlizzard.v1 + maxX - 1, movedBlizzard.v2);
            }
            while (movedBlizzard.v2 <= 0) {
                movedBlizzard = Tuple.tuple(movedBlizzard.v1, movedBlizzard.v2 + maxY - 1);
            }
            while (movedBlizzard.v1 >= maxX) {
                movedBlizzard = Tuple.tuple(movedBlizzard.v1 - maxX + 1, movedBlizzard.v2);
            }
            while (movedBlizzard.v2 >= maxY) {
                movedBlizzard = Tuple.tuple(movedBlizzard.v1, movedBlizzard.v2 - maxY + 1);
            }
            movedBlizzards.add(movedBlizzard);
        }
        return movedBlizzards;
    }

    private static class State {
        private final Tuple2<Integer, Integer> pos;
        private final int minutes;

        public State(Tuple2<Integer, Integer> pos, int minutes) {
            this.pos = pos;
            this.minutes = minutes;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            State state = (State) o;
            return minutes == state.minutes && Objects.equals(pos, state.pos);
        }

        @Override
        public int hashCode() {
            return Objects.hash(pos, minutes);
        }
    }
}
