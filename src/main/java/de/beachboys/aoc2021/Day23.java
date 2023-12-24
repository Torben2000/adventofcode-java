package de.beachboys.aoc2021;

import de.beachboys.Day;

import java.util.*;

public class Day23 extends Day {

    private static final Map<Integer, Integer> TARGET_COLUMNS_BY_COST = new HashMap<>();

    private static final Map<Character, Integer> CHARACTER_TO_COST = new HashMap<>();

    static {
        TARGET_COLUMNS_BY_COST.put(1, 2);
        TARGET_COLUMNS_BY_COST.put(10, 4);
        TARGET_COLUMNS_BY_COST.put(100, 6);
        TARGET_COLUMNS_BY_COST.put(1000, 8);

        CHARACTER_TO_COST.put('D', 1000);
        CHARACTER_TO_COST.put('C', 100);
        CHARACTER_TO_COST.put('B', 10);
        CHARACTER_TO_COST.put('A', 1);
    }

    private int minimalCost;

    private int depth;

    private final Set<State> seenStates = new HashSet<>();

    public Object part1(List<String> input) {
        Set<Piece> pieces = new HashSet<>();
        pieces.add(new Piece(CHARACTER_TO_COST.get(input.get(2).charAt(3)), 21));
        pieces.add(new Piece(CHARACTER_TO_COST.get(input.get(3).charAt(3)), 22));
        pieces.add(new Piece(CHARACTER_TO_COST.get(input.get(2).charAt(5)), 41));
        pieces.add(new Piece(CHARACTER_TO_COST.get(input.get(3).charAt(5)), 42));
        pieces.add(new Piece(CHARACTER_TO_COST.get(input.get(2).charAt(7)), 61));
        pieces.add(new Piece(CHARACTER_TO_COST.get(input.get(3).charAt(7)), 62));
        pieces.add(new Piece(CHARACTER_TO_COST.get(input.get(2).charAt(9)), 81));
        pieces.add(new Piece(CHARACTER_TO_COST.get(input.get(3).charAt(9)), 82));

        depth = 2;
        return runLogic(pieces);
    }

    public Object part2(List<String> input) {
        Set<Piece> pieces = new HashSet<>();
        pieces.add(new Piece(CHARACTER_TO_COST.get(input.get(2).charAt(3)), 21));
        pieces.add(new Piece(CHARACTER_TO_COST.get(input.get(3).charAt(3)), 24));
        pieces.add(new Piece(CHARACTER_TO_COST.get(input.get(2).charAt(5)), 41));
        pieces.add(new Piece(CHARACTER_TO_COST.get(input.get(3).charAt(5)), 44));
        pieces.add(new Piece(CHARACTER_TO_COST.get(input.get(2).charAt(7)), 61));
        pieces.add(new Piece(CHARACTER_TO_COST.get(input.get(3).charAt(7)), 64));
        pieces.add(new Piece(CHARACTER_TO_COST.get(input.get(2).charAt(9)), 81));
        pieces.add(new Piece(CHARACTER_TO_COST.get(input.get(3).charAt(9)), 84));
        pieces.add(new Piece(1000, 22));
        pieces.add(new Piece(1000, 23));
        pieces.add(new Piece(100, 42));
        pieces.add(new Piece(10, 43));
        pieces.add(new Piece(10, 62));
        pieces.add(new Piece(1, 63));
        pieces.add(new Piece(1, 82));
        pieces.add(new Piece(100, 83));

        depth = 4;
        return runLogic(pieces);
    }

    private int runLogic(Set<Piece> pieces) {
        minimalCost = Integer.MAX_VALUE;
        seenStates.clear();
        solve(buildInitialState(pieces));
        return minimalCost;
    }

    private State buildInitialState(Set<Piece> pieces) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < 11; i++) {
            map.put(i, 0);
        }
        pieces.forEach(piece -> map.put(piece.position, piece.cost));
        pieces.forEach(piece -> updateTargetPositionReachedFlag(map, piece));
        return new State(map, pieces, 0);
    }

    private void solve(State state) {
        if (minimalCost < state.cost) {
            return;
        }
        if (seenStates.contains(state)) {
            return;
        }
        seenStates.add(state);
        int stateSolvedCount = 0;
        for (Piece piece : state.pieces) {
            if (piece.targetPositionReached) {
                stateSolvedCount++;
            } else if (piece.position <= 10) {
                moveToTargetPositionIfPossible(state, piece);
            } else {
                moveToHallwayIfPossible(state, piece);
            }
        }
        if (stateSolvedCount == 4 * depth) {
            minimalCost = Math.min(minimalCost, state.cost);
        }
    }

    private void moveToHallwayIfPossible(State state, Piece p) {
        int stepsToHallway = p.position % 10;
        for (int i = 1; i < stepsToHallway; i++) {
            if (state.map.get(p.position - i) != 0) {
                return;
            }
        }
        int currentColumn = p.position / 10;
        for (int targetColumn = currentColumn; targetColumn >= 0; targetColumn--) {
            if (targetColumn % 2 == 1 || targetColumn == 0) {
                if (state.map.get(targetColumn) == 0) {
                    solve(movePiece(state, p, targetColumn, Math.abs(currentColumn - targetColumn) + stepsToHallway, false));
                } else {
                    break;
                }
            }
        }
        for (int targetColumn = currentColumn; targetColumn < 11; targetColumn++) {
            if (targetColumn % 2 == 1 || targetColumn == 10) {
                if (state.map.get(targetColumn) == 0) {
                    solve(movePiece(state, p, targetColumn, Math.abs(currentColumn - targetColumn) + stepsToHallway, false));
                } else {
                    break;
                }
            }
        }
    }

    private void moveToTargetPositionIfPossible(State state, Piece p) {
        int totalStepsFromHallway = 0;
        int targetColumn = TARGET_COLUMNS_BY_COST.get(p.cost);
        boolean pieceFound = false;
        for (int currentStepsFromHallway = 1; currentStepsFromHallway <= depth; currentStepsFromHallway++) {
            int pieceAtPos = state.map.get(targetColumn * 10 + currentStepsFromHallway);
            if (pieceAtPos == 0) {
                if (pieceFound) {
                    return;
                }
                totalStepsFromHallway = currentStepsFromHallway;
            } else if (pieceAtPos != p.cost) {
                return;
            } else {
                pieceFound = true;
            }

        }
        if (p.position > targetColumn) {
            for (int i = targetColumn + 1; i < p.position; i = i + 2) {
                if (state.map.get(i) != 0) {
                    return;
                }
            }
        } else {
            for (int i = targetColumn - 1; i > p.position; i = i - 2) {
                if (state.map.get(i) != 0) {
                    return;
                }
            }
        }
        solve(movePiece(state, p, targetColumn * 10 + totalStepsFromHallway, Math.abs(targetColumn - p.position) + totalStepsFromHallway, true));
    }

    private void updateTargetPositionReachedFlag(Map<Integer, Integer> map, Piece piece) {
        boolean valueToSet = false;
        int column = piece.position / 10;
        if (column == TARGET_COLUMNS_BY_COST.get(piece.cost)) {
            valueToSet = true;
            for (int stepsFromHallway = piece.position % 10; stepsFromHallway <= depth; stepsFromHallway++) {
                if (map.get(column * 10 + stepsFromHallway) != piece.cost) {
                    valueToSet = false;
                    break;
                }
            }
        }
        piece.targetPositionReached = valueToSet;
    }

    private State movePiece(State state, Piece piece, int targetPosition, int stepsDone, boolean targetPositionReached) {
        State newState = new State(state);
        newState.map.put(piece.position, 0);
        newState.map.put(targetPosition, piece.cost);
        newState.pieces.remove(piece);
        newState.pieces.add(new Piece(piece.cost, targetPosition, targetPositionReached));
        newState.cost = state.cost + stepsDone * piece.cost;
        return newState;
    }

    private static class State {

        final Map<Integer, Integer> map;
        final Set<Piece> pieces;
        int cost;

        public State(Map<Integer, Integer> map, Set<Piece> pieces, int cost) {
            this.map = new HashMap<>(map);
            this.pieces = new HashSet<>(pieces);
            this.cost = cost;
        }
        public State(State state) {
            this(state.map, state.pieces, state.cost);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            State state = (State) o;
            return cost == state.cost && pieces.equals(state.pieces);
        }

        @Override
        public int hashCode() {
            return Objects.hash(pieces, cost);
        }
    }

    private static final class Piece {
        final int cost;
        final int position;
        boolean targetPositionReached = false;

        public Piece(int cost, int position) {
            this.cost = cost;
            this.position = position;
        }

        public Piece(int cost, int position, boolean targetPositionReached) {
            this(cost, position);
            this.targetPositionReached = targetPositionReached;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Piece piece = (Piece) o;
            return cost == piece.cost && position == piece.position;
        }
        @Override
        public int hashCode() {
            return Objects.hash(cost, position);
        }

    }

}
