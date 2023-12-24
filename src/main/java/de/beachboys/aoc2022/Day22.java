package de.beachboys.aoc2022;

import de.beachboys.Day;
import de.beachboys.Direction;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.*;
import java.util.stream.Collectors;

public class Day22 extends Day {

    private static final Set<Tuple2<Integer, Integer>> PATTERN_1 = Set.of(Tuple.tuple(2, 0), Tuple.tuple(0, 1), Tuple.tuple(1, 1), Tuple.tuple(2, 1), Tuple.tuple(2, 2), Tuple.tuple(3, 2));
    private static final Set<Tuple2<Integer, Integer>> PATTERN_2 = Set.of(Tuple.tuple(1, 0), Tuple.tuple(2, 0), Tuple.tuple(1, 1), Tuple.tuple(0, 2), Tuple.tuple(1, 2), Tuple.tuple(0, 3));

    private Map<Tuple2<Integer, Integer>, String> map;
    private int mapPartSideLength;
    private final List<MapPart> mapPartList = new ArrayList<>();
    private final Set<Tuple2<Integer, Integer>> mapPattern = new HashSet<>();

    public Object part1(List<String> input) {
        return runLogic(input, false);
    }

    public Object part2(List<String> input) {
        return runLogic(input, true);
    }

    private long runLogic(List<String> input, boolean is3DCube) {
        parseMap(input, is3DCube);
        String commandLine = input.get(input.size() - 1);
        State finalState = moveOnMap(commandLine);

        int result = (finalState.pos.v2 + 1) * 1000;
        result += (finalState.pos.v1 + 1) * 4;
        switch (finalState.dir) {
            case SOUTH:
                result += 1;
                break;
            case WEST:
                result += 2;
                break;
            case NORTH:
                result += 3;
                break;
        }
        return result;
    }

    private State moveOnMap(String commandLine) {
        Tuple2<Integer, Integer> startPos = Tuple.tuple(map.keySet().stream().filter(p -> p.v2 == 0).mapToInt(Tuple2::v1).min().orElseThrow(), 0);
        State state = new State(mapPartList.get(0), startPos, Direction.EAST);
        int steps = 0;
        for (int i = 0; i < commandLine.length(); i++) {
            char c = commandLine.charAt(i);
            switch (c) {
                case 'L':
                    state = moveBySteps(state, steps);
                    state = new State(state.side, state.pos, state.dir.turnLeft());
                    steps = 0;
                    break;
                case 'R':
                    state = moveBySteps(state, steps);
                    state = new State(state.side, state.pos, state.dir.turnRight());
                    steps = 0;
                    break;
                default:
                    steps = steps * 10 + c - '0';
                    break;
            }
        }
        return moveBySteps(state, steps);
    }

    private State moveBySteps(State state, int steps) {
        for (int i = 0; i < steps; i++) {
            Tuple2<Integer, Integer> newPos = state.dir.move(state.pos, 1);
            State newState = new State(state.side, newPos, state.dir);
            int posIndex = state.side.borders.get(state.dir).indexOf(state.pos);
            if (posIndex >= 0) {
                Tuple2<MapPart, Direction> connectedMapPart = state.side.connectedMapParts.get(state.dir);
                int positionIndexOnConnectedBorder = isBorderInOtherDirection(state.dir, connectedMapPart.v2) ? mapPartSideLength - 1 - posIndex : posIndex;
                newPos = connectedMapPart.v1.borders.get(connectedMapPart.v2).get(positionIndexOnConnectedBorder);
                newState = new State(connectedMapPart.v1, newPos, connectedMapPart.v2.getOpposite());

            }
            String tile = map.get(newPos);
            if ("#".equals(tile)) {
                return state;
            }
            state = newState;
        }
        return state;
    }

    boolean isBorderInOtherDirection(Direction dir1, Direction dir2) {
        return dir1.equals(dir2) || !dir1.getOpposite().equals(dir2) && dir1.stepX + dir1.stepY + dir2.stepX + dir2.stepY == 0;
    }

    private void parseMap(List<String> input, boolean is3DCube) {
        map = Util.buildImageMap(input.subList(0, input.size() - 2));
        Set<Tuple2<Integer, Integer>> set = map.entrySet().stream().filter(e -> " ".equals(e.getValue())).map(Map.Entry::getKey).collect(Collectors.toSet());
        for (Tuple2<Integer, Integer> key : set) {
            map.remove(key);
        }
        int tilesPerSide = map.size() / 6;
        mapPartSideLength = (int) Math.sqrt(tilesPerSide);
        fillMapParts(is3DCube);
    }

    private void fillMapParts(boolean is3DCube) {
        mapPartList.clear();
        mapPattern.clear();
        int xMax = map.keySet().stream().mapToInt(Tuple2::v1).max().orElseThrow() / mapPartSideLength;
        int yMax = map.keySet().stream().mapToInt(Tuple2::v2).max().orElseThrow() / mapPartSideLength;
        for (int y = 0; y <= yMax; y++) {
            for (int x = 0; x <= xMax; x++) {
                if (map.containsKey(Tuple.tuple(x * mapPartSideLength, y * mapPartSideLength))) {
                    MapPart mapPart = new MapPart(mapPartSideLength, x, y);
                    mapPartList.add(mapPart);
                    mapPattern.add(mapPart.positionOnMap);
                }
            }
        }
        linkMapParts(is3DCube);
    }

    private void linkMapParts(boolean is3DCube) {
        if (PATTERN_1.equals(mapPattern)) {
            connectMapParts(0, Direction.SOUTH, 3, Direction.NORTH);
            connectMapParts(1, Direction.EAST, 2, Direction.WEST);
            connectMapParts(2, Direction.EAST, 3, Direction.WEST);
            connectMapParts(3, Direction.SOUTH, 4, Direction.WEST);
            connectMapParts(4, Direction.EAST, 5, Direction.WEST);
            if (is3DCube) {
                connectMapParts(0, Direction.NORTH, 1, Direction.NORTH);
                connectMapParts(0, Direction.EAST, 5, Direction.EAST);
                connectMapParts(0, Direction.WEST, 2, Direction.NORTH);
                connectMapParts(1, Direction.SOUTH, 4, Direction.SOUTH);
                connectMapParts(1, Direction.WEST, 5, Direction.SOUTH);
                connectMapParts(2, Direction.SOUTH, 4, Direction.NORTH);
                connectMapParts(3, Direction.EAST, 5, Direction.NORTH);
            } else {
                connectMapParts(0, Direction.NORTH, 4, Direction.SOUTH);
                connectMapParts(0, Direction.EAST, 0, Direction.WEST);
                connectMapParts(1, Direction.NORTH, 1, Direction.SOUTH);
                connectMapParts(1, Direction.WEST, 3, Direction.EAST);
                connectMapParts(2, Direction.NORTH, 2, Direction.SOUTH);
                connectMapParts(4, Direction.WEST, 5, Direction.EAST);
                connectMapParts(5, Direction.NORTH, 5, Direction.SOUTH);
            }
        } else if (PATTERN_2.equals(mapPattern)){
            connectMapParts(0, Direction.EAST, 1, Direction.WEST);
            connectMapParts(0, Direction.SOUTH, 2, Direction.NORTH);
            connectMapParts(2, Direction.SOUTH, 4, Direction.NORTH);
            connectMapParts(3, Direction.EAST, 4, Direction.WEST);
            connectMapParts(3, Direction.SOUTH, 5, Direction.NORTH);
            if (is3DCube) {
                connectMapParts(0, Direction.NORTH, 5, Direction.WEST);
                connectMapParts(0, Direction.WEST, 3, Direction.WEST);
                connectMapParts(1, Direction.NORTH, 5, Direction.SOUTH);
                connectMapParts(1, Direction.EAST, 4, Direction.EAST);
                connectMapParts(1, Direction.SOUTH, 2, Direction.EAST);
                connectMapParts(2, Direction.WEST, 3, Direction.NORTH);
                connectMapParts(4, Direction.SOUTH, 5, Direction.EAST);
            } else {
                connectMapParts(0, Direction.NORTH, 5, Direction.SOUTH);
                connectMapParts(0, Direction.WEST, 1, Direction.EAST);
                connectMapParts(1, Direction.NORTH, 1, Direction.SOUTH);
                connectMapParts(2, Direction.WEST, 2, Direction.EAST);
                connectMapParts(3, Direction.WEST, 4, Direction.EAST);
                connectMapParts(3, Direction.NORTH, 4, Direction.SOUTH);
                connectMapParts(5, Direction.WEST, 5, Direction.EAST);
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    private void connectMapParts(int mapPartIndex1, Direction connectedBorder1, int mapPartIndex2, Direction connectedBorder2) {
        MapPart mapPart1 = mapPartList.get(mapPartIndex1);
        MapPart mapPart2 = mapPartList.get(mapPartIndex2);
        mapPart1.connectedMapParts.put(connectedBorder1, Tuple.tuple(mapPart2, connectedBorder2));
        mapPart2.connectedMapParts.put(connectedBorder2, Tuple.tuple(mapPart1, connectedBorder1));
    }


    private static class State {
        private final MapPart side;
        private final Tuple2<Integer, Integer> pos;
        private final Direction dir;

        private State(MapPart side, Tuple2<Integer, Integer> pos, Direction dir) {
            this.side = side;
            this.pos = pos;
            this.dir = dir;
        }

    }

    private static class MapPart {

        private final Map<Direction, List<Tuple2<Integer, Integer>>> borders = new HashMap<>();
        private final Map<Direction, Tuple2<MapPart, Direction>> connectedMapParts = new HashMap<>();
        private final Tuple2<Integer, Integer> positionOnMap;

        private MapPart(int sideLength, int x, int y) {
            borders.put(Direction.WEST, Util.drawLine(Tuple.tuple(x * sideLength, y * sideLength), Tuple.tuple(x * sideLength, y * sideLength + sideLength - 1)));
            borders.put(Direction.EAST, Util.drawLine(Tuple.tuple(x * sideLength + sideLength - 1, y * sideLength), Tuple.tuple(x * sideLength + sideLength - 1, y * sideLength + sideLength - 1)));
            borders.put(Direction.NORTH, Util.drawLine(Tuple.tuple(x * sideLength, y * sideLength), Tuple.tuple(x * sideLength + sideLength - 1, y * sideLength)));
            borders.put(Direction.SOUTH, Util.drawLine(Tuple.tuple(x * sideLength, y * sideLength + sideLength - 1), Tuple.tuple(x * sideLength + sideLength - 1, y * sideLength + sideLength - 1)));
            this.positionOnMap = Tuple.tuple(x, y);
        }

    }


}
