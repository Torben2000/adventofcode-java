package de.beachboys.aoc2016;

import de.beachboys.Day;
import de.beachboys.Direction;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day22 extends Day {

    private static class State {

        final Set<Tuple2<Integer, Integer>> emptyNodes;
        final Tuple2<Integer, Integer> nodeWithGoalData;
        final int stepsUntilThisState;
        String stringRepresentation;

        public State(Set<Tuple2<Integer, Integer>> emptyNodes, Tuple2<Integer, Integer> nodeWithGoalData, int stepsUntilThisState) {
            this.emptyNodes = emptyNodes;
            this.nodeWithGoalData = nodeWithGoalData;
            this.stepsUntilThisState = stepsUntilThisState;
        }

        public String getStringRepresentation() {
            if (stringRepresentation == null) {
                stringRepresentation = nodeWithGoalData.toString() + "|" + emptyNodes.stream().sorted().toList().toString();
            }
            return stringRepresentation;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            State state = (State) o;
            return getStringRepresentation().equals(state.getStringRepresentation());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getStringRepresentation());
        }

    }

    public Object part1(List<String> input) {
        Map<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>> nodes = parseNodes(input);

        long viablePairCount = 0L;
        for (Map.Entry<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>> innerNode : nodes.entrySet()) {
            if (innerNode.getValue().v1 > 0) {
                for (Map.Entry<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>> outerNode : nodes.entrySet()) {
                    if (innerNode.getKey() != outerNode.getKey() && innerNode.getValue().v1 <= outerNode.getValue().v2) {
                        viablePairCount++;
                    }
                }
            }
        }
        return viablePairCount;
    }

    private final Deque<State> queue = new LinkedList<>();
    private final Set<State> knownStates = new HashSet<>();
    private final Set<Tuple2<Integer, Integer>> allUsefulNodes = new HashSet<>();

    @Override
    public Object part2(List<String> input) {
        Map<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>> nodes = parseNodes(input);
        Tuple2<Integer, Integer> target = Tuple.tuple(0, 0);
        int targetSize = nodes.get(target).v1 + nodes.get(target).v2;
        int smallestData = nodes.values().stream().map(Tuple2::v1).filter(data -> data != 0).mapToInt(Integer::intValue).min().orElseThrow();
        Set<Tuple2<Integer, Integer>> emptyNodes = getInitialEmptyNodesAndFillAllUsefulNodes(nodes, targetSize, smallestData);

        Tuple2<Integer, Integer> source = Tuple.tuple(nodes.keySet().stream().map(Tuple2::v1).max(Comparator.naturalOrder()).orElseThrow(), 0);
        queue.add(new State(emptyNodes, source, 0));
        return getNumberOfStepsUntilDataAtTargetByHandlingQueue(target);
    }

    private Object getNumberOfStepsUntilDataAtTargetByHandlingQueue(Tuple2<Integer, Integer> target) {
        while (!queue.isEmpty()) {
            State state = queue.poll();
            for (Tuple2<Integer, Integer> emptyNode : state.emptyNodes) {
                for (Direction dir : Direction.values()) {
                    Tuple2<Integer, Integer> neighbor = dir.move(emptyNode, 1);
                    if (allUsefulNodes.contains(neighbor)) {
                        int stepsUntilThisState = state.stepsUntilThisState + 1;
                        Tuple2<Integer, Integer> nodeWithGoalData = state.nodeWithGoalData;
                        if (nodeWithGoalData.equals(neighbor)) {
                            nodeWithGoalData = emptyNode;
                        }
                        if (target.equals(nodeWithGoalData)) {
                            return stepsUntilThisState;
                        }
                        Set<Tuple2<Integer, Integer>> newEmpty = new HashSet<>(state.emptyNodes);
                        newEmpty.add(neighbor);
                        newEmpty.remove(emptyNode);
                        State newState = new State(newEmpty, nodeWithGoalData, stepsUntilThisState);
                        if (!knownStates.contains(newState)) {
                            queue.add(newState);
                            knownStates.add(newState);
                        }
                    }
                }
            }
        }
        return "not possible";
    }

    private Set<Tuple2<Integer, Integer>> getInitialEmptyNodesAndFillAllUsefulNodes(Map<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>> nodes, int targetSize, int smallestData) {
        Set<Tuple2<Integer, Integer>> emptyNodes = new HashSet<>();
        for (Tuple2<Integer, Integer> node : nodes.keySet()) {
            Tuple2<Integer, Integer> content = nodes.get(node);
            if (content.v1 != 0 && content.v2 > smallestData) {
                throw new IllegalStateException("idea not working");
            }
            if (content.v1 == 0) {
                emptyNodes.add(node);
            }
            if (content.v1 <= targetSize) {
                allUsefulNodes.add(node);
            }
        }
        allUsefulNodes.addAll(emptyNodes);
        return emptyNodes;
    }

    private Map<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>> parseNodes(List<String> input) {
        Pattern p = Pattern.compile("/dev/grid/node-x([0-9]+)-y([0-9]+) +([0-9]+)T +([0-9]+)T +([0-9]+)T +([0-9]+)%");
        Map<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>> map = new HashMap<>();
        for (String line : input) {
            Matcher m = p.matcher(line);
            if (m.matches()) {
                int x = Integer.parseInt(m.group(1));
                int y = Integer.parseInt(m.group(2));
                int used = Integer.parseInt(m.group(4));
                int avail = Integer.parseInt(m.group(5));
                map.put(Tuple.tuple(x, y), Tuple.tuple(used, avail));
            }
        }
        return map;
    }


}
