package com.n26.nikhil.stats.utils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class BigDecimalRoundTwoHalfUp extends BigDecimal {
    private static MathContext context = new MathContext(6, RoundingMode.HALF_UP);

    public BigDecimalRoundTwoHalfUp(String val, MathContext mc) {
        super(val, mc);
    }

    public BigDecimalRoundTwoHalfUp(String s) {
        super(s, context);
    }
    public BigDecimalRoundTwoHalfUp(BigDecimal bd) {
        this(bd.toString());
    }

    public BigDecimal divide( BigDecimal divisor ){
        return new BigDecimalRoundTwoHalfUp( super.divide( divisor, context ) );
    }
    public BigDecimal add( BigDecimal augend ){
        return new BigDecimalRoundTwoHalfUp( super.add( augend,context ) );
    }

    public BigDecimal multiply( BigDecimal multiple ){
        return new BigDecimalRoundTwoHalfUp( super.multiply( multiple,context ) );
    }
    public BigDecimalRoundTwoHalfUp subtract( BigDecimal multiple ){
        return new BigDecimalRoundTwoHalfUp( super.subtract( multiple,context ) );
    }

    public static BigDecimal valueOf(long value){
        return new BigDecimalRoundTwoHalfUp( BigDecimal.valueOf( value ) );
    }





}
