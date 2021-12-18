package de.beachboys.aoc2021;

import de.beachboys.Day;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day18 extends Day {

    Pattern pairPattern = Pattern.compile("(\\[#[0-9]+,#[0-9]+])");
    Pattern numberPattern = Pattern.compile("([\\[,])([0-9]+)");

    public Long part1(List<String> input) {
        List<SnailfishNumber> snailfishNumbers = parseSnailfishNumbers(input);
        SnailfishNumber snailfishNumber = sumUpSnails(snailfishNumbers);
        return getMagnitude(snailfishNumber);
    }

    public Object part2(List<String> input) {
        long max = 0;
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.size(); j++) {
                if (i == j) {
                    continue;
                }
                max = Math.max(max, part1(List.of(input.get(i), input.get(j))));
            }
        }
        return max;
    }

    public List<SnailfishNumber> parseSnailfishNumbers(List<String> input) {
        List<SnailfishNumber> snailfishNumbers = new ArrayList<>();
        for (String line : input) {
            snailfishNumbers.add(parseSnailfishNumber(line));
        }
        return snailfishNumbers;
    }

    public SnailfishNumber sumUpSnails(List<SnailfishNumber> snailfishNumbers) {
        return snailfishNumbers.stream().reduce(null, this::addSnailfishNumbers);
    }

    public SnailfishNumber parseSnailfishNumber(String line) {
        Map<String, SnailfishNumber> map = new HashMap<>();
        int replacementCounter = 0;
        String workingString = line;
        while (true) {
            Matcher m = numberPattern.matcher(workingString);
            if (m.find()) {
                String match = m.group(2);
                String replacement = "#" + replacementCounter++;
                map.put(replacement, new SnailfishNumber(Integer.parseInt(match)));
                workingString = m.replaceFirst(m.group(1) + replacement);
            } else {
                break;
            }
        }
        SnailfishNumber snailfishNumber = null;
        while (true) {
            Matcher m = pairPattern.matcher(workingString);
            if (m.find()) {
                String match = m.group(1);
                String[] splitMatch = match.substring(1, match.length() - 1).split(",");
                SnailfishNumber left = map.get(splitMatch[0]);
                SnailfishNumber right = map.get(splitMatch[1]);
                snailfishNumber = new SnailfishNumber(left, right);
                String replacement = "#" + replacementCounter++;
                map.put(replacement, snailfishNumber);
                workingString = m.replaceFirst(replacement);
            } else {
                break;
            }
        }
        return snailfishNumber;
    }

    protected long getMagnitude(SnailfishNumber snailfishNumber) {
        if (snailfishNumber.value != -1) {
            return snailfishNumber.value;
        }
        return 3 * getMagnitude(snailfishNumber.left) + 2 * getMagnitude(snailfishNumber.right);
    }

    private SnailfishNumber addSnailfishNumbers(SnailfishNumber snailfishNumber1, SnailfishNumber snailfishNumber2) {
        if (snailfishNumber1 == null) {
            return snailfishNumber2;
        } else if (snailfishNumber2 == null) {
            return snailfishNumber1;
        }
        SnailfishNumber snailfishNumber = new SnailfishNumber(snailfishNumber1, snailfishNumber2);
        return reduceSnailfishNumber(snailfishNumber);
    }

    private SnailfishNumber reduceSnailfishNumber(SnailfishNumber snailfishNumber) {
        String oldString = "";
        while (!oldString.equals(snailfishNumber.toString())) {
            oldString = snailfishNumber.toString();
            Pair<Boolean, SnailfishNumber> explodeResult = explodeLeftmostPair(snailfishNumber, 0);
            if (explodeResult.getValue0()) {
                snailfishNumber = explodeResult.getValue1();
            } else {
                snailfishNumber = splitLeftmostNumber(snailfishNumber).getValue1();
            }
        }
        return snailfishNumber;
    }

    public Pair<Boolean, SnailfishNumber> splitLeftmostNumber(SnailfishNumber snailfishNumber) {
        boolean numberSplit = false;
        if (snailfishNumber.value != -1) {
            if (snailfishNumber.value >= 10) {
                SnailfishNumber newSnailfishNumberLeft = new SnailfishNumber(snailfishNumber.value / 2);
                SnailfishNumber newSnailfishNumberRight = new SnailfishNumber((int) Math.ceil(((double) snailfishNumber.value) / 2));
                SnailfishNumber newSnailfishNumber = new SnailfishNumber(newSnailfishNumberLeft, newSnailfishNumberRight);

                newSnailfishNumber.parent = snailfishNumber.parent;
                return Pair.with(true, newSnailfishNumber);
            }
        } else {
            Pair<Boolean, SnailfishNumber> left = splitLeftmostNumber(snailfishNumber.left);
            if (left.getValue0()) {
                snailfishNumber.left = left.getValue1();
                numberSplit = true;
            } else {
                Pair<Boolean, SnailfishNumber> right = splitLeftmostNumber(snailfishNumber.right);
                if (right.getValue0()) {
                    snailfishNumber.right = right.getValue1();
                    numberSplit = true;
                }
            }
        }
        return Pair.with(numberSplit, snailfishNumber);
    }

    public Pair<Boolean, SnailfishNumber> explodeLeftmostPair(SnailfishNumber snailfishNumber, int i) {
        boolean pairExploded = false;
        if (snailfishNumber.value == -1) {
            Pair<Boolean, SnailfishNumber> explodeResultLeft = explodeLeftmostPair(snailfishNumber.left, i + 1);
            if (explodeResultLeft.getValue0()) {
                snailfishNumber.left = explodeResultLeft.getValue1();
                pairExploded = true;
            } else {
                Pair<Boolean, SnailfishNumber> explodeResultRight = explodeLeftmostPair(snailfishNumber.right, i + 1);
                if (explodeResultRight.getValue0()) {
                    snailfishNumber.right = explodeResultRight.getValue1();
                    pairExploded = true;
                }
            }

            if (!pairExploded && i >= 4) {

                SnailfishNumber nextLeftNeighbor = findNextValueNeighbor(snailfishNumber, s1 -> s1.parent.left, s -> s.right);
                if (nextLeftNeighbor != null) {
                    nextLeftNeighbor.value += snailfishNumber.left.value;
                }
                SnailfishNumber nextRightNeighbor = findNextValueNeighbor(snailfishNumber, s1 -> s1.parent.right, s -> s.left);
                if (nextRightNeighbor != null) {
                    nextRightNeighbor.value += snailfishNumber.right.value;
                }
                
                SnailfishNumber newSnailfishNumber = new SnailfishNumber(0);
                newSnailfishNumber.parent = snailfishNumber.parent;
                return Pair.with(true, newSnailfishNumber);
             }
        }
        return Pair.with(pairExploded, snailfishNumber);
    }

    private SnailfishNumber findNextValueNeighbor(SnailfishNumber snailfishNumber, Function<SnailfishNumber, SnailfishNumber> getSiblingToGoDownAt, Function<SnailfishNumber, SnailfishNumber> nextSnailfishNumberWhenGoingDown) {
        if (snailfishNumber.parent == null) {
            return null;
        }
        SnailfishNumber possibleSibling = getSiblingToGoDownAt.apply(snailfishNumber);
        if (possibleSibling == snailfishNumber) {
            return findNextValueNeighbor(snailfishNumber.parent, getSiblingToGoDownAt, nextSnailfishNumberWhenGoingDown);
        }
        return moveThroughTreeAndFindValue(possibleSibling, nextSnailfishNumberWhenGoingDown);
    }

    private SnailfishNumber moveThroughTreeAndFindValue(SnailfishNumber snailfishNumber, Function<SnailfishNumber, SnailfishNumber> nextSnailfishNumber) {
       if (snailfishNumber.value != -1) {
           return snailfishNumber;
       }
       return moveThroughTreeAndFindValue(nextSnailfishNumber.apply(snailfishNumber), nextSnailfishNumber);
   }

    public static class SnailfishNumber {

        SnailfishNumber left;
        SnailfishNumber right;
        SnailfishNumber parent;
        long value = -1;

        public SnailfishNumber(long value) {
            this.value = value;
        }

        public SnailfishNumber(SnailfishNumber left, SnailfishNumber right) {
            this.left = left;
            this.right = right;
            left.parent = this;
            right.parent = this;
        }

        @Override
        public String toString() {
            if (value != -1) {
                return String.valueOf(value);
            }
            return "[" + left + "," + right + "]";
        }

    }
}
