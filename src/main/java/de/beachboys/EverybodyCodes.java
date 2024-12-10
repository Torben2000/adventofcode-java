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
        quests.put(2, new de.beachboys.ec2024.Quest02());
        quests.put(3, new de.beachboys.ec2024.Quest03());
        quests.put(4, new de.beachboys.ec2024.Quest04());
        quests.put(5, new de.beachboys.ec2024.Quest05());
        quests.put(6, new de.beachboys.ec2024.Quest06());
        quests.put(7, new de.beachboys.ec2024.Quest07());
        quests.put(8, new de.beachboys.ec2024.Quest08());
        quests.put(9, new de.beachboys.ec2024.Quest09());
        quests.put(10, new de.beachboys.ec2024.Quest10());
        quests.put(11, new de.beachboys.ec2024.Quest11());
        quests.put(12, new de.beachboys.ec2024.Quest12());
        quests.put(13, new de.beachboys.ec2024.Quest13());
        quests.put(14, new de.beachboys.ec2024.Quest14());
        quests.put(15, new de.beachboys.ec2024.Quest15());
        quests.put(16, new de.beachboys.ec2024.Quest16());
        quests.put(17, new de.beachboys.ec2024.Quest17());
        quests.put(18, new de.beachboys.ec2024.Quest18());
        quests.put(19, new de.beachboys.ec2024.Quest19());
        quests.put(20, new de.beachboys.ec2024.Quest20());
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
