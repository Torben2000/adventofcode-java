package de.beachboys.aoc2018;

import de.beachboys.Day;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple3;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day23 extends Day {

    public Object part1(List<String> input) {
        List<Bot> bots = parseBotList(input);
        Bot botWithLongestRange = bots.stream().max(Comparator.comparing(bot -> bot.range)).orElseThrow();
        long botsInRange = 0L;
        for (Bot bot : bots) {
            boolean inRange = isInRangeOfBot(botWithLongestRange, bot.position.v1, bot.position.v2, bot.position.v3);
            if (inRange) {
                botsInRange++;
            }
        }
        return botsInRange;
    }

    public Object part2(List<String> input) {
        List<Bot> bots = parseBotList(input);
        List<Bot> botsWithIntersectingRanges = getBotsWithIntersectingRanges(bots);

        long minX = Long.MIN_VALUE;
        long maxX = Long.MAX_VALUE;
        long minY = Long.MIN_VALUE;
        long maxY = Long.MAX_VALUE;
        long minZ = Long.MIN_VALUE;
        long maxZ = Long.MAX_VALUE;
        for (Bot bot : botsWithIntersectingRanges) {
            minX = Math.max(minX, bot.position.v1 - bot.range);
            maxX = Math.min(maxX, bot.position.v1 + bot.range);
            minY = Math.max(minY, bot.position.v2 - bot.range);
            maxY = Math.min(maxY, bot.position.v2 + bot.range);
            minZ = Math.max(minZ, bot.position.v3 - bot.range);
            maxZ = Math.min(maxZ, bot.position.v3 + bot.range);
        }

        Optional<Long> minDistance = getMinimalDistanceToCenterFast(botsWithIntersectingRanges, minX, maxX, minY, maxY, minZ, maxZ);
        if (minDistance.isPresent()) {
            return minDistance.get();
        }

        return getMinimalDistanceToCenterSlow(botsWithIntersectingRanges, minX, maxX, minY, maxY, minZ, maxZ);
    }

    private Optional<Long> getMinimalDistanceToCenterFast(List<Bot> botsWithIntersectingRanges, long minX, long maxX, long minY, long maxY, long minZ, long maxZ) {
        if (minX >= 0 && minY >= 0 && minZ >= 0) {
            long minDistance = minX + minY + minZ;
            long maxDistance = maxX + maxY + maxZ;
            for (Bot bot : botsWithIntersectingRanges) {
                minDistance = Math.max(minDistance, bot.position.v1 + bot.position.v2 + bot.position.v3 - bot.range);
                maxDistance = Math.min(maxDistance, bot.position.v1 + bot.position.v2 + bot.position.v3 + bot.range);
            }
            if (minDistance == maxDistance) {
                return Optional.of(minDistance);
            }
        }
        return Optional.empty();
    }

    private long getMinimalDistanceToCenterSlow(List<Bot> botsWithIntersectingRanges, long minX, long maxX, long minY, long maxY, long minZ, long maxZ) {
        long minDistance = Long.MAX_VALUE;
        for (long i = minX; i <= maxX; i++) {
            for (long j = minY; j <= maxY; j++) {
                for (long k = minZ; k <= maxZ; k++) {
                    boolean workingPosition = true;
                    for (Bot bot : botsWithIntersectingRanges) {
                        if (!isInRangeOfBot(bot, i, j, k)) {
                            workingPosition = false;
                            break;
                        }
                    }
                    if (workingPosition) {
                        long distance = getDistanceToCenter(i, j, k);
                        if (minDistance > distance) {
                            minDistance = distance;
                        }
                    }
                }
            }
        }
        return minDistance;
    }

    private List<Bot> getBotsWithIntersectingRanges(List<Bot> bots) {
        Map<Bot, List<Bot>> botListWithConnectedBots = getBotListWithConnectedBots(bots);
        List<Map.Entry<Bot, List<Bot>>> sortedBotsWithConnectedBots = botListWithConnectedBots.entrySet().stream().sorted(Comparator.comparingInt(entry -> entry.getValue().size())).toList();

        List<Bot> botsToRemove = new ArrayList<>();
        List<Bot> botsWithIntersectingRanges = new ArrayList<>();
        for (int i = 0; i < sortedBotsWithConnectedBots.size(); i++) {
            Map.Entry<Bot, List<Bot>> botListEntry = sortedBotsWithConnectedBots.get(i);
            int minSize = sortedBotsWithConnectedBots.size() - 1 - botsToRemove.size();
            if (botListEntry.getValue().size() == minSize) {
                for (Bot otherBot : botsToRemove) {
                    if (botListEntry.getValue().contains(otherBot)) {
                        throw new IllegalArgumentException();
                    }
                }
                botsWithIntersectingRanges.add(botListEntry.getKey());
            } else if (botListEntry.getValue().size() > minSize) {
                int counter = 0;
                for (Bot otherBot : botsToRemove) {
                    if (botListEntry.getValue().contains(otherBot)) {
                        counter++;
                    }
                }
                if (counter != botListEntry.getValue().size() - minSize) {
                    throw new IllegalArgumentException();
                }
                botsWithIntersectingRanges.add(botListEntry.getKey());
            } else {
                botsToRemove.add(botListEntry.getKey());
            }
        }
        return botsWithIntersectingRanges;
    }

    private Map<Bot, List<Bot>> getBotListWithConnectedBots(List<Bot> bots) {
        Map<Bot, List<Bot>> botsThatCanRangeToSamePosition = new HashMap<>();
        for (Bot bot1 : bots) {
            botsThatCanRangeToSamePosition.put(bot1, new ArrayList<>());
            for (Bot bot2 : bots) {
                if (bot1 != bot2) {
                    if (getBotDistance(bot1, bot2) <= bot1.range + bot2.range) {
                        botsThatCanRangeToSamePosition.get(bot1).add(bot2);
                    }
                }
            }
        }
        return botsThatCanRangeToSamePosition;
    }

    private List<Bot> parseBotList(List<String> input) {
        List<Bot> bots = new ArrayList<>();
        Pattern pattern = Pattern.compile("pos=<([-0-9]+),([-0-9]+),([-0-9]+)>, r=([0-9]+)");
        for (String line : input) {
            Matcher m = pattern.matcher(line);
            if (m.matches()) {
                Tuple3<Long, Long, Long> position = Tuple.tuple(Long.parseLong(m.group(1)), Long.parseLong(m.group(2)), Long.parseLong(m.group(3)));
                bots.add(new Bot(position, Long.parseLong(m.group(4))));
            }
        }
        return bots;
    }

    private boolean isInRangeOfBot(Bot bot, long x, long y, long z) {
        boolean inRange = false;
        long distance = Math.abs(bot.position.v1 - x)
                    + Math.abs(bot.position.v2 - y)
                    + Math.abs(bot.position.v3 - z);
        if (distance <= bot.range) {
            inRange = true;
        }
        return inRange;
    }

    private long getBotDistance(Bot bot1, Bot bot2) {
        return Math.abs(bot1.position.v1 - bot2.position.v1)
                + Math.abs(bot1.position.v2 - bot2.position.v2)
                + Math.abs(bot1.position.v3 - bot2.position.v3);
    }

    private long getDistanceToCenter(long x, long y, long z) {
        return Math.abs(x) + Math.abs(y) + Math.abs(z);
    }

    private static class Bot {

        final Tuple3<Long, Long, Long> position;
        final long range;

        public Bot(Tuple3<Long, Long, Long> position, Long range) {
            this.position = position;
            this.range = range;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Bot bot = (Bot) o;
            return range == bot.range && position.equals(bot.position);
        }

        @Override
        public int hashCode() {
            return Objects.hash(position, range);
        }
    }
}
