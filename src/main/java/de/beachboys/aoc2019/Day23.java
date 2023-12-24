package de.beachboys.aoc2019;

import de.beachboys.Day;
import de.beachboys.IOHelper;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.function.Consumer;

public class Day23 extends Day {

    private enum Mode {
        INIT, ADDRESS, X, Y
    }

    private final Map<Integer, Deque<Tuple2<Long, Long>>> traffic = Collections.synchronizedMap(new HashMap<>());

    private final Map<Integer, IntcodeComputer> nics = Collections.synchronizedMap(new HashMap<>());

    private final Map<Integer, Mode> inputModes = Collections.synchronizedMap(new HashMap<>());

    private final Map<Integer, Mode> outputModes = Collections.synchronizedMap(new HashMap<>());

    private final Map<Integer, Tuple2<Long, Long>> tempOutputs = Collections.synchronizedMap(new HashMap<>());

    private final Set<Thread> threads = new HashSet<>();

    private final Set<Integer> idleNics = Collections.synchronizedSet(new HashSet<>());

    private long returnValue = 0L;

    public Object part1(List<String> input) {
        initAndStartNics(input);
        monitorNat(this::handleNatTrafficPart1);
        return returnValue;
    }

    public Object part2(List<String> input) {
        initAndStartNics(input);
        monitorNat(this::handleNatTrafficPart2);
        return returnValue;
    }

    private void initAndStartNics(List<String> input) {
        List<Long> list = Util.parseLongCsv(input.getFirst());
        for (int i = 0; i < 50; i++) {
            IntcodeComputer nic = new IntcodeComputer();
            nics.put(i, nic);
            inputModes.put(i, Mode.INIT);
            outputModes.put(i, Mode.ADDRESS);
            traffic.put(i, new LinkedBlockingDeque<>());
            final int finalI = i;
            Thread thread = new Thread(() -> runNic(nic, list, finalI));
            threads.add(thread);
        }
        traffic.put(255, new LinkedBlockingDeque<>());
        threads.forEach(Thread::start);
    }

    private void monitorNat(Consumer<Deque<Tuple2<Long, Long>>> natTrafficHandler) {
        while (isAnyThreadActive()) {
            Deque<Tuple2<Long, Long>> natTraffic = traffic.get(255);
            if (!natTraffic.isEmpty()) {
                natTrafficHandler.accept(natTraffic);
            }
            try {
                //noinspection BusyWait
                Thread.sleep(100);
            } catch (InterruptedException e) {
                io.logDebug(e.toString());
            }
        }
    }

    private void handleNatTrafficPart1(Deque<Tuple2<Long, Long>> natTraffic) {
        for (int i = 0; i < 50; i++) {
            nics.get(i).setKillSwitch(true);
        }
        returnValue = natTraffic.stream().findFirst().orElseThrow().v2;
    }

    private void handleNatTrafficPart2(Deque<Tuple2<Long, Long>> natTraffic) {
        if (idleNics.size() == 50) {
            Tuple2<Long, Long> valueToSend = natTraffic.getLast();
            natTraffic.clear();
            traffic.get(0).add(valueToSend);
            if (valueToSend.v2 == returnValue) {
                for (int i = 0; i < 50; i++) {
                    nics.get(i).setKillSwitch(true);
                }
            }
            returnValue = valueToSend.v2;
        }
    }

    private boolean isAnyThreadActive() {
        return threads.stream().anyMatch(Thread::isAlive);
    }

    private void runNic(IntcodeComputer nic, List<Long> list, int address) {
        IOHelper io = new IOHelper() {

            @Override
            public String getInput(String textToDisplay) {
                long returnValue;
                switch (inputModes.get(address)) {
                    case INIT:
                        returnValue = address;
                        inputModes.put(address, Mode.X);
                        break;
                    case X:
                        Deque<Tuple2<Long, Long>> nicTraffic = traffic.get(address);
                        if (!nicTraffic.isEmpty()) {
                            returnValue = nicTraffic.peek().v1;
                            inputModes.put(address, Mode.Y);
                            idleNics.remove(address);
                        } else {
                            returnValue = -1;
                            idleNics.add(address);
                        }
                        break;
                    case Y:
                        //noinspection ConstantConditions
                        returnValue = traffic.get(address).poll().v2;
                        inputModes.put(address, Mode.X);
                        break;
                    default:
                        throw new IllegalArgumentException();
                }
                return returnValue + "";
            }

            @Override
            public void logInfo(Object infoText) {
                long output = Long.parseLong(infoText.toString());
                switch (outputModes.get(address)) {
                    case ADDRESS:
                        tempOutputs.put(address, Tuple.tuple(output, null));
                        outputModes.put(address, Mode.X);
                        break;
                    case X:
                        tempOutputs.put(address, Tuple.tuple(tempOutputs.get(address).v1, output));
                        outputModes.put(address, Mode.Y);
                        break;
                    case Y:
                        Tuple2<Long, Long> tempOutput = tempOutputs.get(address);
                        traffic.get(tempOutput.v1.intValue()).add(Tuple.tuple(tempOutput.v2, output));
                        outputModes.put(address, Mode.ADDRESS);
                        break;
                }
            }

            @Override
            public void logDebug(Object debugText) {
                // do nothing
            }
        };

        nic.runLogic(new ArrayList<>(list), io);
    }
}
