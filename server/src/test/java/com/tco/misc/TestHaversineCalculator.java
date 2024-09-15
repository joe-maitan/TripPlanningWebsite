package com.tco.misc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Assertions;

public class TestHaversineCalculator {
    @Test
    @DisplayName("puleoja : absolute lambda difference")
    public void testLambdaCalc(){
        HaversineCalculator calc = new HaversineCalculator();
        assert calc.deltaLambdaCalc(5.0,13.0).equals(8.0);
    }

    @Test
    @DisplayName("puleoja : absolute phi difference")
    public void testPhiCalc(){
        HaversineCalculator calc = new HaversineCalculator();
        assert calc.deltaPhiCalc(9.0,35.0).equals(26.0);
    }

    @Test
    @DisplayName("puleoja : check random distance")
    public void distanceTest(){
        HaversineCalculator calc = new HaversineCalculator();
        Place from = new Place("40.0","29.0");
        Place to = new Place("67.0","51.0");
        
        Long distance = calc.between(from,to,700);
        assert distance.equals(362L);
    }
    
}
