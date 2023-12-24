package de.beachboys.aoc2020;

import de.beachboys.Day;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Day23 extends Day {

    private static class Node {
        public final int value;
        public Node next;

        public Node(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value + "";
        }
    }

    private final Map<Integer, Node> map = new HashMap<>();

    public Object part1(List<String> input) {
        List<Integer> cups = buildCupListFromInput(input);

        buildCupMapAndShuffle(cups, 100);

        StringBuilder result = new StringBuilder();
        Node current = map.get(1);
        for (int i = 1; i <= 8; i++) {
            current = current.next;
            result.append(current.value);
        }
        return result.toString();
    }

    public Object part2(List<String> input) {
        List<Integer> cups = buildCupListFromInput(input);
        for (int i = cups.size() + 1; i <= 1000000; i++) {
            cups.add(i);
        }

        buildCupMapAndShuffle(cups, 10000000);

        Node one = map.get(1);
        return ((long) one.next.value * one.next.next.value);
    }

    private void buildCupMapAndShuffle(List<Integer> cups, int numCycles) {
        buildCupMap(cups);

        int currentCup = cups.getFirst();
        int numCups = map.size();
        for (int i = 0; i < numCycles; i++) {
            Node currentNode = map.get(currentCup);
            int destinationCup = findDestinationCup(currentCup, currentNode, numCups);

            Node firstCupToMove = currentNode.next;
            currentNode.next = currentNode.next.next.next.next;

            Node destinationNode = map.get(destinationCup);
            Node afterDestinationNode = destinationNode.next;
            destinationNode.next = firstCupToMove;
            firstCupToMove.next.next.next = afterDestinationNode;

            currentCup = currentNode.next.value;
        }

    }

    private int findDestinationCup(int currentCup, Node currentNode, int numCups) {
        int destinationCup = 0;
        for (int j = 1; j < numCups; j++) {
            destinationCup = Math.floorMod((currentCup - j), numCups);
            if (destinationCup == 0) {
                destinationCup = numCups;
            }

            if (currentNode.next.value != destinationCup
                    && currentNode.next.next.value != destinationCup
                    && currentNode.next.next.next.value != destinationCup) {
                break;
            }
        }
        return destinationCup;
    }

    private List<Integer> buildCupListFromInput(List<String> input) {
        List<Integer> cups = new LinkedList<>();
        for (int i = 0; i < input.getFirst().length(); i++) {
            cups.add(Integer.valueOf(input.getFirst().substring(i, i + 1)));
        }
        return cups;
    }

    private void buildCupMap(List<Integer> cups) {
        Node firstNode = null;
        Node lastNode = null;
        for (int cup : cups) {
            Node node = new Node(cup);
            map.put(cup, node);
            if (lastNode != null) {
                lastNode.next = node;
            }
            if (firstNode == null) {
                firstNode = node;
            }
            lastNode = node;
        }
        if (lastNode != null) {
            lastNode.next = firstNode;
        }
    }


}
