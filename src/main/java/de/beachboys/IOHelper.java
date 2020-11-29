package de.beachboys;

import java.util.Scanner;

public class IOHelper {

    private Scanner in = new Scanner(System.in);

    public String getInput(String textToDisplay) {
        System.out.println(textToDisplay);
        return in.nextLine();
    }

    public void logDebug(Object debugText) {
        System.out.println(debugText);
    }

    public void logInfo(Object infoText) {
        System.out.println(infoText);
    }

}
