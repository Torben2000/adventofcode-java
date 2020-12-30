package de.beachboys.aoc2015;

import de.beachboys.Day;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day21 extends Day {

    private interface BestCostPredicate {
        boolean test(int currentBestCost, int cost, Triplet<Integer, Integer, Integer> player, Triplet<Integer, Integer, Integer> boss);
    }

    private static final Map<Pair<Integer, Integer>, Integer> WEAPONS = Map.of(
            Pair.with(4, 0), 8,
            Pair.with(5, 0), 10,
            Pair.with(6, 0), 25,
            Pair.with(7, 0), 40,
            Pair.with(8, 0), 74
    );

    private static final Map< Pair<Integer, Integer>, Integer> ARMOR = Map.of(
            Pair.with(0, 1), 13,
            Pair.with(0, 2), 31,
            Pair.with(0, 3), 53,
            Pair.with(0, 4), 75,
            Pair.with(0, 5), 102
    );

    private static final Map<Pair<Integer, Integer>, Integer> RINGS = Map.of(
            Pair.with(1, 0), 25,
            Pair.with(2, 0), 50,
            Pair.with(3, 0), 100,
            Pair.with(0, 1), 20,
            Pair.with(0, 2), 40,
            Pair.with(0, 3), 80
    );

    public Object part1(List<String> input) {
        return runLogic(input, Integer.MAX_VALUE, this::isBestCostPart1);
    }

    public Object part2(List<String> input) {
        return runLogic(input, 0, this::isBestCostPart2);
    }

    private boolean isBestCostPart1(int currentBestCost, int cost, Triplet<Integer, Integer, Integer> player, Triplet<Integer, Integer, Integer> boss) {
        return cost < currentBestCost && fight(player, boss);
    }

    private boolean isBestCostPart2(int currentBestCost, int cost, Triplet<Integer, Integer, Integer> player, Triplet<Integer, Integer, Integer> boss) {
        return cost > currentBestCost && !fight(player, boss);
    }

    private int runLogic(List<String> input, int bestCostInitialValue, BestCostPredicate bestCostCheck) {
        int bossHealth = Integer.parseInt(input.get(0).split(": ")[1]);
        int bossDamage = Integer.parseInt(input.get(1).split(": ")[1]);
        int bossArmor = Integer.parseInt(input.get(2).split(": ")[1]);

        int bestCost = bestCostInitialValue;
        Map< Pair<Integer, Integer>, Integer> armorOptions = buildArmorOptions();
        Map< Pair<Integer, Integer>, Integer> ringOptions = buildRingOptions();
        for (Pair<Integer, Integer> weapon : WEAPONS.keySet()) {
            for (Pair<Integer, Integer> armorOption : armorOptions.keySet()) {
                for (Pair<Integer, Integer> ringOption : ringOptions.keySet()) {
                    int damage = weapon.getValue0() + armorOption.getValue0() + ringOption.getValue0();
                    int armor = weapon.getValue1() + armorOption.getValue1() + ringOption.getValue1();
                    int cost = WEAPONS.get(weapon) + armorOptions.get(armorOption) + ringOptions.get(ringOption);
                    Triplet<Integer, Integer, Integer> player = Triplet.with(100, damage, armor);
                    Triplet<Integer, Integer, Integer> boss = Triplet.with(bossHealth, bossDamage, bossArmor);
                    if (bestCostCheck.test(bestCost, cost, player, boss)) {
                        bestCost = cost;
                    }
                }
            }
        }
        return bestCost;
    }

    private boolean fight(Triplet<Integer, Integer, Integer> player, Triplet<Integer, Integer, Integer> boss) {
        int bossHealth = boss.getValue0() - Math.max(1, player.getValue1() - boss.getValue2());
        if (bossHealth <= 0) {
            return true;
        }
        int playerHealth = player.getValue0() - Math.max(1, boss.getValue1() - player.getValue2());
        if (playerHealth <= 0) {
            return false;
        }
        return fight(Triplet.with(playerHealth, player.getValue1(), player.getValue2()), Triplet.with(bossHealth, boss.getValue1(), boss.getValue2()));
    }

    private Map<Pair<Integer, Integer>, Integer> buildRingOptions() {
        Map<Pair<Integer, Integer>, Integer> ringOptions = new HashMap<>(RINGS);
        ringOptions.put(Pair.with(0, 0), 0);
        for (Pair<Integer, Integer> ring1 : RINGS.keySet()) {
            for (Pair<Integer, Integer> ring2 : RINGS.keySet()) {
                if (!ring1.equals(ring2)) {
                    ringOptions.put(Pair.with(ring1.getValue0() + ring2.getValue0(), ring1.getValue1() + ring2.getValue1()), RINGS.get(ring1) + RINGS.get(ring2));
                }
            }
        }
        return ringOptions;
    }

    private Map<Pair<Integer, Integer>, Integer> buildArmorOptions() {
        Map<Pair<Integer, Integer>, Integer> armorOptions = new HashMap<>(ARMOR);
        armorOptions.put(Pair.with(0, 0), 0);
        return armorOptions;
    }

}
