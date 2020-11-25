package de.beachboys;

import java.io.*;
import java.util.*;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

public class Runner {

    public static int CURRENT_DAY = 2;
    public static int CURRENT_PART = 1;

    public static final Map<Integer, Day> DAYS = new HashMap<>();

    static {
        DAYS.put(1, new Day01());
        DAYS.put(2, new Day02());
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
        // TODO
    }

    private static List<String> loadInputLines(){
        String paddedDay = String.valueOf(CURRENT_DAY);
        if(CURRENT_DAY < 10) {
            paddedDay = "0" + CURRENT_DAY;
        }
        String fileName = "day" + paddedDay + ".txt";

        try(BufferedReader r = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream(fileName)))){
            return r.lines().collect(toList());
        } catch(IOException e){
            throw new UncheckedIOException(e);
        }
    }
}
