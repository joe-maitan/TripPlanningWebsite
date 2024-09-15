package com.tco.requests;

import com.tco.misc.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestNearRequest {

    private String requestType;
    private NearRequest near;
    private Place place;
    private double distance;
    private double earthRadius;
    private int limit;
    

    @BeforeEach
    public void initializeNearRequest() {
       
       this.place = new Place(); 
       this.distance = 5.0; // in miles
       this.earthRadius = 3959.0; // Radius of the earth in miles
       this.limit = 1; // Limit the results to 1 place

        // Initialize NearRequest with test data
        this.near = new NearRequest(this.place, this.earthRadius, this.distance, this.limit);
    }

    @Test
    @DisplayName("tjolsonx: Test NearRequest builds the correct Place response")

        public void testRequestType(){
            assertEquals("near", this.near.getRequestType());
        }
    
}
