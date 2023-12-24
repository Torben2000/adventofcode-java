package de.beachboys.aoc2018;

import de.beachboys.Day;
import org.apache.commons.lang3.mutable.MutableLong;
import org.jooq.lambda.tuple.Tuple;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class Day21 extends Day {

    public Object part1(List<String> input) {
        Device device = new Device(input);
        Optional<Object> returnValue = device.runProgram(programLine -> {
            if (Tuple.tuple("eqrr", Tuple.tuple(2, 0, 4)).equals(programLine)) {
                return Optional.of(device.registers.get(2));
            }
            return Optional.empty();
        });
        return returnValue.orElse("not found");
    }

    public Object part2(List<String> input) {
        MutableLong lastSeen = new MutableLong();
        Set<Long> seenValues = new HashSet<>();

        Device device = new Device(input);
        Optional<Object> returnValue = device.runProgram(programLine -> {
            if (Tuple.tuple("eqrr", Tuple.tuple(2, 0, 4)).equals(programLine)) {
                Long currentValue = device.registers.get(2);
                if (seenValues.contains(currentValue)) {
                    return Optional.of(lastSeen.getValue());
                }
                lastSeen.setValue(currentValue);
                seenValues.add(currentValue);
            }
            return Optional.empty();
        });
        return returnValue.orElse("not found");
    }

}
