package de.beachboys.aoc2019;

import de.beachboys.Day;
import de.beachboys.IOHelper;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Day15 extends Day {

    private enum Direction {
        NORTH(0, -1, 1), SOUTH(0, 1, 2), WEST(-1, 0, 3), EAST(1, 0, 4);

        public final String command;
        public final int stepX;
        public final int stepY;

        Direction(int stepX, int stepY, int command) {
            this.stepX = stepX;
            this.stepY = stepY;
            this.command = command + "";
        }
    }

    private class Position implements Comparable<Position> {

        public final int x;
        public final int y;
        public final List<Position> pathFromStart;
        public boolean isWall;
        public final Map<Direction, Position> neighbors = new HashMap<>();

        public Position(int x, int y, List<Position> parentPathFromStart) {
            this.x = x;
            this.y = y;
            this.pathFromStart = new ArrayList<>(parentPathFromStart);
            this.pathFromStart.add(this);
            if (positionMap != null) {
                for (Direction dir : Direction.values()) {
                    neighbors.put(dir, positionMap.get(Tuple.tuple(x + dir.stepX, y + dir.stepY)));
                }
            }
        }

        @Override
        public int compareTo(Position o) {
            return Tuple.tuple(x, y).compareTo(Tuple.tuple(o.x, o.y));
        }

        @Override
        public String toString() {
            return "{[" + x + ", " + y + "] wall:" + isWall + '}';
        }
    }

    private static final Map<Direction, Direction> OPPOSITE_DIRS = Map.of(
            Direction.NORTH, Direction.SOUTH,
            Direction.SOUTH, Direction.NORTH,
            Direction.WEST, Direction.EAST,
            Direction.EAST, Direction.WEST);

    private final IntcodeComputer computer = new IntcodeComputer();
    private int lastOutput;

    private Map<Tuple2<Integer, Integer>, Position> positionMap;
    private Position currentPosition;
    private Position nextPosition;

    private TreeMap<Integer, Set<Position>> positionsToInvestigate;
    private Position currentPositionToInvestigate;
    private List<Position> navigationPathToCurrentPositionToInvestigate;

    private Position oxygenSystemPosition;

    public Object part1(List<String> input) {
        List<Long> list = Util.parseLongCsv(input.getFirst());
        init(0, 0);
        runComputer(list, this::handleFoundOxygenSystemPart1);
        paintMap();
        return oxygenSystemPosition.pathFromStart.size() - 1;
    }

    public Object part2(List<String> input) {
        List<Long> list = Util.parseLongCsv(input.getFirst());
        init(0, 0);
        runComputer(list, this::handleFoundOxygenSystemPart2);
        paintMap();
        return positionsToInvestigate.lastKey() - 2;
    }

    private void init(int initialX, int initialY) {
        lastOutput = -1;
        currentPosition = new Position(initialX, initialY, new Stack<>());
        positionMap = new HashMap<>(Map.of(Tuple.tuple(initialX, initialY), currentPosition));
        positionsToInvestigate = new TreeMap<>(Map.of(0, new TreeSet<>(Set.of(currentPosition))));
        currentPositionToInvestigate = currentPosition;
        navigationPathToCurrentPositionToInvestigate = new ArrayList<>();
    }

    private void paintMap() {
        io.logDebug(Util.paintMap(positionMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().isWall ? "1" : "0")), Map.of("1", "#", "0", ".")));
    }

    private void runComputer(List<Long> list, Supplier<String> oxygenSystemFoundHandler) {
        IOHelper io = new IOHelper() {

            @Override
            public String getInput(String textToDisplay) {
                return switch (lastOutput) {
                    case -1 -> investigateCurrentPosition();
                    case 0 -> handleWallFound();
                    case 1 -> handleSuccessfulMove();
                    case 2 -> oxygenSystemFoundHandler.get();
                    default -> throw new IllegalStateException("Illegal system output");
                };

            }

            @Override
            public void logInfo(Object infoText) {
                lastOutput = Integer.parseInt(infoText.toString());
            }

            @Override
            public void logDebug(Object debugText) {
                // do nothing
            }
        };

        computer.runLogic(new ArrayList<>(list), io);
    }

    private String handleWallFound() {
        nextPosition.isWall = true;
        getSetForPositionToInvestigate(nextPosition.pathFromStart.size()).remove(nextPosition);
        return investigateCurrentPosition();
    }

    private String handleSuccessfulMove() {
        if (currentPositionToInvestigate != nextPosition && hasDirectionsToInvestigate(nextPosition)) {
            getSetForPositionToInvestigate(nextPosition.pathFromStart.size()).add(nextPosition);
        }
        currentPosition = nextPosition;
        return moveToNextPositionToInvestigate();
    }

    private String handleFoundOxygenSystemPart1() {
        oxygenSystemPosition = nextPosition;
        computer.setKillSwitch(true);
        // don't care about last direction
        return Direction.NORTH.command;
    }

    private String handleFoundOxygenSystemPart2() {
        if (oxygenSystemPosition != null) {
            return handleSuccessfulMove();
        } else {
            oxygenSystemPosition = nextPosition;
            init(oxygenSystemPosition.x, oxygenSystemPosition.y);
            return investigateCurrentPosition();
        }
    }

    private String investigateCurrentPosition() {
        List<Direction> dirs = getPossibleTargetFromCurrentPosition();
        if (!dirs.isEmpty()) {
            Direction dir = dirs.getFirst();
            prepareMoveToDirectionWithUnknownTarget(dir);
            return dir.command;
        } else {
            return moveToNextPositionToInvestigate();
        }
    }

    private String moveToNextPositionToInvestigate() {
        if (hasDirectionsToInvestigate(currentPositionToInvestigate)) {
            if (currentPosition.neighbors.containsValue(currentPositionToInvestigate)) {
                nextPosition = currentPositionToInvestigate;
                return getNavigationCommand();
            }
        } else {
            buildPathToNextPositionToInvestigate();
        }
        if (currentPositionToInvestigate == currentPosition) {
            return investigateCurrentPosition();
        }
        if (currentPosition.neighbors.containsValue(currentPositionToInvestigate)) {
            nextPosition = currentPositionToInvestigate;
            return getNavigationCommand();
        }
        return moveAlongPathToPositionToInvestigate();
    }

    private String getNavigationCommand() {
        return getDirectionForPosition(nextPosition).command;
    }

    private String moveAlongPathToPositionToInvestigate() {
        nextPosition = navigationPathToCurrentPositionToInvestigate.removeFirst();
        return getNavigationCommand();
    }

    private void buildPathToNextPositionToInvestigate() {
        findNewPositionToInvestigate();
        updateNavigationPathToCurrentPositionToInvestigate();
    }

    private void updateNavigationPathToCurrentPositionToInvestigate() {
        List<Position> pathCurrentPosition = currentPosition.pathFromStart;
        List<Position> pathToCurrentPositionToInvestigate = currentPositionToInvestigate.pathFromStart;
        int max = -1;
        int iToUse = -1;
        int jToUse = -1;
        for (int i = 0; i < pathCurrentPosition.size(); i++) {
            for (int j = 0; j < pathToCurrentPositionToInvestigate.size(); j++) {
                if (pathCurrentPosition.get(i).equals(pathToCurrentPositionToInvestigate.get(j))) {
                    if (max < i + j) {
                        max = i + j;
                        iToUse = i;
                        jToUse = j;
                    }
                }
            }
        }
        navigationPathToCurrentPositionToInvestigate = new ArrayList<>();
        for (int i = pathCurrentPosition.size() - 2; i > iToUse; i--) {
            navigationPathToCurrentPositionToInvestigate.add(pathCurrentPosition.get(i));
        }
        for (int j = jToUse; j < pathToCurrentPositionToInvestigate.size(); j++) {
            navigationPathToCurrentPositionToInvestigate.add(pathToCurrentPositionToInvestigate.get(j));
        }
    }

    private void findNewPositionToInvestigate() {
        boolean newPositionFound = false;
        for (Map.Entry<Integer, Set<Position>> entries : positionsToInvestigate.entrySet()) {
            for (Position position : entries.getValue()) {
                if (hasDirectionsToInvestigate(position)) {
                    currentPositionToInvestigate = position;
                    newPositionFound = true;
                    break;
                }
            }
            if (newPositionFound) {
                break;
            }
        }
        if (newPositionFound) {
            positionsToInvestigate.get(currentPositionToInvestigate.pathFromStart.size()).remove(currentPositionToInvestigate);
        } else {
            computer.setKillSwitch(true);
            // set any other target to keep moving for one more tick
            currentPositionToInvestigate = currentPosition.neighbors.values().stream().filter(Objects::nonNull).findFirst().orElseThrow();
        }
    }

    private Direction getDirectionForPosition(Position currentPositionToInvestigate) {
        return currentPosition.neighbors.entrySet().stream().filter(entry -> currentPositionToInvestigate.equals(entry.getValue())).map(Map.Entry::getKey).findFirst().orElseThrow();
    }

    private boolean hasDirectionsToInvestigate(Position position) {
        return !getPossibleTarget(position).isEmpty();
    }

    private List<Direction> getPossibleTargetFromCurrentPosition() {
        return getPossibleTarget(this.currentPosition);
    }

    private List<Direction> getPossibleTarget(Position position) {
        List<Direction> dirs = new ArrayList<>();
        for (Direction dir : Direction.values()) {
            int newX = position.x + dir.stepX;
            int newY = position.y + dir.stepY;
            Position nextPosition = positionMap.get(Tuple.tuple(newX, newY));
            if (nextPosition != null) {
                position.neighbors.put(dir, nextPosition);
                nextPosition.neighbors.put(OPPOSITE_DIRS.get(dir), position);
            } else {
                dirs.add(dir);
            }
        }
        return dirs;
    }

    private void prepareMoveToDirectionWithUnknownTarget(Direction dir) {
        int newX = currentPosition.x + dir.stepX;
        int newY = currentPosition.y + dir.stepY;
        nextPosition = new Position(newX, newY, currentPosition.pathFromStart);
        currentPosition.neighbors.put(dir, nextPosition);
        positionMap.put(Tuple.tuple(newX, newY), nextPosition);
    }

    private Set<Position> getSetForPositionToInvestigate(int size) {
        if (!positionsToInvestigate.containsKey(size)) {
            positionsToInvestigate.put(size, new TreeSet<>());
        }
        return positionsToInvestigate.get(size);

    }
}
