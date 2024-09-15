package com.tco.requests;

import com.tco.misc.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestDistancesRequest {

    private static final transient Logger log = LoggerFactory.getLogger(DistancesRequest.class);

    private DistancesRequest dist;
    private Places places;
    private double earthRadius;
    private String type;
    private String formula;
    private CalculatorFactory calcFactory;
    private DistanceCalculator calc;

    @BeforeEach
    public void createNewDistancesRequestForTestCases() throws BadRequestException {
        places = new Places();
        earthRadius = 6371.0;
        formula = "vincenty";
        dist = new DistancesRequest(places, earthRadius, formula);
        calcFactory = new CalculatorFactory();
    } // End createDistanceRequestTest() method

    @Test   
    @DisplayName("jjmaitan: Request type is \"distances\"")
    public void testType() {
        type = dist.getRequestType();
        assertEquals("distances", type);
    } // End testType() test

    @Test
    @DisplayName("bagel: Test useCalculator")
    public void testUseCalculator() throws BadRequestException{
        Place albany = new Place("-35.02729217867487", "117.88302295381932");
        Place thailand = new Place("13.866447528849083", "100.50848732189924");

        places.add(albany);
        places.add(thailand);

        Distances temp = new Distances();
        earthRadius = 72.0;

        dist = new DistancesRequest(places, earthRadius, formula);

        assertEquals(65L, dist.useCalculator(albany, thailand, calcFactory.get(formula)));

    }

    @Test
    @DisplayName("tjolsonx: buildDistanceList multiple places")
    void testBuildDistanceListMultiplePlaces() throws BadRequestException{
        Places testPlaces = new Places();
        testPlaces.add(new Place("39.7392", "-104.9903"));
        testPlaces.add(new Place("34.0522", "-118.2437"));
        Double earthRadius = 6371.0;
        String formula = "haversine";
        
        dist = new DistancesRequest(testPlaces, earthRadius, formula);

        Distances distances = dist.buildDistanceList();

        assertEquals(2, distances.size(), "One distance should be calculated for two places.");
        assertEquals(1336,  distances.get(0), "The calculated distance should match the fixed value from our DistanceCalculator.");
    }

    @Test
    @DisplayName("jjmaitan: my DistancesRequest test")
    void jjmaitanTestBuildDistancesList() throws BadRequestException{
        Place albany = new Place("-35.02729217867487", "117.88302295381932");
        Place thailand = new Place("13.866447528849083", "100.50848732189924");
        Place argentina = new Place("-34.60551381209748", "-58.385705372566505");
        Place newYork = new Place("40.78309439690183", "-73.97166985759654");

        places.add(albany);
        places.add(thailand);
        places.add(argentina);
        places.add(newYork);
    
        Distances temp = new Distances();
        earthRadius = 72.0;
        formula = "vincenty";

        dist = new DistancesRequest(places, earthRadius, formula);
        
        temp = dist.buildDistanceList();
        
        assertEquals("distances", dist.getRequestType());
        assertEquals(4, places.size());
        assertEquals(4, temp.size());
        assertEquals(65, temp.get(0));
        assertEquals(191, temp.get(1));
        assertEquals(96, temp.get(2));
        assertEquals(212, temp.get(3));
    } // End test

    @Test
    @DisplayName("jjmaitan: brennawr.json DistancesRequest test")
    void brennaDistancesRequest() throws BadRequestException{
        Place alaska = new Place("67.225436", "-150.180558");
        Place chile = new Place("-32.990413", "-71.268997");
        Place austrailia = new Place("-33.275114", "149.085303");
        Place china = new Place("22.328186", "114.167598");

        places.add(alaska);
        places.add(chile);
        places.add(austrailia);
        places.add(china);
    
        Distances temp = new Distances();
        earthRadius = 6965840.0;
        formula = "vincenty";

        dist = new DistancesRequest(places, earthRadius, formula);
        
        temp = dist.buildDistanceList();
        
        assertEquals("distances", dist.getRequestType());
        assertEquals(4, places.size());
        assertEquals(4, temp.size());
        assertEquals(14112451, temp.get(0));
        assertEquals(12598939, temp.get(1));
        assertEquals(7878828, temp.get(2));
        assertEquals(8709473, temp.get(3));
    } // End test

    @Test
    @DisplayName("jjmaitan: aidansim.json DistancesRequest test")
    void aidanDistancesRequest() throws BadRequestException{
        Place france = new Place("48.856389", "2.351389");
        Place ireland = new Place("51.898889", "-8.475278");
        Place zambia = new Place("-14.212222", "28.439167");
        Place ecuador = new Place("-2.898889", "-79.006389");

        places.add(france);
        places.add(ireland);
        places.add(zambia);
        places.add(ecuador);
    
        Distances temp = new Distances();
        earthRadius = 4454.0;
        formula = "vincenty";

        dist = new DistancesRequest(places, earthRadius, formula);
        
        temp = dist.buildDistanceList();
        
        assertEquals("distances", dist.getRequestType());
        assertEquals(4, places.size());
        assertEquals(4, temp.size());
        assertEquals(586, temp.get(0));
        assertEquals(5709, temp.get(1));
        assertEquals(8250, temp.get(2));
        assertEquals(6726, temp.get(3));
    } // End test

    @Test
    @DisplayName("bagel: Test 1 place")
    void onePlaceTestBuildDistances() throws BadRequestException{
        Place newYork = new Place("40.78309439690183", "-73.97166985759654");

        places.add(newYork);
    
        Distances temp = new Distances();
        earthRadius = 72.0;
        formula = null;

        dist = new DistancesRequest(places, earthRadius, formula);
        
        temp = dist.buildDistanceList();
        
        assertEquals("distances", dist.getRequestType());
        assertEquals(0, temp.get(0));
    }

    @Test
    @DisplayName("jjmaitan: Test isPlacesValid() method")
    void testIsPlacesValid() {
        assert(places != null);
    } // End testIsPlacesValid()

    @Test
    @DisplayName("jjmaitan: Test calculateDistances(dist) method")
    void testCalculateDistances() throws BadRequestException{
        Distances temp = new Distances();
        earthRadius = 72.0;

        Place albany = new Place("-35.02729217867487", "117.88302295381932");
        Place thailand = new Place("13.866447528849083", "100.50848732189924");

        places.add(albany);
        places.add(thailand);

        dist.calculateDistances(temp);
        
        assertEquals(5737, temp.get(0));
    } // End testCalculateDistances()

} // End TestDistanceRequest class
