package de.beachboys.ec2024;

import de.beachboys.Direction;
import de.beachboys.Quest;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.*;
import java.util.regex.Pattern;

public class Quest07 extends Quest {

    public static final List<Character> TRACK_PART1 = List.of('S');
    public static final String TRACK_PART2 = """
            S-=++=-==++=++=-=+=-=+=+=--=-=++=-==++=-+=-=+=-=+=+=++=-+==++=++=-=-=--
            -                                                                     -
            =                                                                     =
            +                                                                     +
            =                                                                     +
            +                                                                     =
            =                                                                     =
            -                                                                     -
            --==++++==+=+++-=+=-=+=-+-=+-=+-=+=-=+=--=+++=++=+++==++==--=+=++==+++-""";

    public static final String TRACK_PART3 = """
            S+= +=-== +=++=     =+=+=--=    =-= ++=     +=-  =+=++=-+==+ =++=-=-=--
            - + +   + =   =     =      =   == = - -     - =  =         =-=        -
            = + + +-- =-= ==-==-= --++ +  == == = +     - =  =    ==++=    =++=-=++
            + + + =     +         =  + + == == ++ =     = =  ==   =   = =++=
            = = + + +== +==     =++ == =+=  =  +  +==-=++ =   =++ --= + =
            + ==- = + =   = =+= =   =       ++--          +     =   = = =--= ==++==
            =     ==- ==+-- = = = ++= +=--      ==+ ==--= +--+=-= ==- ==   =+=    =
            -               = = = =   +  +  ==+ = = +   =        ++    =          -
            -               = + + =   +  -  = + = = +   =        +     =          -
            --==++++==+=+++-= =-= =-+-=  =+-= =-= =--   +=++=+++==     -=+=++==+++-""";

    @Override
    public Object part1(List<String> input) {
        Map<String, Long> scores = new HashMap<>();
        for (String line : input) {
            Tuple2<String, List<Character>> plan = parsePlan(line);
            scores.put(plan.v1, getScore(TRACK_PART1, 10, plan.v2));
        }
        return scores.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).map(Map.Entry::getKey).reduce(String::concat).orElseThrow();
    }

    @Override
    public Object part2(List<String> input) {
        List<Character> track = parseTrack(Util.getStringValueFromUser("Track", TRACK_PART2, io));
        Map<String, Long> scores = new HashMap<>();
        for (String line : input) {
            Tuple2<String, List<Character>> plan = parsePlan(line);
            scores.put(plan.v1, getScore(track, 10, plan.v2));
        }
        return scores.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).map(Map.Entry::getKey).reduce(String::concat).orElseThrow();
    }


    @Override
    public Object part3(List<String> input) {
        List<Character> track = parseTrack(Util.getStringValueFromUser("Track", TRACK_PART3, io));

        long rivalScore = getScore(track, 2024, parsePlan(input.getFirst()).v2);

        List<List<Character>> possiblePlans = buildPossiblePlans(5, 3, 3);
        long returnValue = 0;
        for (List<Character> plan : possiblePlans) {
            long score = getScore(track, 2024, plan);
            if (score > rivalScore) {
                returnValue++;
            }
        }
        return returnValue;
    }

    private static List<Character> parseTrack(String trackAsString) {
        Map<Tuple2<Integer, Integer>, String> map = Util.buildImageMap(trackAsString);
        List<Character> track = new LinkedList<>();
        Tuple2<Integer, Integer> curPos = Tuple.tuple(0, 0);
        Direction curDir = Direction.NORTH;
        String curValue = null;
        while (!"S".equals(curValue)) {
            for (Direction newDir : Direction.values()) {
                if (newDir.getOpposite() != curDir) {
                    String newVal = map.getOrDefault(newDir.move(curPos, 1), " ");
                    if (!" ".equals(newVal)) {
                        track.add(newVal.charAt(0));
                        curValue = newVal;
                        curPos = newDir.move(curPos, 1);
                        curDir = newDir;
                        break;
                    }
                }
            }
        }
        return track;
    }

    private static Tuple2<String, List<Character>> parsePlan(String line) {
        String[] nameAndActions = line.split(":");
        List<Character> actions = nameAndActions[1].replaceAll(Pattern.quote(","), "").chars().mapToObj(c -> (char) c).toList();
        return Tuple.tuple(nameAndActions[0], actions);
    }

    private static long getScore(List<Character> track, int totalRounds, List<Character> plan) {
        long score = 0;
        long powerLevel = 10;
        int planIndex = 0;
        for (int i = 0; i < totalRounds; i++) {
            for (Character trackTerrain : track) {
                switch (trackTerrain) {
                    case 'S':
                    case '=':
                        Character action = plan.get(planIndex);
                        switch (action) {
                            case '=':
                                break;
                            case '+':
                                powerLevel++;
                                break;
                            case '-':
                                powerLevel = Math.max(0, powerLevel - 1);
                                break;
                        }
                        break;
                    case '+':
                        powerLevel++;
                        break;
                    case '-':
                        powerLevel = Math.max(0, powerLevel - 1);
                        break;
                }
                score += powerLevel;
                planIndex = (planIndex + 1) % plan.size();
            }
        }
        return score;
    }

    private List<List<Character>> buildPossiblePlans(int plusToUse, int minusToUse, int equalToUse) {
        List<List<Character>> returnValue = new ArrayList<>();
        if (plusToUse > 0) {
            List<List<Character>> possiblePlansAfterPlus = buildPossiblePlans(plusToUse - 1, minusToUse, equalToUse);
            for (List<Character> possiblePlanAfterPlus : possiblePlansAfterPlus) {
                List<Character> plan = new ArrayList<>();
                plan.add('+');
                plan.addAll(possiblePlanAfterPlus);
                returnValue.add(plan);
            }
        }
        if (minusToUse > 0) {
            List<List<Character>> possiblePlansAfterMinus = buildPossiblePlans(plusToUse, minusToUse - 1, equalToUse);
            for (List<Character> possiblePlanAfterPlus : possiblePlansAfterMinus) {
                List<Character> plan = new ArrayList<>();
                plan.add('-');
                plan.addAll(possiblePlanAfterPlus);
                returnValue.add(plan);
            }
        }
        if (equalToUse > 0) {
            List<List<Character>> possiblePlansAfterEqual = buildPossiblePlans(plusToUse, minusToUse, equalToUse - 1);
            for (List<Character> possiblePlanAfterEqual : possiblePlansAfterEqual) {
                List<Character> plan = new ArrayList<>();
                plan.add('=');
                plan.addAll(possiblePlanAfterEqual);
                returnValue.add(plan);
            }
        }
        if (returnValue.isEmpty()) {
            returnValue.add(List.of());
        }
        return returnValue;
    }
}
