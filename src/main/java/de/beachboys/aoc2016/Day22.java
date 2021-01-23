package de.beachboys.aoc2016;

import de.beachboys.Day;
import de.beachboys.Direction;
import org.javatuples.Pair;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day22 extends Day {

    private static class State {

        final Set<Pair<Integer, Integer>> emptyNodes;
        final Pair<Integer, Integer> nodeWithGoalData;
        final int stepsUntilThisState;
        String stringRepresentation;

        public State(Set<Pair<Integer, Integer>> emptyNodes, Pair<Integer, Integer> nodeWithGoalData, int stepsUntilThisState) {
            this.emptyNodes = emptyNodes;
            this.nodeWithGoalData = nodeWithGoalData;
            this.stepsUntilThisState = stepsUntilThisState;
        }

        public String getStringRepresentation() {
            if (stringRepresentation == null) {
                stringRepresentation = nodeWithGoalData.toString() + "|" + emptyNodes.stream().sorted().collect(Collectors.toList()).toString();
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
        Map<Pair<Integer, Integer>, Pair<Integer, Integer>> nodes = parseNodes(input);

        long viablePairCount = 0L;
        for (Map.Entry<Pair<Integer, Integer>, Pair<Integer, Integer>> innerNode : nodes.entrySet()) {
            if (innerNode.getValue().getValue0() > 0) {
                for (Map.Entry<Pair<Integer, Integer>, Pair<Integer, Integer>> outerNode : nodes.entrySet()) {
                    if (innerNode.getKey() != outerNode.getKey() && innerNode.getValue().getValue0() <= outerNode.getValue().getValue1()) {
                        viablePairCount++;
                    }
                }
            }
        }
        return viablePairCount;
    }

    private final Deque<State> queue = new LinkedList<>();
    private final Set<State> knownStates = new HashSet<>();
    private final Set<Pair<Integer, Integer>> allUsefulNodes = new HashSet<>();

    @Override
    public Object part2(List<String> input) {
        Map<Pair<Integer, Integer>, Pair<Integer, Integer>> nodes = parseNodes(input);
        Pair<Integer, Integer> target = Pair.with(0, 0);
        int targetSize = nodes.get(target).getValue0() + nodes.get(target).getValue1();
        int smallestData = nodes.values().stream().map(Pair::getValue0).filter(data -> data != 0).mapToInt(Integer::intValue).min().orElseThrow();
        Set<Pair<Integer, Integer>> emptyNodes = getInitialEmptyNodesAndFillAllUsefulNodes(nodes, targetSize, smallestData);

        Pair<Integer, Integer> source = Pair.with(nodes.keySet().stream().map(Pair::getValue0).max(Comparator.naturalOrder()).orElseThrow(), 0);
        queue.add(new State(emptyNodes, source, 0));
        return getNumberOfStepsUntilDataAtTargetByHandlingQueue(target);
    }

    private Object getNumberOfStepsUntilDataAtTargetByHandlingQueue(Pair<Integer, Integer> target) {
        while (!queue.isEmpty()) {
            State state = queue.poll();
            for (Pair<Integer, Integer> emptyNode : state.emptyNodes) {
                for (Direction dir : Direction.values()) {
                    Pair<Integer, Integer> neighbor = dir.move(emptyNode, 1);
                    if (allUsefulNodes.contains(neighbor)) {
                        int stepsUntilThisState = state.stepsUntilThisState + 1;
                        Pair<Integer, Integer> nodeWithGoalData = state.nodeWithGoalData;
                        if (nodeWithGoalData.equals(neighbor)) {
                            nodeWithGoalData = emptyNode;
                        }
                        if (target.equals(nodeWithGoalData)) {
                            return stepsUntilThisState;
                        }
                        Set<Pair<Integer, Integer>> newEmpty = new HashSet<>(state.emptyNodes);
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

    private Set<Pair<Integer, Integer>> getInitialEmptyNodesAndFillAllUsefulNodes(Map<Pair<Integer, Integer>, Pair<Integer, Integer>> nodes, int targetSize, int smallestData) {
        Set<Pair<Integer, Integer>> emptyNodes = new HashSet<>();
        for (Pair<Integer, Integer> node : nodes.keySet()) {
            Pair<Integer, Integer> content = nodes.get(node);
            if (content.getValue0() != 0 && content.getValue1() > smallestData) {
                throw new IllegalStateException("idea not working");
            }
            if (content.getValue0() == 0) {
                emptyNodes.add(node);
            }
            if (content.getValue0() <= targetSize) {
                allUsefulNodes.add(node);
            }
        }
        allUsefulNodes.addAll(emptyNodes);
        return emptyNodes;
    }

    private Map<Pair<Integer, Integer>, Pair<Integer, Integer>> parseNodes(List<String> input) {
        Pattern p = Pattern.compile("/dev/grid/node-x([0-9]+)-y([0-9]+) +([0-9]+)T +([0-9]+)T +([0-9]+)T +([0-9]+)%");
        Map<Pair<Integer, Integer>, Pair<Integer, Integer>> map = new HashMap<>();
        for (String line : input) {
            Matcher m = p.matcher(line);
            if (m.matches()) {
                int x = Integer.parseInt(m.group(1));
                int y = Integer.parseInt(m.group(2));
                int used = Integer.parseInt(m.group(4));
                int avail = Integer.parseInt(m.group(5));
                map.put(Pair.with(x, y), Pair.with(used, avail));
            }
        }
        return map;
    }


}
