package de.beachboys;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;

public class AdventOfCode implements PuzzleType {

    private final String browserSession;
    private final String dataFolder;

    public AdventOfCode(String browserSession, String dataFolder) {
        this.browserSession = browserSession;
        this.dataFolder = dataFolder;
    }

    @Override
    public int numberOfParts() {
        return 2;
    }

    @Override
    public String getCurrentStateString(int year, int dayOrQuest, int part) {
        return "Advent of Code " + year + ", day " + dayOrQuest + ", part " + part;
    }

    @Override
    public Function<List<String>, Object> getPart(int year, int dayOrQuest, int part) {
        return switch (part) {
            case 1 -> YearMaps.getDay(year, dayOrQuest)::part1;
            case 2 -> YearMaps.getDay(year, dayOrQuest)::part2;
            default -> throw new IllegalArgumentException();
        };
    }

    @Override
    public void downloadInput(int year, int dayOrQuest, int part) throws Exception {
        HttpURLConnection con = (HttpURLConnection) new URI("https://adventofcode.com/" + year + "/day/" + dayOrQuest + "/input").toURL().openConnection();
        con.setRequestMethod("GET");
        con.addRequestProperty("Cookie", "session=" + browserSession);
        try (BufferedInputStream in = new BufferedInputStream(con.getInputStream())) {
            try (FileOutputStream fileOutputStream = new FileOutputStream(getInputFilePath(year, dayOrQuest, part))) {
                byte[] dataBuffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                    fileOutputStream.write(dataBuffer, 0, bytesRead);
                }
            }
        }
    }

    private String getFolderOfYear(int year) throws IOException {
        String folderName = dataFolder + "/aoc" + year + "/";
        Files.createDirectories(Paths.get(folderName));
        return folderName;
    }

    @Override
    public String getInputFilePath(int year, int dayOrQuest, int part) throws IOException {
        return getFolderOfYear(year) + getInputFileName(dayOrQuest);
    }

    private static String getInputFileName(int day) {
        return "day" + String.format("%02d", day) + ".txt";
    }

    @Override
    public String getHistoryFilePath(int year, int dayOrQuest, int part) throws IOException {
        return getFolderOfYear(year) + getHistoryFileName(dayOrQuest);
    }
    private static String getHistoryFileName(int day) {
        return "day" + String.format("%02d", day) + "-history.txt";
    }
}
