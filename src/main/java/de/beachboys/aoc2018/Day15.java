package de.beachboys.aoc2018;

import de.beachboys.Day;
import de.beachboys.Direction;
import de.beachboys.Util;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.*;
import java.util.stream.Collectors;

public class Day15 extends Day {
    private Map<Pair<Integer, Integer>, String> map;
    private final List<Unit> units = new ArrayList<>();
    private int elfDamage = 3;

    public Object part1(List<String> input) {
        return runFight(input, false);
    }

    public Object part2(List<String> input) {
        int returnValue = -1;
        while (returnValue == -1) {
            returnValue = runFight(input, true);
            elfDamage++;
        }
        return returnValue;
    }

    private int runFight(List<String> input, boolean returnMinusOneOnKilledElf) {
        prepareMapAndCharacters(input);
        int roundCounter = 0;
        while (areBothFractionsAlive()) {
            roundCounter++;
            units.sort(Unit::compareToByPosition);
            for (int i = 0; i < units.size(); i++) {
                Unit unit = units.get(i);
                if (unit.hitPoints > 0) {
                    if (getAdjacentTargets(unit).isEmpty()) {
                        move(unit);
                    }
                    boolean killedTarget = attack(unit);
                    if (killedTarget && unit.isGoblin && returnMinusOneOnKilledElf) {
                        return -1;
                    }
                    if (killedTarget && i < units.size() - 1 && isOtherFractionDead(unit)) {
                        // not a full round => adjust counter and exit loop
                        roundCounter--;
                        break;
                    }
                }
            }
            units.removeIf(unit -> unit.hitPoints <= 0);
        }
        return roundCounter * units.stream().mapToInt(unit -> unit.hitPoints).sum();
    }

    private void prepareMapAndCharacters(List<String> input) {
        map = Util.buildImageMap(input);
        units.clear();
        map.entrySet().stream().filter(entry -> "G".equals(entry.getValue()) || "E".equals(entry.getValue())).forEach(entry -> units.add(new Unit("G".equals(entry.getValue()), entry.getKey())));
    }

    private boolean attack(Unit attacker) {
        List<Unit> adjacentTargets = getAdjacentTargets(attacker);
        if (adjacentTargets.isEmpty()) {
            return false;
        }
        Unit target = adjacentTargets.stream().min(Unit::compareToByHitPointsAndPosition).orElseThrow();
        target.hitPoints -= attacker.isGoblin ? 3 : elfDamage;
        if (target.hitPoints <= 0) {
            map.put(target.position, ".");
        }
        return target.hitPoints <= 0;
    }

    private void move(Unit unit) {
        Deque<Triplet<Pair<Integer, Integer>, Direction, Integer>> queue = new LinkedList<>();
        Set<Pair<Integer, Integer>> seenPositions = new HashSet<>();
        Set<Triplet<Pair<Integer, Integer>, Direction, Integer>> possibleTargetQueueElements = new HashSet<>();
        int shortestRange = Integer.MAX_VALUE;

        Pair<Integer, Integer> position = unit.position;
        for (Direction direction : getDirectionsInReadingOrder()) {
            Pair<Integer, Integer> nextPosition = direction.move(position, 1);
            if (".".equals(map.get(nextPosition))) {
                queue.add(Triplet.with(nextPosition, direction, 1));
            }
        }
        seenPositions.add(position);

        while (!queue.isEmpty()) {
            Triplet<Pair<Integer, Integer>, Direction, Integer> queueElement = queue.poll();
            if (shortestRange >= queueElement.getValue2()) {
                if (!getAdjacentTargets(unit.isGoblin, queueElement.getValue0()).isEmpty()) {
                    shortestRange = queueElement.getValue2();
                    possibleTargetQueueElements.add(queueElement);
                } else {
                    for (Direction direction : getDirectionsInReadingOrder()) {
                        Pair<Integer, Integer> nextPosition = direction.move(queueElement.getValue0(), 1);
                        String nextPositionString = map.get(nextPosition);
                        if (".".equals(nextPositionString) && !seenPositions.contains(nextPosition)) {
                            seenPositions.add(nextPosition);
                            queue.add(Triplet.with(nextPosition, queueElement.getValue1(), queueElement.getValue2() + 1));
                        }
                    }
                }
            }
        }
        possibleTargetQueueElements.stream()
                .min(Comparator.comparing(queueElement -> queueElement.getValue2() * 10000 + queueElement.getValue0().getValue1() * 100 + queueElement.getValue0().getValue0()))
                .ifPresent(queueElement -> executeMove(unit, queueElement));
    }

    private void executeMove(Unit unit, Triplet<Pair<Integer, Integer>, Direction, Integer> queueElement) {
        Pair<Integer, Integer> newPosition = queueElement.getValue1().move(unit.position, 1);
        map.put(unit.position, ".");
        unit.position = newPosition;
        map.put(unit.position, unit.isGoblin ? "G" : "E");
    }

    private List<Direction> getDirectionsInReadingOrder() {
        return List.of(Direction.NORTH, Direction.WEST, Direction.EAST, Direction.SOUTH);
    }

    private List<Unit> getAdjacentTargets(Unit unit) {
        return getAdjacentTargets(unit.isGoblin, unit.position);
    }

    private List<Unit> getAdjacentTargets(boolean isCurrentUnitGoblin, Pair<Integer, Integer> currentPosition) {
        List<Pair<Integer, Integer>> adjacentPositions = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            adjacentPositions.add(direction.move(currentPosition, 1));
        }
        return units.stream().filter(otherUnit -> otherUnit.isGoblin != isCurrentUnitGoblin && otherUnit.hitPoints > 0 && adjacentPositions.contains(otherUnit.position)).collect(Collectors.toList());
    }

    private boolean areBothFractionsAlive() {
        return units.stream().map(unit -> unit.isGoblin).distinct().count() > 1;
    }

    private boolean isOtherFractionDead(Unit unit) {
        return units.stream().noneMatch(otherUnit -> otherUnit.isGoblin != unit.isGoblin && otherUnit.hitPoints > 0);
    }

    private static class Unit {
        boolean isGoblin;
        int hitPoints = 200;
        Pair<Integer, Integer> position;

        public Unit(boolean isGoblin, Pair<Integer, Integer> position) {
            this.isGoblin = isGoblin;
            this.position = position;
        }

        public int compareToByPosition(Unit o) {
            int yComparison = position.getValue1().compareTo(o.position.getValue1());
            if (yComparison == 0) {
                return position.getValue0().compareTo(o.position.getValue0());
            }
            return yComparison;
        }

        public int compareToByHitPointsAndPosition(Unit o) {
            int hitPointComparison = Integer.compare(hitPoints, o.hitPoints);
            if (hitPointComparison == 0) {
                return compareToByPosition(o);
            }
            return hitPointComparison;
        }
    }
}
