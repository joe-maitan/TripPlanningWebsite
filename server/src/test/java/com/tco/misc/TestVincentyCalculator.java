package com.tco.misc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Assertions;

public class TestVincentyCalculator {
    
    @Test
    @DisplayName("puleoja : absolute lambda difference")
    public void testLambdaCalc(){
        VincentyCalculator calc = new VincentyCalculator();

        assert calc.deltaLambdaCalc(10.0,20.0).equals(10.0);

    }

    @Test
    @DisplayName("puleoja : check random distance")
    public void distanceTest(){
        VincentyCalculator calc = new VincentyCalculator();
        Place from = new Place("40.0","29.0");
        Place to = new Place("67.0","51.0");
        
        Long distance = calc.between(from,to,700);
        //The math says the distance is 361.915
        assert distance.equals(362L);
    }

    @Test
    @DisplayName("puleoja : check distance of 0")
    public void noDistance(){
        VincentyCalculator calc = new VincentyCalculator();
        Place from = new Place("40.0","29.0");
        
        Long distance = calc.between(from,from,700);
        assert distance.equals(0L);
    }
}
