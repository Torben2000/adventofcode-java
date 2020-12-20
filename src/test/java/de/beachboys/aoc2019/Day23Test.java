package de.beachboys.aoc2019;

import de.beachboys.Day;
import de.beachboys.DayTest;
import de.beachboys.IOHelper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

public class Day23Test extends DayTest {

    private final Day day = new Day23();

    private static Stream<Arguments> provideTestDataForPart1() {
        return Stream.of(
                Arguments.of(List.of("3,62,1001,62,11,10,109,2263,105,1,0,1117,1230,1360,1554,1261,1490,2166,699,827,1453,2098,1655,2065,761,1294,1158,1088,930,975,862,1521,1989,1857,1053,1826,2199,792,668,1585,1758,1727,730,1892,2232,1696,1197,1793,1325,1422,1620,1927,571,1020,893,1958,635,2129,2026,606,1393,0,0,0,0,0,0,0,0,0,0,0,0,3,64,1008,64,-1,62,1006,62,88,1006,61,170,1105,1,73,3,65,21001,64,0,1,21002,66,1,2,21101,0,105,0,1105,1,436,1201,1,-1,64,1007,64,0,62,1005,62,73,7,64,67,62,1006,62,73,1002,64,2,132,1,132,68,132,1001,0,0,62,1001,132,1,140,8,0,65,63,2,63,62,62,1005,62,73,1002,64,2,161,1,161,68,161,1102,1,1,0,1001,161,1,169,102,1,65,0,1102,1,1,61,1102,1,0,63,7,63,67,62,1006,62,203,1002,63,2,194,1,68,194,194,1006,0,73,1001,63,1,63,1105,1,178,21102,210,1,0,106,0,69,1201,1,0,70,1102,1,0,63,7,63,71,62,1006,62,250,1002,63,2,234,1,72,234,234,4,0,101,1,234,240,4,0,4,70,1001,63,1,63,1105,1,218,1105,1,73,109,4,21102,0,1,-3,21101,0,0,-2,20207,-2,67,-1,1206,-1,293,1202,-2,2,283,101,1,283,283,1,68,283,283,22001,0,-3,-3,21201,-2,1,-2,1106,0,263,22102,1,-3,-3,109,-4,2105,1,0,109,4,21102,1,1,-3,21101,0,0,-2,20207,-2,67,-1,1206,-1,342,1202,-2,2,332,101,1,332,332,1,68,332,332,22002,0,-3,-3,21201,-2,1,-2,1106,0,312,22101,0,-3,-3,109,-4,2106,0,0,109,1,101,1,68,359,20101,0,0,1,101,3,68,367,20102,1,0,2,21102,1,376,0,1106,0,436,21201,1,0,0,109,-1,2105,1,0,1,2,4,8,16,32,64,128,256,512,1024,2048,4096,8192,16384,32768,65536,131072,262144,524288,1048576,2097152,4194304,8388608,16777216,33554432,67108864,134217728,268435456,536870912,1073741824,2147483648,4294967296,8589934592,17179869184,34359738368,68719476736,137438953472,274877906944,549755813888,1099511627776,2199023255552,4398046511104,8796093022208,17592186044416,35184372088832,70368744177664,140737488355328,281474976710656,562949953421312,1125899906842624,109,8,21202,-6,10,-5,22207,-7,-5,-5,1205,-5,521,21102,1,0,-4,21101,0,0,-3,21101,0,51,-2,21201,-2,-1,-2,1201,-2,385,471,20101,0,0,-1,21202,-3,2,-3,22207,-7,-1,-5,1205,-5,496,21201,-3,1,-3,22102,-1,-1,-5,22201,-7,-5,-7,22207,-3,-6,-5,1205,-5,515,22102,-1,-6,-5,22201,-3,-5,-3,22201,-1,-4,-4,1205,-2,461,1106,0,547,21101,-1,0,-4,21202,-6,-1,-6,21207,-7,0,-5,1205,-5,547,22201,-7,-6,-7,21201,-4,1,-4,1105,1,529,22101,0,-4,-7,109,-8,2105,1,0,109,1,101,1,68,564,20101,0,0,0,109,-1,2106,0,0,1102,1,33569,66,1102,1,1,67,1102,598,1,68,1102,1,556,69,1102,3,1,71,1102,600,1,72,1106,0,73,1,5,43,138543,43,184724,11,125564,1102,11783,1,66,1102,1,1,67,1102,1,633,68,1101,556,0,69,1102,0,1,71,1101,635,0,72,1105,1,73,1,1462,1101,62969,0,66,1101,0,2,67,1101,662,0,68,1101,0,351,69,1101,1,0,71,1101,666,0,72,1105,1,73,0,0,0,0,255,53269,1101,50591,0,66,1102,1,1,67,1101,695,0,68,1102,1,556,69,1101,1,0,71,1101,0,697,72,1105,1,73,1,1219,47,86285,1101,0,72551,66,1102,1,1,67,1101,0,726,68,1101,556,0,69,1102,1,1,71,1102,728,1,72,1106,0,73,1,173,46,5342,1102,61363,1,66,1101,0,1,67,1102,1,757,68,1102,1,556,69,1101,1,0,71,1101,759,0,72,1106,0,73,1,-603,32,23167,1101,104179,0,66,1101,0,1,67,1102,1,788,68,1102,556,1,69,1101,0,1,71,1102,1,790,72,1105,1,73,1,19,46,2671,1102,6073,1,66,1101,3,0,67,1102,819,1,68,1102,302,1,69,1101,0,1,71,1102,825,1,72,1105,1,73,0,0,0,0,0,0,12,82982,1101,12491,0,66,1101,3,0,67,1102,1,854,68,1101,0,302,69,1101,0,1,71,1102,1,860,72,1105,1,73,0,0,0,0,0,0,23,292389,1101,24359,0,66,1101,0,1,67,1101,0,889,68,1101,0,556,69,1101,0,1,71,1101,0,891,72,1106,0,73,1,383,28,28759,1102,46181,1,66,1102,4,1,67,1102,1,920,68,1102,1,302,69,1101,1,0,71,1101,0,928,72,1105,1,73,0,0,0,0,0,0,0,0,11,94173,1102,1,61981,66,1101,1,0,67,1102,957,1,68,1102,556,1,69,1101,8,0,71,1101,0,959,72,1106,0,73,1,2,25,64067,42,63598,15,225436,47,34514,36,152962,26,6073,11,156955,11,188346,1102,1,42451,66,1101,0,1,67,1102,1002,1,68,1101,556,0,69,1102,8,1,71,1102,1,1004,72,1105,1,73,1,1,39,61583,46,8013,37,183154,28,86277,4,8914,29,250197,47,17257,8,37473,1101,0,31799,66,1101,2,0,67,1102,1047,1,68,1101,0,302,69,1101,0,1,71,1102,1,1051,72,1105,1,73,0,0,0,0,15,281795,1102,1,97463,66,1101,3,0,67,1101,1080,0,68,1102,1,302,69,1102,1,1,71,1102,1,1086,72,1105,1,73,0,0,0,0,0,0,9,78724,1102,1,51157,66,1101,1,0,67,1101,0,1115,68,1102,556,1,69,1102,0,1,71,1101,1117,0,72,1106,0,73,1,1243,1101,53269,0,66,1101,1,0,67,1102,1,1144,68,1102,1,556,69,1102,6,1,71,1101,0,1146,72,1106,0,73,1,18277,12,41491,23,97463,23,194926,22,76079,22,152158,22,228237,1102,1,56359,66,1101,5,0,67,1101,0,1185,68,1102,302,1,69,1102,1,1,71,1101,0,1195,72,1106,0,73,0,0,0,0,0,0,0,0,0,0,9,19681,1101,0,67699,66,1101,0,1,67,1101,0,1224,68,1101,556,0,69,1101,0,2,71,1102,1,1226,72,1106,0,73,1,11,39,123166,15,56359,1102,1,62981,66,1101,1,0,67,1102,1257,1,68,1102,556,1,69,1102,1,1,71,1102,1259,1,72,1106,0,73,1,-66,8,24982,1102,4457,1,66,1101,2,0,67,1101,1288,0,68,1102,302,1,69,1102,1,1,71,1101,1292,0,72,1105,1,73,0,0,0,0,29,166798,1102,1,52579,66,1102,1,1,67,1102,1321,1,68,1101,0,556,69,1102,1,1,71,1101,1323,0,72,1106,0,73,1,95923,4,4457,1101,0,91577,66,1102,3,1,67,1102,1352,1,68,1101,302,0,69,1102,1,1,71,1102,1,1358,72,1106,0,73,0,0,0,0,0,0,21,14914,1102,87961,1,66,1102,1,1,67,1102,1387,1,68,1102,1,556,69,1101,0,2,71,1101,1389,0,72,1106,0,73,1,3,29,83399,47,69028,1102,1,87083,66,1102,1,1,67,1102,1,1420,68,1102,1,556,69,1102,0,1,71,1102,1422,1,72,1106,0,73,1,1226,1101,0,99371,66,1102,1,1,67,1102,1,1449,68,1101,0,556,69,1101,0,1,71,1101,1451,0,72,1105,1,73,1,47,46,10684,1102,19681,1,66,1101,0,4,67,1101,1480,0,68,1101,253,0,69,1102,1,1,71,1101,1488,0,72,1105,1,73,0,0,0,0,0,0,0,0,45,62969,1102,53881,1,66,1102,1,1,67,1102,1,1517,68,1102,556,1,69,1102,1,1,71,1101,0,1519,72,1106,0,73,1,307,47,51771,1102,37579,1,66,1101,0,1,67,1102,1548,1,68,1102,556,1,69,1102,2,1,71,1102,1,1550,72,1106,0,73,1,10,43,92362,11,31391,1101,80779,0,66,1102,1,1,67,1102,1,1581,68,1102,1,556,69,1102,1,1,71,1102,1,1583,72,1106,0,73,1,5867,39,184749,1101,0,28759,66,1101,0,3,67,1101,0,1612,68,1102,1,302,69,1102,1,1,71,1102,1618,1,72,1106,0,73,0,0,0,0,0,0,21,7457,1102,1,61583,66,1101,0,3,67,1101,1647,0,68,1101,302,0,69,1102,1,1,71,1101,0,1653,72,1105,1,73,0,0,0,0,0,0,32,69501,1101,0,31391,66,1102,1,6,67,1102,1682,1,68,1101,302,0,69,1101,0,1,71,1102,1,1694,72,1105,1,73,0,0,0,0,0,0,0,0,0,0,0,0,45,125938,1102,1,52553,66,1102,1,1,67,1101,1723,0,68,1102,556,1,69,1102,1,1,71,1101,0,1725,72,1106,0,73,1,193,15,112718,1101,0,97157,66,1101,1,0,67,1101,1754,0,68,1102,556,1,69,1102,1,1,71,1102,1,1756,72,1106,0,73,1,125,43,46181,1102,83399,1,66,1102,1,3,67,1102,1785,1,68,1101,302,0,69,1102,1,1,71,1102,1,1791,72,1105,1,73,0,0,0,0,0,0,21,22371,1101,76481,0,66,1101,2,0,67,1101,0,1820,68,1102,1,302,69,1101,0,1,71,1101,1824,0,72,1106,0,73,0,0,0,0,26,18219,1102,1,25453,66,1102,1,1,67,1102,1,1853,68,1102,1,556,69,1101,1,0,71,1102,1855,1,72,1105,1,73,1,739,28,57518,1101,0,76079,66,1102,1,3,67,1102,1,1884,68,1102,302,1,69,1101,1,0,71,1101,1890,0,72,1106,0,73,0,0,0,0,0,0,9,59043,1102,1,23167,66,1101,3,0,67,1101,1919,0,68,1101,253,0,69,1102,1,1,71,1101,1925,0,72,1106,0,73,0,0,0,0,0,0,25,128134,1101,0,102587,66,1102,1,1,67,1101,0,1954,68,1102,1,556,69,1101,0,1,71,1101,1956,0,72,1106,0,73,1,4339,37,274731,1102,1,82759,66,1102,1,1,67,1102,1,1985,68,1102,556,1,69,1101,0,1,71,1102,1,1987,72,1105,1,73,1,103,26,12146,1102,1,7457,66,1101,0,4,67,1101,2016,0,68,1102,253,1,69,1101,0,1,71,1102,2024,1,72,1105,1,73,0,0,0,0,0,0,0,0,36,76481,1102,17257,1,66,1101,0,5,67,1102,1,2053,68,1102,1,302,69,1102,1,1,71,1102,2063,1,72,1105,1,73,0,0,0,0,0,0,0,0,0,0,21,29828,1102,41491,1,66,1102,2,1,67,1101,0,2092,68,1101,302,0,69,1102,1,1,71,1102,1,2096,72,1106,0,73,0,0,0,0,9,39362,1102,27277,1,66,1102,1,1,67,1101,0,2125,68,1101,0,556,69,1101,1,0,71,1101,0,2127,72,1106,0,73,1,-37,37,91577,1102,1,2671,66,1101,4,0,67,1101,2156,0,68,1101,0,302,69,1102,1,1,71,1101,2164,0,72,1105,1,73,0,0,0,0,0,0,0,0,32,46334,1101,0,1163,66,1101,0,1,67,1101,2193,0,68,1102,556,1,69,1101,0,2,71,1101,2195,0,72,1105,1,73,1,521,15,169077,8,12491,1101,64067,0,66,1101,0,2,67,1101,2226,0,68,1101,302,0,69,1102,1,1,71,1102,1,2230,72,1106,0,73,0,0,0,0,42,31799,1102,103079,1,66,1102,1,1,67,1101,0,2259,68,1101,0,556,69,1102,1,1,71,1101,2261,0,72,1106,0,73,1,160,11,62782"), 16549, null)
        );
    }

    private static Stream<Arguments> provideTestDataForPart2() {
        return Stream.of(
                Arguments.of(List.of("3,62,1001,62,11,10,109,2263,105,1,0,1117,1230,1360,1554,1261,1490,2166,699,827,1453,2098,1655,2065,761,1294,1158,1088,930,975,862,1521,1989,1857,1053,1826,2199,792,668,1585,1758,1727,730,1892,2232,1696,1197,1793,1325,1422,1620,1927,571,1020,893,1958,635,2129,2026,606,1393,0,0,0,0,0,0,0,0,0,0,0,0,3,64,1008,64,-1,62,1006,62,88,1006,61,170,1105,1,73,3,65,21001,64,0,1,21002,66,1,2,21101,0,105,0,1105,1,436,1201,1,-1,64,1007,64,0,62,1005,62,73,7,64,67,62,1006,62,73,1002,64,2,132,1,132,68,132,1001,0,0,62,1001,132,1,140,8,0,65,63,2,63,62,62,1005,62,73,1002,64,2,161,1,161,68,161,1102,1,1,0,1001,161,1,169,102,1,65,0,1102,1,1,61,1102,1,0,63,7,63,67,62,1006,62,203,1002,63,2,194,1,68,194,194,1006,0,73,1001,63,1,63,1105,1,178,21102,210,1,0,106,0,69,1201,1,0,70,1102,1,0,63,7,63,71,62,1006,62,250,1002,63,2,234,1,72,234,234,4,0,101,1,234,240,4,0,4,70,1001,63,1,63,1105,1,218,1105,1,73,109,4,21102,0,1,-3,21101,0,0,-2,20207,-2,67,-1,1206,-1,293,1202,-2,2,283,101,1,283,283,1,68,283,283,22001,0,-3,-3,21201,-2,1,-2,1106,0,263,22102,1,-3,-3,109,-4,2105,1,0,109,4,21102,1,1,-3,21101,0,0,-2,20207,-2,67,-1,1206,-1,342,1202,-2,2,332,101,1,332,332,1,68,332,332,22002,0,-3,-3,21201,-2,1,-2,1106,0,312,22101,0,-3,-3,109,-4,2106,0,0,109,1,101,1,68,359,20101,0,0,1,101,3,68,367,20102,1,0,2,21102,1,376,0,1106,0,436,21201,1,0,0,109,-1,2105,1,0,1,2,4,8,16,32,64,128,256,512,1024,2048,4096,8192,16384,32768,65536,131072,262144,524288,1048576,2097152,4194304,8388608,16777216,33554432,67108864,134217728,268435456,536870912,1073741824,2147483648,4294967296,8589934592,17179869184,34359738368,68719476736,137438953472,274877906944,549755813888,1099511627776,2199023255552,4398046511104,8796093022208,17592186044416,35184372088832,70368744177664,140737488355328,281474976710656,562949953421312,1125899906842624,109,8,21202,-6,10,-5,22207,-7,-5,-5,1205,-5,521,21102,1,0,-4,21101,0,0,-3,21101,0,51,-2,21201,-2,-1,-2,1201,-2,385,471,20101,0,0,-1,21202,-3,2,-3,22207,-7,-1,-5,1205,-5,496,21201,-3,1,-3,22102,-1,-1,-5,22201,-7,-5,-7,22207,-3,-6,-5,1205,-5,515,22102,-1,-6,-5,22201,-3,-5,-3,22201,-1,-4,-4,1205,-2,461,1106,0,547,21101,-1,0,-4,21202,-6,-1,-6,21207,-7,0,-5,1205,-5,547,22201,-7,-6,-7,21201,-4,1,-4,1105,1,529,22101,0,-4,-7,109,-8,2105,1,0,109,1,101,1,68,564,20101,0,0,0,109,-1,2106,0,0,1102,1,33569,66,1102,1,1,67,1102,598,1,68,1102,1,556,69,1102,3,1,71,1102,600,1,72,1106,0,73,1,5,43,138543,43,184724,11,125564,1102,11783,1,66,1102,1,1,67,1102,1,633,68,1101,556,0,69,1102,0,1,71,1101,635,0,72,1105,1,73,1,1462,1101,62969,0,66,1101,0,2,67,1101,662,0,68,1101,0,351,69,1101,1,0,71,1101,666,0,72,1105,1,73,0,0,0,0,255,53269,1101,50591,0,66,1102,1,1,67,1101,695,0,68,1102,1,556,69,1101,1,0,71,1101,0,697,72,1105,1,73,1,1219,47,86285,1101,0,72551,66,1102,1,1,67,1101,0,726,68,1101,556,0,69,1102,1,1,71,1102,728,1,72,1106,0,73,1,173,46,5342,1102,61363,1,66,1101,0,1,67,1102,1,757,68,1102,1,556,69,1101,1,0,71,1101,759,0,72,1106,0,73,1,-603,32,23167,1101,104179,0,66,1101,0,1,67,1102,1,788,68,1102,556,1,69,1101,0,1,71,1102,1,790,72,1105,1,73,1,19,46,2671,1102,6073,1,66,1101,3,0,67,1102,819,1,68,1102,302,1,69,1101,0,1,71,1102,825,1,72,1105,1,73,0,0,0,0,0,0,12,82982,1101,12491,0,66,1101,3,0,67,1102,1,854,68,1101,0,302,69,1101,0,1,71,1102,1,860,72,1105,1,73,0,0,0,0,0,0,23,292389,1101,24359,0,66,1101,0,1,67,1101,0,889,68,1101,0,556,69,1101,0,1,71,1101,0,891,72,1106,0,73,1,383,28,28759,1102,46181,1,66,1102,4,1,67,1102,1,920,68,1102,1,302,69,1101,1,0,71,1101,0,928,72,1105,1,73,0,0,0,0,0,0,0,0,11,94173,1102,1,61981,66,1101,1,0,67,1102,957,1,68,1102,556,1,69,1101,8,0,71,1101,0,959,72,1106,0,73,1,2,25,64067,42,63598,15,225436,47,34514,36,152962,26,6073,11,156955,11,188346,1102,1,42451,66,1101,0,1,67,1102,1002,1,68,1101,556,0,69,1102,8,1,71,1102,1,1004,72,1105,1,73,1,1,39,61583,46,8013,37,183154,28,86277,4,8914,29,250197,47,17257,8,37473,1101,0,31799,66,1101,2,0,67,1102,1047,1,68,1101,0,302,69,1101,0,1,71,1102,1,1051,72,1105,1,73,0,0,0,0,15,281795,1102,1,97463,66,1101,3,0,67,1101,1080,0,68,1102,1,302,69,1102,1,1,71,1102,1,1086,72,1105,1,73,0,0,0,0,0,0,9,78724,1102,1,51157,66,1101,1,0,67,1101,0,1115,68,1102,556,1,69,1102,0,1,71,1101,1117,0,72,1106,0,73,1,1243,1101,53269,0,66,1101,1,0,67,1102,1,1144,68,1102,1,556,69,1102,6,1,71,1101,0,1146,72,1106,0,73,1,18277,12,41491,23,97463,23,194926,22,76079,22,152158,22,228237,1102,1,56359,66,1101,5,0,67,1101,0,1185,68,1102,302,1,69,1102,1,1,71,1101,0,1195,72,1106,0,73,0,0,0,0,0,0,0,0,0,0,9,19681,1101,0,67699,66,1101,0,1,67,1101,0,1224,68,1101,556,0,69,1101,0,2,71,1102,1,1226,72,1106,0,73,1,11,39,123166,15,56359,1102,1,62981,66,1101,1,0,67,1102,1257,1,68,1102,556,1,69,1102,1,1,71,1102,1259,1,72,1106,0,73,1,-66,8,24982,1102,4457,1,66,1101,2,0,67,1101,1288,0,68,1102,302,1,69,1102,1,1,71,1101,1292,0,72,1105,1,73,0,0,0,0,29,166798,1102,1,52579,66,1102,1,1,67,1102,1321,1,68,1101,0,556,69,1102,1,1,71,1101,1323,0,72,1106,0,73,1,95923,4,4457,1101,0,91577,66,1102,3,1,67,1102,1352,1,68,1101,302,0,69,1102,1,1,71,1102,1,1358,72,1106,0,73,0,0,0,0,0,0,21,14914,1102,87961,1,66,1102,1,1,67,1102,1387,1,68,1102,1,556,69,1101,0,2,71,1101,1389,0,72,1106,0,73,1,3,29,83399,47,69028,1102,1,87083,66,1102,1,1,67,1102,1,1420,68,1102,1,556,69,1102,0,1,71,1102,1422,1,72,1106,0,73,1,1226,1101,0,99371,66,1102,1,1,67,1102,1,1449,68,1101,0,556,69,1101,0,1,71,1101,1451,0,72,1105,1,73,1,47,46,10684,1102,19681,1,66,1101,0,4,67,1101,1480,0,68,1101,253,0,69,1102,1,1,71,1101,1488,0,72,1105,1,73,0,0,0,0,0,0,0,0,45,62969,1102,53881,1,66,1102,1,1,67,1102,1,1517,68,1102,556,1,69,1102,1,1,71,1101,0,1519,72,1106,0,73,1,307,47,51771,1102,37579,1,66,1101,0,1,67,1102,1548,1,68,1102,556,1,69,1102,2,1,71,1102,1,1550,72,1106,0,73,1,10,43,92362,11,31391,1101,80779,0,66,1102,1,1,67,1102,1,1581,68,1102,1,556,69,1102,1,1,71,1102,1,1583,72,1106,0,73,1,5867,39,184749,1101,0,28759,66,1101,0,3,67,1101,0,1612,68,1102,1,302,69,1102,1,1,71,1102,1618,1,72,1106,0,73,0,0,0,0,0,0,21,7457,1102,1,61583,66,1101,0,3,67,1101,1647,0,68,1101,302,0,69,1102,1,1,71,1101,0,1653,72,1105,1,73,0,0,0,0,0,0,32,69501,1101,0,31391,66,1102,1,6,67,1102,1682,1,68,1101,302,0,69,1101,0,1,71,1102,1,1694,72,1105,1,73,0,0,0,0,0,0,0,0,0,0,0,0,45,125938,1102,1,52553,66,1102,1,1,67,1101,1723,0,68,1102,556,1,69,1102,1,1,71,1101,0,1725,72,1106,0,73,1,193,15,112718,1101,0,97157,66,1101,1,0,67,1101,1754,0,68,1102,556,1,69,1102,1,1,71,1102,1,1756,72,1106,0,73,1,125,43,46181,1102,83399,1,66,1102,1,3,67,1102,1785,1,68,1101,302,0,69,1102,1,1,71,1102,1,1791,72,1105,1,73,0,0,0,0,0,0,21,22371,1101,76481,0,66,1101,2,0,67,1101,0,1820,68,1102,1,302,69,1101,0,1,71,1101,1824,0,72,1106,0,73,0,0,0,0,26,18219,1102,1,25453,66,1102,1,1,67,1102,1,1853,68,1102,1,556,69,1101,1,0,71,1102,1855,1,72,1105,1,73,1,739,28,57518,1101,0,76079,66,1102,1,3,67,1102,1,1884,68,1102,302,1,69,1101,1,0,71,1101,1890,0,72,1106,0,73,0,0,0,0,0,0,9,59043,1102,1,23167,66,1101,3,0,67,1101,1919,0,68,1101,253,0,69,1102,1,1,71,1101,1925,0,72,1106,0,73,0,0,0,0,0,0,25,128134,1101,0,102587,66,1102,1,1,67,1101,0,1954,68,1102,1,556,69,1101,0,1,71,1101,1956,0,72,1106,0,73,1,4339,37,274731,1102,1,82759,66,1102,1,1,67,1102,1,1985,68,1102,556,1,69,1101,0,1,71,1102,1,1987,72,1105,1,73,1,103,26,12146,1102,1,7457,66,1101,0,4,67,1101,2016,0,68,1102,253,1,69,1101,0,1,71,1102,2024,1,72,1105,1,73,0,0,0,0,0,0,0,0,36,76481,1102,17257,1,66,1101,0,5,67,1102,1,2053,68,1102,1,302,69,1102,1,1,71,1102,2063,1,72,1105,1,73,0,0,0,0,0,0,0,0,0,0,21,29828,1102,41491,1,66,1102,2,1,67,1101,0,2092,68,1101,302,0,69,1102,1,1,71,1102,1,2096,72,1106,0,73,0,0,0,0,9,39362,1102,27277,1,66,1102,1,1,67,1101,0,2125,68,1101,0,556,69,1101,1,0,71,1101,0,2127,72,1106,0,73,1,-37,37,91577,1102,1,2671,66,1101,4,0,67,1101,2156,0,68,1101,0,302,69,1102,1,1,71,1101,2164,0,72,1105,1,73,0,0,0,0,0,0,0,0,32,46334,1101,0,1163,66,1101,0,1,67,1101,2193,0,68,1102,556,1,69,1101,0,2,71,1101,2195,0,72,1105,1,73,1,521,15,169077,8,12491,1101,64067,0,66,1101,0,2,67,1101,2226,0,68,1101,302,0,69,1102,1,1,71,1102,1,2230,72,1106,0,73,0,0,0,0,42,31799,1102,103079,1,66,1102,1,1,67,1101,0,2259,68,1101,0,556,69,1102,1,1,71,1101,2261,0,72,1106,0,73,1,160,11,62782"), 11462, null)
        );
    }

    @ParameterizedTest
    @MethodSource("provideTestDataForPart1")
    public void testPart1(List<String> input, Object expected, IOHelper io) {
        testPart1(this.day, input, expected, io);
    }

    @ParameterizedTest
    @MethodSource("provideTestDataForPart2")
    public void testPart2(List<String> input, Object expected, IOHelper io) {
        testPart2(this.day, input, expected, io);
    }

}