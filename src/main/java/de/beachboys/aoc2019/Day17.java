package de.beachboys.aoc2019;

import de.beachboys.Day;
import de.beachboys.Direction;
import de.beachboys.IOHelper;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day17 extends Day {

    private String imageString = "";

    private Map<Tuple2<Integer, Integer>, String> imageMap = new HashMap<>();

    private final IntcodeComputer computer = new IntcodeComputer();

    private List<Integer> inputCharacters = List.of();

    private int inputCounter = 0;

    private int dustAmount = 0;

    public Object part1(List<String> input) {
        List<Long> list = Util.parseLongCsv(input.getFirst());
        runComputer(list);
        imageMap = Util.buildImageMap(imageString);
        io.logDebug(Util.paintMap(imageMap, Map.of("#", "#", "^", "^", "v", "v", "<", "<", ">", ">")));
        return calculateAlignmentSum(imageMap);
    }

    public Object part2(List<String> input) {
        List<Long> list = Util.parseLongCsv(input.getFirst());
        runComputer(list);

        imageMap = Util.buildImageMap(imageString);
        io.logDebug(Util.paintMap(imageMap, Map.of("#", "#", "^", "^", "v", "v", "<", "<", ">", ">")));

        String completeInputString = buildUnoptimizedInputString(imageMap);
        String optimizedInputString = optimizeInputString(completeInputString);
        String printOutput = io.getInput("Print output (y/n)?");
        this.inputCharacters = (optimizedInputString + printOutput + "n\n").chars().boxed().collect(Collectors.toList());

        imageString = "";
        List<Long> list2ndRun = Util.parseLongCsv(input.getFirst());
        list2ndRun.set(0, 2L);
        runComputer(list2ndRun);

        imageMap = Util.buildImageMap(imageString);
        io.logDebug(Util.paintMap(imageMap, Map.of("#", "#", "^", "^", "v", "v", "<", "<", ">", ">")));

        return dustAmount;
    }

    protected String optimizeInputString(String completeInputString) {
        String main = completeInputString;
        String a = getBiggestPossibleMethod(main);
        main = main.replaceAll(Pattern.quote(a), "A");
        String b = getBiggestPossibleMethod(main);
        main = main.replaceAll(b, "B");
        String c = getBiggestPossibleMethod(main);
        main = main.replaceAll(c, "C");
        return main + "\n" + a + "\n" + b + "\n" + c + "\n";
    }

    private String getBiggestPossibleMethod(String main) {
        String method = "";
        List<String> commands = new ArrayList<>(Util.parseCsv(main));
        commands.removeIf("A"::equals);
        commands.removeIf("B"::equals);
        commands.removeIf("C"::equals);
        for (int i = 0; i< commands.size(); i = i + 2) {
            String originalMethod = method;
            if (!method.isEmpty()) {
                method += ",";
            }
            method += commands.get(i);
            method += ",";
            method += commands.get(i + 1);
            if (method.length() > 20) {
                return originalMethod;
            }
            String temp = main;
            int counter = 0;
            while (temp.contains(method)) {
                temp = temp.replaceFirst(method, "");
                counter++;
            }
            if (counter <= 1) {
                return originalMethod;
            }
        }
        return method;
    }

    private void runComputer(List<Long> list) {
        IOHelper io = new IOHelper() {

            @Override
            public String getInput(String textToDisplay) {
                return inputCharacters.get(inputCounter++).toString();
            }

            @Override
            public void logInfo(Object infoText) {
                if (infoText.toString().length() > 2){
                    dustAmount = Integer.parseInt(infoText.toString());
                } else {
                    int charCode = Integer.parseInt(infoText.toString());
                    imageString += Character.toString(charCode);
                }
            }

            @Override
            public void logDebug(Object debugText) {
                // do nothing
            }
        };

        computer.runLogic(new ArrayList<>(list), io);
    }

    protected int calculateAlignmentSum(Map<Tuple2<Integer, Integer>, String> imageMap) {
        int total = 0;
        for (Tuple2<Integer, Integer> point : imageMap.keySet()) {
            boolean scaffold = isScaffold(imageMap.get(point));
            for (Direction dir : Direction.values()) {
                scaffold &= isScaffold(imageMap.get(dir.move(point, 1)));
            }
            if (scaffold) {
                total += point.v1 * point.v2;
            }
        }
        return total;
    }

    private boolean isScaffold(String mapString) {
        return "#".equals(mapString) || isRobot(mapString);
    }

    private boolean isRobot(String mapString) {
        return "^".equals(mapString) || "v".equals(mapString) || "<".equals(mapString) || ">".equals(mapString);
    }

    protected String buildUnoptimizedInputString(Map<Tuple2<Integer, Integer>, String> imageMap) {
        StringBuilder inputString = new StringBuilder();
        Map.Entry<Tuple2<Integer, Integer>, String> robot = imageMap.entrySet().stream().filter(entry -> isRobot(entry.getValue())).findFirst().orElseThrow();
        Direction currentDirection = Direction.fromString(robot.getValue());
        Tuple2<Integer, Integer> currentPosition = robot.getKey();
        Direction newDirection = getNewDirection(imageMap, currentDirection, currentPosition);
        while (newDirection != null) {
            inputString.append(getTurnCommand(currentDirection, newDirection)).append(",");
            currentDirection = newDirection;
            Tuple2<Integer, Integer> newPosition = getNewPositionToMoveForwardTo(imageMap, currentPosition, currentDirection);
            int walkingLength = Math.abs(newPosition.v1 - currentPosition.v1 + newPosition.v2 - currentPosition.v2);
            inputString.append(walkingLength).append(",");
            currentPosition = newPosition;
            newDirection = getNewDirection(imageMap, currentDirection, currentPosition);
        }
        if (!inputString.isEmpty()) {
            inputString.deleteCharAt(inputString.length() - 1);
        }
        return inputString.toString();
    }

    private Tuple2<Integer, Integer> getNewPositionToMoveForwardTo(Map<Tuple2<Integer, Integer>, String> imageMap, Tuple2<Integer, Integer> currentPosition, Direction direction) {
        Tuple2<Integer, Integer> newPosition;
        Tuple2<Integer, Integer> nextPosition = currentPosition;
        do {
            newPosition = nextPosition;
            nextPosition = direction.move(newPosition, 1);
        } while (isScaffold(imageMap.get(nextPosition)));
        return newPosition;
    }

    private String getTurnCommand(Direction currentDirection, Direction newDirection) {
        if (newDirection == currentDirection.turnRight()) {
            return "R";
        }
        if (newDirection == currentDirection.turnLeft()) {
            return "L";
        }
        throw new IllegalArgumentException("Turn not possible");
    }

    private Direction getNewDirection(Map<Tuple2<Integer, Integer>, String> imageMap, Direction currentDirection, Tuple2<Integer, Integer> currentPosition) {
        return switch (currentDirection) {
            case NORTH, SOUTH -> getNewDirection(imageMap, currentPosition, List.of(Direction.WEST, Direction.EAST));
            case EAST, WEST -> getNewDirection(imageMap, currentPosition, List.of(Direction.NORTH, Direction.SOUTH));
        };
    }

    private Direction getNewDirection(Map<Tuple2<Integer, Integer>, String> imageMap, Tuple2<Integer, Integer> currentPosition, List<Direction> possibleNewDirections) {
        for (Direction newDirection : possibleNewDirections) {
            Tuple2<Integer, Integer> newPosition = newDirection.move(currentPosition, 1);
            if (isScaffold(imageMap.get(newPosition))) {
                return newDirection;
            }
        }
        return null;
    }

}
