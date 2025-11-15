package de.beachboys.ec2025;

import de.beachboys.Direction;
import de.beachboys.Quest;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;
import org.jooq.lambda.tuple.Tuple3;

import java.util.*;
import java.util.stream.Collectors;

public class Quest10 extends Quest {

    private final Map<Tuple3<Set<Tuple2<Integer, Integer>>, Tuple2<Integer, Integer>, Boolean>, Long> uniqueSequenceCache = new HashMap<>();
    private Map<Tuple2<Integer, Integer>, String> map;
    private Set<Tuple2<Integer, Integer>> hiding;
    private Set<Tuple2<Integer, Integer>> sheepStartPositions;
    private Tuple2<Integer, Integer> dragonStartPosition;

    @Override
    public Object part1(List<String> input) {
        int rounds = Util.getIntValueFromUser("Number of rounds", 4, io);
        parseInput(input);

        Set<Tuple2<Integer, Integer>> dragonPositions = new HashSet<>();
        dragonPositions.add(dragonStartPosition);
        Set<Tuple2<Integer, Integer>> reachedDragonPositions = new HashSet<>(dragonPositions);
        for (int i = 0; i < rounds; i++) {
            Set<Tuple2<Integer, Integer>> newDragonPositions = new HashSet<>();
            for (Tuple2<Integer, Integer> dragon : dragonPositions) {
                newDragonPositions.addAll(getNextDragonPositions(dragon));
            }
            reachedDragonPositions.addAll(newDragonPositions);
            dragonPositions = newDragonPositions;
        }

        sheepStartPositions.retainAll(reachedDragonPositions);
        return sheepStartPositions.size();
    }

    @Override
    public Object part2(List<String> input) {
        int rounds = Util.getIntValueFromUser("Number of rounds", 20, io);
        parseInput(input);

        int savedSheep = 0;
        Set<Tuple2<Integer, Integer>> dragonPositions = new HashSet<>();
        dragonPositions.add(dragonStartPosition);
        Set<Tuple2<Integer, Integer>> sheepPositions = new HashSet<>(sheepStartPositions);
        for (int i = 0; i < rounds; i++) {
            Set<Tuple2<Integer, Integer>> newDragonPositions = new HashSet<>();
            for (Tuple2<Integer, Integer> dragon : dragonPositions) {
                newDragonPositions.addAll(getNextDragonPositions(dragon));
            }

            eatSheep(newDragonPositions, sheepPositions);

            Set<Tuple2<Integer, Integer>> newSheepPositions = new HashSet<>();
            for (Tuple2<Integer, Integer> sheep : sheepPositions) {
                Tuple2<Integer, Integer> newSheepPos = Direction.SOUTH.move(sheep, 1);
                if (map.containsKey(newSheepPos)) {
                    newSheepPositions.add(newSheepPos);
                } else {
                    savedSheep++;
                }
            }

            eatSheep(newDragonPositions, newSheepPositions);

            sheepPositions = newSheepPositions;
            dragonPositions = newDragonPositions;
        }

        return sheepStartPositions.size() - sheepPositions.size() - savedSheep;
    }

    @Override
    public Object part3(List<String> input) {
        parseInput(input);
        uniqueSequenceCache.clear();
        return getUniqueSequences(sheepStartPositions, dragonStartPosition, true);
     }
    private long getUniqueSequences(Set<Tuple2<Integer, Integer>> sheepPositions, Tuple2<Integer, Integer> dragonPosition, boolean nextTurnIsSheep) {
        Tuple3<Set<Tuple2<Integer, Integer>>,Tuple2<Integer, Integer>, Boolean> cacheKey = Tuple.tuple(sheepPositions, dragonPosition, nextTurnIsSheep);
        if (uniqueSequenceCache.containsKey(cacheKey)) {
            return uniqueSequenceCache.get(cacheKey);
        }
        if (sheepPositions.isEmpty()) {
            return 1;
        }
        long result = 0;
        if (nextTurnIsSheep) {
            boolean sheepCanMove = false;
            for (Tuple2<Integer, Integer> sheep : sheepPositions) {
                Tuple2<Integer, Integer> newSheepPos = Direction.SOUTH.move(sheep, 1);
                if (!map.containsKey(newSheepPos)) {
                    sheepCanMove = true;
                }
                if (map.containsKey(newSheepPos) && (!dragonPosition.equals(newSheepPos) || hiding.contains(newSheepPos))) {
                    Set<Tuple2<Integer, Integer>> newSheep = new HashSet<>(sheepPositions);
                    newSheep.remove(sheep);
                    newSheep.add(newSheepPos);
                    result += getUniqueSequences(newSheep, dragonPosition, false);
                    sheepCanMove = true;
                }
            }
            if (!sheepCanMove) {
                result += getUniqueSequences(sheepPositions, dragonPosition, false);
            }
        } else {
            for (Tuple2<Integer, Integer> newDragonPos : getNextDragonPositions(dragonPosition)) {
                if (map.containsKey(newDragonPos)) {
                    Set<Tuple2<Integer, Integer>> newSheep = sheepPositions;
                    if (!hiding.contains(newDragonPos) && sheepPositions.contains(newDragonPos)) {
                        newSheep = new HashSet<>(sheepPositions);
                        newSheep.remove(newDragonPos);
                    }
                    result += getUniqueSequences(newSheep, newDragonPos, true);
                }
            }
        }

        uniqueSequenceCache.put(cacheKey, result);
        return result;
    }

    private Set<Tuple2<Integer, Integer>> getNextDragonPositions(Tuple2<Integer, Integer> dragon) {
        Set<Tuple2<Integer, Integer>> nextDragonPos = new HashSet<>();
        for (Direction dir : Direction.values()) {
            Tuple2<Integer, Integer> tempNewPos = dir.move(dragon, 2);
            Tuple2<Integer, Integer> newPos1 = dir.turnLeft().move(tempNewPos, 1);
            Tuple2<Integer, Integer> newPos2 = dir.turnRight().move(tempNewPos, 1);
            if (map.containsKey(newPos1)) {
                nextDragonPos.add(newPos1);
            }
            if (map.containsKey(newPos2)) {
                nextDragonPos.add(newPos2);
            }
        }
        return nextDragonPos;
    }

    private void eatSheep(Set<Tuple2<Integer, Integer>> dragonPositions, Set<Tuple2<Integer, Integer>> sheepPositions) {
        for (Tuple2<Integer, Integer> dragon : dragonPositions) {
            if (!hiding.contains(dragon)) {
                sheepPositions.remove(dragon);
            }
        }
    }
    private void parseInput(List<String> input) {
        map = Util.buildImageMap(input);
        sheepStartPositions = map.entrySet().stream().filter(e -> e.getValue().equals("S")).map(Map.Entry::getKey).collect(Collectors.toSet());
        dragonStartPosition = map.entrySet().stream().filter(e -> e.getValue().equals("D")).map(Map.Entry::getKey).findFirst().orElseThrow();
        hiding = map.entrySet().stream().filter(e -> e.getValue().equals("#")).map(Map.Entry::getKey).collect(Collectors.toSet());
    }
}
