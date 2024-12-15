package de.beachboys;

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
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public MixedFraction(long integralPart, long numerator, long denominator) {
        this(BigInteger.valueOf(integralPart), BigInteger.valueOf(numerator), BigInteger.valueOf(denominator));
    }

    public MixedFraction(BigInteger numerator, BigInteger denominator) {
        BigInteger[] dividedAndRemainder = numerator.divideAndRemainder(denominator);
        this.integralPart = dividedAndRemainder[0];
        this.numerator = dividedAndRemainder[1];
        this.denominator = denominator;
    }

    public MixedFraction(long numerator, long denominator) {
        this(BigInteger.valueOf(numerator), BigInteger.valueOf(denominator));
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
