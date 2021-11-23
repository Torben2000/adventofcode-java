package de.beachboys.aoc2018;

import de.beachboys.Day;
import org.javatuples.Triplet;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day23 extends Day {

    public Object part1(List<String> input) {
        List<Bot> bots = parseBotList(input);
        Bot botWithLongestRange = bots.stream().max(Comparator.comparing(bot -> bot.range)).orElseThrow();
        long botsInRange = 0L;
        for (Bot bot : bots) {
                long distance = Math.abs(botWithLongestRange.position.getValue0() - bot.position.getValue0())
                        + Math.abs(botWithLongestRange.position.getValue1() - bot.position.getValue1())
                        + Math.abs(botWithLongestRange.position.getValue2() - bot.position.getValue2());
                if (distance <= botWithLongestRange.range) {
                    botsInRange++;
                }
        }
        return botsInRange;
    }

    private List<Bot> parseBotList(List<String> input) {
        List<Bot> bots = new ArrayList<>();
        Pattern pattern = Pattern.compile("pos=<([-0-9]+),([-0-9]+),([-0-9]+)>, r=([0-9]+)");
        for (String line : input) {
            Matcher m = pattern.matcher(line);
            if (m.matches()) {
                Triplet<Long, Long, Long> position = Triplet.with(Long.parseLong(m.group(1)), Long.parseLong(m.group(2)), Long.parseLong(m.group(3)));
                bots.add(new Bot(position, Long.parseLong(m.group(4))));
            }
        }
        return bots;
    }

    public Object part2(List<String> input) {
        return 2;
    }

    private static class Bot {

        Triplet<Long, Long, Long> position;
        long range;

        public Bot(Triplet<Long, Long, Long> position, Long range) {
            this.position = position;
            this.range = range;
        }

    }
}
