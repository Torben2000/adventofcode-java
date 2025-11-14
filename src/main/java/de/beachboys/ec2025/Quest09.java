package de.beachboys.ec2025;

import de.beachboys.Quest;

import java.util.*;
import java.util.regex.Pattern;

public class Quest09 extends Quest {

    @Override
    public Object part1(List<String> input) {
        return runLogicPart1And2(input);
    }

    @Override
    public Object part2(List<String> input) {
        return runLogicPart1And2(input);
    }

    @Override
    public Object part3(List<String> input) {
        List<String> scales = parseInput(input);

        Map<Integer, Integer> familyIndicesByScale = new HashMap<>();
        Map<Integer, Set<Integer>> familiesByFamilyIndex = new HashMap<>();
        int familyIndex = 0;
        long result = 0;
        for (int i = 0; i < scales.size(); i++) {
            String c = scales.get(i);
            boolean found = false;
            for (int j = 0; j < scales.size()-1; j++) {
                if (found) {
                    break;
                }
                if (i!=j) {
                    for (int k = j + 1; k < scales.size(); k++) {
                        if (i != k) {
                            String p1 = scales.get(j);
                            String p2 = scales.get(k);
                            if (isChild(c, p1, p2)) {
                                Set<Integer> family = new HashSet<>();
                                fillFamilyWithScaleFamily(family, i, familyIndicesByScale, familiesByFamilyIndex);
                                fillFamilyWithScaleFamily(family, j, familyIndicesByScale, familiesByFamilyIndex);
                                fillFamilyWithScaleFamily(family, k, familyIndicesByScale, familiesByFamilyIndex);
                                familiesByFamilyIndex.put(familyIndex, family);

                                for (Integer familyMember : family) {
                                    familyIndicesByScale.put(familyMember, familyIndex);
                                }
                                familyIndex++;
                                found = true;
                            }
                        }
                    }
                }

            }
        }

        Set<Integer> biggestFamily = familiesByFamilyIndex.values().stream().max(Comparator.comparingInt(Set::size)).orElseThrow();
        for (int i : biggestFamily) {
            result += i+1;
        }
        return result;
    }

    private static long runLogicPart1And2(List<String> input) {
        List<String> scales = parseInput(input);
        long result = 0;
        for (int i = 0; i < scales.size(); i++) {
            String c = scales.get(i);
            boolean found = false;
            for (int j = 0; j < scales.size()-1; j++) {
                if (found) {
                    break;
                }
                if (i != j) {
                    for (int k = j + 1; k < scales.size(); k++) {
                        if (i != k) {
                            String p1 = scales.get(j);
                            String p2 = scales.get(k);
                            if (isChild(c, p1, p2)) {
                                result += getSimilarity(c, p1, p2);
                                found = true;
                            }
                        }
                    }
                }
            }

        }
        return result;
    }

    private static void fillFamilyWithScaleFamily(Set<Integer> family, int scaleIndex, Map<Integer, Integer> familyIndicesByScale, Map<Integer, Set<Integer>> familiesByFamilyIndex) {
        if (familyIndicesByScale.containsKey(scaleIndex)) {
            family.addAll(familiesByFamilyIndex.get(familyIndicesByScale.get(scaleIndex)));
        } else {
            family.add(scaleIndex);
        }
    }

    private static List<String> parseInput(List<String> input) {
        List<String> scales = new ArrayList<>();
        for (String line : input) {
            scales.add(line.split(Pattern.quote(":"))[1]);
        }
        return scales;
    }

    private static boolean isChild(String child, String parent1, String parent2) {
        for (int i = 0; i < child.length(); i++) {
            if (!child.substring(i, i + 1).equals(parent1.substring(i, i + 1)) && !child.substring(i, i + 1).equals(parent2.substring(i, i + 1))) {
                return false;
            }
        }
        return true;
    }

    private static long getSimilarity(String child, String parent1, String parent2) {
        long parent1Counter = 0;
        long parent2Counter = 0;
        for (int i = 0; i < child.length(); i++) {
            if (child.substring(i, i+1).equals(parent1.substring(i, i+1))) {
                parent1Counter++;
            }
            if (child.substring(i, i+1).equals(parent2.substring(i, i+1))) {
                parent2Counter++;
            }

        }
        return parent1Counter * parent2Counter;
    }
}
