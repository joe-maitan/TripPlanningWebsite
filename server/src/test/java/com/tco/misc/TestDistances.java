package com.tco.misc;

import com.tco.requests.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestDistances {

    private Places test;
    private DistancesRequest dist;
    private double earthRadius;
    private String formula;

    @Test
    @DisplayName("jjmaitan: test adding and summation to distances")
    public void jjmTestDistances() {
        test = new Places();

        Place albany = new Place("-35.02729217867487", "117.88302295381932");
        Place thailand = new Place("13.866447528849083", "100.50848732189924");
        Place argentina = new Place("-34.60551381209748", "-58.385705372566505");
        Place newYork = new Place("40.78309439690183", "-73.97166985759654");
        
        test.add(albany);
        test.add(thailand);
        test.add(argentina);
        test.add(newYork);

        Distances temp = new Distances();
        earthRadius = 72.0;
        formula = "vincenty";
        try {
            dist = new DistancesRequest(test, earthRadius, formula);
            
            temp = dist.buildDistanceList();
        }

        catch(BadRequestException e) {
            assertTrue(false);
        }
        
        assertEquals(564 ,temp.total());
    }

    @Test
    @DisplayName("tjolsonx: test adding and summation to distances")
    public void tjolsonxTestDistances() {
        test = new Places();

        Place albany = new Place("-35.02729217867487", "117.88302295381932");
        Place thailand = new Place("13.866447528849083", "100.50848732189924");
        Place argentina = new Place("-34.60551381209748", "-58.385705372566505");
        Place newYork = new Place("40.78309439690183", "-73.97166985759654");
        
        test.add(albany);
        test.add(thailand);
        test.add(argentina);
        test.add(newYork);

        Distances temp = new Distances();
        earthRadius = 72.0;
        formula = "haversine";

        try {
            dist = new DistancesRequest(test, earthRadius, formula);
            
            temp = dist.buildDistanceList();
        }

        catch(BadRequestException e) {
            assertTrue(false);
        }
        
        assertEquals(564 ,temp.total());
    }

    
} // End public class TestDistances
