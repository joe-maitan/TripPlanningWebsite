package com.tco.misc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Assertions;

public class TestPlaces {

    public Places placesArray;

    @BeforeEach
    public void initializePlacesArray() {
        placesArray = new Places();
    }
   
    @Test
    @DisplayName("bagel : One Place ArrayList")
    public void onePlaceTest(){
        Place dairyQueen = new Place("40.52440576918095", "-105.05004938623708");
        placesArray.add(dairyQueen);
        assert(placesArray.get(0).equals(dairyQueen));
    }

    @Test
    @DisplayName("jjmaitan: Add multiple places")
    public void testingAdd() {
        Place p1 = new Place("0", "0");
        placesArray.add(p1);
        Place p2 = new Place("0", "0");
        placesArray.add(p2);
        Place p3 = new Place("0", "0");
        placesArray.add(p3);

        assert(placesArray.size() == 3);
    }

    @Test
    @DisplayName("caden147 : Empty Place ArrayList")
    public void zeroPlacesTest(){
        assert(placesArray.size() == 0);
    }
}
