package de.beachboys.aoc2020;

import de.beachboys.Day;
import de.beachboys.Util;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;

public class Day13 extends Day {


    public Object part1(List<String> input) {
        int timestamp = Integer.parseInt(input.get(0));
        List<String> bussesAsString = Util.parseCsv(input.get(1));
        List<Long> busses = new ArrayList<>();
        for (String bus : bussesAsString) {
            try {
                busses.add(Long.valueOf(bus));
            } catch(NumberFormatException e) {
                // ignore non-numbers
            }
        }

        long busToCatch = -1;
        long minimalWaitingTime = Long.MAX_VALUE - timestamp;
        for (long bus : busses) {
            long numberOfBussesBeforeOrAtTimestamp = timestamp / bus;
            if (numberOfBussesBeforeOrAtTimestamp * bus == timestamp) {
                // won't happen, but you never know...
                return 0;
            }
            long timeStampOfNextBus = (numberOfBussesBeforeOrAtTimestamp + 1) * bus;
            long waitingTime = timeStampOfNextBus - timestamp;
            if (waitingTime < minimalWaitingTime) {
                minimalWaitingTime = waitingTime;
                busToCatch = bus;
            }
        }
        return minimalWaitingTime * busToCatch;
    }

    public Object part2(List<String> input) {
        List<String> bussesAsString = Util.parseCsv(input.get(1));
        List<Pair<Long, Long>> busses = new ArrayList<>();
        long counter = 0L;
        for (String bus : bussesAsString) {
            try {
                long busId = Long.parseLong(bus);
                busses.add(Pair.with(busId, busId - counter));
            } catch (NumberFormatException e) {
                // ignore non-numbers
            }
            counter++;
        }

        return Util.chineseRemainderTheorem(busses);
    }
 }
