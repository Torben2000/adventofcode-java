package de.beachboys.aoc2015;

import de.beachboys.Day;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;
import org.jooq.lambda.tuple.Tuple3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day21 extends Day {

    private interface BestCostPredicate {
        boolean test(int currentBestCost, int cost, Tuple3<Integer, Integer, Integer> player, Tuple3<Integer, Integer, Integer> boss);
    }

    private static final Map<Tuple2<Integer, Integer>, Integer> WEAPONS = Map.of(
            Tuple.tuple(4, 0), 8,
            Tuple.tuple(5, 0), 10,
            Tuple.tuple(6, 0), 25,
            Tuple.tuple(7, 0), 40,
            Tuple.tuple(8, 0), 74
    );

    private static final Map< Tuple2<Integer, Integer>, Integer> ARMOR = Map.of(
            Tuple.tuple(0, 1), 13,
            Tuple.tuple(0, 2), 31,
            Tuple.tuple(0, 3), 53,
            Tuple.tuple(0, 4), 75,
            Tuple.tuple(0, 5), 102
    );

    private static final Map<Tuple2<Integer, Integer>, Integer> RINGS = Map.of(
            Tuple.tuple(1, 0), 25,
            Tuple.tuple(2, 0), 50,
            Tuple.tuple(3, 0), 100,
            Tuple.tuple(0, 1), 20,
            Tuple.tuple(0, 2), 40,
            Tuple.tuple(0, 3), 80
    );

    public Object part1(List<String> input) {
        return runLogic(input, Integer.MAX_VALUE, this::isBestCostPart1);
    }

    public Object part2(List<String> input) {
        return runLogic(input, 0, this::isBestCostPart2);
    }

    private boolean isBestCostPart1(int currentBestCost, int cost, Tuple3<Integer, Integer, Integer> player, Tuple3<Integer, Integer, Integer> boss) {
        return cost < currentBestCost && fight(player, boss);
    }

    private boolean isBestCostPart2(int currentBestCost, int cost, Tuple3<Integer, Integer, Integer> player, Tuple3<Integer, Integer, Integer> boss) {
        return cost > currentBestCost && !fight(player, boss);
    }

    private int runLogic(List<String> input, int bestCostInitialValue, BestCostPredicate bestCostCheck) {
        int bossHealth = Integer.parseInt(input.get(0).split(": ")[1]);
        int bossDamage = Integer.parseInt(input.get(1).split(": ")[1]);
        int bossArmor = Integer.parseInt(input.get(2).split(": ")[1]);

        int bestCost = bestCostInitialValue;
        Map< Tuple2<Integer, Integer>, Integer> armorOptions = buildArmorOptions();
        Map< Tuple2<Integer, Integer>, Integer> ringOptions = buildRingOptions();
        for (Tuple2<Integer, Integer> weapon : WEAPONS.keySet()) {
            for (Tuple2<Integer, Integer> armorOption : armorOptions.keySet()) {
                for (Tuple2<Integer, Integer> ringOption : ringOptions.keySet()) {
                    int damage = weapon.v1 + armorOption.v1 + ringOption.v1;
                    int armor = weapon.v2 + armorOption.v2 + ringOption.v2;
                    int cost = WEAPONS.get(weapon) + armorOptions.get(armorOption) + ringOptions.get(ringOption);
                    Tuple3<Integer, Integer, Integer> player = Tuple.tuple(100, damage, armor);
                    Tuple3<Integer, Integer, Integer> boss = Tuple.tuple(bossHealth, bossDamage, bossArmor);
                    if (bestCostCheck.test(bestCost, cost, player, boss)) {
                        bestCost = cost;
                    }
                }
            }
        }
        return bestCost;
    }

    private boolean fight(Tuple3<Integer, Integer, Integer> player, Tuple3<Integer, Integer, Integer> boss) {
        int bossHealth = boss.v1 - Math.max(1, player.v2 - boss.v3);
        if (bossHealth <= 0) {
            return true;
        }
        int playerHealth = player.v1 - Math.max(1, boss.v2 - player.v3);
        if (playerHealth <= 0) {
            return false;
        }
        return fight(Tuple.tuple(playerHealth, player.v2, player.v3), Tuple.tuple(bossHealth, boss.v2, boss.v3));
    }

    private Map<Tuple2<Integer, Integer>, Integer> buildRingOptions() {
        Map<Tuple2<Integer, Integer>, Integer> ringOptions = new HashMap<>(RINGS);
        ringOptions.put(Tuple.tuple(0, 0), 0);
        for (Tuple2<Integer, Integer> ring1 : RINGS.keySet()) {
            for (Tuple2<Integer, Integer> ring2 : RINGS.keySet()) {
                if (!ring1.equals(ring2)) {
                    ringOptions.put(Tuple.tuple(ring1.v1 + ring2.v1, ring1.v2 + ring2.v2), RINGS.get(ring1) + RINGS.get(ring2));
                }
            }
        }
        return ringOptions;
    }

    private Map<Tuple2<Integer, Integer>, Integer> buildArmorOptions() {
        Map<Tuple2<Integer, Integer>, Integer> armorOptions = new HashMap<>(ARMOR);
        armorOptions.put(Tuple.tuple(0, 0), 0);
        return armorOptions;
    }

}
