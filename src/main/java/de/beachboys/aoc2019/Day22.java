package de.beachboys.aoc2019;

import de.beachboys.Day;
import de.beachboys.Util;
import org.javatuples.Pair;

import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;

public class Day22 extends Day {

    enum OpType {
        REVERSE, CUT, DEAL
    }

    Map<Long, List<Pair<OpType, Long>>> opsPerNumOfRuns = new HashMap<>();

    public Object part1(List<String> input) {
        long deckSize = Util.getLongValueFromUser("Deck size",10007, io);
        long positionOfCard = Util.getLongValueFromUser("Position of card",2019, io);
        return getPositionOfCard(input, deckSize, positionOfCard, 1L);
    }

    public Object part2(List<String> input) {
        long deckSize = Util.getLongValueFromUser("Deck size",119315717514047L, io);
        long numberOfRuns = Util.getLongValueFromUser("Number of runs",101741582076661L, io);
        long positionOfCard = Util.getLongValueFromUser("Position of card",2020, io);

        // Math magic... https://www.reddit.com/r/adventofcode/comments/ee56wh/2019_day_22_part_2_so_whats_the_purpose_of_this/fbs6s6z/
        long numOfRunsReverse = deckSize - 1 - numberOfRuns;

        return getPositionOfCard(input, deckSize, positionOfCard, numOfRunsReverse);
    }

    private long getPositionOfCard(List<String> input, long deckSize, long positionOfCard, long numOfRuns) {
        List<Pair<OpType, Long>> unoptimizedOpsForOneRun = getOpList(input, deckSize);
        long i = 1L;
        opsPerNumOfRuns.put(i, shortenListOfOps(unoptimizedOpsForOneRun, deckSize));
        while (numOfRuns >= 2 * i) {
            List<Pair<OpType, Long>> ops = new ArrayList<>();
            ops.addAll(opsPerNumOfRuns.get(i));
            ops.addAll(opsPerNumOfRuns.get(i));
            i *= 2;
            opsPerNumOfRuns.put(i, shortenListOfOps(ops, deckSize));
        }

        List<Pair<OpType, Long>> allOps = new ArrayList<>();
        for (Long numOfRunsBit : opsPerNumOfRuns.keySet()) {
            if ((numOfRuns & numOfRunsBit) > 0) {
                allOps.addAll(opsPerNumOfRuns.get(numOfRunsBit));
            }
        }

        List<Function<Long, Long>> functions = getFunctions(allOps, deckSize);
        for (Function<Long, Long> function : functions) {
            positionOfCard = function.apply(positionOfCard);
        }
        return positionOfCard;
    }

    private List<Pair<OpType, Long>> shortenListOfOps(List<Pair<OpType, Long>> ops, long deckSize) {
        return combineOps(sortDealBeforeCut(ops, deckSize), deckSize);
    }

    private List<Pair<OpType, Long>> sortDealBeforeCut(List<Pair<OpType, Long>> ops, long deckSize) {
        List<Pair<OpType, Long>> sortedOps = new ArrayList<>(ops);
        boolean changeHappened = true;
        while (changeHappened) {
            changeHappened = false;
            for (int i = 0; i < sortedOps.size() - 1; i++) {
                Pair<OpType, Long> thisOp = sortedOps.get(i);
                Pair<OpType, Long> nextOp = sortedOps.get(i + 1);
                if (thisOp.getValue0() == OpType.CUT && nextOp.getValue0() == OpType.DEAL) {
                    sortedOps.set(i, nextOp);
                    sortedOps.set(i + 1, Pair.with(OpType.CUT, multiplyAndMod(thisOp.getValue1(), nextOp.getValue1(), deckSize)));
                    changeHappened = true;
                }
            }
        }
        return sortedOps;
    }

    private List<Pair<OpType, Long>> combineOps(List<Pair<OpType, Long>> ops, long deckSize) {
        List<Pair<OpType, Long>> combinedOps = new ArrayList<>();
        ops.stream().filter(op -> OpType.DEAL.equals(op.getValue0())).reduce((op1, op2) -> Pair.with(OpType.DEAL, multiplyAndMod(op1.getValue1(), op2.getValue1(), deckSize))).ifPresent(combinedOps::add);
        ops.stream().filter(op -> OpType.CUT.equals(op.getValue0())).reduce((op1, op2) -> Pair.with(OpType.CUT, Math.floorMod(Math.addExact(op1.getValue1(), op2.getValue1()), deckSize))).ifPresent(combinedOps::add);
        return combinedOps;
    }

    private long multiplyAndMod(long factor1, long factor2, long modulo) {
        return BigInteger.valueOf(factor1).multiply(BigInteger.valueOf(factor2)).mod(BigInteger.valueOf(modulo)).longValueExact();
    }

    private List<Pair<OpType, Long>> removeReverseOp(List<Pair<OpType, Long>> ops, long deckSize) {
        List<Pair<OpType, Long>> opsWithoutReverse = new ArrayList<>();
        ops.forEach(preOp -> {
            if (preOp.getValue0() == OpType.REVERSE) {
                opsWithoutReverse.add(Pair.with(OpType.DEAL, deckSize - 1));
                opsWithoutReverse.add(Pair.with(OpType.CUT, 1L));
            } else {
                opsWithoutReverse.add(preOp);
            }
        });
        return opsWithoutReverse;
    }

    private List<Pair<OpType, Long>> getOpList(List<String> input, long deckSize) {
        List<Pair<OpType, Long>> ops = new ArrayList<>();
        for (String line : input) {
            if (line.startsWith("deal into")) {
                ops.add(Pair.with(OpType.REVERSE, 0L));
            } else if (line.startsWith("cut")) {
                final long cut = Long.parseLong(line.substring(4));
                ops.add(Pair.with(OpType.CUT, cut));
            } else {
                final long increment = Long.parseLong(line.substring("deal with increment ".length()));
                ops.add(Pair.with(OpType.DEAL, increment));
            }
        }
        return removeReverseOp(ops, deckSize);
    }

    private List<Function<Long, Long>> getFunctions(List<Pair<OpType, Long>> ops, long deckSize) {
        List<Function<Long, Long>> functions = new ArrayList<>();
        final long maxIndex = deckSize - 1;
        for (Pair<OpType, Long> op : ops) {
            switch (op.getValue0()) {
                case REVERSE:
                    // unused, but you never know...
                    functions.add(pos -> maxIndex - pos);
                    break;
                case CUT:
                    functions.add(pos -> Math.floorMod(pos - op.getValue1(), deckSize));
                    break;
                case DEAL:
                    functions.add(pos -> multiplyAndMod(pos, op.getValue1(), deckSize));
                    break;
            }
        }
        return functions;
    }

}
