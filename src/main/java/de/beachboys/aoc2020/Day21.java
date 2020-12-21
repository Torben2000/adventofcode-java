package de.beachboys.aoc2020;

import de.beachboys.Day;

import java.util.*;
import java.util.stream.Collectors;

public class Day21 extends Day {

    private final List<String> allIngredients = new ArrayList<>();

    private final TreeMap<String, Set<String>> allergensToIngredients = new TreeMap<>();

    public Object part1(List<String> input) {
        prepareData(input);

        for (Set<String> ingredients : allergensToIngredients.values()) {
            allIngredients.removeAll(ingredients);
        }

        return allIngredients.size();
    }

    public Object part2(List<String> input) {
        prepareData(input);

        return allergensToIngredients.values().stream().map(set -> set.stream().findFirst().orElseThrow()).collect(Collectors.joining(","));
    }

    private void prepareData(List<String> input) {
        for (String line : input) {
            String[] ingredientsAndAllergens = line.substring(0, line.length() - 1).split(" \\(contains ");
            Set<String> ingredients = new HashSet<>(Arrays.asList(ingredientsAndAllergens[0].split(" ")));
            allIngredients.addAll(ingredients);
            for (String allergens : ingredientsAndAllergens[1].split(", ")) {
                Set<String> ingredientsForAllergen = new HashSet<>(ingredients);
                if (allergensToIngredients.containsKey(allergens)) {
                    ingredientsForAllergen.retainAll(allergensToIngredients.get(allergens));
                }
                allergensToIngredients.put(allergens, ingredientsForAllergen);
            }
        }
        while (allergensToIngredients.values().stream().anyMatch(ingredients -> ingredients.size() > 1)) {
            for (Set<String> ingredients1 : allergensToIngredients.values()) {
                for (Set<String> ingredients2 : allergensToIngredients.values()) {
                    if (ingredients1 != ingredients2 && ingredients2.size() == 1) {
                        ingredients1.removeAll(ingredients2);
                    }
                }
            }
        }
    }

}
