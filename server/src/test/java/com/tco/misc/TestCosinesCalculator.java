package com.tco.misc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestCosinesCalculator {

    private CosinesCalculator calc;
    private double earthRadius;
    private Place from;
    private Place to;

    @BeforeEach
    public void createCosineCalculator() {
        calc = new CosinesCalculator();
        earthRadius = 6371;
    } // End createCosineCalculator() method

    @Test
    @DisplayName("bagel : between Eaton, Big Ben")
    public void testBetween(){
        from = new Place("40.53577233220049", "-104.71392980927988");
        to = new Place("51.50076924429712", "-0.12461467399278547");
        assert(calc.between(from, to, earthRadius) == 7459L);
    }

    @Test
    @DisplayName("jjmaitan: between Castle Rock, Colorado and Sydney, Australia")
    public void jjmaitanTestBetween() {
        from = new Place("39.371649189086675", "-104.8588142597655");
        to = new Place("-33.86833256979209", "151.2134520662958");
        assert(calc.between(from, to, earthRadius) == 13402L);
    } // End testBetween() method

    @Test
    @DisplayName("bagel : Small Earth Radius")
    public void smallEarthRadius() {
        double smallER = 1;
        from = new Place("47.61662438701279", "-122.19078542853347");
        to = new Place("36.1001089112929", "-112.1123988283511");
        assert(calc.between(from, to, smallER) == 0L);
    }

    @Test
    @DisplayName("bagel : Decimal Earth Radius")
    public void decimalEarthRadius() {
        double decimalER = 3185.5;
        from = new Place("38.282462382403665", "129.61989418695018");
        to = new Place("39.90456448901534", "116.41308840521548");
        assert(calc.between(from, to, decimalER) == 576L);
    }

} // End TestCosinesCalculator class
