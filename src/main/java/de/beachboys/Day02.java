package de.beachboys;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day02 implements Day {

    public String part1(List<String> input) {
        String realInput = input.get(0);
        String[] stringArray = realInput.split(",");
        List<Integer> list = Arrays.stream(stringArray).map(Integer::valueOf).collect(Collectors.toList());

        runLogic(list);

        System.out.println("Complete list + " + list.toString());
        return list.get(0) + "";
    }

    private void runLogic(List<Integer> list) {
        int currentIndex = 0;

        int opcode = 0;
        while(opcode != 99) {
            opcode = list.get(currentIndex);
            switch (opcode) {
                case 99:
                    //
                    break;
                case 1:
                    int result = list.get(list.get(currentIndex + 1)) + list.get(list.get(currentIndex + 2));
                    int setIndex = list.get(currentIndex + 3);
                    if (setIndex < list.size()) {
                        list.set(setIndex, result);
                    }
                    break;
                case 2:
                    int result2 = list.get(list.get(currentIndex + 1)) * list.get(list.get(currentIndex + 2));
                    int setIndex2 = list.get(currentIndex + 3);
                    if (setIndex2 < list.size()) {
                        list.set(setIndex2, result2);
                    }
                    break;
                default:
            }
            currentIndex += 4;
        }
    }

    public String part2(List<String> input) {
        String realInput = input.get(0);
        String[] stringArray = realInput.split(",");
        List<Integer> list = Arrays.stream(stringArray).map(Integer::valueOf).collect(Collectors.toList());
        List<Integer> listNew = list;
        for (int i = 0; i<100; i++) {
            for (int j = 0; j< 100; j++) {
                listNew = new ArrayList<>(list);
                listNew.set(1, i);
                listNew.set(2, j);
                try {
                    runLogic(listNew);
                    if (listNew.get(0) == 19690720) {
                        return (listNew.get(1)*100+listNew.get(2)) + "";
                    }
                } catch (Exception e) {
                }

            }
        }

        return "error";
    }

}
