package de.beachboys.aoc2016;

import de.beachboys.Day;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day11 extends Day {

    enum Type {
        RTG, MICROCHIP;

        Type other() {
            if (this == RTG) {
                return MICROCHIP;
            }
            return RTG;
        }
    }

    private final Deque<State> stateQueueToCheck = new LinkedList<>();

    private final Set<State> allEverQueuedStates = new HashSet<>();

    public Object part1(List<String> input) {
        return runLogic(input, state -> {});
    }

    public Object part2(List<String> input) {
        return runLogic(input, this::manipulateInitialStatePart2);
    }

    private Object runLogic(List<String> input, Consumer<State> manipulateInitialState) {
        stateQueueToCheck.clear();
        allEverQueuedStates.clear();
        State initialState = buildInitialState(input);
        manipulateInitialState.accept(initialState);
        queueState(initialState);
        while (!stateQueueToCheck.isEmpty()) {
            State stateToCheck = stateQueueToCheck.poll();
            if (queueFollowUpStatesAndCheckForFinalState(stateToCheck)) {
                return stateToCheck.stepsUntilThisState + 1;
            }
        }
        return "not possible";
    }

    private void manipulateInitialStatePart2(State initialState) {
        initialState.stuffPerFloor.get(1).addAll(List.of(
                Tuple.tuple(Type.RTG, "elerium"),
                Tuple.tuple(Type.MICROCHIP, "elerium"),
                Tuple.tuple(Type.RTG, "dilithium"),
                Tuple.tuple(Type.MICROCHIP, "dilithium")));
    }

    private void queueState(State state) {
        stateQueueToCheck.add(state);
        allEverQueuedStates.add(state);
    }

    private boolean queueFollowUpStatesAndCheckForFinalState(State state) {
        Set<Set<Tuple2<Type, String>>> stuffToMovePossibilities = getStuffToMovePossibilities(state);
        boolean moveDown = state.elevatorFloor > 1 && state.stuffPerFloor.entrySet().stream().filter(e -> e.getKey() < state.elevatorFloor).mapToInt(e -> e.getValue().size()).sum() > 0;
        boolean moveUp = state.elevatorFloor < 4;
        return (moveUp && queueFollowUpStatesAndCheckForFinalState(state, stuffToMovePossibilities, floor -> floor + 1))
            || (moveDown && queueFollowUpStatesAndCheckForFinalState(state, stuffToMovePossibilities, floor -> floor - 1));
    }

    private boolean queueFollowUpStatesAndCheckForFinalState(State state, Set<Set<Tuple2<Type, String>>> stuffToMovePossibilities, Function<Integer, Integer> elevatorFloorManipulator) {
        for (Set<Tuple2<Type, String>> stuffToMovePossibility : stuffToMovePossibilities) {
            if (queueFollowUpStateAndCheckForFinalState(state, stuffToMovePossibility, elevatorFloorManipulator)) {
                return true;
            }
        }
        return false;
    }

    private boolean queueFollowUpStateAndCheckForFinalState(State state, Set<Tuple2<Type, String>> stuffToMovePossibility, Function<Integer, Integer> elevatorFloorManipulator) {
        State copy = state.copy();
        copy.stepsUntilThisState++;
        copy.elevatorFloor = elevatorFloorManipulator.apply(copy.elevatorFloor);
        copy.stuffPerFloor.get(state.elevatorFloor).removeAll(stuffToMovePossibility);
        copy.stuffPerFloor.get(copy.elevatorFloor).addAll(stuffToMovePossibility);
        if (isFinalState(copy)) {
            return true;
        }
        if (isValidState(copy) && !allEverQueuedStates.contains(copy)) {
            queueState(copy);
        }
        return false;
    }

    private Set<Set<Tuple2<Type, String>>> getStuffToMovePossibilities(State state) {
        Set<Set<Tuple2<Type, String>>> stuffToMovePossibilities = new HashSet<>();
        Set<Tuple2<Type, String>> possibleElements = state.stuffPerFloor.get(state.elevatorFloor);
        for (Tuple2<Type, String> element : possibleElements) {
            if (!containsEquivalentPossibilities(stuffToMovePossibilities, possibleElements, element)) {
                stuffToMovePossibilities.add(Set.of(element));
                for (Tuple2<Type, String> element2 : possibleElements) {
                    if (element != element2 && (element.v1 == element2.v1 || element.v2.equals(element2.v2))) {
                        stuffToMovePossibilities.add(Set.of(element, element2));
                    }
                }
            }
        }
        return stuffToMovePossibilities;
    }

    private boolean containsEquivalentPossibilities(Set<Set<Tuple2<Type, String>>> stuffToMovePossibilities, Set<Tuple2<Type, String>> possibleElements, Tuple2<Type, String> element) {
        return containsPartnerElement(possibleElements, element) && containsPartnerElementPairSet(stuffToMovePossibilities);
    }

    private boolean containsPartnerElement(Set<Tuple2<Type, String>> possibleElements, Tuple2<Type, String> element) {
        return possibleElements.contains(Tuple.tuple(element.v1.other(), element.v2));
    }

    private boolean containsPartnerElementPairSet(Set<Set<Tuple2<Type, String>>> setOfElementSets) {
        return setOfElementSets.stream().anyMatch(set -> set.size() == 2 && set.stream().map(Tuple2::v2).distinct().count() == 1);
    }

    private boolean isValidState(State state) {
        for (Set<Tuple2<Type, String>> stuff : state.stuffPerFloor.values()) {
            for (Tuple2<Type, String> element : stuff) {
                if (element.v1 == Type.MICROCHIP && !stuff.contains(Tuple.tuple(Type.RTG, element.v2)) && stuff.stream().anyMatch(e -> e.v1 == Type.RTG)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isFinalState(State state) {
        return state.stuffPerFloor.get(1).isEmpty() && state.stuffPerFloor.get(2).isEmpty() && state.stuffPerFloor.get(3).isEmpty();
    }

    private State buildInitialState(List<String> input) {
        State initialState = new State();
        Pattern p = Pattern.compile("The (first|second|third|fourth) floor contains (.+)");
        for (String line : input) {
            Matcher m = p.matcher(line);
            if (m.matches()) {
                int floor = getFloor(m.group(1));
                String content = m.group(2);
                if (!content.startsWith("nothing")) {
                    for (String contentPartLevel1 : content.split(",")) {
                        for (String contentPartWithPossibleExtraStuff : contentPartLevel1.split("and")) {
                            String contentPart = contentPartWithPossibleExtraStuff
                                    .replaceAll(Pattern.quote("-compatible"), "")
                                    .replaceAll(Pattern.quote("."), "")
                                    .replaceAll("a ", "")
                                    .trim();
                            if (!contentPart.isEmpty()) {
                                String[] elementAndType = contentPart.split(" ");
                                if ("generator".equals(elementAndType[1])) {
                                    initialState.stuffPerFloor.get(floor).add(Tuple.tuple(Type.RTG, elementAndType[0]));
                                } else {
                                    initialState.stuffPerFloor.get(floor).add(Tuple.tuple(Type.MICROCHIP, elementAndType[0]));
                                }
                            }
                        }
                    }
                }
            }
        }
        return initialState;
    }

    private int getFloor(String floorAsString) {
        return switch (floorAsString) {
            case "first" -> 1;
            case "second" -> 2;
            case "third" -> 3;
            case "fourth" -> 4;
            default -> throw new IllegalArgumentException();
        };
    }

    private static class State {

        int elevatorFloor = 1;

        int stepsUntilThisState = 0;

        final Map<Integer, Set<Tuple2<Type, String>>> stuffPerFloor = new HashMap<>();

        private String equivalenceRepresentation;

        public State() {
            for (int i = 1; i <= 4; i++) {
                stuffPerFloor.put(i, new HashSet<>());
            }
        }

        public String getEquivalenceRepresentation() {
            if (equivalenceRepresentation == null) {
                buildEquivalenceRepresentation();
            }
            return equivalenceRepresentation;
        }

        public State copy() {
            State copy = new State();
            copy.stepsUntilThisState = this.stepsUntilThisState;
            copy.elevatorFloor = this.elevatorFloor;
            for (Integer floor : this.stuffPerFloor.keySet()) {
                copy.stuffPerFloor.get(floor).addAll(this.stuffPerFloor.get(floor));
            }
            return copy;
        }

        private void buildEquivalenceRepresentation() {
            AtomicInteger idCounter = new AtomicInteger(0);
            Map<String, Integer> nameToIdMap = new HashMap<>();
            StringBuilder sb = new StringBuilder();
            sb.append(elevatorFloor).append("|");
            for (int floor = 1; floor <= 4; floor++) {
                appendFloorEquivalenceRepresentation(sb, floor, nameToIdMap, idCounter);
            }
            equivalenceRepresentation = sb.toString();
        }

        private void appendFloorEquivalenceRepresentation(StringBuilder sb, int floor, Map<String, Integer> nameToIdMap, AtomicInteger idCounter) {
            List<Integer> rtgIds = getIdsFromContentOfFloor(floor, Type.RTG, nameToIdMap, idCounter);
            List<Integer> microChipIds = getIdsFromContentOfFloor(floor, Type.MICROCHIP, nameToIdMap, idCounter);
            sb.append(floor).append(":");
            sb.append(rtgIds.stream().sorted().map(String::valueOf).collect(Collectors.joining(",")));
            sb.append(";");
            sb.append(microChipIds.stream().sorted().map(String::valueOf).collect(Collectors.joining(",")));
            sb.append("|");
        }

        private List<Integer> getIdsFromContentOfFloor(int floor, Type type, Map<String, Integer> nameToIdMap, AtomicInteger idCounter) {
            List<String> elementNames = stuffPerFloor.get(floor).stream().filter(p -> p.v1 == type).map(Tuple2::v2).sorted().toList();
            List<Integer> ids = new ArrayList<>();
            for (String elementName : elementNames) {
                if (!nameToIdMap.containsKey(elementName)) {
                    nameToIdMap.put(elementName, idCounter.incrementAndGet());
                }
                ids.add(nameToIdMap.get(elementName));
            }
            return ids;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            State state = (State) o;
            return getEquivalenceRepresentation().equals(state.getEquivalenceRepresentation());
        }

        @Override
        public int hashCode() {
            return getEquivalenceRepresentation().hashCode();
        }
    }

}
