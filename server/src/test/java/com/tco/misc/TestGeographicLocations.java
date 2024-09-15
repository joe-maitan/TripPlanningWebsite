package com.tco.misc;

import com.tco.requests.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestGeographicLocations {
    private GeographicLocations geo;
    private Database mockDatabase;

    @Test
    @DisplayName("bagel: initial test for find")
    public void initialFind() {
        Places test = new Places();
        geo = new GeographicLocations();
        test = geo.find("kitty", null, null, 11);
        assert(test.get(0).get("name").equals("The Palms At Kitty Hawk Airport")); //showing test is created 
    }
    
    @Test
    @DisplayName("bagel: initial test for found")
    public void initialFound() {
        int test = 0;
        geo = new GeographicLocations();
        test = geo.found("kitty");
        assert(test == 10); //showing test is created 
    }

    @Test
    @DisplayName("jjmaitan: test find") 
    public void testFindJoe() {
        Places test = new Places();
        geo = new GeographicLocations();
        test = geo.find("Colorado", null, null, 1);
        assertEquals(test.get(0).get("name"), "Colorado Plains Medical Center Heliport");
    }

    @Test
    @DisplayName("jjmaitan: test found")
    public void testFoundJoe() {
        int found = 0;
        geo = new GeographicLocations();
        found = geo.found("Colorado");
        assertEquals(found, 26);
    }

    @Test
    @DisplayName("tjolsonx: Test near method for expected number of places within given radius")
    public void testNearMethod() {
       
        geo = new GeographicLocations();
        Place testPlace = new Place("38.8403583", "-104.7995667");
        double earthRadius = 3959; 
        double distance = 200; 
        int limit = 5; 

        Places resultPlaces = geo.near(testPlace, earthRadius, distance, limit);

        assertTrue(resultPlaces.size() <= limit, "Shouldn't exceed " + limit);
        assertTrue(resultPlaces.size() > 0, "Should find at least one place within 200 miles");
        
    }

    @Test
    @DisplayName("jjmaitan: Test near method for a limit of 1")
    public void jjmaitanInitalTestNear() {
        geo = new GeographicLocations();
        Place paris = new Place("48.85354194349373", "2.347709989081591");
        double earthRadius = 6371;
        double distance = 25; // radius
        int limit = 1;

        Places results = geo.near(paris, earthRadius, distance, limit);
        assertEquals(results.size(), limit);
        assertEquals(results.get(0).get("name"), "Centre Hospitalier Heliport");
    }

    @Test
    @DisplayName("jjmaitan: Test near method for a limit of 25")
    public void jjmaitanTestNear() {
        geo = new GeographicLocations();
        Place paris = new Place("48.85354194349373", "2.347709989081591");
        double earthRadius = 6371;
        double distance = 25; // radius
        int limit = 25;

        Places results = geo.near(paris, earthRadius, distance, limit);
        assertTrue(results.size() <= limit);
        assertEquals(results.get(14).get("name"), "Charles de Gaulle International Airport");
    }

    @Test
    @DisplayName("caden147: Test limit of 50")
    public void nearFiftyLimit() {
        geo = new GeographicLocations();
        Place temp = new Place("40", "-100");
        double earthRadius = 3959;
        double distance = 25;
        int limit = 50;

        Places result = geo.near(temp, earthRadius, distance, limit);
        assertTrue(result.size() <= limit);
    }
}
