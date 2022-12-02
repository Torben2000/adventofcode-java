package de.beachboys.aoc2022;

import de.beachboys.Day;

import java.util.List;

public class Day02 extends Day {

    private static final char OPPONENT_ROCK = 'A';
    private static final char OPPONENT_PAPER = 'B';
    private static final char OPPONENT_SCISSORS = 'C';
    private static final char STRATEGY_ROCK = 'X';
    private static final char STRATEGY_PAPER = 'Y';
    private static final char STRATEGY_SCISSORS = 'Z';
    private static final char STRATEGY_LOSS = 'X';
    private static final char STRATEGY_DRAW = 'Y';
    private static final char STRATEGY_WIN = 'Z';
    private static final int SCORE_ROCK = 1;
    private static final int SCORE_PAPER = 2;
    private static final int SCORE_SCISSORS = 3;
    private static final int SCORE_LOSS = 0;
    private static final int SCORE_DRAW = 3;
    private static final int SCORE_WIN = 6;

    public Object part1(List<String> input) {
        long result = 0;
        for (String line : input) {
            char opponentsChoice = line.charAt(0);
            char strategy = line.charAt(2);
            switch (strategy) {
                case STRATEGY_ROCK:
                    result += SCORE_ROCK;
                    switch (opponentsChoice) {
                        case OPPONENT_ROCK:
                            result += SCORE_DRAW;
                            break;
                        case OPPONENT_PAPER:
                            result += SCORE_LOSS;
                            break;
                        case OPPONENT_SCISSORS:
                            result += SCORE_WIN;
                            break;
                    }
                    break;
                case STRATEGY_PAPER:
                    result += SCORE_PAPER;
                    switch (opponentsChoice) {
                        case OPPONENT_ROCK:
                            result += SCORE_WIN;
                            break;
                        case OPPONENT_PAPER:
                            result += SCORE_DRAW;
                            break;
                        case OPPONENT_SCISSORS:
                            result += SCORE_LOSS;
                            break;
                    }
                    break;
                case STRATEGY_SCISSORS:
                    result += SCORE_SCISSORS;
                    switch (opponentsChoice) {
                        case OPPONENT_ROCK:
                            result += SCORE_LOSS;
                            break;
                        case OPPONENT_PAPER:
                            result += SCORE_WIN;
                            break;
                        case OPPONENT_SCISSORS:
                            result += SCORE_DRAW;
                            break;
                    }
                    break;
            }
        }
        return result;
    }

    public Object part2(List<String> input) {
        long result = 0;
        for (String line : input) {
            char opponentsChoice = line.charAt(0);
            char strategy = line.charAt(2);
            switch (strategy) {
                case STRATEGY_LOSS:
                    result += SCORE_LOSS;
                    switch (opponentsChoice) {
                        case OPPONENT_ROCK:
                            result += SCORE_SCISSORS;
                            break;
                        case OPPONENT_PAPER:
                            result += SCORE_ROCK;
                            break;
                        case OPPONENT_SCISSORS:
                            result += SCORE_PAPER;
                            break;
                    }
                    break;
                case STRATEGY_DRAW:
                    result += SCORE_DRAW;
                    switch (opponentsChoice) {
                        case OPPONENT_ROCK:
                            result += SCORE_ROCK;
                            break;
                        case OPPONENT_PAPER:
                            result += SCORE_PAPER;
                            break;
                        case OPPONENT_SCISSORS:
                            result += SCORE_SCISSORS;
                            break;
                    }
                    break;
                case STRATEGY_WIN:
                    result += SCORE_WIN;
                    switch (opponentsChoice) {
                        case OPPONENT_ROCK:
                            result += SCORE_PAPER;
                            break;
                        case OPPONENT_PAPER:
                            result += SCORE_SCISSORS;
                            break;
                        case OPPONENT_SCISSORS:
                            result += SCORE_ROCK;
                            break;
                    }
                    break;
            }
        }
        return result;
    }

}
