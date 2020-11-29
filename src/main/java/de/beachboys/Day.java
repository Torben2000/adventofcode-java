package de.beachboys;

import java.util.List;

public abstract class Day {

    public IOHelper io = new IOHelper();

    public abstract Object part1(List<String> input);

    public abstract Object part2(List<String> input);

}