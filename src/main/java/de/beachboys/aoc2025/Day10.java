package de.beachboys.aoc2025;

import com.microsoft.z3.*;
import de.beachboys.Day;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple3;

import java.util.*;
import java.util.regex.Pattern;

public class Day10 extends Day {

    public Object part1(List<String> input) {
        long result = 0;
        for (String line : input) {
            Tuple3<List<Boolean>, List<Set<Integer>>, List<Integer>> parsedLine = parseInputLine(line);
            result += getMinimalButtonPressesForLightState(parsedLine.v1, parsedLine.v2);
        }
        return result;
    }

    public Object part2(List<String> input) {
        long result = 0;
        try (Context z3Context = new Context()) {
            for (String line : input) {
                Tuple3<List<Boolean>, List<Set<Integer>>, List<Integer>> parsedLine = parseInputLine(line);
                result += getMinimalButtonPressesForJoltages(parsedLine.v3, parsedLine.v2, z3Context);
            }
        }
        return result;
    }

    private long getMinimalButtonPressesForLightState(List<Boolean> targetLightState, List<Set<Integer>> buttonConfigs) {
        Map<List<Boolean>, Integer> stateToMinPresses = new HashMap<>();
        List<Boolean> initialState = new ArrayList<>();
        for (int i = 0; i < targetLightState.size(); i++) {
            initialState.add(false);
        }
        stateToMinPresses.put(initialState, 0);

        for (Set<Integer> buttonConfig : buttonConfigs) {
            HashMap<List<Boolean>, Integer> newStateToMinPresses = new HashMap<>(stateToMinPresses);
            for (List<Boolean> state : stateToMinPresses.keySet()) {
                List<Boolean> newState = new ArrayList<>(state);
                for (Integer lightToSwitch : buttonConfig) {
                    newState.set(lightToSwitch, !state.get(lightToSwitch));
                }
                newStateToMinPresses.put(newState, Math.min(newStateToMinPresses.getOrDefault(newState, Integer.MAX_VALUE), stateToMinPresses.get(state) + 1));
            }
            stateToMinPresses = newStateToMinPresses;
        }
        return stateToMinPresses.get(targetLightState);
    }

    private static long getMinimalButtonPressesForJoltages(List<Integer> targetJoltages, List<Set<Integer>> buttonConfigs, Context z3Context) {
        List<IntExpr> buttonPressVars = new ArrayList<>(targetJoltages.size());
        for (int i = 0; i < buttonConfigs.size(); i++) {
            buttonPressVars.add(z3Context.mkIntConst("button" + i));
        }

        Optimize z3Solver = z3Context.mkOptimize();

        for (int i = 0; i < targetJoltages.size(); i++) {
            List<IntExpr> buttonPressesForJoltage = new ArrayList<>(buttonConfigs.size());
            for (int j = 0; j < buttonConfigs.size(); j++) {
                if (buttonConfigs.get(j).contains(i)) {
                    buttonPressesForJoltage.add(buttonPressVars.get(j));
                }
            }
            ArithExpr<IntSort> sumOfButtonPressesForJoltage = z3Context.mkAdd(buttonPressesForJoltage.toArray(new IntExpr[0]));
            IntExpr joltage = z3Context.mkInt(targetJoltages.get(i));
            z3Solver.Add(z3Context.mkEq(sumOfButtonPressesForJoltage, joltage));
        }

        for (IntExpr buttonPressVar : buttonPressVars) {
            z3Solver.Add(z3Context.mkGe(buttonPressVar, z3Context.mkInt(0)));
        }

        z3Solver.MkMinimize(z3Context.mkAdd(buttonPressVars.toArray(new IntExpr[0])));

        long local = 0;
        //noinspection unchecked
        if (Status.SATISFIABLE.equals(z3Solver.Check())) {
            Model z3Model = z3Solver.getModel();
            for (IntExpr buttonPressVar : buttonPressVars) {
                IntNum numButtonPresses = (IntNum) z3Model.eval(buttonPressVar, false);
                local += numButtonPresses.getInt();
            }
        } else {
            throw new IllegalArgumentException();
        }
        return local;
    }

    private static Tuple3<List<Boolean>, List<Set<Integer>>, List<Integer>> parseInputLine(String line) {
        String[] splitLine = line.split(Pattern.quote(" "));

        List<Boolean> targetLightStates = new ArrayList<>();
        for (int j = 0; j < splitLine[0].length() - 2; j++) {
            if('#' == splitLine[0].charAt(j + 1)) {
                targetLightStates.add(true);
            } else {
                targetLightStates.add(false);
            }
        }

        List<Set<Integer>> buttonConfigs = new ArrayList<>();
        for (int j = 1; j < splitLine.length-1; j++) {
            Set<Integer> button = new HashSet<>();
            String[] buttonTargets = splitLine[j].substring(1, splitLine[j].length()-1).split(",");
            for (String buttonTarget : buttonTargets) {
                button.add(Integer.parseInt(buttonTarget));
            }
            buttonConfigs.add(button);
        }

        List<Integer> targetJoltages = new ArrayList<>();
        String[] targetJoltagesAsStrings = splitLine[splitLine.length-1].substring(1, splitLine[splitLine.length-1].length()-1).split(",");
        for (String targetJoltageAsString : targetJoltagesAsStrings) {
            targetJoltages.add(Integer.parseInt(targetJoltageAsString));
        }
        return Tuple.tuple(targetLightStates, buttonConfigs, targetJoltages);
    }

}
