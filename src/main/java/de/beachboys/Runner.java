package de.beachboys;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.*;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

public class Runner {

    public static int CURRENT_YEAR = 2019;
    public static int CURRENT_DAY = 3;
    public static int CURRENT_PART = 2;
    // use the session id from your browser session (long hex string)
    public static String BROWSER_SESSION = "secret";

    public static final Map<Integer, Day> DAYS = new HashMap<>();

    static {
        DAYS.put(1, new Day01());
        DAYS.put(2, new Day02());
        DAYS.put(3, new Day03());
    }

    public static void main(String[] args) {
        System.out.println("Current day: " + CURRENT_DAY + ", current part: " + CURRENT_PART);
        Function<List<String>, String> currentPart;
        if (CURRENT_PART == 1) {
            currentPart = DAYS.get(CURRENT_DAY)::part1;
        } else {
            currentPart = DAYS.get(CURRENT_DAY)::part2;
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
            HttpURLConnection con = (HttpURLConnection) new URL("https://adventofcode.com/" + CURRENT_YEAR + "/day/" + CURRENT_DAY + "/input").openConnection();
            con.setRequestMethod("GET");
            con.addRequestProperty("Cookie", "session=" + BROWSER_SESSION);
            try (BufferedInputStream in = new BufferedInputStream(con.getInputStream());
                FileOutputStream fileOutputStream = new FileOutputStream("src/main/resources/" + getInputFileName())) {
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
        String fileName = getInputFileName();

        try(BufferedReader r = new BufferedReader(new InputStreamReader(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream(fileName))))){
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
}
