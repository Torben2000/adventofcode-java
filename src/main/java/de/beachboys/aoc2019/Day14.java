package de.beachboys.aoc2019;

import de.beachboys.Day;
import de.beachboys.Util;
import org.javatuples.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day14 extends Day {

    private final Map<String, Reaction> reactions = new HashMap<>();

    public Object part1(List<String> input) {
        buildReactionMap(input);
        return calculateRequiredOre(1);
    }

    public Object part2(List<String> input) {
        buildReactionMap(input);
        long totalOre = 1000000000000L;
        long minFuel = totalOre / calculateRequiredOre(1);
        long maxFuel = minFuel * 2;

        while (maxFuel - minFuel > 1) {
            long testFuel = (minFuel + maxFuel) / 2;
            long orePerTestFuel = calculateRequiredOre(testFuel);
            if (orePerTestFuel <= totalOre) {
                minFuel = testFuel;
            } else {
                maxFuel = testFuel;
            }
        }
        return minFuel;
    }

    private Long calculateRequiredOre(long fuelAmount) {
        HashMap<String, Long> requirementsMap = new HashMap<>();
        requirementsMap.put("FUEL", fuelAmount);
        while (true) {
            String chemical = requirementsMap.keySet().stream().filter(key -> !"ORE".equals(key) && requirementsMap.get(key) > 0).findFirst().orElse("ORE");
            if ("ORE".equals(chemical)) {
                return requirementsMap.getOrDefault("ORE", 0L);
            }
            Reaction reaction = reactions.get(chemical);
            long requirements = requirementsMap.getOrDefault(chemical, 0L);
            long factor = (long) Math.ceil((double) requirements / reaction.output.getValue0());

            requirementsMap.put(chemical, requirements - factor * reaction.output.getValue0());
            for (Pair<Long, String> inputChemical: reaction.input) {
                requirementsMap.put(inputChemical.getValue1(), Math.addExact(requirementsMap.getOrDefault(inputChemical.getValue1(), 0L), factor * inputChemical.getValue0()));
            }
        }
    }

    private void buildReactionMap(List<String> input) {
        for (String reactionString : input) {
            String[] reactionInputAndOutput = reactionString.split(" => ");
            Pair<Long, String> reactionOutput = buildChemicalPair(reactionInputAndOutput[1]);
            List<String> reactionInputStringList = Util.parseToList(reactionInputAndOutput[0], ", ");
            List<Pair<Long, String>> reactionInput = reactionInputStringList.stream().map(this::buildChemicalPair).collect(Collectors.toList());
            reactions.put(reactionOutput.getValue1(), new Reaction(reactionInput, reactionOutput));
        }
    }

    private Pair<Long, String> buildChemicalPair(String chemicalString) {
        String[] splitChemical = chemicalString.split(" ");
        return Pair.with(Long.valueOf(splitChemical[0]), splitChemical[1]);
    }
    private static class Reaction {
        public final Pair<Long, String> output;
        public final List<Pair<Long, String>> input;

        public Reaction(List<Pair<Long, String>> input, Pair<Long, String> output) {
            this.input = input;
            this.output = output;
        }
    }
}
