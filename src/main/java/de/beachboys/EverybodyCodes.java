package de.beachboys;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class EverybodyCodes implements PuzzleType {

    public static final Map<Integer, Map<Integer, Quest>> YEARS = new HashMap<>();

    static {
        buildStaticMapOfQuests();
    }

    private static void buildStaticMapOfQuests() {
        YEARS.put(2024, getQuests2024());
    }

    private static Map<Integer, Quest> getQuests2024() {
        Map<Integer, Quest> quests = new HashMap<>();
        quests.put(1, new de.beachboys.ec2024.Quest01());
        return quests;
    }

    private final String downloadFolder;
    private final String historyDataFolder;

    public EverybodyCodes(String downloadFolder, String historyDataFolder) {
        this.downloadFolder = downloadFolder;
        this.historyDataFolder = historyDataFolder;
    }

    @Override
    public int numberOfParts() {
        return 3;
    }

    @Override
    public String getCurrentStateString(int year, int dayOrQuest, int part) {
        return "Everybody Codes " + year + ", quest " + dayOrQuest + ", part " + part;
    }

    @Override
    public Function<List<String>, Object> getPart(int year, int dayOrQuest, int part) {
        return switch (part) {
            case 1 -> getQuest(year, dayOrQuest)::part1;
            case 2 -> getQuest(year, dayOrQuest)::part2;
            case 3 -> getQuest(year, dayOrQuest)::part3;
            default -> throw new IllegalArgumentException();
        };
    }

    private Quest getQuest(int year, int quest) {
        return YEARS.get(year).get(quest);
    }

    @Override
    public void downloadInput(int year, int dayOrQuest, int part) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getInputFilePath(int year, int dayOrQuest, int part) {
        return downloadFolder + getInputFileName(year, dayOrQuest, part);
    }

    private static String getInputFileName(int year, int quest, int part) {
        return "everybody_codes_e" + String.format("%04d", year) + "_q" + String.format("%02d", quest) + "_p" + String.format("%01d", part) + ".txt";
    }

    @Override
    public String getHistoryFilePath(int year, int dayOrQuest, int part) throws IOException {
        return getHistoryFolderOfYear(year) + getHistoryFileName(dayOrQuest, part);
    }

    private String getHistoryFolderOfYear(int year) throws IOException {
        String folderName = historyDataFolder + "/ec" + year + "/";
        Files.createDirectories(Paths.get(folderName));
        return folderName;
    }

    private static String getHistoryFileName(int quest, int part) {
        return "quest" + String.format("%02d", quest) + "-part" + String.format("%01d", part) + "-history.txt";
    }
}
