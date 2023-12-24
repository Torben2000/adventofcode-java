package de.beachboys.aoc2021;

import de.beachboys.Day;
import de.beachboys.Util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day04 extends Day {

    public Object part1(List<String> input) {
        List<Integer> drawn = Util.parseIntCsv(input.getFirst());
        List<Board> boards = parseBoards(input);

        Board winner = null;
        for (Integer draw : drawn) {
            for (Board board : boards) {
                if (board.draw(draw)) {
                    winner = board;
                }
            }
            if (winner != null) {
                return winner.score() * draw;
            }
        }

        return "no winner";
    }

    public Object part2(List<String> input) {
        List<Integer> drawn = Util.parseIntCsv(input.getFirst());
        List<Board> boards = parseBoards(input);

        Board lastWinner = null;
        for (Integer draw : drawn) {
            List<Board> boardsThatWon = new ArrayList<>();
            for (Board board : boards) {
                if (board.draw(draw)) {
                    boardsThatWon.add(board);
                }
            }
            boards.removeAll(boardsThatWon);

            if (boards.size() == 1) {
                lastWinner = boards.getFirst();
            }
            if (boards.isEmpty() && lastWinner != null) {
                return lastWinner.score() * draw;
            }
        }

        return "no last winner";
    }

    private List<Board> parseBoards(List<String> input) {
        List<Board> boards = new ArrayList<>();
        Board board = new Board();
        int currentRow = 0;
        for (int i = 2; i < input.size(); i++) {
            String line = input.get(i);
            if (line.isBlank()) {
                boards.add(board);
                board = new Board();
                currentRow = 0;
            } else {
                List<String> numbers = Util.parseToList(line, " ").stream().filter(x -> !x.isBlank()).toList();
                for (int j = 0; j < 5; j++) {
                    int val = Integer.parseInt(numbers.get(j));
                    board.cols.get(j).add(val);
                    board.rows.get(currentRow).add(val);
                }
                currentRow++;
            }
        }
        boards.add(board);
        return boards;
    }

    private static class Board {

        final List<Set<Integer>> rows = new ArrayList<>();
        final List<Set<Integer>> cols = new ArrayList<>();

        public Board() {
            for (int i = 0; i < 5; i++) {
                rows.add(new HashSet<>());
                cols.add(new HashSet<>());
            }
        }

        private boolean draw(Integer draw) {
            boolean isWinner = false;
            for (Set<Integer> row : rows) {
                row.remove(draw);
                if (row.isEmpty()) {
                    isWinner = true;
                }
            }
            for (Set<Integer> col : cols) {
                col.remove(draw);
                if (col.isEmpty()) {
                    isWinner = true;
                }
            }
            return isWinner;
        }

        public int score() {
            int score = 0;
            for (Set<Integer> row : rows) {
                for (Integer number : row) {
                    score += number;
                }
            }
            return score;
        }
    }

}
