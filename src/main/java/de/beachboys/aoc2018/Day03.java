package de.beachboys.aoc2018;

import de.beachboys.Day;
import org.javatuples.Pair;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day03 extends Day {

    public Object part1(List<String> input) {
        return getPositionToClaimIds(input).values().stream().filter(set -> set.size() > 1).count();
    }

    public Object part2(List<String> input) {
        Map<Pair<Integer, Integer>, Set<Integer>> positionToClaimIds = getPositionToClaimIds(input);
        final Set<Integer> claimsThatAreAloneAtAnyPosition = positionToClaimIds.values().stream().filter(set -> set.size() == 1).reduce(new HashSet<>(), (set1, set2) -> {
            set1.addAll(set2);
            return set1;
        });
        positionToClaimIds.values().stream().filter(set -> set.size() > 1).forEach(claimsThatAreAloneAtAnyPosition::removeAll);

        if (claimsThatAreAloneAtAnyPosition.size() != 1) {
            return "error";
        }

        return claimsThatAreAloneAtAnyPosition.stream().findFirst().orElseThrow();
    }

    private Map<Pair<Integer, Integer>, Set<Integer>> getPositionToClaimIds(List<String> input) {
        Map<Pair<Integer, Integer>, Set<Integer>> positionToClaimIds = new HashMap<>();
        Pattern p = Pattern.compile("#([0-9]+) @ ([0-9]+),([0-9]+): ([0-9]+)x([0-9]+)");
        for (String line : input) {
            Matcher m = p.matcher(line);
            if (m.matches()) {
                int id = Integer.parseInt(m.group(1));
                int x = Integer.parseInt(m.group(2));
                int y = Integer.parseInt(m.group(3));
                int w = Integer.parseInt(m.group(4));
                int h = Integer.parseInt(m.group(5));
                for (int i = x; i < x + w; i++) {
                    for (int j = y; j < y + h; j++) {
                        Pair<Integer, Integer> position = Pair.with(i, j);
                        positionToClaimIds.putIfAbsent(position, new HashSet<>());
                        positionToClaimIds.get(position).add(id);
                    }
                }
            }
        }
        return positionToClaimIds;
    }

}
