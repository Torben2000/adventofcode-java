package de.beachboys;

import java.util.HashMap;
import java.util.Map;

public class YearMaps {

    public static final Map<Integer, Map<Integer, Day>> YEARS = new HashMap<>();

    static {
        buildStaticMapOfDays();
    }

    public static Day getDay(int year, int day) {
        return YEARS.get(year).get(day);
    }

    private static void buildStaticMapOfDays() {
        YEARS.put(2022, getDays2022());
        YEARS.put(2021, getDays2021());
        YEARS.put(2020, getDays2020());
        YEARS.put(2019, getDays2019());
        YEARS.put(2018, getDays2018());
        YEARS.put(2017, getDays2017());
        YEARS.put(2016, getDays2016());
        YEARS.put(2015, getDays2015());
    }

    private static Map<Integer, Day> getDays2022() {
        Map<Integer, Day> days = new HashMap<>();
        days.put(1, new de.beachboys.aoc2022.Day01());
        days.put(2, new de.beachboys.aoc2022.Day02());
        days.put(3, new de.beachboys.aoc2022.Day03());
        days.put(4, new de.beachboys.aoc2022.Day04());
        days.put(5, new de.beachboys.aoc2022.Day05());
        days.put(6, new de.beachboys.aoc2022.Day06());
        days.put(7, new de.beachboys.aoc2022.Day07());
        days.put(8, new de.beachboys.aoc2022.Day08());
        days.put(9, new de.beachboys.aoc2022.Day09());
        days.put(10, new de.beachboys.aoc2022.Day10());
        days.put(11, new de.beachboys.aoc2022.Day11());
        days.put(12, new de.beachboys.aoc2022.Day12());
        days.put(13, new de.beachboys.aoc2022.Day13());
        days.put(14, new de.beachboys.aoc2022.Day14());
        days.put(15, new de.beachboys.aoc2022.Day15());
        days.put(16, new de.beachboys.aoc2022.Day16());
        days.put(17, new de.beachboys.aoc2022.Day17());
        days.put(18, new de.beachboys.aoc2022.Day18());
        days.put(19, new de.beachboys.aoc2022.Day19());
        days.put(20, new de.beachboys.aoc2022.Day20());
        days.put(21, new de.beachboys.aoc2022.Day21());
        days.put(22, new de.beachboys.aoc2022.Day22());
        days.put(23, new de.beachboys.aoc2022.Day23());
        days.put(24, new de.beachboys.aoc2022.Day24());
        days.put(25, new de.beachboys.aoc2022.Day25());
        return days;
    }

    private static Map<Integer, Day> getDays2021() {
        Map<Integer, Day> days = new HashMap<>();
        days.put(1, new de.beachboys.aoc2021.Day01());
        days.put(2, new de.beachboys.aoc2021.Day02());
        days.put(3, new de.beachboys.aoc2021.Day03());
        days.put(4, new de.beachboys.aoc2021.Day04());
        days.put(5, new de.beachboys.aoc2021.Day05());
        days.put(6, new de.beachboys.aoc2021.Day06());
        days.put(7, new de.beachboys.aoc2021.Day07());
        days.put(8, new de.beachboys.aoc2021.Day08());
        days.put(9, new de.beachboys.aoc2021.Day09());
        days.put(10, new de.beachboys.aoc2021.Day10());
        days.put(11, new de.beachboys.aoc2021.Day11());
        days.put(12, new de.beachboys.aoc2021.Day12());
        days.put(13, new de.beachboys.aoc2021.Day13());
        days.put(14, new de.beachboys.aoc2021.Day14());
        days.put(15, new de.beachboys.aoc2021.Day15());
        days.put(16, new de.beachboys.aoc2021.Day16());
        days.put(17, new de.beachboys.aoc2021.Day17());
        days.put(18, new de.beachboys.aoc2021.Day18());
        days.put(19, new de.beachboys.aoc2021.Day19());
        days.put(20, new de.beachboys.aoc2021.Day20());
        days.put(21, new de.beachboys.aoc2021.Day21());
        days.put(22, new de.beachboys.aoc2021.Day22());
        days.put(23, new de.beachboys.aoc2021.Day23());
        days.put(24, new de.beachboys.aoc2021.Day24());
        days.put(25, new de.beachboys.aoc2021.Day25());
        return days;
    }
    private static Map<Integer, Day> getDays2020() {
        Map<Integer, Day> days = new HashMap<>();
        days.put(1, new de.beachboys.aoc2020.Day01());
        days.put(2, new de.beachboys.aoc2020.Day02());
        days.put(3, new de.beachboys.aoc2020.Day03());
        days.put(4, new de.beachboys.aoc2020.Day04());
        days.put(5, new de.beachboys.aoc2020.Day05());
        days.put(6, new de.beachboys.aoc2020.Day06());
        days.put(7, new de.beachboys.aoc2020.Day07());
        days.put(8, new de.beachboys.aoc2020.Day08());
        days.put(9, new de.beachboys.aoc2020.Day09());
        days.put(10, new de.beachboys.aoc2020.Day10());
        days.put(11, new de.beachboys.aoc2020.Day11());
        days.put(12, new de.beachboys.aoc2020.Day12());
        days.put(13, new de.beachboys.aoc2020.Day13());
        days.put(14, new de.beachboys.aoc2020.Day14());
        days.put(15, new de.beachboys.aoc2020.Day15());
        days.put(16, new de.beachboys.aoc2020.Day16());
        days.put(17, new de.beachboys.aoc2020.Day17());
        days.put(18, new de.beachboys.aoc2020.Day18());
        days.put(19, new de.beachboys.aoc2020.Day19());
        days.put(20, new de.beachboys.aoc2020.Day20());
        days.put(21, new de.beachboys.aoc2020.Day21());
        days.put(22, new de.beachboys.aoc2020.Day22());
        days.put(23, new de.beachboys.aoc2020.Day23());
        days.put(24, new de.beachboys.aoc2020.Day24());
        days.put(25, new de.beachboys.aoc2020.Day25());
        return days;
    }

    private static Map<Integer, Day> getDays2019() {
        Map<Integer, Day> days = new HashMap<>();
        days.put(1, new de.beachboys.aoc2019.Day01());
        days.put(2, new de.beachboys.aoc2019.Day02());
        days.put(3, new de.beachboys.aoc2019.Day03());
        days.put(4, new de.beachboys.aoc2019.Day04());
        days.put(5, new de.beachboys.aoc2019.Day05());
        days.put(6, new de.beachboys.aoc2019.Day06());
        days.put(7, new de.beachboys.aoc2019.Day07());
        days.put(8, new de.beachboys.aoc2019.Day08());
        days.put(9, new de.beachboys.aoc2019.Day09());
        days.put(10, new de.beachboys.aoc2019.Day10());
        days.put(11, new de.beachboys.aoc2019.Day11());
        days.put(12, new de.beachboys.aoc2019.Day12());
        days.put(13, new de.beachboys.aoc2019.Day13());
        days.put(14, new de.beachboys.aoc2019.Day14());
        days.put(15, new de.beachboys.aoc2019.Day15());
        days.put(16, new de.beachboys.aoc2019.Day16());
        days.put(17, new de.beachboys.aoc2019.Day17());
        days.put(18, new de.beachboys.aoc2019.Day18());
        days.put(19, new de.beachboys.aoc2019.Day19());
        days.put(20, new de.beachboys.aoc2019.Day20());
        days.put(21, new de.beachboys.aoc2019.Day21());
        days.put(22, new de.beachboys.aoc2019.Day22());
        days.put(23, new de.beachboys.aoc2019.Day23());
        days.put(24, new de.beachboys.aoc2019.Day24());
        days.put(25, new de.beachboys.aoc2019.Day25());
        return days;
    }

    private static Map<Integer, Day> getDays2018() {
        Map<Integer, Day> days = new HashMap<>();
        days.put(1, new de.beachboys.aoc2018.Day01());
        days.put(2, new de.beachboys.aoc2018.Day02());
        days.put(3, new de.beachboys.aoc2018.Day03());
        days.put(4, new de.beachboys.aoc2018.Day04());
        days.put(5, new de.beachboys.aoc2018.Day05());
        days.put(6, new de.beachboys.aoc2018.Day06());
        days.put(7, new de.beachboys.aoc2018.Day07());
        days.put(8, new de.beachboys.aoc2018.Day08());
        days.put(9, new de.beachboys.aoc2018.Day09());
        days.put(10, new de.beachboys.aoc2018.Day10());
        days.put(11, new de.beachboys.aoc2018.Day11());
        days.put(12, new de.beachboys.aoc2018.Day12());
        days.put(13, new de.beachboys.aoc2018.Day13());
        days.put(14, new de.beachboys.aoc2018.Day14());
        days.put(15, new de.beachboys.aoc2018.Day15());
        days.put(16, new de.beachboys.aoc2018.Day16());
        days.put(17, new de.beachboys.aoc2018.Day17());
        days.put(18, new de.beachboys.aoc2018.Day18());
        days.put(19, new de.beachboys.aoc2018.Day19());
        days.put(20, new de.beachboys.aoc2018.Day20());
        days.put(21, new de.beachboys.aoc2018.Day21());
        days.put(22, new de.beachboys.aoc2018.Day22());
        days.put(23, new de.beachboys.aoc2018.Day23());
        days.put(24, new de.beachboys.aoc2018.Day24());
        days.put(25, new de.beachboys.aoc2018.Day25());
        return days;
    }

    private static Map<Integer, Day> getDays2017() {
        Map<Integer, Day> days = new HashMap<>();
        days.put(1, new de.beachboys.aoc2017.Day01());
        days.put(2, new de.beachboys.aoc2017.Day02());
        days.put(3, new de.beachboys.aoc2017.Day03());
        days.put(4, new de.beachboys.aoc2017.Day04());
        days.put(5, new de.beachboys.aoc2017.Day05());
        days.put(6, new de.beachboys.aoc2017.Day06());
        days.put(7, new de.beachboys.aoc2017.Day07());
        days.put(8, new de.beachboys.aoc2017.Day08());
        days.put(9, new de.beachboys.aoc2017.Day09());
        days.put(10, new de.beachboys.aoc2017.Day10());
        days.put(11, new de.beachboys.aoc2017.Day11());
        days.put(12, new de.beachboys.aoc2017.Day12());
        days.put(13, new de.beachboys.aoc2017.Day13());
        days.put(14, new de.beachboys.aoc2017.Day14());
        days.put(15, new de.beachboys.aoc2017.Day15());
        days.put(16, new de.beachboys.aoc2017.Day16());
        days.put(17, new de.beachboys.aoc2017.Day17());
        days.put(18, new de.beachboys.aoc2017.Day18());
        days.put(19, new de.beachboys.aoc2017.Day19());
        days.put(20, new de.beachboys.aoc2017.Day20());
        days.put(21, new de.beachboys.aoc2017.Day21());
        days.put(22, new de.beachboys.aoc2017.Day22());
        days.put(23, new de.beachboys.aoc2017.Day23());
        days.put(24, new de.beachboys.aoc2017.Day24());
        days.put(25, new de.beachboys.aoc2017.Day25());
        return days;
    }

    private static Map<Integer, Day> getDays2016() {
        Map<Integer, Day> days = new HashMap<>();
        days.put(1, new de.beachboys.aoc2016.Day01());
        days.put(2, new de.beachboys.aoc2016.Day02());
        days.put(3, new de.beachboys.aoc2016.Day03());
        days.put(4, new de.beachboys.aoc2016.Day04());
        days.put(5, new de.beachboys.aoc2016.Day05());
        days.put(6, new de.beachboys.aoc2016.Day06());
        days.put(7, new de.beachboys.aoc2016.Day07());
        days.put(8, new de.beachboys.aoc2016.Day08());
        days.put(9, new de.beachboys.aoc2016.Day09());
        days.put(10, new de.beachboys.aoc2016.Day10());
        days.put(11, new de.beachboys.aoc2016.Day11());
        days.put(12, new de.beachboys.aoc2016.Day12());
        days.put(13, new de.beachboys.aoc2016.Day13());
        days.put(14, new de.beachboys.aoc2016.Day14());
        days.put(15, new de.beachboys.aoc2016.Day15());
        days.put(16, new de.beachboys.aoc2016.Day16());
        days.put(17, new de.beachboys.aoc2016.Day17());
        days.put(18, new de.beachboys.aoc2016.Day18());
        days.put(19, new de.beachboys.aoc2016.Day19());
        days.put(20, new de.beachboys.aoc2016.Day20());
        days.put(21, new de.beachboys.aoc2016.Day21());
        days.put(22, new de.beachboys.aoc2016.Day22());
        days.put(23, new de.beachboys.aoc2016.Day23());
        days.put(24, new de.beachboys.aoc2016.Day24());
        days.put(25, new de.beachboys.aoc2016.Day25());
        return days;
    }

    private static Map<Integer, Day> getDays2015() {
        Map<Integer, Day> days = new HashMap<>();
        days.put(1, new de.beachboys.aoc2015.Day01());
        days.put(2, new de.beachboys.aoc2015.Day02());
        days.put(3, new de.beachboys.aoc2015.Day03());
        days.put(4, new de.beachboys.aoc2015.Day04());
        days.put(5, new de.beachboys.aoc2015.Day05());
        days.put(6, new de.beachboys.aoc2015.Day06());
        days.put(7, new de.beachboys.aoc2015.Day07());
        days.put(8, new de.beachboys.aoc2015.Day08());
        days.put(9, new de.beachboys.aoc2015.Day09());
        days.put(10, new de.beachboys.aoc2015.Day10());
        days.put(11, new de.beachboys.aoc2015.Day11());
        days.put(12, new de.beachboys.aoc2015.Day12());
        days.put(13, new de.beachboys.aoc2015.Day13());
        days.put(14, new de.beachboys.aoc2015.Day14());
        days.put(15, new de.beachboys.aoc2015.Day15());
        days.put(16, new de.beachboys.aoc2015.Day16());
        days.put(17, new de.beachboys.aoc2015.Day17());
        days.put(18, new de.beachboys.aoc2015.Day18());
        days.put(19, new de.beachboys.aoc2015.Day19());
        days.put(20, new de.beachboys.aoc2015.Day20());
        days.put(21, new de.beachboys.aoc2015.Day21());
        days.put(22, new de.beachboys.aoc2015.Day22());
        days.put(23, new de.beachboys.aoc2015.Day23());
        days.put(24, new de.beachboys.aoc2015.Day24());
        days.put(25, new de.beachboys.aoc2015.Day25());
        return days;
    }

}
