package de.beachboys;

import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class MixedFraction {

    private static final int DEFAULT_SCALE = 10;
    private final BigInteger integralPart;
    private final BigInteger numerator;
    private final BigInteger denominator;

    public MixedFraction(BigInteger integralPart, BigInteger numerator, BigInteger denominator) {
        this.integralPart = integralPart;
        Tuple2<BigInteger, BigInteger> normalizedValues = normalize(numerator, denominator);
        this.numerator = normalizedValues.v1;
        this.denominator = normalizedValues.v2;
    }

    public MixedFraction(long integralPart, long numerator, long denominator) {
        this(BigInteger.valueOf(integralPart), BigInteger.valueOf(numerator), BigInteger.valueOf(denominator));
    }

    public MixedFraction(BigInteger numerator, BigInteger denominator) {
        BigInteger[] dividedAndRemainder = numerator.divideAndRemainder(denominator);
        this.integralPart = dividedAndRemainder[0];
        Tuple2<BigInteger, BigInteger> normalizedValues = normalize(dividedAndRemainder[1], denominator);
        this.numerator = normalizedValues.v1;
        this.denominator = normalizedValues.v2;
    }

    public MixedFraction(long numerator, long denominator) {
        this(BigInteger.valueOf(numerator), BigInteger.valueOf(denominator));
    }

    private Tuple2<BigInteger, BigInteger> normalize(BigInteger numerator, BigInteger denominator) {
        BigInteger normalizedNumerator = numerator;
        BigInteger normalizedDenominator = denominator;
        if (normalizedDenominator.compareTo(BigInteger.ZERO) < 0) {
            normalizedNumerator = normalizedNumerator.multiply(BigInteger.valueOf(-1));
            normalizedDenominator = normalizedDenominator.multiply(BigInteger.valueOf(-1));
        }
        BigInteger gcd = normalizedNumerator.gcd(normalizedDenominator);
        if (gcd.compareTo(BigInteger.ONE) > 0) {
            normalizedNumerator = normalizedNumerator.divide(gcd);
            normalizedDenominator = normalizedDenominator.divide(gcd);
        }
        return Tuple.tuple(normalizedNumerator, normalizedDenominator);
    }

    public BigInteger getIntegralPart() {
        return integralPart;
    }

    public BigInteger getNumerator() {
        return numerator;
    }

    public BigInteger getDenominator() {
        return denominator;
    }

    public long getIntegralPartLong() {
        return getIntegralPart().longValueExact();
    }

    public long getNumeratorLong() {
        return getNumerator().longValueExact();
    }

    public long getDenominatorLong() {
        return getDenominator().longValueExact();
    }

    public BigDecimal getValue(int scale) {
        BigInteger tempNumerator = numerator.add(integralPart.multiply(denominator));
        return new BigDecimal(tempNumerator).divide(new BigDecimal(denominator), scale, RoundingMode.HALF_UP);
    }

    public BigDecimal getValue() {
        return getValue(DEFAULT_SCALE);
    }

    public double getValueDouble() {
        return getValue().doubleValue();
    }
}
