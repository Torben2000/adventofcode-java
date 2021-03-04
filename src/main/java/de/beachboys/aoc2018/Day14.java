package de.beachboys.aoc2018;

import de.beachboys.Day;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;

public class Day14 extends Day {

    private final List<Integer> recipeScores = new ArrayList<>();
    private final StringBuilder recipeScoreString = new StringBuilder();


    public Object part1(List<String> input) {
        int numberOfRecipes = Integer.parseInt(input.get(0));

        runLogic(() -> recipeScores.size() < numberOfRecipes + 10);

        StringBuilder returnValue = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            returnValue.append(recipeScores.get(numberOfRecipes + i));
        }
        return returnValue.toString();
    }

    public Object part2(List<String> input) {
        String scoreSequence = input.get(0);

        runLogic(() -> !recipeScoreString.substring(Math.max(0, recipeScoreString.length() - scoreSequence.length() - 1)).contains(scoreSequence));

        return recipeScoreString.indexOf(scoreSequence);
    }

    private void runLogic(BooleanSupplier continueLoop) {
        recipeScores.clear();
        recipeScoreString.delete(0, recipeScoreString.length());
        recipeScores.add(3);
        recipeScores.add(7);
        recipeScoreString.append(37);

        int elf1Index = 0;
        int elf2Index = 1;
        while (continueLoop.getAsBoolean()) {
            int elf1Recipe = recipeScores.get(elf1Index);
            int elf2Recipe = recipeScores.get(elf2Index);
            int newRecipeScore = elf1Recipe + elf2Recipe;
            if (newRecipeScore > 9) {
                recipeScores.add(1);
                recipeScores.add(newRecipeScore % 10);
            } else {
                recipeScores.add(newRecipeScore);
            }
            recipeScoreString.append(newRecipeScore);
            elf1Index = (elf1Index + 1 + elf1Recipe) % recipeScores.size();
            elf2Index = (elf2Index + 1 + elf2Recipe) % recipeScores.size();
        }
    }

}
