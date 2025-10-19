package de.beachboys;

import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple3;
import org.json.JSONObject;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HexFormat;
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

    private final String browserSession;
    private final String downloadFolder;
    private final String historyDataFolder;
    private Integer seed;
    private final Map<Tuple3<Integer, Integer, Integer>, String> inputCache = new HashMap<>();

    public EverybodyCodes(String browserSession, Integer seed, String downloadFolder, String historyDataFolder) {
        this.browserSession = browserSession;
        this.seed = seed;
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
    public void downloadInput(int year, int dayOrQuest, int part) throws Exception {
        String encodedInput = getEncryptedInput(year, dayOrQuest, part);
        String aesKey = downloadJson("https://everybody.codes/api/event/" + year + "/quest/" + dayOrQuest).getString("key" + part);
        String input = decryptInput(aesKey, encodedInput);

        try (PrintWriter out = new PrintWriter(getInputFilePath(year, dayOrQuest, part))) {
            out.println(input);
        }
    }

    private String getEncryptedInput(int year, int dayOrQuest, int part) {
        Tuple3<Integer, Integer, Integer> cacheKey = Tuple.tuple(year, dayOrQuest, part);
        if (!inputCache.containsKey(cacheKey)) {
            JSONObject json = downloadJson("https://everybody-codes.b-cdn.net/assets/" + year + "/" + dayOrQuest + "/input/" + getSeed() + ".json");
            inputCache.put(Tuple.tuple(year, dayOrQuest, 1), json.getString("1"));
            inputCache.put(Tuple.tuple(year, dayOrQuest, 2), json.getString("2"));
            inputCache.put(Tuple.tuple(year, dayOrQuest, 3), json.getString("3"));
        }
        return inputCache.get(cacheKey);
    }

    private int getSeed() {
        if (seed == null) {
            JSONObject json = downloadJson("https://everybody.codes/api/user/me");
            seed = json.getInt("seed");
        }
        return seed;
    }

    private JSONObject downloadJson(String url) {
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Cookie", "everybody-codes=" + browserSession)
                    .build();

            String responseBody = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .join();
            return new JSONObject(responseBody);
        }
    }

    private static String decryptInput(String aesKey, String encryptedInput) throws Exception {
        byte[] encryptedBytes = HexFormat.of().parseHex(encryptedInput);
        byte[] keyBytes = aesKey.getBytes(StandardCharsets.UTF_8);
        byte[] ivBytes = aesKey.substring(0, 16).getBytes(StandardCharsets.UTF_8);

        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(ivBytes));

        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
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
