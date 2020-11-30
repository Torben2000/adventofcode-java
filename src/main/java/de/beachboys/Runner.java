package de.beachboys;

import de.beachboys.aoc2019.Day01;
import de.beachboys.aoc2019.Day02;
import de.beachboys.aoc2019.Day03;
import de.beachboys.aoc2019.Day04;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

public class Runner {

    private static int CURRENT_YEAR = 2019;
    private static int CURRENT_DAY = 9;
    private static int CURRENT_PART = 1;
    // use the session id from your browser session (long hex string)
    private static String BROWSER_SESSION = "secret";
    private static final String DATA_FOLDER = "c:/temp/";

    public static final Map<Integer, Map<Integer, Day>> YEARS = new HashMap<>();

    static {
        buildStaticMapOfDays();
    }

    public static void main(String[] args) {
        System.out.println("Current year: " + CURRENT_YEAR + ", current day: " + CURRENT_DAY + ", current part: " + CURRENT_PART);
        Function<List<String>, Object> currentPart;
        if (CURRENT_PART == 1) {
            currentPart = YEARS.get(CURRENT_YEAR).get(CURRENT_DAY)::part1;
        } else {
            currentPart = YEARS.get(CURRENT_YEAR).get(CURRENT_DAY)::part2;
        }
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("Testinput (q to exit, d to download, r to use real data): ");
            String input = in.nextLine();
            switch (input) {
                case "q":
                    System.exit(0);
                    break;
                case "d":
                    downloadInput();
                    break;
                case "r":
                    System.out.println(currentPart.apply(loadInputLines()));
                    break;
                default:
                    System.out.println(currentPart.apply(List.of(input)));
                    break;
            }
        }
    }

    private static void downloadInput() {
        try {
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
            } catch (IOException e) {
                // no cool handling, but it should be fine
                e.printStackTrace();
            }
        } catch (Exception e) {
            // no cool handling, but it should be fine
            e.printStackTrace();
        }
    }

    private static List<String> loadInputLines(){
        String fileNameWithPath = DATA_FOLDER + "aoc" + CURRENT_YEAR + "/" + getInputFileName();
        try(BufferedReader r = new BufferedReader((new FileReader(fileNameWithPath)))){
            return r.lines().collect(toList());
        } catch(IOException e){
            throw new UncheckedIOException(e);
        }
    }

    private static String getInputFileName() {
        String paddedDay = String.valueOf(CURRENT_DAY);
        if(CURRENT_DAY < 10) {
            paddedDay = "0" + CURRENT_DAY;
        }
        return "day" + paddedDay + ".txt";
    }

    private static void buildStaticMapOfDays() {
        Map<Integer, Day> days2019 = new HashMap<>();
        days2019.put(1, new Day01());
        days2019.put(2, new Day02());
        days2019.put(3, new Day03());
        days2019.put(4, new Day04());
        days2019.put(5, new de.beachboys.aoc2019.Day05());
        days2019.put(6, new de.beachboys.aoc2019.Day06());
        days2019.put(7, new de.beachboys.aoc2019.Day07());
        days2019.put(8, new de.beachboys.aoc2019.Day08());
        days2019.put(9, new de.beachboys.aoc2019.Day09());
        days2019.put(10, new de.beachboys.aoc2019.Day10());
        days2019.put(11, new de.beachboys.aoc2019.Day11());
        days2019.put(12, new de.beachboys.aoc2019.Day12());
        days2019.put(13, new de.beachboys.aoc2019.Day13());
        days2019.put(14, new de.beachboys.aoc2019.Day14());
        days2019.put(15, new de.beachboys.aoc2019.Day15());
        days2019.put(16, new de.beachboys.aoc2019.Day16());
        days2019.put(17, new de.beachboys.aoc2019.Day17());
        days2019.put(18, new de.beachboys.aoc2019.Day18());
        days2019.put(19, new de.beachboys.aoc2019.Day19());
        days2019.put(20, new de.beachboys.aoc2019.Day20());
        days2019.put(21, new de.beachboys.aoc2019.Day21());
        days2019.put(22, new de.beachboys.aoc2019.Day22());
        days2019.put(23, new de.beachboys.aoc2019.Day23());
        days2019.put(24, new de.beachboys.aoc2019.Day24());
        days2019.put(25, new de.beachboys.aoc2019.Day25());

        Map<Integer, Day> days2020 = new HashMap<>();
        days2020.put(1, new de.beachboys.aoc2020.Day01());
        days2020.put(2, new de.beachboys.aoc2020.Day02());
        days2020.put(3, new de.beachboys.aoc2020.Day03());
        days2020.put(4, new de.beachboys.aoc2020.Day04());
        days2020.put(5, new de.beachboys.aoc2020.Day05());
        days2020.put(6, new de.beachboys.aoc2020.Day06());
        days2020.put(7, new de.beachboys.aoc2020.Day07());
        days2020.put(8, new de.beachboys.aoc2020.Day08());
        days2020.put(9, new de.beachboys.aoc2020.Day09());
        days2020.put(10, new de.beachboys.aoc2020.Day10());
        days2020.put(11, new de.beachboys.aoc2020.Day11());
        days2020.put(12, new de.beachboys.aoc2020.Day12());
        days2020.put(13, new de.beachboys.aoc2020.Day13());
        days2020.put(14, new de.beachboys.aoc2020.Day14());
        days2020.put(15, new de.beachboys.aoc2020.Day15());
        days2020.put(16, new de.beachboys.aoc2020.Day16());
        days2020.put(17, new de.beachboys.aoc2020.Day17());
        days2020.put(18, new de.beachboys.aoc2020.Day18());
        days2020.put(19, new de.beachboys.aoc2020.Day19());
        days2020.put(20, new de.beachboys.aoc2020.Day20());
        days2020.put(21, new de.beachboys.aoc2020.Day21());
        days2020.put(22, new de.beachboys.aoc2020.Day22());
        days2020.put(23, new de.beachboys.aoc2020.Day23());
        days2020.put(24, new de.beachboys.aoc2020.Day24());
        days2020.put(25, new de.beachboys.aoc2020.Day25());

        YEARS.put(2019, days2019);
        YEARS.put(2020, days2020);
    }

}
