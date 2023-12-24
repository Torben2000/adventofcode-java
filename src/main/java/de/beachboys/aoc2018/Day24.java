package de.beachboys.aoc2018;

import de.beachboys.Day;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day24 extends Day {

    public Object part1(List<String> input) {
        List<Group> groups = parseGroups(input);
        if (runFight(groups)) {
            return groups.stream().mapToLong(group -> group.units).sum();
        }
        return "Is is a draw!";
    }

    public Object part2(List<String> input) {
        List<Group> groups = parseGroups(input);
        long boost = 0L;
        List<Group> boostedGroups = List.of();
        boolean immuneSystemWon = false;
        while (!immuneSystemWon) {
            boost++;
            boostedGroups = createBoostedGroups(groups, boost);
            if (runFight(boostedGroups)) {
                immuneSystemWon = boostedGroups.stream().findAny().orElseThrow().type == GroupType.IMMUNE_SYSTEM;
            }
        }
        return boostedGroups.stream().mapToLong(group -> group.units).sum();
    }

    private List<Group> createBoostedGroups(List<Group> groups, long boost) {
        List<Group> boostedGroups = new ArrayList<>(groups.size());
        groups.forEach(group -> boostedGroups.add(new Group(group.type, group.units, group.hitPoints, group.immunities, group.weaknesses, group.damage + (group.type == GroupType.IMMUNE_SYSTEM ? boost : 0), group.attack, group.initiative)));
        return boostedGroups;
    }

    private boolean runFight(List<Group> groups) {
        while (groups.stream().map(group -> group.type).distinct().count() > 1) {
            Map<Group, Group> plannedAttacks = new HashMap<>();
            groups.stream().sorted(Comparator.comparingLong(Group::getTargetSelectionInitiative).reversed()).forEachOrdered(group -> {
                Optional<Group> target = groups.stream().filter(possibleTarget -> possibleTarget.type != group.type && !plannedAttacks.containsValue(possibleTarget)).max(Comparator.comparingLong(t -> ((Group) t).getDealtDamage(group.attack, group.getEffectivePower())).thenComparingLong(t -> ((Group) t).getTargetSelectionInitiative()));
                target.ifPresent(value -> {
                    long dealtDamage = value.getDealtDamage(group.attack, group.getEffectivePower());
                    if (dealtDamage > 0) {
                        plannedAttacks.put(group, value);
                    }
                });
            });

            AtomicBoolean successfulAttack = new AtomicBoolean(false);
            groups.stream().sorted(Comparator.comparingLong(Group::getInitiative).reversed()).forEachOrdered(group -> {
                if (group.units > 0) {
                    Group target = plannedAttacks.get(group);
                    if (target != null) {
                        long dealtDamage = target.getDealtDamage(group.attack, group.getEffectivePower());
                        long unitsKilled = dealtDamage / target.hitPoints;
                        if (target.units > 0 && unitsKilled > 0) {
                            successfulAttack.set(true);
                        }
                        target.units -= unitsKilled;
                        if (target.units <= 0) {
                            groups.remove(target);
                        }
                    }
                } else {
                    group.units = 0;
                }
            });
            if (!successfulAttack.get()) {
                return false;
            }
        }
        return true;
    }

    private List<Group> parseGroups(List<String> input) {
        List<Group> groups = new ArrayList<>();
        Pattern pattern = Pattern.compile("([0-9]+) units each with ([0-9]+) hit points (\\(.*\\) )*with an attack that does ([0-9]+) ([a-z]+) damage at initiative ([0-9]+)");
        GroupType currentGroupType = GroupType.IMMUNE_SYSTEM;
        for (String line : input) {
            if (!line.isEmpty()) {
                if ("Immune System:".equals(line)) {
                    currentGroupType = GroupType.IMMUNE_SYSTEM;
                } else if ("Infection:".equals(line)) {
                    currentGroupType = GroupType.INFECTION;
                } else {
                    Matcher m = pattern.matcher(line);
                    if (m.matches()) {
                        List<String> immunities = List.of();
                        List<String> weaknesses = List.of();
                        int offset = 0;
                        if (m.groupCount() == 6) {
                            offset = 1;
                            String immunitiesWeaknessesString = m.group(3);
                            if (immunitiesWeaknessesString != null) {
                                String[] parts = immunitiesWeaknessesString.substring(1, immunitiesWeaknessesString.length() - 2).split(";");
                                for (String part : parts) {
                                    List<String> list = List.of(part.substring(part.indexOf(" to ") + " to ".length()).split(", "));
                                    if (part.trim().startsWith("immune")) {
                                        immunities = list;
                                    } else {
                                        weaknesses = list;
                                    }
                                }
                            }
                        }
                        groups.add(new Group(currentGroupType, Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)), immunities, weaknesses, Integer.parseInt(m.group(3 + offset)), m.group(4 + offset), Integer.parseInt(m.group(5 + offset))));
                    } else {
                        throw new IllegalArgumentException();
                    }
                }
            }
        }
        return groups;
    }

    private static class Group {
        final GroupType type;
        long units;
        final long hitPoints;
        final long damage;
        final long initiative;
        final String attack;
        final List<String> immunities;
        final List<String> weaknesses;

        public Group(GroupType type, long units, long hitPoints, List<String> immunities, List<String> weaknesses, long damage, String attack, long initiative) {
            this.type = type;
            this.units = units;
            this.hitPoints = hitPoints;
            this.damage = damage;
            this.initiative = initiative;
            this.attack = attack;
            this.immunities = immunities;
            this.weaknesses = weaknesses;
        }

        long getInitiative() {
            return initiative;
        }

        long getEffectivePower() {
            return units * damage;
        }

        long getTargetSelectionInitiative() {
            return getEffectivePower() * 100 + initiative;
        }

        long getDealtDamage(String attackType, long attackersEffectivePower) {
            if (immunities.contains(attackType)) {
                return 0;
            } else if (weaknesses.contains(attackType)) {
                return 2 * attackersEffectivePower;
            }
            return attackersEffectivePower;
        }

        @Override
        public String toString() {
            return "Group{" +
                    "type=" + type +
                    ", units=" + units +
                    ", hitPoints=" + hitPoints +
                    ", damage=" + damage +
                    ", initiative=" + initiative +
                    ", attack='" + attack + '\'' +
                    ", immunities=" + immunities +
                    ", weaknesses=" + weaknesses +
                    '}';
        }
    }

    private enum GroupType {
        IMMUNE_SYSTEM, INFECTION
    }
}
