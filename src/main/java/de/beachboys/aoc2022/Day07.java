package de.beachboys.aoc2022;

import de.beachboys.Day;

import java.util.ArrayList;
import java.util.List;

public class Day07 extends Day {

    public Object part1(List<String> input) {
        List<Directory> allDirectories = parseFilesystem(input);
        return allDirectories.stream().map(Directory::getSize).filter(size -> size <= 100000).reduce(Long::sum).orElseThrow();
    }
    public Object part2(List<String> input) {
        List<Directory> allDirectories = parseFilesystem(input);
        long neededSpace = allDirectories.get(0).getSize() - 40000000;
        return allDirectories.stream().map(Directory::getSize).filter(size -> size >= neededSpace).min(Long::compareTo).orElseThrow();
    }

    private static List<Directory> parseFilesystem(List<String> input) {
        List<Directory> allDirectories = new ArrayList<>();
        Directory root = new Directory("/", null);
        allDirectories.add(root);
        Directory currentDirectory = root;
        for (String line : input) {
            String[] splitLine = line.split(" ");
            if (splitLine[0].equals("$") && splitLine[1].equals("cd")) {
                if (splitLine[2].equals("/")) {
                    currentDirectory = root;
                } else if (splitLine[2].equals("..")) {
                    currentDirectory = currentDirectory.parent;
                } else {
                    Directory newDir = new Directory(splitLine[2], currentDirectory);
                    allDirectories.add(newDir);
                    currentDirectory.dirs.add(newDir);
                    currentDirectory = newDir;
                }
            } else {
                try {
                    int size = Integer.parseInt(splitLine[0]);
                    File file = new File(splitLine[1], size);
                    currentDirectory.files.add(file);
                } catch (NumberFormatException e) {
                    // not a file so we don't care
                }
            }
        }
        return allDirectories;
    }

    private static class Directory {

        String name;
        Directory parent;
        List<Directory> dirs = new ArrayList<>();
        List<File> files = new ArrayList<>();
        long size;

        Directory(String name, Directory parent) {
            this.name = name;
            this.parent = parent;
        }

        long getSize() {
            if (size == 0) {
                for (Directory dir : dirs) {
                    size += dir.getSize();
                }
                for (File file : files) {
                    size += file.size;
                }
            }
            return size;
        }

    }

    private static class File {

        String name;
        int size;

        File(String name, int size) {
            this.name = name;
            this.size = size;
        }

    }

}
