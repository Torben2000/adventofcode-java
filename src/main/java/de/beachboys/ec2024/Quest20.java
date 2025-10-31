package de.beachboys.ec2024;

import de.beachboys.Direction;
import de.beachboys.Quest;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;
import org.jooq.lambda.tuple.Tuple3;

import java.util.*;

public class Quest20 extends Quest {

    private Map<Tuple2<Integer, Integer>, String> map;
    private int w;
    private int h;
    private Tuple2<Integer, Integer> start;

    @Override
    public Object part1(List<String> input) {
        parseInput(input);

        Set<Tuple3<Tuple2<Integer, Integer>, Direction, Integer>> currentPossibleStates = Set.of(Tuple.tuple(start, null, 1000));
        for (int curTime = 0; curTime <= 100; curTime++) {
            Set<Tuple3<Tuple2<Integer, Integer>, Direction, Integer>> newPossibleStates = new HashSet<>();
            for (Tuple3<Tuple2<Integer, Integer>, Direction, Integer> state : currentPossibleStates) {
                int altitude = state.v3;
                String segment = map.get(state.v1);
                if (segmentReachable(segment)) {
                    if (curTime != 0) {
                        altitude = updateAltitude(segment, altitude);
                    }
                    for (Direction newDir : Direction.values()) {
                        if (state.v2 != newDir.getOpposite()) {
                            newPossibleStates.add(Tuple.tuple(newDir.move(state.v1, 1), newDir, altitude));
                        }
                    }
                }
            }
            currentPossibleStates = newPossibleStates;
        }
        return currentPossibleStates.stream().map(p -> p.v3).mapToInt(Integer::intValue).max().orElseThrow();
    }

    @Override
    public Object part2(List<String> input) {
        parseInput(input);

        Map<Tuple3<Tuple2<Integer, Integer>, Direction, String>, Integer> currentPossibleStatesWithMaxAltitude = new HashMap<>();
        currentPossibleStatesWithMaxAltitude.put(Tuple.tuple(start, null, "S"), 10000);
        for (int curTime = 0; curTime < Integer.MAX_VALUE; curTime++) {
            Map<Tuple3<Tuple2<Integer, Integer>, Direction, String>, Integer> newPossibleStatesWithMaxAltitude = new HashMap<>();
            for (Tuple3<Tuple2<Integer, Integer>, Direction, String> stateKey : currentPossibleStatesWithMaxAltitude.keySet()) {
                String segment = map.get(stateKey.v1);
                if (segmentReachable(segment)) {
                    int altitude = currentPossibleStatesWithMaxAltitude.get(stateKey);
                    if (curTime != 0) {
                        altitude = updateAltitude(segment, altitude);
                    }

                    String lastCheckPoint = stateKey.v3;
                    if ("A".equals(segment) && "S".equals(lastCheckPoint)) {
                        lastCheckPoint = "A";
                    } else if ("B".equals(segment) && "A".equals(lastCheckPoint)) {
                        lastCheckPoint = "B";
                    } else if ("C".equals(segment) && "B".equals(lastCheckPoint)) {
                        lastCheckPoint = "C";
                    } else if ("S".equals(segment) && "C".equals(lastCheckPoint) && altitude >= 10000) {
                        return curTime;
                    }

                    for (Direction newDir : Direction.values()) {
                        if (stateKey.v2 != newDir.getOpposite()) {
                            Tuple3<Tuple2<Integer, Integer>, Direction, String> newStateKey = Tuple.tuple(newDir.move(stateKey.v1, 1), newDir, lastCheckPoint);
                            Integer maxAltitude = newPossibleStatesWithMaxAltitude.get(newStateKey);
                            if (maxAltitude == null || maxAltitude < altitude) {
                                newPossibleStatesWithMaxAltitude.put(newStateKey, altitude);
                            }
                        }
                    }
                }
            }
            currentPossibleStatesWithMaxAltitude = newPossibleStatesWithMaxAltitude;

        }
        throw new IllegalArgumentException();
    }

    @Override
    public Object part3(List<String> input) {
        parseInput(input);
        int startingAltitude = Util.getIntValueFromUser("Starting altitude", 384400, io);
        List<Integer> bestCols = getBestColumnsPart3();

        int maxYDistance = 0;

        Map<Tuple2<Tuple2<Integer, Integer>, Direction>, Integer> history = new HashMap<>();
        history.put(Tuple.tuple(start, Direction.SOUTH), startingAltitude);
        boolean bestColFound = false;
        Deque<Tuple2<Tuple2<Integer, Integer>, Direction>> queue = new LinkedList<>();
        queue.add(Tuple.tuple(start, Direction.SOUTH));
        while (!queue.isEmpty()) {
            Tuple2<Tuple2<Integer, Integer>, Direction> queueEntry = queue.poll();
            String segment = map.get(Util.getNormalizedPositionOnRepeatingPattern(queueEntry.v1, w, h));
            if (segmentReachable(segment)) {
                int altitude = history.get(queueEntry);
                if (!start.equals(queueEntry.v1)) {
                    altitude = updateAltitude(segment, altitude);
                }
                if (altitude <= 0) {
                    maxYDistance = Math.max(maxYDistance, queueEntry.v1.v2);
                    continue;
                }

                List<Direction> newDirs = new ArrayList<>();
                if (bestCols.contains(queueEntry.v1.v1)) {
                    if (queueEntry.v1.v2 > w + h){
                        bestColFound = true;
                    }
                    newDirs.add(Direction.SOUTH);
                } else if (!bestColFound || queueEntry.v1.v2 <= w + h){
                    newDirs.add(Direction.SOUTH);
                    if (queueEntry.v2 != Direction.WEST) {
                        newDirs.add(Direction.EAST);
                    }
                    if (queueEntry.v2 != Direction.EAST) {
                        newDirs.add(Direction.WEST);
                    }
                }

                for (Direction newDir : newDirs) {
                    Tuple2<Tuple2<Integer, Integer>, Direction> key = Tuple.tuple(newDir.move(queueEntry.v1, 1), newDir);
                    Integer maxAlt = history.get(key);
                    if (maxAlt == null || maxAlt < altitude) {
                        history.put(key, altitude);
                        queue.add(key);
                    }
                }
            }
        }
        return maxYDistance;
    }

    private List<Integer> getBestColumnsPart3() {
        List<Integer> numPlusInColumn = new ArrayList<>();
        for (int i = 0; i < w; i++) {
            int finalI = i;
            List<String> segmentsOfColumn = map.keySet().stream().filter(k-> k.v1 == finalI).map(k -> map.get(k)).toList();
            if (!segmentsOfColumn.contains("-") && !segmentsOfColumn.contains("#")) {
                numPlusInColumn.add((int) segmentsOfColumn.stream().filter("+"::equals).count());
            } else {
                numPlusInColumn.add(0);
            }
        }
        int maxPlus = numPlusInColumn.stream().max(Integer::compareTo).orElseThrow();
        List<Integer> bestColumns = new ArrayList<>();
        for (int i = 0; i < numPlusInColumn.size(); i++) {
            if (numPlusInColumn.get(i) == maxPlus) {
                bestColumns.add(i);
            }
        }
        return bestColumns;
    }

    private static int updateAltitude(String segment, int altitude) {
        switch (segment) {
            case "+":
                altitude += 1;
                break;
            case "-":
                altitude -= 2;
                break;
            case ".":
            case "A":
            case "B":
            case "C":
            case "S":
                altitude -= 1;
                break;
            case "#":
                throw new IllegalArgumentException();
        }
        return altitude;
    }

    private static boolean segmentReachable(String segment) {
        return segment != null && !"#".equals(segment);
    }

    private void parseInput(List<String> input) {
        map = Util.buildImageMap(input);
        w = input.getFirst().length();
        h = input.size();
        start = map.entrySet().stream().filter(e -> "S".equals(e.getValue())).map(Map.Entry::getKey).findFirst().orElseThrow();
    }
}
