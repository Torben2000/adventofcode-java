package de.beachboys.aoc2024;

import de.beachboys.Day;
import de.beachboys.Util;

import java.util.ArrayList;
import java.util.List;

public class Day09 extends Day {

    public Object part1(List<String> input) {
        List<Integer> fileSystem = parseInitialFileSystem(input);

        int firstFreeBlock = 0;
        int lastFileBlock = fileSystem.size() - 1;
        while (firstFreeBlock <= lastFileBlock) {
            while (fileSystem.get(firstFreeBlock) != null) {
                firstFreeBlock++;
            }
            while (fileSystem.get(lastFileBlock) == null) {
                lastFileBlock--;
            }
            if (firstFreeBlock < lastFileBlock) {
                fileSystem.set(firstFreeBlock, fileSystem.get(lastFileBlock));
                fileSystem.set(lastFileBlock, null);
            }
        }

        return calculateCheckSum(fileSystem);
    }

    public Object part2(List<String> input) {
        List<Integer> fileSystem = parseInitialFileSystem(input);

        int lastFileBlock = fileSystem.size() - 1;
        while (lastFileBlock >= 0) {
            while (fileSystem.get(lastFileBlock) == null) {
                lastFileBlock--;
            }
            Integer fileIdOfLastFileBlock = fileSystem.get(lastFileBlock);
            int indexBeforeLastFile = lastFileBlock;
            while (indexBeforeLastFile >= 0 && fileIdOfLastFileBlock.equals(fileSystem.get(indexBeforeLastFile))) {
                indexBeforeLastFile--;
            }
            int fileSize = lastFileBlock - indexBeforeLastFile;

            int startOfFreeSpace = getStartOfEnoughFreeSpace(fileSize, fileSystem);

            if (startOfFreeSpace < lastFileBlock) {
                for (int i = 0; i < fileSize; i++) {
                    fileSystem.set(startOfFreeSpace + i, fileIdOfLastFileBlock);
                    fileSystem.set(lastFileBlock - i, null);
                }
            }
            lastFileBlock = indexBeforeLastFile;
        }

        return calculateCheckSum(fileSystem);
    }

    private static int getStartOfEnoughFreeSpace(int fileSize, List<Integer> fileSystem) {
        int freeSpaceSize = 0;
        int startOfFreeSpace = 0;
        int indexAfterFreeBlock = startOfFreeSpace;
        while (fileSize > freeSpaceSize && startOfFreeSpace < fileSystem.size()) {
            startOfFreeSpace = indexAfterFreeBlock;
            while (startOfFreeSpace < fileSystem.size() && fileSystem.get(startOfFreeSpace) != null) {
                startOfFreeSpace++;
            }
            indexAfterFreeBlock = startOfFreeSpace;
            while (indexAfterFreeBlock < fileSystem.size() && fileSystem.get(indexAfterFreeBlock) == null) {
                indexAfterFreeBlock++;
            }
            freeSpaceSize = indexAfterFreeBlock - startOfFreeSpace;
        }
        return startOfFreeSpace;
    }

    private static List<Integer> parseInitialFileSystem(List<String> input) {
        List<Integer> mapValues = Util.parseToIntList(input.getFirst(), "");
        List<Integer> fileSystem = new ArrayList<>();
        int fileId = 0;
        for (int i = 0; i < mapValues.size(); i++) {
            Integer mapValue = mapValues.get(i);
            if (i % 2 == 0) {
                for (int j = 0; j < mapValue; j++) {
                    fileSystem.add(fileId);
                }
                fileId++;
            } else {
                for (int j = 0; j < mapValue; j++) {
                    fileSystem.add(null);
                }
            }
        }
        return fileSystem;
    }

    private static long calculateCheckSum(List<Integer> fileSystem) {
        long result = 0;
        for (int i = 0; i < fileSystem.size(); i++) {
            Integer fileId = fileSystem.get(i);
            if (fileId != null) {
                result+= (long) i * fileId;
            }
        }
        return result;
    }

}
