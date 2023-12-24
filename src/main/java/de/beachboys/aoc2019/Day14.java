package de.beachboys.aoc2019;

import de.beachboys.Day;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

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
            long factor = (long) Math.ceil((double) requirements / reaction.output.v1);

            requirementsMap.put(chemical, requirements - factor * reaction.output.v1);
            for (Tuple2<Long, String> inputChemical: reaction.input) {
                requirementsMap.put(inputChemical.v2, Math.addExact(requirementsMap.getOrDefault(inputChemical.v2, 0L), factor * inputChemical.v1));
            }
        }
    }

    private void buildReactionMap(List<String> input) {
        for (String reactionString : input) {
            String[] reactionInputAndOutput = reactionString.split(" => ");
            Tuple2<Long, String> reactionOutput = buildChemicalPair(reactionInputAndOutput[1]);
            List<String> reactionInputStringList = Util.parseToList(reactionInputAndOutput[0], ", ");
            List<Tuple2<Long, String>> reactionInput = reactionInputStringList.stream().map(this::buildChemicalPair).collect(Collectors.toList());
            reactions.put(reactionOutput.v2, new Reaction(reactionInput, reactionOutput));
        }
    }

    private Tuple2<Long, String> buildChemicalPair(String chemicalString) {
        String[] splitChemical = chemicalString.split(" ");
        return Tuple.tuple(Long.valueOf(splitChemical[0]), splitChemical[1]);
    }

    private record Reaction(List<Tuple2<Long, String>> input, Tuple2<Long, String> output) {}
}
