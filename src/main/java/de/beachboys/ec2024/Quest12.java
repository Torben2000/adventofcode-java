package de.beachboys.ec2024;

import de.beachboys.Quest;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Quest12 extends Quest {

    @Override
    public Object part1(List<String> input) {
        int result = 0;
        Map<Tuple2<Integer, Integer>, String> map = Util.buildImageMap(input);

        List<Tuple2<Integer, Integer>> targets = map.entrySet().stream().filter(e -> "T".equals(e.getValue())).map(Map.Entry::getKey).toList();
        List<Tuple2<Integer, Integer>> hardRocks = map.entrySet().stream().filter(e -> "H".equals(e.getValue())).map(Map.Entry::getKey).toList();

        List<Tuple2<Integer, Integer>> catapults = new ArrayList<>(3);
        catapults.add(map.entrySet().stream().filter(e -> "A".equals(e.getValue())).map(Map.Entry::getKey).findFirst().orElseThrow());
        catapults.add(map.entrySet().stream().filter(e -> "B".equals(e.getValue())).map(Map.Entry::getKey).findFirst().orElseThrow());
        catapults.add(map.entrySet().stream().filter(e -> "C".equals(e.getValue())).map(Map.Entry::getKey).findFirst().orElseThrow());

        List<Tuple2<Integer, Integer>> targetsToHit = new ArrayList<>(targets);
        targetsToHit.addAll(hardRocks);
        targetsToHit.addAll(hardRocks);
        int maxPower = Math.max(input.size(), input.getFirst().length());
        for (Tuple2<Integer, Integer> target : targetsToHit) {
            for (int catapultIndex = 0; catapultIndex < 3; catapultIndex++) {
                Tuple2<Integer, Integer> catapult = catapults.get(catapultIndex);
                for (int power = 1; power < maxPower; power++) {
                    int distance = power * 3 - catapult.v2 + target.v2;
                    if (catapult.v1 + distance == target.v1 && power >= catapult.v2 - target.v2) {
                        result += (catapultIndex + 1) * power;
                    }
                }
            }
        }
        return result;
    }

    @Override
    public Object part2(List<String> input) {
        return part1(input);
    }

    @Override
    public Object part3(List<String> input) {
        long result = 0;
        List<Tuple2<Integer, Integer>> meteors = new ArrayList<>();
        for (String meteorPos : input) {
            String[] split = meteorPos.split(" ");
            meteors.add(Tuple.tuple(Integer.parseInt(split[0]), -1 * Integer.parseInt(split[1])));
        }

        List<Tuple2<Integer, Integer>> catapults = new ArrayList<>(3);
        catapults.add(Tuple.tuple(0, 0));
        catapults.add(Tuple.tuple(0, -1));
        catapults.add(Tuple.tuple(0, -2));

        for (Tuple2<Integer, Integer> meteor : meteors) {
            int minScore = Integer.MAX_VALUE;
            int maxAltitude = 0;
            for (int catapultIndex = 0; catapultIndex < 3; catapultIndex++) {
                Tuple2<Integer, Integer> catapult = catapults.get(catapultIndex);
                for (int time = 0; time < meteor.v1; time++) {
                    if (canHitAtDiscreteTime(catapult, meteor, time)) {
                        int traveledMeteorDistanceForPossibleCollision = time + (meteor.v1 - catapult.v1) / 2;
                        Tuple2<Integer, Integer> target = Tuple.tuple(meteor.v1 - traveledMeteorDistanceForPossibleCollision, meteor.v2 + traveledMeteorDistanceForPossibleCollision);
                        if (target.v2 <= maxAltitude) {
                            for (int power = 1; power <= target.v1; power++) {
                                int xDist = target.v1 - catapult.v1;
                                int yDist = catapult.v2 - target.v2;
                                if (xDist <= power && yDist == xDist
                                        || xDist <= 2 * power && yDist == power
                                        || xDist == 3 * power - yDist && yDist <= power) {
                                    if (maxAltitude == target.v2) {
                                        minScore = Math.min(minScore, (1 + catapultIndex) * power);
                                    } else {
                                        maxAltitude = target.v2;
                                        minScore = (1 + catapultIndex) * power;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            result += minScore;
        }
        return result;
    }

    private static boolean canHitAtDiscreteTime(Tuple2<Integer, Integer> catapult, Tuple2<Integer, Integer> meteor, int time) {
        return (time + meteor.v1 - catapult.v1) % 2 == 0;
    }
}
