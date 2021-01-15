package de.beachboys.aoc2016;

import de.beachboys.Day;
import de.beachboys.Util;

import java.util.List;

public class Day16 extends Day {

    public Object part1(List<String> input) {
        return runLogic(input, 272);
    }

    public Object part2(List<String> input) {
        return runLogic(input, 35651584);
    }

    private String runLogic(List<String> input, int fileLengthDefaultValue) {
        int fileLength = Util.getIntValueFromUser("fileLength", fileLengthDefaultValue, io);
        String fileContent = buildFileContent(input, fileLength);
        return getChecksum(fileContent);
    }

    private String buildFileContent(List<String> input, int fileLength) {
        StringBuilder fileContent = new StringBuilder(input.get(0));
        while(fileContent.length() < fileLength) {
            String b = new StringBuilder(
                    fileContent.toString()
                    .replaceAll("1", "2")
                    .replaceAll("0", "1")
                    .replaceAll("2", "0"))
                    .reverse().toString();
            fileContent.append("0").append(b);
        }
        return fileContent.substring(0, fileLength);
    }

    private String getChecksum(String fileContent) {
        String checksum = fileContent;
        while (checksum.length() % 2 == 0) {
            checksum = getNextIterationOfChecksum(checksum);
        }
        return checksum;
    }

    private String getNextIterationOfChecksum(String checksum) {
        Character firstChar = null;
        StringBuilder newChecksum = new StringBuilder();
        for (char character : checksum.toCharArray()) {
            if (firstChar == null) {
                firstChar = character;
            } else {
                if (firstChar == character) {
                    newChecksum.append('1');
                } else {
                    newChecksum.append('0');
                }
                firstChar = null;
            }
        }
        return newChecksum.toString();
    }

}
