package de.beachboys.aoc2018;

import de.beachboys.Day;
import de.beachboys.Util;

import java.util.ArrayList;
import java.util.List;

public class Day08 extends Day {

    private static class Node {

        private final List<Node> subNodes = new ArrayList<>();

        private final List<Integer> metaData = new ArrayList<>();

        private final int endPosition;

        public Node(List<Integer> data, int startPosition) {
            int subNodeCount = data.get(startPosition);
            int metaDataCount = data.get(startPosition + 1);
            int currentEndPosition = startPosition + 1;
            for (int i = 0; i < subNodeCount; i++) {
                Node subNode = new Node(data, currentEndPosition + 1);
                subNodes.add(subNode);
                currentEndPosition = subNode.endPosition;
            }
            metaData.addAll(data.subList(currentEndPosition + 1, currentEndPosition + 1 + metaDataCount));
            endPosition = currentEndPosition + metaDataCount;
        }

        public int getMetaDataSumPart1() {
            return subNodes.stream().mapToInt(Node::getMetaDataSumPart1).sum() + metaData.stream().mapToInt(Integer::intValue).sum();
        }

        public int getMetaDataSumPart2() {
            int sum = 0;
            if (subNodes.isEmpty()) {
                sum = metaData.stream().mapToInt(Integer::intValue).sum();
            } else {
                for (Integer subNodeIndex: metaData) {
                    if (subNodeIndex <= subNodes.size()) {
                        sum += subNodes.get(subNodeIndex - 1).getMetaDataSumPart2();
                    }
                }
            }
            return sum;
        }
    }

    public Object part1(List<String> input) {
        List<Integer> data = Util.parseToIntList(input.get(0), " ");
        Node root = new Node(data, 0);
        return root.getMetaDataSumPart1();
    }

    public Object part2(List<String> input) {
        List<Integer> data = Util.parseToIntList(input.get(0), " ");
        Node root = new Node(data, 0);
        return root.getMetaDataSumPart2();
    }

}
