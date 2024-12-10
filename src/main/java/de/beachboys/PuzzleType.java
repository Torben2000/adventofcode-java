package de.beachboys;

import java.util.List;
import java.util.function.Function;

public interface PuzzleType {

    int numberOfParts();

    String getCurrentStateString(int year, int dayOrQuest, int part);

    Function<List<String>, Object> getPart(int year, int dayOrQuest, int part);

    void downloadInput(int year, int dayOrQuest, int part) throws Exception;

    String getInputFilePath(int year, int dayOrQuest, int part);

    String getHistoryFilePath(int year, int dayOrQuest, int part);

}
