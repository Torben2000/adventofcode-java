package de.beachboys.ec2025;

import de.beachboys.Quest;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Quest02 extends Quest {

    @Override
    public Object part1(List<String> input) {
        Tuple2<Long, Long> a = parseInput(input);
        Tuple2<Long, Long> res = Tuple.tuple(0L, 0L);
        for (int j = 0; j < 3; j++) {
            res = mul(res, res);
            res = div(res, Tuple.tuple(10L, 10L));
            res = add(res, a);
        }
        return "[" + res.v1 + "," + res.v2 + "]";
    }

     @Override
    public Object part2(List<String> input) {
        return runPart2And3(input, 10);
    }

    @Override
    public Object part3(List<String> input) {
        return runPart2And3(input, 1);
    }

    private int runPart2And3(List<String> input, int stepSize) {
        int result = 0;
        Tuple2<Long, Long> a = parseInput(input);
        for (long xOffSet = 0; xOffSet <= 1000; xOffSet += stepSize) {
            for (long yOffset = 0; yOffset <= 1000; yOffset += stepSize) {
                boolean engrave = true;
                Tuple2<Long, Long> currentPos = add(a, Tuple.tuple(xOffSet, yOffset));
                Tuple2<Long, Long> res = Tuple.tuple(0L, 0L);
                for (int i = 0; i < 100; i++) {
                    res = mul(res, res);
                    res = div(res, Tuple.tuple(100000L, 100000L));
                    res = add(res, currentPos);
                    if (res.v1 < -1000000 || res.v1 > 1000000 || res.v2 < -1000000 || res.v2 > 1000000) {
                        engrave = false;
                        break;
                    }
                }
                if (engrave) {
                    result++;
                }
            }
        }
        return result;
    }

    private Tuple2<Long, Long> add(Tuple2<Long, Long> num1, Tuple2<Long, Long> num2) {
        return Tuple.tuple(num1.v1 + num2.v1, num1.v2 + num2.v2);
    }

    private Tuple2<Long, Long> mul(Tuple2<Long, Long> num1, Tuple2<Long, Long> num2) {
        return Tuple.tuple(num1.v1 * num2.v1 - num1.v2 * num2.v2, num1.v1 * num2.v2 + num1.v2 * num2.v1);
    }

    private Tuple2<Long, Long> div(Tuple2<Long, Long> num1, Tuple2<Long, Long> num2) {
        return Tuple.tuple(num1.v1 / num2.v1, num1.v2 / num2.v2);
    }

    private Tuple2<Long, Long> parseInput(List<String> input) {
        Pattern inputPattern = Pattern.compile("([A-z]+)=\\[(-?[0-9]+),(-?[0-9]+)]");
        Matcher m = inputPattern.matcher(input.getFirst());
        if (m.matches()) {
            return Tuple.tuple(Long.parseLong(m.group(2)), Long.parseLong(m.group(3)));

        }
        throw new IllegalArgumentException();
    }
}
