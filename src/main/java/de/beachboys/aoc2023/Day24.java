package de.beachboys.aoc2023;

import com.microsoft.z3.*;
import de.beachboys.Day;
import de.beachboys.MixedFraction;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;
import org.jooq.lambda.tuple.Tuple3;

import java.util.ArrayList;
import java.util.List;

public class Day24 extends Day {

    private List<Tuple2<Tuple3<Long, Long, Long>, Tuple3<Long, Long, Long>>> hailstones;

    public Object part1(List<String> input) {
        parseInput(input);
        long min = Util.getLongValueFromUser("min", 200000000000000L, io);
        long max = Util.getLongValueFromUser("max", 400000000000000L, io);

        long result = 0;
        for (int i = 0; i < hailstones.size()-1; i++) {
            for (int j = i+1; j < hailstones.size(); j++) {
                Tuple2<Tuple3<Long, Long, Long>, Tuple3<Long, Long, Long>> hailstone1 = hailstones.get(i);
                Tuple2<Tuple3<Long, Long, Long>, Tuple3<Long, Long, Long>> hailstone2 = hailstones.get(j);

                // p1+t1*v1=p2+t2*v2 => v1*t1-v2*t2=p2-p1
                List<MixedFraction> solution = Util.solveSystemOfLinearEquations(List.of(
                        Tuple.tuple(List.of(hailstone1.v2.v1, -1L * hailstone2.v2.v1), hailstone2.v1.v1 - hailstone1.v1.v1),
                        Tuple.tuple(List.of(hailstone1.v2.v2, -1L * hailstone2.v2.v2), hailstone2.v1.v2 - hailstone1.v1.v2)));

                if (solution != null && solution.getFirst().getIntegralPartLong() >= 0 && solution.getLast().getIntegralPartLong() >= 0) {
                    MixedFraction t1 = solution.getFirst();
                    double x = hailstone1.v1.v1 + t1.getValueDouble() * hailstone1.v2.v1;
                    double y = hailstone1.v1.v2 + t1.getValueDouble() * hailstone1.v2.v2;
                    if (x >= min && x <= max && y >= min && y <= max) {
                        result++;
                    }
                }
            }
        }
        return result;
    }

    public Object part2(List<String> input) {
        parseInput(input);

        Context z3Context = new Context();
        IntExpr startPositionXVar = z3Context.mkIntConst("x");
        IntExpr startPositionYVar = z3Context.mkIntConst("y");
        IntExpr startPositionZVar = z3Context.mkIntConst("z");
        IntExpr velocityXVar = z3Context.mkIntConst("vx");
        IntExpr velocityYVar = z3Context.mkIntConst("vy");
        IntExpr velocityZVar = z3Context.mkIntConst("vz");
        Solver z3Solver = z3Context.mkSolver();

        int numVariables = 6;
        int numEquations = 0;
        for (int i = 0; numEquations < numVariables; i++) {
            IntExpr timeVar = z3Context.mkIntConst("t" + i);
            Tuple2<Tuple3<Long, Long, Long>, Tuple3<Long, Long, Long>> hailstone = hailstones.get(i);
            z3Solver.add(getEquationForDimension(z3Context, startPositionXVar, velocityXVar, timeVar, hailstone.v1.v1, hailstone.v2.v1));
            z3Solver.add(getEquationForDimension(z3Context, startPositionYVar, velocityYVar, timeVar, hailstone.v1.v2, hailstone.v2.v2));
            z3Solver.add(getEquationForDimension(z3Context, startPositionZVar, velocityZVar, timeVar, hailstone.v1.v3, hailstone.v2.v3));
            numVariables += 1;
            numEquations += 3;
        }

        if (Status.SATISFIABLE.equals(z3Solver.check())) {
            Model z3Model = z3Solver.getModel();

            // just to be sure, won't change the result
            verifyAllHailstones(z3Model.eval(startPositionXVar, false), z3Model.eval(startPositionYVar, false), z3Model.eval(startPositionZVar, false),
                    z3Model.eval(velocityXVar, false), z3Model.eval(velocityYVar, false), z3Model.eval(velocityZVar, false));

            return z3Model.eval(z3Context.mkAdd(startPositionXVar, startPositionYVar, startPositionZVar), false);
        }
        throw new IllegalArgumentException();
    }

    private void verifyAllHailstones(Expr<IntSort> x, Expr<IntSort> y, Expr<IntSort> z, Expr<IntSort> vx, Expr<IntSort> vy, Expr<IntSort> vz) {
        Context z3Context = new Context();
        IntNum startPositionXVar = z3Context.mkInt(x.toString());
        IntNum startPositionYVar = z3Context.mkInt(y.toString());
        IntNum startPositionZVar = z3Context.mkInt(z.toString());
        IntNum velocityXVar = z3Context.mkInt(vx.toString());
        IntNum velocityYVar = z3Context.mkInt(vy.toString());
        IntNum velocityZVar = z3Context.mkInt(vz.toString());
        Solver z3Solver = z3Context.mkSolver();
        for (int i = 0; i < hailstones.size(); i++) {
            IntExpr timeVar = z3Context.mkIntConst("t" + i);
            Tuple2<Tuple3<Long, Long, Long>, Tuple3<Long, Long, Long>> hailstone = hailstones.get(i);
            Status res1 = z3Solver.check(getValidationEquationForDimension(z3Context, startPositionXVar, velocityXVar, timeVar, hailstone.v1.v1, hailstone.v2.v1));
            Status res2 = z3Solver.check(getValidationEquationForDimension(z3Context, startPositionYVar, velocityYVar, timeVar, hailstone.v1.v2, hailstone.v2.v2));
            Status res3 = z3Solver.check(getValidationEquationForDimension(z3Context, startPositionZVar, velocityZVar, timeVar, hailstone.v1.v3, hailstone.v2.v3));
            if (!(Status.SATISFIABLE.equals(res1) && Status.SATISFIABLE.equals(res2) && Status.SATISFIABLE.equals(res3))) {
                throw new IllegalArgumentException("Unsatisfiable hailstone at index " + i + ". Individual results: " + res1 + " " + res2 + " " + res3);
            }
        }
    }

    private BoolExpr getEquationForDimension(Context z3Context, IntExpr startPositionVar, IntExpr velocityVar, IntExpr timeVar, long hailstoneStartPositionValue, long hailstoneVelocityValue) {
        ArithExpr<IntSort> positionDelta = z3Context.mkMul(timeVar, velocityVar);
        ArithExpr<IntSort> position = z3Context.mkAdd(startPositionVar, positionDelta);
        IntNum hailstoneStartPosition = z3Context.mkInt(hailstoneStartPositionValue);
        IntNum hailstoneVelocity = z3Context.mkInt(hailstoneVelocityValue);
        ArithExpr<IntSort> hailstonePositionDelta = z3Context.mkMul(timeVar, hailstoneVelocity);
        ArithExpr<IntSort> hailstonePosition = z3Context.mkAdd(hailstoneStartPosition, hailstonePositionDelta);
        return z3Context.mkEq(position, hailstonePosition);
    }

    private BoolExpr getValidationEquationForDimension(Context z3Context, IntNum startPosition, IntNum velocity, IntExpr timeVar, Long hailstoneStartPositionValue, Long hailstoneVelocityValue) {
        ArithExpr<IntSort> positionDelta = z3Context.mkMul(timeVar, velocity);
        ArithExpr<IntSort> position = z3Context.mkAdd(startPosition, positionDelta);
        IntNum hailstoneStartPosition = z3Context.mkInt(hailstoneStartPositionValue.toString());
        IntNum hailstoneVelocity = z3Context.mkInt(hailstoneVelocityValue.toString());
        ArithExpr<IntSort> hailstonePositionDelta = z3Context.mkMul(timeVar, hailstoneVelocity);
        ArithExpr<IntSort> hailstonePosition = z3Context.mkAdd(hailstoneStartPosition, hailstonePositionDelta);
        return z3Context.mkEq(position, hailstonePosition);
    }

    private void parseInput(List<String> input) {
        hailstones = new ArrayList<>();
        for (String line : input) {
            String[] positionAndVelocity = line.split(" @ ");
            String[] positionAsStrings = positionAndVelocity[0].split(", ");
            String[] velocityAsStrings = positionAndVelocity[1].split(", ");
            Tuple3<Long, Long, Long> position = Tuple.tuple(parseLong(positionAsStrings[0]), parseLong(positionAsStrings[1]), parseLong(positionAsStrings[2]));
            Tuple3<Long, Long, Long> velocity = Tuple.tuple(parseLong(velocityAsStrings[0]), parseLong(velocityAsStrings[1]), parseLong(velocityAsStrings[2]));
            hailstones.add(Tuple.tuple(position, velocity));
        }
    }

    private static long parseLong(String string) {
        return Long.parseLong(string.trim());
    }

}
