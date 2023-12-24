package de.beachboys.aoc2018;

import de.beachboys.Day;
import org.jooq.lambda.tuple.Tuple;

import java.util.List;
import java.util.Optional;

public class Day19 extends Day {

    public Object part1(List<String> input) {
        Device device = new Device(input);
        device.runProgram(programLine -> Optional.empty());
        return device.registers.getFirst();
    }

    public Object part2(List<String> input) {
        Device device = new Device(input);
        device.registers.set(0, 1L);
        Optional<Object> returnValue = device.runProgram(programLine -> {
            if (Tuple.tuple("seti", Tuple.tuple(1, 5, 2)).equals(programLine)) {
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
