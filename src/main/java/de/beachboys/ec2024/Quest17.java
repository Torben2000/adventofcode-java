package de.beachboys.ec2024;

import de.beachboys.Quest;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;
import org.jooq.lambda.tuple.Tuple3;

import java.util.*;

public class Quest17 extends Quest {

    @Override
    public Object part1(List<String> input) {
        Set<Tuple2<Integer, Integer>> stars = Util.buildConwaySet(input, "*");
        List<Tuple3<Integer, Tuple2<Integer, Integer>, Tuple2<Integer, Integer>>> distances = getSortedListOfDistances(stars);

        return getConstellationAndSize(stars, distances).v2;
    }

    @Override
    public Object part2(List<String> input) {
        return part1(input);
    }

    @Override
    public Object part3(List<String> input) {
        Set<Tuple2<Integer, Integer>> stars = Util.buildConwaySet(input, "*");
        List<Tuple3<Integer, Tuple2<Integer, Integer>, Tuple2<Integer, Integer>>> distances = getSortedListOfDistances(stars);

        distances.removeIf(d -> d.v1 >= 6);

        List<Long> sizes = new ArrayList<>();
        Set<Tuple2<Integer, Integer>> unconnectedStars = new HashSet<>(stars);
        while (!unconnectedStars.isEmpty()) {
            Tuple2<Set<Tuple2<Integer, Integer>>, Long> constellationAndSize = getConstellationAndSize(unconnectedStars, distances);
            sizes.add(constellationAndSize.v2);
            unconnectedStars.removeAll(constellationAndSize.v1);
        }

        sizes.sort(Comparator.reverseOrder());
        return sizes.get(0) * sizes.get(1) * sizes.get(2);
    }

    private static Tuple2<Set<Tuple2<Integer, Integer>>, Long> getConstellationAndSize(Set<Tuple2<Integer, Integer>> stars, List<Tuple3<Integer, Tuple2<Integer, Integer>, Tuple2<Integer, Integer>>> distances) {
        long distanceSum = 0;

        Set<Tuple2<Integer, Integer>> starsInConstellation = new HashSet<>();
        starsInConstellation.add(stars.stream().findFirst().orElseThrow());
        boolean added = true;
        while (added) {
            added = false;
            for (Tuple3<Integer, Tuple2<Integer, Integer>, Tuple2<Integer, Integer>> distance : distances) {
                if (starsInConstellation.contains(distance.v2) && !starsInConstellation.contains(distance.v3)) {
                    distanceSum += distance.v1;
                    starsInConstellation.add(distance.v3);
                    added = true;
                    break;
                }
            }
        }
        return Tuple.tuple(starsInConstellation, distanceSum + starsInConstellation.size());
    }

    private static List<Tuple3<Integer, Tuple2<Integer, Integer>, Tuple2<Integer, Integer>>> getSortedListOfDistances(Set<Tuple2<Integer, Integer>> stars) {
        List<Tuple3<Integer, Tuple2<Integer, Integer>, Tuple2<Integer, Integer>>> distances = new ArrayList<>();
        for (Tuple2<Integer, Integer> star : stars) {
            for (Tuple2<Integer, Integer> star2 : stars) {
                if (star != star2) {
                    distances.add(Tuple.tuple(Util.getManhattanDistance(star, star2), star, star2));
                }
            }
        }
        distances.sort(Comparator.comparing(Tuple3::v1));
        return distances;
    }

}
