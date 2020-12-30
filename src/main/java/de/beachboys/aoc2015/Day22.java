package de.beachboys.aoc2015;

import de.beachboys.Day;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class Day22 extends Day {

    enum Spell {
        MAGIC_MISSILE, DRAIN, SHIELD, POISON, RECHARGE
    }

    private static class State {

        public int playerHitPoints = 50;
        public int playerMana = 500;
        public int playerArmor = 0;

        public int bossHitPoints;
        public int bossDamage;

        public int poisonTurns = 0;
        public int shieldTurns = 0;
        public int rechargeTurns = 0;

        public int usedMana = 0;
        public Spell nextSpell;

        public State getNextState(Spell nextSpell) {
            State copy = new State();
            copy.playerHitPoints = this.playerHitPoints;
            copy.playerMana = this.playerMana;
            copy.playerArmor = this.playerArmor;
            copy.bossHitPoints = this.bossHitPoints;
            copy.bossDamage = this.bossDamage;
            copy.poisonTurns = this.poisonTurns;
            copy.shieldTurns = this.shieldTurns;
            copy.rechargeTurns = this.rechargeTurns;
            copy.usedMana = this.usedMana;
            copy.nextSpell = nextSpell;
            return copy;
        }
    }

    private int minimalUsedMana = Integer.MAX_VALUE;

    private boolean hardMode = false;

    private final Deque<State> statesToProcess = new LinkedList<>();

    public Object part1(List<String> input) {
        return winGameAndReturnMinimalUsedMana(input);
    }

    public Object part2(List<String> input) {
        hardMode = true;
        return winGameAndReturnMinimalUsedMana(input);
    }

    private int winGameAndReturnMinimalUsedMana(List<String> input) {
        State initialState = getInitialState(input);

        statesToProcess.add(initialState.getNextState(Spell.MAGIC_MISSILE));
        statesToProcess.add(initialState.getNextState(Spell.DRAIN));
        statesToProcess.add(initialState.getNextState(Spell.SHIELD));
        statesToProcess.add(initialState.getNextState(Spell.POISON));
        statesToProcess.add(initialState.getNextState(Spell.RECHARGE));

        while (!statesToProcess.isEmpty()) {
            processState(statesToProcess.poll());
        }

        return minimalUsedMana;
    }

    private State getInitialState(List<String> input) {
        State initialState = new State();
        String hitPointsAsString = io.getInput("Hit points (default " + initialState.playerHitPoints + "):");
        if (!hitPointsAsString.isEmpty()) {
            initialState.playerHitPoints = Integer.parseInt(hitPointsAsString);
        }
        String manaAsString = io.getInput("Mana (default " + initialState.playerMana + "):");
        if (!manaAsString.isEmpty()) {
            initialState.playerMana = Integer.parseInt(manaAsString);
        }
        initialState.bossHitPoints = Integer.parseInt(input.get(0).split(": ")[1]);
        initialState.bossDamage = Integer.parseInt(input.get(1).split(": ")[1]);
        return initialState;
    }

    private void processState(State state) {
        if (hardMode) {
            state.playerHitPoints -= 1;
        }
        if (isFightOngoing(state)) {
            handleEffects(state);
        }
        if (isFightOngoing(state)) {
            castSpell(state);
        }
        if (isFightOngoing(state)) {
            handleEffects(state);
        }
        if (isFightOngoing(state)) {
            bossAttacks(state);
        }
        if (isFightOngoing(state)) {
            prepareNextStatesAndAddToQueue(state);
        } else {
            handleFightEnd(state);
        }
    }

    private boolean isFightOngoing(State state) {
        return state.playerHitPoints > 0 && state.bossHitPoints > 0 && state.playerMana >= 0;
    }

    private void handleEffects(State state) {
        if (state.shieldTurns > 0) {
            state.shieldTurns--;
            if (state.shieldTurns == 0) {
                state.playerArmor -= 7;
            }
        }
        if (state.poisonTurns > 0) {
            state.poisonTurns--;
            state.bossHitPoints -= 3;
        }
        if (state.rechargeTurns > 0) {
            state.rechargeTurns--;
            state.playerMana += 101;
        }
    }

    private void castSpell(State state) {
        switch (state.nextSpell) {
            case MAGIC_MISSILE:
                state.playerMana -= 53;
                state.usedMana += 53;
                state.bossHitPoints -= 4;
                break;
            case DRAIN:
                state.playerMana -= 73;
                state.usedMana += 73;
                state.bossHitPoints -= 2;
                state.playerHitPoints += 2;
                break;
            case SHIELD:
                state.playerMana -= 113;
                state.usedMana += 113;
                state.playerArmor += 7;
                state.shieldTurns = 6;
                break;
            case POISON:
                state.playerMana -= 173;
                state.usedMana += 173;
                state.poisonTurns = 6;
                break;
            case RECHARGE:
                state.playerMana -= 229;
                state.usedMana += 229;
                state.rechargeTurns = 5;
                break;
        }
    }

    private void bossAttacks(State state) {
        state.playerHitPoints -= Math.max(1, state.bossDamage - state.playerArmor);
    }

    private void prepareNextStatesAndAddToQueue(State state) {
        if (minimalUsedMana < state.usedMana) {
            // too much mana => abort
            return;
        }
        statesToProcess.add(state.getNextState(Spell.MAGIC_MISSILE));
        statesToProcess.add(state.getNextState(Spell.DRAIN));
        if (state.shieldTurns < 2) {
            statesToProcess.add(state.getNextState(Spell.SHIELD));
        }
        if (state.poisonTurns < 2) {
            statesToProcess.add(state.getNextState(Spell.POISON));
        }
        if (state.rechargeTurns < 2) {
            statesToProcess.add(state.getNextState(Spell.RECHARGE));
        }
    }

    private void handleFightEnd(State state) {
        if (state.bossHitPoints <= 0 && state.playerHitPoints >= 0 && state.playerMana >= 0) {
            minimalUsedMana = Math.min(minimalUsedMana, state.usedMana);
        }
    }

}
