package de.beachboys.aoc2023;

import com.microsoft.z3.*;
import de.beachboys.Day;
import de.beachboys.Util;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Day24 extends Day {

    private List<Pair<Triplet<BigDecimal, BigDecimal, BigDecimal>, Triplet<BigDecimal, BigDecimal, BigDecimal>>> hailstones;

    public Object part1(List<String> input) {
        parseInput(input);
        long min = Util.getLongValueFromUser("min", 200000000000000L, io);
        long max = Util.getLongValueFromUser("max", 400000000000000L, io);

        long result = 0;
        for (int i = 0; i < hailstones.size()-1; i++) {
            for (int j = i+1; j < hailstones.size(); j++) {
                Pair<Triplet<BigDecimal, BigDecimal, BigDecimal>, Triplet<BigDecimal, BigDecimal, BigDecimal>> hailstone1 = hailstones.get(i);
                Pair<Triplet<BigDecimal, BigDecimal, BigDecimal>, Triplet<BigDecimal, BigDecimal, BigDecimal>> hailstone2 = hailstones.get(j);
                Triplet<BigDecimal, BigDecimal, BigDecimal> pos1 = hailstone1.getValue0();
                Triplet<BigDecimal, BigDecimal, BigDecimal> pos2 = hailstone2.getValue0();
                Triplet<BigDecimal, BigDecimal, BigDecimal> v1 = hailstone1.getValue1();
                Triplet<BigDecimal, BigDecimal, BigDecimal> v2 = hailstone2.getValue1();
                BigDecimal x1 = pos1.getValue0();
                BigDecimal y1 = pos1.getValue1();
                BigDecimal x2 = pos1.getValue0().add(v1.getValue0());
                BigDecimal y2 = pos1.getValue1().add(v1.getValue1());
                BigDecimal x3 = pos2.getValue0();
                BigDecimal y3 = pos2.getValue1();
                BigDecimal x4 = pos2.getValue0().add(v2.getValue0());
                BigDecimal y4 = pos2.getValue1().add(v2.getValue1());

                BigDecimal denominator = x1.subtract(x2).multiply(y3.subtract(y4)).subtract(y1.subtract(y2).multiply((x3.subtract(x4))));
                if (!denominator.equals(BigDecimal.ZERO)) {
                    BigDecimal f1 = x1.multiply(y2).subtract(y1.multiply(x2));
                    BigDecimal f2 = x3.multiply(y4).subtract(y3.multiply(x4));
                    BigDecimal x = f1.multiply(x3.subtract(x4)).subtract(x1.subtract(x2).multiply(f2)).divide(denominator, RoundingMode.HALF_UP);
                    BigDecimal y = f1.multiply(y3.subtract(y4)).subtract(y1.subtract(y2).multiply(f2)).divide(denominator, RoundingMode.HALF_UP);
                    BigDecimal time = x.subtract(x1).divide(v1.getValue0(), RoundingMode.HALF_UP);
                    BigDecimal time2 = x.subtract(x3).divide(v2.getValue0(), RoundingMode.HALF_UP);

                    if (time.compareTo(BigDecimal.ZERO) > 0 && time2.compareTo(BigDecimal.ZERO) > 0
                            && x.doubleValue() >= min && x.doubleValue() <= max
                            && y.doubleValue() >= min && y.doubleValue() <= max) {
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
            Pair<Triplet<BigDecimal, BigDecimal, BigDecimal>, Triplet<BigDecimal, BigDecimal, BigDecimal>> hailstone = hailstones.get(i);
            z3Solver.add(getEquationForDimension(z3Context, startPositionXVar, velocityXVar, timeVar, hailstone.getValue0().getValue0(), hailstone.getValue1().getValue0()));
            z3Solver.add(getEquationForDimension(z3Context, startPositionYVar, velocityYVar, timeVar, hailstone.getValue0().getValue1(), hailstone.getValue1().getValue1()));
            z3Solver.add(getEquationForDimension(z3Context, startPositionZVar, velocityZVar, timeVar, hailstone.getValue0().getValue2(), hailstone.getValue1().getValue2()));
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
            Pair<Triplet<BigDecimal, BigDecimal, BigDecimal>, Triplet<BigDecimal, BigDecimal, BigDecimal>> hailstone = hailstones.get(i);
            Status res1 = z3Solver.check(getValidationEquationForDimension(z3Context, startPositionXVar, velocityXVar, timeVar, hailstone.getValue0().getValue0(), hailstone.getValue1().getValue0()));
            Status res2 = z3Solver.check(getValidationEquationForDimension(z3Context, startPositionYVar, velocityYVar, timeVar, hailstone.getValue0().getValue1(), hailstone.getValue1().getValue1()));
            Status res3 = z3Solver.check(getValidationEquationForDimension(z3Context, startPositionZVar, velocityZVar, timeVar, hailstone.getValue0().getValue2(), hailstone.getValue1().getValue2()));
            if (!(Status.SATISFIABLE.equals(res1) && Status.SATISFIABLE.equals(res2) && Status.SATISFIABLE.equals(res3))) {
                throw new IllegalArgumentException("Unsatisfiable hailstone at index " + i + ". Individual results: " + res1 + " " + res2 + " " + res3);
            }
        }
    }

    private BoolExpr getEquationForDimension(Context z3Context, IntExpr startPositionVar, IntExpr velocityVar, IntExpr timeVar, BigDecimal hailstoneStartPositionValue, BigDecimal hailstoneVelocityValue) {
        ArithExpr<IntSort> positionDelta = z3Context.mkMul(timeVar, velocityVar);
        ArithExpr<IntSort> position = z3Context.mkAdd(startPositionVar, positionDelta);
        IntNum hailstoneStartPosition = z3Context.mkInt(hailstoneStartPositionValue.toString());
        IntNum hailstoneVelocity = z3Context.mkInt(hailstoneVelocityValue.toString());
        ArithExpr<IntSort> hailstonePositionDelta = z3Context.mkMul(timeVar, hailstoneVelocity);
        ArithExpr<IntSort> hailstonePosition = z3Context.mkAdd(hailstoneStartPosition, hailstonePositionDelta);
        return z3Context.mkEq(position, hailstonePosition);
    }

    private BoolExpr getValidationEquationForDimension(Context z3Context, IntNum startPosition, IntNum velocity, IntExpr timeVar, BigDecimal hailstoneStartPositionValue, BigDecimal hailstoneVelocityValue) {
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
            Triplet<BigDecimal, BigDecimal, BigDecimal> position = Triplet.with(parseBigDecimal(positionAsStrings[0]), parseBigDecimal(positionAsStrings[1]), parseBigDecimal(positionAsStrings[2]));
            Triplet<BigDecimal, BigDecimal, BigDecimal> velocity = Triplet.with(parseBigDecimal(velocityAsStrings[0]), parseBigDecimal(velocityAsStrings[1]), parseBigDecimal(velocityAsStrings[2]));
            hailstones.add(Pair.with(position, velocity));
        }
    }

    private static BigDecimal parseBigDecimal(String string) {
        return BigDecimal.valueOf(Long.parseLong(string.trim()));
    }

}
