package de.beachboys.aoc2019;

import de.beachboys.Day;
import de.beachboys.IOHelper;
import de.beachboys.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day21 extends Day {

    private final IntcodeComputer computer = new IntcodeComputer();

    private List<Integer> inputCharacters = List.of();

    private int inputCounter = 0;

    private int damage = 0;

    private String imageString;

    public Object part1(List<String> input) {
        String programCode =
                "NOT C J\n" +
                "NOT B T\n" +
                "OR T J\n" +
                "AND D J\n" +
                "NOT A T\n" +
                "OR T J\n" +
                "WALK";
        return getDamage(input, programCode);
    }

    public Object part2(List<String> input) {
        String programCode =
                "NOT C J\n" +
                        "NOT B T\n" +
                        "OR T J\n" +
                        "AND D J\n" +
                        "AND H J\n" +
                        "NOT A T\n" +
                        "OR T J\n" +
                        "RUN";
        return getDamage(input, programCode);
    }

    private int getDamage(List<String> input, String programCode) {
        List<Long> list = Util.parseLongCsv(input.getFirst());
        this.inputCharacters = (programCode + "\n").chars().boxed().collect(Collectors.toList());
        runComputer(list);
        io.logDebug(Util.paintMap(Util.buildImageMap(imageString)));
        return damage;
    }

    private void runComputer(List<Long> list) {
        IOHelper io = new IOHelper() {

            @Override
            public String getInput(String textToDisplay) {
                return inputCharacters.get(inputCounter++).toString();
            }

            @Override
            public void logInfo(Object infoText) {
                if (infoText.toString().length() > 2){
                    damage = Integer.parseInt(infoText.toString());
                } else {
                    int charCode = Integer.parseInt(infoText.toString());
                    imageString += Character.toString(charCode);
                }
            }

            @Override
            public void logDebug(Object debugText) {
                // do nothing
            }
        };

        computer.runLogic(new ArrayList<>(list), io);
    }

}
