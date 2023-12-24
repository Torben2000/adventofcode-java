package de.beachboys.aoc2015;

import de.beachboys.Day;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day15 extends Day {
    public Object part1(List<String> input) {
        List<Ingredient> ingredients = parseIngredients(input);
        List<List<Integer>> mutations = getPossibleTeaspoonMutations(ingredients.size());
        return getMaxScore(ingredients, mutations, calories -> true);
    }

    public Object part2(List<String> input) {
        List<Ingredient> ingredients = parseIngredients(input);
        List<List<Integer>> mutations = getPossibleTeaspoonMutations(ingredients.size());
        return getMaxScore(ingredients, mutations, calories -> calories == 500);
    }

    private long getMaxScore(List<Ingredient> ingredients, List<List<Integer>> teaspoonMutations, Predicate<Long> caloriesCheck) {
        long maxScore = 0L;
        for (List<Integer> teaspoonMutation : teaspoonMutations) {
            long sumCapacity = 0L;
            long sumDurability= 0L;
            long sumFlavor = 0L;
            long sumTexture = 0L;
            long sumCalories = 0L;
            for (int i = 0; i < teaspoonMutation.size(); i++) {
                int teaspoons = teaspoonMutation.get(i);
                Ingredient ingredient = ingredients.get(i);
                sumCapacity += (long) teaspoons * ingredient.capacity;
                sumDurability += (long) teaspoons * ingredient.durability;
                sumFlavor += (long) teaspoons * ingredient.flavor;
                sumTexture += (long) teaspoons * ingredient.texture;
                sumCalories += (long) teaspoons * ingredient.calories;
            }
            sumCapacity = Math.max(0, sumCapacity);
            sumDurability = Math.max(0, sumDurability);
            sumFlavor = Math.max(0, sumFlavor);
            sumTexture = Math.max(0, sumTexture);
            if (caloriesCheck.test(sumCalories)) {
                long score = sumCapacity * sumDurability * sumFlavor * sumTexture;
                maxScore = Math.max(maxScore, score);
            }
        }

        return maxScore;
    }

    private List<List<Integer>> getPossibleTeaspoonMutations(int numOfIngredients) {
        List<List<Integer>> mutations = new ArrayList<>();
        for (int i = 0; i < numOfIngredients; i++) {
            List<List<Integer>> tempList = new ArrayList<>();
            if (mutations.isEmpty()) {
                for (int j = 0; j <= 100; j++) {
                    tempList.add(List.of(j));
                }
            } else {
                for (int j = 0; j <= 100; j++) {
                    for (List<Integer> list : mutations) {
                        List<Integer> newList = new ArrayList<>(list);
                        newList.add(j);
                        int sum = list.stream().mapToInt(Integer::intValue).sum();
                        if (sum <= 100) {
                            tempList.add(newList);
                        }
                    }
                }
            }
            mutations = tempList;
        }
        return mutations.stream().filter(m -> m.stream().mapToInt(Integer::intValue).sum() == 100).collect(Collectors.toList());
    }

    private List<Ingredient> parseIngredients(List<String> input) {
        List<Ingredient> ingredients = new ArrayList<>();
        for (String line : input) {
            Matcher m = Pattern.compile("(.*): capacity (-*[0-9]+), durability (-*[0-9]+), flavor (-*[0-9]+), texture (-*[0-9]+), calories (-*[0-9]+)").matcher(line);
            if (m.matches()) {
                int capacity = Integer.parseInt(m.group(2));
                int durability = Integer.parseInt(m.group(3));
                int flavor = Integer.parseInt(m.group(4));
                int texture = Integer.parseInt(m.group(5));
                int calories = Integer.parseInt(m.group(6));
                ingredients.add(new Ingredient(capacity, durability, flavor, texture, calories));
            }
        }
        return ingredients;
    }

    private record Ingredient(int capacity, int durability, int flavor, int texture, int calories) {}

}
