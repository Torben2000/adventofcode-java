package de.beachboys.aoc2019;

import de.beachboys.Day;
import de.beachboys.IOHelper;
import de.beachboys.Util;
import org.javatuples.Pair;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Day23 extends Day {

    private enum Mode {
        INIT, ADDRESS, X, Y
    }

    private final Map<Integer, Deque<Pair<Long, Long>>> traffic = new ConcurrentHashMap<>();

    @SuppressWarnings("SpellCheckingInspection")
    private final Map<Integer, IntcodeComputer> nics = new ConcurrentHashMap<>();

    private final Map<Integer, Mode> inputModes = new ConcurrentHashMap<>();

    private final Map<Integer, Mode> outputModes = new ConcurrentHashMap<>();

    private final Map<Integer, Pair<Long, Long>> tempOutputs = new ConcurrentHashMap<>();

    private final Set<Thread> threads = new HashSet<>();

    public Object part1(List<String> input) {
        List<Long> list = Util.parseLongCsv(input.get(0));
        for (int i = 0; i < 50; i++) {
            IntcodeComputer nic = new IntcodeComputer();
            nics.put(i, nic);
            inputModes.put(i, Mode.INIT);
            outputModes.put(i, Mode.ADDRESS);
            traffic.put(i, new ConcurrentLinkedDeque<>());
            final int finalI = i;
            Thread thread = new Thread(() -> runNic(nic, list, finalI));
            threads.add(thread);
            thread.start();
        }
        traffic.put(255, new ConcurrentLinkedDeque<>());
        while (isAnyThreadActive()) {
            try {
                //noinspection BusyWait
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                io.logDebug(e.toString());
            }
        }
        return traffic.get(255).stream().findFirst().orElseThrow().getValue1();
    }

    private boolean isAnyThreadActive() {
        return threads.stream().anyMatch(Thread::isAlive);
    }

    public Object part2(List<String> input) {
        return 2;
    }

    private void runNic(IntcodeComputer nic, List<Long> list, int address) {
        IOHelper io = new IOHelper() {

            @Override
            public String getInput(String textToDisplay) {
                long returnValue = -1;
                switch (inputModes.get(address)) {
                    case INIT:
                        returnValue = address;
                        inputModes.put(address, Mode.X);
                        break;
                    case X:
                        Deque<Pair<Long, Long>> nicTraffic = traffic.get(address);
                        if (!nicTraffic.isEmpty()) {
                            returnValue = nicTraffic.peek().getValue0();
                            inputModes.put(address, Mode.Y);
                        }
                        break;
                    case Y:
                        //noinspection ConstantConditions
                        returnValue = traffic.get(address).poll().getValue1();
                        inputModes.put(address, Mode.X);
                        break;
                }
                return returnValue + "";
            }

            @Override
            public void logInfo(Object infoText) {
                long output = Long.parseLong(infoText.toString());
                switch (outputModes.get(address)) {
                    case ADDRESS:
                        tempOutputs.put(address, Pair.with(output, null));
                        outputModes.put(address, Mode.X);
                        break;
                    case X:
                        tempOutputs.put(address, Pair.with(tempOutputs.get(address).getValue0(), output));
                        outputModes.put(address, Mode.Y);
                        break;
                    case Y:
                        Pair<Long, Long> tempOutput = tempOutputs.get(address);
                        if (255 == tempOutput.getValue0()) {
                            for (int i = 0; i < 50; i++) {
                                nics.get(i).setKillSwitch(true);
                            }
                        }
                        traffic.get(tempOutput.getValue0().intValue()).add(Pair.with(tempOutput.getValue1(), output));
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
