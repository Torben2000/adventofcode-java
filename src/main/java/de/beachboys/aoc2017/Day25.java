package de.beachboys.aoc2017;

import de.beachboys.Day;
import de.beachboys.Direction;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day25 extends Day {

    private record State(Tuple3<Integer, Direction, String> actionsForZero,
                         Tuple3<Integer, Direction, String> actionsForOne) {}

    private final Map<String, State> states = new HashMap<>();
    private final Map<Integer, Integer> tape = new HashMap<>();
    private int totalSteps;
    private String currentState;

    public Object part1(List<String> input) {
        parseInput(input);

        int currentPosition = 0;
        for (int i = 0; i < totalSteps; i++) {
            State state = states.get(currentState);
            Tuple3<Integer, Direction, String> actions = tape.getOrDefault(currentPosition, 0) == 1 ? state.actionsForOne : state.actionsForZero;
            tape.put(currentPosition, actions.v1);
            currentPosition += actions.v2.stepX;
            currentState = actions.v3;
        }

        return tape.values().stream().filter(i -> i == 1).count();
    }

    private void parseInput(List<String> input) {
        currentState = getSecondToLastCharacter(input.get(0));
        Pattern stepPattern = Pattern.compile("Perform a diagnostic checksum after ([0-9]+) steps.");
        Matcher m = stepPattern.matcher(input.get(1));
        if (m.matches()) {
            totalSteps = Integer.parseInt(m.group(1));
        }
        for (int i = 3; i < input.size(); i = i + 10) {
            String stateName = getSecondToLastCharacter(input.get(i));
            int writeForZero = Integer.parseInt(getSecondToLastCharacter(input.get(i + 2)));
            Direction dirForZero = input.get(i + 3).contains("left") ? Direction.WEST : Direction.EAST;
            String nextStateForZero = getSecondToLastCharacter(input.get(i + 4));
            int writeForOne = Integer.parseInt(getSecondToLastCharacter(input.get(i + 6)));
            Direction dirForOne = input.get(i + 7).contains("left") ? Direction.WEST : Direction.EAST;
            String nextStateForOne = getSecondToLastCharacter(input.get(i + 8));
            State state = new State(Tuple.tuple(writeForZero, dirForZero, nextStateForZero), Tuple.tuple(writeForOne, dirForOne, nextStateForOne));
            states.put(stateName, state);
        }
    }

    private String getSecondToLastCharacter(String string) {
        return string.substring(string.length() - 2, string.length() - 1);
    }

    public Object part2(List<String> input) {
        return "There is no puzzle! :-)";
    }

}
