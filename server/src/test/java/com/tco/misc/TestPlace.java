package com.tco.misc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Assertions;
import static java.lang.Math.toRadians;

public class TestPlace {

    private Place test;

    @Test
    @DisplayName("bagel : Create Place Object")
    public void testPlaceConstructor(){
        test = new Place("40.52440576918095", "-105.05004938623708");
        assert test.get("latitude").equals("40.52440576918095");
        assert test.get("longitude").equals("-105.05004938623708");
    }

    @Test
    @DisplayName("puleoja : GeoCoord Methods")
    public void geoCoordMethods(){
        test = new Place("90", "180");

        double latTest = toRadians(90);
        double lonTest = toRadians(180);

        assert test.latRadians().equals(latTest);
        assert test.lonRadians().equals(lonTest);
    }

}
