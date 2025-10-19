package de.beachboys;

import com.google.common.base.Stopwatch;
import org.apache.commons.cli.*;

import java.io.*;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

public class Runner {

    private static final PuzzleTypeEnum CURRENT_PUZZLE_TYPE = PuzzleTypeEnum.EVERYBODY_CODES;
    private static final int CURRENT_YEAR = 2024;
    private static final int CURRENT_DAY = 1;
    private static final int CURRENT_PART = 1;

    // use the session id from your browser session (long hex string)
    private static final String AOC_BROWSER_SESSION = "secret";
    private static final String AOC_DATA_FOLDER = "c:/temp/";

    // use the session id from your browser session (long hex string)
    private static final String EC_BROWSER_SESSION = "secret";
    // if you fill this value (it is constant for your account), it will speed up downloads
    private static final Integer EC_SEED = null;
    private static final String EC_DOWNLOAD_FOLDER = System.getProperty("user.home") + "/Downloads/";
    private static final String EC_HISTORY_DATA_FOLDER = "c:/temp/";

    private static PuzzleTypeEnum currentPuzzleTypeEnum = CURRENT_PUZZLE_TYPE;
    private static final PuzzleType currentPuzzleType = initPuzzleType();
    private static int currentYear = CURRENT_YEAR;
    private static int currentDayOrQuest = CURRENT_DAY;
    private static int currentPartAsInt = CURRENT_PART;

    public static void main(String[] args) {
        try {
            parseCommandLine(args);
        } catch (ParseException e) {
            // no cool handling, but it should be fine
            e.printStackTrace();
        }
        printCurrentState();
        Function<List<String>, Object> currentPart = currentPuzzleType.getPart(currentYear, currentDayOrQuest, currentPartAsInt);
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("Test input (q to exit, d to download, r to use real data, s to switch to next part): ");
            String input = in.nextLine();
            try {
                switch (input) {
                    case "q":
                        System.exit(0);
                        break;
                    case "d":
                        downloadInput();
                        break;
                    case "r":
                        runLogicWithStopWatchAndPrintResult(currentPart, loadInputLines());
                        break;
                    case "s":
                        currentPartAsInt = (currentPartAsInt % currentPuzzleType.numberOfParts()) + 1;
                        currentPart = currentPuzzleType.getPart(currentYear, currentDayOrQuest, currentPartAsInt);
                        printCurrentState();
                        break;
                    default:
                        runLogicWithStopWatchAndPrintResult(currentPart, List.of(input));
                        break;
                }
            } catch (Exception e) {
                // no cool handling, but it should be fine
                e.printStackTrace();
            }
        }
    }

    private static void runLogicWithStopWatchAndPrintResult(Function<List<String>, Object> currentPart, List<String> inputLines) {
        Stopwatch stop = Stopwatch.createStarted();
        Object result = currentPart.apply(inputLines);
        stop.stop();
        System.out.println("Calculation time: " + stop);
        if (result.toString().contains("\n")) {
            System.out.println("Result:");
            System.out.println(result);
        } else {
            System.out.println("Result: " + result);
        }
        copyToClipboard(result);
        try {
            appendToTodaysHistory(result);
        } catch (IOException e) {
            System.err.println("Adding history entry failed");
        }
    }

    private static void copyToClipboard(Object newClipboardContent) {
        java.awt.Toolkit.getDefaultToolkit().getSystemClipboard()
                .setContents(new java.awt.datatransfer.StringSelection(newClipboardContent.toString()), null);
    }

    private static void printCurrentState() {
        System.out.println(currentPuzzleType.getCurrentStateString(currentYear, currentDayOrQuest, currentPartAsInt));
    }

    private static void downloadInput() throws Exception {
        currentPuzzleType.downloadInput(currentYear, currentDayOrQuest, currentPartAsInt);
    }

    private static List<String> loadInputLines() throws IOException {
        String fileNameWithPath = currentPuzzleType.getInputFilePath(currentYear, currentDayOrQuest, currentPartAsInt);
        try (BufferedReader r = new BufferedReader((new FileReader(fileNameWithPath)))) {
            return Util.removeEmptyTrailingStrings(r.lines().collect(toList()));
        }
    }

    private static void appendToTodaysHistory(Object newHistoryEntry) throws IOException {
        String stringToWrite = "\n" + newHistoryEntry;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentPuzzleType.getHistoryFilePath(currentYear, currentDayOrQuest, currentPartAsInt), true))) {
            writer.write(stringToWrite);
        }
    }

    private static PuzzleType initPuzzleType() {
        return switch (currentPuzzleTypeEnum) {
            case ADVENT_OF_CODE -> new AdventOfCode(AOC_BROWSER_SESSION, AOC_DATA_FOLDER);
            case EVERYBODY_CODES -> new EverybodyCodes(EC_BROWSER_SESSION, EC_SEED, EC_DOWNLOAD_FOLDER, EC_HISTORY_DATA_FOLDER);
            default -> throw new IllegalArgumentException();
        };
    }

    private static void parseCommandLine(String[] args) throws ParseException {
        Options options = new Options();
        Option puzzleType = Option.builder("t").argName("puzzleType").longOpt("type").hasArg().desc("Puzzle type: AOC or EC").build();
        options.addOption(puzzleType);
        Option year = Option.builder().argName("year").longOpt("year").hasArg().desc("Year").build();
        options.addOption(year);
        Option dayOrQuest = Option.builder("d").argName("dayOrQuest").longOpt("day").hasArg().desc("Day or quest").build();
        options.addOption(dayOrQuest);
        Option part = Option.builder("p").argName("part").longOpt("part").hasArg().desc("Part of day/quest").build();
        options.addOption(part);
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        if (cmd.hasOption(puzzleType)) {
            String puzzleTypeArg = cmd.getOptionValue(puzzleType);
            currentPuzzleTypeEnum = switch (puzzleTypeArg.toLowerCase()) {
                case "aoc" -> PuzzleTypeEnum.ADVENT_OF_CODE;
                case "ec" -> PuzzleTypeEnum.EVERYBODY_CODES;
                default -> throw new IllegalArgumentException("Unsupported puzzle type");
            };
            initPuzzleType();
        }
        if (cmd.hasOption(year)) {
            String yearArg = cmd.getOptionValue(year);
            currentYear = Integer.parseInt(yearArg);
        }
        if (cmd.hasOption(dayOrQuest)) {
            String dayOrQuestArg = cmd.getOptionValue(dayOrQuest);
            currentDayOrQuest = Integer.parseInt(dayOrQuestArg);
        }
        if (cmd.hasOption(part)) {
            String partArg = cmd.getOptionValue(part);
            currentPartAsInt = Integer.parseInt(partArg);
        }
    }

    private enum PuzzleTypeEnum {
        ADVENT_OF_CODE, EVERYBODY_CODES
    }
}
