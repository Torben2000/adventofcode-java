package de.beachboys.ec2025;

import de.beachboys.Quest;
import de.beachboys.Util;

import javax.annotation.Nullable;
import java.util.*;
import java.util.regex.Pattern;

public class Quest05 extends Quest {

    @Override
    public Object part1(List<String> input) {
        return parseSwords(input).getFirst().quality;
    }

    @Override
    public Object part2(List<String> input) {
        List<Sword> swords = parseSwords(input);
        List<Long> qualities = swords.stream().map(Sword::quality).toList();
        return qualities.stream().max(Long::compareTo).orElseThrow() - qualities.stream().min(Long::compareTo).orElseThrow();
    }

    @Override
    public Object part3(List<String> input) {
        List<Sword> swords = parseSwords(input);
        swords.sort(Sword::compareTo);

        long result = 0;
        for (int i = 1; i <= swords.size(); i++) {
            result += i * swords.get(swords.size() - i).id;
        }
        return result;
    }

    private static List<Sword> parseSwords(List<String> input) {
        List<Sword> swords = new ArrayList<>();

        for (String line : input) {
            String[] split = line.split(Pattern.quote(":"));
            long id = Long.parseLong(split[0]);
            List<Integer> numbers = Util.parseIntCsv(split[1]);

            List<Integer> spine = new ArrayList<>();
            Map<Integer, Integer> leftFromSpine = new HashMap<>();
            Map<Integer, Integer> rightFromSpine = new HashMap<>();
            for (Integer number : numbers) {
                boolean found = false;
                for (int i = 0; i < spine.size(); i++) {
                    int numberOnSpine = spine.get(i);
                    if (number < numberOnSpine && !leftFromSpine.containsKey(i)) {
                        leftFromSpine.put(i, number);
                        found = true;
                        break;
                    } else if (number > numberOnSpine && !rightFromSpine.containsKey(i)) {
                        rightFromSpine.put(i, number);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    spine.add(number);
                }
            }

            StringBuilder qualityAsString = new StringBuilder();
            List<Long> lines = new ArrayList<>();
            for (int j = 0; j < spine.size(); j++) {
                Integer spineNumber = spine.get(j);
                qualityAsString.append(spineNumber);

                String levelNumberString = "";
                if (leftFromSpine.containsKey(j)) {
                    levelNumberString += leftFromSpine.get(j);
                }
                levelNumberString += spineNumber;
                if (rightFromSpine.containsKey(j)) {
                    levelNumberString += rightFromSpine.get(j);
                }
                lines.add(Long.parseLong(levelNumberString));
            }
            swords.add(new Sword(id, Long.parseLong(qualityAsString.toString()), lines));
        }
        return swords;
    }

    private record Sword(long id, long quality, List<Long> levelNumbers) implements Comparable<Sword> {

        @Override
        public int compareTo(@Nullable Sword other) {
            if (other == null) {
                return 1;
            }
            if (this.quality != other.quality) {
                return Long.compare(this.quality, other.quality);
            }
            for (int i = 0; i < Math.max(this.levelNumbers.size(), other.levelNumbers.size()); i++) {
                long levelNumber1 = this.levelNumbers.size() > i ? this.levelNumbers.get(i) : 0;
                long levelNumber2 = other.levelNumbers.size() > i ? other.levelNumbers.get(i) : 0;
                if (levelNumber1 != levelNumber2) {
                    return Long.compare(levelNumber1, levelNumber2);
                }

            }
            return Long.compare(this.id, other.id);
        }

    }
}
