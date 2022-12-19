package de.beachboys.aoc2022;

import de.beachboys.Day;

import java.util.*;

public class Day19 extends Day {

    public Object part1(List<String> input) {
        List<Long> results = getMaxGeodesOfBlueprints(input, 24, input.size());
        long result = 0;
        for (int i = 0; i < results.size(); i++) {
            result += (i + 1) * results.get(i);
        }
        return result;
    }

    public Object part2(List<String> input) {
        List<Long> results = getMaxGeodesOfBlueprints(input, 32, 3);
        long result = 1;
        for (Long resultOfBlueprint : results) {
            result *= resultOfBlueprint;
        }
        return result;
    }

    private List<Long> getMaxGeodesOfBlueprints(List<String> input, int time, int numberOfResults) {
        List<Long> results = new ArrayList<>();
        for (int i = 0; i < Math.min(numberOfResults, input.size()); i++) {
            results.add(getMaxGeodesOfBlueprint(parseRules(input, i), time));
        }
        return results;
    }

    private Blueprint parseRules(List<String> input, int i) {
        String line = input.get(i);
        String[] split = line.split(" ");
        int oreForOreRobot = Integer.parseInt(split[6]);
        int oreForClayRobot = Integer.parseInt(split[12]);
        int oreForObsidianRobot = Integer.parseInt(split[18]);
        int clayForObsidianRobot = Integer.parseInt(split[21]);
        int oreForGeodeRobot = Integer.parseInt(split[27]);
        int obsidianForGeodeRobot = Integer.parseInt(split[30]);
        return new Blueprint(oreForOreRobot, oreForClayRobot, oreForObsidianRobot, clayForObsidianRobot, oreForGeodeRobot, obsidianForGeodeRobot);
    }


    private long getMaxGeodesOfBlueprint(Blueprint blueprint, int time) {
        Set<State> calculatedStates = new HashSet<>();
        Queue<State> queue = new LinkedList<>();

        queue.add(new State(time, 1, 0, 0, 0, 0, 0, 0, 0));
        long result = 0;
        while (!queue.isEmpty()) {
            State state = queue.poll();
            result = Math.max(result, state.geodeCount);
            if (state.time == 0) {
                continue;
            }

            optimizeState(blueprint, state);
            if (calculatedStates.contains(state)) {
                continue;
            }
            calculatedStates.add(state);

            addFollowUpStatesToQueue(blueprint, queue, state);
        }
        return result;
    }

    private void addFollowUpStatesToQueue(Blueprint blueprint, Queue<State> queue, State state) {
        int newOreCount = state.oreCount + state.oreRobots;
        int newClayCount = state.clayCount + state.clayRobots;
        int newObsidianCount = state.obsidianCount + state.obsidianRobots;
        int newGeodeCount = state.geodeCount + state.geodeRobots;
        if (state.oreCount >= blueprint.oreForOreRobot) {
            queue.add(new State(state.time - 1, state.oreRobots + 1, state.clayRobots, state.obsidianRobots, state.geodeRobots, newOreCount - blueprint.oreForOreRobot, newClayCount, newObsidianCount, newGeodeCount));
        }
        if (state.oreCount >= blueprint.oreForClayRobot) {
            queue.add(new State(state.time - 1, state.oreRobots, state.clayRobots + 1, state.obsidianRobots, state.geodeRobots, newOreCount - blueprint.oreForClayRobot, newClayCount, newObsidianCount, newGeodeCount));
        }
        if (state.oreCount >= blueprint.oreForObsidianRobot) {
            if (state.clayCount >= blueprint.clayForObsidianRobot) {
                queue.add(new State(state.time - 1, state.oreRobots, state.clayRobots, state.obsidianRobots + 1, state.geodeRobots, newOreCount - blueprint.oreForObsidianRobot, newClayCount - blueprint.clayForObsidianRobot, newObsidianCount, newGeodeCount));
            }
        }
        if (state.oreCount >= blueprint.oreForGeodeRobot) {
            if (state.obsidianCount >= blueprint.obsidianForGeodeRobot) {
                queue.add(new State(state.time - 1, state.oreRobots, state.clayRobots, state.obsidianRobots, state.geodeRobots + 1, newOreCount - blueprint.oreForGeodeRobot, newClayCount, newObsidianCount - blueprint.obsidianForGeodeRobot, newGeodeCount));
            }
        }
        queue.add(new State(state.time - 1, state.oreRobots, state.clayRobots, state.obsidianRobots, state.geodeRobots, newOreCount, newClayCount, newObsidianCount, newGeodeCount));
    }

    private static void optimizeState(Blueprint blueprint, State state) {
        int maximumOreForRobot = Math.max(Math.max(Math.max(blueprint.oreForOreRobot, blueprint.oreForClayRobot), blueprint.oreForObsidianRobot), blueprint.oreForGeodeRobot);

        // don't produce more stuff per time frame than you can spend => just remove superfluous robots
        if (state.oreRobots > maximumOreForRobot) {
            state.oreRobots = maximumOreForRobot;
        }
        if (state.clayRobots > blueprint.clayForObsidianRobot) {
            state.clayRobots = blueprint.clayForObsidianRobot;
        }
        if (state.obsidianRobots > blueprint.obsidianForGeodeRobot) {
            state.obsidianRobots = blueprint.obsidianForGeodeRobot;
        }

        // don't store more stuff than you can spend => just remove superfluous stuff
        if (state.oreCount > state.time * maximumOreForRobot - state.oreRobots * (state.time - 1)) {
            state.oreCount = state.time * maximumOreForRobot - state.oreRobots * (state.time - 1);
        }
        if (state.clayCount > state.time * blueprint.clayForObsidianRobot - state.clayRobots * (state.time - 1)) {
            state.clayCount = state.time * blueprint.clayForObsidianRobot - state.clayRobots * (state.time - 1);
        }
        if (state.obsidianCount > state.time * blueprint.obsidianForGeodeRobot - state.obsidianRobots * (state.time - 1)) {
            state.obsidianCount = state.time * blueprint.obsidianForGeodeRobot - state.obsidianRobots * (state.time - 1);
        }
    }

    private static class Blueprint {
        private final int oreForOreRobot;
        private final int oreForClayRobot;
        private final int oreForObsidianRobot;
        private final int clayForObsidianRobot;
        private final int oreForGeodeRobot;
        private final int obsidianForGeodeRobot;

        public Blueprint(int oreForOreRobot, int oreForClayRobot, int oreForObsidianRobot, int clayForObsidianRobot, int oreForGeodeRobot, int obsidianForGeodeRobot) {
            this.oreForOreRobot = oreForOreRobot;
            this.oreForClayRobot = oreForClayRobot;
            this.oreForObsidianRobot = oreForObsidianRobot;
            this.clayForObsidianRobot = clayForObsidianRobot;
            this.oreForGeodeRobot = oreForGeodeRobot;
            this.obsidianForGeodeRobot = obsidianForGeodeRobot;
        }

    }

    private static class State {
        private final int time;
        private int oreRobots;
        private int clayRobots;
        private int obsidianRobots;
        private final int geodeRobots;
        private int oreCount;
        private int clayCount;
        private int obsidianCount;
        private final int geodeCount;

        public State(int time, int oreRobots, int clayRobots, int obsidianRobots, int geodeRobots, int oreCount, int clayCount, int obsidianCount, int geodeCount) {
            this.time = time;
            this.oreRobots = oreRobots;
            this.clayRobots = clayRobots;
            this.obsidianRobots = obsidianRobots;
            this.geodeRobots = geodeRobots;
            this.oreCount = oreCount;
            this.clayCount = clayCount;
            this.obsidianCount = obsidianCount;
            this.geodeCount = geodeCount;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            State state = (State) o;
            return time == state.time && oreRobots == state.oreRobots && clayRobots == state.clayRobots && obsidianRobots == state.obsidianRobots && geodeRobots == state.geodeRobots && oreCount == state.oreCount && clayCount == state.clayCount && obsidianCount == state.obsidianCount && geodeCount == state.geodeCount;
        }

        @Override
        public int hashCode() {
            return Objects.hash(time, oreRobots, clayRobots, obsidianRobots, geodeRobots, oreCount, clayCount, obsidianCount, geodeCount);
        }
    }

}
