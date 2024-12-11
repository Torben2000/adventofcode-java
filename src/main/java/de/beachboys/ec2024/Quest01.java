package de.beachboys.ec2024;

import de.beachboys.Quest;

import java.util.List;
import java.util.Objects;

public class Quest01 extends Quest {

    @Override
    public Object part1(List<String> input) {
        List<Integer> potionsPerMonster = getPotionsPerMonster(input);
        return potionsPerMonster.stream().filter(Objects::nonNull).mapToInt(Integer::intValue).sum();
    }

    @Override
    public Object part2(List<String> input) {
        List<Integer> potionsPerMonster = getPotionsPerMonster(input);
        long result = potionsPerMonster.stream().filter(Objects::nonNull).mapToInt(Integer::intValue).sum();
        for (int i = 0; i < potionsPerMonster.size(); i += 2) {
            Integer potions1 = potionsPerMonster.get(i);
            Integer potions2 = potionsPerMonster.get(i+1);
            if (potions1 != null && potions2 != null) {
                result += 2;
            }
        }
        return result;
    }

    @Override
    public Object part3(List<String> input) {
        List<Integer> potionsPerMonster = getPotionsPerMonster(input);
        long result = potionsPerMonster.stream().filter(Objects::nonNull).mapToInt(Integer::intValue).sum();
        for (int i = 0; i < potionsPerMonster.size(); i += 3) {
            Integer potions1 = potionsPerMonster.get(i);
            Integer potions2 = potionsPerMonster.get(i+1);
            Integer potions3 = potionsPerMonster.get(i+2);
            if (potions1 != null && potions2 != null && potions3 != null) {
                result += 6;
            } else if (potions1 != null && (potions2 != null || potions3 != null)
                    || potions2 != null && potions3 != null) {
                result += 2;
            }
        }
        return result;

    }

    private List<Integer> getPotionsPerMonster(List<String> input) {
        return input.getFirst().chars().mapToObj(this::getPotionPerMonster).toList();
    }

    private Integer getPotionPerMonster(int monsterChar) {
        return switch (monsterChar) {
            case 'A' -> 0;
            case 'B' -> 1;
            case 'C' -> 3;
            case 'D' -> 5;
            default -> null;
        };
    }
}
