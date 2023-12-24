package de.beachboys.aoc2018;

import de.beachboys.Day;
import de.beachboys.Direction;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.*;
import java.util.function.BooleanSupplier;
import java.util.stream.Collectors;

public class Day13 extends Day {

    private enum NextAction {
        TURN_LEFT, STRAIGHT, TURN_RIGHT
    }

    private static class Cart implements Comparable<Cart> {

        Tuple2<Integer, Integer> position;
        Direction currentDirection;
        NextAction nextAction = NextAction.TURN_LEFT;
        boolean hasCrashed = false;

        public Cart(Tuple2<Integer, Integer> position,  Direction currentDirection) {
            this.position = position;
            this.currentDirection = currentDirection;
        }

        @Override
        public int compareTo(Cart o) {
            int yComparison = position.v2.compareTo(o.position.v2);
            if (yComparison == 0) {
                return position.v1.compareTo(o.position.v1);
            }
            return yComparison;
        }
    }

    private final Map<Tuple2<Integer, Integer>, Set<Direction>> connections = new HashMap<>();

    private final List<Cart> carts = new ArrayList<>();

    private final Set<Tuple2<Integer, Integer>> currentCartPositions = new HashSet<>();

    private Tuple2<Integer, Integer> firstCrashPosition;

    public Object part1(List<String> input) {
        runLogic(input, () -> firstCrashPosition == null);
        return firstCrashPosition.v1 + "," + firstCrashPosition.v2;
    }

    public Object part2(List<String> input) {
        runLogic(input, () -> carts.size() > 1);
        Cart finalCart = carts.stream().filter(cart -> !cart.hasCrashed).findAny().orElseThrow();
        return finalCart.position.v1 + "," + finalCart.position.v2;
    }

    private void runLogic(List<String> input, BooleanSupplier continueWithNextTick) {
        parseInput(input);
        runSimulation(continueWithNextTick);
    }

    private void runSimulation(BooleanSupplier continueWithNextTick) {
        while (continueWithNextTick.getAsBoolean()) {
            carts.sort(Cart::compareTo);
            for (Cart cart : carts) {
                if (!cart.hasCrashed) {
                    currentCartPositions.remove(cart.position);

                    cart.position = cart.currentDirection.move(cart.position, 1);

                    if (currentCartPositions.contains(cart.position)) {
                        handleCrash(cart);
                    } else {
                        currentCartPositions.add(cart.position);
                    }

                    Set<Direction> possibleDirections = connections.get(cart.position).stream().filter(direction -> direction != cart.currentDirection.getOpposite()).collect(Collectors.toSet());

                    if (possibleDirections.size() == 1) {
                        cart.currentDirection = possibleDirections.stream().findAny().orElseThrow();
                    } else if (possibleDirections.size() == 3) {
                        handleIntersection(cart);
                    } else {
                        throw new IllegalStateException();
                    }
                }
            }
            carts.removeIf(cart -> cart.hasCrashed);
        }
    }

    private void handleIntersection(Cart cart) {
        switch (cart.nextAction) {
            case TURN_LEFT:
                cart.nextAction = NextAction.STRAIGHT;
                cart.currentDirection = cart.currentDirection.turnLeft();
                break;
            case STRAIGHT:
                cart.nextAction = NextAction.TURN_RIGHT;
                break;
            case TURN_RIGHT:
                cart.nextAction = NextAction.TURN_LEFT;
                cart.currentDirection = cart.currentDirection.turnRight();
                break;
        }
    }

    private void handleCrash(Cart cart) {
        if (firstCrashPosition == null) {
            firstCrashPosition = cart.position;
        }
        cart.hasCrashed = true;
        carts.stream().filter(otherCart -> cart.position.equals(otherCart.position)).forEach(otherCart -> otherCart.hasCrashed = true);
        currentCartPositions.remove(cart.position);
    }

    private void parseInput(List<String> input) {
        connections.clear();
        carts.clear();
        currentCartPositions.clear();
        firstCrashPosition = null;
        for (int y = 0; y < input.size(); y++) {
            String line = input.get(y);
            for (int x = 0; x < line.length(); x++) {
                Set<Direction> connectionsAtPosition = new HashSet<>();
                switch (line.charAt(x)) {
                    case '+':
                        connectionsAtPosition.add(Direction.NORTH);
                        connectionsAtPosition.add(Direction.SOUTH);
                        connectionsAtPosition.add(Direction.WEST);
                        connectionsAtPosition.add(Direction.EAST);
                        break;
                    case '-':
                        connectionsAtPosition.add(Direction.WEST);
                        connectionsAtPosition.add(Direction.EAST);
                        break;
                    case '|':
                        connectionsAtPosition.add(Direction.NORTH);
                        connectionsAtPosition.add(Direction.SOUTH);
                        break;
                    case '\\':
                        if (connections.getOrDefault(Tuple.tuple(x - 1, y), Set.of()).contains(Direction.EAST)) {
                            connectionsAtPosition.add(Direction.WEST);
                            connectionsAtPosition.add(Direction.SOUTH);
                        } else {
                            connectionsAtPosition.add(Direction.NORTH);
                            connectionsAtPosition.add(Direction.EAST);
                        }
                        break;
                    case '/':
                        if (connections.getOrDefault(Tuple.tuple(x - 1, y), Set.of()).contains(Direction.EAST)) {
                            connectionsAtPosition.add(Direction.WEST);
                            connectionsAtPosition.add(Direction.NORTH);
                        } else {
                            connectionsAtPosition.add(Direction.SOUTH);
                            connectionsAtPosition.add(Direction.EAST);
                        }
                        break;
                    case '>':
                        connectionsAtPosition.add(Direction.WEST);
                        connectionsAtPosition.add(Direction.EAST);
                        carts.add(new Cart(Tuple.tuple(x, y), Direction.EAST));
                        break;
                    case '<':
                        connectionsAtPosition.add(Direction.WEST);
                        connectionsAtPosition.add(Direction.EAST);
                        carts.add(new Cart(Tuple.tuple(x, y), Direction.WEST));
                        break;
                    case 'v':
                        connectionsAtPosition.add(Direction.NORTH);
                        connectionsAtPosition.add(Direction.SOUTH);
                        carts.add(new Cart(Tuple.tuple(x, y), Direction.SOUTH));
                        break;
                    case '^':
                        connectionsAtPosition.add(Direction.NORTH);
                        connectionsAtPosition.add(Direction.SOUTH);
                        carts.add(new Cart(Tuple.tuple(x, y), Direction.NORTH));
                        break;

                }
                connections.put(Tuple.tuple(x, y), connectionsAtPosition);
            }
        }
    }

}
