package de.beachboys.aoc2019;

import de.beachboys.Day;
import de.beachboys.Direction;
import de.beachboys.IOHelper;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Day15 extends Day {

    private class Position implements Comparable<Position> {

        public final Tuple2<Integer, Integer> pos;
        public final List<Position> pathFromStart;
        public boolean isWall;
        public final Map<Direction, Position> neighbors = new HashMap<>();

        public Position(Tuple2<Integer, Integer> pos, List<Position> parentPathFromStart) {
            this.pos = pos;
            this.pathFromStart = new ArrayList<>(parentPathFromStart);
            this.pathFromStart.add(this);
            if (positionMap != null) {
                for (Direction dir : Direction.values()) {
                    neighbors.put(dir, positionMap.get(dir.move(pos, 1)));
                }
            }
        }

        @Override
        public int compareTo(Position o) {
            return pos.compareTo(o.pos);
        }

        @Override
        public String toString() {
            return "{" + pos + " wall:" + isWall + '}';
        }
    }

    Map<Direction, String> commandMap = Map.of(Direction.NORTH, "1", Direction.SOUTH, "2", Direction.WEST, "3", Direction.EAST, "4");

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
        init(Tuple.tuple(0, 0));
        runComputer(list, this::handleFoundOxygenSystemPart1);
        paintMap();
        return oxygenSystemPosition.pathFromStart.size() - 1;
    }

    public Object part2(List<String> input) {
        List<Long> list = Util.parseLongCsv(input.getFirst());
        init(Tuple.tuple(0, 0));
        runComputer(list, this::handleFoundOxygenSystemPart2);
        paintMap();
        return positionsToInvestigate.lastKey() - 2;
    }

    private void init(Tuple2<Integer, Integer> initialPos) {
        lastOutput = -1;
        currentPosition = new Position(initialPos, new Stack<>());
        positionMap = new HashMap<>(Map.of(initialPos, currentPosition));
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
        return commandMap.get(Direction.NORTH);
    }

    private String handleFoundOxygenSystemPart2() {
        if (oxygenSystemPosition != null) {
            return handleSuccessfulMove();
        } else {
            oxygenSystemPosition = nextPosition;
            init(oxygenSystemPosition.pos);
            return investigateCurrentPosition();
        }
    }

    private String investigateCurrentPosition() {
        List<Direction> dirs = getPossibleTargetFromCurrentPosition();
        if (!dirs.isEmpty()) {
            Direction dir = dirs.getFirst();
            prepareMoveToDirectionWithUnknownTarget(dir);
            return commandMap.get(dir);
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
        return commandMap.get(getDirectionForPosition(nextPosition));
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
            Position nextPosition = positionMap.get(dir.move(position.pos, 1));
            if (nextPosition != null) {
                position.neighbors.put(dir, nextPosition);
                nextPosition.neighbors.put(dir.getOpposite(), position);
            } else {
                dirs.add(dir);
            }
        }
        return dirs;
    }

    private void prepareMoveToDirectionWithUnknownTarget(Direction dir) {
        Tuple2<Integer, Integer> newPos = dir.move(currentPosition.pos, 1);
        nextPosition = new Position(newPos, currentPosition.pathFromStart);
        currentPosition.neighbors.put(dir, nextPosition);
        positionMap.put(newPos, nextPosition);
    }

    private Set<Position> getSetForPositionToInvestigate(int size) {
        if (!positionsToInvestigate.containsKey(size)) {
            positionsToInvestigate.put(size, new TreeSet<>());
        }
        return positionsToInvestigate.get(size);

    }
}
