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

    private final Map<String, Long> requirements = new HashMap<>();

    public Object part1(List<String> input) {
        buildReactionMap(input);

        return calculateOrePerFuel();
    }

    private Long calculateOrePerFuel() {
        requirements.put("FUEL", 1L);
        while (true) {
            String chemical = requirements.keySet().stream().filter(key -> !"ORE".equals(key) && requirements.get(key) > 0).findFirst().orElse("ORE");
            if ("ORE".equals(chemical)) {
                return requirements.getOrDefault("ORE", 0L);
            }
            Reaction reaction = reactions.get(chemical);
            requirements.put(chemical, requirements.getOrDefault(chemical, 0L) - reaction.output.getValue0());
            for (Pair<Long, String> inputChemical: reaction.input) {
                requirements.put(inputChemical.getValue1(), requirements.getOrDefault(inputChemical.getValue1(), 0L) + inputChemical.getValue0());
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

    public Object part2(List<String> input) {
        return 2;
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
