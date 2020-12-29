package de.beachboys.aoc2015;

import de.beachboys.Day;
import org.javatuples.Pair;

import java.util.*;
import java.util.function.Predicate;

/**
 * Part 2 is very ugly because of a needed optimization that just works by "luck"
 */
public class Day19 extends Day {

    // the lower the faster, but might fail or deliver inaccurate results...16 is the lowest number that works for my input
    private static final int MAX_LENGTH_CHANGEABLE_END = 16;

    private String moleculeFromInput;
    private final List<Pair<String, String>> rules = new ArrayList<>();

    public Object part1(List<String> input) {
        parseRules(input);

        Map<String, Integer> molecules = buildNextMolecules(moleculeFromInput, 0, m -> true);
        return molecules.size();
    }

    private Map<String, Integer> buildNextMolecules(String initialMolecule, int startIndexOfReplacement, Predicate<String> moleculeFilter) {
        Map<String, Integer> molecules = new HashMap<>();
        for (Pair<String, String> rule : rules) {
            int currentIndex = initialMolecule.indexOf(rule.getValue0(), startIndexOfReplacement);
            while (currentIndex >= 0) {
                String molecule = initialMolecule.substring(0, currentIndex) + rule.getValue1() + initialMolecule.substring(currentIndex + rule.getValue0().length());
                if (moleculeFilter.test(molecule) && (!molecules.containsKey(molecule) || currentIndex < molecules.get(molecule))) {
                    molecules.put(molecule, currentIndex);
                }
                currentIndex = initialMolecule.indexOf(rule.getValue0(), currentIndex + 1);
            }
        }
        return molecules;
    }

    public Object part2(List<String> input) {
        parseRules(input);

        Set<String> allMolecules = new HashSet<>();
        Map<String, Integer> moleculesCurrentStep = new HashMap<>();
        moleculesCurrentStep.put("e", 0);
        allMolecules.add("e");

        int step = 0;
        while (!moleculesCurrentStep.containsKey(moleculeFromInput) && !moleculesCurrentStep.isEmpty()) {
            step++;
            Map<String, Integer> molecules = new HashMap<>();
            for (String initialMolecule : moleculesCurrentStep.keySet()) {
                molecules.putAll(buildNextMolecules(initialMolecule, moleculesCurrentStep.get(initialMolecule), m -> this.isValidMoleculeForPart2(m) && !allMolecules.contains(m)));
            }
            allMolecules.addAll(molecules.keySet());
            moleculesCurrentStep = molecules;
        }

        return moleculesCurrentStep.containsKey(moleculeFromInput) ? step : -1;
    }

    private boolean isValidMoleculeForPart2(String molecule) {
        int length = molecule.length();
        return length <= moleculeFromInput.length() && (length <= MAX_LENGTH_CHANGEABLE_END || molecule.substring(0, length - MAX_LENGTH_CHANGEABLE_END).equals(moleculeFromInput.substring(0, length - MAX_LENGTH_CHANGEABLE_END)));
    }

    private void parseRules(List<String> input) {
        for (String rule : input.subList(0, input.size() - 2)) {
            String[] leftAndRight = rule.split(" => ");
            rules.add(Pair.with(leftAndRight[0], leftAndRight[1]));
        }
        moleculeFromInput = input.get(input.size() - 1);
    }

}
