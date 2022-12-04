package de.beachboys;

import com.google.common.base.Stopwatch;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

public class Runner {

    private static final int CURRENT_YEAR = 2022;
    private static final int CURRENT_DAY = 4;
    private static final int CURRENT_PART = 1;
    // use the session id from your browser session (long hex string)
    private static final String BROWSER_SESSION = "secret";
    private static final String DATA_FOLDER = "c:/temp/";

    public static void main(String[] args) {
        int currentPartAsInt = CURRENT_PART;
        printCurrentState(currentPartAsInt);
        Function<List<String>, Object> currentPart = getCurrentPart(currentPartAsInt);
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("Test input (q to exit, d to download, r to use real data, s to switch to other part): ");
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
                        currentPartAsInt = currentPartAsInt == 1 ? 2 : 1;
                        currentPart = getCurrentPart(currentPartAsInt);
                        printCurrentState(currentPartAsInt);
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
    }

    private static void printCurrentState(int currentPartAsInt) {
        System.out.println("Current year: " + CURRENT_YEAR + ", current day: " + CURRENT_DAY + ", current part: " + currentPartAsInt);
    }

    private static Function<List<String>, Object> getCurrentPart(int partToSelect) {
        Function<List<String>, Object> currentPart;
        if (partToSelect == 1) {
            currentPart = YearMaps.getDay(CURRENT_YEAR, CURRENT_DAY)::part1;
        } else {
            currentPart = YearMaps.getDay(CURRENT_YEAR, CURRENT_DAY)::part2;
        }
        return currentPart;
    }

    private static void downloadInput() throws Exception {
        Files.createDirectories(Paths.get(DATA_FOLDER + "/aoc" + CURRENT_YEAR + "/"));
        HttpURLConnection con = (HttpURLConnection) new URL("https://adventofcode.com/" + CURRENT_YEAR + "/day/" + CURRENT_DAY + "/input").openConnection();
        con.setRequestMethod("GET");
        con.addRequestProperty("Cookie", "session=" + BROWSER_SESSION);
        try (BufferedInputStream in = new BufferedInputStream(con.getInputStream());
            FileOutputStream fileOutputStream = new FileOutputStream(DATA_FOLDER + "/aoc" + CURRENT_YEAR + "/" + getInputFileName())) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        }
    }

    private static List<String> loadInputLines() throws IOException {
        String fileNameWithPath = DATA_FOLDER + "aoc" + CURRENT_YEAR + "/" + getInputFileName();
        try(BufferedReader r = new BufferedReader((new FileReader(fileNameWithPath)))){
            return Util.removeEmptyTrailingStrings(r.lines().collect(toList()));
        }
    }

    private static String getInputFileName() {
        return "day" + String.format("%02d", CURRENT_DAY) + ".txt";
    }

}
