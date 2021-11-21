package de.beachboys.aoc2018;

import de.beachboys.Day;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.*;

public class Day19 extends Day {

    public Object part1(List<String> input) {
        Device device = new Device(input);
        device.runProgram(programLine -> Optional.empty());
        return device.registers.get(0);
    }

    public Object part2(List<String> input) {
        Device device = new Device(input);
        device.registers.set(0, 1L);
        Optional<Object> returnValue = device.runProgram(programLine -> {
            if (Pair.with("seti", Triplet.with(1, 5, 2)).equals(programLine)) {
                return Optional.of(getSumOfAllDivisors(device.registers.get(5)));
            }
            return Optional.empty();
        });
        return returnValue.orElse(device.registers.get(0));
    }

    private long getSumOfAllDivisors(long longToCheck) {
        long sum = 0;
        for (long i = 1; i <= longToCheck; i++) {
            if (longToCheck % i == 0) {
                sum += i;
            }
        }
        return sum;
    }

}
