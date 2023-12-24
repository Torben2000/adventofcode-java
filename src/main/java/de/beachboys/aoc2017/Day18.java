package de.beachboys.aoc2017;

import de.beachboys.Day;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.function.Consumer;
import java.util.function.LongConsumer;

public class Day18 extends Day {

    private long lastPlayedSound = -1;

    public Object part1(List<String> input) {
        Assembly asm = new Assembly();
        asm.setSndHandler(snd -> lastPlayedSound = snd);
        asm.setRcvHandler(parameter -> {
            if (asm.getValue(parameter) != 0) {
                asm.killSwitch = true;
            }
        });

        asm.runProgram(input);

        if (asm.killSwitch) {
            return lastPlayedSound;
        }
        return -1;
    }

    public Object part2(List<String> input) {
        AssemblyPart2 asm0 = new AssemblyPart2();
        AssemblyPart2 asm1 = new AssemblyPart2();
        asm0.setOtherAssembly(asm1);
        asm1.setOtherAssembly(asm0);

        asm1.setValue("p", 1L);

        Thread t0 = new Thread(() -> asm0.runProgram(input));
        Thread t1 = new Thread(() -> asm1.runProgram(input));
        t0.start();
        t1.start();
        try {
            t0.join();
            t1.join();
        } catch (InterruptedException e) {
            return "Threads interrupted";
        }

        return asm1.sendCounter;
    }

    private static class AssemblyPart2 extends Assembly {

        private AssemblyPart2 otherAssembly;
        private boolean waiting = false;
        private final Deque<Long> queue = new ConcurrentLinkedDeque<>();
        private long sendCounter = 0;

        public AssemblyPart2() {
            setSndHandler(snd -> {
                otherAssembly.queue.add(snd);
                sendCounter++;
            });
            setRcvHandler(parameter -> {
                while (queue.isEmpty()) {
                    waiting = true;
                    if (otherAssembly.waiting && otherAssembly.queue.isEmpty()) {
                        killSwitch = true;
                        return;
                    }
                    try {
                        //noinspection BusyWait
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        // yeah, it ended early, just continue;
                    }
                }
                waiting = false;
                setValue(parameter, queue.poll());
            });
        }

        public void setOtherAssembly(AssemblyPart2 otherAssembly) {
            this.otherAssembly = otherAssembly;
        }

    }

    private static class Assembly {
        private int instructionPointer = 0;
        protected boolean killSwitch = false;
        private final Map<String, Long> registers = new HashMap<>();
        private LongConsumer sndHandler;
        private Consumer<String> rcvHandler;

        public void setSndHandler(LongConsumer sndHandler) {
            this.sndHandler = sndHandler;
        }

        public void setRcvHandler(Consumer<String> rcvHandler) {
            this.rcvHandler = rcvHandler;
        }

        public void runProgram(List<String> program) {
            while (!killSwitch && instructionPointer < program.size()) {
                instructionPointer += (int) executeInstruction(program.get(instructionPointer));
            }
        }

        private long executeInstruction(String instruction) {
            long instructionPointerManipulation = 1;
            String[] instructionParts = instruction.split(" ");
            switch (instructionParts[0]) {
                case "snd":
                    sndHandler.accept(getValue(instructionParts[1]));
                    break;
                case "set":
                    setValue(instructionParts[1], getValue(instructionParts[2]));
                    break;
                case "add":
                    setValue(instructionParts[1], getValue(instructionParts[1]) + getValue(instructionParts[2]));
                    break;
                case "mul":
                    setValue(instructionParts[1], getValue(instructionParts[1]) * getValue(instructionParts[2]));
                    break;
                case "mod":
                    setValue(instructionParts[1], getValue(instructionParts[1]) % getValue(instructionParts[2]));
                    break;
                case "jgz":
                    if (getValue(instructionParts[1]) > 0) {
                        instructionPointerManipulation = getValue(instructionParts[2]);
                    }
                    break;
                case "rcv":
                    rcvHandler.accept(instructionParts[1]);
                    break;
                default:
                    throw new IllegalArgumentException();
            }
            return instructionPointerManipulation;
        }

        public void setValue(String register, long value) {
            registers.put(register, value);
        }

        public long getValue(String valueReference) {
            long value;
            try {
                value = Long.parseLong(valueReference);
            } catch (NumberFormatException e) {
                value = registers.getOrDefault(valueReference, 0L);
            }
            return value;
        }

    }

}
