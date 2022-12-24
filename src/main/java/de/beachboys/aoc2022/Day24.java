package de.beachboys.aoc2022;

import de.beachboys.Day;
import de.beachboys.Direction;
import de.beachboys.Util;
import org.javatuples.Pair;

import java.util.*;

public class Day24 extends Day {

    int maxX;
    int maxY;

    int modulo;
    private final Map<Integer, Set<Pair<Integer, Integer>>> blizzards = new HashMap<>();
    private Pair<Integer, Integer> start;
    private Pair<Integer, Integer> goal;


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
        start = Pair.with(1, 0);
        goal = Pair.with(maxX - 1, maxY);

        Map<Pair<Integer, Integer>, String> map = Util.buildImageMap(input);
        Set<Pair<Integer, Integer>> north = new HashSet<>();
        Set<Pair<Integer, Integer>> south = new HashSet<>();
        Set<Pair<Integer, Integer>> west = new HashSet<>();
        Set<Pair<Integer, Integer>> east = new HashSet<>();
        for (Map.Entry<Pair<Integer, Integer>, String> mapEntry : map.entrySet()) {
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
            Set<Pair<Integer, Integer>> allBlizzards = new HashSet<>();
            allBlizzards.addAll(moveBlizzards(north, Direction.NORTH, i));
            allBlizzards.addAll(moveBlizzards(south, Direction.SOUTH, i));
            allBlizzards.addAll(moveBlizzards(west, Direction.WEST, i));
            allBlizzards.addAll(moveBlizzards(east, Direction.EAST, i));
            blizzards.put(i, allBlizzards);
        }
    }

    private int findWay(Pair<Integer, Integer> start, Pair<Integer, Integer> goal, int startMinute) {
        Deque<State> queue = new LinkedList<>();
        Set<State> seen = new HashSet<>();
        queue.add(new State(start, startMinute));
        while (!queue.isEmpty()) {
            State state = queue.poll();
            int minuteAfterMove = state.minutes + 1;
            Set<Pair<Integer, Integer>> movedBlizzards = blizzards.get(minuteAfterMove % modulo);
            for (Direction dir : Direction.values()) {
                Pair<Integer, Integer> newPos = dir.move(state.pos, 1);
                if (newPos.equals(goal)) {
                    return minuteAfterMove;
                }
                if (newPos.getValue0() > 0 && newPos.getValue0() < maxX && newPos.getValue1() > 0 && newPos.getValue1() < maxY && !movedBlizzards.contains(newPos)) {
                    addNewStateToQueue(newPos, minuteAfterMove, queue, seen);
                }
            }
            if (!movedBlizzards.contains(state.pos)) {
                addNewStateToQueue(state.pos, minuteAfterMove, queue, seen);
            }
        }
        throw new IllegalArgumentException();
    }

    private void addNewStateToQueue(Pair<Integer, Integer> newPos, int minuteAfterMove, Deque<State> queue, Set<State> seen) {
        State newState = new State(newPos, minuteAfterMove);
        if (!seen.contains(newState)) {
            queue.add(newState);
            seen.add(newState);
        }
    }

    private Set<Pair<Integer, Integer>> moveBlizzards(Set<Pair<Integer, Integer>> blizzardsToMove, Direction dir, int steps) {
        Set<Pair<Integer, Integer>> movedBlizzards = new HashSet<>();
        for (Pair<Integer, Integer> blizzard : blizzardsToMove) {
            Pair<Integer, Integer> movedBlizzard = dir.move(blizzard, steps);
            while (movedBlizzard.getValue0() <= 0) {
                movedBlizzard = Pair.with(movedBlizzard.getValue0() + maxX - 1, movedBlizzard.getValue1());
            }
            while (movedBlizzard.getValue1() <= 0) {
                movedBlizzard = Pair.with(movedBlizzard.getValue0(), movedBlizzard.getValue1() + maxY - 1);
            }
            while (movedBlizzard.getValue0() >= maxX) {
                movedBlizzard = Pair.with(movedBlizzard.getValue0() - maxX + 1, movedBlizzard.getValue1());
            }
            while (movedBlizzard.getValue1() >= maxY) {
                movedBlizzard = Pair.with(movedBlizzard.getValue0(), movedBlizzard.getValue1() - maxY + 1);
            }
            movedBlizzards.add(movedBlizzard);
        }
        return movedBlizzards;
    }

    private static class State {
        private final Pair<Integer, Integer> pos;
        private final int minutes;

        public State(Pair<Integer, Integer> pos, int minutes) {
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
