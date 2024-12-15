package de.beachboys.aoc2024;

import de.beachboys.Day;
import de.beachboys.Direction;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple2;

import java.util.*;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class Day15 extends Day {

    private Map<Tuple2<Integer, Integer>, String> map = new HashMap<>();
    private final List<Direction> dirs = new ArrayList<>();
    public Object part1(List<String> input) {
        return runLogic(input, UnaryOperator.identity());
    }

    public Object part2(List<String> input) {
        return runLogic(input, Day15::transformMapLinePart2);
    }

    private long runLogic(List<String> input, UnaryOperator<String> mapLineTransformer) {
        parseInput(input, mapLineTransformer);

        Tuple2<Integer, Integer> robotPos = map.entrySet().stream().filter(e -> e.getValue().equals("@")).map(Map.Entry::getKey).findFirst().orElseThrow();
        for (Direction dir : dirs) {
            Set<Tuple2<Integer, Integer>> collectedPositionsToMove = new HashSet<>();
            if (canMove(robotPos, dir, collectedPositionsToMove)) {
                Map<Tuple2<Integer, Integer>, String> mapChanges = new HashMap<>();
                for (Tuple2<Integer, Integer> positionToMove : collectedPositionsToMove) {
                    mapChanges.put(dir.move(positionToMove, 1), map.get(positionToMove));
                }
                for (Tuple2<Integer, Integer> positionToMove : collectedPositionsToMove) {
                    if (!mapChanges.containsKey(positionToMove)) {
                        mapChanges.put(positionToMove, ".");
                    }
                }
                map.putAll(mapChanges);
                robotPos = dir.move(robotPos, 1);
            }
        }

        io.logDebug(Util.paintMap(map));

        return getSumOfGPSCoordinatesOfBoxes();
    }

    private boolean canMove(Tuple2<Integer, Integer> pos, Direction dir, Set<Tuple2<Integer, Integer>> collectedPositionsToMove) {
        Tuple2<Integer, Integer> targetPosition = dir.move(pos, 1);
        String characterAtTargetPosition = map.get(targetPosition);
        boolean canMove = switch(characterAtTargetPosition) {
            case "." -> true;
            case "O" -> canMove(targetPosition, dir, collectedPositionsToMove);
            case "[", "]" -> {
                if (Direction.NORTH.equals(dir) || Direction.SOUTH.equals(dir)) {
                    Direction dirOfOtherPart = "[".equals(characterAtTargetPosition) ? Direction.EAST : Direction.WEST;
                    Tuple2<Integer, Integer> otherPartPos = dirOfOtherPart.move(targetPosition, 1);
                    yield canMove(targetPosition, dir, collectedPositionsToMove) && canMove(otherPartPos, dir, collectedPositionsToMove);
                } else {
                    yield canMove(targetPosition, dir, collectedPositionsToMove);
                }
            }
            default -> false;
        };
        if (canMove) {
            collectedPositionsToMove.add(pos);
            return true;
        }
        return false;
    }

    private long getSumOfGPSCoordinatesOfBoxes() {
        long result = 0;
        for (Map.Entry<Tuple2<Integer, Integer>, String> mapEntry : map.entrySet()) {
            if ("O".equals(mapEntry.getValue()) || "[".equals(mapEntry.getValue())) {
                result += mapEntry.getKey().v1 + mapEntry.getKey().v2 * 100;
            }
        }
        return result;
    }

    private void parseInput(List<String> input, UnaryOperator<String> mapLineTransformer) {
        boolean parsingOfMapDone = false;
        dirs.clear();
        List<String> inputMap = new ArrayList<>();
        for (String line : input) {
            if (line.isEmpty()) {
                map = Util.buildImageMap(inputMap);
                parsingOfMapDone = true;
            } else if (parsingOfMapDone) {
                dirs.addAll(Arrays.stream(line.split(Pattern.quote(""))).map(Direction::fromString).toList());
            } else {
                inputMap.add(mapLineTransformer.apply(line));
            }
        }
    }

    private static String transformMapLinePart2(String line) {
        StringBuilder transformedLine = new StringBuilder();
        for (String character : line.split("")) {
            transformedLine.append(switch(character) {
                case "#" -> "##";
                case "O" -> "[]";
                case "." -> "..";
                case "@" -> "@.";
                default -> throw new IllegalArgumentException();
            });
        }
        return transformedLine.toString();
    }

}
