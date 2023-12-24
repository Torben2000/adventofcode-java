package de.beachboys.aoc2020;

import de.beachboys.Day;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.*;
import java.util.stream.Collectors;

public class Day07 extends Day {

    private final Map<String, List<Tuple2<Long, String>>> bagRelations = new HashMap<>();

    public Object part1(List<String> input) {
        buildBagRelations(input);

        Set<String> possibleBagColors = new HashSet<>();
        Set<String> checkedBagColors = new HashSet<>();
        Set<String> uncheckedBagColors = new HashSet<>(List.of("shiny gold"));

        while (!uncheckedBagColors.isEmpty()) {
            String bagColorToCheck = uncheckedBagColors.stream().findFirst().orElseThrow();
            for (Map.Entry<String, List<Tuple2<Long, String>>> bagRelation : bagRelations.entrySet()) {
                bagRelation.getValue().forEach(innerBag -> {
                    String innerBagColor = innerBag.v2;
                    if (innerBagColor.equals(bagColorToCheck)) {
                        String newOuterBagColor = bagRelation.getKey();
                        if (!checkedBagColors.contains(newOuterBagColor)) {
                            possibleBagColors.add(newOuterBagColor);
                            uncheckedBagColors.add(newOuterBagColor);
                        }
                    }
                });
            }
            checkedBagColors.add(bagColorToCheck);
            uncheckedBagColors.remove(bagColorToCheck);
        }
        return possibleBagColors.size();
    }

    public Object part2(List<String> input) {
        buildBagRelations(input);

        Map<String, Long> bagsToCheck = new HashMap<>();
        bagsToCheck.put("shiny gold", 1L);
        // start with -1 because the outer shiny gold one does not count
        long counter = -1;

        while (!bagsToCheck.isEmpty()) {
            String bagColorToCheck = bagsToCheck.keySet().stream().findFirst().orElseThrow();
            List<Tuple2<Long, String>> innerBags = bagRelations.get(bagColorToCheck);
            for (Tuple2<Long, String> innerBag : innerBags) {
                long newInnerBagCount = innerBag.v1 * bagsToCheck.get(bagColorToCheck);
                Long existingInnerBagCount = bagsToCheck.getOrDefault(innerBag.v2, 0L);
                bagsToCheck.put(innerBag.v2, existingInnerBagCount + newInnerBagCount);
            }
            counter += bagsToCheck.get(bagColorToCheck);
            bagsToCheck.remove(bagColorToCheck);
        }
        return counter;
    }

    private void buildBagRelations(List<String> input) {
        for (String relationString : input) {
            String[] relationOuterAndInner = relationString.split(" contain ");
            List<String> innerBagsStringList = Util.parseToList(relationOuterAndInner[1], ", ");
            List<Tuple2<Long, String>> innerBags = innerBagsStringList.stream().map(this::buildBagCountAndColor).filter(p -> p.v1 != 0L).collect(Collectors.toList());
            bagRelations.put(getColor(relationOuterAndInner[0]), innerBags);
        }
    }

    private Tuple2<Long, String> buildBagCountAndColor(String bagString) {
        String[] splitBag = bagString.split(" ", 2);
        long count = Long.parseLong(splitBag[0].replace("no", "0"));
        String color = getColor(splitBag[1]);
        return Tuple.tuple(count, color);
    }

    private String getColor(String bagWithColor) {
        // works for "bag", "bags" and "bags."
        return bagWithColor.substring(0, bagWithColor.indexOf(" bag"));
    }
}
