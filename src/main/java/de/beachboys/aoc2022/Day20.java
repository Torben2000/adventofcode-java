package de.beachboys.aoc2022;

import de.beachboys.Day;

import java.util.*;

public class Day20 extends Day {

    public Object part1(List<String> input) {
        return runLogic(input, 1, 1);
    }

    public Object part2(List<String> input) {
        return runLogic(input, 10, 811589153);
    }

    private long runLogic(List<String> input, int rounds, long valueFactor) {
        long result = 0;
        Item zeroItem = null;
        List<Item> list = new ArrayList<>();
        Item prev = null;
        for (String line : input) {
            Item item = new Item();
            item.value = Long.parseLong(line) * valueFactor;
            if (prev != null) {
                item.prev = prev;
                prev.next = item;
            }
            prev = item;
            if (item.value == 0) {
                zeroItem = item;
            }
            list.add(item);
        }
        list.getFirst().prev = prev;
        prev.next = list.getFirst();
        if (zeroItem == null) {
            throw new IllegalArgumentException();
        }

        for (int round = 0; round < rounds; round++) {
            for (Item item : list) {
                moveItemForwardByValue(item, list.size());
            }
        }

        Item currentItem = zeroItem;
        for (int i = 0; i < 1000; i++) {
            currentItem = currentItem.next;
        }
        result += currentItem.value;
        for (int i = 0; i < 1000; i++) {
            currentItem = currentItem.next;
        }
        result += currentItem.value;
        for (int i = 0; i < 1000; i++) {
            currentItem = currentItem.next;
        }
        result += currentItem.value;
        return result;
    }

    private static void moveItemForwardByValue(Item item, int listSize) {
        int positionsToMoveForward = Math.floorMod(item.value, listSize - 1);

        if (positionsToMoveForward != 0) {
            Item insertAfter = item;
            for (int i = 0; i < positionsToMoveForward; i++) {
                insertAfter = insertAfter.next;
            }
            item.prev.next = item.next;
            item.next.prev = item.prev;

            insertAfter.next.prev = item;
            item.next = insertAfter.next;

            item.prev = insertAfter;
            insertAfter.next = item;
        }
    }

    private static class Item {
        Item prev;
        Item next;
        Long value;

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }
}
